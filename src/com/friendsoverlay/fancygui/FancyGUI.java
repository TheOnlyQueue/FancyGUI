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
			} catch (Throwable e) {
				optifineInstalled = false;
				try {
					Class.forName("GuiPerformanceSettingsOF");
					optifineInstalled = true;
				} catch (Throwable e2) {
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

		obfuscatedClassNames.put("avm", "net/minecraft/src/CallableUpdatingScreenName");
		obfuscatedClassNames.put("avn", "net/minecraft/src/CallableParticleScreenName");
		obfuscatedClassNames.put("avo", "net/minecraft/src/CallableTickingScreenName");
		obfuscatedClassNames.put("awb", "net/minecraft/src/LoadingScreenRenderer");
		obfuscatedClassNames.put("awc", "net/minecraft/src/ScreenShotHelper");
		obfuscatedClassNames.put("awg", "net/minecraft/src/GuiButton");
		obfuscatedClassNames.put("awh", "net/minecraft/src/GuiNewChat");
		obfuscatedClassNames.put("awi", "net/minecraft/src/ScreenChatOptions");
		obfuscatedClassNames.put("awj", "net/minecraft/src/GuiChat");
		obfuscatedClassNames.put("awk", "net/minecraft/src/GuiYesNo");
		obfuscatedClassNames.put("awl", "net/minecraft/src/GuiControls");
		obfuscatedClassNames.put("awm", "net/minecraft/src/GuiCreateFlatWorld");
		obfuscatedClassNames.put("awn", "net/minecraft/src/GuiCreateFlatWorldListSlot");
		obfuscatedClassNames.put("awo", "net/minecraft/src/GuiCreateWorld");
		obfuscatedClassNames.put("awp", "net/minecraft/src/GuiGameOver");
		obfuscatedClassNames.put("awq", "net/minecraft/src/GuiScreenDemo");
		obfuscatedClassNames.put("awr", "net/minecraft/src/GuiScreenServerList");
		obfuscatedClassNames.put("aws", "net/minecraft/src/GuiTextField");
		obfuscatedClassNames.put("awt", "net/minecraft/src/GuiScreenAddServer");
		obfuscatedClassNames.put("awu", "net/minecraft/src/GuiErrorScreen");
		obfuscatedClassNames.put("aww", "net/minecraft/src/GuiIngame");
		obfuscatedClassNames.put("awx", "net/minecraft/src/Gui");
		obfuscatedClassNames.put("awz", "net/minecraft/src/GuiSleepMP");
		obfuscatedClassNames.put("axa", "net/minecraft/src/GuiMultiplayer");
		obfuscatedClassNames.put("axb", "net/minecraft/src/GuiSlotServer");
		obfuscatedClassNames.put("axd", "net/minecraft/src/GuiButtonLanguage");
		obfuscatedClassNames.put("axe", "net/minecraft/src/GuiLanguage");
		obfuscatedClassNames.put("axf", "net/minecraft/src/GuiSlotLanguage");
		obfuscatedClassNames.put("axh", "net/minecraft/src/GuiButtonLink");
		obfuscatedClassNames.put("axj", "net/minecraft/src/GuiOptions");
		obfuscatedClassNames.put("axk", "net/minecraft/src/GuiMemoryErrorScreen");
		obfuscatedClassNames.put("axl", "net/minecraft/src/GuiIngameMenu");
		obfuscatedClassNames.put("axm", "net/minecraft/src/GuiFlatPresets");
		obfuscatedClassNames.put("axn", "net/minecraft/src/GuiFlatPresetsItem");
		obfuscatedClassNames.put("axo", "net/minecraft/src/GuiFlatPresetsListSlot");
		obfuscatedClassNames.put("axp", "net/minecraft/src/GuiProgress");
		obfuscatedClassNames.put("axq", "net/minecraft/src/GuiRenameWorld");
		obfuscatedClassNames.put("axr", "net/minecraft/src/GuiScreen");
		obfuscatedClassNames.put("axt", "net/minecraft/src/GuiSlot");
		obfuscatedClassNames.put("axu", "net/minecraft/src/GuiSelectWorld");
		obfuscatedClassNames.put("axv", "net/minecraft/src/GuiWorldSlot");
		obfuscatedClassNames.put("axw", "net/minecraft/src/GuiShareToLan");
		obfuscatedClassNames.put("axx", "net/minecraft/src/GuiSlider");
		obfuscatedClassNames.put("axy", "net/minecraft/src/GuiSmallButton");
		obfuscatedClassNames.put("axz", "net/minecraft/src/GuiSnooper");
		obfuscatedClassNames.put("aya", "net/minecraft/src/GuiSnooperList");
		obfuscatedClassNames.put("ayb", "net/minecraft/src/GuiVideoSettings");
		obfuscatedClassNames.put("ayc", "net/minecraft/src/GuiAchievement");
		obfuscatedClassNames.put("ayd", "net/minecraft/src/GuiAchievements");
		obfuscatedClassNames.put("aye", "net/minecraft/src/GuiStats");
		obfuscatedClassNames.put("ayf", "net/minecraft/src/GuiSlotStatsBlock");
		obfuscatedClassNames.put("ayh", "net/minecraft/src/GuiSlotStatsGeneral");
		obfuscatedClassNames.put("ayi", "net/minecraft/src/GuiSlotStatsItem");
		obfuscatedClassNames.put("ayk", "net/minecraft/src/GuiSlotStats");
		obfuscatedClassNames.put("ayl", "net/minecraft/src/GuiContainer");
		obfuscatedClassNames.put("aym", "net/minecraft/src/GuiBeacon");
		obfuscatedClassNames.put("ayn", "net/minecraft/src/GuiBeaconButtonCancel");
		obfuscatedClassNames.put("ayo", "net/minecraft/src/GuiBeaconButtonConfirm");
		obfuscatedClassNames.put("ayp", "net/minecraft/src/GuiBeaconButtonPower");
		obfuscatedClassNames.put("ayq", "net/minecraft/src/GuiBeaconButton");
		obfuscatedClassNames.put("ayr", "net/minecraft/src/GuiScreenBook");
		obfuscatedClassNames.put("ays", "net/minecraft/src/GuiButtonNextPage");
		obfuscatedClassNames.put("ayt", "net/minecraft/src/GuiBrewingStand");
		obfuscatedClassNames.put("ayu", "net/minecraft/src/GuiCommandBlock");
		obfuscatedClassNames.put("ayv", "net/minecraft/src/GuiChest");
		obfuscatedClassNames.put("ayw", "net/minecraft/src/GuiCrafting");
		obfuscatedClassNames.put("ayy", "net/minecraft/src/GuiContainerCreative");
		obfuscatedClassNames.put("azd", "net/minecraft/src/GuiEnchantment");
		obfuscatedClassNames.put("aze", "net/minecraft/src/GuiFurnace");
		obfuscatedClassNames.put("azf", "net/minecraft/src/GuiHopper");
		obfuscatedClassNames.put("azg", "net/minecraft/src/GuiInventory");
		obfuscatedClassNames.put("azh", "net/minecraft/src/GuiMerchant");
		obfuscatedClassNames.put("azi", "net/minecraft/src/GuiButtonMerchant");
		obfuscatedClassNames.put("azj", "net/minecraft/src/GuiRepair");
		obfuscatedClassNames.put("azk", "net/minecraft/src/GuiEditSign");
		obfuscatedClassNames.put("azl", "net/minecraft/src/GuiDispenser");
		obfuscatedClassNames.put("azm", "net/minecraft/src/GuiScreenConfigureWorld");
		obfuscatedClassNames.put("azo", "net/minecraft/src/GuiScreenCreateOnlineWorld");
		obfuscatedClassNames.put("azq", "net/minecraft/src/GuiScreenDisconnectedOnline");
		obfuscatedClassNames.put("azr", "net/minecraft/src/GuiScreenEditOnlineWorld");
		obfuscatedClassNames.put("azu", "net/minecraft/src/GuiScreenInvite");
		obfuscatedClassNames.put("azv", "net/minecraft/src/GuiScreenConfirmation");
		obfuscatedClassNames.put("azw", "net/minecraft/src/GuiScreenLongRunningTask");
		obfuscatedClassNames.put("azy", "net/minecraft/src/GuiScreenSelectLocation");
		obfuscatedClassNames.put("bag", "net/minecraft/src/GuiScreenOnlineServers");
		obfuscatedClassNames.put("bah", "net/minecraft/src/ThreadOnlineScreen");
		obfuscatedClassNames.put("bai", "net/minecraft/src/GuiSlotOnlineServerList");
		obfuscatedClassNames.put("bak", "net/minecraft/src/GuiScreenOnlineServersSubscreen");
		obfuscatedClassNames.put("bal", "net/minecraft/src/GuiScreenResetWorld");
		obfuscatedClassNames.put("ban", "net/minecraft/src/GuiScreenSubscription");
		obfuscatedClassNames.put("bap", "net/minecraft/src/GuiParticle");
		obfuscatedClassNames.put("baq", "net/minecraft/src/GuiWinGame");
		obfuscatedClassNames.put("bdm", "net/minecraft/src/GuiConfirmOpenLink");
		obfuscatedClassNames.put("bdn", "net/minecraft/src/GuiConnecting");
		obfuscatedClassNames.put("bdp", "net/minecraft/src/GuiDisconnected");
		obfuscatedClassNames.put("bdw", "net/minecraft/src/GuiPlayerInfo");
		obfuscatedClassNames.put("bdx", "net/minecraft/src/GuiDownloadTerrain");
		obfuscatedClassNames.put("bfr", "net/minecraft/src/CallableScreenName");
		obfuscatedClassNames.put("bft", "net/minecraft/src/CallableScreenSize");
		obfuscatedClassNames.put("bjw", "net/minecraft/src/GuiTexturePacks");
		obfuscatedClassNames.put("bjx", "net/minecraft/src/GuiTexturePackSlot");
		obfuscatedClassNames.put("bkf", "net/minecraft/src/GuiMainMenu");
		obfuscatedClassNames.put("bkg", "net/minecraft/src/RunnableTitleScreen");
		obfuscatedClassNames.put("bkh", "net/minecraft/src/ThreadTitleScreen");

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
