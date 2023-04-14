#include <hal.h>
#include <stdio.h>
#include <map>
#include <string>
#include <com_mindovercnc_linuxcnc_HalHandler.h>
#include <com_mindovercnc_linuxcnc_HalComponent.h>
#include <com_mindovercnc_linuxcnc_BitHalPin.h>
#include <com_mindovercnc_linuxcnc_FloatHalPin.h>
#include <com_mindovercnc_linuxcnc_IntHalPin.h>
#include <string.h>
using namespace std;

union paramunion {
    hal_bit_t b;
    hal_u32_t u32;
    hal_s32_t s32;
    hal_float_t f;
};

union pinunion {
    void *v;
    hal_bit_t *b;
    hal_u32_t *u32;
    hal_s32_t *s32;
    hal_float_t *f;
};

union halunion {
    union pinunion pin;
    union paramunion param;
};

union haldirunion {
    hal_pin_dir_t pindir;
    hal_param_dir_t paramdir;
};

struct halitem {
    bool is_pin;
    hal_type_t type;
    union haldirunion dir;
    union halunion *u;
};

struct pyhalitem {
    halitem  pin;
    char * name;
};

typedef std::map<std::string, struct halitem> itemmap;

typedef struct halobject {
    int hal_id;
    char *name;
    itemmap items;
} halobject;

map<string, halobject> componentMap;

// Call the static method from kotlin that created the halPin with boolean type
jobject createBitPin(JNIEnv *env, jstring compName, jstring name, jobject dir){
    jclass clHalPin  = env->FindClass("com/mindovercnc/linuxcnc/model/HalPin");
    jmethodID myMethod = env->GetStaticMethodID(clHalPin, "bit", "(Ljava/lang/String;Ljava/lang/String;Lcom/mindovercnc/linuxcnc/model/HalPin$Dir;)Lcom/mindovercnc/linuxcnc/model/BitHalPin;");
    return env->CallStaticObjectMethod(clHalPin, myMethod, compName, name, dir);
}

// Call the static method from kotlin that created the halPin with float type
jobject createFloatPin(JNIEnv *env, jstring compName, jstring name, jobject dir){
    jclass clHalPin  = env->FindClass("com/mindovercnc/linuxcnc/model/HalPin");
    jmethodID myMethod = env->GetStaticMethodID(clHalPin, "float", "(Ljava/lang/String;Ljava/lang/String;Lcom/mindovercnc/linuxcnc/model/HalPin$Dir;)Lcom/mindovercnc/linuxcnc/model/FloatHalPin;");
    return env->CallStaticObjectMethod(clHalPin, myMethod, compName, name, dir);
}

// Call the static method from kotlin that created the halPin with s32 type
jobject createS32Pin(JNIEnv *env, jstring compName, jstring name, jobject dir){
    jclass clHalPin  = env->FindClass("com/mindovercnc/linuxcnc/model/HalPin");
    jmethodID myMethod = env->GetStaticMethodID(clHalPin, "s32", "(Ljava/lang/String;Ljava/lang/String;Lcom/mindovercnc/linuxcnc/model/HalPin$Dir;)Lcom/mindovercnc/linuxcnc/model/IntHalPin;");
    return env->CallStaticObjectMethod(clHalPin, myMethod, compName, name, dir);
}

/*
 * Class:     com_mindovercnc_linuxcnc_HalHandler
 * Method:    createComponent
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jobject JNICALL Java_com_mindovercnc_linuxcnc_HalHandler_createComponent
  (JNIEnv *env, jobject thisObject, jstring componentName){
    const char* compName = env->GetStringUTFChars(componentName, NULL);
    int result =  hal_init(compName);
    env->ReleaseStringUTFChars(componentName, compName);

    if(result > 0){
        jclass halCompClass  = env->FindClass("com/mindovercnc/linuxcnc/model/HalComponent");
        jfieldID fCompName   = env->GetFieldID(halCompClass, "name", "Ljava/lang/String;");
        jfieldID fCompId     = env->GetFieldID(halCompClass, "componentId", "I");

        jobject newHalComp = env->AllocObject(halCompClass);
        env->SetObjectField(newHalComp, fCompName, componentName);
        env->SetIntField(newHalComp, fCompId, result);

        char* copy = (char*)malloc(strlen(compName) + 1);
        strcpy(copy, compName);

        //store in the halobject the componentId and the componentName
        halobject aComp{ result, copy };

        //store in a map<componentName, halobject>
        componentMap[compName] = aComp;

        return newHalComp;
    }
    return NULL;
  }

/*
 * Class:     com_mindovercnc_linuxcnc_HalComponent
 * Method:    addPin
 * Signature: (Lcom/mindovercnc/linuxcnc/model/HalPin;)I
 */
