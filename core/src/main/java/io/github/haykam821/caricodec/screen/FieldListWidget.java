package io.github.haykam821.caricodec.screen;

import com.mojang.serialization.Codec;

import io.github.haykam821.caricodec.index.CodecIndexer;
import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.screen.entry.BlockPosFieldEntry;
import io.github.haykam821.caricodec.screen.entry.BooleanFieldEntry;
import io.github.haykam821.caricodec.screen.entry.FieldEntry;
import io.github.haykam821.caricodec.screen.entry.ParsedFieldEntry;
import io.github.haykam821.caricodec.screen.entry.StringFieldEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.util.math.BlockPos;

public class FieldListWidget extends ElementListWidget<FieldEntry<?>> {
	private final CaricodecConfigScreen<?> screen;

	public FieldListWidget(MinecraftClient client, CaricodecConfigScreen<?> screen, int top, int bottom, int itemHeight) {
		super(client, screen.width, screen.height, top, bottom, itemHeight);

		this.screen = screen;
		this.centerListVertically = false;
	}

	public void add(CodecIndexer indexer, String id) {
		indexer.getResults().stream()
			.map(field -> FieldListWidget.createField(this.client, this.screen, field, id))
			.sorted()
			.forEach(this::addEntry);
	}

	@Override
	public int getRowWidth() {
		return WidgetSizes.ROW_WIDTH;
	}

	@Override
	protected int getScrollbarPositionX() {
		return super.getScrollbarPositionX() + 64;
	}

	@SuppressWarnings("unchecked")
	private static FieldEntry<?> createField(MinecraftClient client, CaricodecConfigScreen<?> screen, FieldIndex<?> field, String id) {
		Codec<?> codec = field.getCodec();

		if (codec == Codec.BOOL) {
			return new BooleanFieldEntry(client, screen, (FieldIndex<Boolean>) field, id);
		} else if (codec == Codec.STRING) {
			return new StringFieldEntry(client, screen, (FieldIndex<String>) field, id);
		} else if (codec == BlockPos.CODEC) {
			return new BlockPosFieldEntry(client, screen, (FieldIndex<BlockPos>) field, id);
		} else {
			return new ParsedFieldEntry<>(client, screen, field, id);
		}
	}
}
