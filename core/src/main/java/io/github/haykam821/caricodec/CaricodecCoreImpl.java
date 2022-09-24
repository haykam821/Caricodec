package io.github.haykam821.caricodec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;

import io.github.haykam821.caricodec.api.CaricodecApi;
import io.github.haykam821.caricodec.api.CaricodecConfigHolder;
import io.github.haykam821.caricodec.api.entrypoint.CaricodecProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class CaricodecCoreImpl implements CaricodecApi {
	private static final Logger LOGGER = LoggerFactory.getLogger("Caricodec Core");
	private static final FabricLoader LOADER = FabricLoader.getInstance();

	public static final DynamicOps<JsonElement> OPS = JsonOps.INSTANCE;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	protected static final CaricodecCoreImpl INSTANCE = new CaricodecCoreImpl();

	private final Map<String, CaricodecConfigHolder<?>> holders = CaricodecCoreImpl.loadConfigs();

	private CaricodecCoreImpl() {
		return;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> CaricodecConfigHolder<T> get(String id) {
		return (CaricodecConfigHolder<T>) this.holders.get(id);
	}

	private static Map<String, CaricodecConfigHolder<?>> loadConfigs() {
		Map<String, CaricodecConfigHolder<?>> holders = new HashMap<>();

		for (EntrypointContainer<CaricodecProvider<?>> container : CaricodecCoreImpl.getEntrypoints()) {
			CaricodecProvider<?> provider = container.getEntrypoint();
			String id = provider.getId();

			// Provide ID fallback
			if (id == null) {
				id = container.getProvider().getMetadata().getId();
			}

			CaricodecConfigHolder<?> holder = CaricodecCoreImpl.loadConfig(provider, id);
			holders.put(id, holder);
		}

		return holders;
	}

	@SuppressWarnings("unchecked")
	private static Iterable<EntrypointContainer<CaricodecProvider<?>>> getEntrypoints() {
		return (Iterable<EntrypointContainer<CaricodecProvider<?>>>) (Object) LOADER.getEntrypointContainers(CaricodecProvider.KEY, CaricodecProvider.class);
	}

	public static File getFile(String id) {
		Path configDir = FabricLoader.getInstance().getConfigDir();
		return new File(configDir.toFile(), id + ".json");
	}

	private static <T> CaricodecConfigHolder<T> loadConfig(CaricodecProvider<T> provider, String id) {
		File file = CaricodecCoreImpl.getFile(id);

		Codec<T> codec = provider.getCodec();
		T defaultConfig = provider.getDefaultConfig();

		// No default config fallback
		if (defaultConfig == null) {
			try (Reader reader = new BufferedReader(new FileReader(file))) {
				T config = decodeConfig(reader, codec);
				return new CaricodecConfigHolderImpl<>(config, codec, id);
			} catch (Exception exception) {
				throw new RuntimeException("Failed to read configuration for '" + id + "'; no fallback is available", exception);
			}
		}

		// Has default config fallback
		try (Reader reader = new BufferedReader(new FileReader(file))) {
			T config = decodeConfig(reader, codec);
			return new CaricodecConfigHolderImpl<>(config, codec, id);
		} catch (FileNotFoundException exception) {
			writeConfig(file, codec, defaultConfig, id, true);
			return new CaricodecConfigHolderImpl<>(defaultConfig, codec, id);
		} catch (Exception exception) {
			LOGGER.warn("Failed to read configuration for '{}'; falling back to default", id, exception);
			return new CaricodecConfigHolderImpl<>(defaultConfig, codec, id);
		}
	}

	private static <T> T decodeConfig(Reader reader, Codec<T> codec) {
		JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
		DataResult<Pair<T, JsonElement>> result = codec.decode(OPS, json);

		return result.getOrThrow(false, error -> {}).getFirst();
	}

	public static <T> boolean writeConfig(File file, Codec<T> codec, T config, String id, boolean wroteDefault) {
		try (Writer writer = new BufferedWriter(new FileWriter(file))) {
			DataResult<JsonElement> result = codec.encodeStart(OPS, config);
			GSON.toJson(result.get().left().get(), writer);

			if (wroteDefault) {
				LOGGER.warn("Could not find configuration for '{}'; wrote default to file", id);
			} else {
				LOGGER.debug("Wrote configuration for '{}' to file", id);
			}

			return true;
		} catch (Exception writeException) {
			if (wroteDefault) {
				LOGGER.warn("Could not find configuration for '{}'; failed to write default to file", id, writeException);
			} else {
				LOGGER.warn("Failed to write configuration for '{}' to file");
			}

			return false;
		}
	}
}
