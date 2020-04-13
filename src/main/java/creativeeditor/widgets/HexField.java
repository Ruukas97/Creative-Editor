package creativeeditor.widgets;

import org.lwjgl.glfw.GLFW;

import creativeeditor.data.base.DataColor;
import creativeeditor.styles.Style;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;

public class HexField extends Widget {
    private final FontRenderer fontRenderer;

    @Getter
    DataColor dataColor;
    public char[] digits;

    private char[] allowed = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'a', 'b', 'c', 'd', 'e', 'f' };

    private int cursorCounter;

    private boolean enableBackgroundDrawing = true;
    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    private boolean canLoseFocus = true;

    private int cursorPosition;


    public HexField(FontRenderer font, int x, int y, int height, DataColor color) {
        super( x, y, font.getStringWidth( "#FFFFFF" ) + 8, height, "" );
        this.dataColor = color;
        this.fontRenderer = font;

        this.digits = new char[] { '0', '0', '0', '0', '0', '0' };
        setValue( dataColor.getInt() );
    }


    public static int getMax() {
        return 0xFFFFFF;
    }


    public static int getMin() {
        return 0;
    }


    public String getHexString() {
        return '#' + new String( digits );
    }


    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter() {
        this.cursorCounter++;
    }


    /**
     * Sets the text of the textbox, and moves the cursor to the end.
     */
    public void setDigit( int i, char c ) {
        if (isAllowed( c )) {
            digits[i] = c;
        }

        /*
         * if (getDigitsValue() > getMax()) { String maxStr = (getMax() + ""); int diff
         * = digits.length - maxStr.length(); char mC = maxStr.charAt(
         * this.cursorPosition - diff );
         * 
         * if (c == mC) { this.setValue( getMax() ); } else { this.setDigit( mC ); } }
         * 
         * else if (getDigitsValue() < getMin()) { String minStr = (getMin() + "");
         * 
         * String zeroes = "";
         * 
         * int neededZeroes = digits.length - minStr.length();
         * 
         * for (int z = 0; z < neededZeroes; z++) { zeroes += "0"; }
         * 
         * minStr = zeroes + minStr;
         * 
         * int diff = digits.length - minStr.length(); char mC = minStr.charAt(
         * this.cursorPosition - diff );
         * 
         * if (c == mC) { this.setValue( getMin() ); } else { this.setDigit( mC ); } }
         */
        dataColor.setInt( getDigitsValue() );
    }


    /**
     * Sets the text of the textbox, and moves the cursor to the end.
     */
    public void setDigit( char c ) {
        this.setDigit( cursorPosition, c );
    }


    private void setValue( int value ) {
        value = 0xFFFFFF & value;
        String s = String.format( "%06X", value );

        for (int i = 0; i < s.length() - 1; i++) {
            this.digits[i] = s.charAt( i );
        }
    }


    /**
     * Returns the contents of the textbox
     */
    private int getDigitsValue() {
        try {
            return Integer.decode( "0x" + new String( digits ) );

        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public boolean isAllowed( char c ) {
        for (char ch : allowed) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }


    /**
     * Moves the text cursor by a specified number of characters and clears the
     * selection
     */
    public void moveCursor( boolean right ) {
        this.setCursorPosition( this.cursorPosition + (right ? 1 : -1) );
    }


    /**
     * Sets the current position of the cursor.
     */
    public void setCursorPosition( int pos ) {
        this.cursorPosition = pos;
        int i = digits.length - 1;
        this.cursorPosition = MathHelper.clamp( this.cursorPosition, 0, i );
    }


    /**
     * Moves the cursor to the very start of this text box.
     */
    public void setCursorPositionZero() {
        this.setCursorPosition( 0 );
    }


    /**
     * Moves the cursor to the very end of this text box.
     */
    public void setCursorPositionEnd() {
        this.setCursorPosition( digits.length - 1 );
    }


    @Override
    public boolean keyPressed( int keyCode, int scanCode, int modifier ) {
        switch (keyCode) {
        case GLFW.GLFW_KEY_LEFT:
            moveCursor( false );
            return true;
        case GLFW.GLFW_KEY_RIGHT:
            moveCursor( true );
            return true;
        default:
            return super.keyPressed( keyCode, scanCode, modifier );
        }

    }


    @Override
    public boolean charTyped( char typedChar, int keyCode ) {
        Minecraft mc = Minecraft.getInstance();
        if (!this.isFocused()) {
            return false;
        }
        else if (Screen.isCopy( keyCode )) {
            mc.keyboardListener.setClipboardString( String.valueOf( digits ) );
            return true;
        }
        else if (Screen.isPaste( keyCode )) {
            if (this.active) {
                // TODO pasting
            }

            return true;
        }
        else if (Screen.isCut( keyCode )) {
            mc.keyboardListener.setClipboardString( String.valueOf( digits ) );

            /*
             * if (this.active) { resetDigits(); }
             */

            return true;
        }
        else {
            switch (keyCode) {
            case 14:

                if (this.active) {
                    setDigit( '0' );
                }

                return true;
            case 199:

                setCursorPositionZero();

                return true;
            case 203:

                this.moveCursor( false );

                return true;
            case 205:

                this.moveCursor( true );

                return true;
            case 207:

                this.setCursorPositionEnd();

                return true;
            case 211:

                if (this.active) {
                    setDigit( '0' );
                }

                return true;
            default:
                if (active) {
                    if (isAllowed( typedChar )) {
                        setDigit( Character.toUpperCase( typedChar ) );
                        moveCursor( true );
                    }

                    return true;
                }
                return false;
            }
        }
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        boolean flag = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;

        if (this.canLoseFocus) {
            this.setFocused( flag );
        }

        if (this.isFocused() && flag && mouseButton == 0) {
            int i = (int) mouseX - this.x;

            if (this.enableBackgroundDrawing) {
                i -= 4;
            }

            String s = this.fontRenderer.trimStringToWidth( this.getHexString(), this.getWidth() );
            this.setCursorPosition( this.fontRenderer.trimStringToWidth( s, i ).length() );
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public void renderButton( int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_ ) {
        Style style = StyleManager.getCurrentStyle();
        Color color = style.getFGColor( this );

        if (getDigitsValue() != dataColor.getInt()) {
            setValue( dataColor.getInt() );
        }

        if (this.getEnableBackgroundDrawing()) {
            GuiUtil.drawFrame( x, y, x + width, y + height, 1, color );
        }

        int cursorPos = this.cursorPosition;
        String string = this.fontRenderer.trimStringToWidth( this.getHexString(), this.getWidth() );
        boolean cursorFine = cursorPos >= 0 && cursorPos <= string.length();
        boolean displayCursor = isFocused() && this.cursorCounter / 6 % 2 == 0 && cursorFine;
        int textX = this.enableBackgroundDrawing ? this.x + 4 : this.x;
        int textY = this.enableBackgroundDrawing ? this.y + (this.height - 8) / 2 : this.y;
        int halfX = textX;

        if (!string.isEmpty()) {
            String halfString = cursorFine ? string.substring( 0, cursorPos ) : string;
            halfX = this.fontRenderer.drawStringWithShadow( halfString, (float) textX, (float) textY, dataColor.getInt() ) - 1;
        }

        int cursorX = halfX + (int) Math.ceil( fontRenderer.getCharWidth( '#' ) );

        if (!cursorFine) {
            cursorX = cursorPos > 0 ? textX + this.width : textX;
        }

        if (!string.isEmpty() && cursorFine && cursorPos < string.length()) {
            halfX = this.fontRenderer.drawStringWithShadow( string.substring( cursorPos ), (float) halfX, (float) textY, dataColor.getInt() );
        }

        if (displayCursor) {
            this.fontRenderer.drawStringWithShadow( "_", (float) cursorX, (float) textY, color.getInt() );
        }
    }


    /**
     * returns the current position of the cursor
     */
    public int getCursorPosition() {
        return this.cursorPosition;
    }


    /**
     * Gets whether the background and outline of this text box should be drawn
     * (true if so).
     */
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }


    /**
     * Sets focus to this gui element
     */
    @Override
    public void setFocused( boolean isFocusedIn ) {
        super.setFocused( isFocusedIn );
        if (isFocusedIn) {
            this.cursorCounter = 0;
        }
    }


    /**
     * returns the width of the textbox depending on if background drawing is
     * enabled
     */
    @Override
    public int getWidth() {
        return this.getEnableBackgroundDrawing() ? this.width - 6 : this.width;
    }


    /**
     * Sets whether this text box loses focus when something other than it is
     * clicked.
     */
    public void setCanLoseFocus( boolean canLoseFocusIn ) {
        this.canLoseFocus = canLoseFocusIn;
    }
}
