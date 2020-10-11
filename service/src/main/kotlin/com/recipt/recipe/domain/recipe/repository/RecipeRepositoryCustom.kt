package com.recipt.recipe.domain.recipe.repository

import com.recipt.core.model.PageInfo
import com.recipt.recipe.application.recipe.dto.RecipeSearchQuery
import com.recipt.recipe.domain.recipe.entity.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepositoryCustom {

    fun search(searchQuery: RecipeSearchQuery): PageInfo<Recipe>
}
