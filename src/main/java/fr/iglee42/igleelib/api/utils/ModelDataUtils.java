package fr.iglee42.igleelib.api.utils;

import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Optional;

public class ModelDataUtils {
    public static <T> Optional<T> getData(ModelData data, ModelProperty<T> property) {
        if(!data.has(property)) {
            return Optional.empty();
        }
        return Optional.ofNullable(data.get(property));
    }
}