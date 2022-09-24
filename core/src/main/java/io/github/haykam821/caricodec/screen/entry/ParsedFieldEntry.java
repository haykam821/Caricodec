package io.github.haykam821.caricodec.screen.entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;

import io.github.haykam821.caricodec.CaricodecCoreImpl;
import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import net.minecraft.client.MinecraftClient;

public class ParsedFieldEntry<T> extends TextFieldEntry<T> {
	public ParsedFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<T> field, String id) {
		super(client, screen, field, id);
	}

	@Override
	public T parse(String text) {
		try {
			JsonElement element = JsonParser.parseString(text);

			DataResult<Pair<T, JsonElement>> result = field.getCodec().decode(CaricodecCoreImpl.OPS, element);
			return result.get().left().get().getFirst();
		} catch (Exception exception) {
			return null;
		}
	}

	@Override
	public String serialize() {
		try {
			DataResult<JsonElement> result = field.getCodec().encodeStart(CaricodecCoreImpl.OPS, this.value);
			JsonElement element = result.get().left().get();

			return element.toString();
		} catch (Exception exception) {
			return "";
		}
	}
}
