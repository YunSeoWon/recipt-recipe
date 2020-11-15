package com.recipt.recipe.presentation.request

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.model.MemberInfo
import com.recipt.recipe.application.recipe.dto.RecipeContentCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.SubCookingCreateCommand

data class RecipeCreateRequest(
    val title: String,
    val introduction: String?,
    val thumbnailImageUrl: String?,
    val mainIngredientCategoryNo: Int,
    val kindCategoryNo: Int,
    val difficulty: Int,
    val openRange: OpenRange,

    val subCookings: List<SubCookingCreateCommand>,
    val contents: List<RecipeContentCreateCommand>
) {
    fun toCommand(memberInfo: MemberInfo) = RecipeCreateCommand(
        title = title,
        introduction = introduction,
        thumbnailImageUrl = thumbnailImageUrl,
        mainIngredientCategoryNo = mainIngredientCategoryNo,
        kindCategoryNo = kindCategoryNo,
        difficulty = difficulty,
        openRange = openRange,
        creatorNo = memberInfo.no,
        creatorName = memberInfo.nickname,
        subCookings = subCookings,
        contents = contents
    )
}