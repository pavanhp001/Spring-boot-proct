package com.A.Vdao.util;

import java.util.Set;

import com.truemesh.squiggle.Matchable;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.output.Output;

public class RightMatchable implements Matchable {

	private static final String LOWER = "lower";
	private static final String REPLACE = "replace";
	private String colValue;
	private String action;
	private Boolean addWildCard;
	private String[] args;


	public RightMatchable(String colValue, String action, Boolean addWildCard, String... args){
		this.colValue = colValue;
		this.action = action;
		this.addWildCard = addWildCard;
		this.args = args;
	}
	public void write(Output out) {
		if(action.equalsIgnoreCase(LOWER)){
			out.print("'");
			out.print(colValue.toLowerCase());
			if(addWildCard){
				out.print("%'");
			}else{
				out.print("'");
			}

		}else if(action.equalsIgnoreCase(REPLACE)){
			out.print("'");
			out.print(colValue.replace(args[0], ""));
			out.print("'");
		}
	}

	public void addReferencedTablesTo(Set<Table> tables) {
		if (tables.size() == 0) {
			Table t = new Table("cm_consumer");
			tables.add(t);
		}

	}

}
