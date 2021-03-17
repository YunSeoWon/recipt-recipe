package com.recipt.recipe.application.recipe

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.exception.recipe.RecipeNotFoundException
import com.recipt.core.model.PageInfo
import com.recipt.core.model.map
import com.recipt.recipe.application.recipe.dto.*
import com.recipt.recipe.domain.recipe.RecipeTestData
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import com.recipt.recipe.domain.recipe.entity.RecipeContent
import com.recipt.recipe.domain.recipe.entity.SubCooking
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import com.recipt.recipe.domain.recipe.vo.Categories
import com.recipt.recipe.domain.recipe.vo.CookingIngredient
import com.recipt.recipe.domain.recipe.vo.Creator
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
internal class RecipeQueryServiceTest {
    @MockK
    private lateinit var recipeRepository: RecipeRepository

    private lateinit var recipeQueryService: RecipeQueryService

    @BeforeEach
    fun setUp() {
        recipeQueryService = RecipeQueryService(recipeRepository)
    }

    @Test
    fun `레시피 검색`() {
        val recipe = RecipeTestData.TEST_RECIPE
        val recipes = PageInfo(
            10,
            listOf(recipe)
        )

        val expect = recipes.map { RecipeSummary.of(it) }

        val query = RecipeSearchQuery(
            writer = "작성자",
            mainCategoryNo = null,
            kindCategoryNo = null,
            page = 1,
            pageSize = 1,
            ranges = setOf(OpenRange.PUBLIC)
        )

        every { recipeRepository.search(query) } returns recipes

        val result = recipeQueryService.search(query)

        StepVerifier.create(result)
            .expectNext(expect)
            .verifyComplete()
    }

    @Test
    fun `레시피 조회`() {
        val recipeNo = 1
        val recipe = RecipeTestData.TEST_RECIPE
        val expected = RecipeDetail.of(recipe)

        every { recipeRepository.findByNoAndDeletedIsFalse(recipeNo) } returns recipe
        every { recipeRepository.findByNoAndDeletedIsFalse(not(recipeNo)) } returns null

        val result = recipeQueryService.get(recipeNo)

        StepVerifier.create(result)
            .expectNext(expected)
            .verifyComplete()

        val errorResult = recipeQueryService.get(recipeNo + 1)

        StepVerifier.create(errorResult)
            .expectError(RecipeNotFoundException::class.java)
            .verify()
    }
}