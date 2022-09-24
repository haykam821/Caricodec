package io.github.haykam821.caricodec.screen.entry;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import net.minecraft.client.MinecraftClient;

public class StringFieldEntry extends TextFieldEntry<String> {
	public StringFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<String> field, String id) {
		super(client, screen, field, id);
	}

	@Override
	public String parse(String text) {
		return text;
	}
}
