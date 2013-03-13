package com.friendsoverlay.fancygui.screens;

import net.minecraft.client.Minecraft;
import com.friendsoverlay.fancygui.*;
import net.minecraft.src.*;

public class GuiFancyYesNo extends GuiFancyScreen {
	/**
	 * A reference to the screen object that created this. Used for navigating
	 * between screens.
	 */
	protected GuiScreen parentScreen;

	/** First line of text. */
	private String message1;

	/** Second line of text. */
	private String message2;

	/** The text shown for the first button in GuiYesNo */
	protected String buttonText1;

	/** The text shown for the second button in GuiYesNo */
	protected String buttonText2;

	/** World number to be deleted. */
	protected int worldNumber;
	private GuiFancyRotatingBackground bg;
	
	public GuiFancyYesNo(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		parentScreen = (GuiScreen) getOldValue("parentScreen");
		message1 = (String) getOldValue("message1");
		message2 = (String) getOldValue("message2");
		buttonText1 = (String) getOldValue("buttonText1");
		buttonText2 = (String) getOldValue("buttonText2");
		worldNumber = (Integer) getOldValue("worldNumber");
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		this.buttonList.add(new GuiFancyButton(0, this.width / 2 - 80,
				this.height / 6 + 96, this.buttonText1, 3));
		this.buttonList.add(new GuiFancyButton(1, this.width / 2 + 80,
				this.height / 6 + 96, this.buttonText2, 3));
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of
	 * ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton) {
		this.parentScreen.confirmClicked(par1GuiButton.id == 0,
				this.worldNumber);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		bg.RenderBackground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.message1,
				this.width / 2, 70, 16777215);
		this.drawCenteredString(this.fontRenderer, this.message2,
				this.width / 2, 90, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	public void updateScreen() {
		bg.Update();
	}
}
