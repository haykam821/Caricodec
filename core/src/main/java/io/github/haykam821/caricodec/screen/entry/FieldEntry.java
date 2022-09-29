package io.github.haykam821.caricodec.screen.entry;

import java.util.List;

import com.google.common.collect.ImmutableList;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.VariableHeightListWidget;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

public class FieldEntry<V> extends VariableHeightListWidget.Entry<FieldEntry<?>> implements Comparable<FieldEntry<?>> {
	protected final MinecraftClient client;
	protected final CaricodecConfigScreen<?> screen;
	protected final FieldIndex<V> field;

	private final Text name;
	private final MultilineText description;

	public FieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<V> field, String id) {
		this.client = client;
		this.screen = screen;
		this.field = field;

		this.name = field.getPath().asText(id).formatted(Formatting.BOLD);
		this.description = FieldEntry.getDescription(client, field, id);
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		client.textRenderer.draw(matrices, this.name, x, y, 0xFFFFFF);

		if (this.description != null) {
			int lineHeight = this.client.textRenderer.fontHeight;
			int descriptionY = y + lineHeight + WidgetSizes.SPACING;

			this.description.draw(matrices, x, descriptionY, lineHeight, 0xFFFFFF);
		}
	}

	protected int getWidgetX(int entryX) {
		return entryX + WidgetSizes.SPACING;
	}

	protected int getWidgetY(int entryY) {
		int fontHeight = this.client.textRenderer.fontHeight;
		int y = entryY + fontHeight + WidgetSizes.SPACING;

		if (this.description != null) {
			y += WidgetSizes.SPACING + fontHeight * this.description.count();
		}

		return y;
	}

	@Override
	public int getHeight() {
		return this.getWidgetY(0) + WidgetSizes.HEIGHT + WidgetSizes.FIELD_SPACING;
	}

	@Override
	public List<? extends Element> children() {
		return ImmutableList.of();
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return ImmutableList.of();
	}

	@Override
	public int compareTo(FieldEntry<?> other) {
		return this.field.getPath().compareTo(other.field.getPath());
	}

	private static MultilineText getDescription(MinecraftClient client, FieldIndex<?> field, String id) {
		Text fullDescription = field.getPath().getDescription(id);

		if (Texts.hasTranslation(fullDescription)) {
			return MultilineText.create(client.textRenderer, fullDescription, WidgetSizes.ROW_WIDTH);
		} else {
			return null;
		}
	}
}