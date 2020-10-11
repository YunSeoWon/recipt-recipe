package com.recipt.core.model

import kotlin.reflect.KClass

data class PageInfo<T>(
    val totalCount: Long,
    val contents: List<T>
)