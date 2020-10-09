package mod.noriokun4649.shulkerboxmultiopentool.inventory;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Iterator;
import java.util.List;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.LOGGER;

public class ShulkerInventory extends Inventory {

    private List<IShulkerSlotChangeListener> listeners;

    private ItemStack[] nowItemStacksCopy = {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    private boolean isBeforeCahge = false;

    public ShulkerInventory(final int size, final int pattern, final int addslot) {
        super((size * pattern) + addslot);
    }

    public void addShulkerBoxListener(final IShulkerSlotChangeListener changeListener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }

        this.listeners.add(changeListener);
    }

    public void removeShulkerBoxListener(final IShulkerSlotChangeListener changeListener) {
        this.listeners.remove(changeListener);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (Minecraft.getInstance().world.isRemote) {
            ItemStack[] nowItemStacks = {getStackInSlot(81), getStackInSlot(82), getStackInSlot(83)};
            final boolean isRemovedOnSlot1 = nowItemStacks[0].getItem() == Items.AIR && Block.getBlockFromItem(nowItemStacksCopy[0].getItem()) instanceof ShulkerBoxBlock;
            final boolean isRemovedOnSlot2 = nowItemStacks[1].getItem() == Items.AIR && Block.getBlockFromItem(nowItemStacksCopy[1].getItem()) instanceof ShulkerBoxBlock;
            final boolean isRemovedOnSlot3 = nowItemStacks[2].getItem() == Items.AIR && Block.getBlockFromItem(nowItemStacksCopy[2].getItem()) instanceof ShulkerBoxBlock;
            final boolean isAddOnSlot1 = isAddSlot(nowItemStacks[0], nowItemStacksCopy[0]);
            final boolean isAddOnSlot2 = isAddSlot(nowItemStacks[1], nowItemStacksCopy[1]);
            final boolean isAddOnSlot3 = isAddSlot(nowItemStacks[2], nowItemStacksCopy[2]);

            if (this.listeners != null) {
                Iterator var1 = this.listeners.iterator();
                final int removeSlotNum = getRemovedSlot(isRemovedOnSlot1, isRemovedOnSlot2, isRemovedOnSlot3);
                final int addSlotNum = getAddSlot(isAddOnSlot1, isAddOnSlot2, isAddOnSlot3);
                if (addSlotNum >= 0) {
                    copyToCurrent(nowItemStacks);
                    while (var1.hasNext()) {
                        IShulkerSlotChangeListener changeListener = (IShulkerSlotChangeListener) var1.next();
                        changeListener.addShulkerBoxOnSlot(this, addSlotNum);
                    }
                } else if (removeSlotNum >= 0) {
                    copyToCurrent(nowItemStacks);
                    while (var1.hasNext()) {
                        IShulkerSlotChangeListener changeListener = (IShulkerSlotChangeListener) var1.next();
                        changeListener.removeShulkerBoxOnSlot(this, removeSlotNum);
                    }
                }
            }
        } else {
            LOGGER.info("client");
        }
    }

    private void copyToCurrent(ItemStack[] itemStacks) {
        for (int i = 0; i < itemStacks.length; i++) {
            nowItemStacksCopy[i] = itemStacks[i].copy();
        }
    }

    private boolean isAddSlot(ItemStack nowItemStack, ItemStack beforeItemStack) {
        if (nowItemStack.getItem() == Items.AIR) return false;

        return !Container.areItemsAndTagsEqual(nowItemStack, beforeItemStack);
    }


    private int getRemovedSlot(boolean nowItemStack1, boolean nowItemStack2, boolean nowItemStack3) {
        if (nowItemStack1) {
            return 0;
        } else if (nowItemStack2) {
            return 1;
        } else if (nowItemStack3) {
            return 2;
        } else {
            return -1;
        }
    }

    private int getAddSlot(boolean nowItemStack1, boolean nowItemStack2, boolean nowItemStack3) {
        return getRemovedSlot(nowItemStack1, nowItemStack2, nowItemStack3);
    }
}
