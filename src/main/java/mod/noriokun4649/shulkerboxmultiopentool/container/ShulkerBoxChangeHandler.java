package mod.noriokun4649.shulkerboxmultiopentool.container;

import mod.noriokun4649.shulkerboxmultiopentool.inventory.IShulkerSlotChangeListener;
import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.LOGGER;
import static mod.noriokun4649.shulkerboxmultiopentool.container.Util.*;

public class ShulkerBoxChangeHandler implements IShulkerSlotChangeListener {
    ShulkerInventory iInventory;
    final int maxSize = 27;

    @Override
    public void addShulkerBoxOnSlot(final IInventory iInventory, final int slotNum) {
        LOGGER.info("add");
        //removeItems(slotNum, this.iInventory);
        this.iInventory = (ShulkerInventory) iInventory;
        final ItemStack itemStack = this.iInventory.getStackInSlot(slotNum + 81);
        NonNullList<ItemStack> items = NonNullList.withSize(maxSize, ItemStack.EMPTY);

        if (itemStack.getTag() != null) {
            loadCompoundNBT(itemStack, items);
            setInventoryItemsStacks(slotNum, items, this.iInventory);
        }
    }

    @Override
    public void removeShulkerBoxOnSlot(final IInventory iInventory, final int index) {
        this.iInventory = (ShulkerInventory) iInventory;
        LOGGER.info("remove on Handler");
        removeItems(index, this.iInventory);
    }

    @Override
    public void changeAllSlot(IInventory iInventory) {
        NonNullList<ItemStack> emptyList = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        ItemStack slot1 = iInventory.getStackInSlot(81);
        ItemStack slot2 = iInventory.getStackInSlot(82);
        ItemStack slot3 = iInventory.getStackInSlot(83);

        if (Block.getBlockFromItem(slot1.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot1 = getAllItemStack(0, iInventory);
            if (!nowItemListSlot1.equals(emptyList)) {
                saveCompoundNBT(slot1, nowItemListSlot1);
            }
        }
        if (Block.getBlockFromItem(slot2.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot2 = getAllItemStack(1, iInventory);
            if (!nowItemListSlot2.equals(emptyList)) {
                saveCompoundNBT(slot2, nowItemListSlot2);
            }
        }
        if (Block.getBlockFromItem(slot3.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot3 = getAllItemStack(2, iInventory);
            if (!nowItemListSlot3.equals(emptyList)) {
                saveCompoundNBT(slot3, nowItemListSlot3);
            }
        }
    }


}
