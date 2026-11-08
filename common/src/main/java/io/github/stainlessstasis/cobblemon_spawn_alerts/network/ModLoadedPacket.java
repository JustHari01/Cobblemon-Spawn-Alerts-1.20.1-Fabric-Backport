package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import net.minecraft.network.FriendlyByteBuf;

public record ModLoadedPacket(boolean loaded) {
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBoolean(loaded);
    }

    public static ModLoadedPacket read(FriendlyByteBuf buffer) {
        return new ModLoadedPacket(buffer.readBoolean());
    }
}