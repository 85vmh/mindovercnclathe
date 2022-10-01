# MindOverCNC Lathe 

## Features

TODO

## Build

Set the required arguments in `gradle.properties` **or** environment variables.

| Env           | Property      | Description                |
|---------------|---------------|----------------------------|
| LINUXCNC_HOME | linuxcnc.home | The linuxcnc home folder   |
| LINUXCNC_JDK  | linuxcnc.jdk  | The jdk to be used for JNI |

[//]: # (| VTK_JAR       | vtk.jar       | Path to vtk.jar            |)

[//]: # (| VTK_LIB       | vtk.lib       | Path to vtk so files       |)

TODO

## Run the app

Run using gradle(you need to specify an ini file to run):

```shell
./gradlew run --args="configs/sim/axis/lathe.ini"
```

Other arguments:

| command | description                |
|---------|----------------------------|
| tb      | disable system top app bar |
| v       | disable vtk                |

## Usage

TODO

## Screenshots

[![screenshot](preview/numpad.png)](preview/numpad.png)

[![screenshot](preview/programs.png)](preview/programs.png)

[![screenshot](preview/simple_cycles.png)](preview/simple_cycles.png)

[![screenshot](preview/status.png)](preview/status.png)

[![screenshot](preview/tools_holders.png)](preview/tools_holders.png)

[![screenshot](preview/turning_settings.png)](preview/turning_settings.png)