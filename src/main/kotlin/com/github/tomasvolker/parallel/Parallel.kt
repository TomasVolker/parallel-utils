package com.github.tomasvolker.parallel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Provides a CoroutineScope where child coroutines run in Parallel
 */
fun <T> parallelContext(
    block: suspend CoroutineScope.()->T
): T = runBlocking(Dispatchers.Default, block)

/**
 * Performs a map operation on the receiver in parallel where each coroutine processes [chunkSize] elements
 *
 * @param chunkSize The largest amount of elements each coroutine will process
 */
inline fun <T, R> Iterable<T>.mapParallel(
    chunkSize: Int,
    crossinline transform: (T)->R
): List<R> = parallelContext {
    chunked(chunkSize)
        .map { chunk ->
            async { chunk.map(transform) }
        }
        .flatMap { it.await() }
}

/**
 * Performs a reduce operation on the receiver in parallel where each coroutine processes [chunkSize] elements
 *
 * The operation to perform **must** be associative.
 *
 * @param chunkSize The largest amount of elements each coroutine will process
 */
inline fun <T> List<T>.reduceParallel(
    chunkSize: Int,
    crossinline operation: (T, T) -> T
): T = parallelContext {
    chunked(chunkSize)
        .map { chunk ->
            async { chunk.reduce(operation) }
        }
        .map { it.await() }
        .reduce(operation)
}
