package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import net.minecraft.network.FriendlyByteBuf;

public record DespawnDataPacket(AlertDataPacket alertData, String despawnReason) {
    public void write(FriendlyByteBuf buffer) {
        alertData.write(buffer);
        buffer.writeUtf(despawnReason);
    }

    public static DespawnDataPacket read(FriendlyByteBuf buffer) {
        return new DespawnDataPacket(AlertDataPacket.read(buffer), buffer.readUtf());
    }
}