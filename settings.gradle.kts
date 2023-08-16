rootProject.name = "ktcnc"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include("actor")
include("backend:database")

// data
include("data:linuxcnc:remote")
include("data:linuxcnc:legacy")
include("data:linuxcnc:api")

include("data:common:local")
include("data:common:impl")
include("data:common:api")

include("data:tools:local")
include("data:tools:remote")
include("data:tools:api")

include("data:gcode:local")
include("data:gcode:remote")
include("data:gcode:api")

include("data:settings:local")
include("data:settings:remote")
include("data:settings:api")

include("editor")
include("logger")
include("model")

// frontend
include("frontend:compose")
include("frontend:clipboard")
include("frontend:app")
include("frontend:breadcrumb")
include("frontend:filesystem")
include("frontend:splitpane")
include("frontend:scroll")
include("frontend:editor")
include("frontend:listitem")
include("frontend:widgets")
include("frontend:numpad")
include("frontend:di")
include("frontend:format")
include("frontend:domain")

// screen
include("frontend:screen:base")
include("frontend:screen:manual")
include("frontend:screen:programs")
include("frontend:screen:status")
include("frontend:screen:tools")
include("frontend:screen:conversational")

include("statemachine")
include("initializer")
include("dispatcher")
include("protos")
include("grpc")
include("ktlcnc")

// startup
include("startup:args")

include("ktlcnc:model")
//include("vtk")

//include("localization")
