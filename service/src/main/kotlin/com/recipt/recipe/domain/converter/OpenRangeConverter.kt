package com.recipt.recipe.domain.converter

import com.recipt.core.enums.recipe.OpenRange
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class OpenRangeConverter: AttributeConverter<OpenRange, Int> {
    override fun convertToDatabaseColumn(attribute: OpenRange): Int {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: Int): OpenRange {
        return OpenRange.values().find { it.code == dbData }?: throw Exception()
    }
}