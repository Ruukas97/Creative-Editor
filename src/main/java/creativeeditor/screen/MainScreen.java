package creativeeditor.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import creativeeditor.config.Config;
import creativeeditor.data.DataItem;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.widgets.ClassSpecificWidget;
import creativeeditor.widgets.ItemWidgets;
import creativeeditor.widgets.NumberField;
import creativeeditor.widgets.SliderTag;
import creativeeditor.widgets.StyledDataTextField;
import creativeeditor.widgets.StyledTextButton;
import creativeeditor.widgets.StyledToggle;
import creativeeditor.widgets.WidgetInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;

public class MainScreen extends ParentItemScreen {

    private ArrayList<Widget> toolsWidgets = new ArrayList<>(), loreWidgets = new ArrayList<>(), editWidgets = new ArrayList<>(), advancedWidgets = new ArrayList<>();
    private StyledTextButton nbtButton, tooltipButton, toolsButton, loreButton, editButton, advancedButton;
    private NumberField count, damage;
    // Tools
    private StyledTextButton styleButton;

    // Lore
    private StyledDataTextField nameField;
    private StyledTextButton clearButton;


    public MainScreen(Screen lastScreen, DataItem editing) {
        super( new TranslationTextComponent( "gui.main" ), lastScreen, editing );
    }


    public void tick() {
        if (nameField != null)
            nameField.tick();
        if (count != null)
            count.updateCursorCounter();
        if (damage != null)
            damage.updateCursorCounter();
    }


