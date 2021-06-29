package me.basiqueevangelist.palettebypass.mixin;

import me.basiqueevangelist.palettebypass.FakeChunk;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    private void dontTickInFakeChunks(Entity entity, CallbackInfo ci) {
        if (FakeChunk.isPosFake(entity.world, entity.getBlockPos()))
            ci.cancel();
    }

    @Inject(method = "canPlayerModifyAt", at = @At("HEAD"), cancellable = true)
    private void dontModifyFakeChunks(PlayerEntity player, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (FakeChunk.isPosFake(this, pos))
            cir.setReturnValue(false);
    }
}
