package com.swiftydevs.projectz.Common.Items;

import com.swiftydevs.projectz.Common.entity.RotatableBlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * A tool item that rotates rotatable block entities when used.
 */
public class RotateToolItem extends Item {
    public RotateToolItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        if (blockEntity instanceof RotatableBlockEntity) {
            RotatableBlockEntity rotatableBlockEntity = (RotatableBlockEntity) blockEntity;
            rotatableBlockEntity.rotate(Rotation.CLOCKWISE_90);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
