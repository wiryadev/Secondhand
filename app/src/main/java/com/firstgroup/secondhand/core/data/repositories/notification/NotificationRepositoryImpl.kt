package com.firstgroup.secondhand.core.data.repositories.notification

import com.firstgroup.secondhand.core.model.Notification
import com.firstgroup.secondhand.core.model.UpdateNotification
import com.firstgroup.secondhand.core.network.notification.NotificationRemoteDataSource
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remoteDataSource: NotificationRemoteDataSource
) : NotificationRepository {

    override suspend fun getNotifications(): List<Notification> =
        remoteDataSource.getNotifications().map {
            it.mapToDomainModel()
        }

    override suspend fun getNotificationById(id: Int): Notification =
        remoteDataSource.getNotificationById(id).mapToDomainModel()

    override suspend fun updateNotification(id: Int): UpdateNotification =
        remoteDataSource.updateNotification(id).mapToDomainModel()

}