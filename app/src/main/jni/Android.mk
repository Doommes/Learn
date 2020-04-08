LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := JniTest
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \
	-lz \
	-lm \

LOCAL_SRC_FILES := \
	/Users/lucasli/PersonProject/Learn/app/src/main/jni/main.c \

LOCAL_C_INCLUDES += /Users/lucasli/PersonProject/Learn/app/src/debug/jni
LOCAL_C_INCLUDES += /Users/lucasli/PersonProject/Learn/app/src/main/jni

include $(BUILD_SHARED_LIBRARY)
