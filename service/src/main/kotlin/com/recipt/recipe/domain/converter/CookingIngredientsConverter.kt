package com.recipt.recipe.domain.converter

import com.fasterxml.jackson.databind.ObjectMapper
import com.recipt.recipe.domain.recipe.vo.CookingIngredient
import org.springframework.stereotype.Component
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
@Component
class CookingIngredientsConverter(
    private val objectMapper: ObjectMapper
): AttributeConverter<List<CookingIngredient>, String> {

    override fun convertToDatabaseColumn(attribute: List<CookingIngredient>): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String): List<CookingIngredient> {
        return objectMapper.readValue(dbData, List::class.java).map {
            it as CookingIngredient
        }
    }
}