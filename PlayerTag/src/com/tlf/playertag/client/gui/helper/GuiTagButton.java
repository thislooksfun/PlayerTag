package com.tlf.playertag.client.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
public class GuiTagButton extends GuiSubBox
{
	public boolean disabled = false;
	
	private String text;
	
	private int disabledColor;
	
	private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	
	public GuiTagButton(String content, int width, int height, GuiColorBox parent)
	{
		super(width, height, parent);
		this.text = content;
	}
	
	@Override
	public GuiColorBox setColor(int color)
	{
		super.setColor(color);
		this.disabledColor = (Colors.rgba((Colors.hexToR(this.color) - 100 < 0 ? 0 : Colors.hexToR(this.color) - 100), (Colors.hexToG(this.color) - 100 < 0 ? 0 : Colors.hexToG(this.color) - 100), (Colors.hexToB(this.color) - 100 < 0 ? 0 : Colors.hexToB(this.color) - 100), Colors.hexToA(this.color)));
		return this;
	}
	
	@Override
	public void render()
	{
		int tempColor = this.color;
		if (this.disabled)
		{
			this.color = this.disabledColor;
		}
		
		super.render();
		this.drawCenteredString(this.fontRenderer, this.text, this.left + (this.width / 2), this.top + (this.height / 2) - (this.fontRenderer.FONT_HEIGHT / 2), this.disabled ? Colors.DISABLED_TEXT_COLOR : Colors.TEXT_COLOR);
		
		this.color = tempColor;
	}
	
	@Override
	public boolean wasClicked(int mouseX, int mouseY, int button)
	{
		return !disabled && super.wasClicked(mouseX, mouseY, button);
	}
}