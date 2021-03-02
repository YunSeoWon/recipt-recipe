package com.recipt.core.model

data class PageInfo<T>(
    val totalCount: Long,
    val contents: List<T>
)

fun <I, O> PageInfo<I>.map(transform: (I) -> O): PageInfo<O> {
    return PageInfo<O>(
        totalCount = this.totalCount,
        contents = this.contents.map(transform)
    )
}
