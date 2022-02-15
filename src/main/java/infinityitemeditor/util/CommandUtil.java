package infinityitemeditor.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.item.ItemStack;

public class CommandUtil {
    public ItemStack getStackFromCommand(String command) throws CommandSyntaxException {
        if (!command.startsWith("/give ")) {
            Minecraft.getInstance().gui.getChat().addMessage(new TextComponent("Must have a /give command in clipboard to paste item!"));
            return ItemStack.EMPTY;
        }
        command = command.substring(6);
        int spaceIndex = command.indexOf(' ');
        if (spaceIndex == -1) {
            return ItemStack.EMPTY;
        }

        StringReader reader = new StringReader(command.substring(spaceIndex + 1));
        ItemInput item = ItemArgument.item().parse(reader);

        if (reader.canRead(1)) {
            reader.skipWhitespace();
        }

        int count = reader.canRead() ? IntegerArgumentType.integer(1).parse(reader) : 1;
        return item.createItemStack(count, false);
    }
}
