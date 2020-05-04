<img width="100" height="100" src="art/app_icon.png?raw=true">

<p align="center">
  <img width="285" src="art/overview-light.png?raw=true">
  <img width="285" src="art/countries-light.png?raw=true">
  <img width="285" src="art/country-light.png?raw=true">
</p>

# Covid-19 World Stats

[![GitHub license](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
![Github Followers](https://img.shields.io/github/followers/jeffnyauke?label=Follow&style=social)
![GitHub stars](https://img.shields.io/github/stars/jeffnyauke/Covid19-Notifier-IN?style=social)
![GitHub forks](https://img.shields.io/github/forks/jeffnyauke/Covid19-Notifier-IN?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/jeffnyauke/Covid19-Notifier-IN?style=social)
![Twitter Follow](https://img.shields.io/twitter/follow/pesapocket?label=Follow&style=social)

**Covid-19 World Stats** is an Android App that displays the latest global stats about the COVID-19 spread.

The sample Android application 📱 is built to demonstrate use of *Modern Android development* tools. Dedicated to all Android Developers with ❤ .

***You can Install and test latest Covid-19 World Stats app from below 👇***
[Covid-19 World Stats App](https://appdistribution.firebase.dev/i/fLb85aoy)

## About
- It loads **Total COVID-19 cases worldwide** from [API](https://github.com/NovelCOVID/API).
- It notifies total cases of worldwide COVID-19 cases in after every 1 hours.
- It loads COVID-19 numbers for any specific country.
- It displays the statistics on linear charts.
- It loads COVID related news from the W.H.O. and Google News RSS.
- It is offline capable (Using Cache) 😃.

*It uses `PeriodicWorkManager` which is scheduled at the first run of an app. After that, `Worker` will execute after every one hour of interval and will show notification on Android's system tray.*

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Koin](https://start.insert-koin.io/) - Dependency Injection Framework (Kotlin)
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - The WorkManager API makes it easy to schedule deferrable, asynchronous tasks that are expected to run even if the app exits or device restarts.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android

# Package Structure
    
    dev.jeffnyauke.covid19stats    # Root Package
    .
    ├── api                 # For API Service.
    ├── model               # Model classes
    ├── repository          # Repository to handle data from network using API.
    ├── di                  # Dependency Injection     
    |
    ├── ui                  # Activity/View layer  
    │   └── main            # Main Screen Activity, ViewModel and RecyclerView Adapters.
    |
    ├── utils               # Utility Classes / Kotlin extensions
    └── worker              # Worker class.


### Contributing
Take a look at the [open issues](https://github.com/jeffnyauke/Covid-19-World-Stats/issues) and feel free to pick something up.

### API Sources
This app would not exist without it's data sources.

 - [Tne Novel Covid API](https://github.com/NovelCOVID/API) by [Novel COVID](https://github.com/NovelCOVID)
 - [COVID19 News API](https://github.com/einnar82/covid19-news-api) by [Rannie Ollit](https://github.com/einnar82)

### Credits
Thanks to [Covid19-Notifier-IN](https://github.com/PatilShreyas/Covid19-Notifier-IN) by [Shreyas Patil](https://github.com/PatilShreyas) for inspiration.

### Support
You can support via [paypal](https://www.paypal.com/jeffnyauke)

## Authors

<a href="https://twitter.com/jeffreynyauke" target="_blank">
  <img src="https://avatars1.githubusercontent.com/u/14073448?s=400&u=e21d2306a36644576535f8f2f7ba939aeee148f1&v=4" width="70" align="left">
</a>

**Jeffrey Nyauke**

[![Linkedin](https://img.shields.io/badge/-linkedin-grey?logo=linkedin)](https://www.linkedin.com/in/jeffreynyauke/)
[![Twitter](https://img.shields.io/badge/-twitter-grey?logo=twitter)](https://twitter.com/pesapocket)
[![Medium](https://img.shields.io/badge/-medium-grey?logo=medium)](https://medium.com/@jeffnyauke)

## License
```
MIT License

Copyright 2020 Jeffrey Nyauke

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
