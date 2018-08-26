ALTER TABLE V_tx.om_line_item ALTER COLUMN event_type SET DEFAULT 0;

update V_tx.om_line_item set event_type=0 where event_type is null;
commit;



