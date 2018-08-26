package com.A.ui.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.A.ui.dao.MetricDao;
import com.A.ui.domain.Metric;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Component
public class MetricDaoImpl implements MetricDao {

	private static final String METRIC_INSERT = "INSERT INTO WEB_METRIC (id, created_on, name, measured_value, page, provider, agent, sales_session_id) VALUES(?,?,?,?,?,?,?,?)";

	@Autowired
	@Qualifier("transactionalDatasource")
	private ComboPooledDataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {

		if (jdbcTemplate == null) {
			jdbcTemplate = new JdbcTemplate(dataSource);

		}
		return jdbcTemplate;
	}

	public void put(Metric metric) {

		getJdbcTemplate().update(
				METRIC_INSERT,
				new Object[] { null, metric.getDateEffectiveFrom(),
						metric.getName(), metric.getmValue(), metric.getPage(),
						metric.getProvider(), metric.getAgent(), metric.getSalesSessionId() });

	}
	
	public Long getMetricId() {
		return getJdbcTemplate().queryForLong("SELECT MAX(id) FROM WEB_METRIC");
	}
}
