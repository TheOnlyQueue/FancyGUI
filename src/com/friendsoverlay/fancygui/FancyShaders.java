package com.friendsoverlay.fancygui;

import net.minecraft.src.*;

import java.lang.reflect.*;

public class FancyShaders {
	public static Object getField(String name) {
		try {
			Field f = Shaders.class.getDeclaredField(name);
			f.setAccessible(true);
			return f.get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String versiontostring(int var0)
	{
		String var1 = Integer.toString(var0);
		return Integer.toString(Integer.parseInt(var1.substring(1, 3))) + "." + Integer.toString(Integer.parseInt(var1.substring(3, 5))) + "." + Integer.toString(Integer.parseInt(var1.substring(5)));
	}
}
