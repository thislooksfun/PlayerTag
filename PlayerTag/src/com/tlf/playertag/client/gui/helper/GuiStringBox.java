package com.tlf.playertag.client.gui.helper;

import com.tlf.playertag.util.ColorHelper;
import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
public class GuiStringBox extends GuiSubBox
{
	protected int defaultColor;
	protected int selectedColor;
	protected int textColor = Colors.TEXT_COLOR;
	protected String string;
	protected boolean centered = false;
	private int stringTop = 2;
	
	public boolean selected = false;
	
	public GuiStringBox(int width, GuiColorBox parent)
	{
		super(width, 11, parent);
	}
	
	public GuiStringBox(int width, int height, GuiColorBox parent)
	{
		super(width, height, parent);
	}
	
	public GuiStringBox setTextColor(int color)
	{
		this.textColor = color;
		return this;
	}
	
	public GuiStringBox setString(String s)
	{
		this.string = s;
		return this;
	}
	
	public GuiStringBox setCentered(boolean b)
	{
		this.centered = b;
		return this;
	}
	
	public GuiStringBox setStringTop(int top)
	{
		this.stringTop = top;
		return this;
	}
	
	@Override
	public void render()
	{
		this.color = (this.selected ? this.selectedColor : this.defaultColor);
		super.render();
		if (this.centered) {
			this.drawCenteredString(ColorHelper.limitToLengthExcludingCodes(this.string, this.width - 15), this.width / 2, this.stringTop, Colors.TEXT_COLOR);
		} else {
			this.drawString(ColorHelper.limitToLengthExcludingCodes(this.string, this.width - 15), 6, this.stringTop, Colors.TEXT_COLOR);
		}
	}
	
	public GuiStringBox setDefaultColor(int color)
	{
		this.defaultColor = color;
		return this;
	}
	
	public GuiStringBox setSelectedColor(int color)
	{
		this.selectedColor = color;
		return this;
	}
}
