package io.github.haykam821.caricodec.screen;

import io.github.haykam821.caricodec.screen.VariableHeightListWidget.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.math.MathHelper;

public abstract class VariableHeightListWidget<E extends Entry<E>> extends ElementListWidget<E> {
	public VariableHeightListWidget(MinecraftClient client, int width, int height, int top, int bottom) {
		super(client, width, height, top, bottom, 25);
	}

	@Override
	protected E getEntryAtPosition(double x, double y) {
		int centerX = this.left + this.width / 2;
		int halfWidth = this.getRowWidth() / 2;

		if (x >= this.getScrollbarPositionX()) return null;
		if (x < centerX - halfWidth) return null;
		if (x > centerX + halfWidth) return null;

		int relativeY = MathHelper.floor(y - this.top) - this.headerHeight + (int) this.getScrollAmount() - 4;
		if (relativeY < 0) return null;

		int index = 0;
		int childY = 0;

		for (E entry : this.children()) {
			int height = entry.getHeight();

			if (relativeY >= childY && relativeY < childY + height) {
				return this.children().get(index);
			}

			index += 1;
			childY += entry.getHeight();
		}

		return null;
	}

	@Override
	protected int getMaxPosition() {
		int y = this.headerHeight;

		for (E entry : this.children()) {
			y += entry.getHeight();
		}

		return y;
	}

	@Override
	protected void centerScrollOn(E entry) {
		int index = this.children().indexOf(entry);
		this.setScrollAmount(this.getRowTop(index) + this.itemHeight / 2 - (this.bottom - this.top) / 2);
	}

	@Override
	protected void ensureVisible(E entry) {
		int index = this.children().indexOf(entry);

		int y = this.getRowTop(index);
		int height = entry.getHeight();

		int topY = y - this.top - 4 - height;
		if (topY < 0) {
			this.scroll(topY);
		}

		int bottomY = this.bottom - y - height * 2;
		if (bottomY < 0) {
			this.scroll(-bottomY);
		}
	}

	private int getRowStartY() {
		return this.top + 4 + this.headerHeight - (int) this.getScrollAmount();
	}

	@Override
	protected int getRowTop(int index) {
		int y = this.getRowStartY();

		for (E entry : this.children().subList(0, index)) {
			y += entry.getHeight();
		}

		return y;
	}

	@Override
	protected int getRowBottom(int index) {
		int y = this.getRowStartY();

		for (E entry : this.children()) {
			y += entry.getHeight();
		}

		return y;
	}

	public static abstract class Entry<E extends Entry<E>> extends ElementListWidget.Entry<E> {
		public abstract int getHeight();
	}
}
