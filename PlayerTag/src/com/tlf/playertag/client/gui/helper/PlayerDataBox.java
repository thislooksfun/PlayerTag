package com.tlf.playertag.client.gui.helper;

import com.tlf.playertag.tracker.PlayerData;
import com.tlf.playertag.util.ColorHelper;
import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
public class PlayerDataBox extends GuiSubBox
{
	private PlayerData data;
	
	private int defaultColor;
	private int selectedColor;
	
	public boolean selected = false;
	
	public PlayerDataBox(int width, int height, GuiColorBox parent, PlayerData data)
	{
		super(width, height, parent);
		this.data = data;
	}
	
	@Override
	public void render()
	{
		this.color = (this.selected ? this.selectedColor : this.defaultColor);
		super.render();
		this.drawString(ColorHelper.limitToLengthExcludingCodes(this.data.toString(), this.width-15), 6, 2, Colors.TEXT_COLOR); //TODO Limit string length
	}
	
	public PlayerData data()
	{
		return this.data;
	}
	
	public PlayerDataBox setDefaultColor(int color)
	{
		this.defaultColor = color;
		return this;
	}
	
	public PlayerDataBox setSelectedColor(int color)
	{
		this.selectedColor = color;
		return this;
	}
}