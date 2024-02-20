package me.basiqueevangelist.flashfreeze;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class FakeArmorStandEntity extends ArmorStandEntity {
    private NbtCompound originalData;

    public FakeArmorStandEntity(World world, NbtCompound originalData) {
        super(EntityType.ARMOR_STAND, world);
        this.originalData = originalData;

        setCustomName(Text.of("Unknown entity " + originalData.getString("id")));
        setCustomNameVisible(true);

        NbtList pos = originalData.getList("Pos", NbtElement.DOUBLE_TYPE);
        this.setPos(pos.getDouble(0), pos.getDouble(1), pos.getDouble(2));

        NbtList rot = originalData.getList("Rotation", NbtElement.FLOAT_TYPE);
        this.setRotation(rot.getFloat(0), rot.getFloat(1));

        equipStack(EquipmentSlot.HEAD, new ItemStack(Items.BARRIER));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put("OriginalData", originalData);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        originalData = nbt.getCompound("OriginalData");
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

        if (source.getAttacker().isSneaking()) {
            ItemStack droppedStack = new ItemStack(Items.ARMOR_STAND);
            var newEntityTag = originalData.copy();
            newEntityTag.remove("UUID");
            droppedStack.getOrCreateNbt().put("OriginalEntityData", newEntityTag);
            droppedStack.getOrCreateNbt().putInt("CustomModelData", 10000);
            droppedStack.setCustomName(Text.of("Unknown entity " + originalData.getString("id")));
            Block.dropStack(this.getWorld(), this.getBlockPos(), droppedStack);
        }
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
