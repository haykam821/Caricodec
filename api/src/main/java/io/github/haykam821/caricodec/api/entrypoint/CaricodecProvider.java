package io.github.haykam821.caricodec.api.entrypoint;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;

import io.github.haykam821.caricodec.api.Caricodec;

/**
 * An interface for Caricodec configuration definitions, accessible through the {@value KEY} entrypoint.
 */
public interface CaricodecProvider<T> {
	/**
	 * The key used for the Caricodec provider entrypoint.
	 */
	public static final String KEY = Caricodec.MOD_ID + ":provider";

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
	 * 
	 * <p>If the returned ID is null (as in the default implementation), the mod ID is used as a default.
	 * Overriding this ID is desirable in the case of mods that are internally distributed across multiple mod containers.
	 * 
	 * <p>Returned IDs are expected to be unique between providers.
	 * Behavior in the case of an ID collision is undefined.
	 */
	@Nullable
	public default String getId() {
		return null;
	}
}
