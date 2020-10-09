package mod.noriokun4649.shulkerboxmultiopentool.inventory;

import net.minecraft.inventory.IInventory;

public interface IShulkerSlotChangeListener {

    void addShulkerBoxOnSlot(IInventory inventory, int slotNum);

    void removeShulkerBoxOnSlot(IInventory iInventory, int slotNum);

    void changeAllSlot(IInventory iInventory);
}
