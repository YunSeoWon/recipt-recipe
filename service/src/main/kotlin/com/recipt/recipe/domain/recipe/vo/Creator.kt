package com.recipt.recipe.domain.recipe.vo

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Creator(
    @Column(name = "creator_no")
    val no: Int,

    @Column(name = "creator_name")
    val name: String
)