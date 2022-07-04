package com.firstgroup.secondhand.core.network.notification

import com.firstgroup.secondhand.core.network.notification.model.NotificationDto
import com.firstgroup.secondhand.core.network.notification.model.UpdateNotificationDto

interface NotificationRemoteDataSource {

    suspend fun getNotifications(): List<NotificationDto>

    suspend fun getNotificationById(id: Int): NotificationDto

    suspend fun updateNotification(id: Int): UpdateNotificationDto

}