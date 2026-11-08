package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import net.minecraft.network.FriendlyByteBuf;

public record AlertDataPacket(PokemonSpawnData spawnData, PokemonStats stats,
                              PokemonRarityData rarity, PokemonTraits traits) {
    public void write(FriendlyByteBuf buffer) {
        spawnData.write(buffer);
        stats.write(buffer);
        rarity.write(buffer);
        traits.write(buffer);
    }

    public static AlertDataPacket read(FriendlyByteBuf buffer) {
        return new AlertDataPacket(PokemonSpawnData.read(buffer), PokemonStats.read(buffer),
                PokemonRarityData.read(buffer), PokemonTraits.read(buffer));
    }
}