package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;
import org.lwjgl.opengl.*;

public class GuiFancyIngameMenu extends GuiFancyScreen {
	public GuiFancyIngameMenu(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
	}

	/** Also counts the number of updates, not certain as to why yet. */
	private int updateCounter2 = 0;

	/** Counts the number of screen updates. */
	private int updateCounter = 0;

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		this.updateCounter2 = 0;
		this.buttonList.clear();
		byte var1 = -16;
		this.buttonList.add(new GuiFancyButton(1, 0, this.height / 4 + 170 + var1, StatCollector.translateToLocal("menu.returnToMenu"), 1));

		if (!this.mc.isIntegratedServerRunning())
		{
			((GuiButton)this.buttonList.get(0)).displayString = StatCollector.translateToLocal("menu.disconnect");
		}

		this.buttonList.add(new GuiFancyButton(4, 0, this.height / 4 + 24 + var1, StatCollector.translateToLocal("menu.returnToGame"), 1));
		this.buttonList.add(new GuiFancyButton(0, 0, this.height / 4 + 104 + var1, StatCollector.translateToLocal("menu.options"), 1));
		GuiButton var3;
		this.buttonList.add(var3 = new GuiFancyButton(7, 0, this.height / 4 + 128 + var1, StatCollector.translateToLocal("menu.shareToLan"), 1));
		this.buttonList.add(new GuiFancyButton(5, 0, this.height / 4 + 48 + var1, StatCollector.translateToLocal("gui.achievements"), 1));
		this.buttonList.add(new GuiFancyButton(6, 0, this.height / 4 + 72 + var1, StatCollector.translateToLocal("gui.stats"), 1));
		var3.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch (par1GuiButton.id)
		{
			case 0:
				this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
				break;

			case 1:
				par1GuiButton.enabled = false;
				this.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
				this.mc.theWorld.sendQuittingDisconnectingPacket();
				this.mc.loadWorld((WorldClient)null);
				this.mc.displayGuiScreen(new GuiMainMenu());

			case 2:
			case 3:
			default:
				break;

			case 4:
				this.mc.displayGuiScreen((GuiScreen)null);
				this.mc.setIngameFocus();
				this.mc.sndManager.resumeAllSounds();
				break;

			case 5:
				this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
				break;

			case 6:
				this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
				break;

			case 7:
				this.mc.displayGuiScreen(new GuiShareToLan(this));
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		super.updateScreen();
		++this.updateCounter;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPushMatrix();
		GL11.glScalef(5F, 5F, 5F);
		Boolean defaultunicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(true);
		this.drawCenteredString(this.fontRenderer, "Paused", (int)((float)(width - 200) / 5F), (int)((float)(height - 80) / 5F), 0x32e0e0e0);
		fontRenderer.setUnicodeFlag(defaultunicode);
		GL11.glPopMatrix();
		super.drawScreen(par1, par2, par3);
	}
}
