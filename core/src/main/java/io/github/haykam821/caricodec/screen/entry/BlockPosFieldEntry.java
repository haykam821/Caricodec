package io.github.haykam821.caricodec.screen.entry;

import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.CaricodecConfigScreen;
import io.github.haykam821.caricodec.screen.WidgetSizes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class BlockPosFieldEntry extends FieldEntry<BlockPos> {
	private static final int COMPONENT_SPACING = 5;
	private static final int COMPONENT_WIDTH = WidgetSizes.WIDTH / 3 - COMPONENT_SPACING + 1;

	private static final ComponentUpdater UPDATE_X = (pos, x) -> new BlockPos(x, pos.getY(), pos.getZ());
	private static final ComponentUpdater UPDATE_Y = BlockPos::withY;
	private static final ComponentUpdater UPDATE_Z = (pos, z) -> new BlockPos(pos.getX(), pos.getY(), z);

	private final ClickableWidget x;
	private final ClickableWidget y;
	private final ClickableWidget z;

	private BlockPos value;

	public BlockPosFieldEntry(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<BlockPos> field, String id) {
		super(client, screen, field, id);

		this.value = this.screen.getOriginalValue(field);

		this.x = this.createWidget(Text.literal("X"), BlockPos::getX, UPDATE_X);
		this.y = this.createWidget(Text.literal("Y"), BlockPos::getY, UPDATE_Y);
		this.z = this.createWidget(Text.literal("Z"), BlockPos::getZ, UPDATE_Z);
	}

	private TextFieldWidget createWidget(Text message, ComponentGetter getter, ComponentUpdater updater) {
		TextFieldWidget widget = new TextFieldWidget(this.client.textRenderer, 0, 0, COMPONENT_WIDTH, WidgetSizes.HEIGHT, message);

		widget.setText(Integer.toString(getter.get(this.value)));

		widget.setChangedListener(text -> {
			try {
				int component = Integer.parseInt(text);
				this.value = updater.update(this.value, component);

				this.screen.stageValue(this.field, this.value);
			} catch (Exception exception) {
				return;
			}
		});

		return widget;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		int widgetX = x + entryWidth + 1;
		this.z.x = widgetX -= this.z.getWidth();
		this.y.x = widgetX -= this.y.getWidth() + COMPONENT_SPACING;
		this.x.x = widgetX -= this.x.getWidth() + COMPONENT_SPACING;

		int widgetY = y + entryHeight / 4;
		this.x.y = widgetY;
		this.y.y = widgetY;
		this.z.y = widgetY;

		this.x.render(matrices, mouseX, mouseY, tickDelta);
		this.y.render(matrices, mouseX, mouseY, tickDelta);
		this.z.render(matrices, mouseX, mouseY, tickDelta);

		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.x.mouseClicked(mouseX, mouseY, button) || this.y.mouseClicked(mouseX, mouseY, button) || this.z.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.x.mouseReleased(mouseX, mouseY, button) || this.y.mouseReleased(mouseX, mouseY, button) || this.z.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return this.x.keyPressed(keyCode, scanCode, modifiers) || this.y.keyPressed(keyCode, scanCode, modifiers) || this.z.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return this.x.keyReleased(keyCode, scanCode, modifiers) || this.y.keyReleased(keyCode, scanCode, modifiers) || this.z.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		return this.x.charTyped(chr, modifiers) || this.y.charTyped(chr, modifiers) || this.z.charTyped(chr, modifiers);
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
