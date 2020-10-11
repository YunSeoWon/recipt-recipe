package com.recipt.recipe.presentation.handler

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.application.recipe.RecipeQueryService
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.mock.web.reactive.function.server.MockServerRequest

@ExtendWith(MockKExtension::class)
internal class RecipeHandlerTest {
    @MockK
    private lateinit var recipeQueryService: RecipeQueryService

    private lateinit var recipeHandler: RecipeHandler

    @BeforeEach
    fun setUp() {
        recipeHandler = RecipeHandler(recipeQueryService)
    }

    @Test
    fun `레시피 검색`() {
        val query = RecipeSearchQuery(
            writer = "작성자",
            mainCategoryNo = 1,
            kindCategoryNo = 2,
            pageSize = 10,
            page = 1,
            ranges = setOf(OpenRange.PUBLIC)
        )

        val request = MockServerRequest.builder()
            .queryParam("writer", query.writer!!)
            .queryParam("mainCategoryNo", query.mainCategoryNo!!.toString())
            .queryParam("kindCategoryNo", query.kindCategoryNo!!.toString())
            .queryParam("page", query.page.page.toString())
            .queryParam("pageSize", query.page.sizePerPage.toString())
            .queryParam("ranges", query.ranges.joinToString(","))
            .build()

        coEvery { recipeQueryService.search(query) } returns mockk()

        val result = runBlocking { recipeHandler.search(request) }

        coVerify { recipeQueryService.search(query) }
        assertEquals(HttpStatus.OK, result.statusCode())
    }
}