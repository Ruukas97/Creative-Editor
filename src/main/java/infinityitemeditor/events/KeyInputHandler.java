package infinityitemeditor.events;

import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.collections.ItemCollection;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.data.base.DataUUID;
import infinityitemeditor.saving.DataItemCollection;
import infinityitemeditor.saving.SaveService;
import infinityitemeditor.screen.HeadCollectionScreen;
import infinityitemeditor.screen.MainScreen;
import infinityitemeditor.screen.ParentItemScreen;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.IOException;

public class KeyInputHandler {
    private static KeyBinding OPEN_EDITOR_KEY;
    private static KeyBinding OFF_HAND_SWING;
    public static KeyBinding HEAD_COLLECTION;
    public static KeyBinding BARRIER_TOGGLE;
    private static KeyBinding DEBUG_KEY;


    public static void init() {
        InfinityItemEditor.LOGGER.info("Initializing keybindings");
        OPEN_EDITOR_KEY = registerKeybind("editor", GLFW.GLFW_KEY_U);
        OFF_HAND_SWING = registerKeybind("offhandswing", InputMappings.UNKNOWN.getValue());
        HEAD_COLLECTION = registerKeybind("headcollection", GLFW.GLFW_KEY_V);
        BARRIER_TOGGLE = registerKeybind("barriertoggle", GLFW.GLFW_KEY_B);
        if (InfinityItemEditor.DEBUG)
            DEBUG_KEY = registerKeybind("debug", GLFW.GLFW_KEY_H);

    }


    @SubscribeEvent
    public void onKeyInput(final KeyInputEvent event) {
        if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 292)) return; // F3 key
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
            InfinityItemEditor.BARRIER_VISIBLE = !InfinityItemEditor.BARRIER_VISIBLE;
            mc.levelRenderer.allChanged(); // reload chunks
        } else if (InfinityItemEditor.DEBUG && event.getKey() == DEBUG_KEY.getKey().getValue()) {
            ItemStack stack = mc.player.getMainHandItem();
            if (stack.getItem() == Blocks.PURPLE_SHULKER_BOX.asItem()) {
                DataItem item = new DataItem(stack);
                File file = new File(SaveService.getInstance().getItemCollectionsDir(), new DataUUID().getData().toString() + ".nbt");
                DataString name = new DataString(stack.getHoverName().getString());
                DataItemCollection itemCollection = DataItemCollection.fromBlockEntityTag(file, item.getTag().getBlockEntityTag(), name);
                try {
                    itemCollection.save();
                    SaveService.getInstance().getItemCollections().add(itemCollection);
                    Minecraft.getInstance().player.sendMessage(new StringTextComponent("Added"), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            mc.setScreen(new ParentItemScreen(new StringTextComponent(""), null, new DataItem(new ItemStack(Items.DIAMOND))) {
//                @Override
//                protected void init() {
//                    renderColorHelper = true;
//                    super.init();
//                }
//            });
//            mc.player.getHeldItemMainhand() ) ) );
//            mc.setScreen(new EnchantmentScreen(mc.screen, new DataItem(
//                    mc.player.getHeldItemMainhand())));
//            mc.setScreen(new ItemSpawnerScreen(mc.screen));
//            mc.setScreen(new CreativeScreen(mc.player));
//            mc.setScreen(new WindowManagerScreen(new StringTextComponent(CreativeEditor.NAME)));
        }
    }


    private static KeyBinding registerKeybind(String name, int keyCode) {
        KeyBinding key = new KeyBinding("key." + name, keyCode, InfinityItemEditor.NAME);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
