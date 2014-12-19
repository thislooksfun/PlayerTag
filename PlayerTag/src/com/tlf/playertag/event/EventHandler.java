package com.tlf.playertag.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

import com.tlf.playertag.common.PlayerTag;
import com.tlf.playertag.tracker.PlayerDataManager;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author thislooksfun
 */
public class EventHandler
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyPress(InputEvent.KeyInputEvent event)
	{
		if (PlayerTag.keyOpenGui.isPressed() && Minecraft.getMinecraft().currentScreen == null)
		{
			PlayerTag.guiManager.displayGui();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onNameFormat(PlayerEvent.NameFormat event)
	{
		if (PlayerDataManager.containsKey(event.username))
		{
			event.displayname = PlayerDataManager.get(event.username).toString();
		}
	}
	
	
	private static int nextUpdate = 0;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void clientTick(TickEvent.ClientTickEvent event)
	{
		if (nextUpdate <= 0)
		{
			World world = Minecraft.getMinecraft().theWorld;
			if (world != null)
			{
				List<EntityPlayer> playerList = world.playerEntities;
				if (playerList.size() > 1)
				{
					for (EntityPlayer player : playerList)
					{
						player.refreshDisplayName();
					}
				}
			}
			
			nextUpdate = 2;
		} else {
			nextUpdate--;
		}
	}
}