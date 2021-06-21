package com.recipt.recipe.domain.recipe.entity

import com.recipt.core.enums.recipe.OpenRange
import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeModifyCommand
import com.recipt.recipe.domain.converter.OpenRangeConverter
import com.recipt.recipe.domain.converter.Yn2BooleanConverter
import com.recipt.recipe.domain.recipe.vo.RecipeCategory
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

    @Embedded
    val creator: Creator,

    @Column(name = "create_datetime")
    val createDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "like_count")
    val likeCount: Int = 0,

    @Column(name = "posting_count")
    val postingCount: Int = 0,

    @Convert(converter = Yn2BooleanConverter::class)
    @Column(name = "delete_yn")
    val deleted: Boolean = false,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], orphanRemoval = true)
    @JoinColumn(name = "recipe_no")
    val subCookings: List<SubCooking> = emptyList(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], orphanRemoval = true)
    @JoinColumn(name = "recipe_no")
    val contents: List<RecipeContent> = emptyList()
) {

    var title: String = ""
        private set

    var introduction: String? = null
        private set

    @Column(name = "thumbnail_image_url")
    var thumbnailImageUrl: String? = null
        private set

    @Column(name = "edit_datetime")
    var editDateTime: LocalDateTime? = null
        private set

    @Embedded
    var category: RecipeCategory = RecipeCategory.NOTHING
        private set

    var difficulty: Int = 0
        private set

    @Column(name = "read_count")
    var readCount: Int = 0
        private set

    @Convert(converter = OpenRangeConverter::class)
    @Column(name = "open_range")
    var openRange: OpenRange = OpenRange.PUBLIC
        private set

    companion object {
        fun create(command: RecipeCreateCommand) = Recipe(
            creator = Creator(
                no = command.creatorNo,
                name = command.creatorName
            ),
            createDateTime = LocalDateTime.now(),
            subCookings = command.subCookings.map { SubCooking.create(it) },
            contents = command.contents.map { RecipeContent.create(it) }
        ).apply {
            this.title = command.title
            this.introduction = command.introduction
            this.thumbnailImageUrl = command.thumbnailImageUrl
            this.category = RecipeCategory(
                mainCategoryType = command.mainCategoryType,
                kindCategoryType = command.kindCategoryType
            )
            this.difficulty = command.difficulty
            this.openRange = command.openRange
        }
    }

    fun modify(command: RecipeModifyCommand) {
        this.title = command.title
        this.introduction = command.introduction
        this.thumbnailImageUrl = command.thumbnailImageUrl
        this.category = RecipeCategory(
            mainCategoryType = command.mainCategoryType,
            kindCategoryType = command.kindCategoryType
        )
        this.difficulty = command.difficulty
        this.openRange = command.openRange
        this.editDateTime = LocalDateTime.now()
    }

    fun read() {
        this.readCount++
    }
}
