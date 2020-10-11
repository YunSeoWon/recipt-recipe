package com.recipt.recipe.presentation.filter

import com.recipt.core.http.ReciptAttributes
import com.recipt.core.http.ReciptHeaders
import com.recipt.core.model.MemberInfo
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class AccessTokenFilter: WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        exchange.request.headers[ReciptHeaders.AUTH_TOKEN]?.firstOrNull()?.let {
            if (it == ReciptHeaders.TEST_AUTH_TOKEN) {
                exchange.attributes[ReciptAttributes.MEMBER_INFO] = MemberInfo.TEST_MEMBER_INFO
            }
        }

        return chain.filter(exchange)
    }
}