package com.tlf.playertag.client.gui;

import com.tlf.playertag.client.gui.helper.GuiColorBox;
import com.tlf.playertag.client.gui.helper.GuiSubBox;
import com.tlf.playertag.client.gui.helper.GuiTagButton;
import com.tlf.playertag.client.gui.helper.PlayerDataBox;
import com.tlf.playertag.tracker.PlayerData;
import com.tlf.playertag.tracker.PlayerDataManager;
import com.tlf.playertag.util.Colors;

/**
 * @author thislooksfun
 */
public class GuiTagsBox extends GuiSubBox
{
	private final GuiCurrent main;
	
	private final GuiSubBox bottomBar;
	private final GuiSubBox tagBounds;
	private final GuiTagButton prevPage;
	private final GuiTagButton nextPage;
	private final GuiTagButton remove;
	private final GuiTagButton add;
	
	private int page = 0;
	private int maxPage;
	
	private PlayerDataBox[] boxes = new PlayerDataBox[0];
	
	private static final int itemsPerPage = 11;
	
	@SuppressWarnings("SameParameterValue")
	public GuiTagsBox(int width, int height, GuiColorBox parent, GuiCurrent main)
	{
		super(width, height, parent);
		
		this.main = main;
		
		this.bottomBar = (GuiSubBox)(new GuiSubBox(width, 16, this).setBottomOffset(0).setColor(Colors.rgba(100, 100, 100, 200)));
		this.tagBounds = new GuiSubBox(width, height-this.bottomBar.height(), this);
		this.prevPage = (GuiTagButton)(new GuiTagButton("<", 40, 10, this.bottomBar).setLeftOffset(3).setBottomOffset(3).setColor(Colors.rgba(255, 30, 30, 200)));
		this.nextPage = (GuiTagButton)(new GuiTagButton(">", 40, 10, this.bottomBar).setRightOffset(40).setBottomOffset(3).setColor(Colors.rgba(255, 30, 30, 200)));
		this.remove = (GuiTagButton)(new GuiTagButton("-", 10, 10, this.bottomBar).setRightOffset(15).setBottomOffset(3).setColor(Colors.rgba(255, 30, 30, 200)));
		this.remove.disabled = true;
		this.add = (GuiTagButton)(new GuiTagButton("+", 10, 10, this.bottomBar).setRightOffset(3).setBottomOffset(3).setColor(Colors.rgba(255, 30, 30, 200)));
		
		this.getAndSetData();
	}
	
	public void getAndSetData()
	{
		this.boxes = new PlayerDataBox[PlayerDataManager.size()];
		PlayerData[] datas = PlayerDataManager.valueArray();
		
		String match = "";
		try {
			match = this.main.edit.getPrevData().username;
		} catch (NullPointerException e) {
			//Ignore
		}
		
		for (int i = 0; i < datas.length; i++)
		{
			PlayerData pd = datas[i];
			boxes[i] = (PlayerDataBox)(new PlayerDataBox(this.width - 10, 12, this, pd.clone()).setDefaultColor(Colors.rgba(30, 30, 30, 200)).setSelectedColor(Colors.rgba(10, 10, 10, 200)).setLeftOffset(5).setTopOffset(5 + (16 * (i % itemsPerPage))));
			boxes[i].updateScreenPos(0, 0);
			if (pd.username.equals(match)) {
				boxes[i].selected = true;
				this.remove.disabled = false;
			}
		}
		
		this.maxPage = (int)Math.floor(this.boxes.length / itemsPerPage);
		if (this.page > this.maxPage)
		{
			this.page = this.maxPage;
		}
		
		this.updatePage();
	}
	
	@Override
	public void updateScreenPos(int screenWidth, int screenHeight)
	{
		super.updateScreenPos(screenWidth, screenHeight);
		
		for (PlayerDataBox pdb : this.boxes)
		{
			pdb.updateScreenPos(screenWidth, screenHeight);
		}
		
		this.bottomBar.updateScreenPos(screenWidth, screenHeight);
		this.prevPage.updateScreenPos(screenWidth, screenHeight);
		this.nextPage.updateScreenPos(screenWidth, screenHeight);
		this.remove.updateScreenPos(screenWidth, screenHeight);
		this.add.updateScreenPos(screenWidth, screenHeight);
	}
	
	@Override
	public void render()
	{
		super.render();
		
		int cnt = (this.boxes.length - itemsPerPage * (this.page)) < itemsPerPage ? (this.boxes.length - itemsPerPage * (this.page)) : itemsPerPage;
		for (int i = 0; i < cnt; i++)
		{
			this.boxes[i + (this.page * itemsPerPage)].render();
		}
		
		this.bottomBar.render();
		this.prevPage.render();
		this.nextPage.render();
		this.remove.render();
		this.add.render();
		this.drawString((this.page + 1) + "/" + (this.maxPage + 1), 48, this.height - 12, Colors.WHITE);
	}
	
	private void updatePage()
	{
		this.prevPage.disabled = this.page <= 0;
		this.nextPage.disabled = this.page >= this.maxPage;
	}
	
	public void onClick(int x, int y, int button)
	{
		if (this.tagBounds.wasClicked(x, y, button))
		{
			this.remove.disabled = true;
			for (PlayerDataBox pdb : this.boxes)
			{
				pdb.selected = false;
				if (pdb.wasClicked(x, y, button))
				{
					this.main.edit.setData(pdb.data());
					pdb.selected = true;
					this.remove.disabled = false;
				}
			}
		}
		
		this.getAndSetData();
		
		if (this.prevPage.wasClicked(x, y, button))
		{
			this.page = (this.page > 0 ? this.page - 1 : 0);
			this.updatePage();
		} else if (this.nextPage.wasClicked(x, y, button))
		{
			this.page = (this.page < this.maxPage ? this.page + 1 : this.maxPage);
			this.updatePage();
		} else if (this.remove.wasClicked(x, y, button))
		{
			String username = this.main.edit.getPrevData().username;
			PlayerDataManager.remove(username);
			this.main.edit.setData(null);
			this.getAndSetData();
			this.remove.disabled = true;
		} else if (this.add.wasClicked(x, y, button))
		{
			this.main.edit.setData(new PlayerData("", "", "", false, new String[0]));
		}
	}
}