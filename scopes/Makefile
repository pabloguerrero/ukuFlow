CONTIKI_PROJECT = tester/tester tester/tester_parallel
SIZE = msp430-size
CLOC = cloc
DOX = doxygen
DOXYGEN_CONF = doc/scopes.doxyfile

all: $(CONTIKI_PROJECT)
	$(DOX) $(DOXYGEN_CONF)
	$(CLOC) *.c *.h tester/*.c
	$(SIZE) tester/tester.z1 tester/tester_parallel.z1

ifndef TARGET
  TARGET = z1
endif

# Contiki's flags:
CFLAGS += -DPROJECT_CONF_H=\"project-conf.h\"
CFLAGS += -DDEBUG_DEPTH=1

APPS = data-mgr

SRCS                += bitvector.c frag-unicast.c scopes.c scopes-selfur.c scopes-membership-simple.c # scopes-flooding.c 

PROJECT_OBJECTFILES += bitvector.o frag-unicast.o scopes.o scopes-selfur.o scopes-membership-simple.o # scopes-flooding.o
PROJECT_LIBRARIES   += bitvector.o frag-unicast.o scopes.o scopes-selfur.o scopes-membership-simple.o # scopes-flooding.o

ifndef CONTIKI
  CONTIKI = ../..
endif

include $(CONTIKI)/Makefile.include
