package com.firstgroup.secondhand.core.network.history.retrofit

import com.firstgroup.secondhand.core.network.history.model.HistoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryService {

    @GET("history")
    suspend fun getHistory(): List<HistoryDto>

    @GET("history/{id}")
    suspend fun getHistoryById(
        @Path("id") id: Int,
    ): HistoryDto

}