package com.recipt.recipe.domain.recipe

import com.recipt.core.extensions.thenUnit
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.repository.RecipeCategoryRepository
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RecipeDomainService (
    private val recipeRepository: RecipeRepository,
    private val recipeCategoryRepository: RecipeCategoryRepository
) {
    fun create(command: RecipeCreateCommand): Mono<Unit> {
        return Mono.fromCallable { recipeCategoryRepository.findAllById(command.categoryNos) }
            .map { recipeRepository.save(Recipe.create(command, it)) }
            .thenUnit()
    }
}