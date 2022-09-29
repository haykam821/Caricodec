package io.github.haykam821.caricodec.screen.entry;

import java.util.List;

import com.google.common.collect.ImmutableList;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

public abstract class WidgetFieldEntry<W extends ClickableWidget, V> extends FieldEntry<V> {
	protected V value;
	protected final W widget;

	public WidgetFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<V> field, String id) {
		super(client, screen, field, id);

		this.value = screen.getOriginalValue(field);
		this.widget = this.createWidget();
	}

	protected abstract W createWidget();

	protected int getX(int x, int entryWidth) {
		return WidgetSizes.SPACING + x;
	}

	protected int getY(int y, int entryHeight) {
		return y + this.client.textRenderer.fontHeight + WidgetSizes.SPACING;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

		this.widget.x = this.getX(x, entryWidth);
		this.widget.y = this.getY(y, entryHeight);

		this.widget.render(matrices, mouseX, mouseY, tickDelta);
	}

	@Override
	public List<? extends Element> children() {
		return ImmutableList.of(this.widget);
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return ImmutableList.of(this.widget);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.widget.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.widget.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.widget.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return this.widget.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		return this.widget.charTyped(chr, modifiers);
	}
}
