package infinityitemeditor.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagItemList;
import infinityitemeditor.render.tile.Tileset;
import infinityitemeditor.screen.ParentScreen;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.styles.StyleVanilla;
import infinityitemeditor.util.ColorUtils.Color;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class TooltipHandler {
    @SubscribeEvent
    public void tooltipColor(RenderTooltipEvent.Color e) {
        //TODO custom nbt tooltip color

        if (StyleManager.getCurrentStyle() instanceof StyleVanilla) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof ParentScreen)) {
            StyleManager.getCurrentStyle().update();
        }

        Color color1 = new Color(StyleManager.getCurrentStyle().getMainColor().getInt());
        color1.setValue(.7f).setAlpha(230);
        e.setBorderStart(color1.getInt());

        Color color2 = StyleManager.getCurrentStyle().getFGColor(false, false);
        e.setBorderEnd(color2.getInt());

        color1.setValue(.125f).setAlpha(210);
        e.setBackground(color1.getInt());
    }

    @SubscribeEvent
    public void tooltipText(ItemTooltipEvent event) {
        final ItemStack stack = event.getItemStack();
        if (!stack.hasTag()) {
            return;
        }

        final CompoundNBT nbt = stack.getTag();
        if (nbt == null || !nbt.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND) || !nbt.getCompound("BlockEntityTag").contains("Items", Constants.NBT.TAG_LIST)) {
            return;
        }

        final CompoundNBT blockEntityTag = nbt.getCompound("BlockEntityTag");

        if (!blockEntityTag.contains("Items", Constants.NBT.TAG_LIST)) {
            return;
        }

        TagItemList items = new TagItemList(blockEntityTag.getList("Items", Constants.NBT.TAG_COMPOUND));
        if (items.isDefault()) {
            return;
        }

        List<ITextComponent> tooltip = event.getToolTip();
        int toRemove = Math.min(items.get().size(), 6);
        DataItem item = items.get().get(0);
        String s = item.getItemStack().getHoverName().getString() + " x" + item.getCount().get();

        for (int i = 0; i < tooltip.size(); i++) {
            ITextComponent t = tooltip.get(i);
            if (t.getString().equals(s)) {
                for (; toRemove > 0; toRemove--) {
                    tooltip.remove(i);
                }
                break;
            }
        }
    }

    @SubscribeEvent
    public void tooltipPost(RenderTooltipEvent.PostText event) {
        final ItemStack stack = event.getStack();
        if (!stack.hasTag()) {
            return;
        }

        final CompoundNBT nbt = stack.getTag();
        if (nbt == null || !nbt.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND) || !nbt.getCompound("BlockEntityTag").contains("Items", Constants.NBT.TAG_LIST)) {
            return;
        }

        final CompoundNBT blockEntityTag = nbt.getCompound("BlockEntityTag");

        if (!blockEntityTag.contains("Items", Constants.NBT.TAG_LIST)) {
            return;
        }

        TagItemList items = new TagItemList(blockEntityTag.getList("Items", Constants.NBT.TAG_COMPOUND));
        if (items.isDefault()) {
            return;
        }

        Tileset tile = Tileset.SLOT;
        int size = Math.min(45, items.get().size());
        int xCount = Math.min(size, 9);
        int yCount = (int) Math.ceil(size / 9f);
        Minecraft mc = Minecraft.getInstance();
        MainWindow window = mc.getWindow();
        int width = tile.getBorderSize() * 2 + tile.getSize() * xCount;
        int height = tile.getBorderSize() * 2 + tile.getSize() * yCount;

        int x = event.getX() - 5;
        int y = event.getY() + event.getLines().size() * 10 + 5;

        if (y + height > window.getGuiScaledHeight()) {
            y = event.getY() - height - 5;
        }

        int right = x + width;
        if (right > window.getGuiScaledWidth()) {
            x -= right - window.getGuiScaledWidth();
        }
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, 0, 700);

        Color color = new Color(0xFFFFFFFF);

        if (((BlockItem) stack.getItem()).getBlock() instanceof ShulkerBoxBlock) {
            DyeColor dye = ((ShulkerBoxBlock) ((BlockItem) stack.getItem()).getBlock()).getColor();
            if (dye != null) {
                color.setInt(dye.getColorValue());
            }
        }
        MatrixStack matrix = event.getMatrixStack();
        tile.renderTiles(mc, matrix, x, y, xCount, yCount, color);

        ItemRenderer render = mc.getItemRenderer();

        for (int i = 0; i < size; i++) {
            DataItem item = items.get().get(i);
            int xp = x + tile.getBorderSize() + 1 + (i % 9) * 18;
            int yp = y + tile.getBorderSize() + 1 + (i / 9) * 18;

            if (item != null) {
                ItemStack itemStack = item.getItemStack();
                render.renderGuiItem(itemStack, xp, yp);
                render.renderGuiItemDecorations(mc.font, itemStack, xp, yp);
            }
        }

        RenderSystem.popMatrix();
    }
}

