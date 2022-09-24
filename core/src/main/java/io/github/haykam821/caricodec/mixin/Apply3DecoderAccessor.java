package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.codecs.RecordCodecBuilder;

@Mixin(targets = "com.mojang.serialization.codecs.RecordCodecBuilder$Instance$5", remap = false)
public interface Apply3DecoderAccessor {
	@Accessor("val$function")
	public RecordCodecBuilder<?, ?> getFunction();

	@Accessor("val$f1")
	public RecordCodecBuilder<?, ?> getFirst();

	@Accessor("val$f2")
	public RecordCodecBuilder<?, ?> getSecond();

	@Accessor("val$f3")
	public RecordCodecBuilder<?, ?> getThird();
}
