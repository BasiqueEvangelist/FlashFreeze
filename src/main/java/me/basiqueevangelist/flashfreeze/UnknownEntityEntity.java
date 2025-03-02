package me.basiqueevangelist.flashfreeze;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class UnknownEntityEntity extends Entity {
    private CompoundTag originalData;

    public UnknownEntityEntity(Level world, CompoundTag originalData) {
        super(FlashFreeze.UNKNOWN_ENTITY, world);
        this.originalData = originalData;

        setCustomName(Component.nullToEmpty(originalData.getString("id")));
        setCustomNameVisible(true);

        ListTag pos = originalData.getList("Pos", Tag.TAG_DOUBLE);
        this.setPos(pos.getDouble(0), pos.getDouble(1), pos.getDouble(2));

        ListTag rot = originalData.getList("Rotation", Tag.TAG_FLOAT);
        this.setRot(rot.getFloat(0), rot.getFloat(1));
    }

    public UnknownEntityEntity(EntityType<UnknownEntityEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        nbt.put("OriginalData", originalData);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        originalData = nbt.getCompound("OriginalData");
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand hand) {
        return InteractionResult.FAIL;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) return false;
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if (!source.isCreativePlayer()) return false;

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
        ((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.6666666666666666), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05);
        this.kill();

        return true;
    }

    @Override
    public boolean save(CompoundTag nbt) {
        nbt.merge(originalData);
        if (getVehicle() != null)
            nbt.put("Pos", newDoubleList(getVehicle().getX(), getY(), getVehicle().getZ()));
        else
            nbt.put("Pos", newDoubleList(getX(), getY(), getZ()));
        nbt.put("Rotation", newFloatList(getYRot(), getXRot()));
        return true;
    }

    public CompoundTag getOriginalData() {
        return originalData;
    }
}
