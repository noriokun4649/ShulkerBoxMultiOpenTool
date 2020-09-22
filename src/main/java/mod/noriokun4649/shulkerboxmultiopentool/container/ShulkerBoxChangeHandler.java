package mod.noriokun4649.shulkerboxmultiopentool.container;

import mod.noriokun4649.shulkerboxmultiopentool.inventory.IShulkerSlotChangeListener;
import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import java.util.List;

public class ShulkerBoxChangeHandler implements IShulkerSlotChangeListener {
    ShulkerInventory iInventory;

    @Override
    public void addShulkerBoxOnSlot(final IInventory iInventory, final ItemStack shulkerSlot1, final ItemStack shulkerSlot2, final ItemStack shulkerSlot3) {
        this.iInventory = (ShulkerInventory) iInventory;

        NonNullList<ItemStack> items1 = NonNullList.withSize(27, ItemStack.EMPTY);
        NonNullList<ItemStack> items2 = NonNullList.withSize(27, ItemStack.EMPTY);
        NonNullList<ItemStack> items3 = NonNullList.withSize(27, ItemStack.EMPTY);

        if (shulkerSlot1.getTag() != null) {
            loadCompoundNBT(shulkerSlot1, items1);
            setInventoryItemsStacks(items1);
        }
        if (shulkerSlot2.getTag() != null) {
            loadCompoundNBT(shulkerSlot2, items2);
            setInventoryItemsStacks(27, items2);
        }
        if (shulkerSlot3.getTag() != null) {
            loadCompoundNBT(shulkerSlot3, items3);
            setInventoryItemsStacks(54, items3);
        }
    }

    @Override
    public void removeShulkerBoxOnSlot(final IInventory iInventory, final int slotNum) {
        int slotSize = 27;
        int endSize = slotSize * slotNum;
        for (int i = endSize - slotSize; i < endSize; i++) {
            iInventory.removeStackFromSlot(i);
        }
    }

    @Override
    public void changeAllSlot(IInventory iInventory) {
        
    }

    private void setInventoryItemsStacks(final int offset, final NonNullList<ItemStack> inventoryItemsStacks) {
        int index = offset;
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
        CompoundNBT compoundNBT = itemStack.getTag().getCompound("BlockEntityTag");
        ItemStackHelper.saveAllItems(compoundNBT, itemStacks);
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
