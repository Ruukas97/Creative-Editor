package infinityitemeditor.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.tag.TagEnchantment;
import infinityitemeditor.data.tag.TagList;
import infinityitemeditor.screen.widgets.NumberField;
import infinityitemeditor.screen.widgets.ScrollableScissorWindow;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.screen.widgets.StyledTextButton;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;

public class EnchantmentScreen extends ParentScreen {

    // TODO choice button widget
    /*
     * Needed: Override existing/Allow stacking effects Level input/ List of
     * existing enchants with modify level, remove
     */
    protected TagList<TagEnchantment> enchantmentsTag;
    private ScrollableScissorWindow list;
    private ScrollableScissorWindow added;
    private NumberField level;
    private TagEnchantment selected = null;
    private StyledButton selectedButton = null;
    protected List<AbstractWidget> selectedWidgets = Lists.newArrayList();

    private enum LevelLimit {
        SURVIVAL,
        SHORT,
        INTEGER,
    }

    private enum Filter {
        ALL,
        APPLICABLE,
    }

    public EnchantmentScreen(Screen lastScreen, TagList<TagEnchantment> enchantmentsTag) {
        super(new TranslatableComponent("gui.enchantment"), lastScreen);
        this.enchantmentsTag = enchantmentsTag;
    }


    public static class EnchantComparator implements Comparator<Enchantment> {
        @Override
        public int compare(Enchantment o1, Enchantment o2) {
            return I18n.get(o1.getDescriptionId()).compareTo(I18n.get(o2.getDescriptionId()));
        }
    }


    @Override
    protected void init() {
        super.init();

        List<Enchantment> sortedEnchants = Lists.newArrayList(ForgeRegistries.ENCHANTMENTS);
        sortedEnchants.sort(new EnchantComparator());

        int yStart = 85;
        int yEnd = height - yStart - 10;
        int containerWidth = width / 3 - 10;

        String backString = I18n.get("gui.main.back");
        int backWidth = font.width(backString);
        addRenderableWidget(new StyledTextButton(10 + backWidth / 2, 15, backWidth, backString, b -> {
            minecraft.setScreen(lastScreen);
        }));

        list = addRenderableWidget(new ScrollableScissorWindow(10, yStart, containerWidth, yEnd, new TranslatableComponent("gui.enchantment.all")));
        for (Enchantment ench : sortedEnchants) {
            StyledButton button = new StyledButton(0, 0, 50, 20, ench.getFullname(getLevel(ench)).getString(), b -> {
                TagEnchantment tag = new TagEnchantment(ench, getLevel(ench));
                enchantmentsTag.add(tag);
                addEnchantment(tag);
            });
            if (ench.getRegistryName() != null)
                button.setTooltip(ench.getRegistryName().toString());
            list.getWidgets().add(button);
        }

        added = addRenderableWidget(new ScrollableScissorWindow(width / 3 + 5, yStart, containerWidth, yEnd, new TranslatableComponent("gui.enchantment.applied")));
        for (TagEnchantment tag : enchantmentsTag) {
            addEnchantment(tag);
        }

        initSelectedWidgets();
    }

    public void setSelected(TagEnchantment selected, StyledButton selectedButton) {
        if (this.selectedButton != null && this.selectedButton.getTooltip() != null && this.selected != null) {
            Enchantment ench = getEnchantmentFromResourceString(this.selectedButton.getTooltip());
            if (ench != null) {
                this.selectedButton.setMessage(this.selected.getEnchantment().getFullname(this.selected.getLevel().get()));
            }
        }

        this.selected = selected;
        this.selectedButton = selectedButton;

        if (selected != null && selectedButton != null) {
            this.selectedButton.setMessage(new TranslatableComponent("gui.enchantment.selected", selected.getEnchantment().getFullname(selected.getLevel().get())));
        }

        initSelectedWidgets();
    }

    public void initSelectedWidgets() {
        for (AbstractWidget w : selectedWidgets) {
            renderables.remove(w);
            children.remove(w);
        }
        selectedWidgets.clear();
        if (selected == null) {
            return;
        }

        int xStart = (width / 3 - 10) * 2 + 20;
        int yStart = 85;
        int eWidth = width / 3 - 10;

        selectedWidgets.add(addRenderableWidget(new StyledButton(xStart, yStart, eWidth, 20, I18n.get("gui.enchantment.duplicate"), b -> duplicateSelected())));
        selectedWidgets.add(addRenderableWidget(new StyledButton(xStart, yStart + 25, eWidth, 20, I18n.get("gui.enchantment.remove"), b -> removeSelected())));
//        selectedWidgets.add(addRenderableWidget(new (font, xStart, yStart + 40, 20, selected.getLevel())));
        selectedWidgets.add(addRenderableWidget(new NumberField(font, xStart + eWidth / 2, yStart + 50, 20, selected.getLevel())));
        //selectedWidgets.add(levelNumberField);
    }

