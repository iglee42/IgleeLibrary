package fr.iglee42.igleelib.registries;

import fr.iglee42.igleelib.registries.impl.BlockDeferredRegister;
import fr.iglee42.igleelib.registries.impl.BlockEntityDeferredRegister;
import fr.iglee42.igleelib.registries.impl.CreativeTabDeferredRegister;
import fr.iglee42.igleelib.registries.impl.DataComponentDeferredRegister;
import fr.iglee42.igleelib.registries.impl.EntityTypeDeferredRegister;
import fr.iglee42.igleelib.registries.impl.ItemDeferredRegister;
import fr.iglee42.igleelib.registries.impl.MenuTypeDeferredRegister;
import fr.iglee42.igleelib.registries.impl.PacketDeferredRegister;
import fr.iglee42.igleelib.registries.impl.RecipeDeferredRegister;
import fr.iglee42.igleelib.registries.impl.SoundDeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
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
	private MenuTypeDeferredRegister menuTypes;
	private PacketDeferredRegister packets;
	private final Map<ResourceKey<?>, ExtendedDeferredRegister<?>> customs = new HashMap<>();

	protected final <R> R track(R register, Consumer<IEventBus> registrar) {
		registers.add(registrar);
		return register;
	}

	protected final <R extends DeferredRegister<?>> R track(R register) {
		return track(register, register::register);
	}

	protected final <R extends BiDeferredRegister<?, ?>> R trackBi(R register) {
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

	public CreativeTabDeferredRegister creativeTabs(Consumer<BuildCreativeModeTabContentsEvent> addToExistingTabs) {
		if (creativeTabs == null) creativeTabs = track(new CreativeTabDeferredRegister(modid, addToExistingTabs));
		return creativeTabs;
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

	public MenuTypeDeferredRegister menuTypes() {
		if (menuTypes == null) menuTypes = track(new MenuTypeDeferredRegister(modid));
		return menuTypes;
	}

	/**
	 * Returns the payload register using the given network protocol version.
	 * All calls for this hub must use the same version.
	 */
	public PacketDeferredRegister packets(String version) {
		if (packets == null) {
			PacketDeferredRegister register = new PacketDeferredRegister(modid, version);
			packets = track(register, register::register);
		} else if (!packets.version().equals(version)) {
			throw new IllegalArgumentException("Packet register already configured with version " + packets.version());
		}
		return packets;
	}

	@SuppressWarnings("unchecked")
	public <T> ExtendedDeferredRegister<T> custom(ResourceKey<? extends Registry<T>> key) {
		return (ExtendedDeferredRegister<T>) customs.computeIfAbsent(key, k -> track(new ExtendedDeferredRegister<>(key, modid)));
	}

	public void register(IEventBus bus) {
		registers.forEach(r -> r.accept(bus));
	}
}
