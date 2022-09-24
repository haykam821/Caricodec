package io.github.haykam821.caricodec.index;

import com.mojang.serialization.Codec;

public class FieldIndex<T> {
	private final FieldPath path;
	private final Codec<T> codec;

	public FieldIndex(FieldPath path, Codec<T> codec) {
		this.path = path;
		this.codec = codec;
	}

	public FieldPath getPath() {
		return this.path;
	}

	public Codec<T> getCodec() {
		return this.codec;
	}

	@Override
	public String toString() {
		return "FieldIndex{path=" + this.path + ", codec=" + this.codec + "}";
	}
}
