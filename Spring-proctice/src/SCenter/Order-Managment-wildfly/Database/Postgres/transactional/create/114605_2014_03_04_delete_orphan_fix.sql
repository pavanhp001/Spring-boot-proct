ALTER TABLE om_sel_feature ADD COLUMN is_active boolean DEFAULT true;

ALTER TABLE om_sel_feature ADD COLUMN feature_date timestamp without time zone;

create index concurrently idx_sf_activeflag on V_tx.om_sel_feature (is_active,feature_date);





