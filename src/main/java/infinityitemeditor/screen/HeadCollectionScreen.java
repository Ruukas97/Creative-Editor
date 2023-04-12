package infinityitemeditor.screen;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.json.CachedHead;
import infinityitemeditor.json.MinecraftHeads;
import infinityitemeditor.json.MinecraftHeadsCategory;
import infinityitemeditor.render.HeadRenderer;
import infinityitemeditor.styles.Style;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HeadCollectionScreen extends ParentScreen {
    private final List<CachedHead> filteredHeads = new LinkedList<>();
    private static MinecraftHeadsCategory selCat = MinecraftHeadsCategory.alphabet;
    public boolean ignoreKey = false;
    private int currentElement = 0;
    private String filteredString = null;
    private String searchString = "";
    private final int divideHeight = 20;

    private int maxInRow;
    private int amountInPage;


    public HeadCollectionScreen(Screen lastScreen) {
        super(new TranslationTextComponent("gui.headcollection"), lastScreen);
    }


    @Override
    public void init() {
        long startTime = System.nanoTime();

        super.init();

        maxInRow = (width - 250) / 14;
        amountInPage = maxInRow * 10;

        filteredString = null;

        if (!searchString.equals(filteredString)) {
            filteredHeads.clear();

            Thread thread = new Thread(() -> {
                for (CachedHead head : MinecraftHeads.getHeads(selCat)) {
                    if (head.getData().getName().contains(searchString)) {
                        filteredHeads.add(head);
                    }
                }
            });
            thread.start();

            currentElement = 0;
            filteredString = searchString;
        }

        minecraft.keyboardHandler.setSendRepeatsToGui(true);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Took " + duration + "ms to init head collection.");
    }


    @Override
    public void removed() {
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        int heightOffset = height / divideHeight;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton != 0) {
            return false;
        }

        maxInRow = (width - 250) / 14;
        amountInPage = maxInRow * 10;

        int letterSpace = 80;
        int space = ((width - (maxInRow * 16)) - letterSpace) / 2;
        int nextPageW = font.width("-->");
        int topbar = 20;

        int currentPage = currentElement / amountInPage;
        int amountPages = ((int) Math.ceil(filteredHeads.size() / amountInPage) + 1);

        int searchW = font.width(searchString);
        if (searchString.length() > 0 && !searchString.equals(filteredString) && GuiUtil.isMouseInRegion((int) mouseX, (int) mouseY, (width / 2) - searchW / 2, heightOffset + 6, searchW, 8)) {
            init();
        }

        // Next page
        else if (currentPage + 1 < amountPages && GuiUtil.isMouseInRegion((int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, heightOffset + topbar + 168, nextPageW, 8)) {
            currentElement = Math.min(filteredHeads.size() - 1, (currentPage + 1) * amountInPage);
            playClickSound();
            return false;
        } else if (currentPage > 0 && GuiUtil.isMouseInRegion((int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, heightOffset + topbar + 168, nextPageW, 8)) {
            currentElement = Math.max(0, (currentPage - 1) * amountInPage);
            playClickSound();
            return false;
        }

        for (MinecraftHeadsCategory category : MinecraftHeadsCategory.values()) {
            int x = space + letterSpace / 2;
            int y = category.ordinal() * 15 + heightOffset + 9 + topbar;

            int sWH = font.width(category.getName()) / 2;
            if (mouseX > x - sWH && mouseX < x + sWH && mouseY > y - 1 && mouseY < y + 9) {
                playClickSound();
                selCat = category;
                currentElement = 0;
                init();
                return false;
            }
        }

        if (filteredHeads.size() > 0) {
            long startTime = System.nanoTime();

            for (int i = Math.min(filteredHeads.size() - 1, currentPage * amountInPage); i < Math.min(filteredHeads.size(), (currentPage + 1) * amountInPage); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = heightOffset + topbar + (16 * ((i % amountInPage) / maxInRow));
                Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(filteredHeads.get(i).getData().getGameProfile());
                if (map != null && map.containsKey(Type.SKIN)) {
                    HeadRenderer.loadSkin(map.get(Type.SKIN), null, false);
                }
                if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    ItemStack is = filteredHeads.get(i).getItemStack();
                    if (hasShiftDown()) {
                        minecraft.getConnection().send(new CCreativeInventoryActionPacket(-1, is));
                    } else {
                        int slot = InventoryUtils.getEmptySlotsCount(minecraft.player.inventory);
                        if (slot <= 0) {
                            minecraft.getConnection().send(new CCreativeInventoryActionPacket(-1, is));
                        } else {
                            int emptySlot = InventoryUtils.getEmptySlot(minecraft.player.inventory);
                            minecraft.getConnection().send(new CCreativeInventoryActionPacket(emptySlot, is));
                            minecraft.player.playSound(SoundEvents.ITEM_PICKUP, 0.1F, 1.01F);
                        }

                    }
                    return true;
                }
            }

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("Took " + duration + "ms to load head skins");
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    private void playClickSound() {
        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 0.2F, 1.01F);
    }


    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (super.keyPressed(key, scanCode, modifiers)) {
            return true;
        } else if (key == GLFW.GLFW_KEY_BACKSPACE) {
            searchString = searchString.substring(0, Math.max(searchString.length() - 1, 0));
            if (searchString.length() < 1 && !searchString.equals(filteredString)) {
                init();
            }
            return true;
        } else if ((key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) && !searchString.equals(filteredString)) {
            init();
            return true;
        } else if (key == GLFW.GLFW_KEY_SPACE) {
            searchString += " ";
            return true;
        } else if (Screen.isPaste(key)) {
            searchString = minecraft.keyboardHandler.getClipboard();
            return true;
        }
        return false;
    }

    public boolean charTyped(char c, int idk) {
        if (ignoreKey) {
            ignoreKey = false;
            return false;
        }
        if (searchString.length() < 20) {
            searchString += Character.toString(c);
            return true;
        }
        return false;
    }

    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        // super.backRender( mouseX, mouseY, p3, color );

        maxInRow = (width - 250) / 14;
        amountInPage = maxInRow * 10;

        int topbar = 20;
        int heightOffset = height / divideHeight;
        int letterSpace = 85;
        int space = ((width - (maxInRow * 16 + 3)) - letterSpace) / 2;
        int currentPage = currentElement / amountInPage;
        int blandColor = GuiUtil.getColorFromRGB(255, 230, 230, 245);
        int amountPages = ((int) Math.ceil(filteredHeads.size() / amountInPage) + 1);

        Style style = StyleManager.getCurrentStyle();
        int mainColor = color.getInt();

        // Background
        fill(matrix, space, heightOffset + topbar, space + letterSpace + (maxInRow * 16) + 3, heightOffset + topbar * 2 + 163, GuiUtil.getColorFromRGB(110, 0, 0, 0));

        // Letterspace
        fill(matrix, space + 2, heightOffset + topbar, space + letterSpace - 2, heightOffset + topbar + 161, GuiUtil.getColorFromRGB(100, 50, 50, 50));

        // Topbar
        fill(matrix, space, heightOffset, space + letterSpace + (maxInRow * 16) + 3, heightOffset + topbar, mainColor);

        // Outlines
        fill(matrix, space, heightOffset + topbar, space + 2, heightOffset + topbar + 161 + topbar, mainColor);
        fill(matrix, space + letterSpace + maxInRow * 16 + 1, heightOffset + topbar, space + letterSpace + maxInRow * 16 + 3, heightOffset + topbar + 161 + topbar, mainColor);
        fill(matrix, space + 2, heightOffset + topbar + 161, space + letterSpace + (maxInRow * 16) + 1, heightOffset + topbar + 163, mainColor);
        fill(matrix, space, heightOffset + topbar * 2 + 161, space + letterSpace + (maxInRow * 16) + 3, heightOffset + topbar * 2 + 163, mainColor);

        // Split bar
        fill(matrix, space + letterSpace, heightOffset + topbar, space + letterSpace - 2, heightOffset + topbar + 161, mainColor);

        for (MinecraftHeadsCategory category : MinecraftHeadsCategory.values()) {
            int x = space + letterSpace / 2;
            int y = category.ordinal() * 15 + +heightOffset + 9 + topbar;
            int sW = font.width(category.getName());
            int sWH = sW / 2;

            drawCenteredString(matrix, font, I18n.get("gui.headcollection.category." + category.getName()), x, y, (style.getFGColor(true, category == selCat || GuiUtil.isMouseInRegion(mouseX, mouseY, x - sWH, y - 1, sW, 10)).getInt()));
        }

        drawString(matrix, font, I18n.get("gui.headcollection") + " (" + filteredHeads.size() + ")", space + 7, heightOffset + 6, blandColor);

        drawCenteredString(matrix, font, searchString.length() > 0 ? searchString : I18n.get("gui.headcollection.typesearch"), width / 2, heightOffset + 6, blandColor);

        String pageString = I18n.get("gui.headcollection.currentpage", currentPage + 1, amountPages);
        drawString(matrix, font, pageString, space + letterSpace + maxInRow * 16 - font.width(pageString), heightOffset + 6, blandColor);

        String nextPage = "-->";
        int nextPageW = font.width(nextPage);
        if (currentPage + 1 < amountPages) {
            boolean selectedN = GuiUtil.isMouseInRegion(mouseX, mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, heightOffset + topbar + 168, nextPageW, 8);
            drawString(matrix, font, nextPage, space + letterSpace + maxInRow * 16 - 3 - nextPageW, heightOffset + topbar + 168, style.getFGColor(true, selectedN).getInt());
        }

        drawCenteredString(matrix, font, "" + (currentPage + 1), space + letterSpace + maxInRow * 16 - 13 - nextPageW, heightOffset + topbar + 168, blandColor);

        if (currentPage > 0) {
            String previousPage = "<--";
            boolean selectedP = GuiUtil.isMouseInRegion(mouseX, mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, heightOffset + topbar + 168, nextPageW, 8);
            drawString(matrix, font, previousPage, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, heightOffset + topbar + 168, style.getFGColor(true, selectedP).getInt());
        }

        drawString(matrix, font, I18n.get("gui.headcollection.credit"), space + 7, heightOffset + topbar + 168, blandColor);

        // Has to be before tooltip cause of skull hover
        drawCenteredString(matrix, font, I18n.get("gui.headcollection.freeslots") + ": " + InventoryUtils.getEmptySlotsCount(minecraft.player.inventory), width / 2, height - 45, blandColor);

        drawCenteredString(matrix, font, I18n.get("gui.headcollection.headsininventory") + ": " + InventoryUtils.countItem(minecraft.player.inventory, Items.PLAYER_HEAD), width / 2, height - 35, blandColor);


        ItemStack hovered = null;

        if (filteredHeads.size() > 0) {
            for (int i = Math.min(filteredHeads.size() - 1, currentPage * amountInPage); i < Math.min(filteredHeads.size(), (currentPage + 1) * amountInPage); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = heightOffset + topbar + (16 * ((i % amountInPage) / maxInRow));
                CachedHead cached = filteredHeads.get(i);
                // cached.loadTexture();
                ItemStack stack = cached.getItemStack();
                drawItemStack(stack, x, y, 1, 0, null);
                if (hovered == null && mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    fill(matrix, x, y, x + 16, y + 16, GuiUtil.getColorFromRGB(150, 150, 150, 150));
                    hovered = stack;
                }
            }
        }


        RenderSystem.translatef(0.0F, 0.0F, 100.0F);
        // this.setBlitOffset( 200 );

        int searchW = font.width(searchString);

        if (hovered != null) {
            GuiUtil.addToolTip(matrix, this, mouseX, mouseY, width, height, mouseX, mouseY, hovered.getDisplayName().getString(), TextFormatting.GRAY + I18n.get("gui.headcollection.clickhead"), TextFormatting.GRAY + I18n.get("gui.headcollection.clickheadshift"));
        } else if (!searchString.equals(filteredString) && GuiUtil.isMouseInRegion(mouseX, mouseY, (width / 2) - searchW / 2, 56, searchW, 8)) {
            GuiUtil.addToolTip(matrix, this, mouseX, mouseY, I18n.get("gui.headcollection.clicksearch"));
        } else {
            GuiUtil.addToolTip(matrix, this, space + 2, heightOffset + topbar, letterSpace - 4, 161, mouseX, mouseY, I18n.get("gui.headcollection.changecategory"));
        }

        RenderSystem.translatef(0.0F, 0.0F, -100.0F);
    }
}
