CONTIKI_PROJECT = tester/tester
SIZE = msp430-size
CLOC = cloc
DOX = doxygen
DOXYGEN_CONF = doc/ukuflow.doxyfile

all: $(CONTIKI_PROJECT)	 
	$(DOX) $(DOXYGEN_CONF)
	$(CLOC) *.c *.h tester/*.c
	$(SIZE) tester/tester.sky

ifndef TARGET
  TARGET = sky
endif

# Contiki's flags:
CFLAGS += -DPROJECT_CONF_H=\"project-conf.h\"
CFLAGS += -DDEBUG_DEPTH=0

APPS = serial-shell data-repository scopes 

SRCS				+= workflow.c event.c ukuflow-mgr.c ukuflow-engine.c ukuflow-net-mgr.c ukuflow-cmd-runner.c ukuflow-event-mgr.c  

PROJECT_OBJECTFILES	+= workflow.o event.o ukuflow-mgr.o ukuflow-engine.o ukuflow-net-mgr.o ukuflow-cmd-runner.o ukuflow-event-mgr.o 
PROJECT_LIBRARIES	+= workflow.o event.o ukuflow-mgr.o ukuflow-engine.o ukuflow-net-mgr.o ukuflow-cmd-runner.o ukuflow-event-mgr.o

ifndef CONTIKI
  CONTIKI = ../..
endif

include $(CONTIKI)/Makefile.include
