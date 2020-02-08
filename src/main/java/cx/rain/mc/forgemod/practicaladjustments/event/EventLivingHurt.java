package cx.rain.mc.forgemod.practicaladjustments.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventLivingHurt {
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof EntityAnimal)) {
            return;
        }

        if (!(event.getSource().getImmediateSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer source = (EntityPlayer) event.getSource().getImmediateSource();

        EntityAnimal entity = (EntityAnimal) event.getEntity();

        List<EntityAnimal> animals = entity.world.getEntitiesWithinAABB(entity.getClass(),
                entity.getEntityBoundingBox().grow(8.0D, 2.0D, 8.0D),
                EntitySelectors.IS_ALIVE);

        for (EntityAnimal a : animals) {
            a.setRevengeTarget(source);
        }
    }
}
