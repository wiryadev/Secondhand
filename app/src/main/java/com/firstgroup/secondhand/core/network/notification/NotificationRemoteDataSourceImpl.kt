package com.firstgroup.secondhand.core.network.notification

import com.firstgroup.secondhand.core.network.notification.model.NotificationDto
import com.firstgroup.secondhand.core.network.notification.model.UpdateNotificationDto
import com.firstgroup.secondhand.core.network.notification.retrofit.NotificationService
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val service: NotificationService,
) : NotificationRemoteDataSource {

    override suspend fun getNotifications(): List<NotificationDto> = service.getNotifications()

    override suspend fun getNotificationById(id: Int): NotificationDto =
        service.getNotificationById(id)

    override suspend fun updateNotification(id: Int): UpdateNotificationDto =
        service.updateNotification(id)


}