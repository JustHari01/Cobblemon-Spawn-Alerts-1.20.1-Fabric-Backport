package io.github.stainlessstasis.cobblemon_spawn_alerts.util;

import com.cobblemon.mod.common.api.spawning.CobblemonSpawnPools;
import com.cobblemon.mod.common.api.spawning.context.SpawningContext;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.mojang.serialization.Codec;
import io.github.stainlessstasis.cobblemon_spawn_alerts.config.common.RaritiesConfig;
import io.github.stainlessstasis.cobblemon_spawn_alerts.core.CobblemonSpawnAlerts;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.IntFunction;

public class RarityUtil {
    public enum Bucket implements StringRepresentable {
        COMMON("common", 0),
        UNCOMMON("uncommon", 1),
        RARE("rare", 2),
        ULTRA_RARE("ultra-rare", 3),
        NONE("none", 4);

        private final String name;
        private final int id;
        public static final IntFunction<Bucket> BY_ID = ByIdMap.continuous(
                b -> b.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final Codec<Bucket> CODEC = Codec.INT.xmap(BY_ID::apply, b -> b.id);

        Bucket(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static Bucket fromId(int id) {
            return BY_ID.apply(id);
        }

        public static Bucket fromString(String name) {
            Bucket bucket = StringRepresentable.fromEnum(Bucket::values).byName(name.toLowerCase());
            return bucket != null ? bucket : Bucket.NONE;
        }
    }

    public static Bucket getRarityBucket(PokemonEntity entity, SpawningContext spawningContext) {
        String speciesName = entity.getPokemon().getSpecies().getName().toLowerCase();
        List<SpawnDetail> matchingSpawns = CobblemonSpawnPools.WORLD_SPAWN_POOL.getDetails().stream()
                .filter(detail -> detail instanceof PokemonSpawnDetail spawn
                        && matchesSpecies(spawn.getPokemon().getSpecies(), speciesName))
                .filter(detail -> detail.isSatisfiedBy(spawningContext))
                .toList();

        if (!matchingSpawns.isEmpty()) {
            String bucketName = matchingSpawns.get(0).getBucket().getName();
            Bucket bucket = Bucket.fromString(bucketName);
            if (bucket == Bucket.NONE) {
                CobblemonSpawnAlerts.LOGGER.error(
                        "Cannot get rarity bucket for Pokemon {} with UUID {} - bucket {} is unknown. Defaulting to NONE.",
                        entity.getName().getString(), entity.getUUID(), bucketName);
            }
            return bucket;
        }

        CobblemonSpawnAlerts.LOGGER.warn(
                "Cannot get rarity bucket for Pokemon {} with UUID {} - no matching spawn detail for context {}. Defaulting to NONE.",
                entity.getName().getString(), entity.getUUID(), spawningContext);
        return Bucket.NONE;
    }

    private static boolean matchesSpecies(String spawnSpecies, String speciesName) {
        if (spawnSpecies == null) return false;
        return spawnSpecies.equalsIgnoreCase(speciesName)
                || spawnSpecies.toLowerCase().endsWith(":" + speciesName);
    }

    public static boolean isLegendary(int dexId) {
        return getRaritiesConfig().legendaries().contains(dexId);
    }

    public static boolean isMythical(int dexId) {
        return getRaritiesConfig().mythicals().contains(dexId);
    }

    public static boolean isUltraBeast(int dexId) {
        return getRaritiesConfig().ultra_beasts().contains(dexId);
    }

    public static boolean isParadox(int dexId) {
        return getRaritiesConfig().paradox().contains(dexId);
    }

    public static boolean isStarter(int dexId) {
        return getRaritiesConfig().starters().contains(dexId);
    }

    private static RaritiesConfig getRaritiesConfig() {
        return CobblemonSpawnAlerts.COMMON_CONFIG_MANAGER.getRaritiesConfig();
    }
}