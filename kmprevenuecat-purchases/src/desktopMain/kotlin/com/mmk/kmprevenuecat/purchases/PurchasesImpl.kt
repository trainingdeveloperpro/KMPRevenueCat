package com.mmk.kmprevenuecat.purchases

import com.mmk.kmprevenuecat.purchases.data.CustomerInfo
import com.mmk.kmprevenuecat.purchases.data.LogInResult



internal class PurchasesImpl : Purchases {
    override var logLevel: LogLevel = LogLevel.DEBUG

    override fun configure(apiKey: String, appUserId: String?) {

    }

    override fun login(appUserId: String, onResult: (Result<LogInResult>) -> Unit) {

    }


    override fun logOut(onResult: (Result<CustomerInfo>) -> Unit) {
    }

    override fun getCustomerInfo(
        fetchPolicy: CacheFetchPolicy,
        onResult: (Result<CustomerInfo>) -> Unit
    ) {

    }

    override fun setAttributes(attributes: Map<String, String?>) {
        val map = attributes.map { (key, value) ->
            key as Any? to value as Any?
        }.toMap()

    }

    override fun setFirebaseAppInstanceID(firebaseAppInstanceID: String) {

    }

    override fun collectDeviceIdentifiers() {

    }

    override fun enableAdServicesAttributionTokenCollection() {

    }

    override fun syncPurchases(onResult: (Result<CustomerInfo>) -> Unit) {

    }

}