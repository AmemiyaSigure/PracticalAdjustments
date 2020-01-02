package cx.rain.mc.forgemod.practicaladjustments.items;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class Pestle extends Item {
    public Pestle() {
        super(new Properties());
        this.setRegistryName(new ResourceLocation(PracticalAdjustments.MODID, "pestle"));
    }
}
