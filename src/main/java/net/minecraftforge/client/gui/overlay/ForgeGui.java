package net.minecraftforge.client.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ForgeGui extends Gui {
    public int leftHeight = 39;
    public int rightHeight = 39;

    public ForgeGui(Minecraft mc) {
        super(mc, mc.getItemRenderer());
    }
}
