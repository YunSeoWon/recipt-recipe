package com.recipt.core.enums.recipe

import com.recipt.core.enums.ReciptEnum

enum class OpenRange(
    override val code: Int
): ReciptEnum {
    PUBLIC(1),
    FOLLOWER(2),
    PRIVATE(3)
}