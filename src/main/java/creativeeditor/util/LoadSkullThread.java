package creativeeditor.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import creativeeditor.screen.HeadScreen.HeadCategories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LoadSkullThread implements Runnable {

	@Override
	public void run() {
		try {
			String linkAPI = "https://minecraft-heads.com/scripts/api.php?cat=";
			// for (HeadCategories heads : HeadCategories.values()) {
			HeadCategories heads = HeadCategories.alphabet;
			URL url = new URL(linkAPI + heads.name());
			InputStream st = url.openStream();
			Scanner s = new Scanner(st);
			String line = s.nextLine();
			s.close();
			st.close();
			if (line == null || line.length() < 1) {
				return;
			}
			JsonParser parser = new JsonParser();
			JsonArray array = (JsonArray) parser.parse(line);
			// for (JsonElement e : array) {
			JsonElement e = array.get(0);
			ItemStack skull = new ItemStack(Items.PLAYER_HEAD);
			JsonObject ob = e.getAsJsonObject();
			PlayerHead ph = new PlayerHead(skull);
			ph.setTexture(ob.get("value").getAsString());
			// }
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
