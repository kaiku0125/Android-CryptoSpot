package com.kaiku.cryptospot.domain.use_case

import com.kaiku.cryptospot.common.Resource
import com.kaiku.cryptospot.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetCryptoListUseCase @Inject constructor(
    private val mainRepository: MainRepository
){
    operator fun invoke() : Flow<Resource<List<String>>> = flow {
        try {
            Timber.e("invoke start")
            emit(Resource.Loading<List<String>>())
            val cryptoList = mainRepository.requestCryptoList()
            emit(Resource.Success<List<String>>(cryptoList))
            Timber.e("invoke end")
        }catch(e: HttpException) {
            emit(Resource.Error<List<String>>(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<List<String>>(message = "Couldn't reach server. Check your internet connection."))
        }
    }

}