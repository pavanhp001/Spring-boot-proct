update V_tx.cm_consumer set firstname = LOWER(firstname), lastname = LOWER(lastname);

update V_tx.cm_address set street_name = LOWER(street_name), line2 = LOWER(line2), city = LOWER(city), state_or_prov = LOWER(state_or_prov);

commit;