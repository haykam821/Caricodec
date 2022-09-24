package io.github.haykam821.caricodec;

import io.github.haykam821.caricodec.api.CaricodecApi;
import io.github.haykam821.caricodec.api.entrypoint.CaricodecImplProvider;

public class CaricodecCoreImplProvider implements CaricodecImplProvider {
	@Override
	public CaricodecApi getImplementation() {
		return CaricodecCoreImpl.INSTANCE;
	}
}
