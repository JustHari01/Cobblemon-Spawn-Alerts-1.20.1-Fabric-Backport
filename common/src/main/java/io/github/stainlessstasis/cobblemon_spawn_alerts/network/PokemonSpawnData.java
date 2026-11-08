package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import io.github.stainlessstasis.cobblemon_spawn_alerts.util.RarityUtil;
import net.minecraft.network.FriendlyByteBuf;
import org.joml.Vector3f;

import java.util.UUID;

public record PokemonSpawnData(String pokemonName, UUID pokemonUUID, Vector3f position, int dexId,
                               String nearestPlayerName, String biomeKey, String dimensionKey,
                               RarityUtil.Bucket bucket) {
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUtf(pokemonName);
        buffer.writeUUID(pokemonUUID);
        buffer.writeFloat(position.x);
        buffer.writeFloat(position.y);
        buffer.writeFloat(position.z);
        buffer.writeVarInt(dexId);
        buffer.writeUtf(nearestPlayerName);
        buffer.writeUtf(biomeKey);
        buffer.writeUtf(dimensionKey);
        buffer.writeVarInt(bucket.getId());
    }

    public static PokemonSpawnData read(FriendlyByteBuf buffer) {
        return new PokemonSpawnData(buffer.readUtf(), buffer.readUUID(),
                new Vector3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()),
                buffer.readVarInt(), buffer.readUtf(), buffer.readUtf(), buffer.readUtf(),
                RarityUtil.Bucket.fromId(buffer.readVarInt()));
    }
}