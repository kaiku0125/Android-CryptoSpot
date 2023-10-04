package com.kaiku.cryptospot.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kaiku.cryptospot.extension.appendArguments
import com.kaiku.cryptospot.presentation.flash.FlashUIScreenTag
import timber.log.Timber

abstract class Destination(
    path: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    val route: String = if (arguments.isEmpty()) path else path.appendArguments(arguments)
}

object HomeDestination : Destination("home")



object LoginDestination : Destination("login")

object FindCryptoDestination : Destination("find_crypto")

object PocketHomeworkDestination : Destination("pocket_hw")

object TestDestination : Destination(path = "test")

object FlashUIDestination : Destination(
    path = "flash",
    arguments = listOf(
        navArgument(FlashUIScreenTag.CHANNEL_ID){
            type = NavType.StringType
        },
        navArgument(FlashUIScreenTag.SCORE){
            type = NavType.IntType
        },
        navArgument(FlashUIScreenTag.IS_TESTING){
            type = NavType.BoolType
        }
    )
){

    operator fun invoke(
        invokeString: String,
        invokeInt :Int,
        invokeBoolean :Boolean
    ): String {
        arguments.forEach{
            Timber.e("arguments : ${it.name}")

        }
        val result = route.replace("{${arguments.first().name}}", invokeString)
            .replace("{${arguments[1].name}}", "$invokeInt")
            .replace("{${arguments.last().name}}", "$invokeBoolean")
        Timber.e("testDestination invoke : $result")
        return result
    }

}





