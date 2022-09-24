package io.github.haykam821.caricodec.test;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import io.github.haykam821.caricodec.api.Caricodec;
import io.github.haykam821.caricodec.api.entrypoint.CaricodecProvider;
import io.github.haykam821.caricodec.test.config.CaricodecTestConfig;

public class CaricodecTestProvider implements CaricodecProvider<CaricodecTestConfig> {
	@Override
	public Codec<CaricodecTestConfig> getCodec() {
		return CaricodecTestConfig.CODEC;
	}

	@Override
	public CaricodecTestConfig getDefaultConfig() {
		return CaricodecTestConfig.DEFAULT;
	}

	@Override
	public @Nullable String getId() {
		return Caricodec.MOD_ID;
	}
}
