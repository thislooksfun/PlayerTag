package com.tlf.playertag.client.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;
import java.util.TreeMap;

import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
@SuppressWarnings("CanBeFinal")
public class GuiSuggestionsBox extends GuiSubBox
{
	private int listStart = 0;
	private int index = 0;
	
	private int maxItems;
	private int listMiddle;
	
	private int itemColor = Colors.rgba(30, 30, 30, 255);
	private int itemSelectedColor = Colors.rgba(10, 10, 10, 255);
	
	private final TreeMap<String, GuiStringBox> items = new TreeMap<String, GuiStringBox>();
	private final GuiStringBox[] rendered;
	private final GuiTextField monitoring;
	private final GuiTagButton up;
	private final GuiTagButton down;
	
	public GuiSuggestionsBox(int width, @SuppressWarnings("SameParameterValue") int maxItems, GuiColorBox parent, GuiTextField monitoring)
	{
		super(width, maxItems * 11, parent);
		this.maxItems = (maxItems < 3 ? 3 : maxItems);
		this.maxItems = (this.maxItems % 2 == 1 ? this.maxItems : this.maxItems + 1); //Ensure there is a middle of the list (odd number of elements)
		this.listMiddle = (this.maxItems - 1) / 2;
		this.color = 0;
		this.monitoring = monitoring;
		this.rendered = new GuiStringBox[maxItems];
		
		this.up = (GuiTagButton)(new GuiTagButton("^", width, 8, this).setTopOffset(-8).setColor(Colors.rgba(40, 40, 40, 255)));
		this.down = (GuiTagButton)(new GuiTagButton("v", width, 9, this).setBottomOffset(-9).setColor(Colors.rgba(40, 40, 40, 255)));
	}
	
	@Override
	public void updateScreenPos(int screenWidth, int screenHeight)
	{
		this.left = this.monitoring.xPosition;
		this.top = this.monitoring.yPosition + this.monitoring.height;
		
		for (GuiStringBox b : this.items.values())
		{
			b.updateScreenPos();
		}
		this.up.updateScreenPos();
		this.down.updateScreenPos();
	}
	
	public boolean onClick(int x, int y, int button)
	{
		if (this.wasClicked(x, y, button))
		{
			for (GuiStringBox box : this.rendered)
			{
				if (box != null && box.wasClicked(x, y, button))
				{
					this.monitoring.setText(box.string);
				}
			}
			this.updateItems();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean wasClicked(int mouseX, int mouseY, int button)
	{
		return this.monitoring.isFocused() && button == 0 && mouseX >= this.left && mouseY >= this.top - (this.listStart > 0 ? this.up.height : 0) && mouseX < this.left + this.width && mouseY < this.top + height + (this.listStart < this.items.size() - listMiddle ? this.down.height : 0);
	}
	
	public void keyPress(char ch, int keycode)
	{
		boolean flag = false;
		if (this.items.size() > 0)
		{
			if (keycode == 28)
			{
				//ENTER
				this.monitoring.setText((((GuiStringBox)this.items.values().toArray()[this.index]).string));
			} else if (keycode == 200)
			{
				//UP
				if (this.index > 0) this.index--;
				flag = true;
			} else if (keycode == 208)
			{
				//DOWN
				if (this.index < this.items.size() - 1) this.index++;
				
				flag = true;
			}
		}
		
		
		if (flag)
		{
			this.updateRendered();
		} else
		{
			if (keycode != 57)
			{
				this.monitoring.textboxKeyTyped(ch, keycode);
			}
			this.updateItems();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updateItems()
	{
		this.items.clear();
		this.index = 0;
		this.listStart = 0;
		
		String start = this.monitoring.getText().toLowerCase().trim();
		Minecraft mc = Minecraft.getMinecraft();
		String playerName = mc.thePlayer.getDisplayName();
		List<EntityPlayer> players = mc.theWorld.playerEntities;
		for (EntityPlayer p : players)
		{
			if ((start.equals("") || p.getCommandSenderName().toLowerCase().startsWith(start)) && !p.getCommandSenderName().toLowerCase().equals(start) && !p.getCommandSenderName().equals(playerName))
			{
				this.items.put(p.getCommandSenderName(), (GuiStringBox)(new GuiStringBox(this.width, this).setString(p.getCommandSenderName()).setDefaultColor(this.itemColor).setSelectedColor(this.itemSelectedColor).setLeftOffset(0)));
			}
		}
		
		for (int i = 0; i < 10; i++)
		{
			this.items.put("" + i, (GuiStringBox)(new GuiStringBox(this.width, this).setString("" + i).setDefaultColor(this.itemColor).setSelectedColor(this.itemSelectedColor).setLeftOffset(0)));
		}
		
		
		this.updateRendered();
	}
	
	private void updateRendered()
	{
		GuiStringBox[] boxes = this.items.values().toArray(new GuiStringBox[this.items.size()]);
		this.listStart = (this.index - this.listMiddle);
		this.listStart = (this.listStart + this.maxItems > boxes.length ? boxes.length - this.maxItems : this.listStart); //Ensure it doesn't draw past the end
		this.listStart = (this.listStart < 0 ? 0 : this.listStart); //Ensure it's at least 0
		
		for (int i = 0; i < maxItems; i++)
		{
			int j = i + listStart;
			
			if (this.monitoring.isFocused() && j < boxes.length)
			{
				this.rendered[i] = boxes[j];
				this.rendered[i].setTopOffset(i * 11).updateScreenPos();
				this.rendered[i].selected = j == this.index;
			} else
			{
				this.rendered[i] = null;
			}
		}
	}
	
	@Override
	public void render()
	{
		if (this.monitoring.isFocused())
		{
			for (int i = 0; i < this.maxItems; i++)
			{
				GuiStringBox b = this.rendered[i];
				if (b == null)
				{
					break;
				}
				b.render();
			}
			if (this.listStart > 0)
			{
				this.up.render();
			}
			if (this.listStart + this.maxItems < this.items.size())
			{
				this.down.render();
			}
		}
	}
}