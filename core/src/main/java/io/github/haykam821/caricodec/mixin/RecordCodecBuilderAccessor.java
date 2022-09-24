package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.codecs.RecordCodecBuilder;

@Mixin(value = RecordCodecBuilder.class, remap = false)
public interface RecordCodecBuilderAccessor {
	@Accessor("decoder")
	public MapDecoder<?> getDecoder();
}
