package com.tlf.playertag.tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

import com.tlf.playertag.util.ColorHelper;

/**
 * @author thislooksfun
 */
public class PlayerData
{
	public String username;
	public String prefix;
	public String suffix;
	public boolean overrideTeamColor;
	public String[] servers;
	
	public PlayerData(String username, String prefix, String suffix, boolean overrideTeamColor, String[] servers)
	{
		this.username = username.trim();
		this.prefix = prefix;
		this.suffix = suffix;
		this.overrideTeamColor = overrideTeamColor;
		this.servers = servers;
		
		for (Object player : Minecraft.getMinecraft().theWorld.playerEntities)
		{
			if (((EntityPlayer)player).getCommandSenderName().equalsIgnoreCase(this.username)) {
				this.username = ((EntityPlayer)player).getCommandSenderName();
			}
		}
	}
	
	public String overrideWith()
	{
		return this.overrideTeamColor ? ("\u00a7r" + ColorHelper.getEffectiveEndCodes(this.prefix)) : "";
	}
	
	@Override
	public PlayerData clone()
	{
		return new PlayerData(this.username, this.prefix, this.suffix, this.overrideTeamColor, this.servers);
	}
	
	public String toString(String username)
	{
		username = username.trim();
		
		EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByName(username);
		
		String user = this.overrideWith() + username;
		if (player != null && player.getTeam() != null)
		{
			user = ColorHelper.getEffectiveEndCodes(((ScorePlayerTeam)player.getTeam()).getColorPrefix()) + user;
		}
		return ColorHelper.format("&r" + prefix + user + suffix);
	}
	
	@Override
	public String toString()
	{
		return this.toString(this.username);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof PlayerData))
		{
			return false;
		}
		PlayerData pd = (PlayerData)obj;
		
		return pd.username.equals(this.username) && pd.prefix.equals(this.prefix) && pd.suffix.equals((this.suffix)) && pd.overrideTeamColor == this.overrideTeamColor && this.serversMatch(pd.servers);
	}
	
	private boolean serversMatch(String[] check)
	{
		if (check.length != this.servers.length)
		{
			return false;
		}
		
		for (int i = 0; i < check.length; i++)
		{
			if (!check[i].equals(this.servers[i]))
			{
				return false;
			}
		}
		
		return true;
	}
}