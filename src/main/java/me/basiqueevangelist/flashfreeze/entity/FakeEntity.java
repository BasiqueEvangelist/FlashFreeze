package me.basiqueevangelist.flashfreeze.entity;

import eu.pb4.polymer.entity.VirtualEntity;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class FakeEntity extends Entity implements VirtualEntity {
    private NbtCompound originalData = new NbtCompound();

    public FakeEntity(EntityType<? extends FakeEntity> entityType, World world) {
        super(entityType, world);
    }

    public FakeEntity(World world, NbtCompound originalData) {
        super(FlashFreeze.FAKE_ENTITY, world);
        this.originalData = originalData;

        setCustomName(new LiteralText("Unknown entity " + originalData.getString("id")));
        setCustomNameVisible(true);

        NbtList pos = originalData.getList("Pos", NbtElement.DOUBLE_TYPE);
        this.setPos(pos.getDouble(0), pos.getDouble(1), pos.getDouble(2));

        NbtList rot = originalData.getList("Rotation", NbtElement.FLOAT_TYPE);
        this.setRotation(rot.getFloat(0), rot.getFloat(1));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("OriginalData", originalData);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        originalData = nbt.getCompound("OriginalData");
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    public EntityType<?> getVirtualEntityType() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.world.isClient || this.isRemoved()) return false;
        if (DamageSource.OUT_OF_WORLD.equals(source)) {
            this.kill();
            return false;
        }
        if (!source.isSourceCreativePlayer()) return false;

        if (source.getAttacker().isSneaking()) {
            ItemStack droppedStack = new ItemStack(FlashFreeze.FAKE_ENTITY_TEMPLATE);
            var newEntityTag = originalData.copy();
            newEntityTag.remove("UUID");
            droppedStack.getOrCreateTag().put("OriginalEntityData", newEntityTag);
            droppedStack.setCustomName(new LiteralText("Unknown entity " + originalData.getString("id")));
            Block.dropStack(this.world, this.getBlockPos(), droppedStack);
        }
        world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);

        ((ServerWorld)this.world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), this.getX(), this.getBodyY(0.6666666666666666), this.getZ(), 10, this.getWidth() / 4.0F, this.getHeight() / 4.0F, this.getWidth() / 4.0F, 0.05);
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
