package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class GuiFancyControlsSlot extends GuiFancySlot {

	private GameSettings options;
	private GuiFancyControls parentScreen;

	public GuiFancyControlsSlot(Minecraft par1Minecraft, int par2, int par3,
			int par4, int par5, int par6, GameSettings options, GuiFancyControls parentScreen) {
		super(par1Minecraft, par2, par3, par4, par5, par6);
		this.options = options;
		this.parentScreen = parentScreen;
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 30;
	}
	
	@Override
	protected int getSize() {
		return options.keyBindings.length;
	}

	@Override
	protected void elementClicked(int var1, boolean var2) {
	}

	@Override
	protected boolean isSelected(int var1) {
		return false;
	}

	@Override
	protected void drawBackground() {
	}

	@Override
	protected void drawSlot(int par1, int par2, int par3, int par4,
			Tessellator par5) {
		parentScreen.optionIDs.add(par1);
		
        StringTranslate var1 = StringTranslate.getInstance();
        int var2 = parentScreen.width / 2 - 155;

        GuiButton button = new GuiFancyButton(par1, var2, par3 + 5, this.options.getOptionDisplayString(par1), 3);
        Boolean doubleexist = false;
        for (int i = 0; i < options.keyBindings.length; i++) {
        	if (options.keyBindings[i].keyCode == options.keyBindings[par1].keyCode && par1 != i) {
        		doubleexist = true;
        	}
        }
        
        
        if (par1 == parentScreen.buttonId) {
        	button.displayString = "\u00a7f> \u00a7e??? \u00a7f<";
        } else if (doubleexist)
        {
            button.displayString = "\u00a7c" + this.options.getOptionDisplayString(par1);
        }
        
        parentScreen.buttonList.add(button);
        parentScreen.drawString(Minecraft.getMinecraft().fontRenderer, this.options.getKeyBindingDescription(par1), par2, par3 + 11, -1);

	}
}
