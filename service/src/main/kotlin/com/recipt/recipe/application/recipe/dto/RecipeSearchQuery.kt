package com.recipt.recipe.application.recipe.dto

import com.recipt.core.enums.recipe.KindCategoryType
import com.recipt.core.enums.recipe.MainCategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.model.ReciptPage

data class RecipeSearchQuery (
    val writer: String?,
    val mainCategoryType: MainCategoryType?,
    val kindCategoryType: KindCategoryType?,
    val page: ReciptPage,
    val ranges: Set<OpenRange>
) {
    constructor(
        writer: String?,
        mainCategoryType: MainCategoryType?,
        kindCategoryType: KindCategoryType?,
        page: Int?,
        pageSize: Int?,
        ranges: Set<OpenRange>
    ): this(
        writer,
        mainCategoryType,
        kindCategoryType,
        ReciptPage(page, pageSize, null),
        ranges
    )
}