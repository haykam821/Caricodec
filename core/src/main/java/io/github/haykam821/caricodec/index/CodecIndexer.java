package io.github.haykam821.caricodec.index;

import java.util.HashSet;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapCodec.MapCodecCodec;
import com.mojang.serialization.MapDecoder;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.haykam821.caricodec.mixin.Apply2DecoderAccessor;
import io.github.haykam821.caricodec.mixin.Apply3DecoderAccessor;
import io.github.haykam821.caricodec.mixin.Apply4DecoderAccessor;
import io.github.haykam821.caricodec.mixin.FieldAccessor;
import io.github.haykam821.caricodec.mixin.FieldDecoderAccessor;
import io.github.haykam821.caricodec.mixin.LiftDecoderAccessor;
import io.github.haykam821.caricodec.mixin.MappedDecoderAccessor;
import io.github.haykam821.caricodec.mixin.RecordCodecAccessor;
import io.github.haykam821.caricodec.mixin.RecordCodecBuilderAccessor;
import io.github.haykam821.caricodec.mixin.UnitDecoderAccessor;

public final class CodecIndexer {
	private final Codec<?> codec;
	private final Set<FieldIndex<?>> results = new HashSet<>();

	private CodecIndexer(Codec<?> codec) {
		this.codec = codec;
	}

	public Set<FieldIndex<?>> getResults() {
		return this.results;
	}

	private void index() {
		try {
			MapCodecCodec<?> mapCodecCodec = (MapCodecCodec<?>) codec;
			MapCodec<?> mapCodec = mapCodecCodec.codec();

			RecordCodecBuilder<?, ?> builder = ((RecordCodecAccessor) mapCodec).getBuilder();
			this.indexBuilder(builder, FieldPath.root());
		} catch (Exception exception) {
			throw new CodecIndexException("Failed to index codec " + this.codec, exception);
		}
	}

	private void indexBuilder(RecordCodecBuilder<?, ?> builder, FieldPath path) {
		RecordCodecBuilderAccessor accessor = (RecordCodecBuilderAccessor) (Object) builder;
		this.indexDecoder(accessor.getDecoder(), path);
	}

	private void indexDecoder(MapDecoder<?> decoder, FieldPath path) {
		if (decoder instanceof Apply2DecoderAccessor applyDecoder) {
			this.indexBuilder(applyDecoder.getFunction(), path);
			this.indexBuilder(applyDecoder.getFirst(), path);
			this.indexBuilder(applyDecoder.getSecond(), path);
		} else if (decoder instanceof Apply3DecoderAccessor applyDecoder) {
			this.indexBuilder(applyDecoder.getFunction(), path);
			this.indexBuilder(applyDecoder.getFirst(), path);
			this.indexBuilder(applyDecoder.getSecond(), path);
			this.indexBuilder(applyDecoder.getThird(), path);
		} else if (decoder instanceof Apply4DecoderAccessor applyDecoder) {
			this.indexBuilder(applyDecoder.getFunction(), path);
			this.indexBuilder(applyDecoder.getFirst(), path);
			this.indexBuilder(applyDecoder.getSecond(), path);
			this.indexBuilder(applyDecoder.getThird(), path);
			this.indexBuilder(applyDecoder.getFourth(), path);
		} else if (decoder instanceof LiftDecoderAccessor liftDecoder) {
			this.indexBuilder(liftDecoder.getInner(), path);
		} else if (decoder instanceof MappedDecoderAccessor mappedDecoder) {
			this.indexDecoder(mappedDecoder.getParent(), path);
		} else if (decoder instanceof UnitDecoderAccessor unitDecoder) {
			// Seemingly ignorable
		} else if (decoder instanceof FieldAccessor field) {
			FieldDecoderAccessor fieldDecoder = (FieldDecoderAccessor) field.getDecoder();

			FieldPath fieldPath = path.and(fieldDecoder.getName());
			Decoder<?> fieldElementDecoder = fieldDecoder.getElementCodec();

			if (fieldElementDecoder instanceof Codec<?> fieldCodec) {
				if (fieldCodec instanceof MapCodecCodec<?> mapCodecCodec) {
					MapCodec<?> mapCodec = mapCodecCodec.codec();
					RecordCodecBuilder<?, ?> fieldBuilder = ((RecordCodecAccessor) mapCodec).getBuilder();

					this.indexBuilder(fieldBuilder, fieldPath);
				} else {
					this.results.add(new FieldIndex<>(fieldPath, fieldCodec));
				}
			} else {
				throw new CodecIndexException("Field decoder has too complex codec " + fieldElementDecoder + " (" + fieldElementDecoder.getClass() + ")");
			}
		} else {
			throw new CodecIndexException("Don't know how to index builder decoder " + decoder + " (" + decoder.getClass() + ")");
		}
	}

	@Override
	public String toString() {
		return "CodecIndexer{codec=" + this.codec + ", results=" + this.results + "}";
	}

	public static CodecIndexer index(Codec<?> codec) {
		CodecIndexer indexer = new CodecIndexer(codec);
		indexer.index();

		return indexer;
	}
}
