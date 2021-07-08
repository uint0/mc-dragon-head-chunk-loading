package net.uint0.dragonheadchunkloader.mixin;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.BlockPos;


@Mixin(SkullBlockEntity.class)
public abstract class DragonHeadMixin extends BlockEntity { 
    public DragonHeadMixin(BlockEntityType<?> type) {
        super(type);
    }

    @Shadow
    private boolean powered;

    private boolean isLoadingChunk;

    @Inject(method="tick", at=@At(value="TAIL"))
	private void tick(CallbackInfo ci) {
        this.updateChunkLoading(this.powered);
    }

    @Override
    public void markRemoved() {
        super.markRemoved();
        this.updateChunkLoading(false);
    }

    private void updateChunkLoading(boolean requestedLoading) {
        if(this.isLoadingChunk == requestedLoading) { return; }

        BlockPos pos = this.getPos();
        ServerWorld serverWorld = (ServerWorld)this.getWorld();

        serverWorld.setChunkForced(pos.getX() >> 4, pos.getZ() >> 4, requestedLoading);
        this.isLoadingChunk = requestedLoading;
    }
}
