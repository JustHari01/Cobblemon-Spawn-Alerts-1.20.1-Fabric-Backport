package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import net.minecraft.network.FriendlyByteBuf;

public record PokemonTraits(String natureID, String abilityID, String genderID, String formID) {
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUtf(natureID);
        buffer.writeUtf(abilityID);
        buffer.writeUtf(genderID);
        buffer.writeUtf(formID);
    }

    public static PokemonTraits read(FriendlyByteBuf buffer) {
        return new PokemonTraits(buffer.readUtf(), buffer.readUtf(), buffer.readUtf(), buffer.readUtf());
    }
}