package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.codecs.RecordCodecBuilder;

@Mixin(targets = "com.mojang.serialization.codecs.RecordCodecBuilder$Instance$3", remap = false)
public interface Apply2DecoderAccessor {
	@Accessor("val$function")
	public RecordCodecBuilder<?, ?> getFunction();

	@Accessor("val$fa")
	public RecordCodecBuilder<?, ?> getFirst();

	@Accessor("val$fb")
	public RecordCodecBuilder<?, ?> getSecond();
}
