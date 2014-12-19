package com.tlf.playertag.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.tlf.playertag.client.gui.helper.GuiColorBox;
import com.tlf.playertag.client.gui.helper.GuiSubBox;
import com.tlf.playertag.client.gui.helper.GuiTagButton;
import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
public class GuiCurrent extends GuiScreen
{
	private final GuiColorBox main = new GuiColorBox(350, 235).setCenteredAt(0.5f, 0.5f).setColor(Colors.rgba(20, 20, 20, 230));
	private final GuiTagsBox current = (GuiTagsBox)(new GuiTagsBox(150, 197, this.main, this).setLeftOffset(15).setTopOffset(20).setColor(Colors.rgba(50, 50, 50, 200)));
	public final GuiEditBox edit = (GuiEditBox)(new GuiEditBox(150, 197, this.main, this).setRightOffset(15).setTopOffset(20).setColor(Colors.rgba(50, 50, 50, 200)));
	private final GuiSubBox colorBackground = (GuiSubBox)(new GuiSubBox(this.main.width()-4, 11, this.main).setLeftOffset(2).setBottomOffset(2).setColor(Colors.rgba(100, 100, 100, 200)));
	private final GuiTagButton close = (GuiTagButton)(new GuiTagButton("x", 10, 9, this.main).setTopOffset(2).setRightOffset(2).setColor(Colors.rgba(255, 30, 30, 200)));
	
	private static final String colorCodes = "\u00a70&0 " + "\u00a71&1 " + "\u00a72&2 " + "\u00a73&3 " + "\u00a74&4 " + "\u00a75&5 " + "\u00a76&6 " + "\u00a77&7 " + "\u00a78&8 " + "\u00a79&9 " + "\u00a7a&a " + "\u00a7b&b " + "\u00a7c&c " + "\u00a7d&d " + "\u00a7e&e " + "\u00a7f&f " + "\u00a7k&k\u00a7r " + "\u00a7l&l\u00a7r " + "\u00a7m&m" + "\u00a7r \u00a7n&n" + "\u00a7r \u00a7o&o " + "\u00a7r&r";
	
	@Override
	public void setWorldAndResolution(Minecraft par1Minecraft, int screenWidth, int screenHeight)
	{
		//this.guiLeft = (par2 - this.xSize)/2;
		//this.guiTop = (par3 - this.ySize)/2;
		
//		this.getAndSetElements();
		
		this.main.updateScreenPos(screenWidth, screenHeight);
		this.current.updateScreenPos(screenWidth, screenHeight);
		this.edit.updateScreenPos(screenWidth, screenHeight);
		this.close.updateScreenPos(screenWidth, screenHeight);
		this.colorBackground.updateScreenPos(screenWidth, screenHeight);
		
		super.setWorldAndResolution(par1Minecraft, screenWidth, screenHeight);
	}
	
	@Override
	public void drawScreen(int p1, int p2, float p3)
	{
		this.drawDefaultBackground();
		this.main.render();
		this.current.render();
		this.edit.render();
		this.close.render();
		this.drawCenteredString(this.fontRendererObj, "Manage Tags", this.width / 2, this.main.top() + 5, Colors.rgb(100, 230, 230));
		this.colorBackground.render();
		this.drawCenteredString(this.fontRendererObj, colorCodes, this.width / 2, this.main.bottom()-12, Colors.rgb(244, 244, 244));
		
		super.drawScreen(p1, p2, p3);
	}
	
	@Override
	protected void keyTyped(char ch, int keycode)
	{
		super.keyTyped(ch, keycode);
		this.edit.keyPress(ch, keycode);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button)
	{
		this.current.onClick(x, y, button);
		this.edit.onClick(x, y, button);
		
		if (this.close.wasClicked(x, y, button))
		{
			this.mc.setIngameFocus();
		}
	}
	
	public void update()
	{
		this.current.getAndSetData();
	}
}