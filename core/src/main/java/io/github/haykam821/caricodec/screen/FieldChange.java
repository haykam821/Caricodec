package io.github.haykam821.caricodec.screen;

import io.github.haykam821.caricodec.index.FieldIndex;

public record FieldChange<T>(
	FieldIndex<T> field,
	T value
) {}
