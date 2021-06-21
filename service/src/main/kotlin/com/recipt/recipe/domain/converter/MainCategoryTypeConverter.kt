package com.recipt.recipe.domain.converter

import com.recipt.core.enums.recipe.MainCategoryType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class MainCategoryTypeConverter: AttributeConverter<MainCategoryType, Int> {
    override fun convertToDatabaseColumn(attribute: MainCategoryType): Int {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: Int): MainCategoryType {
        return MainCategoryType.values().find { it.code == dbData } ?: throw Exception()
    }
}