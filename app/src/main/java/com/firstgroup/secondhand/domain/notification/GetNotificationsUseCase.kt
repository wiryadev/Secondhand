package com.firstgroup.secondhand.domain.notification

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.notification.NotificationRepository
import com.firstgroup.secondhand.core.model.Notification
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Any, List<Notification>>(ioDispatcher) {

    override suspend fun execute(param: Any): List<Notification> {
        return notificationRepository.getNotifications()
    }

}