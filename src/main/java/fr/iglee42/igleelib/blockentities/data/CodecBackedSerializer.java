package fr.iglee42.igleelib.blockentities.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public final class CodecBackedSerializer implements FieldSerializer {

    private final Codec<Object> codec;

    public CodecBackedSerializer(Codec<Object> codec) {
        this.codec = codec;
    }

    @Override
    public void encode(HolderLookup.Provider registries, CompoundTag out, String name, Object value) {
        if (value == null) return;
        out.put(name, codec.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), value).getOrThrow());
    }

    @Override
    public Object decode(HolderLookup.Provider registries, CompoundTag in, String name) {
        return decode(registries, in, name, null);
    }

    @Override
    public Object decode(HolderLookup.Provider registries, CompoundTag in, String name, Object current) {
        if (!in.contains(name)) return current;
        return codec.parse(registries.createSerializationContext(NbtOps.INSTANCE), in.get(name))
            .result()
            .orElse(current);
    }

    @Override
    public Codec<Object> codec() {
        return codec;
    }
}

