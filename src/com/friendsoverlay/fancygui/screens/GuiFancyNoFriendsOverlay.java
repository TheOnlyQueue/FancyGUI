package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import java.net.URI;

public class GuiFancyNoFriendsOverlay extends GuiFancyScreen {

    private final GuiScreen parentScreen;
    private GuiFancyRotatingBackground bg;

    public GuiFancyNoFriendsOverlay(GuiScreen parentScreen) {
        super(Minecraft.getMinecraft(), null);
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
        this.buttonList.add(new GuiFancyButton(0, this.width / 2, this.height - 38, "Back", 3));
        this.buttonList.add(new GuiFancyButton(1, this.width / 2, this.height - 58, "Open in Browser", 3));
    }

    @Override
    public void updateScreen() {
        bg.Update();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        bg.RenderBackground(par1, par2, par3);
        drawCenteredString(fontRenderer, "You have to install", this.width / 2, 20, 0xFFFFFF);
        drawCenteredString(fontRenderer, "FriendsOverlay", this.width / 2, 30, 0xFFFFFF);
        drawCenteredString(fontRenderer, "to use the Friends Function!", this.width / 2, 40, 0xFFFFFF);
        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            mc.displayGuiScreen(parentScreen);
        }
        if (par1GuiButton.id == 1) {
            try {
                String url = "http://friendsoverlay.com/";
                if (url != "") {
                    openURL(new URI(url));
                }
            } catch (Exception e) {
            }
        }
    }

    private void openURL(URI par1URI) {
        try {
            Class var2 = Class.forName("java.awt.Desktop");
            Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
            var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[]{par1URI });
        } catch (Throwable var4) {
            var4.printStackTrace();
        }
    }
}
