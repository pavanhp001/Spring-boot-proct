COMMENT ON TABLE V_tx.om_job IS 'Job information table which is used to store job scheduled for Fulfillment app and external processes';

COMMENT ON COLUMN V_tx.om_job.id IS 'Primary key';
COMMENT ON COLUMN V_tx.om_job.context IS 'context for the job';
COMMENT ON COLUMN V_tx.om_job.is_locked IS 'Is job locked or not';
COMMENT ON COLUMN V_tx.om_job.ttl IS 'time to leave';
COMMENT ON COLUMN V_tx.om_job.resource_parent_id IS 'parent id of the resource';
COMMENT ON COLUMN V_tx.om_job.resource_ext_id IS 'resource external id';
COMMENT ON COLUMN V_tx.om_job.status IS 'Status of the job';
COMMENT ON COLUMN V_tx.om_job.flow_state IS 'Flow state of the job';
COMMENT ON COLUMN V_tx.om_job.login_id IS 'Login id';
COMMENT ON COLUMN V_tx.om_job.priority IS 'Priority of the job';
COMMENT ON COLUMN V_tx.om_job.transaction_id IS 'Transaction id';
COMMENT ON COLUMN V_tx.om_job.trans_status IS 'Transaction status';
COMMENT ON COLUMN V_tx.om_job.status_queued IS '';
COMMENT ON COLUMN V_tx.om_job.external_id IS 'Job external id';
COMMENT ON COLUMN V_tx.om_job.desc1 IS 'Job description 1';
COMMENT ON COLUMN V_tx.om_job.desc2 IS 'Job description 2';
COMMENT ON COLUMN V_tx.om_job.reason IS 'Reason';
COMMENT ON COLUMN V_tx.om_job.locked_at IS 'Time when job was locked';
COMMENT ON COLUMN V_tx.om_job.effective_from_on IS 'Effective time from ';
COMMENT ON COLUMN V_tx.om_job.effective_to_on IS 'Effective time on';
COMMENT ON COLUMN V_tx.om_job.customer_id IS 'Reference to customer';