    @Override
    protected void init() {
        super.init();
        minecraft.keyboardListener.enableRepeatEvents( true );
        String nbtLocal = I18n.format( "gui.main.nbt" );
        String tooltipLocal = I18n.format( "gui.main.tooltip" );
        String toolsLocal = I18n.format( "gui.main.tools" );
        String loreLocal = I18n.format( "gui.main.display" );
        String editLocal = I18n.format( "gui.main.data" );
        String advancedLocal = I18n.format( "gui.main.other" );

        int nbtWidth = font.getStringWidth( nbtLocal );
        int tooltipWidth = font.getStringWidth( tooltipLocal );
        int toolsWidth = font.getStringWidth( toolsLocal );
        int loreWidth = font.getStringWidth( loreLocal );
        int editWidth = font.getStringWidth( editLocal );
        int advancedWidth = font.getStringWidth( advancedLocal );

        int nbtX = 21 + nbtWidth / 2;
        int toolsX = width / 3 - 16 - toolsWidth / 2;
        int tooltipX = (nbtX + toolsX) / 2;

        int advancedX = width - 21 - advancedWidth / 2;
        int loreX = 2 * width / 3 + 17 + loreWidth / 2;
        int editX = ((loreX + loreWidth / 2) + (advancedX - advancedWidth / 2)) / 2;

        minecraft.keyboardListener.enableRepeatEvents( true );

        toolsWidgets.clear();
        loreWidgets.clear();
        editWidgets.clear();
        advancedWidgets.clear();

        nbtButton = addButton( new StyledTextButton( nbtX, 35, nbtWidth, nbtLocal, b -> {
            setLeftTab( 0, true );
        } ) );

        tooltipButton = addButton( new StyledTextButton( tooltipX, 35, tooltipWidth, tooltipLocal, b -> {
            setLeftTab( 1, true );
        } ) );

        toolsButton = addButton( new StyledTextButton( toolsX, 35, toolsWidth, toolsLocal, b -> {
            setLeftTab( 2, true );

            for (Widget tool : toolsWidgets) {
                tool.visible = true;
            }
        } ) );

        loreButton = addButton( new StyledTextButton( loreX, 35, loreWidth, loreLocal, b -> {
            setRightTab( 0, true );

            for (Widget lore : loreWidgets) {
                lore.visible = true;
            }
        } ) );

        editButton = addButton( new StyledTextButton( editX, 35, editWidth, editLocal, b -> {
            setRightTab( 1, true );

            for (Widget lore : loreWidgets) {
                lore.visible = false;
            }
        } ) );

        advancedButton = addButton( new StyledTextButton( advancedX, 35, advancedWidth, advancedLocal, b -> {
            setRightTab( 2, true );

            for (Widget lore : loreWidgets) {
                lore.visible = false;
            }
        } ) );

        if (item.getItem().getItem() == Items.ARMOR_STAND) {
            addButton( new StyledTextButton( width / 2, height / 2, font.getStringWidth( "Armor Stand Editor" ), "Armor Stand Editor", b -> {
                minecraft.displayGuiScreen( new ArmorstandScreen( this, item ) );
            } ) );
        }

        int y = 55;
        for (ClassSpecificWidget w : ItemWidgets.getInstance()) {
            WidgetInfo info = new WidgetInfo( width - width / 6, y, advancedWidth, editX, w.text, null, this );
            Widget widget = w.get( info, item );
            if (widget != null) {
                addButton( widget );
                editWidgets.add( widget );
                y += 20;
            }
        }

        // Tools
        String styleLocal = I18n.format( "gui.main.style" );
        styleButton = addButton( new StyledTextButton( width / 6, 55, font.getStringWidth( styleLocal ), styleLocal, b -> StyleManager.setNext() ) );
        toolsWidgets.add( styleButton );

        // Lore
        String clearLocal = I18n.format( "gui.main.clear" );
        int clearWidth = font.getStringWidth( clearLocal );
        int clearX = width - 22 - clearWidth / 2;
        int nameX = 2 * width / 3 + 16;
        nameField = new StyledDataTextField( font, nameX, 55, clearX - nameX - clearWidth / 2 - 7, 20, item.getDisplayNameTag() );
        loreWidgets.add( nameField );
        children.add( nameField );
        clearButton = addButton( new StyledTextButton( clearX, 67, clearWidth, clearLocal, b -> {
            nameField.setText( item.getDisplayNameTag().getDefault().getFormattedText() );
            nameField.setCursorPos( 0 );
            nameField.setSelectionPos( 0 );
        } ) );
        loreWidgets.add( clearButton );


        // String id = I18n.format( "gui.main.id" );
        // int idWidth = font.getStringWidth( id );

        // General Item
        String count = I18n.format( "gui.main.count" );
        int countWidth = font.getStringWidth( count );
        String damage = I18n.format( "gui.main.damage" );
        int damageWidth = font.getStringWidth( damage );

        int x = width / 3 + 16 + Math.max( countWidth, damageWidth );
        int countX = x;
        this.count = addButton( new NumberField( font, countX, 61, 16, item.getCount() ) );
        countX += this.count.getWidth() + 8;
        addButton( new SliderTag( countX, 61, (width - width / 3 - 8) - countX, 16, item.getCount() ) );

        int dmgX = x;
        this.damage = addButton( new NumberField( font, dmgX, 81, 16, item.getTag().getDamage() ) );
        dmgX += this.damage.getWidth() + 8;
        addButton( new SliderTag( dmgX, 81, (width - width / 3 - 8) - dmgX, 16, item.getTag().getDamage() ) );


        addButton( new StyledToggle( width / 2 - 40, 101, 80, 16, "item.tag.unbreakable.true", "item.tag.unbreakable.false", item.getTag().getUnbreakable() ) );

        setLeftTab( Config.MAIN_LEFT_TAB.get(), false );
        setRightTab( Config.MAIN_RIGHT_TAB.get(), false );
    }


    @Override
    public void onClose() {
        super.onClose();
        minecraft.keyboardListener.enableRepeatEvents( true );
    }


    public void setLeftTab( int i, boolean updateConfig ) {
        if (updateConfig && i >= 0 && i <= 3)
            Config.MAIN_LEFT_TAB.set( i );

        switch (i) {
        case 0:
            nbtButton.active = false;
            tooltipButton.active = true;
            toolsButton.active = true;

            for (Widget tool : toolsWidgets) {
                tool.visible = false;
            }
            break;
        case 1:
            nbtButton.active = true;
            tooltipButton.active = false;
            toolsButton.active = true;

            for (Widget tool : toolsWidgets) {
                tool.visible = false;
            }
            break;
        case 2:
            nbtButton.active = true;
            tooltipButton.active = true;
            toolsButton.active = false;

            for (Widget tool : toolsWidgets) {
                tool.visible = true;
            }
            break;
        }
    }


