package creativeeditor.events;

import creativeeditor.data.DataItem;
import creativeeditor.screen.MainScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import org.lwjgl.glfw.GLFW;

import creativeeditor.CreativeEditor;
import creativeeditor.screen.HeadCollectionScreen;
import creativeeditor.screen.WindowManagerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyInputHandler {
    private static KeyBinding OPEN_EDITOR_KEY;
    private static KeyBinding OFF_HAND_SWING;
    public static KeyBinding HEAD_COLLECTION;
    public static KeyBinding BARRIER_TOGGLE;
    private static KeyBinding DEBUG_KEY;


    public static void init() {
        CreativeEditor.LOGGER.info("Initializing keybindings");
        OPEN_EDITOR_KEY = registerKeybind("editor", GLFW.GLFW_KEY_U);
        OFF_HAND_SWING = registerKeybind("offhandswing", InputMappings.UNKNOWN.getValue());
        HEAD_COLLECTION = registerKeybind("headcollection", GLFW.GLFW_KEY_V);
        BARRIER_TOGGLE = registerKeybind("barriertoggle", GLFW.GLFW_KEY_B);
        if (CreativeEditor.DEBUG)
            DEBUG_KEY = registerKeybind("debug", GLFW.GLFW_KEY_H);

    }


    @SubscribeEvent
    public void onKeyInput(final KeyInputEvent event) {
        if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || event.getAction() != GLFW.GLFW_PRESS || (mc.screen != null && !(mc.screen instanceof ContainerScreen<?>)))
            return;

        if (event.getKey() == OPEN_EDITOR_KEY.getKey().getValue()) {
            /*long window = mc.getMainWindow().getHandle();
            long handle = GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR);
            GLFW.glfwSetCursor( window, handle );
            GLFW.glfwSetCursorEnterCallback( window, (windowHandle, entered) -> {
               if(!entered) {
                   GLFW.glfwSetCursor( window, 0 );
               }
            });*/
            assert mc.player != null;
            mc.setScreen(new MainScreen(mc.screen, new DataItem(mc.player.getMainHandItem())));
        } else if (event.getKey() == OFF_HAND_SWING.getKey().getValue()) {
            assert mc.player != null;
            mc.player.swing(Hand.OFF_HAND);
            // mc.setScreen( new TextEditorScreen( mc.screen ) );
        } else if (event.getKey() == HEAD_COLLECTION.getKey().getValue()) {
            mc.setScreen(new HeadCollectionScreen(mc.screen));
        } else if (event.getKey() == BARRIER_TOGGLE.getKey().getValue()) {
            CreativeEditor.BARRIER_VISIBLE = !CreativeEditor.BARRIER_VISIBLE;
            mc.levelRenderer.allChanged(); // reload chunks
            System.out.println("Set barrier visibility: " + CreativeEditor.BARRIER_VISIBLE);
        } else if (CreativeEditor.DEBUG && event.getKey() == DEBUG_KEY.getKey().getValue()) {
            // mc.setScreen( new NBTExplorerScreen( mc.screen, new DataItem(
            // mc.player.getHeldItemMainhand() ) ) );
            // mc.setScreen( new EnchantmentScreen( mc.screen, new DataItem(
            // mc.player.getHeldItemMainhand() ) ) );
            // mc.setScreen( new ItemSpawnerScreen( mc.screen ) );
            mc.setScreen(new CreativeScreen(mc.player));
            //mc.setScreen( new WindowManagerScreen( new StringTextComponent( CreativeEditor.NAME ) ) );
        }
    }


    private static KeyBinding registerKeybind(String name, int keyCode) {
        KeyBinding key = new KeyBinding("key." + name, keyCode, CreativeEditor.NAME);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
