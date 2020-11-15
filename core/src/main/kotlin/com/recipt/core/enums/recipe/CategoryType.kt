package com.recipt.core.enums.recipe

import com.recipt.core.enums.ReciptEnum

enum class CategoryType(
    override val code: Int
): ReciptEnum {
    NOTHING(0),

    /** 메인 재료 카테고리 **/
    MAIN_INGREDIENT(1),

    /** 요리 종류 카테고리 **/
    KIND(2);
}