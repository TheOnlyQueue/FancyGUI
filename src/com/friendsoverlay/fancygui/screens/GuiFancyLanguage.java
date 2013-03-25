package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class GuiFancyLanguage extends GuiFancyScreen {

	/** This GUI's parent GUI. */
	protected GuiScreen parentGui;

	/**
	 * Timer used to update texture packs, decreases every tick and is reset to 20 and updates texture packs upon
	 * reaching 0.
	 */
	private int updateTimer = -1;

	/** This GUI's language list. */
	private GuiFancySlotLanguage languageList;

	/** For saving the user's language selection to disk. */
	private final GameSettings theGameSettings;

	/** This GUI's 'Done' button. */
	private GuiFancyButton doneButton;
	public Minecraft mc;
	public FontRenderer fontRenderer;
	private GuiFancyRotatingBackground bg;

	public GuiFancyLanguage(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		this.parentGui = (GuiScreen) getOldValue(0);
		this.theGameSettings = (GameSettings) getOldValue(3);
		this.mc = Minecraft.getMinecraft();
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		this.buttonList.add(this.doneButton = new GuiFancyButton(6, this.width / 2, this.height - 38, var1.translateKey("gui.done"), 3));
		this.languageList = new GuiFancySlotLanguage(this);
		this.languageList.registerScrollButtons(this.buttonList, 7, 8);
		this.bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			switch (par1GuiButton.id)
			{
				case 5:
					break;

				case 6:
					this.mc.displayGuiScreen(this.parentGui);
					break;

				default:
					this.languageList.actionPerformed(par1GuiButton);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		bg.RenderBackground(par1, par2, par3);
		this.languageList.drawScreen(par1, par2, par3);

		if (this.updateTimer <= 0)
		{
			this.mc.texturePackList.updateAvaliableTexturePacks();
			this.updateTimer += 20;
		}

		StringTranslate var4 = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, var4.translateKey("options.language"), this.width / 2, 16, 16777215);
		this.drawCenteredString(this.fontRenderer, "(" + var4.translateKey("options.languageWarning") + ")", this.width / 2, this.height - 56, 8421504);
		super.drawScreen(par1, par2, par3);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		bg.Update();
		super.updateScreen();
		--this.updateTimer;
	}

	/**
	 * Gets the relevant instance of GameSettings. Synthetic method for use in GuiSlotLanguage
	 */
	static GameSettings getGameSettings(GuiFancyLanguage par0GuiLanguage)
	{
		return par0GuiLanguage.theGameSettings;
	}

	/**
	 * Returns the private doneButton field.
	 */
	static GuiFancyButton getDoneButton(GuiFancyLanguage par0GuiLanguage)
	{
		return par0GuiLanguage.doneButton;
	}
}
