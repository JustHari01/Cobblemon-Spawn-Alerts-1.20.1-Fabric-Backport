package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import net.minecraft.network.FriendlyByteBuf;

public record PokemonRarityData(boolean isShiny, boolean isLegendary, boolean isMythical,
                                boolean isUltraBeast, boolean isParadox, boolean isStarter) {
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(isShiny);
        buffer.writeBoolean(isLegendary);
        buffer.writeBoolean(isMythical);
        buffer.writeBoolean(isUltraBeast);
        buffer.writeBoolean(isParadox);
        buffer.writeBoolean(isStarter);
    }

    public static PokemonRarityData read(FriendlyByteBuf buffer) {
        return new PokemonRarityData(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(),
                buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean());
    }
}