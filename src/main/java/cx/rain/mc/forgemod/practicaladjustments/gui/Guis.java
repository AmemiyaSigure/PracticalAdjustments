package cx.rain.mc.forgemod.practicaladjustments.gui;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.container.ContainerDeCurseTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Guis implements IGuiHandler {
    public static final Map<String, Class<? extends Container>> GUIS = new HashMap<>();

    static {
        GUIS.put("de_curse_table", ContainerDeCurseTable.class);
    }

    public Guis() {
        NetworkRegistry.INSTANCE.registerGuiHandler(PracticalAdjustments.INSTANCE, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        try {
            Constructor<?> constructor = GUIS.values().toArray()[ID].getClass().getConstructor();
            return constructor.newInstance();
        } catch (Exception ignored) {

        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        try {
            Constructor<?> constructor = GUIS.values().toArray()[ID].getClass().getConstructor();
            return constructor.newInstance();
        } catch (Exception ignored) {

        }
        return null;
    }
}
