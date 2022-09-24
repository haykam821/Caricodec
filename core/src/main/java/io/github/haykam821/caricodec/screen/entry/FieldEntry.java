package io.github.haykam821.caricodec.screen.entry;

import java.util.List;

import com.google.common.collect.ImmutableList;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class FieldEntry<V> extends ElementListWidget.Entry<FieldEntry<V>> implements Comparable<FieldEntry<?>> {
	protected final MinecraftClient client;
	protected final CaricodecConfigScreen<?> screen;
	protected final FieldIndex<V> field;
	private final String id;

	public FieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<V> field, String id) {
		this.client = client;
		this.screen = screen;
		this.field = field;
		this.id = id;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		Text text = field.getPath().asText(id);
		client.textRenderer.draw(matrices, text, x, y + entryHeight - this.client.textRenderer.fontHeight - 1, 0xFFFFFF);
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
}