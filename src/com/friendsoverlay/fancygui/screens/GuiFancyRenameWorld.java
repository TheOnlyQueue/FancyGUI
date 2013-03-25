package com.friendsoverlay.fancygui.screens;

import net.minecraft.client.Minecraft;
import com.friendsoverlay.fancygui.*;

import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

public class GuiFancyRenameWorld extends GuiFancyScreen {
	private GuiScreen parentGuiScreen;
	private GuiTextField theGuiTextField;
	private final String worldName;
	private GuiFancyRotatingBackground bg;

	public GuiFancyRenameWorld(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		worldName = (String) getOldValue(2);
		parentGuiScreen = (GuiScreen) getOldValue(0);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		this.theGuiTextField.updateCursorCounter();
		bg.Update();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiFancyButton(0, this.width / 2,
				this.height / 4 + 96 + 12, var1
						.translateKey("selectWorld.renameButton"), 3));
		this.buttonList
				.add(new GuiFancyButton(1, this.width / 2,
						this.height / 4 + 120 + 12, var1
								.translateKey("gui.cancel"), 3));
		ISaveFormat var2 = this.mc.getSaveLoader();
		WorldInfo var3 = var2.getWorldInfo(this.worldName);
		String var4 = var3.getWorldName();
		this.theGuiTextField = new GuiTextField(this.fontRenderer,
				this.width / 2 - 100, 60, 200, 20);
		this.theGuiTextField.setFocused(true);
		this.theGuiTextField.setText(var4);
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of
	 * ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.enabled) {
			if (par1GuiButton.id == 1) {
				this.mc.displayGuiScreen(this.parentGuiScreen);
			} else if (par1GuiButton.id == 0) {
				ISaveFormat var2 = this.mc.getSaveLoader();
				var2.renameWorld(this.worldName, this.theGuiTextField.getText()
						.trim());
				this.mc.displayGuiScreen(this.parentGuiScreen);
			}
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e).
	 */
	protected void keyTyped(char par1, int par2) {
		this.theGuiTextField.textboxKeyTyped(par1, par2);
		((GuiButton) this.buttonList.get(0)).enabled = this.theGuiTextField
				.getText().trim().length() > 0;

		if (par1 == 13) {
			this.actionPerformed((GuiButton) this.buttonList.get(0));
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		this.theGuiTextField.mouseClicked(par1, par2, par3);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {
		StringTranslate var4 = StringTranslate.getInstance();
		bg.RenderBackground(par1, par2, par3);
		this.drawCenteredString(this.fontRenderer,
				var4.translateKey("selectWorld.renameTitle"), this.width / 2,
				this.height / 4 - 60 + 20, 16777215);
		this.drawString(this.fontRenderer,
				var4.translateKey("selectWorld.enterName"),
				this.width / 2 - 100, 47, 10526880);
		this.theGuiTextField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}
