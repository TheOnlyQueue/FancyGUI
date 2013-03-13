package com.friendsoverlay.fancygui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.image.BufferedImage;

public class GuiFancyRotatingBackground extends GuiScreen {
    private int viewportTexture;

    /** Reference to the Minecraft object. */
    private Minecraft mc;

    /** The width of the screen object. */
    private int width;

    /** The height of the screen object. */
    private int height;
    private float zLevel;
    public boolean enabled;

    private static Integer rotation = 0;

    GuiScreen screen;

    public GuiFancyRotatingBackground(Minecraft minecraft, int i, int j,
                                      float f) {
        enabled = true;
        mc = minecraft;
        viewportTexture = minecraft.renderEngine
                .allocateAndSetupTexture(new BufferedImage(256, 256, 2));
        width = i;
        height = j;
        zLevel = f;
    }

    public void Update() {
        rotation++;
    }

    private void drawPanorama(int par1, int par2, float par3) {
        Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        byte var5 = 8;

        for (int var6 = 0; var6 < var5 * var5; ++var6) {
            GL11.glPushMatrix();
            float var7 = ((float) (var6 % var5) / (float) var5 - 0.5F) / 64.0F;
            float var8 = ((float) (var6 / var5) / (float) var5 - 0.5F) / 64.0F;
            float var9 = 0.0F;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(
                    MathHelper.sin(((float) this.rotation + par3) / 400.0F) * 25.0F + 20.0F,
                    1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-((float) this.rotation + par3) * 0.1F, 0.0F,
                    1.0F, 0.0F);

            for (int var10 = 0; var10 < 6; ++var10) {
                GL11.glPushMatrix();

                if (var10 == 1) {
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 2) {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 3) {
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 4) {
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (var10 == 5) {
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                mc.renderEngine.func_98187_b((new StringBuilder()).append("/title/bg/panorama").append(var10).append(".png").toString());
				var4.startDrawingQuads();
                var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
                float var11 = 0.0F;
                var4.addVertexWithUV(-1.0D, -1.0D, 1.0D,
                        (double) (0.0F + var11), (double) (0.0F + var11));
                var4.addVertexWithUV(1.0D, -1.0D, 1.0D,
                        (double) (1.0F - var11), (double) (0.0F + var11));
                var4.addVertexWithUV(1.0D, 1.0D, 1.0D,
                        (double) (1.0F - var11), (double) (1.0F - var11));
                var4.addVertexWithUV(-1.0D, 1.0D, 1.0D,
                        (double) (0.0F + var11), (double) (1.0F - var11));
                var4.draw();
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }

        var4.setTranslation(0.0D, 0.0D, 0.0D);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void rotateAndBlurSkybox(float par1) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.viewportTexture);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256,
                256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, false);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        byte var3 = 3;

        for (int var4 = 0; var4 < var3; ++var4) {
            var2.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (float) (var4 + 1));
            int var5 = this.width;
            int var6 = this.height;
            float var7 = (float) (var4 - var3 / 2) / 256.0F;
            var2.addVertexWithUV((double) var5, (double) var6,
                    (double) this.zLevel, (double) (0.0F + var7), 0.0D);
            var2.addVertexWithUV((double) var5, 0.0D, (double) this.zLevel,
                    (double) (1.0F + var7), 0.0D);
            var2.addVertexWithUV(0.0D, 0.0D, (double) this.zLevel,
                    (double) (1.0F + var7), 1.0D);
            var2.addVertexWithUV(0.0D, (double) var6, (double) this.zLevel,
                    (double) (0.0F + var7), 1.0D);
        }

        var2.draw();
        GL11.glColorMask(true, true, true, true);
    }

    public void RenderBackground(int par1, int par2, float par3) {
        if (!enabled) {
            return;
        }
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(par1, par2, par3);
        this.drawGradientRect(0, 0, this.width, this.height, -2130706433,
                16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0,
                Integer.MIN_VALUE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        float var5 = this.width > this.height ? 120.0F / (float) this.width
                : 120.0F / (float) this.height;
        float var6 = (float) this.height * var5 / 256.0F;
        float var7 = (float) this.width * var5 / 256.0F;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int var8 = this.width;
        int var9 = this.height;
        var4.addVertexWithUV(0.0D, (double) var9, (double) this.zLevel,
                (double) (0.5F - var6), (double) (0.5F + var7));
        var4.addVertexWithUV((double) var8, (double) var9,
                (double) this.zLevel, (double) (0.5F - var6),
                (double) (0.5F - var7));
        var4.addVertexWithUV((double) var8, 0.0D, (double) this.zLevel,
                (double) (0.5F + var6), (double) (0.5F - var7));
        var4.addVertexWithUV(0.0D, 0.0D, (double) this.zLevel,
                (double) (0.5F + var6), (double) (0.5F + var7));
        var4.draw();
        drawGradientRect(0, 0, width, height, 0x80ffffff, 0xffffff);
        drawGradientRect(0, 0, width, height, 0, 0x80000000);
    }
}

