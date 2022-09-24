package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.MapDecoder;

@Mixin(targets = "com.mojang.serialization.MapCodec$1", remap = false)
public interface FieldAccessor {
	@Accessor("val$decoder")
	public MapDecoder<?> getDecoder();
}
