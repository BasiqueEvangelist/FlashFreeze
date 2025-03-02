package me.basiqueevangelist.flashfreeze.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.access.PalettedContainerAccess;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public final class LookupCommand {
    private LookupCommand() {

    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        dispatcher.register(
            literal("flashfreeze")
                .then(literal("lookup")
                    .then(argument("pos", BlockPosArgument.blockPos())
                        .executes(LookupCommand::lookup)))
        );
    }

    private static int lookup(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        BlockPos pos = BlockPosArgument.getLoadedBlockPos(ctx, "pos");

        LevelChunk chunk = ctx.getSource().getLevel().getChunkAt(pos);
        LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(pos.getY()));

        UnknownBlockState unknown = (UnknownBlockState) ((PalettedContainerAccess) section.getStates()).getUnknown(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
        CompoundTag pendingBlockEntity = chunk.getBlockEntityNbt(pos);

        if (unknown != null) {
            ctx.getSource().sendSuccess(() -> Component.nullToEmpty("unknown block: " + unknown), false);
        } else {
            ctx.getSource().sendSuccess(() -> Component.nullToEmpty("not an unknown block"), false);
        }

        if (pendingBlockEntity != null) {
            ctx.getSource().sendSuccess(() -> Component.nullToEmpty("unknown block entity: " + pendingBlockEntity.getString("id")), false);
        } else {
            ctx.getSource().sendSuccess(() -> Component.nullToEmpty("not an unknown block entity"), false);
        }

        return 0;
    }
}
