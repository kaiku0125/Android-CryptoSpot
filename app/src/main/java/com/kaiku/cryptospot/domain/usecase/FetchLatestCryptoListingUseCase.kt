package com.kaiku.cryptospot.domain.usecase

import com.kaiku.cryptospot.data.remote.dto.crypto_list.toEntity
import com.kaiku.cryptospot.domain.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class FetchLatestCryptoListingUseCase(
    private val mainRepository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke() {
        if (mainRepository.isCryptoListCacheExpired()) {
            mainRepository.requestCryptoList(
                start = 1,
                limit = MAX_LIMIT
            ).fold(
                onSuccess = { response ->
                    val isSuccess = response.data.isNotEmpty() && response.data.size == MAX_LIMIT

                    if (isSuccess) {
                        withContext(dispatcher) {
                            val entities = response.data
                                .map { it.toEntity() }
                                .also {
                                    Timber.tag("wtf").d("成功獲取資料: size:${response.data.size}")
                                }
                            mainRepository.updateAllCryptoList(entities = entities)
                        }
                    } else {
                        Timber.tag("wtf").e("拉取幣種資料失敗: data為空")
                    }
                },
                onFailure = {
                    Timber.tag("wtf").e("拉取幣種資料失敗: ${it.message}")
                }
            )
        } else {
            Timber.tag("wtf").d("cache尚未過期")
        }
    }

    companion object {
        private const val MAX_LIMIT = 5000
    }
}