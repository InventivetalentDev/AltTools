package org.inventivetalent.alttools.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public class MinecraftClientMixin {

    @Inject(at = @At(value = "RETURN"), method = "getWindowTitle", cancellable = true)
    private void getWindowTitle(final CallbackInfoReturnable<String> info) {
        MinecraftClient client = (MinecraftClient) (Object) this;

        StringBuilder stringBuilder = new StringBuilder("Minecraft");

        // Version
        stringBuilder.append(" ");
        stringBuilder.append(SharedConstants.getGameVersion().getName());

        stringBuilder.append(" -");

        // User
        stringBuilder.append(" ");
        stringBuilder.append(client.getSession().getUsername());

        // Server
        Optional<String> address = Optional.ofNullable(client.getCurrentServerEntry()).map(s -> s.address);
        if (address.isPresent()) {
            stringBuilder.append(" ");
            stringBuilder.append("on");
            stringBuilder.append(" ");
            stringBuilder.append(address.get());
        }

        info.setReturnValue(stringBuilder.toString());
    }

}
