package creativeeditor.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagGameProfile;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MinecraftHeads {
    public static final String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";


    public static List<ItemStack> loadCategory( MinecraftHeadsCategory category ) {
        return loadCategory( new LinkedList<ItemStack>(), category );
    }


    public static List<ItemStack> loadCategory( List<ItemStack> list, MinecraftHeadsCategory category ) {
        try {
            InputStreamReader reader = new InputStreamReader( category.getURL().openStream() );
            Gson gson = new Gson();
            MinecraftHeadsResponse[] response = gson.fromJson( reader, MinecraftHeadsResponse[].class );
            reader.close();

            for (MinecraftHeadsResponse element : response) {
                DataItem head = new DataItem( new ItemStack( Items.PLAYER_HEAD ) );
                head.getDisplayNameTag().set( element.getName() );
                head.getTag().setSkullOwner( new TagGameProfile( element ) );
                list.add( head.getItemStack() );
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
