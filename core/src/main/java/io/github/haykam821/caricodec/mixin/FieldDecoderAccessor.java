package io.github.haykam821.caricodec.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.serialization.Decoder;
import com.mojang.serialization.codecs.FieldDecoder;

@Mixin(value = FieldDecoder.class, remap = false)
public interface FieldDecoderAccessor {
	@Accessor("name")
	public String getName();

	@Accessor("elementCodec")
	public Decoder<?> getElementCodec();
}
