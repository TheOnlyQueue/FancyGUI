package com.friendsoverlay.fancygui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SaveList {
    public SaveList() {
    }

    public static List getSaveList(Minecraft minecraft) throws AnvilConverterException {
		ISaveFormat isaveformat = minecraft.getSaveLoader();
		List list = isaveformat.getSaveList();
		Collections.sort(list, new Comparator() {
			public int compare(Object obj, Object obj1) {
				return ((SaveFormatComparator) obj)
						.compareTo((SaveFormatComparator) obj1);
			}
		});
		return list;
    }
}
