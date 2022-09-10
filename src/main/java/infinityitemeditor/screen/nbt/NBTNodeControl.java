package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.render.TreeIcons;
import infinityitemeditor.styles.StyleManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTNodeControl extends Widget implements INBTNode {
    private final INBTNode node;
    @Getter
    private final int depth;
    @Getter
    @Setter
    private final String key;

    @Getter
    protected boolean expanded = false;
    @Getter
    private final Map<String, NBTNodeControl> children = new HashMap<>();

    public NBTNodeControl(INBTNode node, int depth, String key) {
        super(depth * 20, 0, 100, 20, new StringTextComponent("NBT"));
        this.node = node;
        this.depth = depth;
        this.key = key;
        if (node.getSubNodes() == null) {
            return;
        }
        for (Map.Entry<String, ? extends INBTNode> sub : node.getSubNodes().entrySet()) {
            children.put(sub.getKey(), new NBTNodeControl(sub.getValue(), depth + 1, sub.getKey()));
        }
    }

    @Override
    public ITextComponent getNodeName(String key) {
        ITextComponent name = node.getNodeName(key);

        if (name instanceof IFormattableTextComponent) {
            IFormattableTextComponent text = (IFormattableTextComponent) name;
            text.append(new StringTextComponent(": "));
            if (!this.children.isEmpty()) {
                text.append(new StringTextComponent("[" + this.children.size() + " entries]"));
            } else if (node instanceof Data<?, ?>) {
                Data<?, ?> data = (Data<?, ?>) node;
                text.append(data.getNBT().getAsString());
            }
            return text;
        }
        return name;
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        node.renderIcon(mc, matrix, x, y);
    }

    @Override
    public Map<String, ? extends INBTNode> getSubNodes() {
        return children;
    }

    @Override
    public ContextMenu getContextMenu() {
        return INBTNode.super.getContextMenu();
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        if (!super.clicked(mouseX, mouseY)) {
            return false;
        }
        if (children.isEmpty()) {
            return false;
        }
        setExpanded(!expanded);
        return true;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        //GuiUtil.drawFrame(matrix, this, 1, new ColorUtils.Color(0xFFFFFFFF));

        int depthOffset = depth * 20;
        if (!children.isEmpty()) {
            (expanded ? TreeIcons.EXPANDED : TreeIcons.COLLAPSED).renderIcon(mc, matrix, x + 4 + depthOffset, y + 5);
        }

        renderIcon(mc, matrix, x + 20 + depthOffset, y + 2);
        drawString(matrix, mc.font, getNodeName(key), x + 40 + depthOffset, y + 7, 0xFFFFFFFF);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void playDownSound(SoundHandler soundHandler) {
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        setChildrenVisibility(expanded);
    }

    public void setChildrenVisibility(boolean visibility) {
        boolean visible = expanded && visibility;
        for (NBTNodeControl control : children.values()) {
            control.visible = visible;
            control.active = visible;
            control.setChildrenVisibility(visible);
        }
    }

    public void addChildren(List<Widget> list) {
        for (NBTNodeControl n : children.values()) {
            list.add(n);
            n.addChildren(list);
        }
    }

    public void renderLines(MatrixStack matrix) {
        if (!active || !visible) {
            return;
        }
        int depthOffset = depth * 20;
        int lineColor = StyleManager.getCurrentStyle().getMainColor().getInt();
        AbstractGui.fill(matrix, x + 8 + depthOffset, y + 9, x + 29 + depthOffset, y + 10, lineColor);
        if (depth > 0) {
            AbstractGui.fill(matrix, x + 8 + depthOffset, y - 10, x + 9 + depthOffset, y + height - 11, lineColor);
        }
    }
}
