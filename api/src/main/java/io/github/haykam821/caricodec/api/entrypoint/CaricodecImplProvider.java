package io.github.haykam821.caricodec.api.entrypoint;

import io.github.haykam821.caricodec.api.Caricodec;
import io.github.haykam821.caricodec.api.CaricodecApi;

/**
 * An interface for an entrypoint used to supply an implementation of the Caricodec API.
 */
public interface CaricodecImplProvider {
	/**
	 * The key used for the Caricodec implementation provider entrypoint.
	 */
	public static final String KEY = Caricodec.MOD_ID + ":implementation";

	/**
	 * {@return the implementation of the Caricodec API}
	 */
	public CaricodecApi getImplementation();
}
