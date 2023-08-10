rootProject.name = "ktcnc"

pluginManagement{
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
