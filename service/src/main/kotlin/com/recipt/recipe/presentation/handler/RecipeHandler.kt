package com.recipt.recipe.presentation.handler

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.exception.request.RequestBodyExtractFailedException
import com.recipt.recipe.application.recipe.RecipeCommandService
import com.recipt.recipe.application.recipe.RecipeQueryService
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.presentation.memberInfoOrThrow
import com.recipt.recipe.presentation.pathVariableToPositiveIntOrThrow
import com.recipt.recipe.presentation.queryParamToListOrNull
import com.recipt.recipe.presentation.queryParamToPositiveIntOrNull
import com.recipt.recipe.presentation.request.RecipeCreateRequest
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.noContent
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class RecipeHandler(
    private val recipeQueryService: RecipeQueryService,
    private val recipeCommandService: RecipeCommandService
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

        return recipeQueryService.search(query)
            .let { ok().body(it).awaitSingle()}
    }

    suspend fun get(request: ServerRequest): ServerResponse {
        val recipeNo = request.pathVariableToPositiveIntOrThrow("recipeNo")

        return recipeQueryService.get(recipeNo)
            .let { ok().body(it).awaitSingle() }
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val createRequest = request.awaitBodyOrNull<RecipeCreateRequest>()
            ?: throw RequestBodyExtractFailedException()

        val memberInfo = request.memberInfoOrThrow()

        recipeCommandService.create(createRequest.toCommand(memberInfo))
            .awaitSingle()

        return noContent().buildAndAwait()
    }
}