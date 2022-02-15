package infinityitemeditor.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class NBTExplorerScreen extends ParentScreen {
    /*
     * Features: Plain text editing Command like syntax with suggestions
     * NBTExplorer-like page Open externally: - create temp file - open it - in-game
     * refresh - apply - back will remove the file
     */
    private long lastTime;
    private DataItem item;
    private File openFile = null;
    private Process process = null;


    public NBTExplorerScreen(Screen lastScreen, DataItem item) {
        super(new TranslatableComponent("gui.externalnbt"), lastScreen);
        this.item = item;
    }


    @Override
    protected void init() {
        super.init();

        int buttons = 3;
        int slice = (width - 110) / buttons;
        int i = 0;

        // StyledTextField fileName = addRenderableWidget( new StyledTextField( font, 15 + slice *
        // i, height / 2 + 10, 80, 20, null ) );

        // i++;
        StyledButton open = addRenderableWidget(new StyledButton(15 + slice * i + slice / 2, height / 2 + 10, 80, 20, "Open", button -> {
            try {
                open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        i++;

        StyledButton reload = addRenderableWidget(new StyledButton(15 + slice * i + slice / 2, height / 2 + 10, 80, 20, "Reload", button -> {
            if (openFile != null) {
                try {
                    item = new DataItem(ItemStack.of(CompressedStreamTools.read(openFile)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        i++;

        StyledButton save = addRenderableWidget(new StyledButton(15 + slice * i + slice / 2, height / 2 + 10, 80, 20, "Save", button -> {
//            minecraft.gameMode.sendSlotPacket( item.getItemStack(), 36 + minecraft.player.inventory.currentItem );
        }));
        i++;
    }


    public void open() throws IOException {
        if (openFile == null) {
            File nbtDir = getTempDir().toFile();
            nbtDir.mkdirs();
            openFile = File.createTempFile("external", ".dat", nbtDir);
            openFile.deleteOnExit();
            CompressedStreamTools.write(item.getTag(), openFile);
        }

        if (process != null) {
            process.destroy();
        }

        process = new ProcessBuilder(getNBTExplorer(), "\"" + openFile.toPath() + "\"").start();
    }


    public Path getTempDir() {
        return InfinityItemEditor.DATAPATH.resolve("temp");
    }


    public String getNBTExplorer() {
        return "\"" + InfinityItemEditor.DATAPATH.resolve("nbtexplorer").resolve("NBTExplorer.exe") + "\"";
    }


    @Override
    public void backRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.backRender(poseStack,mouseX, mouseY, p3, color);
    }


    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(poseStack,mouseX, mouseY, p3, color);
    }


    @Override
    public void overlayRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(poseStack,mouseX, mouseY, p3, color);
    }

}
