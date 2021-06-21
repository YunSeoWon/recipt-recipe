package com.recipt.recipe.application.recipe.dto

import com.recipt.core.enums.recipe.KindCategoryType
import com.recipt.core.enums.recipe.MainCategoryType
import com.recipt.core.enums.recipe.OpenRange

data class RecipeModifyCommand(
    val recipeNo: Int,
    val title: String,
    val introduction: String?,
    val thumbnailImageUrl: String?,
    val mainIngredientCategoryNo: Int,
    val kindCategoryNo: Int,
    val difficulty: Int,
    val openRange: OpenRange,
    val mainCategoryType: MainCategoryType,
    val kindCategoryType: KindCategoryType,

    val editorNo: Int
) {
    val categoryNos = setOf(mainIngredientCategoryNo, kindCategoryNo)
}
