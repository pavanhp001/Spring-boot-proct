package com.A.ui.service.auth.impl;

import java.util.HashMap;
import java.util.Map;

import com.A.ui.constants.Constants;
import com.A.ui.service.auth.Permission;
public enum AuthorizationRepository {

	INSTANCE;
	
	public static Map<String, Permission> data = new HashMap<String, Permission>();
	
	static {
		data.put(Constants.CARE_MODULE_NAME, Permission.view);
		data.put(Constants.FULFILLMENT_MODULE_NAME, Permission.view);
		data.put(Constants.METRICS_MODULE_NAME, Permission.view);
		
		data.put("queue.new", Permission.view);
		data.put("queue.hold", Permission.view);
		data.put("queue.pending", Permission.view);
		data.put("queue.jeopardy", Permission.view);
		data.put("help", Permission.view);
		
		data.put("search.basic", Permission.execute);
		data.put("search.adv", Permission.execute);
		// First Energy Search
		data.put("search.fes", Permission.execute);
		
		data.put("name", Permission.view);
		data.put("identification", Permission.view);
		data.put("price", Permission.view);
		data.put("ssn", Permission.mask);
		data.put("dob", Permission.view);
		data.put("notes", Permission.view);
		data.put("options", Permission.view);
		data.put("financial", Permission.view);
		data.put("contact", Permission.view);
		data.put("address", Permission.view);
		data.put("address.role", Permission.view);
		data.put("account", Permission.view);
		data.put("payment", Permission.mask);
		data.put("paymentevent", Permission.mask);
		
		data.put("order", Permission.view);
		data.put("orders", Permission.browse);
		data.put("lineitem", Permission.view);
		data.put("schedule", Permission.view);
		data.put("email", Permission.execute);
		
		data.put("status", Permission.view);
		data.put("status.update", Permission.execute);
		data.put("status.cancel", Permission.execute);
		data.put("status.confirm", Permission.execute);
		data.put("status.sales", Permission.execute);
		data.put("status.delete", Permission.execute);
		data.put("status.submit", Permission.execute);
		data.put("status.hold", Permission.execute);
		data.put("status.provision", Permission.execute);
		
		data.put("user.new", Permission.create);
      	data.put("user.list", Permission.view);
      	data.put("user.status", Permission.edit);
      	data.put("user.login", Permission.edit);
      	data.put("user.delete", Permission.execute);
      	data.put("user.clone", Permission.execute);
      	data.put("user.edit", Permission.execute);
      	data.put("role.new", Permission.create);
      	data.put("role.list", Permission.view);
      	data.put("role.delete", Permission.execute);
      	data.put("role.edit", Permission.execute);
      	data.put("resource.new", Permission.create);
      	data.put("resource.list", Permission.view);
      	data.put("resource.delete", Permission.execute);
      	data.put("resource.edit", Permission.execute);
      	data.put("permission.new", Permission.create);
      	data.put("permission.list", Permission.view);
      	data.put("entity.new", Permission.create);
      	data.put("entity.list", Permission.view);
      	data.put("context.new", Permission.create);
      	data.put("context.list", Permission.view);
      	
      	data.put("cache.clear", Permission.none);
      	data.put("rule.new", Permission.none);
      	data.put("rule.list", Permission.none);
      	data.put("rule.edit", Permission.none);
      	data.put("rule.search", Permission.none);
      	data.put("rule.upload", Permission.none);
      	
      	data.put("ruleset.new", Permission.none);
      	data.put("ruleset.assign", Permission.none);
      	data.put("prod.new", Permission.none);
      	data.put("category.new", Permission.none);
      	
      	data.put("uam.upload", Permission.view);
      	
      	data.put("giou", Permission.view);
      	data.put("goob", Permission.view);
      	
      	//This is for session review tool search
      	data.put("srt", Permission.view);
      	
      	//This is for Sales Gross Commissionable Revenue
      	data.put("gcr", Permission.view);
      	
      	//for external process redesign
      	data.put("process", Permission.execute);
      	
        //for agent grouping
      	data.put("agentGroup", Permission.execute);
		data.put("agentGroup.new", Permission.create);
      	data.put("agentGroup.dashboard", Permission.view);
      	data.put("agentGroup.bulkUpdate", Permission.view);
      	
        //for Channel Line up
      	data.put("channelLineup", Permission.execute);
		data.put("channelLineup.channel", Permission.view);
      	data.put("channelLineup.provider", Permission.view);
      	data.put("channelLineup.categories", Permission.view);
      	
      	//for pending user feedback
      	data.put("puf.template", Permission.view);
		data.put("puf.template.new", Permission.create);
      	data.put("puf.template.edit", Permission.edit);
      	data.put("puf.template.delete", Permission.delete);
      	data.put("puf.template.search", Permission.execute);
      	
      	//for PUF Submit in encore
      	data.put("submit.puf", Permission.execute);
      	
      	//for Instant message functionality in VAT
      	data.put("agent.message", Permission.view);
		data.put("red.message", Permission.create);
      	data.put("green.message", Permission.create);
		data.put("yellow.message", Permission.create);
      	data.put("blue.message", Permission.create);
	}
	
	public Permission get(final String moduleName) {
		Permission p = data.get(moduleName);
		
		if (p == null) {
			return Permission.none;
		}
		
		return p;
	}

	public static Map<String, Permission> getData() {
		return data;
	}

	public static void setData(Map<String, Permission> data) {
		AuthorizationRepository.data = data;
	}
	
}
