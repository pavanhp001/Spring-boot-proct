package com.AL.ome.marshall;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.AL.V.beans.entity.SalesOrderContext;

public class SalesOrderContextTestF {
	public static void main(String[] args) {
		Set<SalesOrderContext> set1 = new HashSet<SalesOrderContext>();
		Set<SalesOrderContext> set2 = new HashSet<SalesOrderContext>();
		Set<SalesOrderContext> finalSet = new HashSet<SalesOrderContext>();

		set1 = populateSet1(set1);
		set2 = populateSet2(set2);

		Iterator<SalesOrderContext> oldItr = set1.iterator();
		while(oldItr.hasNext()){
			SalesOrderContext oldCtx = oldItr.next();
			for(SalesOrderContext newCtx : set2){
				if(oldCtx.getEntityName().equalsIgnoreCase(newCtx.getEntityName()) && oldCtx.getName().equalsIgnoreCase(newCtx.getName())){
					//finalSet.add(newCtx);
					oldItr.remove();
				}
			}
		}
		finalSet.addAll(set1);
		finalSet.addAll(set2);
		System.out.println("Total Contexts : "  + finalSet.size());
		for(SalesOrderContext ctx : finalSet){
			System.out.println(ctx);
		}
	}

	private static Set<SalesOrderContext> populateSet1(
			Set<SalesOrderContext> set1) {
		set1.add(getContext(1L,"harmony", "source", "accord"));
		set1.add(getContext(2L,"DW", "DW1", "DWV1"));
		set1.add(getContext(2L,"DW", "DW3", "DWV3"));
		return set1;
	}

	private static Set<SalesOrderContext> populateSet2(
			Set<SalesOrderContext> set2) {

		//set2.add(getContext(1L,"harmony", "source", "accord"));
		set2.add(getContext(2L,"DW", "DW2", "DWV2"));
		set2.add(getContext(2L,"DW", "DW1", "NewDWV1"));
		set2.add(getContext(2L,"OME", "OME1", "OMEV1"));
		return set2;
	}

	public static SalesOrderContext getContext(Long id, String entity, String name, String value){
		SalesOrderContext ctx = new SalesOrderContext();
		ctx.setId(id);
		ctx.setEntityName(entity);
		ctx.setName(name);
		ctx.setValue(value);
		return ctx;
	}
}
