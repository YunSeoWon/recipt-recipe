package com.recipt.recipe.domain.recipe

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
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
import reactor.test.StepVerifier

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
        val command = RecipeCreateCommand(
            mainIngredientCategoryNo = categoryNos[0],
            kindCategoryNo = categoryNos[1],
            title = "제육볶음",
            introduction = "제육볶음을 만들어봅시다",
            thumbnailImageUrl = null,
            creatorNo = 1,
            creatorName = "백종원",
            difficulty = 1,
            openRange = OpenRange.PUBLIC,
            subCookings = listOf(),
            contents = listOf()
        )

        val categories = listOf(RecipeCategory(
            no = 1,
            title = "카테고리",
            type = CategoryType.KIND,
            imageUrl = null
        ))

        val recipe = Recipe.create(command, categories)

        every { recipeCategoryRepository.findAllById(command.categoryNos) } returns categories
        every { recipeRepository.save(any<Recipe>()) } returns recipe

        val result = recipeDomainService.create(command)

        StepVerifier.create(result)
            .expectNext(Unit)
            .verifyComplete()

        verify {
            recipeCategoryRepository.findAllById(command.categoryNos)
            recipeRepository.save(any<Recipe>())
        }
    }
}