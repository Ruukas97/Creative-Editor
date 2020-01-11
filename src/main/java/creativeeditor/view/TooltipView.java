package creativeeditor.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
import creativeeditor.screen.ParentItemScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Size2i;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.client.gui.GuiModList;

public class TooltipView extends ItemView {
	public TooltipView(int x, int y, int width, int height, ParentItemScreen parent) {
		super(x, y, width, height, parent);
	}
	

	@Override
	public void mainRender() {
	}
	
	/*
    class TooltipPanel extends ScrollPanel {
    	TooltipPanel(Minecraft mc, int width, int height, int top)
        {
            super(mc, width, height, top, TooltipView.this.x);
        }

        @Override
        public int getContentHeight()
        {
            int height = 50;
            height += (parent.getItem().getItemStack()..size() * font.FONT_HEIGHT);
            if (height < this.bottom - this.top - 8)
                height = this.bottom - this.top - 8;
            return height;
        }

        @Override
        protected int getScrollAmount()
        {
            return font.FONT_HEIGHT * 3;
        }

        @Override
        protected void drawPanel(int entryRight, int relativeY, Tessellator tess, int mouseX, int mouseY)
        {
            if (logoPath != null) {
                Minecraft.getInstance().getTextureManager().bindTexture(logoPath);
                GlStateManager.enableBlend();
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                // Draw the logo image inscribed in a rectangle with width entryWidth (minus some padding) and height 50
                int headerHeight = 50;
                GuiUtils.drawInscribedRect(left, relativeY, width - 5, headerHeight, logoDims.width, logoDims.height, false, true);
                relativeY += headerHeight;
            }

            for (ITextComponent line : lines)
            {
                if (line != null)
                {
                    GlStateManager.enableBlend();
                    GuiModList.this.font.drawStringWithShadow(line.getFormattedText(), left + 4, relativeY, 0xFFFFFF);
                    GlStateManager.disableAlphaTest();
                    GlStateManager.disableBlend();
                }
                relativeY += font.FONT_HEIGHT;
            }

            final ITextComponent component = findTextLine(mouseX, mouseY);
            if (component!=null) {
                GuiModList.this.renderComponentHoverEffect(component, mouseX, mouseY);
            }
        }

        private ITextComponent findTextLine(final int mouseX, final int mouseY) {
            double offset = (mouseY - top) + border + scrollDistance + 1;
            if (logoPath != null) {
                offset -= 50;
            }
            if (offset <= 0)
                return null;

            int lineIdx = (int) (offset / font.FONT_HEIGHT);
            if (lineIdx >= lines.size() || lineIdx < 1)
                return null;

            ITextComponent line = lines.get(lineIdx-1);
            if (line != null)
            {
                int k = left + border;
                for (ITextComponent part : line) {
                    if (!(part instanceof StringTextComponent))
                        continue;
                    k += GuiModList.this.font.getStringWidth(((StringTextComponent)part).getText());
                    if (k >= mouseX)
                    {
                        return part;
                    }
                }
            }
            return null;
        }

        @Override
        public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
            final ITextComponent component = findTextLine((int) mouseX, (int) mouseY);
            if (component != null) {
                GuiModList.this.handleComponentClicked(component);
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        protected void drawBackground() {
        }
    }*/
}
