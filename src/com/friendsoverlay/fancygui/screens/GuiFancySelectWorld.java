package com.friendsoverlay.fancygui.screens;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.minecraft.client.Minecraft;
import com.friendsoverlay.fancygui.*;
import net.minecraft.src.*;

public class GuiFancySelectWorld extends GuiFancyScreen {

	/** simple date formater */
	private final DateFormat dateFormatter = new SimpleDateFormat();

	/**
	 * A reference to the screen object that created this. Used for navigating
	 * between screens.
	 */
	protected GuiScreen parentScreen;

	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Select world";

	/** True if a world has been selected. */
	private boolean selected = false;

	/** the currently selected world */
	private int selectedWorld;

	/** The save list for the world selection screen */
	private List saveList;
	private GuiWorldSlot worldSlotContainer;

	/** E.g. World, Welt, Monde, Mundo */
	private String localizedWorldText;
	private String localizedMustConvertText;

	/**
	 * The game mode text that is displayed with each world on the world
	 * selection list.
	 */
	private String[] localizedGameModeText = new String[3];

	/** set to true if you arein the process of deleteing a world/save */
	private boolean deleting;

	/** the rename button in the world selection gui */
	private GuiButton buttonRename;

	/** the select button in the world selection gui */
	private GuiButton buttonSelect;

	/** the delete button in the world selection gui */
	private GuiButton buttonDelete;
	private GuiButton field_82316_w;

	private GuiFancyRotatingBackground bg;

