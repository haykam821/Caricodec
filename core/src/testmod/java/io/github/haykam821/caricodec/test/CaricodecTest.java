package io.github.haykam821.caricodec.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.haykam821.caricodec.api.CaricodecConfigHolder;
import io.github.haykam821.caricodec.index.CodecIndexer;
import io.github.haykam821.caricodec.test.config.CaricodecTestConfig;
import net.fabricmc.api.ModInitializer;

public class CaricodecTest implements ModInitializer {
	protected static final Logger LOGGER = LoggerFactory.getLogger("Caricodec Core Test Mod");

	@Override
	public void onInitialize() {
		CaricodecConfigHolder<CaricodecTestConfig> config = CaricodecTestConfig.HOLDER;

		LOGGER.info("Loaded test configuration: {}", config.getConfig());
		LOGGER.info("Indexed test codec: {}", CodecIndexer.index(CaricodecTestConfig.CODEC));
	}
}
