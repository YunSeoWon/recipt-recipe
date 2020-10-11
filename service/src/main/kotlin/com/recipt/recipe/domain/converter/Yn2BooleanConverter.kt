package com.recipt.recipe.domain.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class Yn2BooleanConverter: AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean): String {
        return if(attribute) "Y" else "N"
    }

    override fun convertToEntityAttribute(dbData: String): Boolean {
        return dbData == "Y"
    }
}