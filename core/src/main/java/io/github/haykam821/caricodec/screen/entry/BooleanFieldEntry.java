package io.github.haykam821.caricodec.screen.entry;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class BooleanFieldEntry extends WidgetFieldEntry<ButtonWidget, Boolean> {
	public BooleanFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<Boolean> field, String id) {
		super(client, screen, field, id);
	}

	@Override
	protected ButtonWidget createWidget() {
		return ButtonWidget.builder(this.getMessage(), this::onClick)
			.position(0, 0)
			.size(WidgetSizes.FULL_WIDTH, WidgetSizes.HEIGHT)
			.build();
	}

	public void onClick(ButtonWidget button) {
		this.value = !this.value;
		this.widget.setMessage(this.getMessage());

		this.screen.stageValue(field, this.value);
	}

	private Text getMessage() {
		return this.value ? ScreenTexts.ON : ScreenTexts.OFF;
	}
}
