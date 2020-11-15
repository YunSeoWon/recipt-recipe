package com.recipt.recipe.presentation.router

import com.ninjasquad.springmockk.MockkBean
import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.http.ReciptHeaders
import com.recipt.core.model.PageInfo
import com.recipt.member.presentation.exception.GlobalErrorAttributes
import com.recipt.member.presentation.exception.GlobalErrorWebExceptionHandler
import com.recipt.recipe.application.recipe.RecipeCommandService
import com.recipt.recipe.application.recipe.RecipeQueryService
import com.recipt.recipe.application.recipe.dto.*
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import com.recipt.recipe.domain.recipe.entity.RecipeContent
import com.recipt.recipe.domain.recipe.entity.SubCooking
import com.recipt.recipe.domain.recipe.vo.Categories
import com.recipt.recipe.domain.recipe.vo.CookingIngredient
import com.recipt.recipe.domain.recipe.vo.Creator
import com.recipt.recipe.presentation.filter.AccessTokenFilter
import com.recipt.recipe.presentation.handler.RecipeHandler
import com.recipt.recipe.presentation.request.RecipeCreateRequest
import com.recipt.recipe.presentation.toDocument
import io.mockk.coEvery
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
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

    @MockkBean
    private lateinit var recipeCommandService: RecipeCommandService

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

    @Test
    fun `레시피 상세 조회`() {
        val recipeNo = 1
        val response = Recipe(
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
        ).let { RecipeDetail(it) }

        coEvery { recipeQueryService.get(recipeNo) } returns response

        webTestClient.get()
            .uri("/recipes/{recipeNo}", recipeNo)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "get-recipe-detail",
                    pathParameters(
                        parameterWithName("recipeNo").description("레시피 번호")
                    ),
                    responseFields(
                        *response.toDocument()
                    )
                )
            )
    }

    @Test
    fun `레시피 생성`() {
        val createRequest = RecipeCreateRequest(
            title = "레시피",
            introduction = "테스트용 레시피입니다.",
            thumbnailImageUrl = "https://www.a.com",
            mainIngredientCategoryNo = 1,
            kindCategoryNo = 1,
            difficulty = 1,
            openRange = OpenRange.PUBLIC,
            subCookings = listOf(
                SubCookingCreateCommand(
                    name = "주재료",
                    ingredients = listOf(
                        CookingIngredient(
                            name = "돼지고기",
                            amount = 400.0,
                            unit = "g"
                        )
                    )
                )
            ),
            contents = listOf(
                RecipeContentCreateCommand(
                    order = 1,
                    content = "돼지고기를 굽는다",
                    expectTime = 300,
                    necessary = true,
                    imageUrl = "https://www.a.com"
                )
            )
        )

        coEvery { recipeCommandService.create(any()) } just runs

        webTestClient.post()
            .uri("/recipes")
            .header(ReciptHeaders.AUTH_TOKEN, ReciptHeaders.TEST_AUTH_TOKEN)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(createRequest)
            .exchange()
            .expectStatus().isNoContent
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "create-recipe",
                    requestFields(
                        *createRequest.toDocument()
                    )
                )
            )
    }
}
