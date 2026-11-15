package fr.iglee42.igleelib.blockentities.storage.slot;

import net.minecraft.resources.ResourceLocation;

public class SlotOverlay {

	private final ResourceLocation texture;
	private final int width;
	private final int height;

	public SlotOverlay(ResourceLocation texture, int width, int height) {
		this.texture = texture;
		this.width = width;
		this.height = height;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
