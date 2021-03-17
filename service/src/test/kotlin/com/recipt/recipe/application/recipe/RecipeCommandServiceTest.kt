package com.recipt.recipe.application.recipe

import com.recipt.recipe.application.recipe.dto.RecipeCreateCommand
import com.recipt.recipe.application.recipe.dto.RecipeModifyCommand
import com.recipt.recipe.domain.recipe.RecipeDomainService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.function.Consumer

@ExtendWith(MockKExtension::class)
internal class RecipeCommandServiceTest {

    @MockK
    private lateinit var recipeDomainService: RecipeDomainService

    private lateinit var recipeCommandService: RecipeCommandService

    @BeforeEach
    fun setUp() {
        recipeCommandService = RecipeCommandService(recipeDomainService)

//        every { transactionTemplate.execute(any<TransactionCallback<*>>()) } answers {
//            firstArg<TransactionCallback<*>>().doInTransaction(mockk())
//        }
    }

    @Test
    fun `레시피 생성`() {
        val command = mockk<RecipeCreateCommand>()

        every { recipeDomainService.create(command) } returns Mono.just(Unit)

        val result = recipeCommandService.create(command)

        StepVerifier.create(result)
            .expectNext(Unit)
            .verifyComplete()

        verify { recipeDomainService.create(command) }
    }

    @Test
    fun `레시피 변경`() {
        val command = mockk<RecipeModifyCommand>()

        every { recipeDomainService.modify(command) } returns Mono.just(Unit)

        val result = recipeCommandService.modify(command)

        StepVerifier.create(result)
            .expectNext(Unit)
            .verifyComplete()

        verify { recipeDomainService.modify(command) }
    }
}