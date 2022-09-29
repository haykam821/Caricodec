package io.github.haykam821.caricodec.screen;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;

public final class WidgetSizes {
	public static final int WIDTH = ButtonWidget.field_39500;
	public static final int HEIGHT = ButtonWidget.field_39501;
	public static final int SPACING = 5;

	public static final int ROW_WIDTH = WIDTH * 2;
	public static final int FULL_WIDTH = ROW_WIDTH - SPACING * 2;

	public static final int THIRD_WIDTH = FULL_WIDTH / 3;
	public static final int HALF_WIDTH = FULL_WIDTH / 2;

	private WidgetSizes() {
		return;
	}

	public static int getEntryHeight(TextRenderer textRenderer) {
		return textRenderer.fontHeight + SPACING + HEIGHT + SPACING * 3;
	}
}
