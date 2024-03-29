package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.text.TranslationTextComponent;

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
        super(new TranslationTextComponent("gui.externalnbt"), lastScreen);
        this.item = item;
    }


    @Override
    protected void init() {
        super.init();

        int buttons = 3;
        int slice = (width - 110) / buttons;
        int i = 0;

        // StyledTextField fileName = addButton( new StyledTextField( font, 15 + slice *
        // i, height / 2 + 10, 80, 20, null ) );

        // i++;

        StyledButton open = addButton(new StyledButton(15 + slice * i + slice / 2, height / 2 + 10, 80, 20, "Open", button -> {
            try {
                open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        i++;

        StyledButton reload = addButton(new StyledButton(15 + slice * i + slice / 2, height / 2 + 10, 80, 20, "Reload", button -> {
            if (openFile != null) {
                try {
                    item = new DataItem(ItemStack.of(CompressedStreamTools.read(openFile)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        i++;

        StyledButton save = addButton(new StyledButton(15 + slice * i + slice / 2, height / 2 + 10, 80, 20, "Save", button -> {
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
            CompressedStreamTools.write(item.getNBT(), openFile);
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
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }


    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }

}
