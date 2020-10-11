package com.recipt.core.model

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class ReciptPage(
    val page: Int,
    val sizePerPage: Int,
    val sortKeyword: String?
): Pageable {
    constructor(page: Int?, sizePerPage: Int?, sortKeyword: String?): this(
        page?: 1,
        sizePerPage?: 10,
        sortKeyword
    )

    override fun getPageNumber(): Int = page

    override fun hasPrevious(): Boolean = page > 1

    override fun getSort(): Sort = sortKeyword?.let {
        Sort.by(it)
    }?: Sort.by(*arrayOfNulls<Sort.Order>(0))

    override fun next(): Pageable {
        return ReciptPage(page+1, sizePerPage, sortKeyword)
    }

    override fun getPageSize(): Int = sizePerPage

    override fun getOffset(): Long = ((page - 1) * sizePerPage).toLong()

    override fun first(): Pageable {
        return ReciptPage(1, sizePerPage, sortKeyword)
    }

    override fun previousOrFirst(): Pageable {
        return when {
            page == 1 -> this
            page > 1 -> ReciptPage(page - 1, sizePerPage, sortKeyword)
            else -> throw Exception()
        }
    }
}