package com.recipt.recipe.presentation.handler

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.application.recipe.RecipeQueryService
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.presentation.queryParamToListOrNull
import com.recipt.recipe.presentation.queryParamToPositiveIntOrNull
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

@Component
class RecipeHandler(
    private val recipeQueryService: RecipeQueryService
) {

    suspend fun search(request: ServerRequest): ServerResponse {
        val writer = request.queryParamOrNull("writer")
        val mainCategoryNo = request.queryParamToPositiveIntOrNull("mainCategoryNo")
        val kindCategoryNo = request.queryParamToPositiveIntOrNull("kindCategoryNo")
        val pageSize = request.queryParamToPositiveIntOrNull("pageSize")
        val page = request.queryParamToPositiveIntOrNull("page")
        val ranges = request.queryParamToListOrNull("ranges")
            ?.map { OpenRange.valueOf(it) }
            ?.toSet()
            ?: setOf(OpenRange.PUBLIC)

        // TODO: range에 대한 회원 검증 필요.

        val query = RecipeSearchQuery(
            writer = writer,
            mainCategoryNo = mainCategoryNo,
            kindCategoryNo = kindCategoryNo,
            page = page,
            pageSize = pageSize,
            ranges = ranges
        )

        return ok().bodyValueAndAwait(recipeQueryService.search(query))
    }

    suspend fun get(request: ServerRequest): ServerResponse {

        return ok().bodyValueAndAwait("")
    }
}