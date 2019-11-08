package creativeeditor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import creativeeditor.events.CEGuiScreenEvent;
import creativeeditor.nbt.NBTItemBase;
import creativeeditor.screen.MainScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("creativeeditor")
public class CreativeEditor {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final KeyBinding OPEN_EDITOR_KEY = new KeyBinding("key.editor", GLFW.GLFW_KEY_U, "creativeeditor");

	Minecraft mc;

	public CreativeEditor() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);
		MinecraftForge.EVENT_BUS.register(new CEGuiScreenEvent());
		MinecraftForge.EVENT_BUS.register(this);
		mc = Minecraft.getInstance();
	}

	private void clientInit(final FMLClientSetupEvent event) {
		LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
		ClientRegistry.registerKeyBinding(OPEN_EDITOR_KEY);
	}

	@SubscribeEvent
	public void onKeyInput(final KeyInputEvent event) {
		//preventing crash on main menu
		if (mc.player != null) {
			if (event.getKey() == OPEN_EDITOR_KEY.getKey().getKeyCode()) {
				mc.displayGuiScreen(new MainScreen(mc.currentScreen, new NBTItemBase(mc.player.getHeldItemMainhand())));
			}
		}
	}
}
