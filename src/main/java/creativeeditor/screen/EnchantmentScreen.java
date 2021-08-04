package creativeeditor.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.tag.TagEnchantment;
import creativeeditor.data.tag.TagList;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.ScrollableScissorWindow;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
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
    private Button selectedButton = null;
    protected List<Widget> selectedWidgets = Lists.newArrayList();

    private enum LevelLimit {
        SURVIVAL
    }


    public EnchantmentScreen(Screen lastScreen, TagList<TagEnchantment> enchantmentsTag) {
        super(new TranslationTextComponent("gui.enchantment"), lastScreen);
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

        int yStart = 45;
        int yEnd = height - yStart - 15;
        int containerWidth = width / 3 - 10;

        added = addButton(new ScrollableScissorWindow(10, yStart, containerWidth, yEnd, I18n.get("gui.enchantment.applied")));
        for (TagEnchantment tag : enchantmentsTag) {
            addEnchantment(tag);
        }

        list = addButton(new ScrollableScissorWindow(width / 3 + 5, yStart, containerWidth, yEnd, I18n.get("gui.enchantment.all")));
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

        initSelectedWidgets();
    }

    public void setSelected(TagEnchantment selected, Button selectedButton) {
        this.selected = selected;
        this.selectedButton = selectedButton;
        initSelectedWidgets();
    }

    public void initSelectedWidgets() {
        selectedWidgets.clear();
        if (selected == null) {
            return;
        }

        int xStart = width - width / 3 + 20;
        int yStart = 45;

        selectedWidgets.add(addButton(new StyledButton(xStart, yStart, 60, 20, I18n.get("gui.enchantment.duplicate"), b -> duplicateSelected())));
        selectedWidgets.add(addButton(new StyledButton(xStart, yStart + 20, 60, 20, I18n.get("gui.enchantment.remove"), b -> removeSelected())));
        //Widget levelNumberField = addButton(new NumberField(xStart, yStart, 20, selected.getLevel()));
        //selectedWidgets.add(levelNumberField);
    }

    public void removeSelected() {
        if (selectedButton != null)
            added.getWidgets().remove(selectedButton);
        if (selected != null)
            enchantmentsTag.remove(selected);
    }

    public void duplicateSelected() {
        TagEnchantment tag = new TagEnchantment(selected.getEnchantment(), selected.getLevel());
        enchantmentsTag.add(tag);
        addEnchantment(tag);
    }

    public void addEnchantment(TagEnchantment tag) {
        StyledButton button = new StyledButton(0, 0, 50, 20, tag.getEnchantment().getFullname(tag.getLevel()).getString(), b -> {
            setSelected(tag, b);
        });
        if (tag.getEnchantment().getRegistryName() != null)
            button.setTooltip(tag.getEnchantment().getRegistryName().toString());
        added.getWidgets().add(button);
    }

    public int getLevel(Enchantment ench) {
        return ench.getMaxLevel();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float partialTicks, Color color) {
        super.backRender(matrix, mouseX, mouseY, partialTicks, color);
    }


    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float partialTicks, Color color) {
        super.mainRender(matrix, mouseX, mouseY, partialTicks, color);

        // int number = 5;
        // int offset = width / number;
        // int i = 1;

        drawString(matrix, font, added.getMessage(), added.x, added.y - 10, color.getInt());
        drawString(matrix, font, list.getMessage(), list.x, list.y - 10, color.getInt());
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float partialTicks, Color color) {
        super.overlayRender(matrix, mouseX, mouseY, partialTicks, color);

        if (!added.isScrolling() && GuiUtil.isMouseIn(mouseX, mouseY, added.x, added.y, added.getWidth(), added.getHeight())) {
            for (Widget w : added.getWidgets()) {
                if (w instanceof StyledButton) {
                    StyledButton b = (StyledButton) w;
                    String enchantment = b.getTooltip();
                    if (enchantment != null) {
                        GuiUtil.addToolTip(matrix, this, b, mouseX, mouseY, I18n.get(enchantment));
                    }
                }
            }
        }

        if (!list.isScrolling() && GuiUtil.isMouseIn(mouseX, mouseY, list.x, list.y, list.getWidth(), list.getHeight())) {
            for (Widget w : list.getWidgets()) {
                if (!(w instanceof StyledButton))
                    continue;
                StyledButton b = (StyledButton) w;
                String tooltip = b.getTooltip();
                if (tooltip == null)
                    continue;
                ResourceLocation loc = new ResourceLocation(tooltip);
                Enchantment ench = GameRegistry.findRegistry(Enchantment.class).getValue(loc);
                if (ench == null)
                    continue;
                String name = I18n.get(ench.getDescriptionId());
                String rarity = I18n.get("gui.enchantment.rarity." + ench.getRarity().toString().toLowerCase());
                String rarityLine = I18n.get("gui.enchantment.tooltip.rarity", rarity);
                String minLevel = I18n.get("gui.enchantment.tooltip.minlevel", ench.getMinLevel());
                String maxLevel = I18n.get("gui.enchantment.tooltip.maxlevel", ench.getMaxLevel());
                String typeLine = I18n.get("gui.enchantment.tooltip.type", ench.category != null ? ench.category.toString().toLowerCase() : "N/A");
                String descKey = ench.getDescriptionId() + ".desc";
                if (false && !I18n.exists(descKey))
                    GuiUtil.addToolTip(matrix, this, b, mouseX, mouseY, name, rarityLine, minLevel, maxLevel, typeLine);
                else {
                    String descLine = I18n.get(descKey);
                    GuiUtil.addToolTip(matrix, this, b, mouseX, mouseY, name, rarityLine, minLevel, maxLevel, typeLine, descLine);
                }
            }
        }
    }
}
