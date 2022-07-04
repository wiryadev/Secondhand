package com.firstgroup.secondhand.core.network.notification.retrofit

import com.firstgroup.secondhand.core.network.notification.model.NotificationDto
import com.firstgroup.secondhand.core.network.notification.model.UpdateNotificationDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationService {

    @GET("notification")
    suspend fun getNotifications(): List<NotificationDto>

    @GET("notification/{id}")
    suspend fun getNotificationById(
        @Path("id") id: Int,
    ): NotificationDto

    @PATCH("notification/{id}")
    suspend fun updateNotification(
        @Path("id") id: Int,
    ): UpdateNotificationDto

}