package com.wafflestudio.account

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
                override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {

                    if (request.url.scheme == "intent") {
                        try {
                            // Intent 생성
                            val intent =
                                Intent.parseUri(request.url.toString(), Intent.URI_INTENT_SCHEME)

                            // 실행 가능한 앱이 있으면 앱 실행
                            if (intent.resolveActivity(packageManager) != null) {
                                startActivity(intent)
                                Log.d(TAG, "ACTIVITY: ${intent.`package`}")
                                return true
                            }

                            // Fallback URL이 있으면 현재 웹뷰에 로딩
                            val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                            if (fallbackUrl != null) {
                                view.loadUrl(fallbackUrl)
                                Log.d(TAG, "FALLBACK: $fallbackUrl")
                                return true
                            }

                            Log.e(TAG, "Could not parse anythings")

                        } catch (e: URISyntaxException) {
                            Log.e(TAG, "Invalid intent request", e)
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
                // 구글한테 구라치기
                userAgentString = USER_AGENT

                setSupportMultipleWindows(true)
                domStorageEnabled = true
            }
        }
        webView.loadUrl("https://sso.wafflestudio.com/");
    }
}
