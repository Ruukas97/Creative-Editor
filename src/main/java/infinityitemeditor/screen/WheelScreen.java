package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagEffect;
import infinityitemeditor.data.tag.TagList;
import infinityitemeditor.screen.models.TagFilter;
import infinityitemeditor.screen.models.TagModifier;
import infinityitemeditor.screen.models.WheelType;
import infinityitemeditor.screen.widgets.ScrollableScissorWindow;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.screen.widgets.StyledOptionSwitcher;
import infinityitemeditor.screen.widgets.StyledTextField;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils;
import infinityitemeditor.util.GuiUtil;
import infinityitemeditor.util.TextComponentUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class WheelScreen<T extends Data<?, ?>> extends ParentItemScreen {
    protected final String name;

    protected final WheelType<T> wheelType;
    protected final TagFilter<?>[] tagFilters;
    protected final TagModifier<T> tagModifier;
    protected final T[] allTags;
    protected final TagList<T> added;
    protected T hovered = null;

    protected ArrayList<T> filteredTags;
    private StyledOptionSwitcher filter;
    private StyledTextField searchField;
    private TagFilter<?> filteredFilter = WheelType.ALL;
    private String filteredString = "";
    private StyledButton addAll;

    private ScrollableScissorWindow addedWidgets;
    private Map<Widget, T> widgetTags;

    private int midX, midY;
    private double radius, radiusSquared;
    private int rotOff = 0;
    private int mouseDistSquared = 0;

    public WheelScreen(Screen lastScreen, String name, WheelType<T> wheelType, DataItem item, TagList<T> list) {
        super(new TranslationTextComponent("gui." + name), lastScreen, item);
        this.name = "gui." + name;
        this.wheelType = wheelType;
        tagFilters = wheelType.getTagFilters();
        tagModifier = wheelType.getTagModifier();
        allTags = wheelType.getAll();
        filteredTags = new ArrayList<>(allTags.length);
        Collections.addAll(filteredTags, allTags);
        added = list;
        renderItem = false;
    }

    @Override
    public void reset(Widget w) {
        super.reset(w);
        addedWidgets.children().clear();
    }

    public void filter() {
        String newFilter = searchField.getText().toLowerCase();
        if (newFilter.equals(filteredString) && filter.getOption() == filteredFilter) {
            return;
        }
        filteredTags.clear();
        filteredFilter = (TagFilter<?>) filter.getOption();
        filteredString = searchField.getText();

        T[] filtered = ((TagFilter<T>) filteredFilter).filter(item, allTags);
        for (T tag : filtered) {
            if (TextComponentUtils.getPlainText(wheelType.displayTag(tag)).toLowerCase().contains(newFilter)) {
                filteredTags.add(tag);
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        minecraft.keyboardHandler.setSendRepeatsToGui(true);

        for (Widget w : tagModifier.widgets(font, width, height)) {
            addButton(w);
        }

        filter = addButton(new StyledOptionSwitcher(10, 10, 100, 20, tagFilters, filteredFilter));
        searchField = addButton(new StyledTextField(font, 10, 35, 100, 20, filteredString));
        searchField.setHint(I18n.get("gui.wheel.search"));
        addAll = addButton(new StyledButton(10, 60, 100, 20, new TranslationTextComponent("gui.wheel.addall"), b -> {
            for (T tag : filteredTags) {
                addTag(tag);
            }
        }));

        addedWidgets = addButton(new ScrollableScissorWindow(width - 130, 50, 120, height - 60, new TranslationTextComponent(name + ".added")));
        widgetTags = new HashMap<>();

        for (T tag : added) {
            addTag(tag);
        }

        midX = width / 2;
        midY = height / 2;
        radius = height / 3d;
        radiusSquared = radius * radius;
    }

    @Override
    public void onClose() {
        super.onClose();
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    private void addTag(T tag) {
        Widget tagButton = new StyledButton(0, 0, 0, 20, TextComponentUtils.getPlainText(wheelType.displayTag(tag)), this::removeTagByWidget);
        addedWidgets.getWidgets().add(tagButton);
        widgetTags.put(tagButton, tag);
    }

    private void removeTagByWidget(Widget w) {
        addedWidgets.getWidgets().remove(w);
        if (widgetTags.containsKey(w)) {
            added.remove(widgetTags.get(w));
            widgetTags.remove(w);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (Math.abs(mouseDistSquared - radiusSquared) >= 3000) {
            rotOff++;
        }
        filter();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }

        if (hovered != null) {
            T clicked = wheelType.clone(hovered);
            tagModifier.modify(clicked);
            addTag(clicked);
            added.add(clicked);
            return true;
        }

        return false;
    }

    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        hovered = null;
        addedWidgets.visible = !addedWidgets.children().isEmpty();

        int midDistX = midX - mouseX;
        int midDistY = midY - mouseY;
        mouseDistSquared = midDistX * midDistX + midDistY * midDistY;

        double angle = 2 * Math.PI / filteredTags.size();
        double closestDistanceSquared = Integer.MAX_VALUE;
        boolean hovering = Math.abs(mouseDistSquared - radiusSquared) < 3000;

        if (addedWidgets.visible) {
            drawString(matrix, font, addedWidgets.getMessage(), addedWidgets.x, addedWidgets.y - 10, color.getInt());
        }


        //drawItemStack(matrix, item.getItemStack(), midX, midY, 5f, 0f, 0f, -rotOff * 3, null);

        for (int i = 0; i < filteredTags.size(); i++) {
            T tag = filteredTags.get(i);
            tagModifier.modify(tag);
            double angleI = (rotOff + (hovering ? 0d : p3)) * 0.01 + angle * i - 1.5708d;
            double x = midX + (radius * Math.cos(angleI));
            double y = midY + (radius * Math.sin(angleI));

            if (hovering && hovered == null) {
                double distX = x - mouseX;
                double distY = y - mouseY;
                double distSquared = distX * distX + distY * distY;

                if (distSquared < 100 && distSquared < closestDistanceSquared) {
                    closestDistanceSquared = distSquared;
                    hovered = filteredTags.get(i);
                }
            }

            RenderSystem.pushMatrix();
            RenderSystem.translated(x, y, 0);
            if (tag instanceof TagEffect) {
                TagEffect tagEffect = (TagEffect) tag;
                PotionSpriteUploader potionspriteuploader = this.minecraft.getMobEffectTextures();
                Effect effect = tagEffect.getEffectId().getEffect();
                TextureAtlasSprite textureatlassprite = potionspriteuploader.get(effect);
                this.minecraft.getTextureManager().bind(textureatlassprite.atlas().location());

                blit(matrix, -9, -9, this.getBlitOffset(), 18, 18, textureatlassprite);
                //fill(matrix, (int) x - 1, (int) y - 1, (int) x + 1, (int) y + 1, 0xFFFFFFFF);

            } else if (item.getItem().getItem() != Items.AIR) {
                drawItemStack(item.getItemStack(), -8, -8, 0f, 0f, null);
            } else {
                fill(matrix, -1, -1, 1, 1, color.getInt());
            }
            RenderSystem.popMatrix();
        }
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);

        double angle = 2 * Math.PI / filteredTags.size();
        double closestDistanceSquared = Integer.MAX_VALUE;
        boolean hovering = Math.abs(mouseDistSquared - radiusSquared) < 3000;

        for (int i = 0; i < filteredTags.size(); i++) {
            T tag = filteredTags.get(i);
            tagModifier.modify(tag);
            double angleI = (rotOff + (hovering ? 0d : p3)) * 0.01 + angle * i - 1.5708d;
            double relX = (radius * Math.cos(angleI));
            double relY = (radius * Math.sin(angleI));
            double x = midX + relX;
            double y = midY + relY;

            if (hovering && hovered == null) {
                double distX = x - mouseX;
                double distY = y - mouseY;
                double distSquared = distX * distX + distY * distY;

                if (distSquared < 100 && distSquared < closestDistanceSquared) {
                    closestDistanceSquared = distSquared;
                    hovered = filteredTags.get(i);
                }
            }

            RenderSystem.pushMatrix();
            RenderSystem.translated(x, y, 300);
            IFormattableTextComponent component = wheelType.displayTag(tag).withStyle(Style.EMPTY.withColor(StyleManager.getCurrentStyle().getFGColor(true, tag == hovered).toMcColor()));
            if (0.1d * Math.abs(relY) > Math.abs(relX)) {
                int textYOffset = relY < 0 ? -17 : 7;
                drawCenteredString(matrix, font, component, 0, textYOffset, color.getInt());
            }/* else if (0.2d * Math.abs(relY) > Math.abs(relX)) {
                int textYOffset = relY < 0 ? -12 : 2;
                if (relX < 0) {
                    drawString(matrix, font, component, -10 - font.width(component), textYOffset, color.getInt());
                } else {
                    drawString(matrix, font, component, 10, textYOffset, color.getInt());
                }
            }*/ else {
                if (relX < 0) {
                    drawString(matrix, font, component, -10 - font.width(component), -5, color.getInt());
                } else {
                    drawString(matrix, font, component, 10, -5, color.getInt());
                }
            }

            RenderSystem.popMatrix();
        }

        if (hovered != null) {
            GuiUtil.addToolTip(matrix, this, mouseX, mouseY, wheelType.tooltip(hovered));
        } else for (Widget w : addedWidgets.getWidgets()) {
            if (w.isHovered() && widgetTags.containsKey(w)) {
                GuiUtil.addToolTip(matrix, this, mouseX, mouseY, wheelType.tooltip(widgetTags.get(w)));
                return;
            }
        }
    }
}
