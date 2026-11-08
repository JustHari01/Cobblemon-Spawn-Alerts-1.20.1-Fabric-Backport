package io.github.stainlessstasis.cobblemon_spawn_alerts;

import io.github.stainlessstasis.cobblemon_spawn_alerts.core.CobblemonSpawnAlerts;
import io.github.stainlessstasis.cobblemon_spawn_alerts.core.CommandRegistry;
import io.github.stainlessstasis.cobblemon_spawn_alerts.network.ModLoadedPacket;
import io.github.stainlessstasis.cobblemon_spawn_alerts.network.ModPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class CSAFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		CobblemonSpawnAlerts.initServer();

		CommandRegistrationCallback.EVENT.register(CommandRegistry::registerCommonCommands);
		ServerPlayConnectionEvents.JOIN.register(this::onPlayerJoin);

	}

	private void onPlayerJoin(ServerGamePacketListenerImpl serverGamePacketListener, PacketSender packetSender, MinecraftServer minecraftServer) {
		var player = serverGamePacketListener.getPlayer();
		if (ServerPlayNetworking.canSend(player, ModPackets.MOD_LOADED)) {
			var buffer = PacketByteBufs.create();
			new ModLoadedPacket(true).write(buffer);
			ServerPlayNetworking.send(player, ModPackets.MOD_LOADED, buffer);
		}
	}
}
