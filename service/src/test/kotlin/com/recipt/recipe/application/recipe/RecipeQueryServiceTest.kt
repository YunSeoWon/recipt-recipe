package com.recipt.recipe.application.recipe

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.exception.recipe.RecipeNotFoundException
import com.recipt.core.model.PageInfo
import com.recipt.core.model.map
import com.recipt.recipe.application.recipe.dto.*
import com.recipt.recipe.domain.recipe.RecipeTestData
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import reactor.test.StepVerifier

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
        val recipe = RecipeTestData.TEST_RECIPE.copy()
        val expected = RecipeDetail.of(recipe)
            .copy(readCount = recipe.readCount + 1)

        every { recipeRepository.findByNoAndDeletedIsFalse(recipeNo) } returns recipe

        val result = recipeQueryService.get(recipeNo)

        StepVerifier.create(result)
            .expectNext(expected)
            .verifyComplete()
    }

    @Test
    fun `레시피 조회 (레시피가 없는 경우)`() {
        val recipeNo = 1

        every { recipeRepository.findByNoAndDeletedIsFalse(recipeNo) } returns null

        val errorResult = recipeQueryService.get(recipeNo)

        StepVerifier.create(errorResult)
            .expectError(RecipeNotFoundException::class.java)
            .verify()
    }
}