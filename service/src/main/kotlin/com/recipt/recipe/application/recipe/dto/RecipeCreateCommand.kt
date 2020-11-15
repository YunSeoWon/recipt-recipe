package com.recipt.recipe.application.recipe.dto

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.domain.recipe.vo.CookingIngredient

data class RecipeCreateCommand(
    val title: String,
    val introduction: String?,
    val thumbnailImageUrl: String?,
    val creatorNo: Int,
    val creatorName: String,
    val mainIngredientCategoryNo: Int,
    val kindCategoryNo: Int,
    val difficulty: Int,
    val openRange: OpenRange,

    val subCookings: List<SubCookingCreateCommand>,
    val contents: List<RecipeContentCreateCommand>
) {

    val categoryNos = setOf(mainIngredientCategoryNo, kindCategoryNo)
}

data class SubCookingCreateCommand(
    val name: String,
    val ingredients: List<CookingIngredient>
)

data class RecipeContentCreateCommand(
    val order: Int,
    val content: String,
    val expectTime: Int = 0,    // 선택사항
    val necessary: Boolean,
    val imageUrl: String?
)