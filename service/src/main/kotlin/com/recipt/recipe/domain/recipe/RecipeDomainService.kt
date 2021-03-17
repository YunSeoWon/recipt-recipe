package com.recipt.recipe.domain.recipe

import com.recipt.core.exception.recipe.RecipeNotFoundException
import com.recipt.core.exception.recipe.UnAuthorizedRecipeException
import com.recipt.core.extensions.thenUnit
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeModifyCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.repository.RecipeCategoryRepository
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import org.springframework.data.repository.findByIdOrNull
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

    fun modify(command: RecipeModifyCommand): Mono<Unit> {
        val recipeMono = Mono.fromCallable {
            recipeRepository.findByIdOrNull(command.recipeNo)?: throw RecipeNotFoundException()
        }.filter { it.creator.no == command.editorNo }
            .switchIfEmpty(Mono.error(UnAuthorizedRecipeException()))

        val categoriesMono = Mono.fromCallable { recipeCategoryRepository.findAllById(command.categoryNos) }

        return recipeMono.zipWith(categoriesMono) { recipe, categories ->
            recipe.modify(command, categories)
        }
    }
}