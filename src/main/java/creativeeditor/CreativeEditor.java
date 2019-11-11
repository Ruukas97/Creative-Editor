package creativeeditor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import creativeeditor.creativetabs.TabUnavailable;
import creativeeditor.events.CEClientChatReceivedEvent;
import creativeeditor.events.CEGuiScreenEvent;
import creativeeditor.events.CERegistryEvent;
import creativeeditor.events.CEScreenshotEvent;
import creativeeditor.nbt.NBTItemBase;
import creativeeditor.screen.MainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod("creativeeditor")
public class CreativeEditor {
	private static final Logger LOGGER = LogManager.getLogger();
	private static KeyBinding OPEN_EDITOR_KEY;

	private Minecraft mc;

	//private ArrayList<ItemGroup> itemGroupArray = new ArrayList<>();
	@SuppressWarnings("unused")
	private ItemGroup tabUnavailable;

	public CreativeEditor() {
		OPEN_EDITOR_KEY = new KeyBinding("key.editor", GLFW.GLFW_KEY_U, "creativeeditor");
		ClientRegistry.registerKeyBinding(OPEN_EDITOR_KEY);
		
		// Register Events
		registerEventHandler(new CEGuiScreenEvent());
		registerEventHandler(new CERegistryEvent());
		registerEventHandler(new CEScreenshotEvent());
		registerEventHandler(new CEClientChatReceivedEvent());
		registerEventHandler(this);

		// Register Creative Tabs
		registerTabs();

		mc = Minecraft.getInstance();
	}

	private void registerEventHandler(Object target) {
		MinecraftForge.EVENT_BUS.register(target);
	}

	private void registerTabs() {
		tabUnavailable = new TabUnavailable();
	}

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent event) {
		// preventing crash on main menu
		if (mc.player != null && event.getKey() == OPEN_EDITOR_KEY.getKey().getKeyCode()) {
			mc.displayGuiScreen(new MainScreen(mc.currentScreen, new NBTItemBase(mc.player.getHeldItemMainhand())));
		}
	}
}
