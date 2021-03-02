package com.recipt.recipe.application.recipe.dto

import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.entity.RecipeContent
import com.recipt.recipe.domain.recipe.entity.SubCooking
import com.recipt.recipe.domain.recipe.vo.Categories
import com.recipt.recipe.domain.recipe.vo.CookingIngredient
import com.recipt.recipe.domain.recipe.vo.Creator
import java.time.LocalDateTime

data class RecipeDetail(
    val no: Int,
    val title: String,
    val introduction: String?,
    val thumbnailImageUrl: String?,
    val creator: Creator,
    val createDateTime: LocalDateTime,
    val editDateTime: LocalDateTime?,
    val categories: Categories,
    val difficulty: Int,
    val readCount: Int,
    val likeCount: Int,
    val postingCount: Int,
    val subCookings: List<Cooking>,
    val contents: List<Content>
) {

    companion object {
        fun of(recipe: Recipe) = RecipeDetail(
            no = recipe.no,
            title = recipe.title,
            introduction = recipe.introduction,
            thumbnailImageUrl = recipe.thumbnailImageUrl,
            creator = recipe.creator,
            createDateTime = recipe.createDateTime,
            editDateTime = recipe.editDateTime,
            categories = recipe.categories,
            difficulty = recipe.difficulty,
            readCount = recipe.readCount,
            likeCount = recipe.likeCount,
            postingCount = recipe.postingCount,
            subCookings = recipe.subCookings.map { Cooking.of(it) },
            contents = recipe.contents.map { Content.of(it) }
        )
    }
}

data class Cooking(
    val name: String,
    val ingredients: List<CookingIngredient>
) {
    companion object {
        fun of(cooking: SubCooking) = Cooking(
            name = cooking.name,
            ingredients = cooking.cookingIngredients
        )
    }
}

data class Content (
    val order: Int,
    val content: String,
    val expectTime: Int,
    val necessary: Boolean,
    val imageUrl: String?
) {
    companion object {
        fun of(content: RecipeContent) = Content(
            order = content.order,
            content = content.content,
            expectTime = content.expectTime,
            necessary = content.necessary,
            imageUrl = content.imageUrl
        )
    }
}