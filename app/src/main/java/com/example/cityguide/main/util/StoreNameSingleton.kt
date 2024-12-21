package com.example.cityguide.main.util

class StoreNameSingleton private constructor() {
    companion object {
        private var storeName: String = "canerture"

        fun getStoreName(): String = storeName
    }
}
