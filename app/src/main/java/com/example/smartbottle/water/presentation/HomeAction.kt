package com.example.smartbottle.water.presentation

sealed interface HomeAction {
    data object ChangeWater :HomeAction
}