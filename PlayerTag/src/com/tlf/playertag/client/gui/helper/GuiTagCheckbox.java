package com.tlf.playertag.client.gui.helper;

import net.minecraft.client.Minecraft;

import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
@SuppressWarnings("CanBeFinal")
public class GuiTagCheckbox extends GuiSubBox
{
	@SuppressWarnings("WeakerAccess")
	public boolean disabled = false;
	public boolean checked = false;
	
	private int outerColor = Colors.rgba(160, 160, 160, 255);
	private int innerColor = Colors.toRgba(Colors.BLACK);
	
	public GuiTagCheckbox(GuiColorBox parent)
	{
		super(12, 12, parent);
	}
	
	@Override
	public void render()
	{
		drawRect(this.left, this.top, this.left + this.width, this.top + this.height, outerColor);
		drawRect(this.left + 1, this.top + 1, this.left + this.width - 1, this.top + this.height - 1, innerColor);
		
		if (checked)
		{
			this.drawString(Minecraft.getMinecraft().fontRenderer, "x", this.left+3, this.top+1, this.disabled ? Colors.DISABLED_TEXT_COLOR : Colors.TEXT_COLOR);
		}
	}
	
	public void onClick(int mouseX, int mouseY, int button)
	{
		if (!this.disabled && this.wasClicked(mouseX, mouseY, button))
		{
			this.checked = !this.checked;
		}
	}
}