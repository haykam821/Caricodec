package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.codecs.RecordCodecBuilder;

@Mixin(targets = "com.mojang.serialization.codecs.RecordCodecBuilder$Instance$1", remap = false)
public interface LiftDecoderAccessor {
	@Accessor("val$a")
	public RecordCodecBuilder<?, ?> getInner();
}
