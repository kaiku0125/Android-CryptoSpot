package com.kaiku.cryptospot.domain.use_case

import com.kaiku.cryptospot.common.Resource
import com.kaiku.cryptospot.data.remote.dto.crypto_list.toPoint
import com.kaiku.cryptospot.domain.model.CryptoListingData
import com.kaiku.cryptospot.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class GetCryptoListUseCase (
    private val mainRepository: MainRepository
) {
//    operator fun invoke() : Flow<Resource<List<String>>> = flow {
//        try {
//            Timber.e("invoke start")
//            emit(Resource.Loading<List<String>>())
//            val cryptoList = mainRepository.requestCryptoList()
//            emit(Resource.Success<List<String>>(cryptoList))
//            Timber.e("invoke end")
//        }catch(e: HttpException) {
//            emit(Resource.Error<List<String>>(message = e.localizedMessage ?: "An unexpected error occurred"))
//        } catch(e: IOException) {
//            emit(Resource.Error<List<String>>(message = "Couldn't reach server. Check your internet connection."))
//        }
//    }

    operator fun invoke(): Flow<Resource<List<CryptoListingData>>> = flow {
        try {
            Timber.e("invoke start")
            emit(Resource.Loading<List<CryptoListingData>>())
            val mResponse = mainRepository.requestCryptoList2()
            Timber.e("onResponse: body ➔ $mResponse")

            val mCryptoList = mResponse.data.map { it.toPoint() }
            val mStatus = mResponse.status.toPoint()
            Timber.e("mCryptoList -> $mCryptoList , size -> ${mCryptoList.size}")
            Timber.e("msg : errorCode -> ${mStatus.errorCode}, errorMessage -> ${mStatus.errorMessage}")

            emit(Resource.Success<List<CryptoListingData>>(mCryptoList))
            Timber.e("invoke end")
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<CryptoListingData>>(
                    message = e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<CryptoListingData>>(message = "Couldn't reach server. Check your internet connection."))
        }
    }

}