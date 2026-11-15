package fr.iglee42.igleelib.blockentities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static net.minecraft.nbt.CompoundTag.TAG_STRING;

/**
 * Serializer for enum values used by the {@link Save} field system.
 * <p>
 * Two enum families are supported:
 * <ul>
 *     <li>Regular enums are encoded/decoded by {@link Enum#name()}.</li>
 *     <li>Enums implementing {@link StringRepresentable} use {@link StringRepresentable#fromEnum} so serialized
 *     values match the enum's {@link StringRepresentable#getSerializedName()} contract.</li>
 * </ul>
 */
public final class EnumSerializer implements FieldSerializer {

    private final Class<? extends Enum<?>> enumClass;
    private final Enum<?> defaultValue;
    private final Codec<Object> codec;

    /**
     * Creates a serializer for an enum class.
     * <p>
     * Decode fallback rules:
     * <ul>
     *     <li>When the NBT key is missing, current field value is kept if it is of the same enum type.</li>
     *     <li>Otherwise, the first enum constant is used as default.</li>
     *     <li>When the stored value is invalid, the same fallback is used.</li>
     * </ul>
     */
    @SuppressWarnings("unchecked")
    public EnumSerializer(Class<?> clazz) {
        this.enumClass = (Class<? extends Enum<?>>) clazz.asSubclass(Enum.class);
        Enum<?>[] values = enumClass.getEnumConstants();
        if (values.length == 0) {
            throw new IllegalArgumentException("Enum type '" + enumClass.getName() + "' has no constants");
        }
        this.defaultValue = values[0];

        if (StringRepresentable.class.isAssignableFrom(enumClass)) {
            this.codec = stringRepresentableCodec(enumClass);
        } else {
            Map<String, Enum<?>> byName = Arrays.stream(values).collect(Collectors.toMap(
                Enum::name,
                value -> value
            ));

            this.codec = Codec.STRING.comapFlatMap(
                name -> {
                    Enum<?> value = byName.get(name);
                    if (value != null) return DataResult.success(value);
                    return DataResult.error(() -> "Unknown " + enumClass.getSimpleName() + " value '" + name + "'");
                },
                value -> ((Enum<?>) value).name()
            );
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Codec<Object> stringRepresentableCodec(Class<? extends Enum<?>> enumClass) {
        return (Codec<Object>) (Codec<?>) stringRepresentableCodecTyped((Class) enumClass);
    }

    private static <E extends Enum<E> & StringRepresentable> Codec<E> stringRepresentableCodecTyped(Class<E> enumClass) {
        return StringRepresentable.fromEnum(enumClass::getEnumConstants);
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
        Object fallback = enumClass.isInstance(current) ? current : defaultValue;
        if (!in.contains(name, TAG_STRING)) return fallback;
        return codec.parse(registries.createSerializationContext(NbtOps.INSTANCE), in.get(name))
            .result()
            .orElse(fallback);
    }

    @Override
    public Codec<Object> codec() {
        return codec;
    }
}




