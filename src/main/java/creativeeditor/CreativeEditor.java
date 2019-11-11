package creativeeditor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import creativeeditor.config.ConfigHandler;
import creativeeditor.creativetabs.TabUnavailable;
import creativeeditor.events.CEClientChatReceivedEvent;
import creativeeditor.events.CEGuiScreenEvent;
import creativeeditor.events.CERegistryEvent;
import creativeeditor.events.CEScreenshotEvent;
import creativeeditor.nbt.NBTItemBase;
import creativeeditor.screen.MainScreen;
import creativeeditor.styles.Style;
import creativeeditor.styles.StyleSpectrum;
import creativeeditor.styles.StyleVanilla;
import creativeeditor.util.CameraUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
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
	private static KeyBinding TEST_KEY;

	private Minecraft mc;

	//private ArrayList<ItemGroup> itemGroupArray = new ArrayList<>();
	@SuppressWarnings("unused")
	private ItemGroup tabUnavailable;

	public CreativeEditor() {
		mc = Minecraft.getInstance();
		final ModLoadingContext context = ModLoadingContext.get();
		
		LOGGER.info("Registering config");
		context.registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
		Style.setCurrentStyle(ConfigHandler.CLIENT.currentStyle.get() == 0 ? new StyleSpectrum() : new StyleVanilla());
		
		LOGGER.info("Registering keybindings");
		OPEN_EDITOR_KEY = new KeyBinding("key.editor", GLFW.GLFW_KEY_U, "creativeeditor");
		TEST_KEY = new KeyBinding("key.inspector", GLFW.GLFW_KEY_G, "creativeeditor");
		ClientRegistry.registerKeyBinding(OPEN_EDITOR_KEY);
		ClientRegistry.registerKeyBinding(TEST_KEY);
		
		// Register Events
		LOGGER.info("Registering events");
		registerEventHandler(new CEGuiScreenEvent());
		registerEventHandler(new CERegistryEvent());
		registerEventHandler(new CEScreenshotEvent());
		registerEventHandler(new CEClientChatReceivedEvent());
		registerEventHandler(this);

		// Register Creative Tabs
		registerTabs();
	}

	private void registerEventHandler(Object target) {
		MinecraftForge.EVENT_BUS.register(target);
	}

	private void registerTabs() {
		LOGGER.info("Registering Creative tabs");
		tabUnavailable = new TabUnavailable();
	}

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent event) {
		if(mc.world == null || event.getAction() != GLFW.GLFW_PRESS || (mc.currentScreen != null && mc.currentScreen instanceof ChatScreen))
			return;

		// preventing crash on main menu
		if (event.getKey() == OPEN_EDITOR_KEY.getKey().getKeyCode()) {
			mc.displayGuiScreen(new MainScreen(mc.currentScreen, new NBTItemBase(mc.player.getHeldItemMainhand())));
		}
		
		else if(event.getKey() == TEST_KEY.getKey().getKeyCode()) 
		{
			CameraUtil.setThirdPersonPerspective();
			Entity entity = mc.pointedEntity;
			if(entity instanceof PlayerEntity)
			{
				mc.player.sendMessage(entity.getDisplayName());
			}
		}
	}
}
