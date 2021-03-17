package com.recipt.recipe.application.recipe.dto

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.domain.recipe.vo.CookingIngredient

data class RecipeModifyCommand(
    val recipeNo: Int,
    val title: String,
    val introduction: String?,
    val thumbnailImageUrl: String?,
    val mainIngredientCategoryNo: Int,
    val kindCategoryNo: Int,
    val difficulty: Int,
    val openRange: OpenRange,

    val editorNo: Int
) {
    val categoryNos = setOf(mainIngredientCategoryNo, kindCategoryNo)
}
