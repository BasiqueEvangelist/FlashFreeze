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
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class UnknownEntityEntity extends Entity {
    private CompoundTag originalData;

    public UnknownEntityEntity(Level world, CompoundTag originalData) {
        super(FlashFreeze.UNKNOWN_ENTITY.get(), world);
        this.originalData = originalData;

        setCustomName(Component.nullToEmpty(originalData.getString("id").orElseThrow()));
        setCustomNameVisible(true);

        Vec3 pos = originalData.read("Pos", Vec3.CODEC).orElse(Vec3.ZERO);;
        this.setPos(pos);

        Vec2 rot = originalData.read("Rotation", Vec2.CODEC).orElse(Vec2.ZERO);
        this.setRot(rot.y, rot.x);
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
        originalData = nbt.getCompound("OriginalData").orElseGet(CompoundTag::new);
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
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) return false;
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill(level);
            return false;
        }
        if (!source.isCreativePlayer()) return false;

        level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.6666666666666666), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05);
        this.kill(level);

        return true;
    }

    @Override
    public boolean save(CompoundTag nbt) {
        nbt.merge(originalData);

        if (getVehicle() != null) {
            nbt.store("Pos", Vec3.CODEC, new Vec3(getVehicle().getX(), this.getY(), getVehicle().getZ()));
        } else {
            nbt.store("Pos", Vec3.CODEC, this.position());
        }

        nbt.store("Rotation", Vec2.CODEC, new Vec2(this.getYRot(), this.getXRot()));
        return true;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    public CompoundTag getOriginalData() {
        return originalData;
    }
}
