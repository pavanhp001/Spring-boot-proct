COMMENT ON TABLE V_tx.cm_payevent IS 'Payment information';

COMMENT ON COLUMN V_tx.cm_payevent.id IS 'Primary key';
COMMENT ON COLUMN V_tx.cm_payevent.amount IS 'Amount for this payment';
COMMENT ON COLUMN V_tx.cm_payevent.billing_info_ex_id IS 'Billing info external id for this payment.';
COMMENT ON COLUMN V_tx.cm_payevent.confirm_num IS 'confirmation number';
COMMENT ON COLUMN V_tx.cm_payevent.event_type IS 'Type of payment event';
COMMENT ON COLUMN V_tx.cm_payevent.lineitem_id IS 'Lineitem id associated for this payment';
COMMENT ON COLUMN V_tx.cm_payevent.order_id IS 'Order id associated for this payment';
COMMENT ON COLUMN V_tx.cm_payevent.pay_status IS 'Payment status';
COMMENT ON COLUMN V_tx.cm_payevent.trans_date IS 'Transfer date';
COMMENT ON COLUMN V_tx.cm_payevent.external_id IS 'System generated unique id';
COMMENT ON COLUMN V_tx.cm_payevent.currentstatus_id IS 'Current payment statsu';
COMMENT ON COLUMN V_tx.cm_payevent.consumer_id IS 'Reference to customer';
COMMENT ON COLUMN V_tx.cm_payevent.cc_disclosure IS 'Disclosure';
