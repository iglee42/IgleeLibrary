package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.function.QuadConsumer;
import fr.iglee42.igleelib.function.QuadFunction;
import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

public class BlockEntityDeferredHolder<T extends BlockEntity> extends ExtendedDeferredHolder<BlockEntityType<?>, BlockEntityType<T>> {

	@Nullable
	private ClientTicker<T> clientTicker;
	@Nullable
	private ServerTicker<T> serverTicker;

	public BlockEntityDeferredHolder(ResourceKey<BlockEntityType<?>> key) {
		super(key);
	}

	@Internal
	void tickers(@Nullable ServerTicker<T> serverTicker, @Nullable ClientTicker<T> clientTicker) {
		this.serverTicker = serverTicker;
		this.clientTicker = clientTicker;
	}

	public @Nullable ServerTicker<T> getServerTicker() {
		return serverTicker;
	}

	public @Nullable ClientTicker<T> getClientTicker() {
		return clientTicker;
	}

	/**
	 * Returns a ticker usable in {@code Block#getTicker}, or null when no ticker is set for the given side or the type does not match.
	 */
	@SuppressWarnings("unchecked")
	public <E extends BlockEntity> @Nullable BlockEntityTicker<E> getTicker(Level level, BlockEntityType<E> type) {
		if (type != value()) {
			return null;
		}
		if (level.isClientSide()) {
			ClientTicker<T> ticker = clientTicker;
			return ticker == null ? null : (lvl, pos, state, be) -> ticker.accept((T) be, lvl, pos, state);
		}
		ServerTicker<T> ticker = serverTicker;
		return ticker == null ? null : (lvl, pos, state, be) -> ticker.apply((T) be, (ServerLevel) lvl, pos, state);
	}

	@FunctionalInterface
	public interface ServerTicker<T extends BlockEntity> extends QuadFunction<T, ServerLevel, BlockPos, BlockState, Boolean> { }

	@FunctionalInterface
	public interface ClientTicker<T extends BlockEntity> extends QuadConsumer<T, Level, BlockPos, BlockState> { }
}
