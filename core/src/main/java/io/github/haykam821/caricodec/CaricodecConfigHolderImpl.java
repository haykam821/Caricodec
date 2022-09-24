package io.github.haykam821.caricodec;

import java.io.File;

import com.mojang.serialization.Codec;

import io.github.haykam821.caricodec.api.CaricodecConfigHolder;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import net.minecraft.client.gui.screen.Screen;

public class CaricodecConfigHolderImpl<T> implements CaricodecConfigHolder<T> {
	private T config;

	private final Codec<T> codec;
	private final String id;

	public CaricodecConfigHolderImpl(T config, Codec<T> codec, String id) {
		this.config = config;
		this.codec = codec;
		this.id = id;
	}

	@Override
	public T getConfig() {
		return this.config;
	}

	@Override
	public boolean setConfig(T config) {
		this.config = config;

		File file = CaricodecCoreImpl.getFile(this.id);
		return CaricodecCoreImpl.writeConfig(file, this.codec, config, this.id, false);
	}

	@Override
	public Codec<T> getCodec() {
		return this.codec;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public Screen createScreen(Screen parent) {
		return new CaricodecConfigScreen<>(parent, this);
	}

	@Override
	public String toString() {
		return "CaricodecConfigHolderImpl[" + this.id + "]";
	}
}
