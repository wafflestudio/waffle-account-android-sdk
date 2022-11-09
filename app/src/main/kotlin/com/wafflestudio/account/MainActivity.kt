package com.wafflestudio.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {
    //    private val webView: WebView = findViewById(R.id.webview)
    private lateinit var webView: WebView
    val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview)


        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, webResourceRequest: WebResourceRequest): Boolean {
                    if (webResourceRequest.url != null && webResourceRequest.url.toString().startsWith("intent://")) {
                        try {
                            val intent = Intent.parseUri(webResourceRequest.url.toString(), Intent.URI_INTENT_SCHEME)
                            val existPackage =
                                packageManager.getLaunchIntentForPackage(intent.getPackage()!!)
                            if (existPackage != null) {
                                startActivity(intent)
                            } else {
                                val marketIntent = Intent(Intent.ACTION_VIEW)
                                marketIntent.data =
                                    Uri.parse("market://details?id=" + intent.getPackage()!!)
                                startActivity(marketIntent)
                            }
                            return true
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } else if (webResourceRequest.url != null && webResourceRequest.url.toString().startsWith("market://")) {
                        try {
                            val intent = Intent.parseUri(webResourceRequest.url.toString(), Intent.URI_INTENT_SCHEME)
                            if (intent != null) {
                                startActivity(intent)
                            }
                            return true
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }

                    }
                    return false
                }
            }
            webChromeClient = WebChromeClient()
            setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength -> }

            settings.apply {
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(false)
                builtInZoomControls = false
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                userAgentString = USER_AGENT

                setSupportMultipleWindows(true)
                domStorageEnabled = true
            }
        }
        webView.loadUrl("https://sso.wafflestudio.com/");
    }
}