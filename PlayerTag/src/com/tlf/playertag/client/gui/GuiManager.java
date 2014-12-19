package com.tlf.playertag.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.tlf.playertag.common.PlayerTag;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * @author thislooksfun
 */
public class GuiManager implements IGuiHandler
{
	public GuiManager()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(PlayerTag.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiCurrent();
	}
	
	
	public void displayGui()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiCurrent());
	}
}