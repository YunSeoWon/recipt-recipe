package com.recipt.recipe.domain.recipe

import com.recipt.core.enums.recipe.KindCategoryType
import com.recipt.core.enums.recipe.MainCategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.core.exception.recipe.UnAuthorizedRecipeException
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeModifyCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.repository.RecipeRepository
import com.recipt.recipe.domain.recipe.vo.Creator
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
internal class RecipeDomainServiceTest {

    @MockK
    private lateinit var recipeRepository: RecipeRepository

    @InjectMockKs
    private lateinit var recipeDomainService: RecipeDomainService

    @Test
    fun `레시피 생성`() {
        val categoryNos = listOf(1,2)
        val command = RecipeCreateCommand(
            mainIngredientCategoryNo = categoryNos[0],
            kindCategoryNo = categoryNos[1],
            title = "제육볶음",
            introduction = "제육볶음을 만들어봅시다",
            thumbnailImageUrl = null,
            creatorNo = 1,
            creatorName = "백종원",
            difficulty = 1,
            openRange = OpenRange.PUBLIC,
            subCookings = listOf(),
            contents = listOf(),
            mainCategoryType = MainCategoryType.OTHER,
            kindCategoryType = KindCategoryType.OTHER
        )

        val recipe = Recipe.create(command)

        every { recipeRepository.save(any<Recipe>()) } returns recipe

        val result = recipeDomainService.create(command)

        StepVerifier.create(result)
            .expectNext(Unit)
            .verifyComplete()

        verify {
            recipeRepository.save(any<Recipe>())
        }
    }

    @Test
    fun `레시피 변경`() {
        val categoryNos = listOf(1,2)
        val command = RecipeModifyCommand(
            mainIngredientCategoryNo = categoryNos[0],
            kindCategoryNo = categoryNos[1],
            title = "제육볶음",
            introduction = "제육볶음을 만들어봅시다",
            thumbnailImageUrl = null,
            editorNo = 1,
            difficulty = 1,
            openRange = OpenRange.PUBLIC,
            recipeNo = 1,
            mainCategoryType = MainCategoryType.OTHER,
            kindCategoryType = KindCategoryType.OTHER
        )

        val recipe = mockk<Recipe> {
            every { creator } returns Creator(1, "백종원")
            every { modify(any()) } just runs
        }

        every { recipeRepository.findByIdOrNull(command.recipeNo) } returns recipe

        val result = recipeDomainService.modify(command)

        StepVerifier.create(result)
            .expectNext(Unit)
            .verifyComplete()

        verify {
            recipeRepository.findByIdOrNull(command.recipeNo)
            recipe.modify(any())
        }
    }

    @Test
    fun `레시피 변경 (권한 없음)`() {
        val categoryNos = listOf(1,2)
        val command = RecipeModifyCommand(
            mainIngredientCategoryNo = categoryNos[0],
            kindCategoryNo = categoryNos[1],
            title = "제육볶음",
            introduction = "제육볶음을 만들어봅시다",
            thumbnailImageUrl = null,
            editorNo = 1,
            difficulty = 1,
            openRange = OpenRange.PUBLIC,
            recipeNo = 1,
            mainCategoryType = MainCategoryType.OTHER,
            kindCategoryType = KindCategoryType.OTHER
        )

        val recipe = mockk<Recipe> {
            every { creator } returns Creator(2, "승우아빠")
            every { modify(any()) } just runs
        }

        every { recipeRepository.findByIdOrNull(command.recipeNo) } returns recipe

        val result = recipeDomainService.modify(command)

        StepVerifier.create(result)
            .expectError(UnAuthorizedRecipeException::class.java)
            .verify()

        verify {
            recipeRepository.findByIdOrNull(command.recipeNo)
        }
        verify(exactly = 0) {
            recipe.modify(any())
        }
    }
}