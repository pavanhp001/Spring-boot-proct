COMMENT ON TABLE V_tx.om_rule_sets IS 'Store information about rule sets';

COMMENT ON COLUMN V_tx.om_rule_sets.id IS 'Primary key';

COMMENT ON COLUMN V_tx.om_rule_sets.context IS 'Rule context name';
COMMENT ON COLUMN V_tx.om_rule_sets.effective_from_on IS 'Date from which this rule will be active';
COMMENT ON COLUMN V_tx.om_rule_sets.effective_to_on IS 'Date untill which this rule should be active';
COMMENT ON COLUMN V_tx.om_rule_sets.is_enabled IS 'Is rule enabled or not';
COMMENT ON COLUMN V_tx.om_rule_sets.line_item_detail_ex_id IS 'reference to lineitem detail external id';
COMMENT ON COLUMN V_tx.om_rule_sets.name IS 'Name of the rule';
COMMENT ON COLUMN V_tx.om_rule_sets.provider IS 'Provider';
COMMENT ON COLUMN V_tx.om_rule_sets.source IS 'Source';
COMMENT ON COLUMN V_tx.om_rule_sets.source_context IS 'Source context';
COMMENT ON COLUMN V_tx.om_rule_sets.rulestorage_id IS 'Storage id of the rule';
