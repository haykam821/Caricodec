package io.github.haykam821.caricodec.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "com.mojang.serialization.Decoder$5", remap = false)
public interface UnitDecoderAccessor {
	@Accessor("val$instance")
	public Supplier<?> getSupplier();
}