	public GuiFancySelectWorld(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		this.parentScreen = new GuiMainMenu();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		StringTranslate var1 = StringTranslate.getInstance();
		this.screenTitle = var1.translateKey("selectWorld.title");

		try
		{
			this.loadSaves();
		}
		catch (AnvilConverterException var3)
		{
			var3.printStackTrace();
			this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load words", var3.getMessage()));
			return;
		}

		this.localizedWorldText = var1.translateKey("selectWorld.world");
		this.localizedMustConvertText = var1.translateKey("selectWorld.conversion");
		this.localizedGameModeText[EnumGameType.SURVIVAL.getID()] = var1.translateKey("gameMode.survival");
		this.localizedGameModeText[EnumGameType.CREATIVE.getID()] = var1.translateKey("gameMode.creative");
		this.localizedGameModeText[EnumGameType.ADVENTURE.getID()] = var1.translateKey("gameMode.adventure");
		this.worldSlotContainer = new GuiWorldSlot(this);
		this.worldSlotContainer.registerScrollButtons(this.buttonList, 4, 5);
		this.initButtons();
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * loads the saves
	 */
	private void loadSaves() throws AnvilConverterException
	{
		ISaveFormat var1 = this.mc.getSaveLoader();
		this.saveList = var1.getSaveList();
		Collections.sort(this.saveList);
		this.selectedWorld = -1;
	}

	/**
	 * returns the file name of the specified save number
	 */
	protected String getSaveFileName(int par1) {
		return ((SaveFormatComparator) this.saveList.get(par1)).getFileName();
	}

	/**
	 * returns the name of the saved game
	 */
	protected String getSaveName(int par1) {
		String var2 = ((SaveFormatComparator) this.saveList.get(par1))
				.getDisplayName();

		if (var2 == null || MathHelper.stringNullOrLengthZero(var2)) {
			StringTranslate var3 = StringTranslate.getInstance();
			var2 = var3.translateKey("selectWorld.world") + " " + (par1 + 1);
		}

		return var2;
	}

	/**
	 * intilize the buttons for this GUI
	 */
	public void initButtons() {
		StringTranslate var1 = StringTranslate.getInstance();
		this.buttonList.add(buttonSelect = new GuiFancyButton(1,
				this.width / 2, this.height - 52, var1
				.translateKey("selectWorld.select"), 3));
		this.buttonList.add(new GuiFancyButton(3, this.width,
				this.height - 52, var1.translateKey("selectWorld.create"), 2));
		this.buttonList.add(buttonDelete = new GuiFancyButton(6, 0,
				this.height - 52, var1.translateKey("selectWorld.rename"), 1));
		this.buttonList.add(buttonRename = new GuiFancyButton(2, 0,
				this.height - 28, var1.translateKey("selectWorld.delete"), 1));
		this.buttonList.add(field_82316_w = new GuiFancyButton(7,
				this.width / 2, this.height - 28, var1
						.translateKey("selectWorld.recreate"), 3));
		this.buttonList.add(new GuiFancyButton(0, this.width,
				this.height - 28, var1.translateKey("gui.cancel"), 2));
		this.buttonSelect.enabled = false;
		this.buttonRename.enabled = false;
		this.buttonDelete.enabled = false;
		this.field_82316_w.enabled = false;
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of
	 * ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 2) {
				String var2 = this.getSaveName(this.selectedWorld);

				if (var2 != null) {
					this.deleting = true;
					GuiYesNo var3 = getDeleteWorldScreen(this, var2,
							this.selectedWorld);
					this.mc.displayGuiScreen(var3);
				}
			} else if (par1GuiButton.id == 1) {
				this.selectWorld(this.selectedWorld);
			} else if (par1GuiButton.id == 3) {
				this.mc.displayGuiScreen(new GuiCreateWorld(this));
			} else if (par1GuiButton.id == 6) {
				this.mc.displayGuiScreen(new GuiRenameWorld(this, this
						.getSaveFileName(this.selectedWorld)));
			} else if (par1GuiButton.id == 0) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if (par1GuiButton.id == 7) {
				GuiCreateWorld var5 = new GuiCreateWorld(this);
				ISaveHandler var6 = this.mc.getSaveLoader().getSaveLoader(
						this.getSaveFileName(this.selectedWorld), false);
				WorldInfo var4 = var6.loadWorldInfo();
				var6.flush();
				var5.func_82286_a(var4);
				this.mc.displayGuiScreen(var5);
			} else {
				this.worldSlotContainer.actionPerformed(par1GuiButton);
			}
		}
	}

	/**
	 * Gets the selected world.
	 */
	public void selectWorld(int par1) {
		this.mc.displayGuiScreen((GuiScreen) null);

		if (!this.selected) {
			this.selected = true;
			String var2 = this.getSaveFileName(par1);

			if (var2 == null) {
				var2 = "World" + par1;
			}

			String var3 = this.getSaveName(par1);

			if (var3 == null) {
				var3 = "World" + par1;
			}

			if (this.mc.getSaveLoader().canLoadWorld(var2)) {
				this.mc.launchIntegratedServer(var2, var3, (WorldSettings) null);
			}
		}
	}

	public void confirmClicked(boolean par1, int par2)
	{
		if (this.deleting)
		{
			this.deleting = false;

			if (par1)
			{
				ISaveFormat var3 = this.mc.getSaveLoader();
				var3.flushCache();
				var3.deleteWorldDirectory(this.getSaveFileName(par2));

				try
				{
					this.loadSaves();
				}
				catch (AnvilConverterException var5)
				{
					var5.printStackTrace();
				}
			}

			this.mc.displayGuiScreen(this);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		bg.RenderBackground(par1, par2, par3);
		this.worldSlotContainer.drawScreen(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer, this.screenTitle,
				this.width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}

	/**
	 * Gets a GuiYesNo screen with the warning, buttons, etc.
	 */
	public static GuiYesNo getDeleteWorldScreen(GuiScreen par0GuiScreen,
			String par1Str, int par2) {
		StringTranslate var3 = StringTranslate.getInstance();
		String var4 = var3.translateKey("selectWorld.deleteQuestion");
		String var5 = "\'" + par1Str + "\' "
				+ var3.translateKey("selectWorld.deleteWarning");
		String var6 = var3.translateKey("selectWorld.deleteButton");
		String var7 = var3.translateKey("gui.cancel");
		GuiYesNo var8 = new GuiYesNo(par0GuiScreen, var4, var5, var6, var7,
				par2);
		return var8;
	}

	@Override
	public void updateScreen() {
		bg.Update();
	}

	@Override
	public void drawDefaultBackground() {
	}

	static List getSize(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.saveList;
	}

	/**
	 * called whenever an element in this gui is selected
	 */
	static int onElementSelected(GuiFancySelectWorld par0GuiSelectWorld,
			int par1) {
		return par0GuiSelectWorld.selectedWorld = par1;
	}

	/**
	 * returns the world currently selected
	 */
	static int getSelectedWorld(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.selectedWorld;
	}

	/**
	 * returns the select button
	 */
	static GuiButton getSelectButton(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.buttonSelect;
	}

	/**
	 * returns the rename button
	 */
	static GuiButton getRenameButton(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.buttonRename;
	}

	/**
	 * returns the delete button
	 */
	static GuiButton getDeleteButton(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.buttonDelete;
	}

	static GuiButton func_82312_f(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.field_82316_w;
	}

	static String func_82313_g(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.localizedWorldText;
	}

	static DateFormat func_82315_h(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.dateFormatter;
	}

	static String func_82311_i(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.localizedMustConvertText;
	}

	static String[] func_82314_j(GuiFancySelectWorld par0GuiSelectWorld) {
		return par0GuiSelectWorld.localizedGameModeText;
	}

	class GuiWorldSlot extends GuiFancySlot {
		final GuiFancySelectWorld parentWorldGui;

		public GuiWorldSlot(GuiFancySelectWorld par1GuiSelectWorld) {
			super(par1GuiSelectWorld.mc, par1GuiSelectWorld.width,
					par1GuiSelectWorld.height, 32,
					par1GuiSelectWorld.height - 64, 36);
			this.parentWorldGui = par1GuiSelectWorld;
		}

		/**
		 * Gets the size of the current slot list.
		 */
		protected int getSize() {
			return GuiFancySelectWorld.getSize(this.parentWorldGui).size();
		}

		/**
		 * the element in the slot that was clicked, boolean for wether it was
		 * double clicked or not
		 */
		protected void elementClicked(int par1, boolean par2) {
			GuiFancySelectWorld.onElementSelected(this.parentWorldGui, par1);
			boolean var3 = GuiFancySelectWorld
					.getSelectedWorld(this.parentWorldGui) >= 0
					&& GuiFancySelectWorld
							.getSelectedWorld(this.parentWorldGui) < this
							.getSize();
			GuiFancySelectWorld.getSelectButton(this.parentWorldGui).enabled = var3;
			GuiFancySelectWorld.getRenameButton(this.parentWorldGui).enabled = var3;
			GuiFancySelectWorld.getDeleteButton(this.parentWorldGui).enabled = var3;
			GuiFancySelectWorld.func_82312_f(this.parentWorldGui).enabled = var3;

			if (par2 && var3) {
				this.parentWorldGui.selectWorld(par1);
			}
		}

		/**
		 * returns true if the element passed in is currently selected
		 */
		protected boolean isSelected(int par1) {
			return par1 == GuiFancySelectWorld
					.getSelectedWorld(this.parentWorldGui);
		}

		/**
		 * return the height of the content being scrolled
		 */
		protected int getContentHeight() {
			return GuiFancySelectWorld.getSize(this.parentWorldGui).size() * 36;
		}

		protected void drawBackground() {
			this.parentWorldGui.drawDefaultBackground();
		}

		protected void drawSlot(int par1, int par2, int par3, int par4,
				Tessellator par5Tessellator) {
			SaveFormatComparator var6 = (SaveFormatComparator) GuiFancySelectWorld
					.getSize(this.parentWorldGui).get(par1);
			String var7 = var6.getDisplayName();

			if (var7 == null || MathHelper.stringNullOrLengthZero(var7)) {
				var7 = GuiFancySelectWorld.func_82313_g(this.parentWorldGui)
						+ " " + (par1 + 1);
			}

			String var8 = var6.getFileName();
			var8 = var8
					+ " ("
					+ GuiFancySelectWorld.func_82315_h(this.parentWorldGui)
							.format(new Date(var6.getLastTimePlayed()));
			var8 = var8 + ")";
			String var9 = "";

			if (var6.requiresConversion()) {
				var9 = GuiFancySelectWorld.func_82311_i(this.parentWorldGui)
						+ " " + var9;
			} else {
				var9 = GuiFancySelectWorld.func_82314_j(this.parentWorldGui)[var6
						.getEnumGameType().getID()];

				if (var6.isHardcoreModeEnabled()) {
					var9 = "\u00a74"
							+ StatCollector
									.translateToLocal("gameMode.hardcore")
							+ "\u00a7r";
				}

				if (var6.getCheatsEnabled()) {
					var9 = var9
							+ ", "
							+ StatCollector
									.translateToLocal("selectWorld.cheats");
				}
			}

			this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer,
					var7, par2 + 2, par3 + 1, 16777215);
			this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer,
					var8, par2 + 2, par3 + 12, 8421504);
			this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer,
					var9, par2 + 2, par3 + 12 + 10, 8421504);
		}
	}
}
