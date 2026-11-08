package io.github.stainlessstasis.cobblemon_spawn_alerts.mixin;

import com.cobblemon.mod.common.command.SpawnPokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import io.github.stainlessstasis.cobblemon_spawn_alerts.core.CobblemonSpawnAlerts;
import io.github.stainlessstasis.cobblemon_spawn_alerts.platform.Services;
import io.github.stainlessstasis.cobblemon_spawn_alerts.util.RarityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpawnPokemon.class)
public abstract class PokemonSpawnCommandMixin {
    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
            )
    )
    private boolean cobblemonSpawnAlerts$afterCommandSpawn(Level level, Entity entity) {
        boolean added = level.addFreshEntity(entity);
        if (added && entity instanceof PokemonEntity pokemonEntity
                && CobblemonSpawnAlerts.COMMON_CONFIG_MANAGER.getServerConfig().enableSpawnCommandAlerts()) {
            Services.PLATFORM.onPokemonSpawned(pokemonEntity, RarityUtil.Bucket.NONE);
        }
        return added;
    }
}