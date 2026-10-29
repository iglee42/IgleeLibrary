package fr.iglee42.igleelib.blockentities.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapSerializer implements FieldSerializer {

    private record Entry(Object key, Object value) {}

    private final Codec<Object> codec;

    @SuppressWarnings("unchecked")
    public MapSerializer(FieldSerializer keySerializer, FieldSerializer valueSerializer) {
        Codec<Object> keyCodec = keySerializer.codec();
        Codec<Object> valueCodec = valueSerializer.codec();

        Codec<Entry> entryCodec = RecordCodecBuilder.create(instance ->
            instance.group(
                keyCodec.fieldOf("k").forGetter(Entry::key),
                valueCodec.fieldOf("v").forGetter(Entry::value)
            ).apply(instance, Entry::new)
        );

        Codec<Map<Object, Object>> mapCodec = entryCodec.listOf().xmap(
            list -> list.stream().collect(Collectors.toMap(
                Entry::key, Entry::value, (a, b) -> b, LinkedHashMap::new
            )),
            map -> map.entrySet().stream()
                      .map(e -> new Entry(e.getKey(), e.getValue()))
                      .collect(Collectors.toList())
        );

        this.codec = (Codec<Object>) (Codec<?>) mapCodec;
    }

    @Override
    public void encode(HolderLookup.Provider registries, CompoundTag out, String name, Object value) {
        if (value == null) return;
        out.put(name, codec.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), value).getOrThrow());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object decode(HolderLookup.Provider registries, CompoundTag in, String name) {
        Codec<Map<Object, Object>> mapCodec = (Codec<Map<Object, Object>>) (Codec<?>) codec;
        return in.contains(name, CompoundTag.TAG_LIST)
            ? mapCodec.parse(registries.createSerializationContext(NbtOps.INSTANCE), in.get(name))
                .result()
                .map(LinkedHashMap::new)
                .orElseGet(LinkedHashMap::new)
            : new LinkedHashMap<>();
    }

    @Override
    public Codec<Object> codec() {
        return codec;
    }
}
