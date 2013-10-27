/**
 * \file
 *         ukuflow shell
 * \author
 *         Pablo Guerrero <guerrero@dvs.tu-darmstadt.de>
 */

#include <stdio.h>
#include <string.h>

#include "contiki.h"
#include "apps/shell/shell.h"
#include "apps/serial-shell/serial-shell.h"

#include "testcmd.h"


/*---------------------------------------------------------------------------*/
PROCESS(ukuflow_shell_process, "ukuflow shell");
AUTOSTART_PROCESSES(&ukuflow_shell_process);
PROCESS_THREAD(ukuflow_shell_process, ev, data)
{
  PROCESS_BEGIN();

  serial_shell_init();
  testcmd_init();
  
  PROCESS_END();
}
/*---------------------------------------------------------------------------*/
