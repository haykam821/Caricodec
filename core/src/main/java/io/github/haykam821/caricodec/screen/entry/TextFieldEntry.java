package io.github.haykam821.caricodec.screen.entry;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.WidgetColors;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;

public abstract class TextFieldEntry<V> extends WidgetFieldEntry<TextFieldWidget, V> {
	private boolean valid = true;

	public TextFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<V> field, String id) {
		super(client, screen, field, id);
	}

	public abstract V parse(String text);

	private V parseChecked(String text) {
		try {
			V value = this.parse(text);
			this.valid = true;
			return value;
		} catch (Exception exception) {
			this.valid = false;
			return null;
		}
	}

	public String serialize() {
		return this.value.toString();
	}

	@Override
	protected TextFieldWidget createWidget() {
		TextFieldWidget widget = new TextFieldWidget(this.client.textRenderer, 0, 0, WidgetSizes.FULL_WIDTH - 2, WidgetSizes.HEIGHT, ScreenTexts.EMPTY);

		widget.setChangedListener(this::updateText);
		widget.setText(this.serialize());

		return widget;
	}

	@Override
	protected int getWidgetX(int entryX) {
		return super.getWidgetX(entryX) + 1;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

		if (!this.valid) {
			int color = this.widget.isFocused() ? WidgetColors.FOCUSED_INVALID_OUTLINE : WidgetColors.INVALID_OUTLINE;
			TextFieldEntry.drawOutline(matrices, this.widget, color);
		}
	}

	public void updateText(String text) {
		V value = this.parseChecked(text);

		if (this.valid) {
			this.screen.stageValue(field, value);
		}
	}

	protected static void drawOutline(MatrixStack matrices, TextFieldWidget widget, int color) {
		TextFieldEntry.drawOutline(matrices, widget.x - 1, widget.y - 1, widget.x + widget.getWidth() + 1, widget.y + widget.getHeight() + 1, color);
	}

	private static void drawOutline(MatrixStack matrices, int x1, int y1, int x2, int y2, int color) {
		DrawableHelper.fill(matrices, x1, y1, x1 + 1, y2, color);
		DrawableHelper.fill(matrices, x1, y1, x2, y1 + 1, color);
		DrawableHelper.fill(matrices, x2, y1, x2 - 1, y2, color);
		DrawableHelper.fill(matrices, x1, y2, x2, y2 - 1, color);
	}
}
