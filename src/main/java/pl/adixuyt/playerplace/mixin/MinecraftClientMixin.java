package pl.adixuyt.playerplace.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * PlayerPlace - always on (no toggle, no keybind, no GUI).
 * When the crosshair is over another player and you right-click, the target is
 * temporarily swapped for the block raycast result behind that player.
 */
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Unique private HitResult playerplace$originalTarget;
    @Unique private boolean playerplace$replacedTarget;

    @Inject(method = "doItemUse", at = @At("HEAD"))
    private void playerplace$useBlockBehindPlayer(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        playerplace$replacedTarget = false;
        playerplace$originalTarget = null;

        if (client.player == null || client.world == null) return;

        MinecraftClientAccessor accessor = (MinecraftClientAccessor) client;
        HitResult target = accessor.playerplace$getCrosshairTarget();
        if (!(target instanceof EntityHitResult entityHit)
                || !(entityHit.getEntity() instanceof PlayerEntity)) return;

        double reach = client.player.getBlockInteractionRange();
        Vec3d start = client.player.getCameraPosVec(1.0F);
        Vec3d end = start.add(client.player.getRotationVec(1.0F).multiply(reach));
        BlockHitResult blockHit = client.world.raycast(new RaycastContext(
                start, end,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                client.player
        ));

        if (blockHit.getType() == HitResult.Type.BLOCK) {
            playerplace$originalTarget = target;
            accessor.playerplace$setCrosshairTarget(blockHit);
            playerplace$replacedTarget = true;
        }
    }

    @Inject(method = "doItemUse", at = @At("RETURN"))
    private void playerplace$restoreTarget(CallbackInfo ci) {
        if (!playerplace$replacedTarget) return;
        MinecraftClient client = (MinecraftClient) (Object) this;
        ((MinecraftClientAccessor) client).playerplace$setCrosshairTarget(playerplace$originalTarget);
        playerplace$originalTarget = null;
        playerplace$replacedTarget = false;
    }
}
