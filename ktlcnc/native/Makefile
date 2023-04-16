# make file
#
ifeq ($(TOOL_NML),yes)
  TOOL_NML_FLAG := -DTOOL_NML
  $(info TOOL_NML_FLAG = $(TOOL_NML_FLAG))
endif
O_HAL   = de_schwarzrot_hal_ItemList.new.o
O_NML   = com_mindovercnc_linuxcnc_CommandWriter.o \
 com_mindovercnc_linuxcnc_ErrorReader.o \
 com_mindovercnc_linuxcnc_StatusReader.o \
 com_mindovercnc_linuxcnc_HalHandler.o
INCLUDES=-I. \
 -I$(abspath ${LINUXCNC_HOME}/src) \
 -I$(abspath ${LINUXCNC_HOME}/include) \
 -I${LINUXCNC_JDK}/include \
 -I${LINUXCNC_JDK}/include/linux
CC_FLAGS=$(INCLUDES) \
 -DULAPI -g -Wall -Os -fwrapv -Woverloaded-virtual \
 -D_GNU_SOURCE $(TOOL_NML_FLAG) -Wno-psabi -std=gnu++11 \
 -MP -MD -fPIC
LC_FLAGS = -DLOCALE_DIR=\"/usr/share/locale\" \
 -DPACKAGE=\"linuxcnc\" \
 $(TOOL_NML_FLAG) \
 -L$(abspath ${LINUXCNC_HOME}/lib) \
  $(abspath ${LINUXCNC_HOME}/lib/libnml.so.0) \
 -Wl,-rpath,$(abspath ${LINUXCNC_HOME}/lib) \
 -Xlinker -Map=libLinuxCNC.map \
 -L/usr/X11R6/lib -lm -lGL

GCC = g++

JLIBS = ../lib/derby.jar:../lib/derbyshared.jar:../lib/derbytools.jar:../lib/glazedlists-1.11.0.jar:../lib/jme3-core.jar:../lib/jme3-desktop.jar:../lib/jme3-lwjgl.jar:../lib/LCExportHandler.jar:../lib/lwjgl.jar:../lib/lwjgl-platform-natives-linux.jar

LC_OBJS = $(abspath ${LINUXCNC_HOME}/src/objects/emc/nml_intf/emc.o) \
 $(abspath ${LINUXCNC_HOME}/src/objects/emc/nml_intf/emcops.o) \
 $(abspath ${LINUXCNC_HOME}/src/objects/emc/rs274ngc/modal_state.o) \
 $(abspath ${LINUXCNC_HOME}/src/objects/emc/tooldata/tooldata_common.o) \
 $(abspath ${LINUXCNC_HOME}/src/objects/emc/tooldata/tooldata_mmap.o) \
 $(abspath ${LINUXCNC_HOME}/src/objects/emc/tooldata/tooldata_db.o) \
 $(abspath ${LINUXCNC_HOME}/lib/libnml.so.0)

all: 	libLinuxCNC.so DumpBufDesc

clean:
	rm *.o
	rm *.d
	rm *.so
	rm DumpBufDesc
	rm ../src/main/resources/libLinuxCNC.so

# copy:

headers:
	javac -h . -cp .:../src:$(JLIBS) ../src/de/schwarzrot/system/CommandWriter.java
	javac -h . -cp .:../src:$(JLIBS) ../src/de/schwarzrot/system/ErrorReader.java
	javac -h . -cp .:../src:$(JLIBS) ../src/de/schwarzrot/system/StatusReader.java

%.o : %.cc
	$(GCC) -c $(CC_FLAGS) -o $@ $<

# we add the libLinuxCNC.so into the resources folder so that when the jar is created, it contains the .so file
libLinuxCNC.so: $(O_NML) $(LC_OBJS)
	$(GCC) -shared $(LC_FLAGS) -o $@ $^
	cp libLinuxCNC.so ../src/main/resources/libLinuxCNC.so


DumpBufDesc:	dumpBufDesc.o
	$(GCC) $(LC_FLAGS) -o $@ $^

TestNML:	TestNML.o $(LC_OBJS)
	$(GCC) $(LC_FLAGS) -o $@ $^

