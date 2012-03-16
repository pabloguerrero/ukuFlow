CONTIKI_PROJECT = tester/tester
DOX = doxygen
DOXYGEN_CONF = doc/data-repository.doxyfile

all: $(CONTIKI_PROJECT)
	$(DOX) $(DOXYGEN_CONF)

ifndef TARGET
  TARGET = sky
endif


SRCS += data-mgr.c expression-eval.c

PROJECT_OBJECTFILES += base64.o data-mgr.o expression-eval.o
PROJECT_LIBRARIES += base64.o data-mgr.o expression-eval.o

ifndef CONTIKI
  CONTIKI = ../..
endif

include $(CONTIKI)/Makefile.include
