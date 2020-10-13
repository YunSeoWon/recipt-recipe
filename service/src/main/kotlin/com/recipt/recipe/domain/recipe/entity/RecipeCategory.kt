package com.recipt.recipe.domain.recipe.entity

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.recipe.domain.converter.CategoryTypeConverter
import javax.persistence.*

@Entity
@Table(name = "RECIPE_CATEGORY")
data class RecipeCategory(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_category_no")
    val no: Int,

    val title: String,

    @Convert(converter = CategoryTypeConverter::class)
    @Column(name = "category_type")
    val type: CategoryType,

    @Column(name = "category_image_url")
    val imageUrl: String? = null
)
