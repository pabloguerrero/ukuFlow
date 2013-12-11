CONTIKI_PROJECT = tester/tester tester/tester_parallel
DOX = doxygen
DOXYGEN_CONF = doc/scopes.doxyfile
CLOC = cloc
SIZE = msp430-size

ifndef TARGET
  TARGET = z1
endif

all: $(CONTIKI_PROJECT)
	$(DOX) $(DOXYGEN_CONF)
	$(CLOC) *.c *.h tester/*.c
	$(SIZE) tester/tester.$(TARGET) tester/tester_parallel.$(TARGET)

# Contiki's flags:
CFLAGS += -DPROJECT_CONF_H=\"project-conf.h\"
CFLAGS += -DDEBUG_DEPTH=3
CFLAGS += -DPROCESS_CONF_NO_PROCESS_NAMES=1

APPS = data-mgr

SRCS                += bitvector.c frag-unicast.c scopes.c scopes-selfur.c scopes-membership-simple.c # scopes-flooding.c 

PROJECT_OBJECTFILES += bitvector.o frag-unicast.o scopes.o scopes-selfur.o scopes-membership-simple.o # scopes-flooding.o
PROJECT_LIBRARIES   += bitvector.o frag-unicast.o scopes.o scopes-selfur.o scopes-membership-simple.o # scopes-flooding.o

ifndef CONTIKI
  CONTIKI = ../..
endif

include $(CONTIKI)/Makefile.include