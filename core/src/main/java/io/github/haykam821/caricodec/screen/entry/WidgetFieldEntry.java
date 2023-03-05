package io.github.haykam821.caricodec.screen.entry;

import java.util.List;

import com.google.common.collect.ImmutableList;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
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

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

		this.widget.setX(this.getWidgetX(x));
		this.widget.setY(this.getWidgetY(y));

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
}
