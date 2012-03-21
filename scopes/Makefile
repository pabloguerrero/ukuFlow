CONTIKI_PROJECT = tester/tester tester/tester_parallel
DOX = doxygen
DOXYGEN_CONF = doc/scopes.doxyfile

all: $(CONTIKI_PROJECT)
	$(DOX) $(DOXYGEN_CONF)

ifndef TARGET
  TARGET = sky
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
