package creativeeditor.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
	public final ForgeConfigSpec.BooleanValue clientBoolean;
	public final ForgeConfigSpec.ConfigValue<List<String>> clientStringList;
	public final ForgeConfigSpec.ConfigValue<DyeColor> clientEnumDyeColor;
	public final ForgeConfigSpec.ConfigValue<Integer> currentStyle;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		clientBoolean = builder
				.comment("An example boolean in the client config")
				.translation("creativeeditor.config.clientBoolean")
				.define("clientBoolean", true);
		clientStringList = builder
				.comment("An example list of Strings in the client config")
				.translation("creativeeditor.config.clientStringList")
				.define("clientStringList", new ArrayList<>());
		clientEnumDyeColor = builder
				.comment("An example enum DyeColor in the client config")
				.translation("creativeeditor.config.clientEnumDyeColor")
				.defineEnum("clientEnumDyeColor", DyeColor.WHITE);
		currentStyle = builder
				.comment("Sets the style to use in GUIs. 0: Spectrum, 1: Vanilla.")
				.translation("creativeeditor.config.currentStyle")
				.defineInRange("currentStyle", 0, 0, 1);
		builder.pop();
	}
}