package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.EVs;
import com.cobblemon.mod.common.pokemon.IVs;
import net.minecraft.network.FriendlyByteBuf;

public record PokemonStats(int level, IVs ivs, EVs evYield) {
    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(level);
        buffer.writeVarInt(ivs.get(Stats.HP));
        buffer.writeVarInt(ivs.get(Stats.ATTACK));
        buffer.writeVarInt(ivs.get(Stats.DEFENCE));
        buffer.writeVarInt(ivs.get(Stats.SPECIAL_ATTACK));
        buffer.writeVarInt(ivs.get(Stats.SPECIAL_DEFENCE));
        buffer.writeVarInt(ivs.get(Stats.SPEED));
        buffer.writeVarInt(evYield.get(Stats.HP));
        buffer.writeVarInt(evYield.get(Stats.ATTACK));
        buffer.writeVarInt(evYield.get(Stats.DEFENCE));
        buffer.writeVarInt(evYield.get(Stats.SPECIAL_ATTACK));
        buffer.writeVarInt(evYield.get(Stats.SPECIAL_DEFENCE));
        buffer.writeVarInt(evYield.get(Stats.SPEED));
    }

    public static PokemonStats read(FriendlyByteBuf buffer) {
        int level = buffer.readVarInt();
        IVs ivs = IVs.Companion.createRandomIVs(0);
        ivs.set(Stats.HP, buffer.readVarInt());
        ivs.set(Stats.ATTACK, buffer.readVarInt());
        ivs.set(Stats.DEFENCE, buffer.readVarInt());
        ivs.set(Stats.SPECIAL_ATTACK, buffer.readVarInt());
        ivs.set(Stats.SPECIAL_DEFENCE, buffer.readVarInt());
        ivs.set(Stats.SPEED, buffer.readVarInt());
        EVs evs = EVs.Companion.createEmpty();
        evs.set(Stats.HP, buffer.readVarInt());
        evs.set(Stats.ATTACK, buffer.readVarInt());
        evs.set(Stats.DEFENCE, buffer.readVarInt());
        evs.set(Stats.SPECIAL_ATTACK, buffer.readVarInt());
        evs.set(Stats.SPECIAL_DEFENCE, buffer.readVarInt());
        evs.set(Stats.SPEED, buffer.readVarInt());
        return new PokemonStats(level, ivs, evs);
    }
}
