package com.tlf.playertag.tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author thislooksfun
 */
public class PlayerDataManager
{
	//All the PlayerData instances
	private static final SortedMap<String, PlayerData> PLAYER_DATA = new TreeMap<String, PlayerData>(new Comparator<String>() {
		public int compare(String o1, String o2) {
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
	}
	
	public static void remove(String username)
	{
		PLAYER_DATA.remove(username);
		forceNameRefresh(username);
	}
	
	public static void forceNameRefresh(String username)
	{
		EntityPlayer p = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(username);
		if (p != null) {
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
}