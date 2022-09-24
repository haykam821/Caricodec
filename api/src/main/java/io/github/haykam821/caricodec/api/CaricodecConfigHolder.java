package io.github.haykam821.caricodec.api;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import net.minecraft.client.gui.screen.Screen;

public interface CaricodecConfigHolder<T> {
	public T getConfig();

	/**
	 * Sets the configuration and attempts save operations.
	 * @return whether the configuration was successfully saved
	 */
	public boolean setConfig(T config);

	/**
	 * {@return the codec used to serialize and deserialize configurations}
	 */
	public Codec<T> getCodec();

	/**
	 * {@return the configuration used if a user-supplied configuration is not available}
	 */
	@Nullable
	public default T getDefaultConfig() {
		return null;
	}

	/**
	 * {@return the ID of this configuration}
	 */
	public String getId();

	public Screen createScreen(Screen parent);
}
