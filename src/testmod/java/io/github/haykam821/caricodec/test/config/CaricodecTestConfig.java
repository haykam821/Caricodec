package io.github.haykam821.caricodec.test.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.github.haykam821.caricodec.api.Caricodec;
import io.github.haykam821.caricodec.api.CaricodecConfigHolder;
import net.minecraft.util.math.BlockPos;

public record CaricodecTestConfig(
	boolean booleanValue,
	byte byteValue,
	short shortValue,
	int intValue,
	long longValue,
	float floatValue,
	double doubleValue,
	String stringValue,
	BlockPos pos,
	InnerConfig inner
) {
	public static final CaricodecTestConfig DEFAULT = new CaricodecTestConfig(false, (byte) 1, (short) 2, 3, 4, 5, 6, "String", new BlockPos(1, 2, 3), InnerConfig.DEFAULT);

	public static final Codec<CaricodecTestConfig> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			Codec.BOOL.fieldOf("boolean").forGetter(CaricodecTestConfig::booleanValue),
			Codec.BYTE.fieldOf("byte").forGetter(CaricodecTestConfig::byteValue),
			Codec.SHORT.fieldOf("short").forGetter(CaricodecTestConfig::shortValue),
			Codec.INT.fieldOf("int").forGetter(CaricodecTestConfig::intValue),
			Codec.LONG.fieldOf("long").forGetter(CaricodecTestConfig::longValue),
			Codec.FLOAT.fieldOf("float").forGetter(CaricodecTestConfig::floatValue),
			Codec.DOUBLE.fieldOf("double").forGetter(CaricodecTestConfig::doubleValue),
			Codec.STRING.fieldOf("string").forGetter(CaricodecTestConfig::stringValue),
			BlockPos.CODEC.fieldOf("pos").forGetter(CaricodecTestConfig::pos),
			InnerConfig.CODEC.fieldOf("inner").forGetter(CaricodecTestConfig::inner)
		).apply(instance, CaricodecTestConfig::new);
	});

	public static final CaricodecConfigHolder<CaricodecTestConfig> HOLDER = Caricodec.getApi().get(Caricodec.MOD_ID);
}
