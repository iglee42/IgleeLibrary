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
import java.util.List;
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

	private <R extends DeferredRegister<?>> R track(R register) {
		registers.add(register::register);
		return register;
	}

	public ItemDeferredRegister items() {
		return track(new ItemDeferredRegister(modid));
	}

	public BlockDeferredRegister blocks() {
		BlockDeferredRegister register = new BlockDeferredRegister(modid);
		registers.add(register::register);
		return register;
	}

	public BlockEntityDeferredRegister blockEntities() {
		return track(new BlockEntityDeferredRegister(modid));
	}

	public EntityTypeDeferredRegister entities() {
		EntityTypeDeferredRegister register = new EntityTypeDeferredRegister(modid);
		registers.add(register::register);
		return register;
	}

	public CreativeTabDeferredRegister creativeTabs() {
		CreativeTabDeferredRegister register = new CreativeTabDeferredRegister(modid);
		registers.add(register::register);
		return register;
	}

	public RecipeDeferredRegister recipes() {
		RecipeDeferredRegister register = new RecipeDeferredRegister(modid);
		registers.add(register::register);
		return register;
	}

	public DataComponentDeferredRegister dataComponents() {
		return track(new DataComponentDeferredRegister(modid));
	}

	public SoundDeferredRegister sounds() {
		return track(new SoundDeferredRegister(modid));
	}

	public <T> ExtendedDeferredRegister<T> custom(ResourceKey<? extends Registry<T>> key) {
		return track(new ExtendedDeferredRegister<>(key, modid));
	}

	public void register(IEventBus bus) {
		registers.forEach(r -> r.accept(bus));
	}
}
