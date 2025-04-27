package com.kaiku.cryptospot.domain.usecase

import com.kaiku.cryptospot.data.repository.UserHoldingRepository
import com.kaiku.cryptospot.domain.model.Holding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class AddHoldingUseCase(
    private val userHoldingRepository: UserHoldingRepository
) {

    suspend operator fun invoke(holding: Holding): Result<Holding?> {
        return withContext(Dispatchers.IO) {
            runCatching {
                userHoldingRepository.add(holding = holding.toEntity())
                userHoldingRepository.getHoldingBySymbol(symbol = holding.symbol)?.toUsage()
            }
        }
    }
}