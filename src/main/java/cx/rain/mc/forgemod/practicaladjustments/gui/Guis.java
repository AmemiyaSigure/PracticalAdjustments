package cx.rain.mc.forgemod.practicaladjustments.gui;

import cx.rain.mc.forgemod.practicaladjustments.PracticalAdjustments;
import cx.rain.mc.forgemod.practicaladjustments.gui.container.ContainerDeCurseTable;
import cx.rain.mc.forgemod.practicaladjustments.gui.container.ContainerFurnace;
import cx.rain.mc.forgemod.practicaladjustments.utility.GuiTriple;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Guis implements IGuiHandler {
    public static final Map<String, GuiTriple> GUIS = new HashMap<>();

    static {
        if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            GUIS.put("de_curse_table",
                    new GuiTriple(ContainerDeCurseTable.class, null));
            GUIS.put("furnace",
                    new GuiTriple(ContainerFurnace.class, null));
        } else {
            GUIS.put("de_curse_table",
                    new GuiTriple(ContainerDeCurseTable.class, GuiDeCurseTable.class));
            GUIS.put("furnace",
                    new GuiTriple(ContainerFurnace.class, GuiFurnace.class));
        }
    }

    public Guis() {
        PracticalAdjustments.INSTANCE.getLogger().info("Registered GUIs.");
        NetworkRegistry.INSTANCE.registerGuiHandler(PracticalAdjustments.INSTANCE, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return ((GuiTriple) GUIS.values().toArray()[ID]).getContainer(player, new BlockPos(x, y, z));
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return ((GuiTriple) GUIS.values().toArray()[ID]).getGuiContainer(player, new BlockPos(x, y, z));
    }
}
