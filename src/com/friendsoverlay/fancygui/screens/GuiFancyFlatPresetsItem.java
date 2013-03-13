package com.friendsoverlay.fancygui.screens;

class GuiFancyFlatPresetsItem
{
    /** ID for the item used as icon for this preset. */
    public int iconId;

    /** Name for this preset. */
    public String presetName;

    /** Data for this preset. */
    public String presetData;

    public GuiFancyFlatPresetsItem(int par1, String par2Str, String par3Str)
    {
        this.iconId = par1;
        this.presetName = par2Str;
        this.presetData = par3Str;
    }
}
