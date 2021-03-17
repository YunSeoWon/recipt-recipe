package com.recipt.recipe.domain.recipe

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.application.recipe.dto.RecipeContentCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.SubCookingCreateCommand
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import com.recipt.recipe.domain.recipe.vo.CookingIngredient

object RecipeTestData {
    val TEST_RECIPE = Recipe.create(
        RecipeCreateCommand(
            title = "title",
            introduction = null,
            creatorNo = 1,
            creatorName = "작성자",
            mainIngredientCategoryNo = 1,
            kindCategoryNo = 2,
            difficulty = 1,
            openRange = OpenRange.PUBLIC,
            thumbnailImageUrl = null,
            subCookings = listOf(
                SubCookingCreateCommand(
                    name = "양념",
                    ingredients = listOf(
                        CookingIngredient(name = "간장", amount = 1.0, unit = "큰술"),
                        CookingIngredient(name = "고추장", amount = 2.0, unit = "큰술")
                    )
                ),
                SubCookingCreateCommand(
                    name = "주재료",
                    ingredients = listOf(
                        CookingIngredient(name = "대패삼겹살", amount = 200.0, unit = "g"),
                        CookingIngredient(name = "양파", amount = 0.5, unit = "개")
                    )
                )
            ),
            contents = listOf(
                RecipeContentCreateCommand(order = 1, content = "먼저 양념을 만들기 위해 간장과 고추장을 섞습니다.", expectTime = 20, necessary = true, imageUrl = null),
                RecipeContentCreateCommand(order = 2, content = "그 다음, 양파를 썰어놓습니다.", expectTime = 20, necessary = true, imageUrl = null)
            )
        ),
        listOf(
            RecipeCategory(no = 1, title =  "주재료", type = CategoryType.MAIN_INGREDIENT),
            RecipeCategory(no = 2, title = "종류", type = CategoryType.KIND)
        )
    )
}