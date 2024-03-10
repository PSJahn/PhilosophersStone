package de.psjahn.philo.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SelfRemainingItem extends Item {
    public SelfRemainingItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(this, 1);
    }
}
