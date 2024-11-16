/*
 * Copyright 2024 TerminalMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    /**
     * World-top wrap.
     */
    @WrapMethod(method = "tick")
    private void onTick(Operation<Void> original) {
        // Don't complain unless you can demonstrate a fix for the
        // `defineSynchedData` dummy override error.
        Entity entity = ((Entity)(Object)this);
        Config.Options options = Config.get().options;
        if (options.enabled && (!options.playersOnly || entity instanceof Player)
                && options.wrapMax && entity.getY() > options.maxHeight) {
            Vec3 delta = entity.getDeltaMovement();
            entity.teleportTo(entity.getX(), options.minHeight + 10, entity.getZ());
            entity.setDeltaMovement(delta);
            entity.hurtMarked = true;
        }
        original.call();
    }

    /**
     * Damage-prevention and world-bottom wrap.
     */
    @WrapMethod(method = "onBelowWorld")
    private void onVoidTick(Operation<Void> original) {
        Entity entity = ((Entity)(Object)this);
        Config.Options options = Config.get().options;
        if (options.enabled && (!options.playersOnly || entity instanceof Player)) {
            if (options.wrapMin && entity.getY() < options.minHeight) {
                Vec3 delta = entity.getDeltaMovement();
                entity.teleportTo(entity.getX(), options.maxHeight - 10, entity.getZ());
                entity.setDeltaMovement(delta);
                entity.hurtMarked = true;
            }
        } else {
            original.call();
        }
    }
}
