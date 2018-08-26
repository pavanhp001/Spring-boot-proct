package com.AL.ui.domain.dynamic.dialogue;

import java.util.ArrayList;
import java.util.List;

public class DataFieldMatrix {

    protected List<DataFieldDependency> dependency;


    public List<DataFieldDependency> getDependencyList() {
        if (dependency == null) {
            dependency = new ArrayList<DataFieldDependency>();
        }
        return this.dependency;
    }


	public void setDependency(List<DataFieldDependency> dependency) {
		this.dependency = dependency;
	}
    
    

}
