package com.recipt.recipe.domain.recipe

import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import com.recipt.recipe.domain.recipe.repository.RecipeCategoryRepository
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class RecipeDomainServiceTest {

    @MockK
    private lateinit var recipeRepository: RecipeRepository

    @MockK
    private lateinit var recipeCategoryRepository: RecipeCategoryRepository

    private lateinit var recipeDomainService: RecipeDomainService

    @BeforeEach
    fun setup() {
        recipeDomainService = RecipeDomainService(recipeRepository, recipeCategoryRepository)
    }

    @Test
    fun `레시피 생성`() {
        val categoryNos = listOf(1,2)
        val command = mockk<RecipeCreateCommand> {
            every { mainIngredientCategoryNo } returns categoryNos[0]
            every { kindCategoryNo } returns categoryNos[1]
        }

        val categories = listOf(mockk<RecipeCategory>())
        val recipe = mockk<Recipe>()

        mockkStatic(Recipe::class)

        every { Recipe.create(any(), categories) } returns recipe

        every { recipeCategoryRepository.findAllById(categoryNos) } returns categories
        every { recipeRepository.save(recipe) } returns recipe

        recipeDomainService.create(command)

        verify {
            Recipe.create(any(), categories)
            recipeCategoryRepository.findAllById(categoryNos)
            recipeRepository.save(recipe)
        }
    }
}