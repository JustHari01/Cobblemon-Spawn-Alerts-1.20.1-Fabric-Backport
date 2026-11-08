package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import io.github.stainlessstasis.cobblemon_spawn_alerts.core.CobblemonSpawnAlerts;
import net.minecraft.resources.ResourceLocation;

public class ModPackets {
    public static final ResourceLocation POKEMON_DATA = new ResourceLocation(CobblemonSpawnAlerts.MOD_ID, "pokemon-data-packet");
    public static final ResourceLocation ALERT_DATA = new ResourceLocation(CobblemonSpawnAlerts.MOD_ID, "alert-data-packet");
    public static final ResourceLocation DESPAWN_DATA = new ResourceLocation(CobblemonSpawnAlerts.MOD_ID, "despawn-data-packet");
    public static final ResourceLocation MOD_LOADED = new ResourceLocation(CobblemonSpawnAlerts.MOD_ID, "mod-loaded-packet");
}