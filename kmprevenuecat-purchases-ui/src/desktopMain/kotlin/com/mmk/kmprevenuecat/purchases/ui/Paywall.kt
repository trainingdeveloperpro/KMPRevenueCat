package com.mmk.kmprevenuecat.purchases.ui

import androidx.compose.runtime.Composable



@Composable
public actual fun Paywall(
    shouldDisplayDismissButton: Boolean,
    onDismiss: () -> Unit,
    listener: PaywallListener?
) {
    println("Paywall is called")
}