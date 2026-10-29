package fr.iglee42.igleelib.blockentities.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

import java.util.ArrayList;
import java.util.List;

public final class ListSerializer implements FieldSerializer {

    private final Codec<Object> codec;

    @SuppressWarnings("unchecked")
    public ListSerializer(FieldSerializer elementSerializer) {
        this.codec = (Codec<Object>) (Codec<?>) elementSerializer.codec().listOf();
    }

    @Override
    public void encode(HolderLookup.Provider registries, CompoundTag out, String name, Object value) {
        if (value == null) return;
        out.put(name, codec.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), value).getOrThrow());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object decode(HolderLookup.Provider registries, CompoundTag in, String name) {
        Codec<List<Object>> listCodec = (Codec<List<Object>>) (Codec<?>) codec;
        return in.contains(name, CompoundTag.TAG_LIST)
            ? listCodec.parse(registries.createSerializationContext(NbtOps.INSTANCE), in.get(name))
                .result()
                .map(ArrayList::new)
                .orElseGet(ArrayList::new)
            : new ArrayList<>();
    }

    @Override
    public Codec<Object> codec() {
        return codec;
    }
}
