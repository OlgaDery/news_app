package com.bonial.newsapp.model

data class ObservableMessage(var alreadyReceived: Boolean = false, val appendingData: Boolean = false) {
}