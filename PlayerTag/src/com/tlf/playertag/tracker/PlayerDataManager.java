package com.tlf.playertag.tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tlf.playertag.util.ColorHelper;
import com.tlf.playertag.util.LogHelper;
import org.apache.commons.io.FileUtils;

/**
 * @author thislooksfun
 */
public class PlayerDataManager
{
	private static File file;
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	//All the PlayerData instances
	private static final SortedMap<String, PlayerData> PLAYER_DATA = new TreeMap<String, PlayerData>(new Comparator<String>()
	{
		public int compare(String o1, String o2)
		{
			return o1.toLowerCase().compareTo(o2.toLowerCase());
		}
	});
	
	public static int size()
	{
		return PLAYER_DATA.size();
	}
	
	public static void add(PlayerData data)
	{
		PLAYER_DATA.put(data.username, data);
		forceNameRefresh(data.username);
		save();
	}
	
	public static void remove(String username)
	{
		PLAYER_DATA.remove(username);
		forceNameRefresh(username);
		save();
	}
	
	public static void forceNameRefresh(String username)
	{
		EntityPlayer p = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(username);
		if (p != null)
		{
			p.refreshDisplayName();
		}
	}
	
	public static PlayerData[] valueArray()
	{
		return PLAYER_DATA.values().toArray(new PlayerData[size()]);
	}
	
	public static boolean containsKey(String username)
	{
		return PLAYER_DATA.containsKey(username);
	}
	
	public static PlayerData get(String username)
	{
		return PLAYER_DATA.get(username);
	}
	
	public static void save()
	{
		if (file == null)
		{
			file = new File("config/playertag.json");
			file.getParentFile().mkdirs();
		}
		
		try
		{
			JsonObject main = new JsonObject();
			
			for (PlayerData data : PLAYER_DATA.values())
			{
				JsonObject o = new JsonObject();
				
				o.addProperty("prefix", ColorHelper.unformat(data.prefix));
				o.addProperty("suffix", ColorHelper.unformat(data.suffix));
				o.addProperty("overrideTeam", data.overrideTeamColor);
				
				main.add(data.username, o);
			}
			
			FileUtils.writeStringToFile(file, gson.toJson(main).replace("\\u0026", "&"));
		} catch (FileNotFoundException e)
		{
			LogHelper.error("Save file not found!");
		} catch (IOException e)
		{
			LogHelper.error("Problem saving!");
		}
	}
	
	public static void read()
	{
		if (file == null)
		{
			file = new File("config/playertag.json");
			file.getParentFile().mkdirs();
		}
		
		try
		{
			String data = FileUtils.readFileToString(file);
			
			Type mapType = new TypeToken<Map<String,Map<String, String>>>() {}.getType();
			Map<String, Map<String, Object>> map = gson.fromJson(data, mapType);
			
			for (Map.Entry<String, Map<String, Object>> e : map.entrySet())
			{
				Map<String, Object> val = e.getValue();
				
				PLAYER_DATA.put(e.getKey(), new PlayerData(e.getKey(), (String)val.get("prefix"), (String)val.get("suffix"), Boolean.parseBoolean((String)val.get("overrideTeam")), new String[0]));
			}
		} catch (FileNotFoundException e)
		{
			LogHelper.error("Save file not found! &&");
		} catch (IOException e)
		{
			LogHelper.error("Problem saving!");
		}
	}
}