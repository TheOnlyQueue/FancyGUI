package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.src.*;
import org.lwjgl.opengl.*;

import java.util.*;

public class GuiFancyTexturePackSlot extends GuiFancySlot {
	final GuiFancyTexturePacks parentTexturePackGui;

	public GuiFancyTexturePackSlot(GuiFancyTexturePacks par1GuiTexturePacks)
	{
		super(GuiFancyTexturePacks.func_73950_a(par1GuiTexturePacks), par1GuiTexturePacks.width, par1GuiTexturePacks.height, 32, par1GuiTexturePacks.height - 55 + 4, 36);
		this.parentTexturePackGui = par1GuiTexturePacks;
	}

	/**
	 * Gets the size of the current slot list.
	 */
	protected int getSize()
	{
		return GuiFancyTexturePacks.func_73955_b(this.parentTexturePackGui).texturePackList.availableTexturePacks().size();
	}

	/**
	 * the element in the slot that was clicked, boolean for wether it was double clicked or not
	 */
	protected void elementClicked(int par1, boolean par2)
	{
		List var3 = GuiFancyTexturePacks.func_73958_c(this.parentTexturePackGui).texturePackList.availableTexturePacks();

		try
		{
			GuiFancyTexturePacks.func_73951_d(this.parentTexturePackGui).texturePackList.setTexturePack((ITexturePack)var3.get(par1));
			GuiFancyTexturePacks.func_73952_e(this.parentTexturePackGui).renderEngine.refreshTextures();
			GuiFancyTexturePacks.func_73962_f(this.parentTexturePackGui).renderGlobal.loadRenderers();
		}
		catch (Exception var5)
		{
			GuiFancyTexturePacks.func_73959_g(this.parentTexturePackGui).texturePackList.setTexturePack((ITexturePack)var3.get(0));
			GuiFancyTexturePacks.func_73957_h(this.parentTexturePackGui).renderEngine.refreshTextures();
			GuiFancyTexturePacks.func_73956_i(this.parentTexturePackGui).renderGlobal.loadRenderers();
		}
	}

	/**
	 * returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int par1)
	{
		List var2 = GuiFancyTexturePacks.func_73953_j(this.parentTexturePackGui).texturePackList.availableTexturePacks();
		return GuiFancyTexturePacks.func_73961_k(this.parentTexturePackGui).texturePackList.getSelectedTexturePack() == var2.get(par1);
	}

	/**
	 * return the height of the content being scrolled
	 */
	protected int getContentHeight()
	{
		return this.getSize() * 36;
	}

	protected void drawBackground()
	{
	}

	protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
	{
		ITexturePack var6 = (ITexturePack)GuiFancyTexturePacks.func_96143_l(this.parentTexturePackGui).texturePackList.availableTexturePacks().get(par1);
		var6.bindThumbnailTexture(GuiFancyTexturePacks.func_96142_m(this.parentTexturePackGui).renderEngine);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		par5Tessellator.startDrawingQuads();
		par5Tessellator.setColorOpaque_I(16777215);
		par5Tessellator.addVertexWithUV((double)par2, (double)(par3 + par4), 0.0D, 0.0D, 1.0D);
		par5Tessellator.addVertexWithUV((double)(par2 + 32), (double)(par3 + par4), 0.0D, 1.0D, 1.0D);
		par5Tessellator.addVertexWithUV((double)(par2 + 32), (double)par3, 0.0D, 1.0D, 0.0D);
		par5Tessellator.addVertexWithUV((double)par2, (double)par3, 0.0D, 0.0D, 0.0D);
		par5Tessellator.draw();
		String var7 = var6.getTexturePackFileName();

		if (!var6.isCompatible())
		{
			var7 = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("texturePack.incompatible") + " - " + var7;
		}

		if (var7.length() > 32)
		{
			var7 = var7.substring(0, 32).trim() + "...";
		}

		this.parentTexturePackGui.drawString(GuiFancyTexturePacks.func_73954_n(this.parentTexturePackGui), var7, par2 + 32 + 2, par3 + 1, 16777215);
		this.parentTexturePackGui.drawString(GuiFancyTexturePacks.func_96145_o(this.parentTexturePackGui), var6.getFirstDescriptionLine(), par2 + 32 + 2, par3 + 12, 8421504);
		this.parentTexturePackGui.drawString(GuiFancyTexturePacks.func_96144_p(this.parentTexturePackGui), var6.getSecondDescriptionLine(), par2 + 32 + 2, par3 + 12 + 10, 8421504);
	}
}
