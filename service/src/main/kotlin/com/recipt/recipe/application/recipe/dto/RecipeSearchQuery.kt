package com.recipt.recipe.application.recipe.dto

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.model.ReciptPage

data class RecipeSearchQuery (
    val writer: String?,
    val mainCategoryNo: Int?,
    val kindCategoryNo: Int?,
    val page: ReciptPage,
    val ranges: Set<OpenRange>
) {
    constructor(
        writer: String?,
        mainCategoryNo: Int?,
        kindCategoryNo: Int?,
        page: Int?,
        pageSize: Int?,
        ranges: Set<OpenRange>
    ): this(
        writer,
        mainCategoryNo,
        kindCategoryNo,
        ReciptPage(page, pageSize, null),
        ranges
    )
}