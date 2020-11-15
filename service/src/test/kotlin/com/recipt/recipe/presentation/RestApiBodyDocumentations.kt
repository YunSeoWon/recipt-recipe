package com.recipt.recipe.presentation

import com.recipt.core.model.PageInfo
import com.recipt.recipe.application.recipe.dto.*
import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import com.recipt.recipe.domain.recipe.vo.Categories
import com.recipt.recipe.domain.recipe.vo.CookingIngredient
import com.recipt.recipe.domain.recipe.vo.Creator
import com.recipt.recipe.presentation.request.RecipeCreateRequest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

fun RecipeSearchQuery.toDocument() = arrayOf(
    parameterWithName("writer")
        .description("작성자").optional(),
    parameterWithName("mainCategoryNo")
        .description("메인재료 카테고리 번호").optional(),
    parameterWithName("kindCategoryNo")
        .description("요리종류 카테고리 번호").optional(),
    parameterWithName("page")
        .description("페이지 번호 (default = 1)").optional(),
    parameterWithName("pageSize")
        .description("페이지 크기 (default = 10").optional(),
    parameterWithName("ranges")
        .description("공개 범위 (중복 선택 가능)")
)

fun RecipeSummary.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}no").type(JsonFieldType.NUMBER)
        .description("레시피 번호"),
    fieldWithPath("${prefix}title").type(JsonFieldType.STRING)
        .description("레시피 제목"),
    fieldWithPath("${prefix}thumbnailImageUrl").type(JsonFieldType.STRING)
        .description("레시피 썸네일 이미지 url").optional(),
    fieldWithPath("${prefix}creator").type(JsonFieldType.OBJECT)
        .description("레시피 작성자"),
    *creator.toDocument("${prefix}creator."),
    fieldWithPath("${prefix}createDateTime").type(JsonFieldType.STRING)
        .description("레시피 생성 시간"),
    fieldWithPath("${prefix}editDateTime").type(JsonFieldType.STRING)
        .description("레시피 수정 시간").optional(),
    fieldWithPath("${prefix}difficulty").type(JsonFieldType.NUMBER)
        .description("레시피 난이도"),
    fieldWithPath("${prefix}readCount").type(JsonFieldType.NUMBER)
        .description("조회 수"),
    fieldWithPath("${prefix}likeCount").type(JsonFieldType.NUMBER)
        .description("좋아요 수"),
    fieldWithPath("${prefix}postingCount").type(JsonFieldType.NUMBER)
        .description("포스팅 수")
)

fun Creator.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}no").type(JsonFieldType.NUMBER)
        .description("레시피 작성자 번호"),
    fieldWithPath("${prefix}name").type(JsonFieldType.STRING)
        .description("레시피 작성자 이름")
)

fun RecipeDetail.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}no").type(JsonFieldType.NUMBER)
        .description("레시피 번호"),
    fieldWithPath("${prefix}title").type(JsonFieldType.STRING)
        .description("레시피 제목"),
    fieldWithPath("${prefix}introduction").type(JsonFieldType.STRING)
        .description("레시피 소개 글").optional(),
    fieldWithPath("${prefix}thumbnailImageUrl").type(JsonFieldType.STRING)
        .description("레시피 썸네일 이미지 url").optional(),
    fieldWithPath("${prefix}creator").type(JsonFieldType.OBJECT)
        .description("레시피 작성자"),
    *creator.toDocument("${prefix}creator."),
    fieldWithPath("${prefix}createDateTime").type(JsonFieldType.STRING)
        .description("레시피 생성 시간"),
    fieldWithPath("${prefix}editDateTime").type(JsonFieldType.STRING)
        .description("레시피 수정 시간").optional(),
    fieldWithPath("${prefix}difficulty").type(JsonFieldType.NUMBER)
        .description("레시피 난이도"),
    fieldWithPath("${prefix}readCount").type(JsonFieldType.NUMBER)
        .description("조회 수"),
    fieldWithPath("${prefix}likeCount").type(JsonFieldType.NUMBER)
        .description("좋아요 수"),
    fieldWithPath("${prefix}postingCount").type(JsonFieldType.NUMBER)
        .description("포스팅 수"),
    fieldWithPath("${prefix}categories").type(JsonFieldType.OBJECT)
        .description("레시피 카테고리 정보"),
    *categories.toDocument("${prefix}categories."),
    fieldWithPath("${prefix}subCookings").type(JsonFieldType.ARRAY)
        .description("서브 요리 정보"),
    *subCookings[0].toDocument("${prefix}subCookings[]."),
    fieldWithPath("${prefix}contents").type(JsonFieldType.ARRAY)
        .description("레시피 내용 정보"),
    *contents[0].toDocument("${prefix}contents[].")
)

