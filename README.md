# The Purpose
- Learn the modern Android platform, including architecture and jetpack or other you should to learn in Android
- Ready to get the certificate from [Associate Android Developer Certification](https://grow.google/certificates/android-developer/#?modal_active=none)

# How?
- Create sample codes derived from Google Official Training
- Add some boilerplate code for practicing and examination

# Android Components
- Navigation
- ViewModel
- Coroutine 
- LiveData & FlowData
- WorkManager

## Unit 1: Kotlin basics

## Unit 2: Layouts

## Unit 3: Navigation
- Kotlin Collection, Map, Set, List etc, and how use the high-level function
## Unit 4: Connect to the internet
- [Coroutines] (https://developer.android.com/courses/pathways/android-basics-kotlin-unit-4-pathway-1)
    - Job(Deferred), CoroutineScope, Dispatcher, suspend
    - A Deferred (also called a Promise or Future in other languages) guarantees that a value will be returned to this object at a later time
    - runBlocking(), which as the name implies, starts a new coroutine and blocks the current thread until completion
    - Moshi, Retrofit

## Unit 5: Data persistence
- [SQL basics]([https://developer.android.com/courses/android-basics-kotlin/unit-6](https://developer.android.com/codelabs/basic-android-kotlin-training-sql-basics?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-sql-basics#8))
    - SQL data types, Aggregate functions
## Unit 6: WorkManager
- [android-basics-kotlin: unit-6](https://developer.android.com/courses/android-basics-kotlin/unit-6)
    - WorkManager, OneTimeWorkRequest, WorkRequests, WorkInfo
- [Architecture Component -> Data Layer Libraries -> WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager?gclid=CjwKCAjwyryUBhBSEiwAGN5OCBW-ril0KwCwnSiLJT0AUTHqHLUOpZE3nbCN5JeBzvTyFVbVQlddrRoCpmsQAvD_BwE&gclsrc=aw.ds)
    - Persistent work
    - Work is persistent when it remains scheduled through app restarts and system reboots
    - For background processing.
    - Versus Coroutines, All asynchronous work that doesn't need to be persistent.
