package dev.terminalmc.safevoid.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.terminalmc.safevoid.config.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public class MixinEntity {
    /**
     * Allow use of fireworks in the void.
     */
    @WrapMethod(method = "onBelowWorld")
    private void onVoidTick(Operation<Void> original) {
        if (((Entity)(Object)this) instanceof FireworkRocketEntity && Config.get().options.enabled) {
            // pass
        } else {
            original.call();
        }
    }
}
