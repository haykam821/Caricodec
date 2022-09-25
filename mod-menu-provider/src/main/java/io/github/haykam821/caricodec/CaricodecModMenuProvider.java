package io.github.haykam821.caricodec;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import io.github.haykam821.caricodec.api.Caricodec;
import io.github.haykam821.caricodec.api.CaricodecApi;
import io.github.haykam821.caricodec.api.CaricodecConfigHolder;

public class CaricodecModMenuProvider implements ModMenuApi {
	@Override
	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		CaricodecApi api = Caricodec.getApi();
		ImmutableMap.Builder<String, ConfigScreenFactory<?>> builder = ImmutableMap.builder();

		for (CaricodecConfigHolder<?> holder : api.getAllHolders()) {
			builder.put(holder.getId(), holder::createScreen);
		}

		return builder.build();
	}
}
