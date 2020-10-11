package com.recipt.recipe.application.recipe

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.model.PageInfo
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.application.recipe.dto.RecipeSummary
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import com.recipt.recipe.domain.recipe.vo.Categories
import com.recipt.recipe.domain.recipe.vo.Creator
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
        val recipe = Recipe(
            no = 1,
            title = "title",
            introduction = null,
            creator = Creator(1, "작성자"),
            createDateTime = LocalDateTime.now(),
            editDateTime = null,
            difficulty = 1,
            readCount = 1,
            likeCount = 1,
            postingCount = 1,
            categories = Categories(
                mainIngredientCategory = RecipeCategory(no = 1, title =  "주재료", categoryType = CategoryType.MAIN_INGREDIENT),
                kindCategory = RecipeCategory(no = 2, title = "종류", categoryType = CategoryType.KIND)
            )
        )
        val recipes = PageInfo(
            10,
            listOf(recipe)
        )

        val query = RecipeSearchQuery(
            writer = "작성자",
            mainCategoryNo = null,
            kindCategoryNo = null,
            page = 1,
            pageSize = 1,
            ranges = setOf(OpenRange.PUBLIC)
        )

        every { recipeRepository.search(query) } returns recipes

        val result = runBlocking { recipeQueryService.search(query) }

        assertEquals(PageInfo(10, listOf(RecipeSummary(recipe))), result)
    }
}