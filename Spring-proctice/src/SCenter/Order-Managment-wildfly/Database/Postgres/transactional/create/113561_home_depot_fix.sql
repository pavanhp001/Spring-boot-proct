update V_tx.om_job set status=1,trans_status =null, transaction_id=null
where context='18486164' and trans_status='P';

commit;