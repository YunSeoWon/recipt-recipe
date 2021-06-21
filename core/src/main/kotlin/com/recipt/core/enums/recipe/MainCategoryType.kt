package com.recipt.core.enums.recipe

import com.recipt.core.enums.ReciptEnum

enum class MainCategoryType(
    override val code: Int
): ReciptEnum {
    FORK(1),
    BEEF(2),
    CHICKEN(3),
    OTHER_MEAT(4),
    FISH(5),
    VEGETABLE(6),
    SEAFOOD(7),
    RICE(8),
    EGG(9),
    DAIRY_PRODUCT(10),
    FLOUR(11),
    OTHER(12)
}