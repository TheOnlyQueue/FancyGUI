package com.friendsoverlay.fancygui.screens;

import net.minecraft.client.Minecraft;
import com.friendsoverlay.fancygui.*;
import net.minecraft.src.*;

public class GuiFancyCreateFlatWorld extends GuiFancyScreen {

    private static RenderItem theRenderItem = new RenderItem();
    private final GuiFancyCreateWorld createWorldGui;
    private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
    private String customizationTitle;
    private String layerMaterialLabel;
    private String heightLabel;
    private GuiFancyCreateFlatWorldListSlot createFlatWorldListSlotGui;
    private GuiButton buttonAddLayer;
    private GuiButton buttonEditLayer;
    private GuiButton buttonRemoveLayer;
    private GuiFancyRotatingBackground bg;
    public final Minecraft mc;
	public final Float zLevel;
    
	public GuiFancyCreateFlatWorld(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		createWorldGui = null;
        this.mc = mc;
		this.zLevel = super.zLevel;
	}
	
    public GuiFancyCreateFlatWorld(GuiFancyCreateWorld par1, String par2Str)
    {
    	super(Minecraft.getMinecraft(), null);
        this.createWorldGui = par1;
        this.setFlatGeneratorInfo(par2Str);
        this.mc = Minecraft.getMinecraft();
		this.zLevel = super.zLevel;
    }

    public String getFlatGeneratorInfo()
    {
        return this.theFlatGeneratorInfo.toString();
    }

    public void setFlatGeneratorInfo(String par1Str)
    {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(par1Str);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.customizationTitle = StatCollector.translateToLocal("createWorld.customize.flat.title");
        this.layerMaterialLabel = StatCollector.translateToLocal("createWorld.customize.flat.tile");
        this.heightLabel = StatCollector.translateToLocal("createWorld.customize.flat.height");
        
        this.createFlatWorldListSlotGui = new GuiFancyCreateFlatWorldListSlot(this);
        this.buttonList.add(this.buttonAddLayer = new GuiFancyButton(2, this.width / 2 - 80, this.height - 52, StatCollector.translateToLocal("createWorld.customize.flat.addLayer") + " (NYI)", 3));
        this.buttonList.add(this.buttonEditLayer = new GuiFancyButton(3, this.width / 2 + 80, this.height - 52, StatCollector.translateToLocal("createWorld.customize.flat.editLayer") + " (NYI)", 3));
        this.buttonList.add(this.buttonRemoveLayer = new GuiFancyButton(4, this.width / 2 - 80, this.height - 52, 150, 20, StatCollector.translateToLocal("createWorld.customize.flat.removeLayer"), 3));
        this.buttonList.add(new GuiFancyButton(0, this.width / 2 - 80, this.height - 28, StatCollector.translateToLocal("gui.done"), 3));
        this.buttonList.add(new GuiFancyButton(5, this.width / 2 + 80, this.height - 52, StatCollector.translateToLocal("createWorld.customize.presets"), 3));
        this.buttonList.add(new GuiFancyButton(1, this.width / 2 + 80, this.height - 28, StatCollector.translateToLocal("gui.cancel"), 3));
        this.buttonAddLayer.drawButton = this.buttonEditLayer.drawButton = false;
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_82270_g();
        bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        int var2 = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_82454_a - 1;

        if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(this.createWorldGui);
        }
        else if (par1GuiButton.id == 0)
        {
            this.createWorldGui.field_82290_a = this.getFlatGeneratorInfo();
            this.mc.displayGuiScreen(this.createWorldGui);
        }
        else if (par1GuiButton.id == 5)
        {
            this.mc.displayGuiScreen(new GuiFancyFlatPresets(this));
        }
        else if (par1GuiButton.id == 4 && this.func_82272_i())
        {
            this.theFlatGeneratorInfo.getFlatLayers().remove(var2);
            this.createFlatWorldListSlotGui.field_82454_a = Math.min(this.createFlatWorldListSlotGui.field_82454_a, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
        }

        this.theFlatGeneratorInfo.func_82645_d();
        this.func_82270_g();
    }

    public void func_82270_g()
    {
        boolean var1 = this.func_82272_i();
        this.buttonRemoveLayer.enabled = var1;
        this.buttonEditLayer.enabled = var1;
        this.buttonEditLayer.enabled = false;
        this.buttonAddLayer.enabled = false;
    }

    private boolean func_82272_i()
    {
        return this.createFlatWorldListSlotGui.field_82454_a > -1 && this.createFlatWorldListSlotGui.field_82454_a < this.theFlatGeneratorInfo.getFlatLayers().size();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
    	bg.RenderBackground(par1, par2, par3);
        this.createFlatWorldListSlotGui.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.customizationTitle, this.width / 2, 8, 16777215);
        int var4 = this.width / 2 - 92 - 16;
        this.drawString(this.fontRenderer, this.layerMaterialLabel, var4, 32, 16777215);
        this.drawString(this.fontRenderer, this.heightLabel, var4 + 2 + 213 - this.fontRenderer.getStringWidth(this.heightLabel), 32, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    static RenderItem getRenderItem()
    {
        return theRenderItem;
    }

    static FlatGeneratorInfo func_82271_a(GuiFancyCreateFlatWorld par0GuiCreateFlatWorld)
    {
        return par0GuiCreateFlatWorld.theFlatGeneratorInfo;
    }
    
    @Override
    public void updateScreen() {
    	bg.Update();
    }
}
