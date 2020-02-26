package cx.rain.mc.forgemod.practicaladjustments.utility;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

public abstract class TileEntityBase extends TileEntity {
    public TileEntityBase() {
        super();
    }

    protected final void sync() {
        if (!this.world.isRemote) {
            SPacketUpdateTileEntity packet = this.getUpdatePacket();
            PlayerChunkMapEntry trackingEntry = ((WorldServer)this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
            if (trackingEntry != null) {
                for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
                    player.connection.sendPacket(packet);
                }
            }
        }
    }

    protected void readPacket(NBTTagCompound data) {
        if (this.world.isRemote) {
            this.world.markBlockRangeForRenderUpdate(this.pos, this.pos);
        }
    }

    protected NBTTagCompound writePacket(NBTTagCompound data) {
        return data;
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, -1, this.writePacket(new NBTTagCompound()));
    }

    @Override
    public final void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
        this.readPacket(packet.getNbtCompound());
    }
}
