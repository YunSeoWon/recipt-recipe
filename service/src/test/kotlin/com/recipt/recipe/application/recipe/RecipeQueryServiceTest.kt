package com.recipt.recipe.application.recipe

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.exception.recipe.RecipeNotFoundException
import com.recipt.core.model.PageInfo
import com.recipt.core.model.map
import com.recipt.recipe.application.recipe.dto.Cooking
import com.recipt.recipe.application.recipe.dto.RecipeDetail
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.application.recipe.dto.RecipeSummary
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
                mainIngredientCategory = RecipeCategory(no = 1, title =  "주재료", type = CategoryType.MAIN_INGREDIENT),
                kindCategory = RecipeCategory(no = 2, title = "종류", type = CategoryType.KIND)
            )
        )
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
        val recipe = Recipe(
            no = recipeNo,
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
                mainIngredientCategory = RecipeCategory(no = 1, title =  "주재료", type = CategoryType.MAIN_INGREDIENT),
                kindCategory = RecipeCategory(no = 2, title = "종류", type = CategoryType.KIND)
            ),
            subCookings = listOf(
                SubCooking(no = 1, name = "양념", cookingIngredients = listOf(
                    CookingIngredient(name = "간장", amount = 1.0, unit = "큰술"),
                    CookingIngredient(name = "고추장", amount = 2.0, unit = "큰술")
                )),
                SubCooking(no = 1, name = "주재료", cookingIngredients = listOf(
                    CookingIngredient(name = "대패삼겹살", amount = 200.0, unit = "g"),
                    CookingIngredient(name = "양파", amount = 0.5, unit = "개")
                ))
            ),
            contents = listOf(
                RecipeContent(no = 1, order = 1, content = "먼저 양념을 만들기 위해 간장과 고추장을 섞습니다.", expectTime = 20),
                RecipeContent(no = 2, order = 2, content = "그 다음, 양파를 썰어놓습니다.", expectTime = 20)
            )
        )
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