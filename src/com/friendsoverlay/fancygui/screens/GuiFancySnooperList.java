package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.src.*;

public class GuiFancySnooperList extends GuiFancySlot {
	final GuiFancySnooper snooperGui;

	public GuiFancySnooperList(GuiFancySnooper par1GuiSnooper)
	{
		super(par1GuiSnooper.mc, par1GuiSnooper.width, par1GuiSnooper.height, 80, par1GuiSnooper.height - 40, par1GuiSnooper.fontRenderer.FONT_HEIGHT + 1);
		this.snooperGui = par1GuiSnooper;
	}

	/**
	 * Gets the size of the current slot list.
	 */
	protected int getSize()
	{
		return GuiFancySnooper.func_74095_a(this.snooperGui).size();
	}

	/**
	 * the element in the slot that was clicked, boolean for wether it was double clicked or not
	 */
	protected void elementClicked(int par1, boolean par2) {}

	/**
	 * returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int par1)
	{
		return false;
	}

	protected void drawBackground() {}

	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		this.snooperGui.fontRenderer.drawString((String)GuiFancySnooper.func_74095_a(this.snooperGui).get(par1), 10, par3, 16777215);
		this.snooperGui.fontRenderer.drawString((String)GuiFancySnooper.func_74094_b(this.snooperGui).get(par1), 230, par3, 16777215);
	}

	protected int getScrollBarX()
	{
		return this.snooperGui.width - 10;
	}
}
