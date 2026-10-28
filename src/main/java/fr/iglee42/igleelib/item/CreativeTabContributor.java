package fr.iglee42.igleelib.item;

import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * Implemented by items that want to control which stacks are shown in a creative tab.
 */
public interface CreativeTabContributor {

    void addToTab(Consumer<ItemStack> adder);
}
