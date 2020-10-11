package com.recipt.recipe.presentation.router

import com.ninjasquad.springmockk.MockkBean
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.model.PageInfo
import com.recipt.member.presentation.exception.GlobalErrorAttributes
import com.recipt.member.presentation.exception.GlobalErrorWebExceptionHandler
import com.recipt.recipe.application.recipe.RecipeQueryService
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.application.recipe.dto.RecipeSummary
import com.recipt.recipe.domain.recipe.vo.Creator
import com.recipt.recipe.presentation.filter.AccessTokenFilter
import com.recipt.recipe.presentation.handler.RecipeHandler
import com.recipt.recipe.presentation.toDocument
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime

@WebFluxTest
@ExtendWith(RestDocumentationExtension::class)
@ContextConfiguration(classes = [RecipeRouter::class, RecipeHandler::class, AccessTokenFilter::class, GlobalErrorWebExceptionHandler::class, GlobalErrorAttributes::class])
internal class RecipeRouterTest {
    @MockkBean
    private lateinit var recipeQueryService: RecipeQueryService

    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setUp(
        applicationContext: ApplicationContext,
        restDocumentation: RestDocumentationContextProvider
    ) {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withResponseDefaults(Preprocessors.prettyPrint()))
            .build()
    }

    @Test
    fun `레시피 검색`() {
        val query = RecipeSearchQuery(
            writer = "작성자",
            mainCategoryNo = 1,
            kindCategoryNo = 2,
            page = 1,
            pageSize = 10,
            ranges = setOf(OpenRange.PUBLIC)
        )

        val summary = RecipeSummary(
            no = 1,
            title = "제목",
            thumbnailImageUrl = null,
            creator = Creator(1, "작성자"),
            createDateTime = LocalDateTime.now(),
            editDateTime = null,
            difficulty = 1,
            readCount = 1,
            likeCount = 1,
            postingCount = 1
        )

        val recipes = PageInfo(
            10,
            listOf(summary)
        )

        coEvery { recipeQueryService.search(query) } returns recipes

        webTestClient.get()
            .uri("/recipes?writer=${query.writer!!}&mainCategoryNo=${query.mainCategoryNo!!}&kindCategoryNo=${query.kindCategoryNo!!}&page=${query.page.page}&pageSize=${query.page.sizePerPage}&ranges=${query.ranges.joinToString(",")}")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "recipe-search",
                    requestParameters(
                        *query.toDocument()
                    ),
                    responseFields(
                        fieldWithPath("totalCount").type(JsonFieldType.NUMBER)
                            .description("총 갯수"),
                        fieldWithPath("contents").type(JsonFieldType.ARRAY)
                            .description("레시피"),
                        *summary.toDocument("contents[].")
                    )
                )
            )
    }
}
