package mod.noriokun4649.shulkerboxmultiopentool.inventory;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

import java.util.Iterator;
import java.util.List;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.LOGGER;

public class ShulkerInventory extends Inventory {

    private List<IShulkerSlotChangeListener> listeners;

    private ItemStack itemStack1 = ItemStack.EMPTY;
    private ItemStack itemStack2 = ItemStack.EMPTY;
    private ItemStack itemStack3 = ItemStack.EMPTY;

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

        ItemStack[] nowItemStacks = {getStackInSlot(81), getStackInSlot(82), getStackInSlot(83)};

        boolean isRemovedOnSlot1 = nowItemStacks[0].getItem() == Items.AIR && Block.getBlockFromItem(itemStack1.getItem()) instanceof ShulkerBoxBlock;
        boolean isRemovedOnSlot2 = nowItemStacks[1].getItem() == Items.AIR && Block.getBlockFromItem(itemStack2.getItem()) instanceof ShulkerBoxBlock;
        boolean isRemovedOnSlot3 = nowItemStacks[2].getItem() == Items.AIR && Block.getBlockFromItem(itemStack3.getItem()) instanceof ShulkerBoxBlock;

        if (this.listeners != null) {
            Iterator var1 = this.listeners.iterator();
            int slotNum = getRemovedSlot(isRemovedOnSlot1, isRemovedOnSlot2, isRemovedOnSlot3);
            if (isAddSlot(nowItemStacks[0], itemStack1) || isAddSlot(nowItemStacks[1], itemStack2) || isAddSlot(nowItemStacks[2], itemStack3)) {

                itemStack1 = nowItemStacks[0].copy();
                itemStack2 = nowItemStacks[1].copy();
                itemStack3 = nowItemStacks[2].copy();
                while (var1.hasNext()) {
                    IShulkerSlotChangeListener changeListener = (IShulkerSlotChangeListener) var1.next();
                    changeListener.addShulkerBoxOnSlot(this, nowItemStacks[0],nowItemStacks[1],nowItemStacks[2]);
                    LOGGER.info("add");
                }
            } else if (slotNum > 0) {
                itemStack1 = nowItemStacks[0].copy();
                itemStack2 = nowItemStacks[1].copy();
                itemStack3 = nowItemStacks[2].copy();
                while (var1.hasNext()) {
                    IShulkerSlotChangeListener changeListener = (IShulkerSlotChangeListener) var1.next();
                    changeListener.removeShulkerBoxOnSlot(this, slotNum);
                }
            }
            while (var1.hasNext()) {
                IShulkerSlotChangeListener changeListener = (IShulkerSlotChangeListener) var1.next();
                changeListener.changeAllSlot(this);
            }
        }
    }

    private boolean isAddSlot(ItemStack nowItemStack, ItemStack beforeItemStack) {
        boolean isItemChange = beforeItemStack.getItem() == Items.AIR && Block.getBlockFromItem(nowItemStack.getItem()) instanceof ShulkerBoxBlock;
        if (!isItemChange) {
            isItemChange = Block.getBlockFromItem(beforeItemStack.getItem()) instanceof ShulkerBoxBlock && Block.getBlockFromItem(nowItemStack.getItem()) instanceof ShulkerBoxBlock;
        }
        boolean isEqualItem = !nowItemStack.isItemEqual(beforeItemStack);

        CompoundNBT nowTag = nowItemStack.getTag();
        CompoundNBT beforeTag = beforeItemStack.getTag();

        if (nowTag != null && beforeTag != null) {
        }


        return isEqualItem && isItemChange;
    }


    private int getRemovedSlot(boolean nowItemStack1, boolean nowItemStack2, boolean nowItemStack3) {
        if (nowItemStack1) {
            return 1;
        } else if (nowItemStack2) {
            return 2;
        } else if (nowItemStack3) {
            return 3;
        } else {
            return 0;
        }
    }
}
