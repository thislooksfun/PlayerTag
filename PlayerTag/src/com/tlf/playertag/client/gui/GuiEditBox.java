package com.tlf.playertag.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

import com.tlf.playertag.client.gui.helper.*;
import com.tlf.playertag.tracker.PlayerData;
import com.tlf.playertag.tracker.PlayerDataManager;
import com.tlf.playertag.util.ColorHelper;
import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
public class GuiEditBox extends GuiSubBox
{
	private final GuiCurrent main;
	
	private final GuiColorBox disabledMask;
	private final GuiColorBox bottomBar;
	private final GuiTagButton save;
	private final GuiTagButton cancel;
	
	private final GuiTextField name;
	private final GuiSuggestionsBox nameSuggest;
	private final GuiTextField prefix;
	private final GuiTextField suffix;
	private final GuiTagCheckbox overrideTeamColor;
	
	private boolean focused = false;
	private boolean shouldFocusName = false;
	
	private PlayerData prevData;
	private PlayerData data;
	
	private static final String playerName = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
	
	@SuppressWarnings("SameParameterValue")
	public GuiEditBox(int width, int height, GuiColorBox parent, GuiCurrent main)
	{
		super(width, height, parent);
		
		this.main = main;
		
		this.disabledMask = new GuiSubBox(width, height, this).setColor(Colors.rgba(20, 20, 20, 170));
		this.bottomBar = new GuiSubBox(width, 16, this).setBottomOffset(0).setColor(Colors.rgba(100, 100, 100, 200));
		this.save = (GuiTagButton)(new GuiTagButton("Save", 50, 10, this.bottomBar).setLeftOffset(3).setBottomOffset(3).setColor(Colors.rgba(255, 30, 30, 200)));
		this.cancel = (GuiTagButton)(new GuiTagButton("Cancel", 50, 10, this.bottomBar).setRightOffset(3).setBottomOffset(3).setColor(Colors.rgba(255, 30, 30, 200)));
		
		
		this.name = new GuiTextField(this.fontRenderer, 0, 0, 100, 14);
		this.name.setMaxStringLength(50);
		this.nameSuggest = new GuiSuggestionsBox(this.name.width, 5, this, this.name);
		
		this.prefix = new GuiTextField(this.fontRenderer, 0, 0, 100, 14);
		this.prefix.setMaxStringLength(50);
		
		this.suffix = new GuiTextField(this.fontRenderer, 0, 0, 100, 14);
		this.suffix.setMaxStringLength(50);
		
		this.overrideTeamColor = (GuiTagCheckbox)(new GuiTagCheckbox(this).setTopOffset(134).setLeftOffset(24));
	}
	
	public void setData(PlayerData data)
	{
		if (data == null)
		{
			this.prevData = this.data = null;
			
			this.name.setText("");
			this.prefix.setText("");
			this.suffix.setText("");
			
			this.name.setFocused(false);
			this.nameSuggest.updateItems();
			this.prefix.setFocused(false);
			this.suffix.setFocused(false);
			
			this.overrideTeamColor.checked = false;
			
			this.focused = false;
			return;
		}
		
		this.prevData = data.clone();
		this.data = data.clone();
		
		this.name.setText(data.username);
		this.prefix.setText(data.prefix);
		this.suffix.setText(data.suffix);
		this.overrideTeamColor.checked = data.overrideTeamColor;
		
		this.shouldFocusName = true;
		
		this.focused = true;
	}
	
	public PlayerData getPrevData()
	{
		return this.prevData;
	}
	
	@Override
	public void updateScreenPos(int screenWidth, int screenHeight)
	{
		super.updateScreenPos(screenWidth, screenHeight);
		
		this.disabledMask.updateScreenPos(screenWidth, screenHeight);
		this.bottomBar.updateScreenPos(screenWidth, screenHeight);
		this.save.updateScreenPos(screenWidth, screenHeight);
		this.cancel.updateScreenPos(screenWidth, screenHeight);
		this.overrideTeamColor.updateScreenPos(screenWidth, screenHeight);
		
		
		this.name.xPosition = (this.left + ((this.width - this.name.width) / 2));
		this.name.yPosition = (this.top + 30);
		
		this.prefix.xPosition = (this.left + ((this.width - this.prefix.width) / 2));
		this.prefix.yPosition = (this.top + 65);
		
		this.suffix.xPosition = (this.left + ((this.width - this.suffix.width) / 2));
		this.suffix.yPosition = (this.top + 100);
		
		this.nameSuggest.updateScreenPos(screenWidth, screenHeight);
	}
	
