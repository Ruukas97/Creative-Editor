package creativeeditor.handlers;

import org.lwjgl.glfw.GLFW;

import creativeeditor.CreativeEditor;
import creativeeditor.data.DataItem;
import creativeeditor.screen.EnchantmentsScreen;
import creativeeditor.screen.HeadCollectionScreen;
import creativeeditor.screen.MainScreen;
import creativeeditor.screen.PlayerInspectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyInputHandler {
    private static KeyBinding OPEN_EDITOR_KEY;
    private static KeyBinding PLAYER_INSPECT;
    private static KeyBinding OFF_HAND_SWING;
    public static KeyBinding HEAD_COLLECTION;
    public static KeyBinding BARRIER_TOGGLE;
    private static KeyBinding DEBUG_KEY;


    public static void init() {
        CreativeEditor.LOGGER.info( "Initializing keybindings" );
        OPEN_EDITOR_KEY = registerKeybind( "editor", GLFW.GLFW_KEY_U );
        PLAYER_INSPECT = registerKeybind( "inspector", GLFW.GLFW_KEY_G );
        OFF_HAND_SWING = registerKeybind( "offhandswing", InputMappings.INPUT_INVALID.getKeyCode() );
        HEAD_COLLECTION = registerKeybind( "headcollection", GLFW.GLFW_KEY_V );
        BARRIER_TOGGLE = registerKeybind( "barriertoggle", GLFW.GLFW_KEY_B );
        if (CreativeEditor.DEBUG)
            DEBUG_KEY = registerKeybind( "debug", GLFW.GLFW_KEY_H );

    }


    @SubscribeEvent
    public void onKeyInput( final KeyInputEvent event ) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null || event.getAction() != GLFW.GLFW_PRESS || mc.currentScreen instanceof ChatScreen || (mc.currentScreen != null && !(mc.currentScreen instanceof ContainerScreen<?>)))
            return;

        if (event.getKey() == OPEN_EDITOR_KEY.getKey().getKeyCode()) {
            mc.displayGuiScreen( new MainScreen( mc.currentScreen, new DataItem( mc.player.getHeldItemMainhand() ) ) );
        }
        else if (event.getKey() == PLAYER_INSPECT.getKey().getKeyCode()) {
            Entity entity = mc.pointedEntity;
            if (entity != null && entity instanceof PlayerEntity) {
                mc.displayGuiScreen( new PlayerInspectorScreen( mc.currentScreen, (PlayerEntity) mc.pointedEntity ) );
            }
            else {
                mc.displayGuiScreen( new PlayerInspectorScreen( mc.currentScreen, mc.player ) );
                // mc.displayGuiScreen( new ItemInspectorScreen( mc.currentScreen, new DataItem(
                // mc.player.getHeldItemMainhand() ) ) );
            }
        }
        else if (event.getKey() == OFF_HAND_SWING.getKey().getKeyCode()) {
            mc.player.swingArm( Hand.OFF_HAND );
            try {
                mc.player.sendMessage( mc.player.inventory.getCurrentItem().getTag().toFormattedComponent() );
            }
            catch(NullPointerException e) {
                System.out.println( "Nuller" );
            }
            //mc.displayGuiScreen( new TextEditorScreen( mc.currentScreen ) );
        }
        else if (event.getKey() == HEAD_COLLECTION.getKey().getKeyCode()) {
            mc.displayGuiScreen( new HeadCollectionScreen( mc.currentScreen ) );
        }
        else if (event.getKey() == BARRIER_TOGGLE.getKey().getKeyCode()) {
            CreativeEditor.BARRIER_VISIBLE = !CreativeEditor.BARRIER_VISIBLE;
        }
        else if (CreativeEditor.DEBUG && event.getKey() == DEBUG_KEY.getKey().getKeyCode()) {
            mc.displayGuiScreen( new EnchantmentsScreen( mc.currentScreen, new DataItem( mc.player.getHeldItemMainhand() ) ) );
            // mc.displayGuiScreen( new ItemSpawnerScreen( mc.currentScreen ) );
            // mc.displayGuiScreen( new CreativeScreen( mc.player ) );
        }
    }


    private static KeyBinding registerKeybind( String name, int keyCode ) {
        KeyBinding key = new KeyBinding( "key." + name, keyCode, CreativeEditor.NAME );
        ClientRegistry.registerKeyBinding( key );
        return key;
    }
}
