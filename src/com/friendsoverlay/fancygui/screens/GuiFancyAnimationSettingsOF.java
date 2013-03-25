package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class GuiFancyAnimationSettingsOF extends GuiFancyScreen {
	private GuiScreen prevScreen;
	protected String title = "Animation Settings";
	private GameSettings settings;
	private static EnumOptions[] enumOptions = new EnumOptions[] {EnumOptions.ANIMATED_WATER, EnumOptions.ANIMATED_LAVA, EnumOptions.ANIMATED_FIRE, EnumOptions.ANIMATED_PORTAL, EnumOptions.ANIMATED_REDSTONE, EnumOptions.ANIMATED_EXPLOSION, EnumOptions.ANIMATED_FLAME, EnumOptions.ANIMATED_SMOKE, EnumOptions.VOID_PARTICLES, EnumOptions.WATER_PARTICLES, EnumOptions.RAIN_SPLASH, EnumOptions.PORTAL_PARTICLES, EnumOptions.PARTICLES, EnumOptions.DRIPPING_WATER_LAVA, EnumOptions.ANIMATED_TERRAIN, EnumOptions.ANIMATED_ITEMS, EnumOptions.ANIMATED_TEXTURES};
	private GuiFancyRotatingBackground bg;

	public GuiFancyAnimationSettingsOF(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		prevScreen = (GuiScreen) getOldValue(0);
		settings = (GameSettings) getOldValue(2);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		int var2 = 0;
		EnumOptions[] var3 = enumOptions;
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5)
		{
			EnumOptions var6 = var3[var5];
			int var7 = this.width / 2 - 155 + var2 % 2 * 160;
			int var8 = this.height / 6 + 21 * (var2 / 2) - 10;

			if (!var6.getEnumFloat())
			{
				this.buttonList.add(new GuiFancySmallButton(var6.returnEnumOrdinal(), var7 + 70, var8, var6, this.settings.getKeyBinding(var6), 3));
			}
			else
			{
				this.buttonList.add(new GuiFancySlider(var6.returnEnumOrdinal(), var7, var8, var6, this.settings.getKeyBinding(var6), this.settings.getOptionFloatValue(var6)));
			}

			++var2;
		}

		this.buttonList.add(new GuiFancyButton(210, this.width / 2 - 155 + 30, this.height / 6 + 168 + 11, "All ON", 3));
		this.buttonList.add(new GuiFancyButton(211, this.width / 2 - 155 + 110, this.height / 6 + 168 + 11, "All OFF", 3));
		this.buttonList.add(new GuiFancyButton(200, this.width / 2 + 75, this.height / 6 + 168 + 11, var1.translateKey("gui.done"), 3));
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton var1)
	{
		if (var1.enabled)
		{
			if (var1.id < 100 && var1 instanceof GuiFancySmallButton)
			{
				this.settings.setOptionValue(((GuiFancySmallButton)var1).returnEnumOptions(), 1);
				var1.displayString = this.settings.getKeyBinding(EnumOptions.getEnumOptions(var1.id));
			}

			if (var1.id == 200)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.prevScreen);
			}

			if (var1.id == 210)
			{
				this.mc.gameSettings.setAllAnimations(true);
			}

			if (var1.id == 211)
			{
				this.mc.gameSettings.setAllAnimations(false);
			}

			if (var1.id != EnumOptions.CLOUD_HEIGHT.ordinal())
			{
				ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
				int var3 = var2.getScaledWidth();
				int var4 = var2.getScaledHeight();
				this.setWorldAndResolution(this.mc, var3, var4);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int var1, int var2, float var3)
	{
		bg.RenderBackground(var1, var2, var3);
		this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
		super.drawScreen(var1, var2, var3);
	}

	@Override
	public void updateScreen() {
		bg.Update();
	}
}
