ALTER TABLE cm_consumer ADD COLUMN ssn_last_four character varying(4);

ALTER TABLE CM_BILLING_INFORMATION DROP COLUMN EXPIRATION_DATE;
ALTER TABLE CM_BILLING_INFORMATION ADD COLUMN EXPIRE_MONTH character varying(1000);
ALTER TABLE CM_BILLING_INFORMATION ADD COLUMN EXPIRE_YEAR character varying(1000);

ALTER TABLE CM_PAYEVENT DROP COLUMN CVV;

ALTER TABLE cm_billing_information ALTER COLUMN credit_card_number TYPE character varying(1000);
ALTER TABLE cm_billing_information ALTER COLUMN card_holder_name TYPE character varying(1000);
ALTER TABLE cm_consumer ADD COLUMN referrer_general_name character varying(255);


