package fr.iglee42.igleelib.mixins;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.SequencedMap;

@Mixin(value = MultiBufferSource.BufferSource.class,remap = false)
public interface AccessorMultiBufferSource {
	@Accessor("sharedBuffer")
	ByteBufferBuilder getFallbackBuffer();

	@Accessor("fixedBuffers")
	SequencedMap<RenderType, ByteBufferBuilder> getFixedBuffers();
}