package infinityitemeditor.events;

import com.mojang.blaze3d.platform.InputConstants;
import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.HeadCollectionScreen;
import infinityitemeditor.screen.MainScreen;
import infinityitemeditor.screen.ParentItemScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    private static KeyMapping OPEN_EDITOR_KEY;
    private static KeyMapping OFF_HAND_SWING;
    public static KeyMapping HEAD_COLLECTION;
    public static KeyMapping BARRIER_TOGGLE;
    private static KeyMapping DEBUG_KEY;


    public static void init() {
        InfinityItemEditor.LOGGER.info("Initializing keybindings");
        OPEN_EDITOR_KEY = registerKeybind("editor", GLFW.GLFW_KEY_U);
        OFF_HAND_SWING = registerKeybind("offhandswing", InputConstants.UNKNOWN.getValue());
        HEAD_COLLECTION = registerKeybind("headcollection", GLFW.GLFW_KEY_V);
        BARRIER_TOGGLE = registerKeybind("barriertoggle", GLFW.GLFW_KEY_B);
        if (InfinityItemEditor.DEBUG)
            DEBUG_KEY = registerKeybind("debug", GLFW.GLFW_KEY_H);

    }


    @SubscribeEvent
    public void onKeyInput(final KeyInputEvent event) {
        if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) return; // F3 key
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
            mc.player.swing(InteractionHand.OFF_HAND);
            // mc.setScreen( new TextEditorScreen( mc.screen ) );
        } else if (event.getKey() == HEAD_COLLECTION.getKey().getValue()) {
            mc.setScreen(new HeadCollectionScreen(mc.screen));
        } else if (event.getKey() == BARRIER_TOGGLE.getKey().getValue()) {
            InfinityItemEditor.BARRIER_VISIBLE = !InfinityItemEditor.BARRIER_VISIBLE;
            mc.levelRenderer.allChanged(); // reload chunks
        } else if (InfinityItemEditor.DEBUG && event.getKey() == DEBUG_KEY.getKey().getValue()) {
             mc.setScreen( new ParentItemScreen(new TextComponent(""), null, new DataItem(new ItemStack(Items.DIAMOND))) {
                 @Override
                 protected void init() {
                     renderColorHelper = true;
                     super.init();
                 }
             });
            // mc.player.getHeldItemMainhand() ) ) );
            // mc.setScreen( new EnchantmentScreen( mc.screen, new DataItem(
            // mc.player.getHeldItemMainhand() ) ) );
            // mc.setScreen( new ItemSpawnerScreen( mc.screen ) );
//            mc.setScreen(new CreativeScreen(mc.player));
            //mc.setScreen( new WindowManagerScreen( new TextComponent( CreativeEditor.NAME ) ) );
        }
    }


    private static KeyMapping registerKeybind(String name, int keyCode) {
        KeyMapping key = new KeyMapping("key." + name, keyCode, InfinityItemEditor.NAME);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
