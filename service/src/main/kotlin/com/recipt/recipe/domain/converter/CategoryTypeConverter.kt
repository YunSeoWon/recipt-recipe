package com.recipt.recipe.domain.converter

import com.recipt.core.enums.recipe.CategoryType
import com.recipt.core.enums.recipe.OpenRange
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CategoryTypeConverter: AttributeConverter<CategoryType, Int> {
    override fun convertToDatabaseColumn(attribute: CategoryType): Int {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: Int): CategoryType {
        return CategoryType.values().find { it.code == dbData } ?: throw Exception()
    }
}