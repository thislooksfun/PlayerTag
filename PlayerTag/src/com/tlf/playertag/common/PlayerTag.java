package com.tlf.playertag.common;

import net.minecraft.client.settings.KeyBinding;

import com.tlf.playertag.client.gui.GuiManager;
import com.tlf.playertag.event.EventHandler;
import com.tlf.playertag.tracker.PlayerDataManager;
import com.tlf.playertag.util.LogHelper;
import com.tlf.playertag.util.Settings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/**
 * @author thislooksfun
 */
@SuppressWarnings({"UnusedParameters", "UnusedDeclaration"})
@Mod(name = Settings.NAME, modid = Settings.MODID, version = Settings.VERSION)
public class PlayerTag
{
	public static GuiManager guiManager;
	
	@Mod.Instance(Settings.MODID)
	public static PlayerTag instance;
	
	@SideOnly(Side.CLIENT)
	public static KeyBinding keyOpenGui;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LogHelper.init(event.getModLog());
		LogHelper.info("Pre-Init!");
		
		if (event.getSide().isClient())
		{
			keyOpenGui = new KeyBinding("Manage Tags", Keyboard.KEY_C, "playertag.opengui");
			ClientRegistry.registerKeyBinding(keyOpenGui);
			
			guiManager = new GuiManager();
		}
		
		//S3EPacketTeams
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		LogHelper.info("Init!");
		EventHandler ev = new EventHandler();
		FMLCommonHandler.instance().bus().register(ev);
		MinecraftForge.EVENT_BUS.register(ev);
		PlayerDataManager.read();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		LogHelper.info("Post-Init!");
		LogHelper.info(Settings.ident() + " Loaded!");
	}
}