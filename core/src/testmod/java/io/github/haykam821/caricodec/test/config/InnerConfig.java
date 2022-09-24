package io.github.haykam821.caricodec.test.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record InnerConfig(
	boolean checked
) {
	protected static final InnerConfig DEFAULT = new InnerConfig(true);

	protected static final Codec<InnerConfig> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			Codec.BOOL.fieldOf("checked").forGetter(InnerConfig::checked)
		).apply(instance, InnerConfig::new);
	});
}
