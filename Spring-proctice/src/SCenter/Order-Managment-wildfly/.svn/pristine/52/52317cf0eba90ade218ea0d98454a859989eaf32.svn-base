create index concurrently cm_cust_refid_idx01 on V_tx.cm_consumer (referrer_id);

create index concurrently om_stat_idx01 on V_tx.om_stat_rec (status);

create index concurrently om_stat_date_idx01 on V_tx.om_stat_rec (created_at);

create index concurrently om_so_custid_idx01 on V_tx.om_sales_order (cust_ext_id);

create index concurrently cm_custaddr_role_idx01 on V_tx.cm_consumer_address (address_role);

create index concurrently om_li_dtlid_idx01 on V_tx.om_line_item (lineitem_dtl_id);

create index concurrently om_lidtl_cat_idx01 on V_tx.om_line_item_dtl (category);

create index concurrently om_lidtl_extid_idx01 on V_tx.om_line_item_dtl (lineitem_detail_ext_id);

create index concurrently cm_intn_liextid_idx01 on V_tx.cm_customer_interaction (li_ext_id);

create index concurrently cm_attribs_idx01 on V_tx.cm_cust_attribute (name,value);

create index concurrently cm_addr_extid_idx01 on V_tx.cm_address (external_id);

create index concurrently cm_hphone_idx01 on V_tx.cm_consumer (home_phone_value);

create index concurrently cm_cphone_idx01 on V_tx.cm_consumer (cell_phone_value);

create index concurrently cm_wphone_idx01 on V_tx.cm_consumer (work_phone_value);

create index concurrently cm_secphone_idx01 on V_tx.cm_consumer (second_phone);

create index concurrently cm_lastssn_idx01 on V_tx.cm_consumer (ssn_last_four);

create unique index concurrently pk_address_id on V_tx.cm_address (address_id);

ALTER TABLE cm_address ADD CONSTRAINT pk_address_id PRIMARY KEY USING INDEX pk_address_id;
