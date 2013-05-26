package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

public class GuiFancyOptions extends GuiFancyScreen {
	/**
	 * An array of options that can be changed directly from the options GUI.
	 */
	private static final EnumOptions[] relevantOptions = new EnumOptions[] {EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.FOV, EnumOptions.DIFFICULTY, EnumOptions.TOUCHSCREEN};

	/**
	 * A reference to the screen object that created this. Used for navigating between screens.
	 */
	private final GuiScreen parentScreen;

	/** Reference to the GameSettings object. */
	private final GameSettings options;

	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Options";
	private GuiFancyRotatingBackground bg;

	public GuiFancyOptions(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		this.parentScreen = (GuiScreen) getOldValue(1);
		this.options = (GameSettings) getOldValue(2);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		int var2 = 0;
		this.screenTitle = var1.translateKey("options.title");
		EnumOptions[] var3 = relevantOptions;
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5)
		{
			EnumOptions var6 = var3[var5];

			if (var6.getEnumFloat())
			{
				this.buttonList.add(new GuiFancySlider(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160, this.height / 6 - 12 + 24 * (var2 >> 1), var6, this.options.getKeyBinding(var6), this.options.getOptionFloatValue(var6)));
			}
			else
			{
				GuiFancySmallButton var7 = new GuiFancySmallButton(var6.returnEnumOrdinal(), this.width / 2 - 155 + var2 % 2 * 160 + 75, this.height / 6 - 12 + 24 * (var2 >> 1), var6, this.options.getKeyBinding(var6), 3);

				if (var6 == EnumOptions.DIFFICULTY && this.mc.theWorld != null && this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled())
				{
					var7.enabled = false;
					var7.displayString = StatCollector.translateToLocal("options.difficulty") + ": " + StatCollector.translateToLocal("options.difficulty.hardcore");
				}

				this.buttonList.add(var7);
			}

			++var2;
		}

		this.buttonList.add(new GuiFancyButton(101, this.width / 2 - 77, this.height / 6 + 96 - 6, 100, 20, var1.translateKey("options.video"), 3));
		this.buttonList.add(new GuiFancyButton(100, this.width / 2 + 77, this.height / 6 + 96 - 6, 100, 20, var1.translateKey("options.controls"), 3));
		this.buttonList.add(new GuiFancyButton(102, this.width / 2 - 77 - 36 - 4, this.height / 6 + 120 - 6, 100, 20, var1.translateKey("options.language"), 3));
		if (FancyGUI.shadersInstalled) {
			this.buttonList.add(new GuiFancyButton(190, this.width / 2 - 36 + 4, this.height / 6 + 120 - 6, 100, 20, "Shaders...", 3));
		}
		this.buttonList.add(new GuiFancyButton(103, this.width / 2 + 77, this.height / 6 + 120 - 6, 100, 20, var1.translateKey("options.multiplayer.title"), 3));
		this.buttonList.add(new GuiFancyButton(105, this.width / 2 - 77, this.height / 6 + 144 - 6, 100, 20, var1.translateKey("options.texture.pack"), 3));
		this.buttonList.add(new GuiFancyButton(104, this.width / 2 + 77, this.height / 6 + 144 - 6, 100, 20, var1.translateKey("options.snooper.view"), 3));
		this.buttonList.add(new GuiFancyButton(200, this.width / 2, this.height / 6 + 168, var1.translateKey("gui.done"), 3));
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			if (par1GuiButton.id < 100 && par1GuiButton instanceof GuiFancySmallButton)
			{
				this.options.setOptionValue(((GuiFancySmallButton)par1GuiButton).returnEnumOptions(), 1);
				par1GuiButton.displayString = this.options.getKeyBinding(EnumOptions.getEnumOptions(par1GuiButton.id));
			}

			if (par1GuiButton.id == 101)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiVideoSettings(this, this.options));
			}

			if (par1GuiButton.id == 100)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiControls(this, this.options));
			}

			if (par1GuiButton.id == 102)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiLanguage(this, this.options));
			}

			if (par1GuiButton.id == 103)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new ScreenChatOptions(this, this.options));
			}

			if (par1GuiButton.id == 104)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiSnooper(this, this.options));
			}

			if (par1GuiButton.id == 190 && FancyGUI.shadersInstalled)
			{
				this.mc.gameSettings.saveOptions();
				try {
					Class c = Class.forName("net.minecraft.src.GuiShaders");
					Object o = c.getConstructor(GuiScreen.class, GameSettings.class).newInstance(this, this.options);
					this.mc.displayGuiScreen((GuiScreen) o);
				} catch (Throwable t) {
					try {
						Class c = Class.forName("GuiShaders");
						Object o = c.getConstructor(GuiScreen.class, GameSettings.class).newInstance(this, this.options);
						this.mc.displayGuiScreen((GuiScreen) o);
					} catch (Throwable t2) {
					}
				}
			}

			if (par1GuiButton.id == 200)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentScreen);
			}

			if (par1GuiButton.id == 105)
			{
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiTexturePacks(this, this.options));
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		bg.RenderBackground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 15, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {
		bg.Update();
	}
}
