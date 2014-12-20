package com.tlf.playertag.client.gui.helper;

import net.minecraft.client.gui.Gui;

/**
 * @author thislooksfun
 */
@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class GuiColorBox extends Gui
{
	protected float centerX;
	protected float centerY;
	
	protected int left;
	protected int top;
	
	protected int width;
	protected int height;
	
	protected int color;
	
	public GuiColorBox(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	@SuppressWarnings("SameParameterValue")
	public GuiColorBox setCenteredAt(float x, float y)
	{
		this.centerX = x;
		this.centerY = y;
		return this;
	}
	
	
	public GuiColorBox setColor(int color)
	{
		this.color = color;
		return this;
	}
	
	public void updateScreenPos(int screenWidth, int screenHeight)
	{
		this.left = (int)((screenWidth-this.width)*this.centerX);
		this.top = (int)((screenHeight-this.height)*this.centerY);
	}
	
	public int top()
	{
		return this.top;
	}
	public int bottom()
	{
		return this.top + this.height;
	}
	public int width()
	{
		return this.width;
	}
	public int height()
	{
		return this.height;
	}
	
	public void render()
	{
		drawRect(this.left, this.top, this.left + this.width, this.top + this.height, color);
	}
	
	public boolean wasClicked(int mouseX, int mouseY, int button)
	{
		return button == 0 && mouseX >= this.left && mouseY >= this.top && mouseX < this.left + this.width && mouseY < this.top + height;
	}
}