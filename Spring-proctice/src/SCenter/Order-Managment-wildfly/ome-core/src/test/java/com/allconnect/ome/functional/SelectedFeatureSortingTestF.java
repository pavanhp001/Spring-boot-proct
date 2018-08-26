package com.AL.ome.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.AL.V.beans.entity.SelectedFeatureValue;

public class SelectedFeatureSortingTestF {

	public static void main(String[] args) {
		List<SelectedFeatureValue> srcFeatureBeanList = getSampleSet();

		/*List<SelectedFeatureValue> srcFeatureBeanList = new ArrayList<SelectedFeatureValue>(featureSet);
		Collections.sort(srcFeatureBeanList, new Comparator<SelectedFeatureValue>() {

			public int compare(SelectedFeatureValue o1, SelectedFeatureValue o2) {
				if(o1.getParentNode() == null){
					return -1;
				}else if(o1.getParentNode().getExternalId() == o2.getExternalId()){
					return 0;
				}else{
					return 1;
				}
			}
		});*/

		print(srcFeatureBeanList);
	}

	private static List<SelectedFeatureValue> getSampleSet() {
		List<SelectedFeatureValue> featureSet = new ArrayList<SelectedFeatureValue>();

		SelectedFeatureValue parent1 = new SelectedFeatureValue();
		parent1.setExternalId("parent1");

		SelectedFeatureValue parent2 = new SelectedFeatureValue();
		parent2.setExternalId("parent2");

		SelectedFeatureValue child1 = new SelectedFeatureValue();
		child1.setExternalId("child1_parent2");
		child1.setParentNode(parent2);

		SelectedFeatureValue parent3 = new SelectedFeatureValue();
		parent3.setExternalId("parent3");

		featureSet.add(parent1);
		featureSet.add(parent2);
		featureSet.add(child1);
		featureSet.add(parent3);



		return featureSet;
	}

	public static void print(List<SelectedFeatureValue> featureList){
		for (SelectedFeatureValue value : featureList) {
			System.out.println(value.getExternalId());
		}
	}
}
