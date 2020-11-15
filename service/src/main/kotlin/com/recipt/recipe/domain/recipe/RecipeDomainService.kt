package com.recipt.recipe.domain.recipe

import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.repository.RecipeCategoryRepository
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import org.springframework.stereotype.Component

@Component
class RecipeDomainService (
    private val recipeRepository: RecipeRepository,
    private val recipeCategoryRepository: RecipeCategoryRepository
) {
    fun create(command: RecipeCreateCommand) {
        val categories = recipeCategoryRepository.findAllById(command.categoryNos)

        recipeRepository.save(Recipe.create(command, categories))
    }
}