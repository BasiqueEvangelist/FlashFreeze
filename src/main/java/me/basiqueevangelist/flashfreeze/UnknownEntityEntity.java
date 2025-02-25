package me.basiqueevangelist.flashfreeze;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class UnknownEntityEntity extends Entity {
    private NbtCompound originalData;

    public UnknownEntityEntity(World world, NbtCompound originalData) {
        super(FlashFreeze.UNKNOWN_ENTITY, world);
        this.originalData = originalData;

        setCustomName(Text.of(originalData.getString("id")));
        setCustomNameVisible(true);

        NbtList pos = originalData.getList("Pos", NbtElement.DOUBLE_TYPE);
        this.setPosition(pos.getDouble(0), pos.getDouble(1), pos.getDouble(2));

        NbtList rot = originalData.getList("Rotation", NbtElement.FLOAT_TYPE);
        this.setRotation(rot.getFloat(0), rot.getFloat(1));
    }

    public UnknownEntityEntity(EntityType<UnknownEntityEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("OriginalData", originalData);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        originalData = nbt.getCompound("OriginalData");
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        return ActionResult.FAIL;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getWorld().isClient || this.isRemoved()) return false;
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if (!source.isSourceCreativePlayer()) return false;

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
        ((ServerWorld)this.getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), this.getX(), this.getBodyY(0.6666666666666666), this.getZ(), 10, (double)(this.getWidth() / 4.0F), (double)(this.getHeight() / 4.0F), (double)(this.getWidth() / 4.0F), 0.05);
        this.kill();

        return true;
    }

    @Override
    public boolean saveNbt(NbtCompound nbt) {
        nbt.copyFrom(originalData);
        if (getVehicle() != null)
            nbt.put("Pos", toNbtList(getVehicle().getX(), getY(), getVehicle().getZ()));
        else
            nbt.put("Pos", toNbtList(getX(), getY(), getZ()));
        nbt.put("Rotation", toNbtList(getYaw(), getPitch()));
        return true;
    }

    public NbtCompound getOriginalData() {
        return originalData;
    }
}
