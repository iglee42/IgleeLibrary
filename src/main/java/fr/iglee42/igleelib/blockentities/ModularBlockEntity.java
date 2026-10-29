package fr.iglee42.igleelib.blockentities;

import fr.iglee42.igleelib.blockentities.storage.StorageManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Block entity with modular storages. Add modules in the constructor:
 * <pre>
 * items = storage().add("items", new ItemStorageModule(9));
 * tank = storage().add("tank", new FluidStorageModule(8000));
 * energy = storage().add("energy", new EnergyStorageModule(100000));
 * </pre>
 * Then register the capabilities with {@link StorageManager#registerDefaults} (and
 * {@link StorageManager#register} for custom ones such as gas).
 */
public abstract class ModularBlockEntity extends ExtendedBlockEntity {

    public static final String STORAGE_TAG = "storage";

    private final StorageManager storage = new StorageManager(this::setChanged);

    public ModularBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public StorageManager storage() {
        return storage;
    }

    @Override
    protected void load(CompoundTag input, HolderLookup.Provider registries, boolean forClient) {
        super.load(input, registries, forClient);
        if (input.contains(STORAGE_TAG))
            storage.deserializeNBT(registries, input.getCompound(STORAGE_TAG));
    }

    @Override
    protected void save(CompoundTag output, HolderLookup.Provider registries, boolean forClient) {
        super.save(output, registries, forClient);
        output.put(STORAGE_TAG, storage.serializeNBT(registries));
    }

    @Override
    public void destroy() {
        if (level != null && !level.isClientSide) storage.onDestroyed(level, getBlockPos());
    }
}
