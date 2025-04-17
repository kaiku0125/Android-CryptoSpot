package com.kaiku.composecomponent.model

import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import java.io.File

class PocketImageLoader(
    private val context: Context,
    private val isDebug: Boolean = false,
    private val diskDirectory: File = context.cacheDir
) : ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(context).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(diskDirectory)
                    .build()
            }
            .logger(
                if (isDebug) {
                    DebugLogger()
                } else {
                    null
                }
            )
            .crossfade(true)
            .build()
    }

    companion object {
        fun init(
            context: Context,
            isDebugMode: Boolean,
            diskDirectory: File = context.cacheDir
        ) : ImageLoader {
            return PocketImageLoader(
                context = context,
                isDebug = isDebugMode,
                diskDirectory = diskDirectory
            ).newImageLoader()
        }
    }
}