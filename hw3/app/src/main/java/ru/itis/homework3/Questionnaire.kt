package ru.itis.homework3

data class Questionnaire(
    var question: String,
    var answer1: String,
    var answer2: String,
    var answer3: String,
    var answer4: String,
    var answer5: String,
    var chosenNumber: Int = -1
)
