package fr.iglee42.igleelib.registries.impl;

import com.google.common.base.Preconditions;

import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import fr.iglee42.igleelib.registries.impl.BlockEntityDeferredHolder.ClientTicker;
import fr.iglee42.igleelib.registries.impl.BlockEntityDeferredHolder.ServerTicker;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

import net.neoforged.neoforge.registries.DeferredHolder;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockEntityDeferredRegister extends ExtendedDeferredRegister<BlockEntityType<?>> {
	public BlockEntityDeferredRegister(String namespace) {
		super(Registries.BLOCK_ENTITY_TYPE, namespace, BlockEntityDeferredHolder::new);
	}

	public <BE extends BlockEntity> BlockEntityTypeBuilder<BE> builder(DeferredHolder<Block, ?> block, BlockEntitySupplier<? extends BE> factory) {
		return new BlockEntityTypeBuilder<>(block, factory);
	}

	@SuppressWarnings("unchecked")
	private <BE extends BlockEntity> BlockEntityDeferredHolder<BE> registerBE(String name, Supplier<? extends BlockEntityType<BE>> sup) {
		return (BlockEntityDeferredHolder<BE>) super.register(name, sup);
	}

	public class BlockEntityTypeBuilder<BE extends BlockEntity> {

		private final DeferredHolder<Block, ?> block;
		private final BlockEntitySupplier<? extends BE> factory;
		@Nullable
		private ClientTicker<BE> clientTicker;
		@Nullable
		private ServerTicker<BE> serverTicker;

		BlockEntityTypeBuilder(DeferredHolder<Block, ?> block, BlockEntitySupplier<? extends BE> factory) {
			this.block = block;
			this.factory = factory;
		}

		public BlockEntityTypeBuilder<BE> clientTicker(ClientTicker<BE> ticker) {
			Preconditions.checkState(clientTicker == null, "Client ticker may only be set once.");
			clientTicker = ticker;
			return this;
		}

		public BlockEntityTypeBuilder<BE> serverTicker(ServerTicker<BE> ticker) {
			Preconditions.checkState(serverTicker == null, "Server ticker may only be set once.");
			serverTicker = ticker;
			return this;
		}

		public BlockEntityDeferredHolder<BE> build() {
			BlockEntityDeferredHolder<BE> holder = registerBE(block.getId().getPath(),
					() -> BlockEntityType.Builder.<BE>of(factory::create, block.value()).build(null));
			holder.tickers(serverTicker, clientTicker);
			return holder;
		}
	}
}
