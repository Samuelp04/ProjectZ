package com.swiftydevs.projectz.Common.Items;

import net.minecraft.world.food.FoodProperties;

/**
 * Defines various FoodProperties for different food items in the Project Z mod.
 */
public class FoodBase {

    // Food properties for various items
    public static final FoodProperties CAN_CORN = createFoodProperties(8, 0.3F);
    public static final FoodProperties CAKE_PIECE = createFoodProperties(5, 0.3F);
    public static final FoodProperties CAN_CHICKEN = createFoodProperties(4, 0.3F);
    public static final FoodProperties CAN_FISH = createFoodProperties(6, 0.3F);
    public static final FoodProperties CAN_TUNA = createFoodProperties(7, 0.3F);
    public static final FoodProperties JERKY = createFoodProperties(9, 0.3F);
    public static final FoodProperties NRG_BAR = createFoodProperties(4, 0.3F);
    public static final FoodProperties CAN_CORN_OPENED = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_MUSHROOMS = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_MUSHROOMS_OPENED = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_OLIVES = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_OLIVES_OPEN = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_PEAS = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_PINEAPPLE = createFoodProperties(2, 0.1F);
    public static final FoodProperties CAN_PINEAPPLE_OPENED = createFoodProperties(2, 0.1F);
    public static final FoodProperties BANANA = createFoodProperties(2, 0.1F);
    public static final FoodProperties BEANS = createFoodProperties(2, 0.1F);
    public static final FoodProperties BEANS_CAN = createFoodProperties(2, 0.1F);
    public static final FoodProperties BERRYS = createFoodProperties(2, 0.1F);
    public static final FoodProperties CEREALS = createFoodProperties(2, 0.1F);

    /**
     * Creates a FoodProperties object with the given hunger and saturation values.
     *
     * @param hunger the amount of hunger restored by the food
     * @param saturation the saturation added by the food
     * @return a new FoodProperties instance
     */
    private static FoodProperties createFoodProperties(int hunger, float saturation) {
        return new FoodProperties.Builder()
                .nutrition(hunger)
                .saturationMod(saturation)
                .build();
    }
}
