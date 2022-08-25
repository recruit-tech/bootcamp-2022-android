package com.example.mygithubapp.manager.abtest

enum class AbTest(var identifier: String, var map: Map<String, AbPattern>) {
    FIRST_AB_TEST_01("FIRST_AB_TEST_01", mapOf("01_A" to AbPattern.DEFAULT, "01_B" to AbPattern.TEST));

}