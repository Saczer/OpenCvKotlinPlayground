# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#path to opencv installation, relative to the directory of CMakeList.txt
set(pathToOpenCV C:/OpenCV-android-sdk)

add_library( detection-tracker SHARED
             ${CMAKE_SOURCE_DIR}/src/main/cpp/DetectionBasedTracker.cpp )

add_library( lib_opencv SHARED IMPORTED)

include_directories( ${pathToOpenCV}/sdk/native/jni/include)

set_target_properties( lib_opencv PROPERTIES IMPORTED_LOCATION
                       ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so )

find_library( log-lib
              log )


target_link_libraries( detection-tracker
                       lib_opencv
                       ${log-lib} )