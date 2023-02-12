package com.example.natifegif.utils

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.natifegif.presentation.GifActivity
import java.lang.Math.abs

class OnSwipeTouchListener internal constructor(mainView: View, private val activity: GifActivity) :
    View.OnTouchListener {
    private val gestureDetector: GestureDetector
    private lateinit var onSwipe: OnSwipeListener

    init {
        gestureDetector = GestureDetector(activity, GestureListener())
        mainView.setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private companion object {
        private const val swipeThreshold = 100
        private const val swipeVelocityThreshold = 100
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }
    }

    internal fun onSwipeRight() {
        activity.swipeRight()
        this.onSwipe.swipeRight()
    }

    internal fun onSwipeLeft() {
        activity.swipeLeft()
        this.onSwipe.swipeLeft()
    }

    internal interface OnSwipeListener {
        fun swipeRight()
        fun swipeLeft()
    }
}
