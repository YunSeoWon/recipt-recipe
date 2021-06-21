package com.recipt.recipe.presentation.request

import com.recipt.core.enums.recipe.KindCategoryType
import com.recipt.core.enums.recipe.MainCategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.model.MemberInfo
import com.recipt.recipe.application.recipe.dto.RecipeModifyCommand

data class RecipeModifyRequest(
    val title: String,
    val introduction: String?,
    val thumbnailImageUrl: String?,
    val mainIngredientCategoryNo: Int,
    val kindCategoryNo: Int,
    val difficulty: Int,
    val openRange: OpenRange,
    val mainCategoryType: MainCategoryType,
    val kindCategoryType: KindCategoryType
) {
    fun toCommand(recipeNo: Int, memberInfo: MemberInfo) = RecipeModifyCommand(
        recipeNo = recipeNo,
        title = title,
        introduction = introduction,
        thumbnailImageUrl = thumbnailImageUrl,
        mainIngredientCategoryNo = mainIngredientCategoryNo,
        kindCategoryNo = kindCategoryNo,
        difficulty = difficulty,
        openRange = openRange,
        editorNo = memberInfo.no,
        mainCategoryType = mainCategoryType,
        kindCategoryType = kindCategoryType
    )
}