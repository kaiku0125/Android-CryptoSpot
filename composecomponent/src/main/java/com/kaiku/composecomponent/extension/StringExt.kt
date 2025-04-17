package com.kaiku.composecomponent.extension

import androidx.navigation.NamedNavArgument
import com.kaiku.composecomponent.GlobalConstant.PK_EMPTY_DASH
import timber.log.Timber
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @param navArguments Compose導航argument清單
 */
fun String.appendArguments(
    navArguments: List<NamedNavArgument>
): String {
    val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
        .orEmpty()
    val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
        .orEmpty()
    return "$this$mandatoryArguments$optionalArguments"
}

/**
 * 格式化數字格式，預設為RoundingMode.HALF_EVEN
 *
 * @param isDecimalPointFillZero 小數是否補0，預設為false。
 * @param decimalPointFillZeroCount 補0的數量，當[isDecimalPointFillZero]為true有效，預設為0。
 * @param isWholeNumberFillZero 整數是否補0，預設為false。
 * @param wholeNumberFillZeroCount 補0的數量，當[isWholeNumberFillZero]為true有效，預設為0。
 * @param roundingMode 進位模式，預設使用[RoundingMode.HALF_EVEN]
 * @param negativePrefix 數字為負數的前綴字，預設為`-`。
 * @param positivePrefix 數字為正數的前綴字，預設為空字串。
 * @param isIgnoreZeroPrefix 是否忽略0的前綴字，當[positivePrefix]有設定時，例如：+0，-0。
 * @param invalidText 不合法的預設文字，預設為空字串。
 * @param isShowThousandsSign 是否需要千位符號，即 1,000，預設是false。
 */
internal fun Number?.toNumberFormat(
    isDecimalPointFillZero: Boolean = false,
    decimalPointFillZeroCount: Int = 0,
    isWholeNumberFillZero: Boolean = false,
    wholeNumberFillZeroCount: Int = 0,
    roundingMode: RoundingMode = RoundingMode.HALF_EVEN,
    negativePrefix: String = "-",
    positivePrefix: String = "",
    isIgnoreZeroPrefix: Boolean = false,
    invalidText: String = "-",
    isShowThousandsSign: Boolean = false
): String {
    if (this == null) {
        return invalidText
    }
    val decimalFormat = DecimalFormat()
    val wholeNumberPattern = buildString {
        if (isWholeNumberFillZero) {
            append("#".repeat(4 - wholeNumberFillZeroCount))
            append("0".repeat(wholeNumberFillZeroCount))
        } else {
            append("####")
        }
        if (isShowThousandsSign) {
            insert(1, ",")
        }
    }

    val decimalPointPattern = buildString {
        if (decimalPointFillZeroCount > 0) {
            append(".")
            if (isDecimalPointFillZero) {
                append("0".repeat(decimalPointFillZeroCount))
            } else {
                append("##")
            }
        }
    }
    val pattern = wholeNumberPattern + decimalPointPattern
    decimalFormat.applyPattern(pattern)
    decimalFormat.positivePrefix = positivePrefix
    decimalFormat.negativePrefix = negativePrefix
    decimalFormat.roundingMode = roundingMode

    val formatString = decimalFormat.format(this)
    return if (isIgnoreZeroPrefix) {
        formatString.replace("^\\+(?=0(\\.0*)?$)".toRegex(), "")
    } else {
        formatString
    }
}

internal fun Int.toStepMissionText(): String {
    return when {
        this >= 100_000_000 -> {
            val billions = this / 100_000_000
            val remainder = (this % 100_000_000) / 10_000
            if (remainder > 0) {
                "$billions.${remainder.toString().trimEnd('0')}億"
            } else {
                "${billions}億"
            }
        }

        this >= 10_000 -> {
            val tenThousands = this / 10_000
            val remainder = (this % 10_000)
            if (remainder > 0) {
                "$tenThousands.${remainder.toString().trimEnd('0')}萬"
            } else {
                "${tenThousands}萬"
            }
        }

        else -> {
            this.toString()
        }
    }
}

internal fun String.replaceArg(vararg arg: String): String {
    var result = this
    arg.forEachIndexed { index, argValue ->
        result = this.replace("%${index + 1}s", argValue)
    }
    return result
}

internal fun String.spiltByReplacement(separator: String): List<String> {
    return this.split(separator, limit = 2).let { parts ->
        if (parts.size == 2) listOf(parts[0], separator, parts[1])
        else listOf(this)
    }
}

fun String.splitByReplacements(replacements: List<String>): List<String> {
    var temp = mutableListOf<String>()
    replacements.forEach {
        if(temp.isEmpty()) {
            temp = this.spiltByReplacement(it).toMutableList()
        } else {
            val lastSeparate = temp.last().spiltByReplacement(it).toMutableList()
            temp.removeAt(temp.lastIndex)
            lastSeparate.forEach {new ->
                temp.add(new)
            }
        }
    }
    return temp
}

fun String?.orNA() = this ?: PK_EMPTY_DASH

fun String?.debug(msg: String) {
    if (!this.isNullOrEmpty()) {
        Timber.tag(this).d(msg)
    }
}