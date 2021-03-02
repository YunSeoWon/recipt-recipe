package com.recipt.recipe.application.recipe.dto

import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.vo.Creator
import java.time.LocalDateTime

data class RecipeSummary(
    val no: Int,
    val title: String,
    val thumbnailImageUrl: String?,
    val creator: Creator,
    val createDateTime: LocalDateTime,
    val editDateTime: LocalDateTime?,
    val difficulty: Int,
    val readCount: Int,
    val likeCount: Int,
    val postingCount: Int
) {
    companion object {
        fun of(recipe: Recipe) = RecipeSummary(
            no = recipe.no,
            title = recipe.title,
            thumbnailImageUrl = recipe.thumbnailImageUrl,
            creator = recipe.creator,
            createDateTime = recipe.createDateTime,
            editDateTime = recipe.editDateTime,
            difficulty = recipe.difficulty,
            readCount = recipe.readCount,
            likeCount = recipe.likeCount,
            postingCount = recipe.postingCount
        )
    }
}