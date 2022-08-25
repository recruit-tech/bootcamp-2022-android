package com.example.mygithubapp.manager.abtest

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * ABTestManager
 *
 * [Usage]
 * AbTestManager.getInstance(context)["ABIDENTIFIER"]で呼び出せる。
 * AbTest.ktに新しいEnumの値を追加したあと、assets/AbTestData.jsonにjson形式でAB識別子(反映させたいパターン)を入力する。
 */
class AbTestManager {
    companion object {
        fun getInstance(
            context: Context
        ): Map<String, AbPattern> {
            return if (abTestIdentifier != null) {
                abTestIdentifierToMap(abTestIdentifier!!)
            } else {
                lateinit var jsonString: String
                try {
                    jsonString = context.assets.open("AbTestData.json")
                        .bufferedReader()
                        .use { it.readText() }
                } catch (t: Throwable) {
                    Log.ERROR
                }
                val types = object : TypeToken<Map<String, String>>() {}.type
                abTestIdentifier = Gson().fromJson(jsonString, types)
                abTestIdentifierToMap(abTestIdentifier!!)
            }
        }

        private var abTestIdentifier: Map<String, String>? = null

        private fun abTestIdentifierToMap(map: Map<String, String>): Map<String, AbPattern> {
            return buildMap {
                AbTest.values().forEach {
                    put(it.identifier, it.map[map[it.identifier]] ?: AbPattern.DEFAULT)
                }
            }
        }
    }
}