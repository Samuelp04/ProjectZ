package com.swiftydevs.projectz.Common.Items;

import net.minecraft.world.item.Item;

/**
 * A base class for items in the Project Z mod.
 * Extends the default Item class to provide custom properties or behaviors for items.
 */
public class BaseItem extends Item {

    /**
     * Constructor to create a new BaseItem with specified properties.
     *
     * @param properties the properties to set for this item
     */
    public BaseItem(Item.Properties properties) {
        super(properties);
    }
}
