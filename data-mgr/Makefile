CONTIKI_PROJECT = tester/tester
DOX = doxygen
DOXYGEN_CONF = doc/data-mgr.doxyfile
CLOC = cloc
SIZE = msp430-size

all: $(CONTIKI_PROJECT)
	$(DOX) $(DOXYGEN_CONF)
	$(CLOC) *.c *.h tester/*.c
	$(SIZE) tester/tester.z1

ifndef TARGET
  TARGET = z1
endif


SRCS += data-mgr.c expression-eval.c

PROJECT_OBJECTFILES += base64.o data-mgr.o expression-eval.o
PROJECT_LIBRARIES += base64.o data-mgr.o expression-eval.o

ifndef CONTIKI
  CONTIKI = ../..
endif

include $(CONTIKI)/Makefile.include