fun Categories.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}mainIngredientCategory").type(JsonFieldType.OBJECT)
        .description("주 재료 카테고리 정보"),
    *mainIngredientCategory.toDocument("${prefix}mainIngredientCategory."),
    fieldWithPath("${prefix}kindCategory").type(JsonFieldType.OBJECT)
        .description("요리 종류 카테고리 정보"),
    *mainIngredientCategory.toDocument("${prefix}kindCategory.")
)

fun RecipeCategory.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}no").type(JsonFieldType.NUMBER)
        .description("카테고리 번호"),
    fieldWithPath("${prefix}title").type(JsonFieldType.STRING)
        .description("카테고리 제목"),
    fieldWithPath("${prefix}type").type(JsonFieldType.STRING)
        .description("카테고리 타입"),
    fieldWithPath("${prefix}imageUrl").type(JsonFieldType.STRING)
        .description("카테고리 아이콘 이미지 url").optional()
)

fun Cooking.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}name").type(JsonFieldType.STRING)
        .description("서브 요리 이름"),
    fieldWithPath("${prefix}ingredients").type(JsonFieldType.ARRAY)
        .description("서브 요리재료 정보"),
    *ingredients[0].toDocument("${prefix}ingredients[].")
)

fun CookingIngredient.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}name").type(JsonFieldType.STRING)
        .description("서브 요리재료 이름"),
    fieldWithPath("${prefix}amount").type(JsonFieldType.NUMBER)
        .description("서브 요리재료 양"),
    fieldWithPath("${prefix}unit").type(JsonFieldType.STRING)
        .description("서브 요리재료 단위")
)

fun Content.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}order").type(JsonFieldType.NUMBER)
        .description("레시피 순서"),
    fieldWithPath("${prefix}content").type(JsonFieldType.STRING)
        .description("레시피 내용"),
    fieldWithPath("${prefix}expectTime").type(JsonFieldType.NUMBER)
        .description("예상 시간"),
    fieldWithPath("${prefix}necessary").type(JsonFieldType.BOOLEAN)
        .description("필수 과정 여부"),
    fieldWithPath("${prefix}imageUrl").type(JsonFieldType.STRING)
        .description("이미지 url").optional()
)

fun RecipeCreateRequest.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}title").type(JsonFieldType.STRING)
        .description("레시피 제목"),
    fieldWithPath("${prefix}introduction").type(JsonFieldType.STRING)
        .description("레시피 소개글"),
    fieldWithPath("${prefix}thumbnailImageUrl").type(JsonFieldType.STRING)
        .description("레시피 썸네일 url").optional(),
    fieldWithPath("${prefix}mainIngredientCategoryNo").type(JsonFieldType.NUMBER)
        .description("레시피 메인 재료 카테고리 번호"),
    fieldWithPath("${prefix}kindCategoryNo").type(JsonFieldType.NUMBER)
        .description("레시피 종류 카테고리 번호"),
    fieldWithPath("${prefix}difficulty").type(JsonFieldType.NUMBER)
        .description("레시피 난이도 (0~5)"),
    fieldWithPath("${prefix}openRange").type(JsonFieldType.STRING)
        .description("레시피 공개 범위"),
    fieldWithPath("${prefix}subCookings").type(JsonFieldType.ARRAY)
        .description("레시피 서브재료"),
    *subCookings.first().toDocument("${prefix}subCookings[]."),
    fieldWithPath("${prefix}contents").type(JsonFieldType.ARRAY)
        .description("레시피 내용"),
    *contents.first().toDocument("${prefix}contents[].")
)

fun SubCookingCreateCommand.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}name").type(JsonFieldType.STRING)
        .description("레시피 서브재료 이름"),
    fieldWithPath("${prefix}ingredients").type(JsonFieldType.ARRAY)
        .description("레시피 서브재료들"),
    *ingredients.first().toDocument("${prefix}.ingredients[].")
)

fun RecipeContentCreateCommand.toDocument(prefix: String = "") = arrayOf(
    fieldWithPath("${prefix}order").type(JsonFieldType.NUMBER)
        .description("레시피 내용 순서"),
    fieldWithPath("${prefix}content").type(JsonFieldType.STRING)
        .description("레시피 내용 본문"),
    fieldWithPath("${prefix}expectTime").type(JsonFieldType.NUMBER)
        .description("내용 당 걸리는 시간").optional(),
    fieldWithPath("${prefix}necessary").type(JsonFieldType.BOOLEAN)
        .description("필수 단계 여부"),
    fieldWithPath("${prefix}imageUrl").type(JsonFieldType.STRING)
        .description("레시피 내용 설명 이미지 url").optional()
)