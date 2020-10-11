package com.recipt.recipe.domain.recipe.vo

import com.recipt.recipe.domain.recipe.entity.RecipeCategory
import javax.persistence.Embeddable
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
data class Categories(

    @ManyToOne(targetEntity = RecipeCategory::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_ingredient_category_no")
    val mainIngredientCategory: RecipeCategory,


    @ManyToOne(targetEntity = RecipeCategory::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "kind_category_no")
    val kindCategory: RecipeCategory
)