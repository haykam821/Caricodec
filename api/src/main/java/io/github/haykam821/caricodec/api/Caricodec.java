package io.github.haykam821.caricodec.api;

import io.github.haykam821.caricodec.api.entrypoint.CaricodecImplProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

/**
 * The accessor for the Caricodec API.
 */
public final class Caricodec {
	/**
	 * The Caricodec mod ID.
	 */
	public static final String MOD_ID = "caricodec";

	private static CaricodecApi api;

	private Caricodec() {
		return;
	}

	/**
	 * {@return the Caricodec API}
	 */
	public static CaricodecApi getApi() {
		if (Caricodec.api == null) {
			FabricLoader loader = FabricLoader.getInstance();

			for (EntrypointContainer<CaricodecImplProvider> container : loader.getEntrypointContainers(CaricodecImplProvider.KEY, CaricodecImplProvider.class)) {
				if (Caricodec.api != null) {
					String id = container.getProvider().getMetadata().getId();
					throw new RuntimeException("The mod '" + id + "' is providing a conflicting Caricodec API implementation!");
				}

				Caricodec.api = container.getEntrypoint().getImplementation();
			}

			if (Caricodec.api == null) {
				throw new RuntimeException("No Caricodec API implementation was provided!");
			}
		}

		return Caricodec.api;
	}
}
