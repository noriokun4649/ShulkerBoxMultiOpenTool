package mod.noriokun4649.shulkerboxmultiopentool.container;

import mod.noriokun4649.shulkerboxmultiopentool.inventory.IShulkerSlotChangeListener;
import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import java.util.List;

public class ShulkerBoxChangeHandler implements IShulkerSlotChangeListener {
    ShulkerInventory iInventory;
    final int maxSize = 27;

    @Override
    public void addShulkerBoxOnSlot(final IInventory iInventory, final ItemStack shulkerSlot1, final ItemStack shulkerSlot2, final ItemStack shulkerSlot3) {
        this.iInventory = (ShulkerInventory) iInventory;

        NonNullList<ItemStack> items1 = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        NonNullList<ItemStack> items2 = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        NonNullList<ItemStack> items3 = NonNullList.withSize(maxSize, ItemStack.EMPTY);

        if (shulkerSlot1.getTag() != null) {
            loadCompoundNBT(shulkerSlot1, items1);
            setInventoryItemsStacks(items1);
        }
        if (shulkerSlot2.getTag() != null) {
            loadCompoundNBT(shulkerSlot2, items2);
            setInventoryItemsStacks(1, items2);
        }
        if (shulkerSlot3.getTag() != null) {
            loadCompoundNBT(shulkerSlot3, items3);
            setInventoryItemsStacks(2, items3);
        }
    }

    @Override
    public void removeShulkerBoxOnSlot(final IInventory iInventory, final int slotNum) {
        final int endSize = maxSize * slotNum;
        for (int i = endSize - maxSize; i < endSize; i++) {
            iInventory.removeStackFromSlot(i);
        }
    }

    @Override
    public void changeAllSlot(IInventory iInventory) {
        NonNullList<ItemStack> emptyList = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        ItemStack slot1 = iInventory.getStackInSlot(81);
        ItemStack slot2 = iInventory.getStackInSlot(82);
        ItemStack slot3 = iInventory.getStackInSlot(83);

        if (Block.getBlockFromItem(slot1.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot1 = getAllItemStack(0);
            if (!nowItemListSlot1.equals(emptyList)) {
                saveCompoundNBT(slot1, nowItemListSlot1);
            }
        }
        if (Block.getBlockFromItem(slot2.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot2 = getAllItemStack(1);
            if (!nowItemListSlot2.equals(emptyList)) {
                saveCompoundNBT(slot2, nowItemListSlot2);
            }
        }
        if (Block.getBlockFromItem(slot3.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot3 = getAllItemStack(2);
            if (!nowItemListSlot3.equals(emptyList)) {
                saveCompoundNBT(slot3, nowItemListSlot3);
            }
        }
    }

    private NonNullList<ItemStack> getAllItemStack(final int startSlot) {
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        for (int i = 0; i < maxSize; i++) {
            itemStacks.set(i, iInventory.getStackInSlot(i + (maxSize * startSlot)));
        }
        return itemStacks;
    }

    private void setInventoryItemsStacks(final int offset, final NonNullList<ItemStack> inventoryItemsStacks) {
        int index = maxSize * offset;
        for (ItemStack itemStack : inventoryItemsStacks) {
            iInventory.setInventorySlotContents(index++, itemStack);
        }
    }

    private void setInventoryItemsStacks(final NonNullList<ItemStack> inventoryItemsStacks) {
        setInventoryItemsStacks(0, inventoryItemsStacks);
    }

    private void loadCompoundNBT(final ItemStack itemStack, final NonNullList<ItemStack> itemStacks) {
        CompoundNBT compoundNBT = itemStack.getTag().getCompound("BlockEntityTag");
        ItemStackHelper.loadAllItems(compoundNBT, itemStacks);
    }

    private void saveCompoundNBT(final ItemStack itemStack, final NonNullList<ItemStack> itemStacks) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null){
            compoundNBT = compoundNBT.getCompound("BlockEntityTag");
            itemStack.setTag(ItemStackHelper.saveAllItems(compoundNBT, itemStacks));
        }
    }

    private void loadCompoundNBTs(final List<ItemStack> itemStackList, final List<NonNullList<ItemStack>> nonNullLists) {
        if (itemStackList.size() == nonNullLists.size()) {
            int size = itemStackList.size();
            for (int index = 0; index < size; index++) {
                ItemStack itemStack = itemStackList.get(index);
                if (itemStack.getTag() != null) {
                    loadCompoundNBT(itemStack, nonNullLists.get(index));
                }
            }
        }
    }

}
