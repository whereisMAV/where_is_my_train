package com.example.helpmav;

import okhttp3.*;
import com.google.gson.*;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import java.util.List;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private MapView map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().load(getApplicationContext(), getPreferences(MODE_PRIVATE));

        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        map.setMultiTouchControls(true);


        IMapController mapController = map.getController();
        mapController.setZoom(10.0);
        GeoPoint startPoint = new GeoPoint(47.4979, 19.0402);
        mapController.setCenter(startPoint);


       // drawTrainArrow(new GeoPoint(47.4979, 19.0402), new GeoPoint(47.5, 19.1));


        fetchTrainPositions();
    }

    private void drawTrainArrow(GeoPoint start, GeoPoint end) {
        double bearing = start.bearingTo(end);

        Marker marker = new Marker(map);
        marker.setPosition(start);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setRotation((float) bearing);
        marker.setFlat(true);
        Drawable icon = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_assistant_navigation);
        marker.setIcon(icon);
        map.getOverlays().add(marker);
        map.invalidate();
    }

    private Bitmap getBitmapFromDrawable(@DrawableRes int resId) {
        Drawable drawable = ContextCompat.getDrawable(this, resId);
        if (drawable == null) return null;

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void fetchTrainPositions() {
        OkHttpClient client = new OkHttpClient();

        String graphqlQuery = "{ vehiclePositions(swLat: 45.7371, swLon: 16.1139, neLat: 48.5852, neLon: 22.8977, modes: [RAIL]) { vehicleId lat lon heading label lastUpdated speed stopRelationship { status stop { gtfsId name } arrivalTime departureTime } trip { id gtfsId routeShortName tripHeadsign tripShortName route { mode shortName longName textColor color } pattern { id } } } }";

        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("query", graphqlQuery);
        jsonPayload.add("variables", new JsonObject());

        RequestBody body = RequestBody.create(
                jsonPayload.toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("https://emma.mav.hu//otp2-backend/otp/routers/default/index/graphql")  // <-- replace with your real API
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // handle failure
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonResponse = response.body().string();

                    JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                    JsonObject data = jsonObject.getAsJsonObject("data");

                    if (data != null && data.has("vehiclePositions")) {

                        JsonArray vehicles = data.getAsJsonArray("vehiclePositions");
                        List<Train> trains = TrainParser.parseTrains(vehicles);

                        runOnUiThread(() -> {
                            map.getOverlays().clear();

                            for (Train train : trains) {


                                GeoPoint position = new GeoPoint(train.getLatitude(), train.getLongitude());

                                Marker marker = new Marker(map);
                                marker.setPosition(position);
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                                marker.setRotation((float) train.getDirection());
                                marker.setFlat(true);
                                marker.setTitle(train.getTrainHeadSign());

                                String snippet = "Route: " + train.getTrainHeadSign() +
                                        "\nFrom: " + train.getOriginStation() +
                                        "\nTo: " + train.getDestinationStation() +
                                        "\nDelay: " + (train.getDelaySeconds() / 60) + " min";

                                marker.setSnippet(snippet);

                                Drawable icon = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_assistant_navigation);

              /*
                                String colorHex = train.get(); // you need to add getRouteColor() in Train model and parse it
                                if (colorHex != null && !colorHex.isEmpty()) {
                                    try {
                                        int color = android.graphics.Color.parseColor("#" + colorHex);
                                        icon.setTint(color);
                                    } catch (IllegalArgumentException e) {
                                        // fallback if color parsing fails, do nothing or set default color
                                    }
                                }*/

                                marker.setIcon(icon);

                                map.getOverlays().add(marker);
                            }

                            map.invalidate();
                        });
                    }
                } else {
                    // handle error
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume(); // needed for compass, my location overlays, etc.
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();  // needed for compass, my location overlays, etc.
    }
}
