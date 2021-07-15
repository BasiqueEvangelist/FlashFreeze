package me.basiqueevangelist.flashfreeze.item;

import eu.pb4.polymer.item.VirtualItem;
import me.basiqueevangelist.flashfreeze.FakeEntity;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import me.basiqueevangelist.flashfreeze.mixin.EntityTypeAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FakeEntityTemplateItem extends Item implements VirtualItem {
    public FakeEntityTemplateItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        if (direction == Direction.DOWN) return ActionResult.FAIL;

        World world = context.getWorld();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        ItemStack itemStack = context.getStack();
        Vec3d bottomCenter = Vec3d.ofBottomCenter(blockPos);
        Box box = FlashFreeze.FAKE_ENTITY.getDimensions().getBoxAt(bottomCenter.getX(), bottomCenter.getY(), bottomCenter.getZ());
        if (!world.isSpaceEmpty(null, box, entity -> true) || !world.getOtherEntities(null, box).isEmpty()) return ActionResult.FAIL;
        if (world instanceof ServerWorld serverWorld) {
            FakeEntity fakeEntity = new FakeEntity(serverWorld, context.getStack().getOrCreateSubTag("OriginalEntityData").copy());

            fakeEntity.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
            double originY = EntityTypeAccessor.callGetOriginY(world, blockPos, true, fakeEntity.getBoundingBox());

            fakeEntity.refreshPositionAndAngles(blockPos.getX() + 0.5, blockPos.getY() + originY, blockPos.getZ() + 0.5, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0F), 0.0F);

            float yaw = MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
            fakeEntity.refreshPositionAndAngles(fakeEntity.getX(), fakeEntity.getY(), fakeEntity.getZ(), yaw, 0.0F);
            serverWorld.spawnEntityAndPassengers(fakeEntity);
            world.playSound(null, fakeEntity.getX(), fakeEntity.getY(), fakeEntity.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, fakeEntity);
        }

        itemStack.decrement(1);
        return ActionResult.success(world.isClient);
    }

    @Override
    public Item getVirtualItem() {
        return Items.ARMOR_STAND;
    }
}
