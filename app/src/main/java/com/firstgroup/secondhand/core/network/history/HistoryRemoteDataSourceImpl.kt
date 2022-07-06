package com.firstgroup.secondhand.core.network.history

import com.firstgroup.secondhand.core.network.history.model.HistoryDto
import com.firstgroup.secondhand.core.network.history.retrofit.HistoryService
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val service: HistoryService
) : HistoryRemoteDataSource {

    override suspend fun getHistory(): List<HistoryDto> = service.getHistory()

    override suspend fun getHistoryById(id: Int): HistoryDto = service.getHistoryById(id)

}