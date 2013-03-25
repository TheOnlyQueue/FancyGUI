package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

import java.util.*;

public class GuiFancySnooper extends GuiFancyScreen {

	/** Instance of GuiScreen. */
	private final GuiScreen snooperGuiScreen;

	/** Instance of GameSettings. */
	private final GameSettings snooperGameSettings;
	private final List field_74098_c = new ArrayList();
	private final List field_74096_d = new ArrayList();

	/** The Snooper title. */
	private String snooperTitle;
	private String[] field_74101_n;
	private GuiFancySnooperList snooperList;
	private GuiFancyButton buttonAllowSnooping;
	public Minecraft mc;
	public FontRenderer fontRenderer;
	private GuiFancyRotatingBackground bg;

	public GuiFancySnooper(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		snooperGuiScreen = (GuiScreen) getOldValue(0);
		snooperGameSettings = (GameSettings) getOldValue(1);
		this.mc = Minecraft.getMinecraft();
		fontRenderer = mc.fontRenderer;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		this.snooperTitle = StatCollector.translateToLocal("options.snooper.title");
		String var1 = StatCollector.translateToLocal("options.snooper.desc");
		ArrayList var2 = new ArrayList();
		Iterator var3 = this.fontRenderer.listFormattedStringToWidth(var1, this.width - 30).iterator();

		while (var3.hasNext())
		{
			String var4 = (String)var3.next();
			var2.add(var4);
		}

		this.field_74101_n = (String[])var2.toArray(new String[0]);
		this.field_74098_c.clear();
		this.field_74096_d.clear();
		this.buttonList.add(this.buttonAllowSnooping = new GuiFancyButton(1, this.width / 2, this.height - 40, this.snooperGameSettings.getKeyBinding(EnumOptions.SNOOPER_ENABLED), 3));
		this.buttonList.add(new GuiFancyButton(2, this.width / 2, this.height - 20, StatCollector.translateToLocal("gui.done"), 3));
		boolean var6 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
		Iterator var7 = (new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet().iterator();
		Map.Entry var5;

		while (var7.hasNext())
		{
			var5 = (Map.Entry)var7.next();
			this.field_74098_c.add((var6 ? "C " : "") + (String)var5.getKey());
			this.field_74096_d.add(this.fontRenderer.trimStringToWidth((String)var5.getValue(), this.width - 220));
		}

		if (var6)
		{
			var7 = (new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet().iterator();

			while (var7.hasNext())
			{
				var5 = (Map.Entry)var7.next();
				this.field_74098_c.add("S " + (String)var5.getKey());
				this.field_74096_d.add(this.fontRenderer.trimStringToWidth((String)var5.getValue(), this.width - 220));
			}
		}

		this.snooperList = new GuiFancySnooperList(this);
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		if (par1GuiButton.enabled)
		{
			if (par1GuiButton.id == 2)
			{
				this.snooperGameSettings.saveOptions();
				this.snooperGameSettings.saveOptions();
				this.mc.displayGuiScreen(this.snooperGuiScreen);
			}

			if (par1GuiButton.id == 1)
			{
				this.snooperGameSettings.setOptionValue(EnumOptions.SNOOPER_ENABLED, 1);
				this.buttonAllowSnooping.displayString = this.snooperGameSettings.getKeyBinding(EnumOptions.SNOOPER_ENABLED);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		bg.RenderBackground(par1, par2, par3);
		this.snooperList.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.snooperTitle, this.width / 2, 8, 16777215);
		int var4 = 22;
		String[] var5 = this.field_74101_n;
		int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7)
		{
			String var8 = var5[var7];
			this.drawCenteredString(this.fontRenderer, var8, this.width / 2, var4, 8421504);
			var4 += this.fontRenderer.FONT_HEIGHT;
		}

		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {
		bg.Update();
	}

	static List func_74095_a(GuiFancySnooper par0GuiSnooper)
	{
		return par0GuiSnooper.field_74098_c;
	}

	static List func_74094_b(GuiFancySnooper par0GuiSnooper)
	{
		return par0GuiSnooper.field_74096_d;
	}
}
