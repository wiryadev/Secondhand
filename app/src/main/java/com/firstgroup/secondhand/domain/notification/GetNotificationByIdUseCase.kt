package com.firstgroup.secondhand.domain.notification

import com.firstgroup.secondhand.core.common.dispatchers.AppDispatcher
import com.firstgroup.secondhand.core.common.dispatchers.SecondhandDispatchers.IO
import com.firstgroup.secondhand.core.data.repositories.notification.NotificationRepository
import com.firstgroup.secondhand.core.model.Notification
import com.firstgroup.secondhand.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNotificationByIdUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @AppDispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : UseCase<Int, Notification>(ioDispatcher) {

    override suspend fun execute(param: Int): Notification {
        return notificationRepository.getNotificationById(param)
    }

}