package io.github.haykam821.caricodec.api;

/**
 * The Caricodec API.
 */
public interface CaricodecApi {
	/**
	 * {@return a configuration holder}
	 * @param <T> the type of the configuration
	 * @param id the ID of the provider specified by {@link io.github.haykam821.caricodec.api.entrypoint.CaricodecProvider#getId}
	 * @param clazz the class of the configuration
	 */
	public <T> CaricodecConfigHolder<T> get(String id);
}
