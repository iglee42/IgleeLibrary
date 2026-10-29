package fr.iglee42.igleelib.blockentities.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.lang.reflect.Constructor;

/**
 * Serializer for classes implementing {@link INBTSerializable}. The field's existing value is reused when present, otherwise a no-arg constructor is required.
 * When used as a list/map element, registries are not available (an empty provider is used).
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public final class NbtSerializableSerializer implements FieldSerializer {

    private final Class<?> clazz;
    private final Constructor<?> constructor;
    private final Codec<Object> codec;

    public NbtSerializableSerializer(Class<?> clazz) {
        this.clazz = clazz;
        Constructor<?> ctor = null;
        try {
            ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);
        } catch (NoSuchMethodException ignored) {
            // Only usable when the field already holds an instance to deserialize into
        }
        this.constructor = ctor;
        this.codec = Codec.of(
                new Encoder<>() {
                    @Override
                    public <T> DataResult<T> encode(Object input, DynamicOps<T> ops, T prefix) {
                        Tag tag = ((INBTSerializable<Tag>) input).serializeNBT(HolderLookup.Provider.create(java.util.stream.Stream.empty()));
                        return DataResult.success(NbtOps.INSTANCE.convertTo(ops, tag));
                    }
                },
                new Decoder<>() {
                    @Override
                    public <T> DataResult<Pair<Object, T>> decode(DynamicOps<T> ops, T input) {
                        try {
                            Tag tag = ops.convertTo(NbtOps.INSTANCE, input);
                            INBTSerializable<Tag> obj = (INBTSerializable<Tag>) newInstance();
                            obj.deserializeNBT(HolderLookup.Provider.create(java.util.stream.Stream.empty()), tag);
                            return DataResult.success(Pair.of(obj, input));
                        } catch (RuntimeException e) {
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
        return decode(registries, in, name, null);
    }

    /** Deserializes into {@code current} when it is an instance of the handled type, otherwise uses the no-arg constructor. */
    @Override
    public Object decode(HolderLookup.Provider registries, CompoundTag in, String name, Object current) {
        boolean reuse = clazz.isInstance(current);
        if (!in.contains(name)) return reuse ? current : null;
        INBTSerializable<Tag> obj = (INBTSerializable<Tag>) (reuse ? current : newInstance());
        obj.deserializeNBT(registries, in.get(name));
        return obj;
    }

    private Object newInstance() {
        if (constructor == null)
            throw new IllegalStateException(
                "INBTSerializable type '" + clazz.getName() + "' needs a no-arg constructor or a default value on the field"
            );
        try {
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot instantiate " + clazz.getName(), e);
        }
    }

    @Override
    public Codec<Object> codec() {
        return codec;
    }
}
