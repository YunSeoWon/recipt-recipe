package com.recipt.recipe.domain.recipe.entity

import com.recipt.recipe.application.recipe.dto.SubCookingCreateCommand
import com.recipt.recipe.domain.converter.CookingIngredientsConverter
import com.recipt.recipe.domain.recipe.vo.CookingIngredient
import javax.persistence.*

@Entity
@Table(name = "SUB_COOKING")
data class SubCooking(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_cooking_no")
    val no: Int = 0,

    @Column(name = "sub_cooking_name")
    val name: String,

    @Convert(converter = CookingIngredientsConverter::class)
    @Column(name = "sub_cooking_ingredients")
    val cookingIngredients: List<CookingIngredient>
) {
    companion object {
        fun create(command: SubCookingCreateCommand) = SubCooking(
            name = command.name,
            cookingIngredients = command.ingredients
        )
    }
}