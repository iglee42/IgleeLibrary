package fr.iglee42.igleelib.blockentities.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.lang.reflect.Constructor;

/**
 * Serializer for classes implementing {@link INBTSerializable}. The class needs a no-arg constructor.
 * When used as a list/map element, registries are not available (an empty provider is used).
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class NbtSerializableSerializer implements FieldSerializer {

    private final Class<?> clazz;
    private final Constructor<?> constructor;
    private final Codec<Object> codec;

    public NbtSerializableSerializer(Class<?> clazz) {
        this.clazz = clazz;
        try {
            this.constructor = clazz.getDeclaredConstructor();
            this.constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(
                "INBTSerializable type '" + clazz.getName() + "' requires a no-arg constructor", e
            );
        }
        this.codec = Codec.of(
            new com.mojang.serialization.Encoder<Object>() {
                @Override
                public <T> DataResult<T> encode(Object input, com.mojang.serialization.DynamicOps<T> ops, T prefix) {
                    Tag tag = ((INBTSerializable<Tag>) input).serializeNBT(HolderLookup.Provider.create(java.util.stream.Stream.empty()));
                    return DataResult.success(NbtOps.INSTANCE.convertTo(ops, tag));
                }
            },
            new com.mojang.serialization.Decoder<Object>() {
                @Override
                public <T> DataResult<Pair<Object, T>> decode(com.mojang.serialization.DynamicOps<T> ops, T input) {
                    try {
                        Tag tag = ops.convertTo(NbtOps.INSTANCE, input);
                        INBTSerializable<Tag> obj = (INBTSerializable<Tag>) constructor.newInstance();
                        obj.deserializeNBT(HolderLookup.Provider.create(java.util.stream.Stream.empty()), tag);
                        return DataResult.success(Pair.of(obj, input));
                    } catch (ReflectiveOperationException | RuntimeException e) {
                        return DataResult.error(() -> "Failed to decode " + clazz.getName() + ": " + e.getMessage());
                    }
                }
            }
        );
    }

    @Override
    public void encode(HolderLookup.Provider registries, CompoundTag out, String name, Object value) {
        if (value == null) return;
        out.put(name, ((INBTSerializable<Tag>) value).serializeNBT(registries));
    }

    @Override
    public Object decode(HolderLookup.Provider registries, CompoundTag in, String name) {
        if (!in.contains(name)) return null;
        try {
            INBTSerializable<Tag> obj = (INBTSerializable<Tag>) constructor.newInstance();
            obj.deserializeNBT(registries, in.get(name));
            return obj;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot instantiate " + clazz.getName(), e);
        }
    }

    @Override
    public Codec<Object> codec() {
        return codec;
    }
}
