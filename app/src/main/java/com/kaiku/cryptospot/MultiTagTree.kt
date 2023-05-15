package com.kaiku.cryptospot

import timber.log.Timber

class MultiTagTree :Timber.DebugTree(){

    override fun createStackElementTag(element: StackTraceElement): String? {
        return "(${element.fileName}:${element.lineNumber})"
//        return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }


}