package mod.noriokun4649.shulkerboxmultiopentool.container;

import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerInventory;
import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerSlot;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.LOGGER;
import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.MULTI_OPEN_TOOL_CONT;


public class MultiOpenContainer extends Container {

    private final ShulkerInventory inventory;
    private final int shulkerBoxItemSlotSize = 27;
    private final int shulkerBoxCount = 3;
    private final int shulkerBoxSlotSize = 3;

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        dropOnSlot(playerIn, 117);
        dropOnSlot(playerIn, 118);
        dropOnSlot(playerIn, 119);
        playerIn.dropItem(new ItemStack(Items.PUMPKIN), true, false);
        LOGGER.info("close");
    }

    private void dropOnSlot(final PlayerEntity playerEntity, final int index) {
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            LOGGER.info("drop");
            playerEntity.dropItem(slot.getStack(), true, false);
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack clickItemStack = slot.getStack();
            if (index <= 35) {
                if (Block.getBlockFromItem(clickItemStack.getItem()) instanceof ShulkerBoxBlock) {
                    if (!mergeItemStack(clickItemStack, 117, 120, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!mergeItemStack(clickItemStack, 36, 117, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!mergeItemStack(clickItemStack, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (clickItemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            slot.onTake(playerIn, clickItemStack);
        }
        return ItemStack.EMPTY;
    }

    public MultiOpenContainer(final int windowNum, final PlayerInventory playerInventory, final PacketBuffer packetBuffer) {
        super(MULTI_OPEN_TOOL_CONT, windowNum);
        int all_slots = shulkerBoxItemSlotSize * shulkerBoxCount + shulkerBoxSlotSize;
        inventory = new ShulkerInventory(shulkerBoxItemSlotSize, shulkerBoxCount, shulkerBoxSlotSize);
        inventory.addShulkerBoxListener(new ShulkerBoxChangeHandler());
        assertInventorySize(inventory, all_slots);

        createSlotBox(playerInventory, 9, 3, 9, 84, 95);
        for (int ix = 0; ix < 9; ++ix) {
            this.addSlot(new Slot(playerInventory, ix, 95 + (ix * 18), 142));
        }

        int size;
        size = createShulkerBoxSlotBox(inventory, 3, 9, 26, -72);
        size = createShulkerBoxSlotBox(inventory, size, 3, 9, 106, -72);
        size = createShulkerBoxSlotBox(inventory, size, 3, 9, 26, 95);

        this.addSlot(new ShulkerSlot(inventory, size, -72, 6));
        this.addSlot(new ShulkerSlot(inventory, ++size, -72, 86));
        this.addSlot(new ShulkerSlot(inventory, ++size, 95, 6));
    }

    private void createSlotBox(final IInventory inventory, final int offset, final int row, final int column, final int h, final int w) {
        for (int iy = 0; iy < row; ++iy) {
            for (int ix = 0; ix < column; ++ix) {
                this.addSlot(new Slot(inventory, iy * 9 + ix + offset, w + (ix * 18), h + (iy * 18)));
            }
        }
    }

    private int createShulkerBoxSlotBox(final IInventory inventory, final int row, final int column, final int h, final int w) {
        return createShulkerBoxSlotBox(inventory, 0, row, column, h, w);
    }

    private int createShulkerBoxSlotBox(final IInventory inventory, final int offset, final int row, final int column, final int h, final int w) {
        for (int iy = 0; iy < row; ++iy) {
            for (int ix = 0; ix < column; ++ix) {
                this.addSlot(new ShulkerBoxSlot(inventory, iy * 9 + ix + offset, w + (ix * 18), h + (iy * 18)));
            }
        }
        return row * column + offset;
    }

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return true;
    }


}
