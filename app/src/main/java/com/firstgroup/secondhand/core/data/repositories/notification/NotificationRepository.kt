package com.firstgroup.secondhand.core.data.repositories.notification

import com.firstgroup.secondhand.core.model.Notification
import com.firstgroup.secondhand.core.model.UpdateNotification

interface NotificationRepository {

    suspend fun getNotifications(): List<Notification>

    suspend fun getNotificationById(id: Int): Notification

    suspend fun updateNotification(id: Int): UpdateNotification

}