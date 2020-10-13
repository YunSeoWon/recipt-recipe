package com.recipt.recipe.domain.recipe.entity

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.domain.converter.OpenRangeConverter
import com.recipt.recipe.domain.converter.Yn2BooleanConverter
import com.recipt.recipe.domain.recipe.vo.Categories
import com.recipt.recipe.domain.recipe.vo.Creator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "RECIPE")
data class Recipe(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_no")
    val no: Int = 0,

    val title: String,

    val introduction: String?,

    @Column(name = "thumbnail_image_url")
    val thumbnailImageUrl: String? = null,

    @Embedded
    val creator: Creator,

    @Column(name = "create_datetime")
    val createDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "edit_datetime")
    val editDateTime: LocalDateTime? = null,

    @Embedded
    val categories: Categories,

    val difficulty: Int = 0,

    @Column(name = "read_count")
    val readCount: Int = 0,

    @Column(name = "like_count")
    val likeCount: Int = 0,

    @Column(name = "posting_count")
    val postingCount: Int = 0,

    @Convert(converter = OpenRangeConverter::class)
    @Column(name = "open_range")
    val openRange: OpenRange = OpenRange.PUBLIC,

    @Convert(converter = Yn2BooleanConverter::class)
    @Column(name = "delete_yn")
    val deleted: Boolean = false,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], orphanRemoval = true)
    @JoinColumn(name = "recipe_no")
    val subCookings: List<SubCooking> = emptyList(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], orphanRemoval = true)
    @JoinColumn(name = "recipe_no")
    val contents: List<RecipeContent> = emptyList()
)
