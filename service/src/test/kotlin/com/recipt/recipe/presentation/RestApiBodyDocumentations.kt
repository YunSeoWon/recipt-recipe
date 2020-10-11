package com.recipt.recipe.presentation

import com.recipt.core.model.PageInfo
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.application.recipe.dto.RecipeSummary
import com.recipt.recipe.domain.recipe.vo.Creator
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