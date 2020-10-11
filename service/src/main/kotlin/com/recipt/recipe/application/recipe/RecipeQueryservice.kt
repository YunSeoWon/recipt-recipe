package com.recipt.recipe.application.recipe

import com.recipt.core.model.PageInfo
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.application.recipe.dto.RecipeSummary
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class RecipeQueryService(
    private val recipeRepository: RecipeRepository
) {
    suspend fun search(query: RecipeSearchQuery): PageInfo<RecipeSummary> {
        return recipeRepository.search(query).let { recipePage ->
            PageInfo(
                totalCount = recipePage.totalCount,
                contents = recipePage.contents.map { RecipeSummary(it) }
            )
        }
    }
}