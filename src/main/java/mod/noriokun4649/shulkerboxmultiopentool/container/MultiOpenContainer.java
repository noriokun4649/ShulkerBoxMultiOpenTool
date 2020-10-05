package mod.noriokun4649.shulkerboxmultiopentool.container;

import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerInventory;
import mod.noriokun4649.shulkerboxmultiopentool.inventory.ShulkerSlot;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;

import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.LOGGER;
import static mod.noriokun4649.shulkerboxmultiopentool.ShulkerBoxMultiOpenToolMod.MULTI_OPEN_TOOL_CONT;


public class MultiOpenContainer extends Container {

    private final ShulkerInventory inventory;
    private final int shulkerBoxItemSlotSize = 27;
    private final int shulkerBoxCount = 3;
    private final int shulkerBoxSlotSize = 3;
    private final NonNullList<ItemStack> emptyList = NonNullList.withSize(27, ItemStack.EMPTY);

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
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        dropOnSlot(playerIn, 81);
        dropOnSlot(playerIn, 82);
        dropOnSlot(playerIn, 83);
        if (playerIn.world.isRemote) {
            LOGGER.info("Server");
        } else {
            LOGGER.info("Client");
        }
        playerIn.dropItem(new ItemStack(Items.PUMPKIN), false);
        LOGGER.info("close");
    }

    private void dropOnSlot(final PlayerEntity playerEntity, final int index) {
        if (!playerEntity.isAlive() || playerEntity instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerEntity).hasDisconnected()) {
            LOGGER.info("In Server");
            Slot slot = inventorySlots.get(index + 36);
            if (slot != null && slot.getHasStack()) {
                LOGGER.info("drop");
                playerEntity.dropItem(slot.getStack(), false);
            }
        } else {
            LOGGER.info("Not Server");
            playerEntity.inventory.placeItemBackInInventory(playerEntity.world, inventory.removeStackFromSlot(index));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        LOGGER.info("Quick index"+index);
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
                removeItemsAndSaveTags(clickItemStack, index);
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

    @Override
    public boolean canInteractWith(final PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack nowItemStack = super.slotClick(slotId, dragType, clickTypeIn, player);
        if (Block.getBlockFromItem(nowItemStack.getItem()) instanceof ShulkerBoxBlock && slotId >= 117) {
            removeItemsAndSaveTags(nowItemStack, slotId);
        }
        return nowItemStack;
    }

    private void removeItemsAndSaveTags(final ItemStack clickItemStack, final int index){
        LOGGER.info("remove "+ index);
        LOGGER.info("remove "+ clickItemStack.getDisplayName().getString());
        if (Block.getBlockFromItem(clickItemStack.getItem()) instanceof ShulkerBoxBlock) {
            NonNullList<ItemStack> nowItemListSlot1 = getAllItemStack(index - 117);
            LOGGER.info(nowItemListSlot1);
            if (!nowItemListSlot1.equals(emptyList)) {
                LOGGER.info("save");
                saveCompoundNBT(clickItemStack, nowItemListSlot1);
            }
            final int endSize = 27 * (index - 116);
            for (int i = endSize - 27; i < endSize; i++) {
                inventory.removeStackFromSlot(i);
            }
        }
    }

    private NonNullList<ItemStack> getAllItemStack(final int startSlot) {
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(shulkerBoxItemSlotSize, ItemStack.EMPTY);
        for (int i = 0; i < shulkerBoxItemSlotSize; i++) {
            itemStacks.set(i, inventory.getStackInSlot(i + (shulkerBoxItemSlotSize * startSlot)));
        }
        return itemStacks;
    }

    private void saveCompoundNBT(final ItemStack itemStack, final NonNullList<ItemStack> itemStacks) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null) {
            compoundNBT = compoundNBT.getCompound("BlockEntityTag");
             ItemStackHelper.saveAllItems(compoundNBT, itemStacks);
             itemStack.setTagInfo("BlockEntityTag",compoundNBT);
        }
    }
}
