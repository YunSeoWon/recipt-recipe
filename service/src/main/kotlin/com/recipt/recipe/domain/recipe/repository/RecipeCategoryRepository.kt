package com.recipt.recipe.domain.recipe.repository

import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeCategoryRepository : JpaRepository<RecipeCategory, Int>