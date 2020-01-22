package cx.rain.mc.forgemod.practicaladjustments.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Constructor;

public class GuiTriple {
    private Class<? extends Container> c = null;
    private Class<? extends GuiContainer> g = null;

    public GuiTriple(Class<? extends Container> container, Class<? extends GuiContainer> guiContainer) {
        c = container;
        g = guiContainer;
    }

    public Container getContainer(EntityPlayer player, BlockPos pos) {
        try {
            Constructor<?> constructor = c.getConstructor(EntityPlayer.class, BlockPos.class);
            return (Container) constructor.newInstance(player, pos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public GuiContainer getGuiContainer(EntityPlayer player, BlockPos pos) {
        try {
            Constructor<?> constructor = g.getConstructor(Container.class);
            return (GuiContainer) constructor.newInstance(getContainer(player, pos));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
