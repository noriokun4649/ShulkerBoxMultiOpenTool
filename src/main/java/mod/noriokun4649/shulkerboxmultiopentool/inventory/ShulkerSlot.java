package mod.noriokun4649.shulkerboxmultiopentool.inventory;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ShulkerSlot extends Slot {
    public ShulkerSlot(final IInventory inventory, final int i, final int i1, final int i2) {
        super(inventory, i, i1, i2);
    }

    public boolean isItemValid(final ItemStack itemStack) {
        return Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock;
    }
}
