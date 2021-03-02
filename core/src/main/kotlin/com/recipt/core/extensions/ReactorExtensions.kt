package com.recipt.core.extensions

import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

fun <T> Mono<T>.thenUnit(): Mono<Unit> {
    return this.then(Mono.just(Unit))
}

fun <T> Mono<T>.filterOrError(error: Throwable, predicate: (T) -> Boolean): Mono<T> {
    return this.filter(predicate)
        .switchIfEmpty { Mono.error(error) }
}