package infinityitemeditor.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.ColorHelperWidget;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.play.client.CCreativeInventoryActionPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ParentItemScreen extends ParentScreen {
    protected static DataItem item;

    // Back, reset, drop, save button (has essential buttons)
    protected boolean hasEssButtons = true;
    protected StyledButton backButton;
    protected StyledButton resetButton;
    protected StyledButton saveButton;
    protected StyledButton dropButton;

    // render item
    protected boolean renderItem = true;
    protected boolean renderColorHelper = false;
    private ColorHelperWidget colorHelperWidget;
    protected float itemScale = 2.0f;
    protected float itemRotX = 0.0f;
    protected int correctX = 0;

    // render tooltip top left
    protected boolean renderToolTip = false;


    public ParentItemScreen(MutableComponent title, Screen lastScreen, DataItem editing) {
        super(title, lastScreen);
        item = editing;
    }


    @Override
    protected void init() {
        super.init();
        renderables.clear();
        children().clear();
        renderWidgets.clear();

        if (hasEssButtons) {
            int bwidth = 68;
            int posX = width / 2 - (bwidth / 2);
            int posY = height - 42;
            boolean hasLastscreen = lastScreen != null;
            String butCloseBack = hasLastscreen ? "gui.main.back" : "gui.main.close";
            if (hasLastscreen) {
                posY += 10;
            }

            backButton = addRenderableWidget(new StyledButton(posX - bwidth - 1, posY, bwidth, 20, new TranslatableComponent(butCloseBack), this::back));

            resetButton = addRenderableWidget(new StyledButton(posX, (hasLastscreen ? posY : posY - 11), bwidth, 20, new TranslatableComponent("gui.main.reset"), this::reset));

            saveButton = hasLastscreen ? null : addRenderableWidget(new StyledButton(posX, posY + 10, bwidth, 20, new TranslatableComponent("gui.main.save"), this::save));

            dropButton = addRenderableWidget(new StyledButton(posX + bwidth + 1, posY, bwidth, 20, new TranslatableComponent("gui.main.drop"), this::drop));

            //if (!minecraft.player.abilities.instabuild) {
            //im not sure why it was checking instabuild instead of creative...
            if (!minecraft.player.isCreative()&&!minecraft.hasSingleplayerServer()) {
                if (saveButton != null) saveButton.active = false;
                dropButton.active = false;
            }

        }
        if (renderColorHelper) {
            colorHelperWidget = new ColorHelperWidget(renderables, (width - dropButton.x - dropButton.getWidth() - 10), 30, width, height);
            renderWidgets.add(colorHelperWidget);
        }
    }


    public void back(Widget w) {
        minecraft.setScreen(lastScreen);
    }


    public void reset(Widget w) {
        DataItem dItem = new DataItem(item.getItem().getItem(), 1, new CompoundTag(), item.getSlot().get());
        item = dItem;
        Screen last = lastScreen;
        while (last instanceof ParentItemScreen) {
            ParentItemScreen lastItemScreen = (ParentItemScreen) last;
            lastItemScreen.item = dItem;
            last = lastItemScreen.lastScreen;
        }
        init();
    }

    public void save(Widget w) {
        if (item.getItem().getItem() != Items.AIR) {
            int slotId = 36 + minecraft.player.inventory.selected;
            if (minecraft.hasSingleplayerServer()) {
                minecraft.getSingleplayerServer().getPlayerList().getPlayer(minecraft.player.getUUID()).inventoryMenu.setItem(slotId, item.getItemStack());
            } else {
                minecraft.getConnection().send(new CCreativeInventoryActionPacket(slotId, item.getItemStack()));
            }
        }
    }


    public void drop(Widget w) {
        if (item.getItem().getItem() != Items.AIR) {
            if (hasShiftDown()) {
                minecraft.keyboardHandler.setClipboard(generateGiveCommand(item));
            } else {
                minecraft.getConnection().send(new CCreativeInventoryActionPacket(-1, item.getItemStack()));
            }
        }
    }

    private String generateGiveCommand(DataItem item) {
        String nbt = item.getTag().getTag().toString();
        nbt = (nbt.length() > 2) ? nbt : "";
        String resource_location = item.getItem().getItem().getRegistryName().toString();
        String amount = item.getCount().get() > 1 ? " " + item.getCount().get() : "";
        return "/give @p " + resource_location + nbt + amount;
    }


    public DataItem getItem() {
        return item;
    }


    public void setRenderItem(boolean shouldRender, float scale) {
        this.renderItem = shouldRender;
        if (scale > 0)
            this.itemScale = scale;
    }

    public void setRenderItem(boolean shouldRender, float scale, int correctX) {
        setRenderItem(shouldRender, scale);
        this.correctX = correctX;
    }

    protected boolean isInColorWidget(int mouseX, int mouseY) {
        return colorHelperWidget != null && GuiUtil.isMouseInColorWidget(mouseX, mouseY, colorHelperWidget);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isInColorWidget((int) mouseX, (int) mouseY))
            return colorHelperWidget.mouseClicked(mouseX, mouseY, mouseButton);
        if (super.mouseClicked(mouseX, mouseY, mouseButton)) return true;
        if (renderItem && GuiUtil.isMouseIn((int) mouseX, (int) mouseY, width / 2 - 17, 43, 36, 36)) {
            minecraft.setScreen(new ItemInspectorScreen(this, item));
            return true;
        }
        return false;
    }

    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(poseStack,mouseX, mouseY, p3, color);
    }


    @Override
    public void overlayRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(poseStack,mouseX, mouseY, p3, color);
        // Item (Tooltip must render last or colors will be messed up)
        GuiUtil.addToolTip(poseStack,this, dropButton, mouseX, mouseY, I18n.get("gui.main.copyclipboard"));
        if (renderItem) {
            renderItem(poseStack,mouseX, mouseY, color, item);
        }
    }

    protected void renderItem(PoseStack poseStack, int mouseX, int mouseY, Color color, DataItem item) {
        ItemStack stack = item.getItemStack();

        Item ite = item.getItem().getItem();
        int x = width / 2;
        int y = 60;
        int xFrameStart = x - 19 + correctX;
        int xFrameEnd = x + 19;
        int yFrameStart = 41;
        if (ite == Items.AIR) {
            drawCenteredString(poseStack,font, ite.getName(stack).getString(), x, y - 3, color.getInt());
        } else {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(itemScale, itemScale, 1f);
            RenderSystem.scalef(itemScale, itemScale, 1f);
            drawItemStack(item.getItemStack(), (int) (xFrameStart / itemScale + 1), (int) (yFrameStart / itemScale + 1), itemRotX, 0, null);
            RenderSystem.popMatrix();
        }


        // TODO Item scale support
        if (GuiUtil.isMouseIn(mouseX, mouseY, width / 2 - 17, 43, 36, 36)) {
            itemRotX += 0.25;
            if (itemScale == 2f)
                GuiUtil.drawFrame(poseStack,xFrameStart, yFrameStart, xFrameEnd, 79, 1, StyleManager.getCurrentStyle().getFGColor(true, true));

            renderTooltip(poseStack,stack, mouseX, mouseY);
        } else {
            itemRotX = 0f;
            if (itemScale == 2f)
                GuiUtil.drawFrame(poseStack,xFrameStart, yFrameStart, xFrameEnd, 79, 1, color);
        }
    }
}
