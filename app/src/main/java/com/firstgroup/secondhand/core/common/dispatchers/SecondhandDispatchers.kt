package com.firstgroup.secondhand.core.common.dispatchers

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppDispatcher(val dispatcher: SecondhandDispatchers)

enum class SecondhandDispatchers {
    Main, Default, IO,
}