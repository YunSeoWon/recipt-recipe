package com.recipt.recipe.infrastructure.jpa

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.recipt.core.model.PageInfo
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.domain.recipe.entity.QRecipe
import com.recipt.recipe.domain.recipe.entity.Recipe
import com.recipt.recipe.domain.recipe.repository.RecipeRepositoryCustom
import org.springframework.stereotype.Repository

@Repository
class RecipeRepositoryImpl: RecipeRepositoryCustom, AbstractReciptRepository(Recipe::class) {
    private val recipe = QRecipe.recipe

    override fun search(searchQuery: RecipeSearchQuery): PageInfo<Recipe> {
        val predicate = BooleanBuilder().apply {
            searchQuery.writer?.let {
                this.and(recipe.creator.name.eq(it))
            }
            searchQuery.mainCategoryNo?.let {
                this.and(recipe.categories.mainIngredientCategory.no.eq(it))
            }
            searchQuery.kindCategoryNo?.let {
                this.and(recipe.categories.kindCategory.no.eq(it))
            }
            this.and(recipe.deleted.isFalse)
            this.and(recipe.openRange.`in`(searchQuery.ranges))
        }

        val query = from(recipe)
            .select(lazyRecipeProjection())
            .where(predicate)

        return PageInfo(
            totalCount = query.fetchCount(),
            contents = querydsl.applyPagination(searchQuery.page, query).fetch()
        )
    }

    private fun lazyRecipeProjection() = Projections.fields(Recipe::class.java,
        recipe.no,
        recipe.title,
        recipe.thumbnailImageUrl,
        recipe.creator,
        recipe.createDateTime,
        recipe.editDateTime,
        recipe.categories,
        recipe.difficulty,
        recipe.readCount,
        recipe.likeCount,
        recipe.postingCount,
        recipe.openRange
    )
}