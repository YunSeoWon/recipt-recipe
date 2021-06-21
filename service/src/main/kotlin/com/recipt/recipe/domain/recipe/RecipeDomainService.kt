package com.recipt.recipe.domain.recipe

import com.recipt.core.exception.recipe.RecipeNotFoundException
import com.recipt.core.exception.recipe.UnAuthorizedRecipeException
import com.recipt.core.extensions.thenUnit
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeModifyCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class RecipeDomainService (
    private val recipeRepository: RecipeRepository
) {

    fun create(command: RecipeCreateCommand): Mono<Unit> {
        return Mono.fromCallable { recipeRepository.save(Recipe.create(command)) }
            .thenUnit()
    }

    fun modify(command: RecipeModifyCommand): Mono<Unit> {
        return Mono.fromCallable {
            recipeRepository.findByIdOrNull(command.recipeNo)?: throw RecipeNotFoundException()
        }.filter { it.creator.no == command.editorNo }
            .switchIfEmpty(Mono.error(UnAuthorizedRecipeException()))
            .map { it.modify(command) }
            .thenUnit()
    }
}