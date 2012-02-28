/**
 * \addtogroup routing
 * @{
 */
#include "contiki-net.h"

#include "scopes-flooding.h"
#include "logger.h"

int
receive_from_rime(struct netflood_conn *s, const rimeaddr_t *from,
                      const rimeaddr_t *originator, uint8_t seqno,
                      uint8_t hops)
{
  scopes_receive(packetbuf_dataptr());
  return 1;
}

static struct netflood_conn netflood;
static struct netflood_callbacks callbacks = { receive_from_rime, NULL, NULL };
static uint8_t seqno = SCOPES_FLOODING_FIRST_SEQNO;

static void
scopes_flooding_init(void)
{
  netflood_open(&netflood, SCOPES_FLOODING_QUEUE_TIME, SCOPES_FLOODING_CHANNEL, &callbacks);
}

static void
scopes_flooding_send(scope_id_t scope_id, bool to_creator)
{
  netflood_send(&netflood, seqno++);
}

static void
scopes_flooding_add(scope_id_t scope_id, bool is_creator)
{
  PRINTF(3,"ROUTING added scope: %u\n", scope_id);
}

static void
scopes_flooding_remove(scope_id_t scope_id)
{
  PRINTF(3,"ROUTING removed scope: %u\n", scope_id);
}

static void
scopes_flooding_join(scope_id_t scope_id)
{
  PRINTF(3,"ROUTING joined scope: %u\n", scope_id);
}

static void
scopes_flooding_leave(scope_id_t scope_id)
{
  PRINTF(3,"ROUTING left scope: %u\n", scope_id);
}

static void
scopes_flooding_buffer_clear(bool to_creator) {
	packetbuf_clear();
}

static uint8_t *scopes_flooding_buffer_ptr(bool to_creator) {
	return packetbuf_dataptr();
}

static void scopes_flooding_buffer_setlen(bool to_creator, uint16_t len) {
	packetbuf_set_datalen(len);
}


ROUTING(scopes_flooding,
        "flooding",
        scopes_flooding_init,
        scopes_flooding_send,
        scopes_flooding_add,
        scopes_flooding_remove,
        scopes_flooding_join,
        scopes_flooding_leave,
        scopes_flooding_buffer_clear,
        scopes_flooding_buffer_ptr,
        scopes_flooding_buffer_setlen);
/** @} */
