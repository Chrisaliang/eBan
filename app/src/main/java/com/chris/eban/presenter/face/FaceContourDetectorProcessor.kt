package com.chris.eban.presenter.face

import android.graphics.Bitmap
import com.chris.eban.common.CameraImageGraphic
import com.chris.eban.common.FrameMetadata
import com.chris.eban.common.GraphicOverlay
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import timber.log.Timber
import java.io.IOException

/**
 * Face Contour Demo.
 */
class FaceContourDetectorProcessor : VisionProcessorBase<List<FirebaseVisionFace>>() {

    private val detector: FirebaseVisionFaceDetector

    init {
        val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                .build()

        detector = FirebaseVision.getInstance().getVisionFaceDetector(options)
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Timber.tag(TAG).e("Exception thrown while trying to close Face Contour Detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionFace>> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(
            originalCameraImage: Bitmap?,
            results: List<FirebaseVisionFace>,
            frameMetadata: FrameMetadata,
            graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()

        originalCameraImage?.let {
            val imageGraphic = CameraImageGraphic(graphicOverlay, it)
            graphicOverlay.add(imageGraphic)
        }

        results.forEach {
            val faceGraphic = FaceContourGraphic(graphicOverlay, it)
            graphicOverlay.add(faceGraphic)
        }

        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Timber.tag(TAG).e("Face detection failed $e")
    }

    companion object {
        private const val TAG = "FaceContourDetectorProc"
    }
}