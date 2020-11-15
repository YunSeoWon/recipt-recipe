package com.recipt.recipe.application.recipe

import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.domain.recipe.RecipeDomainService
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate

@Component
class RecipeCommandService (
    private val recipeDomainService: RecipeDomainService,
    private val transactionTemplate: TransactionTemplate
) {
    suspend fun create(command: RecipeCreateCommand) {
        transactionTemplate.execute {
            recipeDomainService.create(command)
        }
    }
}