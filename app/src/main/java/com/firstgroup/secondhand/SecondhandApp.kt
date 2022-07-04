package com.firstgroup.secondhand

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.firstgroup.secondhand.core.network.utils.CoilResponseHeaderInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class SecondhandApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addNetworkInterceptor(
                        CoilResponseHeaderInterceptor(
                            name = "Cache-Control",
                            value = "max-age=31536000,public",
                        )
                    )
                    .build()
            }
            .build()
    }

}