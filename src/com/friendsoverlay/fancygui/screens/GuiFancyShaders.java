package com.friendsoverlay.fancygui.screens;

import com.friendsoverlay.fancygui.*;
import net.minecraft.client.*;
import net.minecraft.src.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.io.*;
import java.net.*;

public class GuiFancyShaders extends GuiFancyScreen {
	protected GuiScreen parentGui;
	private int updateTimer = -1;
	public boolean needReinit;
	private GuiFancySlotShaders shaderList;
	private GuiFancyRotatingBackground bg;

	public GuiFancyShaders(Minecraft mc, GuiScreen oldScreen) {
		super(mc, oldScreen);
		this.parentGui = (GuiScreen) getOldValue(0);
	}

	public void initGui()
	{
		if (Shaders.shadersConfig == null)
		{
			Shaders.loadConfig();
		}

		this.buttonList.add(new GuiFancyButton(9, this.width / 2 + 36 + 75, 30, 100, 20, "Cloud shadow: " + Shaders.configCloudShadow, 3));
		this.buttonList.add(new GuiFancyButton(4, this.width / 2 + 36 + 75, 54, 100, 20, "New block breaking: " + Shaders.dtweak, 3));
		this.buttonList.add(new GuiFancyButton(10, this.width / 2 + 36 + 75, 78, 100, 20, "Hand depth: " + Shaders.configHandDepthMul, 3));
		this.buttonList.add(new GuiFancyButton(5, this.width / 4 - 64 + 75, this.height - 55, 100, 20, "Open shaderpacks folder", 3));
		this.buttonList.add(new GuiFancyButton(6, this.width / 2 + 36 + 75, this.height - 25, 100, 20, "Done", 3));
		this.shaderList = new GuiFancySlotShaders(this);
		this.shaderList.registerScrollButtons(this.buttonList, 7, 8);
		this.needReinit = false;
		bg = new GuiFancyRotatingBackground(mc, width, height, zLevel);
	}

	protected void actionPerformed(GuiButton var1)
	{
		if (var1.enabled)
		{
			switch (var1.id)
			{
				case 4:
					Shaders.dtweak = !Shaders.dtweak;
					var1.displayString = "New block breaking: " + Shaders.dtweak;
					break;

				case 5:
					if (Minecraft.getOs() == EnumOS.MACOS)
					{
						try
						{
							Runtime.getRuntime().exec(new String[]{"/usr/bin/open", ((File) FancyShaders.getField("shaderpacksdir")).getAbsolutePath()});
							return;
						}
						catch (IOException var8)
						{
							var8.printStackTrace();
						}
					}
					else if (Minecraft.getOs() == EnumOS.WINDOWS)
					{
						String var9 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] {((File) FancyShaders.getField("shaderpacksdir")).getAbsolutePath()});

						try
						{
							Runtime.getRuntime().exec(var9);
							return;
						}
						catch (IOException var7)
						{
							var7.printStackTrace();
						}
					}

					boolean var10 = false;

					try
					{
						Class var3 = Class.forName("java.awt.Desktop");
						Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
						var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {(new File(Minecraft.getMinecraftDir(), (String) FancyShaders.getField("shaderpacksdirname"))).toURI()});
					}
					catch (Throwable var6)
					{
						var6.printStackTrace();
						var10 = true;
					}

					if (var10)
					{
						System.out.println("Opening via system class!");
						Sys.openURL("file://" + ((File) FancyShaders.getField("shaderpacksdir")).getAbsolutePath());
					}

					break;

				case 6:
					new File((File) FancyShaders.getField("shadersdir"), "current.cfg");

					try
					{
						Shaders.storeConfig();
					}
					catch (Exception var5)
					{
						;
					}

					if (this.needReinit)
					{
						this.needReinit = false;
						Shaders.loadShaderPack();
						Shaders.uninit();
						this.mc.renderGlobal.loadRenderers();
					}

					this.mc.displayGuiScreen(this.parentGui);
					break;

				case 7:
				case 8:
				default:
					this.shaderList.actionPerformed(var1);
					break;

				case 9:
					Shaders.configCloudShadow = !Shaders.configCloudShadow;
					var1.displayString = "Cloud shadow: " + Shaders.configCloudShadow;
					break;

				case 10:
					float var2 = Shaders.configHandDepthMul / 2.0F;

					if (var2 < 0.0625F)
					{
						var2 = 1.0F;
					}

					Shaders.configHandDepthMul = var2;
					var1.displayString = "Hand depth: " + Shaders.configHandDepthMul;
			}
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int var1, int var2, float var3)
	{
		bg.RenderBackground(var1, var2, var3);
		drawBackground();
		this.shaderList.drawScreen(var1, var2, var3);

		if (this.updateTimer <= 0)
		{
			this.shaderList.updateList();
			this.updateTimer += 20;
		}

		this.drawCenteredString(this.fontRenderer, "Shaders ", this.width / 2, 16, 16777215);
		this.drawCenteredString(this.fontRenderer, " v" + FancyShaders.versiontostring(1014600), this.width - 30, 10, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, "( Place zipped Shader files here. )", this.width / 4 + 10, this.height - 26, 8421504);
		super.drawScreen(var1, var2, var3);
	}

	private void drawBackground() {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator var2 = Tessellator.instance;
		this.mc.renderEngine.bindTexture("/gui/background.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var3 = 32.0F;
		var2.startDrawingQuads();
		var2.setColorOpaque_I(4210752);
		var2.addVertexWithUV(this.width / 2 + 20, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / var3 + (float)0));
		var2.addVertexWithUV((double)this.width + this.width / 2 + 20, (double)this.height, 0.0D, (double)((float)this.width / var3), (double)((float)this.height / var3 + (float)0));
		var2.addVertexWithUV((double)this.width + this.width / 2 + 20, 0.0D, 0.0D, (double)((float)this.width / var3), (double)0);
		var2.addVertexWithUV(this.width / 2 + 20, 0.0D, 0.0D, 0.0D, (double)0);
		var2.draw();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen()
	{
		super.updateScreen();
		--this.updateTimer;
		bg.Update();
	}

	@Override
	public void drawDefaultBackground() {
	}
}
