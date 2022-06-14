# The Purpose
- Learn the modern Android platform, including architecture and jetpack or other you should to learn in Android
- Ready to get the certificate from [Associate Android Developer Certification](https://grow.google/certificates/android-developer/#?modal_active=none)

# How?
- Create sample codes derived from Google Official Training
- Add some boilerplate code for practicing and examination

# Kotlin
- Java to Kotlin
  - The annotation such as @Nullable, helps the convert to the appropriated declaration.
  - Equality
  - Default arguments, named arguments
  - Destructuring
  - Elvis operator
  - string templates
  - With Kotlin, we prefer using properties rather than functions for such cases.
  - top-level functions
  - Scope functions: let, apply, with, run, also
- Ready to get the certificate from [Add Kotlin to an existing app](https://developer.android.com/kotlin/add-kotlin)
  - How to create Java/Kotlin files
  - Source organization
  - Code conversion and nullability
  - lateinit
  - safe-call (?.) v.s elvis operator (?:)
- Calling Kotlin codes from Java
  - The @JvmStatic annotation tells the Kotlin compiler to generate a static version of the annotated member or method
  - The @JvmName annotation is a powerful tool that influences how Kotlin names classes, properties, and methods in the bytecode it generates for the Java Virtual Machine (JVM). We'll discuss another of its uses later in this codelab.
  - @JvmStatic @Throws(IOException::class)
# Android Components
- Navigation
- ViewModel
- Coroutine
  - runBlocking
  - lifecycleScope.launch
- LiveData & FlowData
- WorkManager
- DataStore

## Unit 1: Kotlin basics

## Unit 2: Layouts

## Unit 3: Navigation
- Kotlin Collection, Map, Set, List etc, and how use the high-level function
- ViewModel, Kotlin Delegate, Get() / Set(), backing property
- MaterialAlertDialogBuilder
## Unit 4: Connect to the internet
- [Coroutines] (https://developer.android.com/courses/pathways/android-basics-kotlin-unit-4-pathway-1)
  - Job(Deferred), CoroutineScope, Dispatcher, suspend
  - A Deferred (also called a Promise or Future in other languages) guarantees that a value will be returned to this object at a later time
  - runBlocking(), which as the name implies, starts a new coroutine and blocks the current thread until completion
  - Moshi, Retrofit

## Unit 5: Data persistence
- [SQL basics]([https://developer.android.com/courses/android-basics-kotlin/unit-6](https://developer.android.com/codelabs/basic-android-kotlin-training-sql-basics?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-kotlin-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-training-sql-basics#8))
  - SQL data types, Aggregate functions
- ROOM/DAO/SQLite db
## Unit 6: WorkManager
- [android-basics-kotlin: unit-6](https://developer.android.com/courses/android-basics-kotlin/unit-6)
  - WorkManager, OneTimeWorkRequest, WorkRequests, WorkInfo
- [Architecture Component -> Data Layer Libraries -> WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager?gclid=CjwKCAjwyryUBhBSEiwAGN5OCBW-ril0KwCwnSiLJT0AUTHqHLUOpZE3nbCN5JeBzvTyFVbVQlddrRoCpmsQAvD_BwE&gclsrc=aw.ds)
  - Persistent work
  - Work is persistent when it remains scheduled through app restarts and system reboots
  - For background processing.
  - Versus Coroutines, All asynchronous work that doesn't need to be persistent.
