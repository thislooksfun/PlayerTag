package com.tlf.playertag.client.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author thislooksfun
 */
public class GuiSubBox extends GuiColorBox
{
	@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
	protected GuiColorBox parent;
	
	private int topOffset = 0;
	private int leftOffset = 0;
	private int rightOffset = 0;
	private int bottomOffset = 0;
	private boolean topBottom = true;
	private boolean leftRight = true;
	
	protected final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	
	public GuiSubBox(int width, int height, GuiColorBox parent)
	{
		super(width, height);
		this.parent = parent;
	}
	
	public GuiSubBox setTopOffset(int topOffset)
	{
		this.topOffset = topOffset;
		this.bottomOffset = 0;
		this.topBottom = true;
		return this;
	}
	public GuiSubBox setLeftOffset(int leftOffset)
	{
		this.leftOffset = leftOffset;
		this.rightOffset = 0;
		this.leftRight = true;
		return this;
	}
	public GuiSubBox setBottomOffset(int bottomOffset)
	{
		this.bottomOffset = bottomOffset;
		this.topOffset = 0;
		this.topBottom = false;
		return this;
	}
	public GuiSubBox setRightOffset(int rightOffset)
	{
		this.rightOffset = rightOffset;
		this.leftOffset = 0;
		this.leftRight = false;
		return this;
	}
	
	protected void drawString(String s, int left, int top, int color)
	{
		this.drawString(this.fontRenderer, s, this.left+left, this.top+top, color);
	}
	protected void drawCenteredString(String s, int left, int top, @SuppressWarnings("SameParameterValue") int color)
	{
		this.drawCenteredString(this.fontRenderer, s, this.left+left, this.top+top, color);
	}
	
	@SuppressWarnings("WeakerAccess")
	public void updateScreenPos()
	{
		int toff = (this.topBottom ? this.topOffset : (this.parent.height - this.bottomOffset - this.height));
		int loff = (this.leftRight ? this.leftOffset : (this.parent.width - this.rightOffset - this.width));
		this.top = this.parent.top + toff;
		this.left = this.parent.left + loff;
	}
	
	@Override
	public void updateScreenPos(int screenWidth, int screenHeight)
	{
		this.updateScreenPos();
	}
	
	@Override
	public void render()
	{
		drawRect(this.left, this.top, this.left + this.width, this.top + this.height, color);
	}
}