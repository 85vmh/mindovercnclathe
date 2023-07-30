rootProject.name = "MindOverCNC Lathe"
include("actor")
include("compose")
include("clipboard")
include("database")

// data
include("data:linuxcnc:remote")
include("data:linuxcnc:legacy")
include("data:linuxcnc:api")

include("data:common:local")
include("data:common:impl")
include("data:common:api")

include("editor")
include("logger")
include("model")

// frontend
include("frontend:app")
include("frontend:breadcrumb")
include("frontend:filesystem")
include("frontend:splitpane")
include("frontend:scroll")

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
