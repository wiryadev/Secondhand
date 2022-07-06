package com.firstgroup.secondhand.core.data.repositories.history

import com.firstgroup.secondhand.core.model.History
import com.firstgroup.secondhand.core.network.history.HistoryRemoteDataSource
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: HistoryRemoteDataSource
) : HistoryRepository {

    override suspend fun getHistory(): List<History> = remoteDataSource.getHistory()
        .map {
            it.mapToDomainModel()
        }

    override suspend fun getHistoryById(id: Int): History =
        remoteDataSource.getHistoryById(id).mapToDomainModel()

}