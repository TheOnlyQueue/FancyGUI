package com.friendsoverlay.fancygui;

import net.minecraft.src.*;

public class GuiFancySmallButton extends GuiFancyButton {
	private final EnumOptions enumOptions;

	public GuiFancySmallButton(int par1, int par2, int par3, String par4Str, Integer mode)
	{
		this(par1, par2, par3, (EnumOptions)null, par4Str, mode);
	}

	public GuiFancySmallButton(int par1, int par2, int par3, int par4, int par5, String par6Str, Integer mode)
	{
		super(par1, par2, par3, par4, par5, par6Str, mode);
		this.enumOptions = null;
	}

	public GuiFancySmallButton(int par1, int par2, int par3, EnumOptions par4EnumOptions, String par5Str, Integer mode)
	{
		super(par1, par2, par3, 150, 20, par5Str, mode);
		this.enumOptions = par4EnumOptions;
	}

	public EnumOptions returnEnumOptions()
	{
		return this.enumOptions;
	}
}
