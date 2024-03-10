package de.psjahn.philo;

import de.psjahn.philo.item.SelfRemainingItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(PhiloMod.MODID)
public class PhiloMod
{
    public static final String MODID = "philo";

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Item> PHILOSOPHERS_STONE = ITEMS.register("philosophers_stone", ()->new SelfRemainingItem(new Item.Properties().stacksTo(1).fireResistant()));

    private static final RegistryObject<CreativeModeTab> PHILO_TAB = CREATIVE_TABS.register("philo_tab",
            ()-> CreativeModeTab.builder()
                    .title(Component.translatable("item.philo.philosophers_stone"))
                    .icon(()->new ItemStack(PHILOSOPHERS_STONE.get()))
                    .displayItems(((itemDisplayParameters, output) -> output.accept(PHILOSOPHERS_STONE.get())))
                    .build());

    public PhiloMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CREATIVE_TABS.register(modEventBus);
        ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
