package io.github.haykam821.caricodec.screen.entry;

import java.util.List;

import com.google.common.collect.ImmutableList;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.WidgetColors;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class BlockPosFieldEntry extends FieldEntry<BlockPos> {
	private static final int COMPONENT_WIDTH = WidgetSizes.THIRD_WIDTH - WidgetSizes.SPACING + 1;

	private static final ComponentUpdater UPDATE_X = (pos, x) -> new BlockPos(x, pos.getY(), pos.getZ());
	private static final ComponentUpdater UPDATE_Y = BlockPos::withY;
	private static final ComponentUpdater UPDATE_Z = (pos, z) -> new BlockPos(pos.getX(), pos.getY(), z);

	private final PosComponent x;
	private final PosComponent y;
	private final PosComponent z;

	private BlockPos value;

	public BlockPosFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<BlockPos> field, String id) {
		super(client, screen, field, id);

		this.value = this.screen.getOriginalValue(field);

		this.x = new PosComponent(Text.literal("X"), BlockPos::getX, UPDATE_X);
		this.y = new PosComponent(Text.literal("Y"), BlockPos::getY, UPDATE_Y);
		this.z = new PosComponent(Text.literal("Z"), BlockPos::getZ, UPDATE_Z);
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		int widgetX = this.getWidgetX(x) + 1;
		int widgetY = this.getWidgetY(y);

		this.x.setPos(widgetX, widgetY);
		this.y.setPos(widgetX += this.x.getWidth() + WidgetSizes.SPACING + 1, widgetY);
		this.z.setPos(widgetX += this.y.getWidth() + WidgetSizes.SPACING + 1, widgetY);

		this.x.render(matrices, mouseX, mouseY, tickDelta);
		this.y.render(matrices, mouseX, mouseY, tickDelta);
		this.z.render(matrices, mouseX, mouseY, tickDelta);

		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
	}

	@Override
	public List<? extends Element> children() {
		return ImmutableList.of(this.x.getWidget(), this.y.getWidget(), this.z.getWidget());
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return ImmutableList.of(this.x.getWidget(), this.y.getWidget(), this.z.getWidget());
	}

	private class PosComponent {
		private final TextFieldWidget widget;
		private final ComponentUpdater updater;

		private boolean valid = true;

		public PosComponent(Text message, ComponentGetter getter, ComponentUpdater updater) {
			this.widget = new TextFieldWidget(BlockPosFieldEntry.this.client.textRenderer, 0, 0, COMPONENT_WIDTH, WidgetSizes.HEIGHT, message);

			this.widget.setText(Integer.toString(getter.get(BlockPosFieldEntry.this.value)));
			this.widget.setChangedListener(this::onChanged);

			this.updater = updater;
		}

		private int parse(String text) {
			try {
				int value = Integer.parseInt(text);
				this.valid = true;
				return value;
			} catch (NumberFormatException exception) {
				this.valid = false;
				return 0;
			}
		}

		private void onChanged(String text) {
			int component = this.parse(text);
			BlockPosFieldEntry.this.value = this.updater.update(BlockPosFieldEntry.this.value, component);

			if (this.valid) {
				BlockPosFieldEntry.this.screen.stageValue(field, value);
			}
		}

		public TextFieldWidget getWidget() {
			return this.widget;
		}

		public int getWidth() {
			return this.widget.getWidth();
		}

		public void setPos(int x, int y) {
			this.widget.setX(x);
			this.widget.setY(y);
		}

		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			this.widget.render(matrices, mouseX, mouseY, delta);

			if (!this.valid) {
				int color = this.widget.isFocused() ? WidgetColors.FOCUSED_INVALID_OUTLINE : WidgetColors.INVALID_OUTLINE;
				TextFieldEntry.drawOutline(matrices, this.widget, color);
			}
		}
	}

	@FunctionalInterface
	private interface ComponentGetter {
		public int get(BlockPos pos);
	}

	@FunctionalInterface
	private interface ComponentUpdater {
		public BlockPos update(BlockPos pos, int component);
	}
}
