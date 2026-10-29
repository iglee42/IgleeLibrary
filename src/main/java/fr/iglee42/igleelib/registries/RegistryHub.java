package fr.iglee42.igleelib.registries;

import fr.iglee42.igleelib.registries.impl.BlockDeferredRegister;
import fr.iglee42.igleelib.registries.impl.BlockEntityDeferredRegister;
import fr.iglee42.igleelib.registries.impl.CreativeTabDeferredRegister;
import fr.iglee42.igleelib.registries.impl.DataComponentDeferredRegister;
import fr.iglee42.igleelib.registries.impl.EntityTypeDeferredRegister;
import fr.iglee42.igleelib.registries.impl.ItemDeferredRegister;
import fr.iglee42.igleelib.registries.impl.RecipeDeferredRegister;
import fr.iglee42.igleelib.registries.impl.SoundDeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Convenience factory that creates the registers of a mod and registers them all on the mod event bus at once.
 */
public class RegistryHub {

	private final String modid;
	private final List<Consumer<IEventBus>> registers = new ArrayList<>();

	public RegistryHub(String modid) {
		this.modid = modid;
	}

	public String getModid() {
		return modid;
	}

	private ItemDeferredRegister items;
	private BlockDeferredRegister blocks;
	private BlockEntityDeferredRegister blockEntities;
	private EntityTypeDeferredRegister entities;
	private CreativeTabDeferredRegister creativeTabs;
	private RecipeDeferredRegister recipes;
	private DataComponentDeferredRegister dataComponents;
	private SoundDeferredRegister sounds;
	private final Map<ResourceKey<?>, ExtendedDeferredRegister<?>> customs = new HashMap<>();

	private <R> R track(R register, Consumer<IEventBus> registrar) {
		registers.add(registrar);
		return register;
	}

	private <R extends DeferredRegister<?>> R track(R register) {
		return track(register, register::register);
	}

	private <R extends BiDeferredRegister<?, ?>> R trackBi(R register) {
		return track(register, register::register);
	}

	public ItemDeferredRegister items() {
		if (items == null) items = track(new ItemDeferredRegister(modid));
		return items;
	}

	public BlockDeferredRegister blocks() {
		if (blocks == null) blocks = trackBi(new BlockDeferredRegister(modid));
		return blocks;
	}

	public BlockEntityDeferredRegister blockEntities() {
		if (blockEntities == null) blockEntities = track(new BlockEntityDeferredRegister(modid));
		return blockEntities;
	}

	public EntityTypeDeferredRegister entities() {
		if (entities == null) entities = track(new EntityTypeDeferredRegister(modid));
		return entities;
	}

	public CreativeTabDeferredRegister creativeTabs() {
		if (creativeTabs == null) creativeTabs = track(new CreativeTabDeferredRegister(modid));
		return creativeTabs;
	}

	public RecipeDeferredRegister recipes() {
		if (recipes == null) recipes = trackBi(new RecipeDeferredRegister(modid));
		return recipes;
	}

	public DataComponentDeferredRegister dataComponents() {
		if (dataComponents == null) dataComponents = track(new DataComponentDeferredRegister(modid));
		return dataComponents;
	}

	public SoundDeferredRegister sounds() {
		if (sounds == null) sounds = track(new SoundDeferredRegister(modid));
		return sounds;
	}

	@SuppressWarnings("unchecked")
	public <T> ExtendedDeferredRegister<T> custom(ResourceKey<? extends Registry<T>> key) {
		return (ExtendedDeferredRegister<T>) customs.computeIfAbsent(key, k -> track(new ExtendedDeferredRegister<>(key, modid)));
	}

	public void register(IEventBus bus) {
		registers.forEach(r -> r.accept(bus));
	}
}