	@Override
	public void render()
	{
		super.render();
		
		if (this.shouldFocusName)
		{
			this.name.setFocused(true);
			this.nameSuggest.updateItems();
			this.prefix.setFocused(false);
			this.suffix.setFocused(false);
			this.shouldFocusName = false;
		}
		
		this.drawCenteredString("Edit tag", this.width / 2, 5, Colors.TEXT_COLOR);
		this.bottomBar.render();
		this.save.render();
		this.cancel.render();
		this.drawString("Username:", 5, 19, Colors.TEXT_COLOR);
		this.name.drawTextBox();
		this.drawString("Prefix:", 5, 54, Colors.TEXT_COLOR);
		this.prefix.drawTextBox();
		this.drawString("Suffix:", 5, 89, Colors.TEXT_COLOR);
		this.suffix.drawTextBox();
		this.drawString("Override Team Color:", 5, 124, Colors.TEXT_COLOR);
		this.overrideTeamColor.render();
		this.drawString("Preview:", 5, 157, Colors.TEXT_COLOR);
		this.drawString(ColorHelper.limitToLengthExcludingCodes(this.data == null ? "" : this.data.toString(), this.width-10), 5, 168, Colors.TEXT_COLOR);
		
		this.nameSuggest.render();
		
		if (!this.focused)
		{
			this.disabledMask.render();
		}
	}
	
	public void keyPress(char ch, int keycode)
	{
		if (this.focused)
		{
			if (keycode == 15)
			{
				int selected = 0;
				if (this.name.isFocused())
				{
					selected = 1;
				} else if (this.prefix.isFocused())
				{
					selected = 2;
				} else if (this.suffix.isFocused())
				{
					selected = 0;
				}
				
				this.name.setFocused(false);
				this.prefix.setFocused(false);
				this.suffix.setFocused(false);
				
				switch (selected)
				{
					case 0:
						this.name.setFocused(true);
						break;
					case 1:
						this.prefix.setFocused(true);
						break;
					case 2:
						this.suffix.setFocused(true);
						break;
				}
				this.nameSuggest.updateItems();
			} else
			{
				if (keycode != 200 && keycode != 208)
				{
					this.prefix.textboxKeyTyped(ch, keycode);
					this.suffix.textboxKeyTyped(ch, keycode);
				}
				
				this.nameSuggest.keyPress(ch, keycode);
				
				this.data.username = this.name.getText();
				this.data.prefix = this.prefix.getText();
				this.data.suffix = this.suffix.getText();
				
				this.save.disabled = (this.data.username.equals("") || this.data.equals(this.prevData) || this.data.username.equals(playerName));
			}
		}
	}
	
	public void onClick(int x, int y, int button)
	{
		if (this.focused)
		{
			this.name.mouseClicked(x, y, button);
			this.prefix.mouseClicked(x, y, button);
			this.suffix.mouseClicked(x, y, button);
			this.overrideTeamColor.onClick(x, y, button);
			
			this.nameSuggest.onClick(x, y, button);
			
			this.data.overrideTeamColor = this.overrideTeamColor.checked;
			this.save.disabled = (this.data.username.equals("") || this.data.equals(this.prevData) || this.data.username.equals(playerName));
			
			if (this.save.wasClicked(x, y, button))
			{
				PlayerDataManager.remove(this.prevData.username);
				PlayerDataManager.add(this.data.clone());
				this.prevData = this.data.clone();
				this.save.disabled = true;
				this.main.update();
			}
			if (this.cancel.wasClicked(x, y, button))
			{
				this.data = this.prevData.clone();
				this.name.setText(this.data.username);
				this.prefix.setText(this.data.prefix);
				this.suffix.setText(this.data.suffix);
				this.overrideTeamColor.checked = this.data.overrideTeamColor;
			}
		}
	}
}