package com.kaiku.composecomponent.component.image

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.request.ImageRequest
import com.kaiku.composecomponent.component.loading.CircularLoadingScene
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.pocketAspectRatio
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.OnComposeLifecycleEvent
import com.kaiku.composecomponent.utils.isPreviewMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

/**
 * @param url 圖片網址 (預設url為空的話，就沒有PocketAsyncImage的效果)
 * @param ratio 圖片寬高比
 * @param errorDrawable 抓取錯誤時的placeholder
 * @param shape 圖片形狀
 * @param contentScale 圖片剪裁模式
 * @param bg 背景顏色
 * @param border 外框顏色
 * @param description 圖片描述
 */
data class PocketAsyncImageConfig(
    val url: String = "",
    val ratio: Float? = null,
    val errorDrawable: Int? = null,
    val shape: Shape = RoundedCornerShape(8.dp),
    val contentScale: ContentScale = ContentScale.Fit,
    val bg: Color = Color.Transparent,
    val border: BorderStroke = BorderStroke(0.dp, Color.Transparent),
    val alignment: Alignment = Alignment.Center,
    val needReload: Boolean = true,
    val maxReloadTimes: Int = 3,
    val description: String = "box_async_image"
)

/**
 * coil AsyncImage with click effect
 * @param imageLoader 自定義 image loader
 * @param imageConfig view 設定
 * @param clickConfig clickable 設定
 * @param loadingScene 載入UI
 * @param onClick export 點擊事件
 */
@Composable
fun PocketAsyncImage(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader? = null,
    imageConfig: PocketAsyncImageConfig = PocketAsyncImageConfig(),
    clickConfig: ClickableConfig? = null,
    loadingScene: @Composable () -> Unit = {
        PocketAsyncImageLoadingScene(config = imageConfig)
    },
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    var reloadKey by remember { mutableLongStateOf(0L) }
    var reloadTimes by remember { mutableIntStateOf(0) }
    val dynamicImageLoader = if (isPreviewMode()) {
        LocalContext.current.imageLoader
    } else {
        imageLoader ?: get()
    }

    OnComposeLifecycleEvent { _, event ->
        when(event) {
            Lifecycle.Event.ON_PAUSE -> job?.cancel()
            else -> Unit
        }
    }

    val request = remember(reloadKey, imageConfig.url) {
        ImageRequest.Builder(context)
            .decoderFactory(SvgDecoder.Factory())
            .data(imageConfig.url)
            .diskCacheKey(imageConfig.url)
            .memoryCacheKey(imageConfig.url)
            .crossfade(true)
            .setParameter("retry_hash", reloadKey)
            .build()
    }

    if (imageConfig.url.isNotEmpty()) {
        SubcomposeAsyncImage(
            modifier = modifier
                .pocketAspectRatio(ratio = imageConfig.ratio)
                .clipByShape(
                    shape = imageConfig.shape,
                    backgroundColor = imageConfig.bg,
                    border = imageConfig.border
                )
                .clickableEffectConfig(
                    config = clickConfig ?: ClickableConfig(
                        needRipple = onClick != null,
                        needSound = onClick != null
                    ),
                    onClick = {
                        // 避免url為空時還觸發點擊事件
                        if (imageConfig.url.isNotEmpty()) {
                            onClick?.invoke()
                        }
                    }
                ),
            model = request,
            imageLoader = dynamicImageLoader,
            loading = { loadingScene.invoke() },
            error = {
                imageConfig.errorDrawable?.let { mErrorDrawable ->
                    Image(
                        modifier = Modifier
                            .pocketAspectRatio(ratio = imageConfig.ratio)
                            .clip(shape = imageConfig.shape),
                        painter = painterResource(id = mErrorDrawable),
                        contentDescription = null
                    )
                }
            },
            onError = {
                if (reloadTimes < imageConfig.maxReloadTimes && imageConfig.needReload) {
                    job?.cancel()
                    job = scope.launch(Dispatchers.IO) {
                        delay(2000)
                        ensureActive()
                        reloadTimes++
                        reloadKey = System.currentTimeMillis()
                    }
                }
            },
            alignment = imageConfig.alignment,
            contentScale = imageConfig.contentScale,
            contentDescription = imageConfig.description
        )
    } else {
        Box(modifier = modifier)
    }
}


@Composable
private fun PocketAsyncImageLoadingScene(config: PocketAsyncImageConfig) {
    if (isPreviewMode()) {
        config.errorDrawable?.let { mErrorDrawable ->
            Image(
                modifier = Modifier
                    .pocketAspectRatio(ratio = config.ratio)
                    .clip(shape = config.shape),
                painter = painterResource(id = mErrorDrawable),
                contentDescription = null
            )
        }
    } else {
        CircularLoadingScene()
//        PocketCircularProgressIndicator(
//            modifier = Modifier.fillMaxSize(),
//            indicatorModifier = Modifier.size(18.sdp())
//        )
    }
}
