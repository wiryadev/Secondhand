package com.firstgroup.secondhand.domain

import com.firstgroup.secondhand.core.common.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class UseCase<in P, out R> constructor(
    private val coroutineDispatcher: CoroutineDispatcher
) {

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    abstract suspend fun execute(param: P): R

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param param the input parameters to run the use case with
     */
    suspend operator fun invoke(param: P): Result<R> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute(param).let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}

/**
 * UseCase version that exposes [Flow]
 */
abstract class FlowUseCase<in P, out R> constructor(
    private val coroutineDispatcher: CoroutineDispatcher
) {
    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    abstract fun execute(param: P): Flow<R>

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param param the input parameters to run the use case with
     */
    operator fun invoke(param: P): Flow<Result<R>> {
        return execute(param)
            .map<R, Result<R>> {
                Result.Success(it)
            }
            .catch {
                emit(Result.Error(it))
            }
    }
}