package com.firstgroup.secondhand.core.data.repositories.history

import com.firstgroup.secondhand.core.model.History

interface HistoryRepository {

    suspend fun getHistory(): List<History>

    suspend fun getHistoryById(id: Int): History

}