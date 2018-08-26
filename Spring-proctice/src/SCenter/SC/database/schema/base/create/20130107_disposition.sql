create table DISPOSITION_CATEGORY
(
  CATEGORYID              bigint NOT NULL,
  CATEGORY_NAME           character varying(60),
  CONSTRAINT DISPOSITION_CATEGORY_pkey PRIMARY KEY (CATEGORYID)
);

create table DISPO_CAT_ASSOC
(
  DISPOSITIONID           bigint NOT NULL,
  CATEGORYID              bigint,
  CONSTRAINT DISPO_CAT_ASSOC_pkey PRIMARY KEY (DISPOSITIONID)
);

create table DISPOSITION
(
  DISPOSITIONID           bigint NOT NULL,
  CODE                    character varying(16),
  DESCRIPTION             character varying(60),
  ACTIVE                  integer,
  SEQUENCE_ORDER          bigint,
  CONSTRAINT DISPOSITION_pkey PRIMARY KEY (DISPOSITIONID)
);


CREATE TABLE SALES_SESSION
(
  SALES_SESSION_ID              BIGINT auto_increment,
  ORDERID                       BIGINT,
  CUSTOMERID                    BIGINT,
  AGENT                         CHARACTER VARYING(20),
  DISPOSITION_ID                BIGINT,
  START_TIME                    timestamp,
  END_TIME                      timestamp,
  SALES_CALL_ID                 BIGINT,
  ID                            BIGINT,
  CONSTRAINT SALES_SESSION_pkey PRIMARY KEY (SALES_SESSION_ID)
);

CREATE TABLE SALES_CALL
(
  SALES_CALL_ID                 BIGINT AUTO_INCREMENT,
  SALES_SESSION_ID              BIGINT,
  AGENT_PHONEID                 CHARACTER VARYING(20),
  AGENT                         CHARACTER VARYING(20),
  CALLED_ADDRESS                CHARACTER VARYING(60),
  CALLING_ADDRESS               CHARACTER VARYING(60),
  UEC                           BIGINT,
  UCID                          BIGINT,
  ALERTING_TIME                 TIMESTAMP,
  ESTABLISHED_TIME              TIMESTAMP,
  DISCONNECT_TIME               TIMESTAMP,
  CONSTRAINT SALES_CALL_pkey PRIMARY KEY (SALES_CALL_ID)
);

CREATE INDEX SALES_SESSION_IX1 ON SALES_SESSION(SALES_SESSION_ID);
CREATE INDEX SALES_SESSION_IX2 ON SALES_SESSION(ORDERID);
CREATE INDEX SALES_SESSION_IX3 ON SALES_SESSION(CUSTOMERID);