JNIEXPORT jobject JNICALL Java_com_mindovercnc_linuxcnc_model_HalComponent_addPin
  (JNIEnv *env, jobject thisObject, jstring pinName, jobject pinTypeObj, jobject pinDirObj){

    jclass halCompClass  = env->FindClass("com/mindovercnc/linuxcnc/model/HalComponent");
    jclass clHalPin  = env->FindClass("com/mindovercnc/linuxcnc/model/HalPin");
    jclass clPinType  = env->FindClass("com/mindovercnc/linuxcnc/model/HalPin$Type");
    jclass clPinDir  = env->FindClass("com/mindovercnc/linuxcnc/model/HalPin$Dir");

    //Get details of the component object
    jfieldID fCompName    = env->GetFieldID(halCompClass, "name", "Ljava/lang/String;");
    jfieldID fCompId = env->GetFieldID(halCompClass, "componentId", "I");
    int componentId = env->GetIntField(thisObject, fCompId);

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    jmethodID pinTypeValueMethod = env->GetMethodID(clPinType, "getValue", "()I");
    jint typeValue = env->CallIntMethod(pinTypeObj, pinTypeValueMethod);

    jmethodID pinDirValueMethod = env->GetMethodID(clPinDir, "getValue", "()I");
    jint dirValue = env->CallIntMethod(pinDirObj, pinDirValueMethod);

    //rtapi_print_msg(RTAPI_MSG_ERR, "dir is: (%i)\n", dirValue);
    
    //Copy of the logic from halmodule.cc
    char pin_name[HAL_NAME_LEN+1];
    int result;

    halitem pin;
    pin.is_pin = 1;
    pin.type = (hal_type_t)typeValue;
    pin.dir.pindir = (hal_pin_dir_t)dirValue;
    pin.u = (halunion*)hal_malloc(sizeof(halunion));
    result = snprintf(pin_name, sizeof(pin_name), "%s.%s", compNameChars, pinNameChars);
    if(result > HAL_NAME_LEN || result < 0) {
            rtapi_print_msg(RTAPI_MSG_ERR,
                "Invalid pin name length \"%s.%s\": max = %d characters",
                compNameChars, pinNameChars, HAL_NAME_LEN);
    }

    result = hal_pin_new(pin_name, pin.type, pin.dir.pindir, (void**)pin.u, componentId);
    if(result) return NULL;

    //in the componentMap, find the component, then update its map of items with:
    // items[pinNameWithoutPrefix] = pin
    // later when we need the pin, we don't wanna need the prefix (which is the component name)
    componentMap[compNameChars].items[pinNameChars] = pin;

    //rtapi_print_msg(RTAPI_MSG_ERR, "pinName with prefix is: (%s)\n", pin_name);

    switch(pin.type){
        case HAL_BIT:
            return createBitPin(env, compName, pinName, pinDirObj);
            break;
        case HAL_FLOAT:
            return createFloatPin(env, compName, pinName, pinDirObj);
            break;
        case HAL_S32:
            return createS32Pin(env, compName, pinName, pinDirObj);
            break;
    }
    return NULL;
  }

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_model_HalComponent_setReady
  (JNIEnv *env, jobject thisObject, jint componentId){

//    jclass halCompClass  = env->FindClass("com/mindovercnc/linuxcnc/model/HalComponent");
//    jfieldID fCompId = env->GetFieldID(halCompClass, "componentId", "I");
//    int componentId = env->GetIntField(thisObject, fCompId);
    rtapi_print_msg(RTAPI_MSG_ERR, "ready compId is: (%d)\n", componentId);
    int res = hal_ready(componentId);
    rtapi_print_msg(RTAPI_MSG_ERR, "ready result is: (%s)\n", res);
  }

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_model_BitHalPin_setPinValue
  (JNIEnv *env, jobject thisObject, jboolean value){

    jclass clHalPin     = env->FindClass("com/mindovercnc/linuxcnc/model/BitHalPin");
    jfieldID fCompName   = env->GetFieldID(clHalPin, "componentName", "Ljava/lang/String;");
    jfieldID fPinName   = env->GetFieldID(clHalPin, "name", "Ljava/lang/String;");

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    jstring pinName = (jstring) env->GetObjectField(thisObject, fPinName);

    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    halitem pin = componentMap[compNameChars].items[pinNameChars];
    *(pin.u->pin.b) = value;
    //rtapi_print_msg(RTAPI_MSG_ERR, "pinName setPinValue is: (%b)\n", value);
  }

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_model_FloatHalPin_setPinValue
  (JNIEnv *env, jobject thisObject, jfloat value){

    jclass clHalPin     = env->FindClass("com/mindovercnc/linuxcnc/model/FloatHalPin");
    jfieldID fCompName   = env->GetFieldID(clHalPin, "componentName", "Ljava/lang/String;");
    jfieldID fPinName   = env->GetFieldID(clHalPin, "name", "Ljava/lang/String;");

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    jstring pinName = (jstring) env->GetObjectField(thisObject, fPinName);

    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    halitem pin = componentMap[compNameChars].items[pinNameChars];
    *(pin.u->pin.f) = value;
    //rtapi_print_msg(RTAPI_MSG_ERR, "pinName setPinValue is: (%b)\n", value);
  }

