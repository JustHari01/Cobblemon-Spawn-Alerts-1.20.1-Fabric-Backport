package io.github.stainlessstasis.cobblemon_spawn_alerts.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.stainlessstasis.cobblemon_spawn_alerts.core.CobblemonSpawnAlerts;
import io.github.stainlessstasis.cobblemon_spawn_alerts.platform.Services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public final class DexProgressStore {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Set<Integer> SEEN = new HashSet<>();
    private static final Set<Integer> CAUGHT = new HashSet<>();
    private static boolean loaded;

    private DexProgressStore() {}

    public static synchronized boolean hasSeen(int dexId) {
        load();
        return SEEN.contains(dexId);
    }

    public static synchronized boolean hasCaught(int dexId) {
        load();
        return CAUGHT.contains(dexId);
    }

    public static synchronized void markSeen(int dexId) {
        load();
        if (dexId > 0 && SEEN.add(dexId)) save();
    }

    public static synchronized void markCaught(int dexId) {
        load();
        if (dexId > 0) {
            boolean changed = SEEN.add(dexId);
            changed |= CAUGHT.add(dexId);
            if (changed) save();
        }
    }

    private static void load() {
        if (loaded) return;
        loaded = true;
        Path file = getFile();
        if (!Files.isRegularFile(file)) return;

        try {
            JsonObject root = GSON.fromJson(Files.readString(file, StandardCharsets.UTF_8), JsonObject.class);
            if (root == null) return;
            readIds(root.getAsJsonArray("seen"), SEEN);
            readIds(root.getAsJsonArray("caught"), CAUGHT);
        } catch (Exception exception) {
            CobblemonSpawnAlerts.LOGGER.warn("Could not read local Pokédex progress; starting a fresh tracker.", exception);
        }
    }

    private static void readIds(JsonArray values, Set<Integer> destination) {
        if (values == null) return;
        for (JsonElement value : values) {
            try {
                destination.add(value.getAsInt());
            } catch (RuntimeException ignored) {
                // Skip malformed entries while preserving the rest of the saved progress.
            }
        }
    }

    private static void save() {
        Path file = getFile();
        Path temporary = file.resolveSibling(file.getFileName() + ".tmp");
        try {
            Files.createDirectories(file.getParent());
            JsonObject root = new JsonObject();
            root.add("seen", toArray(SEEN));
            root.add("caught", toArray(CAUGHT));
            Files.writeString(temporary, GSON.toJson(root), StandardCharsets.UTF_8);
            try {
                Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicMoveUnavailable) {
                Files.move(temporary, file, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            CobblemonSpawnAlerts.LOGGER.warn("Could not save local Pokédex progress.", exception);
        }
    }

    private static JsonArray toArray(Set<Integer> values) {
        JsonArray result = new JsonArray();
        values.stream().sorted().forEach(result::add);
        return result;
    }

    private static Path getFile() {
        return Services.PLATFORM.getConfigDir().resolve("cobblemon-spawn-alerts").resolve("dex-progress.json");
    }
}