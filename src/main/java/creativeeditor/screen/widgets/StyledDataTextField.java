package creativeeditor.screen.widgets;

import creativeeditor.data.Data;
import creativeeditor.data.base.DataString;
import creativeeditor.data.base.DataTextComponent;
import creativeeditor.screen.DataController;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.StringTextComponent;

public class StyledDataTextField extends StyledTextField implements DataController {
    private Data<?, StringNBT> data;


    public StyledDataTextField(FontRenderer fontIn, int x, int y, int width, int height, Data<?, StringNBT> data) {
        super( fontIn, x, y, width, height, data.getNBT().getString() );
        this.data = data;
        if (data instanceof DataTextComponent) {
            DataTextComponent dataT = (DataTextComponent) data;
            this.text = dataT.getFormatted();

        }
        else if (data instanceof DataString) {
            DataString dataS = (DataString) data;
            this.text = dataS.get();
        }
        setMaxStringLength( 1000 );
    }


    /**
     * Sets the maximum length for the text in this text box. If the current text is
     * longer than this length, the current text will be trimmed.
     */
    public void setMaxStringLength( int length ) {
        this.maxStringLength = length;
        if (this.text.length() > length) {
            setTag( this.text.substring( 0, length ) );
            this.triggerResponder( this.text );
        }
    }


    /**
     * Adds the given text after the cursor, or replaces the currently selected text
     * if there is a selection.
     */
    public void writeText( String textToWrite ) {
        String s = "";
        String s1 = SharedConstants.filterAllowedCharacters( textToWrite );
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLength - this.text.length() - (i - j);
        if (!this.text.isEmpty()) {
            s = s + this.text.substring( 0, i );
        }

        int l;
        if (k < s1.length()) {
            s = s + s1.substring( 0, k );
            l = k;
        }
        else {
            s = s + s1;
            l = s1.length();
        }

        if (!this.text.isEmpty() && j < this.text.length()) {
            s = s + this.text.substring( j );
        }

        if (this.validator.test( s )) {
            setTag( s );
            this.setCursorPos( i + l );
            this.setSelectionPos( this.cursorPosition );
            this.triggerResponder( this.text );
        }
    }


    /**
     * Deletes the given number of characters from the current cursor's position,
     * unless there is currently a selection, in which case the selection is deleted
     * instead.
     */
    public void deleteFromCursor( int num ) {
        if (this.text.isEmpty())
            return;

        if (this.selectionEnd != this.cursorPosition) {
            this.writeText( "" );
        }
        else {
            boolean flag = num < 0;
            int i = flag ? this.cursorPosition + num : this.cursorPosition;
            int j = flag ? this.cursorPosition : this.cursorPosition + num;
            String s = "";
            if (i >= 0) {
                s = this.text.substring( 0, i );
            }

            if (j < this.text.length()) {
                s = s + this.text.substring( j );
            }

            if (this.validator.test( s )) {
                setTag( s );
                if (flag) {
                    this.moveCursorBy( num );
                }

                this.triggerResponder( this.text );
            }
        }
    }


    /**
     * Sets the text of the textbox, and moves the cursor to the end.
     */
    public void setText( String textIn ) {
        if (this.validator.test( textIn )) {
            setTag( textIn.length() > this.maxStringLength ? textIn.substring( 0, this.maxStringLength ) : textIn );
            this.setCursorPositionEnd();
            this.setSelectionPos( this.cursorPosition );
            this.triggerResponder( textIn );
        }
    }


    private void setTag( String text ) {
        if (data instanceof DataTextComponent) {
            DataTextComponent dataT = (DataTextComponent) data;
            dataT.set( new StringTextComponent( text ) );
            this.text = dataT.getFormatted();

        }
        else if (data instanceof DataString) {
            DataString dataS = (DataString) data;
            dataS.set( text );
            this.text = dataS.get();
        }
    }


    @Override
    public Data<?, ?> getDataTag() {
        return data;
    }
}
