package com.recipt.recipe.domain.converter

import com.recipt.core.enums.recipe.KindCategoryType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class KindCategoryTypeConverter: AttributeConverter<KindCategoryType, Int> {
    override fun convertToDatabaseColumn(attribute: KindCategoryType): Int {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: Int): KindCategoryType {
        return KindCategoryType.values().find { it.code == dbData } ?: throw Exception()
    }
}