//Dependencies.kt
/**
 * Created by Sanket Gurav on 2/21/2018.
 */
//https://handstandsam.com/2018/02/11/kotlin-buildsrc-for-better-gradle-dependency-management/
object Versions {
    val appVersion = "11"
    val appVersionCode = 1
    val kotlin = "1.2.21"
    val support_lib = "27.0.2"
    val constraint_layout = "1.0.2"
    val retrofit = "2.3.0"
    val rxjava = "2.1.9"
    val rxBinding= "2.0.0"
    val rxKotlin= "2+"
    val frescoVersion = "1.5.0"
    val timberVersion = "4.5.+"
    val daggerVersion = "2.13"
    val rxAndroidVersion = "2.0.1"
    val lifecycleVersion = "1.0.0-alpha3"
    val retrofitVersion = "2.3.0"
    val gsonVersion = "2.8.1"
    val koin = "0.8.2"
    val lifecycleRuntimeVersion = "1.0.3"
    val lifecycleCompilerVersion = "1.0.0"
    val javaVersion = "1.0.0"
    val extensionsVersion = "1.0.0"
    val reactivestreamsVersion = "1.0.0"
    val pagingRuntimeVersion = "1.0.0-alpha3"

}

object Dependencie {

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"

    //region supportLibs
    val support_annotations = "com.android.support:support-annotations:${Versions.support_lib}"
    val support_appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    val support_design = "com.android.support:design:${Versions.support_lib}"
    val support_vector_drawable = "com.android.support:support-vector-drawable:${Versions.support_lib}"
    val support_v4 = "com.android.support:support-v4:${Versions.support_lib}"
    val support_palette_v7 = "com.android.support:palette-v7:${Versions.support_lib}"
    val support_cardview_v7 = "com.android.support:cardview-v7:${Versions.support_lib}"
    val support_gridlayout_v7 = "com.android.support:gridlayout-v7:${Versions.support_lib}"
    val constraint_layout = "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"

    //endregion
    val inject="javax.inject:javax.inject:1"
    val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    val fresco = "com.facebook.fresco:fresco:${Versions.frescoVersion}"

    val koin = "org.koin:koin-android:${Versions.koin}"
    val koin_android_architecture = "org.koin:koin-android-architecture:${Versions.koin}"

    // JSON Parsing
    val gson= "com.google.code.gson:gson:${Versions.gsonVersion}"
    val retrofit_converter= "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    //region ReactiveX
    val rxJava="io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxAndroid="io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
    val rxKotlin= "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    val rxBinding= "com.jakewharton.rxbinding2:rxbinding:${Versions.rxBinding}"

    //Lifecycle
    val lifecycle_runtime= "android.arch.lifecycle:runtime:${Versions.lifecycleRuntimeVersion}"
    val lifecycle_compiler= "android.arch.lifecycle:compiler:${Versions.lifecycleCompilerVersion}"
    val java= "android.arch.lifecycle:common-java8:${Versions.javaVersion}"
    val paging_runtime="android.arch.paging:runtime:${Versions.pagingRuntimeVersion}"

    //LiveData and view model
    val extensions= "android.arch.lifecycle:extensions:${Versions.extensionsVersion}"
    val reactivestreams= "android.arch.lifecycle:reactivestreams:${Versions.reactivestreamsVersion}"
}
