package fr.iglee42.igleelib.blockentities.storage;

import com.mojang.serialization.Codec;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.StringRepresentable;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.IntFunction;

public enum RelativeSide implements StringRepresentable {
	FRONT,
	BACK,
	LEFT,
	RIGHT,
	TOP,
	BOTTOM;
	;

	public static final Codec<RelativeSide> CODEC = StringRepresentable.fromEnum(RelativeSide::values);

	public static final IntFunction<RelativeSide> BY_ID = ByIdMap.continuous(RelativeSide::ordinal, values(), OutOfBoundsStrategy.WRAP);

	public static final StreamCodec<ByteBuf, RelativeSide> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, RelativeSide::ordinal);

	private final String serializedName;

	RelativeSide() {
		this.serializedName = name().toLowerCase(Locale.ROOT);
	}

	public Direction getDirection(Direction facing) {
		return switch (this) {
			case FRONT -> facing;
			case BACK -> facing.getOpposite();
			case LEFT -> facing == Direction.DOWN || facing == Direction.UP ? Direction.EAST : facing.getClockWise();
			case RIGHT -> facing == Direction.DOWN || facing == Direction.UP ? Direction.WEST : facing.getCounterClockWise();
			case TOP -> switch (facing) {
				case DOWN -> Direction.NORTH;
				case UP -> Direction.SOUTH;
				default -> Direction.UP;
			};
			case BOTTOM -> switch (facing) {
				case DOWN -> Direction.SOUTH;
				case UP -> Direction.NORTH;
				default -> Direction.DOWN;
			};
		};
	}

	public static RelativeSide fromDirections(Direction facing, Direction side) {
		if (side == facing) {
			return FRONT;
		} else if (side == facing.getOpposite()) {
			return BACK;
		} else if (facing == Direction.DOWN || facing == Direction.UP) {
			return switch (side) {
				case NORTH -> facing == Direction.DOWN ? TOP : BOTTOM;
				case SOUTH -> facing == Direction.DOWN ? BOTTOM : TOP;
				case WEST -> RIGHT;
				case EAST -> LEFT;
				default -> throw new IllegalStateException("Case should have been caught earlier, facing " + facing + ", side " + side + ".");
			};
		} else if (side == Direction.DOWN) {
			return BOTTOM;
		} else if (side == Direction.UP) {
			return TOP;
		} else if (side == facing.getCounterClockWise()) {
			return RIGHT;
		} else if (side == facing.getClockWise()) {
			return LEFT;
		}
		//Fall back to front, should never get here
		return FRONT;
	}

	@Override
	public @NotNull String getSerializedName() {
		return serializedName;
	}
}