JNIEXPORT void JNICALL Java_com_mindovercnc_linuxcnc_model_IntHalPin_setPinValue
  (JNIEnv *env, jobject thisObject, jint value){

    jclass clHalPin     = env->FindClass("com/mindovercnc/linuxcnc/model/IntHalPin");
    jfieldID fCompName   = env->GetFieldID(clHalPin, "componentName", "Ljava/lang/String;");
    jfieldID fPinName   = env->GetFieldID(clHalPin, "name", "Ljava/lang/String;");

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    jstring pinName = (jstring) env->GetObjectField(thisObject, fPinName);

    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    halitem pin = componentMap[compNameChars].items[pinNameChars];
    *(pin.u->pin.s32) = value;
    //rtapi_print_msg(RTAPI_MSG_ERR, "pinName setPinValue is: (%b)\n", value);
  }

/*
 * Class:     com_mindovercnc_linuxcnc_HalComponent
 * Method:    getPin
 * Signature: (Ljava/lang/String;)Lcom/mindovercnc/linuxcnc/model/HalPin;
 */
JNIEXPORT jobject JNICALL Java_com_mindovercnc_linuxcnc_model_BitHalPin_refreshValue
  (JNIEnv *env, jobject thisObject){

    jclass clHalPin     = env->FindClass("com/mindovercnc/linuxcnc/model/BitHalPin");
    jfieldID fCompName   = env->GetFieldID(clHalPin, "componentName", "Ljava/lang/String;");
    jfieldID fPinName   = env->GetFieldID(clHalPin, "name", "Ljava/lang/String;");

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    jstring pinName = (jstring) env->GetObjectField(thisObject, fPinName);

    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    halitem pin = componentMap[compNameChars].items[pinNameChars];

    bool value = *(pin.u->pin.b);

    //now that we got the value, build java Boolean object, set the value into it and return it.
    jclass clBool = env->FindClass("java/lang/Boolean");
    jmethodID boolConstructor = env->GetStaticMethodID(clBool, "valueOf", "(Z)Ljava/lang/Boolean;");

    return env->CallStaticObjectMethod(clBool, boolConstructor, value);
  }

/*
 * Class:     com_mindovercnc_linuxcnc_HalComponent
 * Method:    getPin
 * Signature: (Ljava/lang/String;)Lcom/mindovercnc/linuxcnc/model/HalPin;
 */
JNIEXPORT jobject JNICALL Java_com_mindovercnc_linuxcnc_model_FloatHalPin_refreshValue
  (JNIEnv *env, jobject thisObject){

    jclass clHalPin     = env->FindClass("com/mindovercnc/linuxcnc/model/FloatHalPin");
    jfieldID fCompName   = env->GetFieldID(clHalPin, "componentName", "Ljava/lang/String;");
    jfieldID fPinName   = env->GetFieldID(clHalPin, "name", "Ljava/lang/String;");

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    jstring pinName = (jstring) env->GetObjectField(thisObject, fPinName);

    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    halitem pin = componentMap[compNameChars].items[pinNameChars];

    float value = *(pin.u->pin.f);

    //now that we got the value, build java Float object, set the value into it and return it.
    jclass clFloat = env->FindClass("java/lang/Float");
    jmethodID floatConstructor = env->GetStaticMethodID(clFloat, "valueOf", "(F)Ljava/lang/Float;");

    return env->CallStaticObjectMethod(clFloat, floatConstructor, value);
  }

/*
 * Class:     com_mindovercnc_linuxcnc_HalComponent
 * Method:    getPin
 * Signature: (Ljava/lang/String;)Lcom/mindovercnc/linuxcnc/model/HalPin;
 */
JNIEXPORT jobject JNICALL Java_com_mindovercnc_linuxcnc_model_IntHalPin_refreshValue
  (JNIEnv *env, jobject thisObject){

    jclass clHalPin     = env->FindClass("com/mindovercnc/linuxcnc/model/IntHalPin");
    jfieldID fCompName   = env->GetFieldID(clHalPin, "componentName", "Ljava/lang/String;");
    jfieldID fPinName   = env->GetFieldID(clHalPin, "name", "Ljava/lang/String;");

    jstring compName = (jstring) env->GetObjectField(thisObject, fCompName);
    jstring pinName = (jstring) env->GetObjectField(thisObject, fPinName);

    const char* compNameChars = env -> GetStringUTFChars(compName, 0);
    const char* pinNameChars = env -> GetStringUTFChars(pinName, 0);

    halitem pin = componentMap[compNameChars].items[pinNameChars];

    int value = *(pin.u->pin.s32);

    //now that we got the value, build java Integer object, set the value into it and return it.
    jclass clInteger = env->FindClass("java/lang/Integer");
    jmethodID integerConstructor = env->GetStaticMethodID(clInteger, "valueOf", "(I)Ljava/lang/Integer;");

    return env->CallStaticObjectMethod(clInteger, integerConstructor, value);
  }

