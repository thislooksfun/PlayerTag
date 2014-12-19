package com.tlf.playertag.client.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author thislooksfun
 */
public class GuiSubBox extends GuiColorBox
{
	protected GuiColorBox parent;
	
	private int topOffset = -1;
	private int leftOffset = -1;
	private int rightOffset = -1;
	private int bottomOffset = -1;
	
	protected final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	
	public GuiSubBox(int width, int height, GuiColorBox parent)
	{
		super(width, height);
		this.parent = parent;
	}
	
	public GuiSubBox setTopOffset(int topOffset)
	{
		this.topOffset = topOffset;
		this.bottomOffset = -1;
		return this;
	}
	public GuiSubBox setLeftOffset(int leftOffset)
	{
		this.leftOffset = leftOffset;
		this.rightOffset = -1;
		return this;
	}
	public GuiSubBox setBottomOffset(int bottomOffset)
	{
		this.bottomOffset = bottomOffset;
		this.topOffset = -1;
		return this;
	}
	public GuiSubBox setRightOffset(int rightOffset)
	{
		this.rightOffset = rightOffset;
		this.leftOffset = -1;
		return this;
	}
	
	protected void drawString(String s, int l, int t, int color)
	{
		this.drawString(this.fontRenderer, s, this.left+l, this.top+t, color);
	}
	protected void drawCenteredString(String s, int l, int t, int color)
	{
		this.drawCenteredString(this.fontRenderer, s, this.left+l, this.top+t, color);
	}
	
	@Override
	public void updateScreenPos(int screenWidth, int screenHeight)
	{
		int toff = (this.bottomOffset > -1 ? (this.parent.height-this.bottomOffset-this.height) : (this.topOffset > -1 ? this.topOffset : 0));
		int loff = (this.rightOffset > -1 ? (this.parent.width-this.rightOffset-this.width) : (this.leftOffset > -1 ? this.leftOffset : 0));
		this.top = this.parent.top + toff;
		this.left = this.parent.left + loff;
	}
	
	@Override
	public void render()
	{
		drawRect(this.left, this.top, this.left + this.width, this.top + this.height, color);
	}
}