    public void removeSelected() {
        if (selectedButton != null) {
            added.getWidgets().remove(selectedButton);
            selectedButton = null;
        }
        if (selected != null) {
            enchantmentsTag.remove(selected);
            selected = null;
        }
        initSelectedWidgets();
    }

    public void duplicateSelected() {
        TagEnchantment tag = new TagEnchantment(selected.getEnchantment(), selected.getLevel());
        enchantmentsTag.add(tag);
        addEnchantment(tag);
    }

    public void addEnchantment(TagEnchantment tag) {
        StyledButton button = new StyledButton(0, 0, 50, 20, tag.getEnchantment().getFullname(tag.getLevel().get()).getString(), b -> {
            setSelected(tag, (StyledButton) b);
            if (hasControlDown()) {
                removeSelected();
            }
        });
        if (tag.getEnchantment().getRegistryName() != null)
            button.setTooltip(tag.getEnchantment().getRegistryName().toString());
        added.getWidgets().add(button);
        setSelected(tag, button);
    }

    public int getLevel(Enchantment ench) {
        return ench.getMaxLevel();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public void backRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, Color color) {
        super.backRender(poseStack,mouseX, mouseY, partialTicks, color);
    }


    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, Color color) {
        super.mainRender(poseStack,mouseX, mouseY, partialTicks, color);

        drawString(poseStack,font, list.getMessage(), list.x, list.y - 10, color.getInt());
        drawString(poseStack,font, added.getMessage(), added.x, added.y - 10, color.getInt());
    }


    @Override
    public void overlayRender(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, Color color) {
        super.overlayRender(poseStack,mouseX, mouseY, partialTicks, color);

        if (!list.isScrolling() && GuiUtil.isMouseIn(mouseX, mouseY, list.x, list.y, list.getWidth(), list.getHeight())) {
            for (Widget w : list.getWidgets()) {
                if (!(w instanceof StyledButton))
                    continue;
                StyledButton b = (StyledButton) w;
                String tooltip = b.getTooltip();
                if (tooltip == null)
                    continue;
                Enchantment ench = getEnchantmentFromResourceString(tooltip);
                if (ench != null && w.isHovered()) {
                    drawTooltip(poseStack,mouseX, mouseY, ench);
                }
            }
        }

        if (!added.isScrolling() && GuiUtil.isMouseIn(mouseX, mouseY, added.x, added.y, added.getWidth(), added.getHeight())) {
            for (Widget w : added.getWidgets()) {
                if (!(w instanceof StyledButton))
                    continue;
                StyledButton b = (StyledButton) w;
                String tooltip = b.getTooltip();
                if (tooltip == null)
                    continue;
                ResourceLocation loc = new ResourceLocation(tooltip);
                Enchantment ench = GameRegistry.findRegistry(Enchantment.class).getValue(loc);
                if (ench != null && w.isHovered()) {
                    drawTooltip(poseStack,mouseX, mouseY, ench);
                }
            }
        }

        if(selected != null){
            int xStart = (width / 3 - 10) * 2 + 20;
            int eWidth = width / 3 - 10;
            int textWidth = font.width(I18n.get("gui.enchantment.level"));
            drawString(poseStack,font, new TranslatableComponent("gui.enchantment.level"), xStart + eWidth/2 - 3 - textWidth,141, color.getInt());
        }
    }

    void drawTooltip(PoseStack poseStack, int x, int y, Enchantment ench) {
        String name = I18n.get(ench.getDescriptionId());
        String rarity = I18n.get("gui.enchantment.rarity." + ench.getRarity().toString().toLowerCase());
        String rarityLine = I18n.get("gui.enchantment.tooltip.rarity", rarity);
        String minLevel = I18n.get("gui.enchantment.tooltip.minlevel", ench.getMinLevel());
        String maxLevel = I18n.get("gui.enchantment.tooltip.maxlevel", ench.getMaxLevel());
        String typeLine = I18n.get("gui.enchantment.tooltip.type", ench.category != null ? ench.category.toString().toLowerCase() : "N/A");
        String descKey = ench.getDescriptionId() + ".desc";
        if (!I18n.exists(descKey))
            GuiUtil.addToolTip(poseStack,this, x, y, name, rarityLine, minLevel, maxLevel, typeLine);
        else {
            String descLine = I18n.get(descKey);
            GuiUtil.addToolTip(poseStack,this, x, y, name, rarityLine, minLevel, maxLevel, typeLine, descLine);
        }
    }

    Enchantment getEnchantmentFromResourceString(String resource) {
        ResourceLocation loc = new ResourceLocation(resource);
        return GameRegistry.findRegistry(Enchantment.class).getValue(loc);
    }
}
