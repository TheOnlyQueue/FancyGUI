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
	public static Boolean optifineInstalled = false;
	public static Boolean shadersInstalled = false;

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

		if (className.equalsIgnoreCase("GuiVideoSettings") && optifineInstalled) {
			className = "GuiVideoSettingsOF";
		}

		if (!fancyScreensMap.containsKey(className)) {
			return false;
		}

		try {
			Class c = fancyScreensMap.get(className);
			Object o = c.getConstructor(Minecraft.class, GuiScreen.class).newInstance(mc, oldScreen);
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

		try {
			Class.forName("net.minecraft.src.GuiPerformanceSettingsOF");
			optifineInstalled = true;
		} catch (Throwable e) {
			optifineInstalled = false;
			try {
				Class.forName("GuiPerformanceSettingsOF");
				optifineInstalled = true;
			} catch (Throwable e2) {
				optifineInstalled = false;
			}
		}

		try {
			Class.forName("net.minecraft.src.Shaders");
			shadersInstalled = true;
		} catch (Throwable e) {
			shadersInstalled = false;
			try {
				Class.forName("Shaders");
				shadersInstalled = true;
			} catch (Throwable e2) {
				shadersInstalled = false;
			}
		}

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

		obfuscatedClassNames.put("avm", "CallableUpdatingScreenName");
		obfuscatedClassNames.put("avn", "CallableParticleScreenName");
		obfuscatedClassNames.put("avo", "CallableTickingScreenName");
		obfuscatedClassNames.put("awb", "LoadingScreenRenderer");
		obfuscatedClassNames.put("awc", "ScreenShotHelper");
		obfuscatedClassNames.put("awg", "GuiButton");
		obfuscatedClassNames.put("awh", "GuiNewChat");
		obfuscatedClassNames.put("awi", "ScreenChatOptions");
		obfuscatedClassNames.put("awj", "GuiChat");
		obfuscatedClassNames.put("awk", "GuiYesNo");
		obfuscatedClassNames.put("awl", "GuiControls");
		obfuscatedClassNames.put("awm", "GuiCreateFlatWorld");
		obfuscatedClassNames.put("awn", "GuiCreateFlatWorldListSlot");
		obfuscatedClassNames.put("awo", "GuiCreateWorld");
		obfuscatedClassNames.put("awp", "GuiGameOver");
		obfuscatedClassNames.put("awq", "GuiScreenDemo");
		obfuscatedClassNames.put("awr", "GuiScreenServerList");
		obfuscatedClassNames.put("aws", "GuiTextField");
		obfuscatedClassNames.put("awt", "GuiScreenAddServer");
		obfuscatedClassNames.put("awu", "GuiErrorScreen");
		obfuscatedClassNames.put("aww", "GuiIngame");
		obfuscatedClassNames.put("awx", "Gui");
		obfuscatedClassNames.put("awz", "GuiSleepMP");
		obfuscatedClassNames.put("axa", "GuiMultiplayer");
		obfuscatedClassNames.put("axb", "GuiSlotServer");
		obfuscatedClassNames.put("axd", "GuiButtonLanguage");
		obfuscatedClassNames.put("axe", "GuiLanguage");
		obfuscatedClassNames.put("axf", "GuiSlotLanguage");
		obfuscatedClassNames.put("axh", "GuiButtonLink");
		obfuscatedClassNames.put("axj", "GuiOptions");
		obfuscatedClassNames.put("axk", "GuiMemoryErrorScreen");
		obfuscatedClassNames.put("axl", "GuiIngameMenu");
		obfuscatedClassNames.put("axm", "GuiFlatPresets");
		obfuscatedClassNames.put("axn", "GuiFlatPresetsItem");
		obfuscatedClassNames.put("axo", "GuiFlatPresetsListSlot");
		obfuscatedClassNames.put("axp", "GuiProgress");
		obfuscatedClassNames.put("axq", "GuiRenameWorld");
		obfuscatedClassNames.put("axr", "GuiScreen");
		obfuscatedClassNames.put("axt", "GuiSlot");
		obfuscatedClassNames.put("axu", "GuiSelectWorld");
		obfuscatedClassNames.put("axv", "GuiWorldSlot");
		obfuscatedClassNames.put("axw", "GuiShareToLan");
		obfuscatedClassNames.put("axx", "GuiSlider");
		obfuscatedClassNames.put("axy", "GuiSmallButton");
		obfuscatedClassNames.put("axz", "GuiSnooper");
		obfuscatedClassNames.put("aya", "GuiSnooperList");
		obfuscatedClassNames.put("ayb", "GuiVideoSettings");
		obfuscatedClassNames.put("ayc", "GuiAchievement");
		obfuscatedClassNames.put("ayd", "GuiAchievements");
		obfuscatedClassNames.put("aye", "GuiStats");
		obfuscatedClassNames.put("ayf", "GuiSlotStatsBlock");
		obfuscatedClassNames.put("ayh", "GuiSlotStatsGeneral");
		obfuscatedClassNames.put("ayi", "GuiSlotStatsItem");
		obfuscatedClassNames.put("ayk", "GuiSlotStats");
		obfuscatedClassNames.put("ayl", "GuiContainer");
		obfuscatedClassNames.put("aym", "GuiBeacon");
		obfuscatedClassNames.put("ayn", "GuiBeaconButtonCancel");
		obfuscatedClassNames.put("ayo", "GuiBeaconButtonConfirm");
		obfuscatedClassNames.put("ayp", "GuiBeaconButtonPower");
		obfuscatedClassNames.put("ayq", "GuiBeaconButton");
		obfuscatedClassNames.put("ayr", "GuiScreenBook");
		obfuscatedClassNames.put("ays", "GuiButtonNextPage");
		obfuscatedClassNames.put("ayt", "GuiBrewingStand");
		obfuscatedClassNames.put("ayu", "GuiCommandBlock");
		obfuscatedClassNames.put("ayv", "GuiChest");
		obfuscatedClassNames.put("ayw", "GuiCrafting");
		obfuscatedClassNames.put("ayy", "GuiContainerCreative");
		obfuscatedClassNames.put("azd", "GuiEnchantment");
		obfuscatedClassNames.put("aze", "GuiFurnace");
		obfuscatedClassNames.put("azf", "GuiHopper");
		obfuscatedClassNames.put("azg", "GuiInventory");
		obfuscatedClassNames.put("azh", "GuiMerchant");
		obfuscatedClassNames.put("azi", "GuiButtonMerchant");
		obfuscatedClassNames.put("azj", "GuiRepair");
		obfuscatedClassNames.put("azk", "GuiEditSign");
		obfuscatedClassNames.put("azl", "GuiDispenser");
		obfuscatedClassNames.put("azm", "GuiScreenConfigureWorld");
		obfuscatedClassNames.put("azo", "GuiScreenCreateOnlineWorld");
		obfuscatedClassNames.put("azq", "GuiScreenDisconnectedOnline");
		obfuscatedClassNames.put("azr", "GuiScreenEditOnlineWorld");
		obfuscatedClassNames.put("azu", "GuiScreenInvite");
		obfuscatedClassNames.put("azv", "GuiScreenConfirmation");
		obfuscatedClassNames.put("azw", "GuiScreenLongRunningTask");
		obfuscatedClassNames.put("azy", "GuiScreenSelectLocation");
		obfuscatedClassNames.put("bag", "GuiScreenOnlineServers");
		obfuscatedClassNames.put("bah", "ThreadOnlineScreen");
		obfuscatedClassNames.put("bai", "GuiSlotOnlineServerList");
		obfuscatedClassNames.put("bak", "GuiScreenOnlineServersSubscreen");
		obfuscatedClassNames.put("bal", "GuiScreenResetWorld");
		obfuscatedClassNames.put("ban", "GuiScreenSubscription");
		obfuscatedClassNames.put("bap", "GuiParticle");
		obfuscatedClassNames.put("baq", "GuiWinGame");
		obfuscatedClassNames.put("bdm", "GuiConfirmOpenLink");
		obfuscatedClassNames.put("bdn", "GuiConnecting");
		obfuscatedClassNames.put("bdp", "GuiDisconnected");
		obfuscatedClassNames.put("bdw", "GuiPlayerInfo");
		obfuscatedClassNames.put("bdx", "GuiDownloadTerrain");
		obfuscatedClassNames.put("bfr", "CallableScreenName");
		obfuscatedClassNames.put("bft", "CallableScreenSize");
		obfuscatedClassNames.put("bjw", "GuiTexturePacks");
		obfuscatedClassNames.put("bjx", "GuiTexturePackSlot");
		obfuscatedClassNames.put("bkf", "GuiMainMenu");
		obfuscatedClassNames.put("bkg", "RunnableTitleScreen");
		obfuscatedClassNames.put("bkh", "ThreadTitleScreen");

		loaded = true;
		loading = false;
	}

	public synchronized static Class getClass(String className) {
		load();
		Class c = null;
		if (obfuscatedClassNames.containsValue(className)) {
			for (Map.Entry<String, String> entry : obfuscatedClassNames.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(className)) {
					className = entry.getKey();
				}
			}
		}

		try {
			c = Class.forName(className);
		} catch (Throwable e) {
			try {
				c = Class.forName("net.minecraft.src." + className);
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
