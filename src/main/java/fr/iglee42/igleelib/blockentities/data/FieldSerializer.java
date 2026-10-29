package fr.iglee42.igleelib.blockentities.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public sealed interface FieldSerializer permits FieldType, ListSerializer, MapSerializer, NbtSerializableSerializer {

    void encode(HolderLookup.Provider registries, CompoundTag out, String name, Object value);

    Object decode(HolderLookup.Provider registries, CompoundTag in, String name);

    Codec<Object> codec();

    static FieldSerializer fromField(Field field) {
        Save save = field.getAnnotation(Save.class);
        String context = field.getDeclaringClass().getSimpleName() + "." + field.getName();
        Type genericType = field.getGenericType();

        if (genericType instanceof Class<?> clazz && save != null) {
            if (List.class.isAssignableFrom(clazz) && save.elementType() != void.class) {
                return new ListSerializer(fromType(save.elementType(), context + "<element>"));
            }
            if (Map.class.isAssignableFrom(clazz)
                    && save.keyType() != void.class
                    && save.valueType() != void.class) {
                return new MapSerializer(
                    fromType(save.keyType(), context + "<key>"),
                    fromType(save.valueType(), context + "<value>")
                );
            }
        }

        try {
            return fromType(genericType, context);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("@Save field '" + context + "': " + e.getMessage(), e);
        }
    }

    static FieldSerializer fromType(Type type, String context) {
        if (type instanceof Class<?> clazz) {
            if (List.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException(
                    "Raw collection type '" + clazz.getSimpleName() + "' requires generic type parameters " +
                    "(e.g. List<String>) or explicit @Save(elementType = ...) hints"
                );
            }
            for (FieldType ft : FieldType.values()) {
                if (ft.matches(clazz)) return ft;
            }
            if (INBTSerializable.class.isAssignableFrom(clazz)) return new NbtSerializableSerializer(clazz);
            return FieldType.from(clazz);
        }

        if (type instanceof ParameterizedType pt) {
            Class<?> raw = (Class<?>) pt.getRawType();
            Type[] args = pt.getActualTypeArguments();
            if (List.class.isAssignableFrom(raw)) {
                return new ListSerializer(fromType(args[0], context + "<element>"));
            }
            if (Map.class.isAssignableFrom(raw)) {
                return new MapSerializer(
                    fromType(args[0], context + "<key>"),
                    fromType(args[1], context + "<value>")
                );
            }
            throw new IllegalArgumentException(
                "Unsupported parameterized type '" + raw.getSimpleName() + "'. " +
                "Supported collection types: List, Map"
            );
        }

        throw new IllegalArgumentException(
            "Unsupported type '" + type.getTypeName() + "'. " +
            "Use a scalar type, List<T>, or Map<K,V>"
        );
    }
}
