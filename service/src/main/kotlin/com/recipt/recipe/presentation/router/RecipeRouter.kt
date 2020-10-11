package com.recipt.recipe.presentation.router

import com.recipt.recipe.presentation.handler.RecipeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

/**
 * Static 자원에 접근하기 위해 사용하는 Router
 */
@Configuration
class RecipeRouter(
    private val recipeHandler: RecipeHandler
) {
    @Bean
    fun recipeRoute(): RouterFunction<ServerResponse> {
        return coRouter {
            "/recipes".nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    GET("", recipeHandler::search)
                    GET("/{recipeNo}", recipeHandler::get)
                }
            }
        }
    }
}