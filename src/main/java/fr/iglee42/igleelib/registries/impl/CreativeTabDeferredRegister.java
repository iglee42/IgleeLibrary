package fr.iglee42.igleelib.registries.impl;

import fr.iglee42.igleelib.item.CreativeTabContributor;
import fr.iglee42.igleelib.registries.ExtendedDeferredHolder;
import fr.iglee42.igleelib.registries.ExtendedDeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CreativeTabDeferredRegister extends ExtendedDeferredRegister<CreativeModeTab> {

	private final Consumer<BuildCreativeModeTabContentsEvent> addToExistingTabs;

	public CreativeTabDeferredRegister(String modid) {
		this(modid, event -> {});
	}

	public CreativeTabDeferredRegister(String modid, Consumer<BuildCreativeModeTabContentsEvent> addToExistingTabs) {
		super(Registries.CREATIVE_MODE_TAB, modid);
		this.addToExistingTabs = addToExistingTabs;
	}

	@Override
	public void register(@NotNull IEventBus bus) {
		super.register(bus);
		bus.addListener(addToExistingTabs);
	}

	public ExtendedDeferredHolder<CreativeModeTab, CreativeModeTab> registerMain(Component title, Holder<Item> icon, UnaryOperator<CreativeModeTab.Builder> operator) {
		return register(getNamespace(), title, icon, operator);
	}

	public ExtendedDeferredHolder<CreativeModeTab, CreativeModeTab> register(String name, Component title, Holder<Item> icon, UnaryOperator<CreativeModeTab.Builder> operator) {
		return register(name, () -> operator.apply(CreativeModeTab.builder()
				.title(title)
				.icon(() -> new ItemStack(icon))).build());
	}

	public static void addToDisplay(CreativeModeTab.Output output, Collection<? extends Holder<Item>> items, Predicate<Holder<Item>> shouldSkip) {
		for (Holder<Item> item : items) {
			if (!shouldSkip.test(item)) {
				addToDisplay(output, item);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void addToDisplay(CreativeModeTab.Output output, BlockDeferredHolder<?, ?>... blocks) {
		addToDisplay(output, Arrays.stream(blocks).map(BlockDeferredHolder::getItemHolder).toArray(Holder[]::new));
	}

	@SafeVarargs
	public static void addToDisplay(CreativeModeTab.Output output, Holder<Item>... items) {
		CreativeModeTab.TabVisibility visibility = output instanceof BuildCreativeModeTabContentsEvent
				? CreativeModeTab.TabVisibility.PARENT_TAB_ONLY
				: CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
		for (Holder<Item> item : items) {
			Item value = item.value();
			if (value instanceof CreativeTabContributor contributor) {
				contributor.addToTab(stack -> output.accept(stack, visibility));
			} else {
				output.accept(value, visibility);
			}
		}
	}

	public static void addToDisplay(ItemDeferredRegister register, CreativeModeTab.Output output) {
		addToDisplay(output, register.getEntries(), it -> false);
	}

	public static void addToDisplay(BlockDeferredRegister register, CreativeModeTab.Output output) {
		addToDisplay(output, register.getSecondaryEntries(), it -> false);
	}
}
