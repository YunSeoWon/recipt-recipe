package com.recipt.recipe.domain.recipe.vo

import com.recipt.core.enums.recipe.KindCategoryType
import com.recipt.core.enums.recipe.MainCategoryType
import com.recipt.recipe.domain.converter.KindCategoryTypeConverter
import com.recipt.recipe.domain.converter.MainCategoryTypeConverter
import javax.persistence.*

@Embeddable
data class RecipeCategory(

    @Convert(converter = MainCategoryTypeConverter::class)
    @Column(name = "main_category_type")
    val mainCategoryType: MainCategoryType,


    @Convert(converter = KindCategoryTypeConverter::class)
    @Column(name = "kind_category_type")
    val kindCategoryType: KindCategoryType
) {
    companion object {
        val NOTHING: RecipeCategory = RecipeCategory(
            mainCategoryType = MainCategoryType.OTHER,
            kindCategoryType = KindCategoryType.OTHER
        )
    }
}