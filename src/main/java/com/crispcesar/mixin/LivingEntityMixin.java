package com.crispcesar.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tryUseTotem", at = @At("RETURN"), cancellable = true)
    private void onTotemUse(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Solo ejecutamos si el Tótem fue efectivamente usado
        if (entity instanceof ServerPlayerEntity player && info.getReturnValue()) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                // Mensaje de anuncio de uso del Tótem
                Text message = Text.literal("★ " + player.getEntityName() + " ha usado un Tótem")
                        .formatted(Formatting.YELLOW, Formatting.BOLD);
                server.getPlayerManager().broadcast(message, false);
            }
        }
    }
}