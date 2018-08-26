package com.A.Vdao.util;

import java.util.Set;

import com.truemesh.squiggle.Matchable;
import com.truemesh.squiggle.Table;
import com.truemesh.squiggle.output.Output;

/**
 * Customer class to use function around columns.
 *
 * For eg. lower(tableName.colName)
 *
 * @author PPatel
 *
 */
public class LeftMatchable implements Matchable {

	private String tableName;
	private String colName;
	private String function;
	private String[] args;


	public LeftMatchable(String tableName, String colName, String function, String... args){
		this.tableName = tableName;
		this.colName = colName;
		this.function = function;
		this.args = args;
	}

	public void write(Output out) {
		if(function.equalsIgnoreCase("lower")){
			out.print(function + "( ");
			out.print(tableName+"."+colName);
			out.print(")");
		}else if(function.equalsIgnoreCase("replace")){
			out.print(function +"(");
			out.print(tableName+"."+colName + "," + args[0] + "," + args[1]);
			out.print(")");
		}

	}

	public void addReferencedTablesTo(Set<Table> tables) {
		if (tables.size() == 0) {
			Table t = new Table("cm_consumer");
			tables.add(t);
		}
	}

}
