# Why choose the "Coil" to be the Image Loader library?
According to [Top 10: Best Android Image Loading and Caching Libraries](https://ourcodeworld.com/articles/read/929/top-10-best-android-image-loading-and-caching-libraries) , there are common libraries such as Picasso & Fresso & Glide and so on. The "Coil" attract my sights because the below description:

## An image loading library for Android backed by Kotlin Coroutines.
[Coil](https://coil-kt.github.io/coil/) is an acronym for: Coroutine Image Loader.
The coroutine is the asynchrous operation for modern android design.

For deep diving to learn and understand coroutine, I should pick up this one

* **Fast**: Coil performs a number of optimizations including memory and disk caching, downsampling the image in memory, re-using Bitmaps, automatically pausing/cancelling requests, and more.
* **Lightweight**: Coil adds ~1500 methods to your APK (for apps that already use OkHttp and Coroutines), which is comparable to Picasso and significantly less than Glide and Fresco.
* **Easy to use**: Coil's API leverages Kotlin's language features for simplicity and minimal boilerplate.
* **Modern**: Coil is Kotlin-first and uses modern libraries including Coroutines, OkHttp, Okio, and AndroidX Lifecycles.