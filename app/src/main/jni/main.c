//
// Created by lucas li on 2020-03-31.
//

#include <jni.h>
/* Header for class com_doommes_learn_Jni_TestJni */
#include <stddef.h>
#ifndef _Included_com_doommes_learn_Jni_TestJni
#define _Included_com_doommes_learn_Jni_TestJni
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_doommes_learn_Jni_TestJni
 * Method:    sayHello
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_doommes_learn_Jni_TestJni_sayHello
        (JNIEnv *env, jclass object){
    return (*env)->NewStringUTF(env,"hello umeng");
}

JNIEXPORT jint JNICALL Java_com_doommes_learn_Jni_TestJni_sum
        (JNIEnv *env, jclass object, jint a, jint b){
    return (a+b);
}

#ifdef __cplusplus
}

#endif
#endif