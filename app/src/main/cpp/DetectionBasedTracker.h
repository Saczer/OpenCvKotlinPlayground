//
// Created by molszak on 13.12.2017.
//

#ifndef OPENCVPLAYGROUND_DETECTIONBASEDTRACKER_JNI_H
#define OPENCVPLAYGROUND_DETECTIONBASEDTRACKER_JNI_H
#endif OPENCVPLAYGROUND_DETECTIONBASEDTRACKER_JNI_H

#include <jni.h>

#ifndef _Included_pl_olszak_michal_opencvplayground_DetectionBasedTracker
#define _Included_pl_olszak_michal_opencvplayground_DetectionBasedTracker
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     pl_michal_olszak_opencvplayground_DetectionBasedTracker
 * Method:    nativeCreateObject
 * Signature: (Ljava/lang/String;F)J
 */
JNIEXPORT jlong JNICALL Java_pl_olszak_michal_opencvplayground_DetectionBasedTracker_nativeCreateObject
  (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     pl_michal_olszak_opencvplayground_DetectionBasedTracker
 * Method:    nativeDestroyObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDestroyObject
        (JNIEnv *, jclass, jlong);

/*
 * Class:     pl_michal_olszak_opencvplayground_DetectionBasedTracker
 * Method:    nativeStart
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStart
        (JNIEnv *, jclass, jlong);

/*
 * Class:     pl_michal_olszak_opencvplayground_DetectionBasedTracker
 * Method:    nativeStop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStop
        (JNIEnv *, jclass, jlong);

/*
  * Class:     pl_michal_olszak_opencvplayground_DetectionBasedTracker
  * Method:    nativeSetFaceSize
  * Signature: (JI)V
  */
JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeSetFaceSize
        (JNIEnv *, jclass, jlong, jint);

/*
 * Class:     pl_michal_olszak_opencvplayground_DetectionBasedTracker
 * Method:    nativeDetect
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDetect
        (JNIEnv *, jclass, jlong, jlong, jlong);


#ifdef __cplusplus
}
#endif
#endif
