package org.bowserfartgif.cugmod.content.propulsion.jet.intake;

import dev.ryanhcode.sable.api.block.BlockSubLevelAssemblyListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bowserfartgif.cugmod.registry.DoodooBlockEntities;

public class IntakeBlockEntity extends BlockEntity implements BlockSubLevelAssemblyListener {
	Direction facing = getBlockState().getValue(IntakeBlock.FACING);

	public IntakeBlockEntity(BlockPos pos, BlockState state) {
		super(DoodooBlockEntities.INTAKE.get(), pos, state);
	}

	public Direction getBlockDirection() {
		return facing.getOpposite();
	}

	public static void tick(Level level, BlockPos pos, BlockState state, IntakeBlockEntity be) {

	}

	@Override
	public void afterMove(ServerLevel oldLevel, ServerLevel newLevel, BlockState state,
						  BlockPos oldPos, BlockPos newPos) {
	}
}