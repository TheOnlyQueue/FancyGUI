package com.friendsoverlay.fancygui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;

import java.lang.reflect.Field;

public abstract class GuiFancyScreen extends GuiScreen {
    protected Minecraft mc;
    protected GuiScreen oldScreen;

    public GuiFancyScreen(Minecraft mc, GuiScreen oldScreen) {
        this.mc = mc;
        this.oldScreen = oldScreen;
    }

	protected Object getOldValue(Integer i) {
		try {
			Field f = oldScreen.getClass().getDeclaredFields()[i];
			f.setAccessible(true);
			return f.get(oldScreen);
		} catch (Exception e) {
			return null;
		}
	}

    protected Object getOldValue(String name) {
        try {
            Field f = oldScreen.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(oldScreen);
        } catch (Exception e) {
            return null;
        }
    }
}

