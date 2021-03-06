CREATE TABLE  OM_JOB ( 
    ID                	NUMBER NOT NULL,
    CONTEXT           	VARCHAR2(100) NULL,
    IS_LOCKED         	NUMBER NULL,
    TTL               	NUMBER NULL,
    RESOURCE_PARENT_ID	VARCHAR2(20) NULL,
    RESOURCE_EXT_ID   	VARCHAR2(20) NULL,
    STATUS            	NUMBER NULL,
    FLOW_STATE        	NUMBER NULL,
    LOGIN_ID          	VARCHAR2(50) NULL,
    EFFECTIVE_FROM_ON 	DATE NULL,
    EFFECTIVE_TO_ON   	DATE NULL,
    PRIORITY          	NUMBER NULL,
    TRANSACTION_ID    	VARCHAR2(40) NULL,
    TRANS_STATUS      	VARCHAR2(1) NULL,
    STATUS_QUEUED     	VARCHAR2(30) NULL,
    EXTERNAL_ID       	VARCHAR2(60) NULL,
    LOCKED_AT         	DATE DEFAULT null NULL,
    DESC2             	VARCHAR2(120) NULL,
    DESC1             	VARCHAR2(120) NULL 
);
 

ALTER TABLE OM_JOB
ADD CONSTRAINT OM_JOB_PK PRIMARY KEY 
(
  ID 
)
ENABLE;


CREATE SEQUENCE OM_JOB_SEQ INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
