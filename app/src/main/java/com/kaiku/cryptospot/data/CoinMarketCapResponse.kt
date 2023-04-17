package com.kaiku.cryptospot.data

data class CoinMarketCapResponse(
    val data : Map<String, CryptoCurrency>

)

data class CryptoCurrency(
    val quote: Map<String, Quote>
)


data class Quote(
    val price : Double
)
