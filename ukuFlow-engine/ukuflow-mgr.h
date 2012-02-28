/*
 * ukuflow-wf-mgr.h
 *
 *  Created on: Jul 14, 2011
 *      Author: guerrero
 */

#ifndef __UKUFLOW_MGR_H__
#define __UKUFLOW_MGR_H__

#include "workflow-types.h"
#include "ukuflow-engine.h"

/*---------------------------------------------------------------------------*/
void ukuflow_mgr_init();
/*---------------------------------------------------------------------------*/
void ukuflow_mgr_register(struct workflow *wf);
/*---------------------------------------------------------------------------*/


#endif /* __UKUFLOW_MGR_H__ */
