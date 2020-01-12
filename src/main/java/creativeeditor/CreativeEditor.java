package creativeeditor;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import creativeeditor.config.ConfigHandler;
import creativeeditor.creativetabs.TabUnavailable;
import creativeeditor.data.DataItem;
import creativeeditor.eventhandlers.ScreenHandler;
import creativeeditor.eventhandlers.ScreenshotHandler;
import creativeeditor.screen.HeadScreen;
import creativeeditor.screen.HeadScreen.HeadCategories;
import creativeeditor.screen.MainScreen;
import creativeeditor.screen.ScreenPlayerInspector;
import creativeeditor.styles.StyleManager;
import creativeeditor.styles.StyleSpectrum;
import creativeeditor.styles.StyleVanilla;
import creativeeditor.util.LoadSkullThread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("creativeeditor")
public class CreativeEditor {
	private static final Logger LOGGER = LogManager.getLogger();
	private static KeyBinding OPEN_EDITOR_KEY;
	private static KeyBinding PLAYER_INSPECT;
	private static KeyBinding OFF_HAND_SWING;

	private Minecraft mc;

	@SuppressWarnings("unused")
	private ItemGroup tabUnavailable;

	public CreativeEditor() {
		mc = Minecraft.getInstance();
		final ModLoadingContext context = ModLoadingContext.get();

		LOGGER.info("Registering config");
		context.registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
		StyleManager.setCurrentStyle(ConfigHandler.CLIENT.currentStyle.get() == 0 ? new StyleSpectrum() : new StyleVanilla());

		LOGGER.info("Registering keybindings");
		OPEN_EDITOR_KEY = new KeyBinding("key.editor", GLFW.GLFW_KEY_U, "creativeeditor");
		PLAYER_INSPECT = new KeyBinding("key.inspector", GLFW.GLFW_KEY_G, "creativeeditor");
		OFF_HAND_SWING = new KeyBinding("key.offhandswing", InputMappings.INPUT_INVALID.getKeyCode(), "creativeeditor");
		ClientRegistry.registerKeyBinding(OPEN_EDITOR_KEY);
		ClientRegistry.registerKeyBinding(OFF_HAND_SWING);
		ClientRegistry.registerKeyBinding(PLAYER_INSPECT);

		// Register Events
		LOGGER.info("Registering events");
		registerEventHandler(new ScreenHandler());
		registerEventHandler(new ScreenshotHandler());
		registerEventHandler(this);

		// Register Creative Tabs
		registerTabs();

		// Register head library arrays
		registerHeadArrays();

		// Download Head Collection
		downloadHeads();
	}

	private void registerHeadArrays() {
		for (HeadCategories hc : HeadCategories.values()) {
			HeadScreen.headLibrary.put(hc, new ArrayList<ItemStack>());
		}
	}

	private void downloadHeads() {
		Thread thread = new Thread(new LoadSkullThread());
		thread.start();
	}

	private void registerEventHandler(Object target) {
		MinecraftForge.EVENT_BUS.register(target);
	}

	private void registerTabs() {
		LOGGER.info("Adding Creative Tabs");
		tabUnavailable = new TabUnavailable();
	}

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent event) {
		if (mc.world == null || event.getAction() != GLFW.GLFW_PRESS
				|| (mc.currentScreen != null && mc.currentScreen instanceof ChatScreen))
			return;

		if (event.getKey() == OPEN_EDITOR_KEY.getKey().getKeyCode()) {
			mc.displayGuiScreen(new MainScreen(mc.currentScreen, new DataItem(mc.player.getHeldItemMainhand())));
		}

		else if (event.getKey() == PLAYER_INSPECT.getKey().getKeyCode()) {
			Entity entity = mc.pointedEntity;
			if (entity != null) {
				mc.displayGuiScreen(new ScreenPlayerInspector(mc.currentScreen, mc.player));
			}
		} else if (event.getKey() == OFF_HAND_SWING.getKey().getKeyCode()) {
			mc.player.swingArm(Hand.OFF_HAND);

		}
	}
}
