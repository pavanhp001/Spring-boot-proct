ALTER TABLE CM_CONSUMER  ADD (HOME_PHONE_VALUE VARCHAR2(30) );
ALTER TABLE CM_CONSUMER  ADD (CELL_PHONE_VALUE VARCHAR2(30) );
ALTER TABLE CM_CONSUMER  ADD (WORK_PHONE_VALUE VARCHAR2(30) );
ALTER TABLE CM_CONSUMER  ADD (HOME_EMAIL_VALUE VARCHAR2(120) );
ALTER TABLE CM_CONSUMER  ADD (WORK_EMAIL_VALUE VARCHAR2(120) );

ALTER TABLE OM_JOB  ADD (EXTERNAL_ID VARCHAR2(60) );
ALTER TABLE OM_JOB  ADD (LOCKED_AT DATE default null );


ALTER TABLE OM_JOB  ADD (DESC2 VARCHAR2(120)   );
ALTER TABLE OM_JOB  ADD (DESC1 VARCHAR2(120)   );

ALTER TABLE OM_LINE_ITEM MODIFY INSTALLATION_FEE DEFAULT '0';