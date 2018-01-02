//
// Created by molszak on 02.01.2018.
//

#ifndef OPENCVPLAYGROUND_EDGEDETECTOR_H
#define OPENCVPLAYGROUND_EDGEDETECTOR_H
#endif //OPENCVPLAYGROUND_EDGEDETECTOR_H

#include <jni.h>

#ifndef _Included_pl_olszak_michal_opencvplayground_detector_EdgeDetector
#define _Included_pl_olszak_michal_opencvplayground_detector_EdgeDetector
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     pl_olszak_michal_opencvplayground_detector_EdgeDetector
 * Method:    nativeCreateObject
 * Signature: (Ljava/lang/String;F)J
 */
JNIEXPORT jlong JNICALL Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_createObject
        (JNIEnv *, jclass);

JNIEXPORT void JNICALL
Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_nativeDestroyObject
        (JNIEnv *, jclass, jlong);

JNIEXPORT void JNICALL Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_nativeDetect
        (JNIEnv *, jclass, jlong, jlong, jlong);


#ifdef __cplusplus
}
#endif
#endif