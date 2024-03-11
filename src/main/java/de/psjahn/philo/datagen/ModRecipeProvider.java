package de.psjahn.philo.datagen;

import de.psjahn.philo.PhiloMod;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        philosopherStoneRecipe(consumer, false);
        philosopherStoneRecipe(consumer, true);
        addConversionRecipes(consumer);
    }

    private static void philosopherStoneRecipe(Consumer<FinishedRecipe> consumer, boolean alternate) {
        String name = getName(PhiloMod.PHILOSOPHERS_STONE.get());
        ShapedRecipeBuilder philoStone = ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, PhiloMod.PHILOSOPHERS_STONE.get())
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .define('A', Tags.Items.STORAGE_BLOCKS_AMETHYST)
                .define('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND_BLOCK), has(Tags.Items.STORAGE_BLOCKS_DIAMOND))
                .group(name);
        if (alternate) {
            philoStone.pattern("AIA")
                    .pattern("IDI")
                    .pattern("AIA")
                    .save(consumer, name + "_alt");
        } else {
            philoStone.pattern("IAI")
                    .pattern("ADA")
                    .pattern("IAI")
                    .save(consumer);
        }
    }

    private static void addConversionRecipes(Consumer<FinishedRecipe> consumer) {
        //6 Gold -> 1 Diamond
        conversionDouble(consumer, Items.GOLD_INGOT, 6, Items.DIAMOND, 1);
        //6 Iron -> 1 Gold
        conversionDouble(consumer, Items.IRON_INGOT, 6, Items.GOLD_INGOT, 1);
        //4 Copper -> 3 Iron
        conversionDouble(consumer, Items.COPPER_INGOT, 4, Items.IRON_INGOT, 3);

        //6 Gold Blocks -> 1 Diamond Block
        conversionDouble(consumer, Items.GOLD_BLOCK, 6, Items.DIAMOND_BLOCK, 1);
        //6 Iron Blocks -> 1 Gold Block
        conversionDouble(consumer, Items.IRON_BLOCK, 6, Items.GOLD_BLOCK, 1);
        //4 Copper Blocks -> 3 Iron Blocks
        conversionDouble(consumer, Items.COPPER_BLOCK, 4, Items.IRON_BLOCK, 3);

        // Redstone Dust/Glowstone Dust
        conversionDouble(consumer, Items.REDSTONE, 1, Items.GLOWSTONE_DUST, 1);

        // Redstone Block/Glowstone
        conversionDouble(consumer, Items.REDSTONE_BLOCK, 1, Items.GLOWSTONE, 1);

        // Granite/Andesite/Diorite
        conversionSingle(consumer, Items.ANDESITE, 1, Items.GRANITE, 1);
        conversionSingle(consumer, Items.GRANITE, 1, Items.DIORITE, 1);
        conversionSingle(consumer, Items.DIORITE, 1, Items.ANDESITE, 1);

        // Stone/Tuff/Deepslate
        conversionSingle(consumer, Items.STONE, 1, Items.TUFF, 1);
        conversionSingle(consumer, Items.TUFF, 1, Items.DEEPSLATE, 1);
        conversionSingle(consumer, Items.DEEPSLATE, 1, Items.STONE, 1);

        // Slime Block/Honey Block
        conversionDouble(consumer, Items.SLIME_BLOCK, 1, Items.HONEY_BLOCK, 1);
    }

    private static void conversionDouble(Consumer<FinishedRecipe> consumer, ItemLike a, int aAmount, ItemLike b, int bAmount) {
        conversionSingle(consumer, a, aAmount, b, bAmount);
        conversionSingle(consumer, b, bAmount, a, aAmount);
    }

    private static void conversionSingle(Consumer<FinishedRecipe> consumer, ItemLike input, int inputAmount, ItemLike output, int outputAmount) {
        ShapelessRecipeBuilder conversion = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, outputAmount)
            .requires(PhiloMod.PHILOSOPHERS_STONE.get())
            .unlockedBy(getHasName(PhiloMod.PHILOSOPHERS_STONE.get()), has(PhiloMod.PHILOSOPHERS_STONE.get()));
        for (int i = 0; i < inputAmount; i++) conversion.requires(input);
        conversion.save(consumer, new ResourceLocation(PhiloMod.MODID,"conversions/" + getName(input) + "_to_" + getName(output)));
    }

    public static String getName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }
}
