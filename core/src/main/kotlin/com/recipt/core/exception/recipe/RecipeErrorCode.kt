package com.recipt.core.exception.recipe

import com.recipt.core.exception.ErrorCode

enum class RecipeErrorCode(override val code: String): ErrorCode {
    NOT_FOUND("error.recipe.not-found");
}