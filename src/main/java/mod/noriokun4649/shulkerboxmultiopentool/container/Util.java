package mod.noriokun4649.shulkerboxmultiopentool.container;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import java.util.List;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.LOGGER;

public class Util {

    private static final int maxSize = 27;

    public static void itemStackSaveTags(final ItemStack clickItemStack, final int index, final IInventory iInventory) {
        LOGGER.info("itemStackSaveTags1");
        final NonNullList<ItemStack> emptyList = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        if (Block.getBlockFromItem(clickItemStack.getItem()) instanceof ShulkerBoxBlock) {
            LOGGER.info("itemStackSaveTags2");
            NonNullList<ItemStack> nowItemListSlot1 = getAllItemStack(index, iInventory);
            if (!nowItemListSlot1.equals(emptyList)) {
                LOGGER.info("itemStackSaveTags3");
                saveCompoundNBT(clickItemStack, nowItemListSlot1);
            }
        }
    }

    public static void removeItems(final int index, final IInventory inventory) {
        LOGGER.info("remove");
        final int endSize = maxSize * (index + 1);
        for (int i = endSize - maxSize; i < endSize; i++) {
            inventory.removeStackFromSlot(i);
        }
    }

    public static void saveCompoundNBT(final ItemStack itemStack, final NonNullList<ItemStack> itemStacks) {
        LOGGER.info("save");
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null) {
            compoundNBT = compoundNBT.getCompound("BlockEntityTag");
            ItemStackHelper.saveAllItems(compoundNBT, itemStacks);
            itemStack.setTagInfo("BlockEntityTag", compoundNBT);
        }
    }

    public static NonNullList<ItemStack> getAllItemStack(final int startSlot, final IInventory iInventory) {
        LOGGER.info("getAllItemStack");
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(maxSize, ItemStack.EMPTY);
        for (int i = 0; i < maxSize; i++) {
            itemStacks.set(i, iInventory.getStackInSlot(i + (maxSize * startSlot)));
        }
        return itemStacks;
    }

    public static void setInventoryItemsStacks(final int offset, final NonNullList<ItemStack> inventoryItemsStacks, final IInventory inventory) {
        LOGGER.info("setInventoryItemsStacks");
        int index = maxSize * offset;
        for (ItemStack itemStack : inventoryItemsStacks) {
            inventory.setInventorySlotContents(index++, itemStack);
        }
    }

    public static void setInventoryItemsStacks(final NonNullList<ItemStack> inventoryItemsStacks, IInventory inventory) {
        setInventoryItemsStacks(0, inventoryItemsStacks, inventory);
    }

    public static void loadCompoundNBT(final ItemStack itemStack, final NonNullList<ItemStack> itemStacks) {
        LOGGER.info("loadCompoundNBT");
        CompoundNBT compoundNBT = itemStack.getTag().getCompound("BlockEntityTag");
        ItemStackHelper.loadAllItems(compoundNBT, itemStacks);
    }

    public static void loadCompoundNBTs(final List<ItemStack> itemStackList, final List<NonNullList<ItemStack>> nonNullLists) {
        LOGGER.info("loadCompoundNBTs");
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
