CONTIKI_PROJECT = ukuflow-serial tester/tester
DOX = doxygen
DOXYGEN_CONF = doc/ukuFlow.doxyfile
CLOC = cloc
SIZE = msp430-size

ifndef TARGET
  TARGET = z1
endif

all: $(CONTIKI_PROJECT)	 
	$(DOX) $(DOXYGEN_CONF)
	$(CLOC) *.c *.h tester/*.c
	$(SIZE) ukuflow-serial.$(TARGET) tester/tester.$(TARGET)

# Contiki's flags:
CFLAGS += -DPROJECT_CONF_H=\"project-conf.h\"
CFLAGS += -DDEBUG_DEPTH=1
CFLAGS += -DPROCESS_CONF_NO_PROCESS_NAMES=1

APPS = serial-shell data-mgr scopes 

SRCS				+= workflow.c event.c ukuflow-mgr.c ukuflow-engine.c ukuflow-net-mgr.c ukuflow-cmd-runner.c ukuflow-event-mgr.c

PROJECT_OBJECTFILES	+= workflow.o event.o ukuflow-mgr.o ukuflow-engine.o ukuflow-net-mgr.o ukuflow-cmd-runner.o ukuflow-event-mgr.o 
PROJECT_LIBRARIES	+= workflow.o event.o ukuflow-mgr.o ukuflow-engine.o ukuflow-net-mgr.o ukuflow-cmd-runner.o ukuflow-event-mgr.o

ifndef CONTIKI
  CONTIKI = ../..
endif

include $(CONTIKI)/Makefile.include