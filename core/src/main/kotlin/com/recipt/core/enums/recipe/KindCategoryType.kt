package com.recipt.core.enums.recipe

import com.recipt.core.enums.ReciptEnum

enum class KindCategoryType(
    override val code: Int
): ReciptEnum {
    NOODLE(1),
    STEAK(2),
    RICE(3),
    KOREAN_SOUP(4),
    SOUP(5),
    FRIED(6),
    STEW(7), // 찌개
    SIDE_DISH(8),
    SALAD(9),
    BREAD(10),
    SNACK(11),
    OTHER(12)
}