package com.example.helpmav;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class TrainParser {

    public static List<Train> parseTrains(JsonArray vehicles) {
        List<Train> trains = new ArrayList<>();

        for (JsonElement elem : vehicles) {
            JsonObject vehicle = elem.getAsJsonObject();

            String trainId = vehicle.has("vehicleId") ? vehicle.get("vehicleId").getAsString() : "";
            double lat = vehicle.has("lat") ? vehicle.get("lat").getAsDouble() : 0.0;
            double lon = vehicle.has("lon") ? vehicle.get("lon").getAsDouble() : 0.0;
            double direction = vehicle.has("heading") ? vehicle.get("heading").getAsDouble() : 0.0;
            String lineName = "";
            String status = "";
            String nextStation = "";
            long timestamp = vehicle.has("lastUpdated") ? vehicle.get("lastUpdated").getAsLong() * 1000L : 0L; // Convert to ms
            double speed = vehicle.has("speed") ? vehicle.get("speed").getAsDouble() : 0.0;
            int occupancy = 0; // Not in the JSON sample - default 0
            String originStation = "";
            String destinationStation = "";
            int delaySeconds = 0; // Not in sample, default 0
            boolean wheelchairAccessible = false; // Not in sample, default false
            String trainHeadSign = ""; // Not in sample, default empty
            int arrivalTimeSeconds = 0;   // New field, default 0
            int departureTimeSeconds = 0; // New field, default 0

            // label or trip.routeShortName
            if (vehicle.has("label")) {
                lineName = vehicle.get("label").getAsString();
            } else if (vehicle.has("trip")) {
                JsonObject trip = vehicle.getAsJsonObject("trip");
                if (trip.has("routeShortName")) {
                    lineName = trip.get("routeShortName").getAsString();
                }
            }

            // stopRelationship.status and stopRelationship.stop.name
            if (vehicle.has("stopRelationship")) {
                JsonObject stopRel = vehicle.getAsJsonObject("stopRelationship");
                if (stopRel.has("status")) {
                    status = stopRel.get("status").getAsString();
                }
                if (stopRel.has("stop")) {
                    JsonObject stop = stopRel.getAsJsonObject("stop");
                    if (stop.has("name")) {
                        nextStation = stop.get("name").getAsString();
                    }
                }
            }

            // trip.originStation, trip.destinationStation, trip.tripHeadsign (for trainType)
            if (vehicle.has("trip")) {
                JsonObject trip = vehicle.getAsJsonObject("trip");

                if (trip.has("originStation")) {
                    originStation = trip.get("originStation").getAsString();
                }

                if (trip.has("destinationStation")) {
                    destinationStation = trip.get("destinationStation").getAsString();
                }

                if (trip.has("tripHeadsign")) {
                    trainHeadSign = trip.get("tripHeadsign").getAsString();
                }

                // New: parse arrivalTimeSeconds and departureTimeSeconds if present
                if (trip.has("arrivalTimeSeconds")) {
                    arrivalTimeSeconds = trip.get("arrivalTimeSeconds").getAsInt();
                }
                if (trip.has("departureTimeSeconds")) {
                    departureTimeSeconds = trip.get("departureTimeSeconds").getAsInt();
                }
            }

            Train train = new Train(
                    trainId,
                    lineName,
                    lat,
                    lon,
                    status,
                    nextStation,
                    timestamp,
                    speed,
                    direction,
                    occupancy,
                    originStation,
                    destinationStation,
                    delaySeconds,
                    wheelchairAccessible,
                    trainHeadSign,
                    arrivalTimeSeconds,
                    departureTimeSeconds
            );

            trains.add(train);
        }

        return trains;
    }
}
