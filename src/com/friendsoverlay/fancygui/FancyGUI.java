package com.friendsoverlay.fancygui;

import java.io.*;
import java.util.*;

import net.minecraft.src.*;
import net.minecraft.client.*;

public class FancyGUI {

	private static HashMap<String, Class> fancyScreensMap = new HashMap<String, Class>();
	private static HashMap<String, String> obfuscatedClassNames = new HashMap<String, String>();
	private static Boolean loaded = false;
	private static Boolean loading = false;

	public synchronized static void addFancyScreen(String name, Class c) {
		load();
		if (!GuiFancyScreen.class.isAssignableFrom(c)) {
			return;
		}
		fancyScreensMap.put(name, c);
	}

	public synchronized static void removeFancyScreen(String name) {
		load();
		if (fancyScreensMap.containsKey(name)) {
			fancyScreensMap.remove(name);
		}
	}

	public synchronized static void removeFancyScreen(Class c) {
		load();
		if (fancyScreensMap.containsValue(c)) {
			fancyScreensMap.values().remove(c);
		}
	}

	public synchronized static Boolean replaceScreen(Minecraft mc, GuiScreen oldScreen) {
		load();
		GuiFancyScreen newScreen = null;
		if (oldScreen == null) {
			return false;
		}
		String className = getClassName(oldScreen.getClass());
		if (className.equalsIgnoreCase("GuiVideoSettings")) {
			Boolean optifineInstalled = false;
			try {
				Class.forName("net.minecraft.src.GuiPerformanceSettingsOF");
				optifineInstalled = true;
			} catch (Exception e) {
				optifineInstalled = false;
				try {
					Class.forName("GuiPerformanceSettingsOF");
					optifineInstalled = true;
				} catch (Exception e2) {
					optifineInstalled = false;
				}
			}
			if (optifineInstalled) {
				className = "GuiVideoSettingsOF";
			}
		}

		if (!fancyScreensMap.containsKey(className)) {
			return false;
		}

		try {
			Class c = fancyScreensMap.get(className);
			Object o = c.getConstructor(Minecraft.class, GuiScreen.class)
					.newInstance(mc, oldScreen);
			if (!(o instanceof GuiFancyScreen)) {
				return false;
			}
			newScreen = (GuiFancyScreen) o;
		} catch (Exception e) {
			return false;
		}

		if (newScreen != null) {
			mc.currentScreen = newScreen;
			mc.setIngameNotInFocus();
			ScaledResolution sr = new ScaledResolution(mc.gameSettings,
					mc.displayWidth, mc.displayHeight);
			int width = sr.getScaledWidth();
			int height = sr.getScaledHeight();
			mc.currentScreen.setWorldAndResolution(mc, width, height);
			mc.skipRenderWorld = false;
		}
		return true;
	}

	private synchronized static void load() {
		if (loaded || loading) {
			return;
		}
		loading = true;

		fancyScreensMap.clear();
		obfuscatedClassNames.clear();
		try {
			StringBuffer sb = new StringBuffer();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					FancyGUI.class.getResourceAsStream("/fancyscreens.txt"),
					"UTF-8"));
			for (int c = br.read(); c != -1; c = br.read())
				sb.append((char) c);

			String fancyScreens = sb.toString();
			String[] fancyScreensList = fancyScreens.split("\n");
			for (String fancyScreen : fancyScreensList) {
				String[] stringParts = fancyScreen.split(":");
				String origScreen = stringParts[0].trim();
				String newScreen = stringParts[1].trim();
				Class c = getClass(newScreen);
				if (c != null && GuiFancyScreen.class.isAssignableFrom(c)) {
					fancyScreensMap.put(origScreen, c);
				}
			}
		} catch (Exception e) {
		}
		loaded = true;
		loading = false;
	}

	public synchronized static Class getClass(String className) {
		load();
		Class c = null;
		if (obfuscatedClassNames.containsValue(className)) {
			for (Map.Entry<String, String> entry : obfuscatedClassNames
					.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(className)) {
					className = entry.getKey();
				}
			}
		}

		try {
			c = FancyGUI.class.forName(className);
		} catch (Exception e) {
			try {
				c = FancyGUI.class.forName("net.minecraft.src." + className);
			} catch (Exception e2) {
			}
		}

		return c;
	}

	public synchronized static String getClassName(Class c) {
		load();
		String className = null;
		className = c.getSimpleName();
		if (obfuscatedClassNames.containsKey(className)) {
			className = obfuscatedClassNames.get(className);
		}
		return className;
    }
}
