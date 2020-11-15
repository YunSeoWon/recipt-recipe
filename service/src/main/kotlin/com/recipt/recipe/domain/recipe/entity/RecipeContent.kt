package com.recipt.recipe.domain.recipe.entity

import com.recipt.recipe.application.recipe.dto.RecipeContentCreateCommand
import javax.persistence.*

@Entity
@Table(name = "RECIPE_CONTENT")
data class RecipeContent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_content_no")
    val no: Int = 0,

    val order: Int,

    val content: String,

    @Column(name = "expect_time")
    val expectTime: Int = 0,

    @Column(name = "necessary_yn")
    val necessary: Boolean = true,

    @Column(name = "image_url")
    val imageUrl: String? = null
) {
    companion object {
        fun create(command: RecipeContentCreateCommand) = RecipeContent(
            order = command.order,
            content = command.content,
            expectTime = command.expectTime,
            necessary = command.necessary,
            imageUrl = command.imageUrl
        )
    }
}