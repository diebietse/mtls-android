# mTLS Android Example App

![build](https://github.com/diebietse/mtls-android/workflows/build/badge.svg)

This is an example app that demonstrates how to do [Mutual TLS authentication (mTLS)][mtls] when
using a [WebView][web-view] as well as when using [OkHttp][ok-http]

![home](https://raw.githubusercontent.com/diebietse/mtls-android/master/art/home.png)
![web-view](https://raw.githubusercontent.com/diebietse/mtls-android/master/art/web-view.png)
![ok-http](https://raw.githubusercontent.com/diebietse/mtls-android/master/art/ok-http.png)

## Settings

In `app/build.gradle` you can set:

`SKIP_TLS_VERIFY` to `true` if you are testing against a dev site that
does not have a trusted certificate

`WEB_VIEW_URL` and `OK_HTTP_URL` to your own url if you are testing against your own deploy

[mtls]: https://en.wikipedia.org/wiki/Mutual_authentication
[web-view]: https://developer.android.com/reference/android/webkit/WebView
[ok-http]: https://square.github.io/okhttp/