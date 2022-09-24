package io.github.haykam821.caricodec.test;

import io.github.haykam821.caricodec.test.config.CaricodecTestConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CaricodecTestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenEvents.AFTER_INIT.register(CaricodecTestClient::afterScreenInit);
	}

	private static void afterScreenInit(MinecraftClient client, Screen screen, int scaledWidth, int scaledHeight) {
		if (screen instanceof TitleScreen) {
			int x = scaledWidth / 2 + 128;
			int y = scaledHeight / 4 + 132;
			CaricodecTestClient.addButton(client, screen, x, y);
		} else if (screen instanceof GameMenuScreen) {
			int x = scaledWidth / 2 + 128;
			int y = scaledHeight / 4 + 132;
			CaricodecTestClient.addButton(client, screen, x, y);
		}
	}

	private static ButtonWidget addButton(MinecraftClient client, Screen screen, int x, int y) {
		Text text = Text.literal("CFG");
		ButtonWidget button = new ButtonWidget(x, y, 20, 20, text, buttonx -> {
			CaricodecTest.LOGGER.info("Opening configuration screen");
			client.setScreen(CaricodecTestConfig.HOLDER.createScreen(screen));
		});

		Screens.getButtons(screen).add(button);
		return button;
	}
}
