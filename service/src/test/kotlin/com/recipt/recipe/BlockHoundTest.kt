//package com.recipt.recipe
//
//import kotlinx.coroutines.debug.CoroutinesBlockHoundIntegration
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import reactor.blockhound.BlockHound
//import reactor.blockhound.integration.BlockHoundIntegration
//import reactor.blockhound.integration.ReactorIntegration
//import reactor.core.publisher.Mono
//import reactor.core.scheduler.Schedulers
//import reactor.test.StepVerifier
//import java.time.Duration
//
//class BlockHoundTest {
//
//    val blockHound = BlockHound.install(
//        CoroutinesBlockHoundIntegration(),
//        ReactorIntegration(),
//        TestBlockHoundIntegration()
//    )
//
//    @Test
//    fun `marking some methods as blocking`() {
//        val test = Mono.just(TestClass(1000))
//            .map { it.runAsBlocking() }
//
//        StepVerifier.create(test)
//            .expectNext(1000)
//            .verifyComplete()
//
//    }
//
//    @Test
//    fun `BlockHound test1`() {
//        Mono.delay(Duration.ofMillis(1)) // Blocking Code 1
//            .doOnNext {
//                try {
//                    Thread.sleep(10)
//                } catch (e: InterruptedException) {
//                    throw RuntimeException()
//                }
//            }
//            .block() // Blocking Code 2
//    }
//
//    @Test
//    fun `BlockHound test2, with StepVerifier`() {
//        val mono = Mono.delay(Duration.ofMillis(1)) // Blocking Code 1
//            .doOnNext {
//                runBlocking { // Blocking Code
//                    delay(100L)
//                }
//            }
//
//        StepVerifier.create(mono)
//            .expectNext(0)
//            .verifyComplete()
//    }
//
//    @Test
//    fun `BlockHound test3, with StepVerifier, nonBlocking`() {
//        val mono = Mono.just(100)
//            .map { it * 2 }
//
//        StepVerifier.create(mono)
//            .expectNext(200)
//            .verifyComplete()
//    }
//
//    @Test
//    fun `BlockHound test4, with StepVerifier, using fromCallable`() {
//        val mono = Mono.fromCallable {
//            Thread.sleep(1000)
//            200
//        }.subscribeOn(Schedulers.elastic())
//
//        StepVerifier.create(mono)
//            .expectNext(200)
//            .verifyComplete()
//    }
//
//    @Test
//    fun `BlockHound test, detecting runBlocking`() {
//        val mono = Mono.just(200)
//            .map {
//                runBlocking { // Blocking Code
//                    delay(100L)
//                }
//                it * 2
//            }.map {
//                Thread.sleep(100)
//                it * 2
//            }
//
//
//        StepVerifier.create(mono)
//            .expectNext(800)
//            .verifyComplete()
//    }
//}
//
//class TestClass(private val value: Int) {
//
//    fun runAsBlocking(): Int {
//        return value
//    }
//}
//
//class TestBlockHoundIntegration: BlockHoundIntegration {
//    override fun applyTo(builder: BlockHound.Builder?) {
//        builder?.markAsBlocking(TestClass::class.java, "runAsBlocking", "(V)I")
//            ?.disallowBlockingCallsInside("reactor.core.publisher.Mono", "fromCallable")
//    }
//}