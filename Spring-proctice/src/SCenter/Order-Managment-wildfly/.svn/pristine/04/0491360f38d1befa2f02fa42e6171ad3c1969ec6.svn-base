CREATE TABLE V_tx.om_broadcast
(
   id bigint NOT NULL,
   broadcast_type character varying(50),
   message_headers character varying(250),
   transaction_type character varying(50),
   broadcast_message text,
   ccp_status character varying(1),
   dwme_status character varying(1),
   dmadapter_status character varying(1),
   guid character varying(200),
   external_id bigint NOT NULL,
   broadcast_date timestamp without time zone,
   CONSTRAINT pk_broadcast_id PRIMARY KEY (id)
)
WITH (
  OIDS = FALSE
)
;

CREATE SEQUENCE V_tx.om_broadcast_seq INCREMENT BY 1 START WITH 1 CACHE 2;

create index concurrently om_broad_extid_idx01 on V_tx.om_broadcast(external_id);
create index concurrently om_broad_guid_idx01 on V_tx.om_broadcast(guid);
create index concurrently om_broad_dt_idx01 on V_tx.om_broadcast(broadcast_date);