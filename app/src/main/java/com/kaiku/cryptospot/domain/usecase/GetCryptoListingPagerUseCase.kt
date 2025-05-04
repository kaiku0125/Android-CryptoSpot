package com.kaiku.cryptospot.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toData
import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCryptoListingPagerUseCase(
    private val mainRepository: MainRepository
) {

    operator fun invoke(query: String): Flow<PagingData<CryptoListingData>> {
        return mainRepository.getPagerByQuery(query)
            .flow
            .map { pagingData ->
                pagingData.map { it.toData() }
            }
    }
}