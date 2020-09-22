package mod.noriokun4649.shulkerboxmultiopentool.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface IShulkerSlotChangeListener {

    void addShulkerBoxOnSlot(IInventory inventory, ItemStack slot1, ItemStack slot2, ItemStack slot3);

    void removeShulkerBoxOnSlot(IInventory iInventory, int slotNum);

    void changeAllSlot(IInventory iInventory);
}
