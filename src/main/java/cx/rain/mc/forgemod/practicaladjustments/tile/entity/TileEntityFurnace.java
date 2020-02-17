package cx.rain.mc.forgemod.practicaladjustments.tile.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityFurnace extends TileEntity implements ITickable {
    private boolean isExpend = false;

    public TileEntityFurnace() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        isExpend = compound.getBoolean("expend");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("expend", isExpend);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {

    }

    public void setExpend(boolean expend) {
        isExpend = expend;
    }

    public boolean isExpend() {
        return isExpend;
    }
}
