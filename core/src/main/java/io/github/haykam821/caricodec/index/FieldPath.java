package io.github.haykam821.caricodec.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class FieldPath implements Iterable<String>, Comparable<FieldPath> {
	private static final FieldPath ROOT = new FieldPath(Collections.emptyList());
	private static final Text SEPARATOR = Text.literal(" > ");

	private final List<String> components;

	private FieldPath(List<String> components) {
		this.components = components;
	}

	public FieldPath and(String component) {
		List<String> components = new ArrayList<>(this.components);
		components.add(component);

		return new FieldPath(components);
	}

	public MutableText asText(String id) {
		MutableText text = Text.empty();

		Iterator<String> iterator = this.components.iterator();
		int index = 0;

		while (iterator.hasNext()) {
			iterator.next();

			String builtPath = String.join(".", this.components.subList(0, index + 1));
			text.append(Text.translatable("text.caricodec.field." + id + "." + builtPath));

			if (iterator.hasNext()) {
				text.append(SEPARATOR);
				index += 1;
			}
		}

		return text;
	}

	public MutableText getDescription(String id) {
		return Text.translatable("text.caricodec.description." + id + "." + String.join(".", this.components));
	}

	@Override
	public Iterator<String> iterator() {
		return this.components.iterator();
	}

	@Override
	public int compareTo(FieldPath other) {
		return this.toString().compareTo(other.toString());
	}

	@Override
	public String toString() {
		return String.join("/", this.components);
	}

	public static FieldPath root() {
		return FieldPath.ROOT;
	}
}
