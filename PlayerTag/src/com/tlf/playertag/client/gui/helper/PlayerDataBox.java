package com.tlf.playertag.client.gui.helper;

import com.tlf.playertag.tracker.PlayerData;
import com.tlf.playertag.util.ColorHelper;

/**
 * @author thislooksfun
 */
public class PlayerDataBox extends GuiStringBox
{
	private final PlayerData data;
	
	@SuppressWarnings("SameParameterValue")
	public PlayerDataBox(int width, int height, GuiColorBox parent, PlayerData data)
	{
		super(width, height, parent);
		this.data = data;
		this.string = ColorHelper.limitToLengthExcludingCodes(this.data.toString(), this.width-15);
	}
	
	public PlayerData data()
	{
		return this.data;
	}
}