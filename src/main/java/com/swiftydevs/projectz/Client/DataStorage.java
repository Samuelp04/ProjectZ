package com.swiftydevs.projectz.Client;

import com.google.gson.*;

import java.io.File;
import java.util.UUID;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private static final Gson GSON = new Gson();
    private static final File SAVE_FILE = new File("config/projectz_player_data.json");

    public static void saveData(Map<UUID, Integer> data, String key) {
        JsonObject jsonObject = loadJson();
        JsonObject jsonData = new JsonObject();

        data.forEach((uuid, value) -> jsonData.addProperty(uuid.toString(), value));
        jsonObject.add(key, jsonData);

        try (Writer writer = new FileWriter(SAVE_FILE)) {
            GSON.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, Integer> loadData(String key) {
        JsonObject jsonObject = loadJson();
        Map<UUID, Integer> data = new HashMap<>();

        if (jsonObject.has(key)) {
            JsonObject jsonData = jsonObject.getAsJsonObject(key);
            for (Map.Entry<String, JsonElement> entry : jsonData.entrySet()) {
                UUID uuid = UUID.fromString(entry.getKey());
                int value = entry.getValue().getAsInt();
                data.put(uuid, value);
            }
        }

        return data;
    }

    private static JsonObject loadJson() {
        if (SAVE_FILE.exists()) {
            try (Reader reader = new FileReader(SAVE_FILE)) {
                return GSON.fromJson(reader, JsonObject.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JsonObject();
    }
}
