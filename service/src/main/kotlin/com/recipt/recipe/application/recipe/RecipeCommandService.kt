package com.recipt.recipe.application.recipe

import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.domain.recipe.RecipeDomainService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
class RecipeCommandService (
    private val recipeDomainService: RecipeDomainService
) {

    @Transactional
    fun create(command: RecipeCreateCommand): Mono<Unit> {
        return recipeDomainService.create(command)
            .subscribeOn(Schedulers.elastic())
    }
}