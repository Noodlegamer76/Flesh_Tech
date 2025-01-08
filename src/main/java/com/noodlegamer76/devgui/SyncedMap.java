package com.noodlegamer76.devgui;

import com.noodlegamer76.devgui.particles.EditableParticleConfig;
import com.noodlegamer76.devgui.particles.EditableParticles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncedMap {
    private static final Map<String, Object> syncedMap = new HashMap<>();

    public synchronized static Object get(String key) {
        return syncedMap.get(key);
    }

    public synchronized static void put(String key, Object value) {
        syncedMap.put(key, value);
    }

    public synchronized static boolean getBoolean(String key) {
        Object value = get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    public synchronized static List<EditableParticleConfig> getAllConfigs() {
        List<EditableParticleConfig> configs = new ArrayList<>();
        for (Object object: syncedMap.values()) {
            if (object instanceof EditableParticleConfig config) {
                configs.add(config);
            }
        }
        return configs;
    }
}