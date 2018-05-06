/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.facebook.samples.zoomable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Matrix
import android.view.animation.DecelerateInterpolator
import com.facebook.common.internal.Preconditions
import com.facebook.common.logging.FLog
import com.facebook.samples.gestures.TransformGestureDetector

/**
 * ZoomableController that adds animation capabilities to DefaultZoomableController using standard
 * Android animation classes
 */
class AnimatedZoomableController constructor(transformGestureDetector: TransformGestureDetector) :
        AbstractAnimatedZoomableController(transformGestureDetector) {

    private val mValueAnimator: ValueAnimator = ValueAnimator.ofFloat(0F, 1F)

    override val logTag: Class<*>
        get() = TAG

    init {
        mValueAnimator.interpolator = DecelerateInterpolator()
    }

    @SuppressLint("NewApi")
    override fun setTransformAnimated(
            newTransform: Matrix,
            durationMs: Long,
            onAnimationComplete: Runnable?) {
        FLog.v(logTag, "setTransformAnimated: duration %d ms", durationMs)
        stopAnimation()
        Preconditions.checkArgument(durationMs > 0)
        Preconditions.checkState(!isAnimating)
        isAnimating = true
        mValueAnimator.duration = durationMs
        transform.getValues(startValues)
        newTransform.getValues(stopValues)
        mValueAnimator.addUpdateListener { valueAnimator ->
            calculateInterpolation(workingTransform, valueAnimator.animatedValue as Float)
            super@AnimatedZoomableController.transform = workingTransform
        }
        mValueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator) {
                FLog.v(logTag, "setTransformAnimated: animation cancelled")
                onAnimationStopped()
            }

            override fun onAnimationEnd(animation: Animator) {
                FLog.v(logTag, "setTransformAnimated: animation finished")
                onAnimationStopped()
            }

            private fun onAnimationStopped() {
                onAnimationComplete?.run()
                isAnimating = false
                detector.restartGesture()
            }
        })
        mValueAnimator.start()
    }

    @SuppressLint("NewApi")
    public override fun stopAnimation() {
        if (!isAnimating) {
            return
        }
        FLog.v(logTag, "stopAnimation")
        mValueAnimator.cancel()
        mValueAnimator.removeAllUpdateListeners()
        mValueAnimator.removeAllListeners()
    }



    companion object {

        private val TAG = AnimatedZoomableController::class.java

        fun newInstance(): AnimatedZoomableController {
            return AnimatedZoomableController(TransformGestureDetector.newInstance())
        }
    }

}
