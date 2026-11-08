package io.github.stainlessstasis.cobblemon_spawn_alerts.network;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.api.pokemon.Natures;
import com.cobblemon.mod.common.pokemon.EVs;
import com.cobblemon.mod.common.pokemon.IVs;
import com.cobblemon.mod.common.pokemon.Nature;
import io.github.stainlessstasis.cobblemon_spawn_alerts.util.RarityUtil;
import net.minecraft.network.FriendlyByteBuf;

public record PokemonDataPacket(int pokemonNetworkID, IVs ivs, EVs evYield, Nature nature,
                                Ability ability, RarityUtil.Bucket bucket) {
    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(pokemonNetworkID);
        new PokemonStats(0, ivs, evYield).write(buffer);
        buffer.writeUtf(nature.getName().getPath());
        buffer.writeUtf(ability.getName());
        buffer.writeVarInt(bucket.getId());
    }

    public static PokemonDataPacket read(FriendlyByteBuf buffer) {
        int entityId = buffer.readVarInt();
        PokemonStats stats = PokemonStats.read(buffer);
        String natureId = buffer.readUtf();
        String abilityId = buffer.readUtf();
        RarityUtil.Bucket bucket = RarityUtil.Bucket.fromId(buffer.readVarInt());

        Nature nature = Natures.INSTANCE.getNature(natureId);
        if (nature == null) {
            nature = Natures.INSTANCE.getNAUGHTY();
        }
        Ability ability = Abilities.INSTANCE.get(abilityId).create(false);
        return new PokemonDataPacket(entityId, stats.ivs(), stats.evYield(), nature, ability, bucket);
    }
}