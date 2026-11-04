package fr.iglee42.igleelib.blockentities.storage;

import com.mojang.serialization.Codec;

import fr.iglee42.igleelib.IgleeLibrary;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.StringRepresentable;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.IntFunction;

/** Describes what an external block can do with a storage module on a given side. */
public enum IOMode implements StringRepresentable {
    NONE(false, false),
    INPUT(true, false),
    OUTPUT(false, true),
    BOTH(true, true);

	public static final Codec<IOMode> CODEC = StringRepresentable.fromEnum(IOMode::values);

	public static final IntFunction<IOMode> BY_ID = ByIdMap.continuous(IOMode::ordinal, values(), OutOfBoundsStrategy.WRAP);

	public static final StreamCodec<ByteBuf, IOMode> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, IOMode::ordinal);


	private final boolean input;
    private final boolean output;
	private final String serializedName;

    IOMode(boolean input, boolean output) {
        this.input = input;
        this.output = output;
		this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    public static IOMode of(boolean input, boolean output) {
        return input ? (output ? BOTH : INPUT) : (output ? OUTPUT : NONE);
    }

    public boolean canInput() {
        return input;
    }

    public boolean canOutput() {
        return output;
    }

	@Override
	public @NotNull String getSerializedName() {
		return serializedName;
	}

	public Component translate(){
		return Component.translatable("io_mode."+ IgleeLibrary.MODID + "." + serializedName);
	}
}
