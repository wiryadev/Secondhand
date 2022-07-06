package com.firstgroup.secondhand.core.network.history

import com.firstgroup.secondhand.core.network.history.model.HistoryDto

interface HistoryRemoteDataSource {

    suspend fun getHistory(): List<HistoryDto>

    suspend fun getHistoryById(id: Int): HistoryDto

}