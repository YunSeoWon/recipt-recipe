package com.recipt.core.exception.recipe

import com.recipt.core.exception.ReciptException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class RecipeNotFoundException: ReciptException(RecipeErrorCode.NOT_FOUND)

