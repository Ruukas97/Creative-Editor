package creativeeditor.screen;

import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagEnchantment;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.ScrollableScissorWindow;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentScreen extends ParentItemScreen {

    // TODO choice button widget
    private ScrollableScissorWindow list;
    private ScrollableScissorWindow added;
    private NumberField level;
    private TagEnchantment selected = null;


    public EnchantmentScreen(Screen lastScreen, DataItem editing) {
        super( new TranslationTextComponent( "gui.enchantment" ), lastScreen, editing );
    }


    public static class EnchantComparator implements Comparator<Enchantment> {
        @Override
        public int compare( Enchantment o1, Enchantment o2 ) {
            return I18n.format( o1.getName() ).compareTo( I18n.format( o2.getName() ) );
        }
    }


    @Override
    protected void init() {
        super.init();

        List<Enchantment> sortedEnchants = Lists.newArrayList( ForgeRegistries.ENCHANTMENTS );
        sortedEnchants.sort( new EnchantComparator() );

        list = addButton( new ScrollableScissorWindow( 100, 100, width / 5, height / 2, I18n.format( "gui.enchantment.enchantments" ) ) );
        for (Enchantment ench : sortedEnchants) {
            list.getWidgets().add( new StyledButton( 0, 0, 50, 20, I18n.format( ench.getName() ) + ' ' + getLevel( ench ), button -> {
                item.getTag().getEnchantments().add( new TagEnchantment( ench, getLevel( ench ) ) );
                System.out.println( "hey" );
            } ) );
        }
    }


    public int getLevel( Enchantment ench ) {
        return ench.getMaxLevel();
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float partialTicks, Color color ) {
        super.backRender( mouseX, mouseY, partialTicks, color );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float partialTicks, Color color ) {
        super.mainRender( mouseX, mouseY, partialTicks, color );

        int number = 5;
        int offset = width / number;
        int i = 1;

        drawString( font, I18n.format( "gui.enchantment.enchantments" ), list.x, list.y - 10, color.getInt() );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float partialTicks, Color color ) {
        super.overlayRender( mouseX, mouseY, partialTicks, color );
    }
}
