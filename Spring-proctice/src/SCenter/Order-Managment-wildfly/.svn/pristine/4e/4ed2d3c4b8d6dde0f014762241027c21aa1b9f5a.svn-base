CREATE TABLE om_job
(
  id NUMERIC(19,0) NOT NULL
, context character varying(100)
, is_locked BOOLEAN
, ttl NUMERIC
, resource_parent_id character varying(20)
, resource_ext_id character varying(20)
, status NUMERIC
, flow_state NUMERIC
, login_id character varying(50)
, effective_from_on DATE
, effective_to_on DATE
, priority NUMERIC
, transaction_id character varying(40)
, trans_status character varying(1)
, status_queued character varying(30)
);

ALTER TABLE om_job ADD CONSTRAINT pk_om_job PRIMARY KEY(id);

CREATE SEQUENCE om_job_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;

CREATE INDEX idx_om_stat_rec_element   ON om_stat_rec_reasons(element);
CREATE INDEX idx_li_stat_rec_ele  ON om_line_item_om_stat_rec(om_line_item_id,historicstatus_id);
CREATE INDEX idx_om_so_li ON om_sales_order_om_line_item(om_sales_order_id, lineitems_id);

ALTER TABLE om_line_item ADD COLUMN new_phone character varying(14);

CREATE TABLE om_li_attribute
(
  id BIGINT NOT NULL
, name character varying(500)
, value character varying(500)
, description character varying(4000)
, line_item_id BIGINT
, source character varying(50)
);

ALTER TABLE om_li_attribute ADD CONSTRAINT pk_om_line_item_attribute PRIMARY KEY(id);

CREATE SEQUENCE om_line_item_attribute_seq INCREMENT BY 1 START WITH 100 CACHE 20;


CREATE TABLE om_line_item_om_li_attribute
(
  om_line_item_id BIGINT
, lineitemattribute_id BIGINT
);

ALTER TABLE om_line_item_om_li_attribute ADD CONSTRAINT fk_om_line_item_om_li_attrib FOREIGN KEY(om_line_item_id) REFERENCES om_line_item(id);

ALTER TABLE om_sales_order_context ADD COLUMN entity_name character varying(100);



CREATE TABLE OM_SYSTEM_REASON
(
  ID BIGINT NOT NULL
, CODE character varying(20)
, DESCRIPTION character varying(120)
, CREATED_ON DATE DEFAULT CURRENT_TIMESTAMP
, UPDATED_ON DATE DEFAULT CURRENT_TIMESTAMP
, CONTEXT character varying(20) DEFAULT ''
);


ALTER TABLE OM_SYSTEM_REASON ADD CONSTRAINT id_unique UNIQUE(ID);
ALTER TABLE OM_SYSTEM_REASON ADD CONSTRAINT code_unique UNIQUE(CODE);





