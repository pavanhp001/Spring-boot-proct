package com.AL.ui;

import java.io.Serializable;
import java.util.List;

import com.AL.ui.vo.DataFeildFeatureVO;

public class DataGroupVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String subTitle;
	private List<DataFeildFeatureVO> dataFeildList;
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public List<DataFeildFeatureVO> getDataFeildList() {
		return dataFeildList;
	}
	public void setDataFeildList(List<DataFeildFeatureVO> dataFeildList) {
		this.dataFeildList = dataFeildList;
	}
	@Override
	public String toString() {
		return "DataGroupVO [subTitle=" + subTitle + ", dataFeildList="
				+ dataFeildList + "]";
	}
	
	

}
