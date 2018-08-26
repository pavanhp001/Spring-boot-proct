create table WEB_LOOKUP(
	id bigint not null AUTO_INCREMENT,
	CONTEXT bigint,
	EFFECTIVE_FROM_ON date,
	EFFECTIVE_TO_ON date,
	DESCRIPTION varchar(255),
	IS_ENABLED bit, 
	LOOKUP_KEY varchar(255), 
	primary key (id)
) ;