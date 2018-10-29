package com.miruken.mvc.android

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import android.view.View
import java.util.concurrent.FutureTask

// Binding

fun <T : View> Activity.bind(@IdRes idRes: Int) =
    unsafeLazy { findViewById<T>(idRes) }

fun <T : View> View.bind(@IdRes idRes: Int) =
        unsafeLazy { findViewById<T>(idRes) }

private fun <T> unsafeLazy(initializer: () -> T) =
        lazy(LazyThreadSafetyMode.NONE, initializer)

// Synchronization

fun <T: Any?> runOnMainThread(block: () -> T): T =
    if (Thread.currentThread() != mainThread) {
        val task = FutureTask<T>(block)
        mainHandler.post(task)
        task.get()
    } else {
        block()
    }

private val mainLooper  = Looper.getMainLooper()
private val mainHandler = Handler(mainLooper)
private val mainThread  = mainLooper.thread
