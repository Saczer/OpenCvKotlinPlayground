//
// Created by molszak on 02.01.2018.
//

#include "EdgeDetector.h"
#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>

#include <string>
#include <vector>

#include <android/log.h>
#include <jni.h>

#define TAG "EdgeDetector"
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

struct contour_sorter {
    bool operator()(const vector<Point> &a, const vector<Point> &b) {
        Rect ra(boundingRect(a));
        Rect rb(boundingRect(b));

        return ra.area() > rb.area();
    }
};

class EdgeDetector {
public:
    EdgeDetector() {
        gaussianScalar = Size(5, 5);
        contourColor = Scalar(0, 255, 0);
    }

    void detect(const Mat &imageRgb, const Mat &imageGray) {
        vector<vector<Point> > contours;
        vector<Vec4i> hierarchy;

//        GaussianBlur(imageGray, edged, gaussianScalar, 0);
        Canny(imageGray, imageGray, 100, 300);

        LOGD("canny processed");

        findContours(imageGray, contours, hierarchy, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
        sort(contours.begin(), contours.end(), contour_sorter());

        vector<vector<Point> > sorted;
        if (contours.size() > 5) {
            sorted = vector<vector<Point> >(contours.begin(), contours.begin() + 5);
        } else {
            sorted = contours;
        }

        LOGD("found some contours");

        for (int i = 0; i < sorted.size(); i++) {
            double peri = arcLength(sorted[i], true);
            vector<Point> approx;
            approxPolyDP(sorted[i], approx, 0.02 * peri, true);

            if (approx.size() == 4) {
                LOGD("found approx");
                vector<vector<Point> > found = vector<vector<Point> >();
                found.push_back(approx);

                drawContours(imageRgb, found, -1, contourColor, 2);
                LOGD("drew approx");
                break;
            }
        }
    }

    virtual ~EdgeDetector() {
    }

private:
    Size gaussianScalar;
    Scalar contourColor;
};

JNIEXPORT jlong JNICALL
Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_createObject
        (JNIEnv *jenv, jclass) {
    LOGD("Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_createObject start");

    jlong result = 0;
    try {
        result = (jlong) new EdgeDetector();
    } catch (Exception &e) {
        throwCVException(jenv, e, "createObject");
    } catch (...) {
        throwUnknownException(jenv, "createObject");
    }

    LOGD("Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_createObject exit");

    return result;
}

JNIEXPORT void JNICALL
Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_nativeDestroyObject
        (JNIEnv *jenv, jclass, jlong thiz) {
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDestroyObject");

    try {
        if (thiz != 0) {
            delete (EdgeDetector *) thiz;
        }
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeDestroyObject");
    } catch (...) {
        throwUnknownException(jenv, "nativeDestroyObject");
    }
    LOGD("Java_pl_michal_olszak_opencvplayground_DetectionBasedTracker_nativeDestroyObject exit");
}


JNIEXPORT void JNICALL
Java_pl_olszak_michal_opencvplayground_detector_EdgeDetector_nativeDetect
        (JNIEnv *jenv, jclass, jlong thiz, jlong imageRgb, jlong imageGray) {
    LOGD("Java_pl_michal_olszak_opencvplayground_detector_EdgeDetector_nativeDetect start");

    try {
        EdgeDetector *detector = (EdgeDetector *) thiz;
        detector->detect(*((Mat *) imageRgb), *((Mat *) imageGray));
    } catch (Exception &e) {
        throwCVException(jenv, e, "nativeDetect");
    } catch (...) {
        throwUnknownException(jenv, "nativeDetect");
    }

    LOGD("Java_pl_michal_olszak_opencvplayground_detector_EdgeDetector_nativeDetect exit");
}