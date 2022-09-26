package io.github.haykam821.caricodec.screen.entry;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.screen.ScreenTexts;

public abstract class TextFieldEntry<V> extends WidgetFieldEntry<TextFieldWidget, V> {
	public TextFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<V> field, String id) {
		super(client, screen, field, id);
	}

	public abstract V parse(String text);

	public String serialize() {
		return this.value.toString();
	}

	@Override
	protected TextFieldWidget createWidget() {
		TextFieldWidget widget = new TextFieldWidget(this.client.textRenderer, 0, 0, WidgetSizes.WIDTH - 2, WidgetSizes.HEIGHT, ScreenTexts.EMPTY);

		widget.setChangedListener(this::updateText);
		widget.setText(this.serialize());

		return widget;
	}

	@Override
	protected int getX(int x, int entryWidth) {
		return super.getX(x, entryWidth) + 1;
	}

	public void updateText(String text) {
		V value = this.parse(text);
		this.screen.stageValue(field, value);
	}
}
