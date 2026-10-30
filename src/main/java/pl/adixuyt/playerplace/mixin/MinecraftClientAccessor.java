package pl.adixuyt.playerplace.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Accessor("crosshairTarget")
    HitResult playerplace$getCrosshairTarget();

    @Accessor("crosshairTarget")
    void playerplace$setCrosshairTarget(HitResult hitResult);
}
