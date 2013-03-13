package com.friendsoverlay.fancygui.screens;

import net.minecraft.client.*;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.friendsoverlay.fancygui.*;

public class GuiFancyFlatPresetsListSlot extends GuiFancySlot {
    public int field_82459_a;

    final GuiFancyFlatPresets flatPresetsGui;

    public GuiFancyFlatPresetsListSlot(GuiFancyFlatPresets par1)
    {
        super(par1.mc, par1.width, par1.height, 80, par1.height - 37, 24);
        this.flatPresetsGui = par1;
        this.field_82459_a = -1;
    }

    private void func_82457_a(int par1, int par2, int par3)
    {
        this.func_82456_d(par1 + 1, par2 + 1);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.enableGUIStandardItemLighting();
        GuiFancyFlatPresets.getPresetIconRenderer().renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, this.flatPresetsGui.mc.renderEngine, new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

    private void func_82456_d(int par1, int par2)
    {
        this.func_82455_b(par1, par2, 0, 0);
    }

    private void func_82455_b(int par1, int par2, int par3, int par4)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.flatPresetsGui.mc.renderEngine.func_98187_b("/gui/slot.png");
		Tessellator var10 = Tessellator.instance;
        var10.startDrawingQuads();
        var10.addVertexWithUV((double)(par1 + 0), (double)(par2 + 18), (double)this.flatPresetsGui.zLevel, (double)((float)(par3 + 0) * 0.0078125F), (double)((float)(par4 + 18) * 0.0078125F));
        var10.addVertexWithUV((double)(par1 + 18), (double)(par2 + 18), (double)this.flatPresetsGui.zLevel, (double)((float)(par3 + 18) * 0.0078125F), (double)((float)(par4 + 18) * 0.0078125F));
        var10.addVertexWithUV((double)(par1 + 18), (double)(par2 + 0), (double)this.flatPresetsGui.zLevel, (double)((float)(par3 + 18) * 0.0078125F), (double)((float)(par4 + 0) * 0.0078125F));
        var10.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.flatPresetsGui.zLevel, (double)((float)(par3 + 0) * 0.0078125F), (double)((float)(par4 + 0) * 0.0078125F));
        var10.draw();
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiFancyFlatPresets.getPresets().size();
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        this.field_82459_a = par1;
        this.flatPresetsGui.func_82296_g();
        GuiFancyFlatPresets.func_82298_b(this.flatPresetsGui).setText(((GuiFancyFlatPresetsItem)GuiFancyFlatPresets.getPresets().get(GuiFancyFlatPresets.func_82292_a(this.flatPresetsGui).field_82459_a)).presetData);
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return par1 == this.field_82459_a;
    }

    protected void drawBackground() {}

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        GuiFancyFlatPresetsItem var6 = (GuiFancyFlatPresetsItem)GuiFancyFlatPresets.getPresets().get(par1);
        this.func_82457_a(par2, par3, var6.iconId);
        Minecraft.getMinecraft().fontRenderer.drawString(var6.presetName, par2 + 18 + 5, par3 + 6, 16777215);
    }
}
