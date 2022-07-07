package com.firstgroup.secondhand.domain.history

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.history.HistoryRepository
import com.firstgroup.secondhand.core.model.History
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetHistoriesUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Any, List<History>>(ioDispatcher) {

    override suspend fun execute(param: Any): List<History> {
        return historyRepository.getHistory()
    }

}