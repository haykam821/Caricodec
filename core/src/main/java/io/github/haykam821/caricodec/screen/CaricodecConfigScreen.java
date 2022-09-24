package io.github.haykam821.caricodec.screen;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import io.github.haykam821.caricodec.CaricodecCoreImpl;
import io.github.haykam821.caricodec.api.CaricodecConfigHolder;
import io.github.haykam821.caricodec.index.CodecIndexer;
import io.github.haykam821.caricodec.index.FieldIndex;
import io.github.haykam821.caricodec.index.FieldPath;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class CaricodecConfigScreen<T> extends Screen {
	private final Screen parent;

	private FieldListWidget widget;
	private ButtonWidget saveButton;
	private ButtonWidget exitButton;

	private final CaricodecConfigHolder<T> holder;
	private final CodecIndexer indexer;

	private final JsonObject tree;
	private final Set<FieldChange<?>> changes = new HashSet<>();

	public CaricodecConfigScreen(Screen parent, CaricodecConfigHolder<T> holder) {
		super(Text.translatable("text.caricodec.title." + holder.getId()));

		this.parent = parent;

		this.holder = holder;
		this.indexer = CodecIndexer.index(holder.getCodec());

		DataResult<JsonElement> result = holder.getCodec().encodeStart(CaricodecCoreImpl.OPS, holder.getConfig());
		this.tree = result.get().left().get().getAsJsonObject();
	}

	@Override
	protected void init() {
		super.init();

		this.widget = new FieldListWidget(this.client, this, 32, this.height - 32, 25);
		this.widget.add(this.indexer, this.holder.getId());
		this.addSelectableChild(this.widget);

		this.initButtons();
	}

	private void initButtons() {
		int width = WidgetSizes.WIDTH;
		int height = WidgetSizes.HEIGHT;

		int y = this.height - 27;

		this.saveButton = new ButtonWidget(this.width / 2 - width - 5, y, width, height, Text.literal("Save"), this::onClickSave);
		this.saveButton.active = false;
		this.addDrawableChild(this.saveButton);

		this.exitButton = new ButtonWidget(this.width / 2 + 5, y, width, height, Text.literal("Exit"), this::onClickExit);
		this.addDrawableChild(this.exitButton);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.widget.render(matrices, mouseX, mouseY, delta);
		super.render(matrices, mouseX, mouseY, delta);

		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
		this.saveButton.active = !this.changes.isEmpty();
	}

	public <V> V getValue(FieldIndex<V> field) {
		Codec<V> codec = field.getCodec();
		JsonObject object = this.tree;

		Iterator<String> iterator = field.getPath().iterator();

		while (iterator.hasNext()) {
			String component = iterator.next();

			if (iterator.hasNext()) {
				object = object.getAsJsonObject(component);
			} else {
				JsonElement element = object.get(component);
				return codec.decode(CaricodecCoreImpl.OPS, element).get().left().get().getFirst();
			}
		}

		throw new RuntimeException("Invalid path: " + field.getPath());
	}

	private <V> void setValue(FieldChange<V> change) {
		Codec<V> codec = change.field().getCodec();
		JsonObject object = this.tree;

		FieldPath path = change.field().getPath();
		Iterator<String> iterator = path.iterator();

		while (iterator.hasNext()) {
			String component = iterator.next();

			if (iterator.hasNext()) {
				object = object.getAsJsonObject(component);
			} else {
				JsonElement element = codec.encodeStart(CaricodecCoreImpl.OPS, change.value()).get().left().get();
				object.add(component, element);

				return;
			}
		}

		throw new RuntimeException("Invalid path: " + path);
	}

	public <V> void stageValue(FieldIndex<V> field, V value) {
		this.changes.add(new FieldChange<>(field, value));
	}

	@SuppressWarnings("unchecked")
	public <V> V getStagedValue(FieldIndex<V> field) {
		for (FieldChange<?> change : this.changes) {
			if (change.field() == field) {
				return (V) change.value();
			}
		}

		return null;
	}

	public void clearStagedValue(FieldIndex<?> field) {
		this.changes.removeIf(change -> {
			return change.field() == field;
		});
	}

	private void onClickSave(ButtonWidget button) {
		this.changes.forEach(this::setValue);
		this.changes.clear();

		T config = this.holder.getCodec().decode(CaricodecCoreImpl.OPS, this.tree).get().left().get().getFirst();
		this.holder.setConfig(config);
	}

	private void onClickExit(ButtonWidget button) {
		this.client.setScreen(this.parent);
	}
}