    public void setRightTab( int i, boolean updateConfig ) {
        if (updateConfig && i >= 0 && i <= 3)
            Config.MAIN_RIGHT_TAB.set( i );

        switch (Config.MAIN_RIGHT_TAB.get()) {
        case 0:
            loreButton.active = false;
            editButton.active = true;
            advancedButton.active = true;

            for (Widget lore : loreWidgets) {
                lore.visible = true;
            }
            for (Widget edit : editWidgets) {
                edit.visible = false;
            }
            for (Widget advanced : advancedWidgets) {
                advanced.visible = false;
            }
            break;
        case 1:
            loreButton.active = true;
            editButton.active = false;
            advancedButton.active = true;

            for (Widget lore : loreWidgets) {
                lore.visible = false;
            }
            for (Widget edit : editWidgets) {
                edit.visible = true;
            }
            for (Widget advanced : advancedWidgets) {
                advanced.visible = false;
            }
            break;
        case 2:
            loreButton.active = true;
            editButton.active = true;
            advancedButton.active = false;

            for (Widget lore : loreWidgets) {
                lore.visible = false;
            }
            for (Widget edit : editWidgets) {
                edit.visible = false;
            }
            for (Widget advanced : advancedWidgets) {
                advanced.visible = true;
            }
            break;
        }
    }


    @Override
    public void resize( Minecraft minecraft, int par2, int par3 ) {
        this.init( minecraft, par2, par3 );
    }


    @Override
    public void removed() {
        this.minecraft.keyboardListener.enableRepeatEvents( false );
    }


    @Override
    public boolean keyPressed( int key1, int key2, int key3 ) {
        return super.keyPressed( key1, key2, key3 );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float partialTicks, Color color ) {
        super.backRender( mouseX, mouseY, partialTicks, color );

        // First vertical line
        fill( width / 3, 20, width / 3 + 1, height - 20, color.getInt() );
        // Second vertical line
        fill( width * 2 / 3, 20, width * 2 / 3 + 1, height - 20, color.getInt() );
        // Left horizontal line
        fill( 20, 40, width / 3 - 15, 41, color.getInt() );
        // Right horizontal line
        fill( width * 2 / 3 + 16, 40, width - 20, 41, color.getInt() );

        nameField.render( mouseY, mouseY, partialTicks );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float partialTicks, Color color ) {
        super.mainRender( mouseX, mouseY, partialTicks, color );

        // Item Name
        String itemCount = item.getCount().get() > 1 ? item.getCount().get() + "x " : "";
        String itemOverview = itemCount + item.getItemStack().getDisplayName().getFormattedText();
        String overviewTrimmed = font.trimStringToWidth( itemOverview, width / 3 - 15 );
        drawCenteredString( font, overviewTrimmed.equals( itemOverview ) ? overviewTrimmed : overviewTrimmed + "...", width / 2, 27, color.getInt() );


        String count = I18n.format( "gui.main.count" );
        int countWidth = font.getStringWidth( count );
        String damage = I18n.format( "gui.main.damage" );
        int damageWidth = font.getStringWidth( damage );

        int x = width / 3 + 10;
        String id = I18n.format( "gui.main.id" );
        //int idWidth = font.getStringWidth( id );
        drawString( font, id, x, 45, color.getInt() );
        drawString( font, item.getItem().get(), x + 6 + Math.max( countWidth, damageWidth ), 45, color.getInt() );


        drawString( font, count, x, 65, color.getInt() );

        // int damageWidth = font.getStringWidth( damage );
        drawString( font, damage, x, 85, color.getInt() );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float partialTicks, Color color ) {
        super.overlayRender( mouseX, mouseY, partialTicks, color );

        if (Config.MAIN_LEFT_TAB.get() == 0) {
            // NBT
            List<String> nbtLines = Arrays.asList( (minecraft.gameSettings.advancedItemTooltips ? item.getNBT() : item.getTag().getNBT()).toFormattedComponent( " ", 0 ).getFormattedText().split( "\n" ) );

            GuiUtils.drawHoveringText( item.getItemStack(), nbtLines, 0, 60, width / 3 - 1, height, -1, font );
        }

        else if (Config.MAIN_LEFT_TAB.get() == 1) {
            // renderTooltip(item.getItemStackClean(), 0, 60);
            ItemStack stack = item.getItemStack();

            GuiUtils.drawHoveringText( item.getItemStack(), getTooltipFromItem( stack ), 0, 60, width / 3 - 1, height, -1, font );
        }
    }
}
