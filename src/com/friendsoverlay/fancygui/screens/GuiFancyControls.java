package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;

import java.util.*;

public class GuiFancyControls extends GuiFancyScreen
{
    /**
     * A reference to the screen object that created this. Used for navigating between screens.
     */
    private GuiScreen parentScreen;
    private GuiFancyControlsSlot controlsSlot;

    /** The title string that is displayed in the top-center of the screen. */
    protected String screenTitle = "Controls";

    /** Reference to the GameSettings object. */
    private GameSettings options;
    private GuiFancyRotatingBackground bg;
    
    /** The ID of the  button that has been pressed. */
    protected int buttonId = -1;

    private GuiButton doneButton;
    public ArrayList<Integer> optionIDs = new ArrayList<Integer>();
	public List buttonList;

	public GuiFancyControls(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		parentScreen = (GuiScreen) getOldValue(0);
		options = (GameSettings) getOldValue(3);
		this.buttonList = super.buttonList;
	}

	private int func_73907_g()
    {
        return this.width / 2 - 155;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
    	if (controlsSlot == null) {
    		controlsSlot = new GuiFancyControlsSlot(mc, this.width, this.height, 32, this.height - 50, 30, options, this);
    	} else {
    		controlsSlot.func_77207_a(this.width, this.height, 32, this.height - 50);
    	}

    	StringTranslate var1 = StringTranslate.getInstance();

    	doneButton = new GuiFancyButton(200, this.width / 2, this.height / 6 + 168, var1.translateKey("gui.done"), 3);
        this.screenTitle = var1.translateKey("controls.title");
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 200)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        else
        {
            this.buttonId = par1GuiButton.id;
            par1GuiButton.displayString = "> " + this.options.getOptionDisplayString(par1GuiButton.id) + " <";
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (this.buttonId >= 0)
        {
            this.options.setKeyBinding(this.buttonId, -100 + par3);

            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else
        {
            super.mouseClicked(par1, par2, par3);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (this.buttonId >= 0)
        {
            this.options.setKeyBinding(this.buttonId, par2);
            this.buttonId = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        }
        else
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
    	bg.RenderBackground(par1, par2, par3);
    	
    	super.drawScreen(par1, par2, par3);
    	buttonList.clear();
    	controlsSlot.drawScreen(par1, par2, par3);
    	
    	StringTranslate var1 = StringTranslate.getInstance();
    	doneButton.drawButton(mc, par1, par2);
		buttonList.add(doneButton);
    	
    	
    	this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
    }
    
    @Override
    public void updateScreen() {
    	bg.Update();
    	super.updateScreen();
    }
}
