package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.MapDecoder;

@Mixin(targets = "com.mojang.serialization.MapDecoder$4", remap = false)
public interface MappedDecoderAccessor {
	@Accessor("this$0")
	public MapDecoder<?> getParent();
}
