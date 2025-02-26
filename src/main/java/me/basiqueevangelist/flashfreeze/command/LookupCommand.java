package me.basiqueevangelist.flashfreeze.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.access.PalettedContainerAccess;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class LookupCommand {
    private LookupCommand() {

    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
            literal("flashfreeze")
                .then(literal("lookup")
                    .then(argument("pos", BlockPosArgumentType.blockPos())
                        .executes(LookupCommand::lookup)))
        );
    }

    private static int lookup(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        BlockPos pos = BlockPosArgumentType.getLoadedBlockPos(ctx, "pos");

        WorldChunk chunk = ctx.getSource().getWorld().getWorldChunk(pos);
        ChunkSection section = chunk.getSection(chunk.getSectionIndex(pos.getY()));

        UnknownBlockState unknown = (UnknownBlockState) ((PalettedContainerAccess) section.getBlockStateContainer()).getUnknown(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
        NbtCompound pendingBlockEntity = chunk.getBlockEntityNbt(pos);

        if (unknown != null) {
            ctx.getSource().sendFeedback(() -> Text.of("unknown block: " + unknown), false);
        } else {
            ctx.getSource().sendFeedback(() -> Text.of("not an unknown block"), false);
        }

        if (pendingBlockEntity != null) {
            ctx.getSource().sendFeedback(() -> Text.of("unknown block entity: " + pendingBlockEntity.getString("id")), false);
        } else {
            ctx.getSource().sendFeedback(() -> Text.of("not an unknown block entity"), false);
        }

        return 0;
    }
}
