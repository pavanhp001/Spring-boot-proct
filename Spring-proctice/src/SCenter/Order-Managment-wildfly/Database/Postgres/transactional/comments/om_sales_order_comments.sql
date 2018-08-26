COMMENT ON TABLE V_tx.om_sales_order IS 'Sales order Information';

COMMENT ON COLUMN V_tx.om_sales_order.id IS 'Primary key, Autogenerated id';
COMMENT ON COLUMN V_tx.om_sales_order.agent_id IS 'Agent, who has created order';
COMMENT ON COLUMN V_tx.om_sales_order.all_connect_acct_num IS 'Customers AL Account number';
COMMENT ON COLUMN V_tx.om_sales_order.all_connect_confirm_num IS 'Customers AL Confirmation number';
COMMENT ON COLUMN V_tx.om_sales_order.is_associated_with_move IS '';
COMMENT ON COLUMN V_tx.om_sales_order.campaign_id IS 'Campaign id';
COMMENT ON COLUMN V_tx.om_sales_order.cust_ext_id IS 'Reference to Customer External Id';
COMMENT ON COLUMN V_tx.om_sales_order.external_id IS 'System generated unique id';
COMMENT ON COLUMN V_tx.om_sales_order.move_on IS 'Move date';
COMMENT ON COLUMN V_tx.om_sales_order.order_date IS 'Order created Date & Time';
COMMENT ON COLUMN V_tx.om_sales_order.referrer_id IS 'Utility partner who transferred customer to AL';
COMMENT ON COLUMN V_tx.om_sales_order.source IS 'Source of order creation. Web, call center etc';
COMMENT ON COLUMN V_tx.om_sales_order.basenonrecurringprice IS 'Non-recurring price';
COMMENT ON COLUMN V_tx.om_sales_order.baserecurringprice IS 'Recurring price';
COMMENT ON COLUMN V_tx.om_sales_order.when_to_call_back_at IS 'Call back Date & Time';
COMMENT ON COLUMN V_tx.om_sales_order.current_status_id IS 'Reference to cusrrent lineitem status id';
COMMENT ON COLUMN V_tx.om_sales_order.non_recur_price_u IS 'Non-recurring price unit, DEFAULT USD';
COMMENT ON COLUMN V_tx.om_sales_order.recur_price_u IS 'Recurring price unit, DEFAULT USD';