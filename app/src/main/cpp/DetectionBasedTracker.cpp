//
// Created by molszak on 13.12.2017.
//

#include "DetectionBasedTracker.h"
#include <opencv2/core.hpp>
#include <opencv2/objdetect.hpp>

#include <string>
#include <vector>

#include <android/log.h>
#include <jni.h>

#define TAG "DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__))

using namespace std;
using namespace cv;

inline void throwUnknownException(JNIEnv *jenv, string method) {
    LOGD("%s caught unknown exception", method.c_str());
    jclass je = jenv->FindClass("java/lang/Exception");
    string exception = "Unknown exception in JNI code of ";
    exception.append(TAG).append(".").append(method);
    const char *exceptionMessage = exception.c_str();

    jenv->ThrowNew(je, exceptionMessage);
}

inline void throwCVException(JNIEnv *jenv, Exception &ex, string method) {
    LOGD("%s caught cv::Exception: %s", method.c_str(), ex.what());
    jclass je = jenv->FindClass("org/opencv/core/CvException");
    if (!je) {
        je = jenv->FindClass("java/lang/Exception");
    }
    jenv->ThrowNew(je, ex.what());
}


class CascadeDetectorAdapter : public DetectionBasedTracker::IDetector {
public:
    CascadeDetectorAdapter(Ptr<CascadeClassifier> detector) :
            IDetector(),
            Detector(detector) {
        LOGD("CascadeDetectorAdapter::Detect::Detect");
        CV_Assert(detector);
    }

    void detect(const Mat &Image, vector<Rect> &objects) {
        LOGD("CascadeDetectorAdapter::Detect: begin");
        LOGD("CascadeDetectorAdapter::Detect: scaleFactor=%.2f, minNeighbours=%d, minObjSize=(%dx%d),"
                     " maxObjSize=(%dx%d)", scaleFactor, minNeighbours, minObjSize.width,
             minObjSize.height,
             maxObjSize.width, maxObjSize.height);
        Detector->detectMultiScale(Image, objects, scaleFactor, minNeighbours, 0, minObjSize,
                                   maxObjSize);
        LOGD("CascadeDetectorAdapter::Detect:end");
    }

    virtual ~CascadeDetectorAdapter() {
        LOGD("CascadeDetectorAdapter::Detect::~Detect");
    }

private:
    CascadeDetectorAdapter() {

    }

    Ptr<CascadeClassifier> Detector;
};

struct DetectorAggregator {
    Ptr<CascadeDetectorAdapter> mainDetector;
    Ptr<CascadeDetectorAdapter> trackingDetector;

    Ptr<DetectionBasedTracker> tracker;

    DetectorAggregator(Ptr<CascadeDetectorAdapter> &_mainDetector,
                      Ptr<CascadeDetectorAdapter> &_trackingDetector) :
            mainDetector(_mainDetector),
            trackingDetector(_trackingDetector) {
        CV_Assert(_mainDetector);
        CV_Assert(_trackingDetector);

        DetectionBasedTracker::Parameters DetectorParams;
        tracker = makePtr<DetectionBasedTracker>(mainDetector, trackingDetector, DetectorParams);
    }
};

JNIEXPORT jlong JNICALL
Java_pl_olszak_michal_opencvplayground_DetectionBasedTracker_nativeCreateObject
        (JNIEnv *jenv, jclass, jstring jFileName, jint faceSize) {
    LOGD("Java_pl_olszak_michal_opencvplayground_DetectionBasedTracker_nativeCreateObject enter");
    const char *jnamestr = jenv->GetStringUTFChars(jFileName, NULL);
    string stdFileName(jnamestr);
    jlong result = 0;

    try {
        Ptr<CascadeDetectorAdapter> mainDetector = makePtr<CascadeDetectorAdapter>(
                makePtr<CascadeClassifier>(stdFileName));
        Ptr<CascadeDetectorAdapter> trackingDetector = makePtr<CascadeDetectorAdapter>(
                makePtr<CascadeClassifier>(stdFileName));
        result = (jlong) new DetectorAggregator(mainDetector, trackingDetector);
        if (faceSize > 0) {
            mainDetector->setMaxObjectSize(Size(faceSize, faceSize));
        }
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeCreateObject");
    } catch (...) {
        throwUnknownException(jenv, "nativeCreateObject");
    }

    LOGD("Java_pl_olszak_michal_opencvplayground_DetectionBasedTracker_nativeCreateObject exit");
    return result;
}

JNIEXPORT void JNICALL
Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDestroyObject
        (JNIEnv *jenv, jclass, jlong thiz) {
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDestroyObject");

    try {
        if (thiz != 0) {
            ((DetectorAggregator *) thiz)->tracker->stop();
            delete (DetectorAggregator *) thiz;
        }
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeDestroyObject");
    } catch (...) {
        throwUnknownException(jenv, "nativeDestroyObject");
    }
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDestroyObject exit");
}

JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStart
        (JNIEnv *jenv, jclass, jlong thiz) {
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStart");

    try {
        ((DetectorAggregator *) thiz)->tracker->run();
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeStart");
    } catch (...) {
        throwUnknownException(jenv, "nativeStart");
    }

    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStart exit");
}

JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStop
        (JNIEnv *jenv, jclass, jlong thiz) {
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStop");

    try {
        ((DetectorAggregator *) thiz)->tracker->stop();
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeStop");
    } catch (...) {
        throwUnknownException(jenv, "nativeStop");
    }

    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeStop exit");
}

JNIEXPORT void JNICALL
Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeSetFaceSize
        (JNIEnv *jenv, jclass, jlong thiz, jint faceSize) {
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeSetFaceSize");

    try {
        if (faceSize > 0) {
            ((DetectorAggregator *) thiz)->mainDetector->setMinObjectSize(Size(faceSize, faceSize));
        }
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeSetFaceSize");
    } catch (...) {
        throwUnknownException(jenv, "nativeSetFaceSize");
    }

    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeSetFaceSize exit");
}

JNIEXPORT void JNICALL Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDetect
        (JNIEnv *jenv, jclass, jlong thiz, jlong imageGray, jlong faces) {
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDetect");

    try {
        vector<Rect> rectFaces;
        DetectorAggregator *aggregator = (DetectorAggregator *) thiz;
        aggregator->tracker->process(*((Mat*)imageGray));
        aggregator->tracker->getObjects(rectFaces);

        *((Mat*)faces) = Mat(rectFaces, true);
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeDetect");
    } catch (...) {
        throwUnknownException(jenv, "nativeDetect");
    }

    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDetect exit");
}







