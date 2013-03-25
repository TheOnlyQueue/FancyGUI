package com.friendsoverlay.fancygui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiFancyButton extends GuiButton {

    public int mode = 0;
    public int xPositionOrig = 0;
    private boolean first = false;
    private EnumOptions enumOptions;
	public Integer width;
	public Integer height;

    public GuiFancyButton(int par1, int par2, int par3, String par4Str) {
        this(par1, par2, par3, 200, 20, par4Str);
    }

    public GuiFancyButton(int par1, int par2, int par3, String par4Str,
                          int par5) {
        this(par1, par2, par3, 100, 20, par4Str, par5);
    }

    public GuiFancyButton(int par1, int par2, int par3, int par4, int par5,
                          String par6Str, int par7) {
        this(par1, par2, par3, par4, par5, par6Str);
        mode = par7;
        xPositionOrig = xPosition;
    }

    public GuiFancyButton(int par1, int par2, int par3, int par4, int par5,
                          String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
        first = true;
    }

    public GuiFancyButton(int returnEnumOrdinal, int i, int j,
                          EnumOptions var6, String keyBinding) {
        this(returnEnumOrdinal, i, j, 150, 20, keyBinding, 3);
        this.enumOptions = var6;
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		this.width = super.width;
		this.height = super.height;
        if (this.drawButton) {

            if (mode == 0) {
                FontRenderer var4 = par1Minecraft.fontRenderer;
                par1Minecraft.renderEngine.bindTexture("/gui/gui.png");
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.field_82253_i = par2 >= this.xPosition
                        && par3 >= this.yPosition
                        && par2 < this.xPosition + this.width
                        && par3 < this.yPosition + this.height;
                int var5 = this.getHoverState(this.field_82253_i);
                this.drawTexturedModalRect(this.xPosition, this.yPosition,
                        0, 46 + var5 * 20, this.width / 2, this.height);
                this.drawTexturedModalRect(this.xPosition + this.width / 2,
                        this.yPosition, 200 - this.width / 2,
                        46 + var5 * 20, this.width / 2, this.height);
                this.mouseDragged(par1Minecraft, par2, par3);
                int var6 = 14737632;

                if (!this.enabled) {
                    var6 = -6250336;
                } else if (this.field_82253_i) {
                    var6 = 16777120;
                }

                this.drawCenteredString(var4, this.displayString,
                        this.xPosition + this.width / 2, this.yPosition
                        + (this.height - 8) / 2, var6);
            } else if (mode == 1) {
                FontRenderer var4 = par1Minecraft.fontRenderer;
                par1Minecraft.renderEngine.bindTexture("%blur%/gui/fancygui.png");
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                this.field_82253_i = par2 >= this.xPosition
                        && par3 >= this.yPosition
                        && par2 < this.xPosition + this.width
                        && par3 < this.yPosition + this.height;
                int var5 = this.getHoverState(this.field_82253_i);
                this.drawTexturedModalRect(this.xPosition, this.yPosition,
                        0, var5 * 20, this.width, this.height);
                this.mouseDragged(par1Minecraft, par2, par3);
                this.drawString(var4, this.displayString,
                        this.xPosition + 10, this.yPosition
                        + (this.height - 8) / 2, 0xc8e0e0e0);
            } else if (mode == 2) {
                if (first) {
                    xPosition = xPosition - 100;
                }

                FontRenderer var4 = par1Minecraft.fontRenderer;
                par1Minecraft.renderEngine.bindTexture("%blur%/gui/fancygui.png");
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                this.field_82253_i = par2 >= this.xPosition
                        && par3 >= this.yPosition
                        && par2 < this.xPosition + this.width
                        && par3 < this.yPosition + this.height;
                int var5 = this.getHoverState(this.field_82253_i);
                this.drawTexturedModalRect(this.xPosition, this.yPosition,
                        156, var5 * 20, this.width, this.height);
                this.mouseDragged(par1Minecraft, par2, par3);
                this.drawRightString(var4, this.displayString,
                        xPositionOrig - 10, this.yPosition
                        + (this.height - 8) / 2, 0xc8e0e0e0);
            } else if (mode == 3) {
                if (first) {
                    xPosition = xPosition - 51;
                }

                FontRenderer var4 = par1Minecraft.fontRenderer;
                par1Minecraft.renderEngine.bindTexture("%blur%/gui/fancygui.png");
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                this.field_82253_i = par2 >= this.xPosition
                        && par3 >= this.yPosition
                        && par2 < this.xPosition + this.width
                        && par3 < this.yPosition + this.height;
                int var5 = this.getHoverState(this.field_82253_i);
                this.drawTexturedModalRect(this.xPosition, this.yPosition,
                        0, var5 * 20 + 60, this.width, this.height);
                this.mouseDragged(par1Minecraft, par2, par3);
                this.drawCenteredString(var4, this.displayString,
                        xPositionOrig, this.yPosition + (this.height - 8)
                        / 2, 0xc8e0e0e0);
            }

            GL11.glDisable(GL11.GL_BLEND);
            first = false;
        }
    }

    public void drawRightString(FontRenderer fontrenderer, String s, int i,
                                int j, int k) {
        fontrenderer.drawStringWithShadow(s,
                i - fontrenderer.getStringWidth(s), j, k);
    }

    public EnumOptions returnEnumOptions() {
        return this.enumOptions;
    }
}
