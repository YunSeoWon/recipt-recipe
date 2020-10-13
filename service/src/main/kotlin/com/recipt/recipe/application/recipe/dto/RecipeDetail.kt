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
    constructor(recipe: Recipe): this(
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
        subCookings = recipe.subCookings.map { Cooking(it) },
        contents = recipe.contents.map { Content(it) }
    )
}

data class Cooking(
    val name: String,
    val ingredients: List<CookingIngredient>
) {
    constructor(cooking: SubCooking): this(
        name = cooking.name,
        ingredients = cooking.cookingIngredients
    )
}

data class Content (
    val order: Int,
    val content: String,
    val expectTime: Int,
    val necessary: Boolean,
    val imageUrl: String?
) {
    constructor(content: RecipeContent): this(
        order = content.order,
        content = content.content,
        expectTime = content.expectTime,
        necessary = content.necessary,
        imageUrl = content.imageUrl
    )
}