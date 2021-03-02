package com.recipt.recipe.application.recipe

import com.recipt.core.exception.recipe.RecipeNotFoundException
import com.recipt.core.model.PageInfo
import com.recipt.core.model.map
import com.recipt.recipe.application.recipe.dto.RecipeDetail
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.application.recipe.dto.RecipeSummary
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class RecipeQueryService(
    private val recipeRepository: RecipeRepository
) {

    fun search(query: RecipeSearchQuery): Mono<PageInfo<RecipeSummary>> {
        return Mono.fromCallable { recipeRepository.search(query) }
            .map { page ->
                page.map { RecipeSummary.of(it) }
            }.subscribeOn(Schedulers.elastic())
    }

    fun get(recipeNo: Int): Mono<RecipeDetail> {
        return Mono.fromCallable {
            recipeRepository.findByNoAndDeletedIsFalse(recipeNo)
                ?: throw RecipeNotFoundException()
        }
            .map { RecipeDetail.of(it) }
            .subscribeOn(Schedulers.elastic())
    }
}