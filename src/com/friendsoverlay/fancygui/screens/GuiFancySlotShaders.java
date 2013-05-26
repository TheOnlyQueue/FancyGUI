package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

import java.lang.reflect.*;
import java.util.ArrayList;

class GuiFancySlotShaders extends GuiFancySlot
{
	private ArrayList shaderslist;
	final GuiFancyShaders shadersGui;

	public GuiFancySlotShaders(GuiFancyShaders var1)
	{
		super(Minecraft.getMinecraft(), var1.width / 2 + 20, var1.height, 32, var1.height - 60, 18);
		this.shadersGui = var1;
		this.shaderslist = getListOfShaders();
	}

	public void updateList()
	{
		this.shaderslist = getListOfShaders();
	}

	/**
	 * Gets the size of the current slot list.
	 */
	protected int getSize()
	{
		return this.shaderslist.size();
	}

	/**
	 * the element in the slot that was clicked, boolean for wether it was double clicked or not
	 */
	protected void elementClicked(int var1, boolean var2)
	{
		Shaders.setShaderPack((String)this.shaderslist.get(var1));
		this.shadersGui.needReinit = false;
		Shaders.loadShaderPack();
		Shaders.uninit();
		Minecraft.getMinecraft().renderGlobal.loadRenderers();
	}

	/**
	 * returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int var1)
	{
		return (this.shaderslist.get(var1)).equals(FancyShaders.getField("currentshadername"));
	}

	/**
	 * return the height of the content being scrolled
	 */
	protected int getContentHeight()
	{
		return this.getSize() * 18;
	}

	protected void drawBackground()
	{
		this.shadersGui.drawDefaultBackground();
	}

	protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5)
	{
		this.shadersGui.drawCenteredString(Minecraft.getMinecraft().fontRenderer, (String)this.shaderslist.get(var1), this.shadersGui.width / 4 + 10, var3 + 1, 16777215);
	}

	private ArrayList getListOfShaders() {
		ArrayList listofShaders = null;

		try {
			Method m = Shaders.class.getDeclaredMethod("listofShaders");
			m.setAccessible(true);
			listofShaders = (ArrayList) m.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listofShaders;
	}
}
