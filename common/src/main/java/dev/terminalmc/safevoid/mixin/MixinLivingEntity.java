package dev.terminalmc.safevoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.terminalmc.safevoid.config.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @WrapMethod(method = "tick")
    private void onTick(Operation<Void> original) {
        // Don't complain unless you can demonstrate a fix for the
        // `defineSynchedData` dummy override error.
        Entity entity = ((Entity)(Object)this);
        Config.Options options = Config.get().options;
        if (options.enabled && (!options.playersOnly || entity instanceof Player)
                && options.wrapMax && entity.getY() > options.maxHeight) {
            Vec3 delta = entity.getDeltaMovement();
            entity.teleportTo(entity.getX(), options.minHeight, entity.getZ());
            entity.setDeltaMovement(delta);
            entity.hurtMarked = true;
        }
        original.call();
    }

    @WrapMethod(method = "onBelowWorld")
    private void onVoidTick(Operation<Void> original) {
        Entity entity = ((Entity)(Object)this);
        Config.Options options = Config.get().options;
        if (options.enabled && (!options.playersOnly || entity instanceof Player)) {
            if (options.wrapMin && entity.getY() < options.minHeight) {
                Vec3 delta = entity.getDeltaMovement();
                entity.teleportTo(entity.getX(), options.maxHeight, entity.getZ());
                entity.setDeltaMovement(delta);
                entity.hurtMarked = true;
            }
        } else {
            original.call();
        }
    }
}
