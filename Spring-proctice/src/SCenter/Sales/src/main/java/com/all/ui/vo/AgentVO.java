package com.AL.ui.vo;

import java.io.Serializable;

public class AgentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String agentId;
	private String callCenter;
	private String agentName;
	private String region;
	private Double GRPC;
	private Double GRPO;
	private Integer enterpriseRank;
	private Integer callCenterRank;
	private Integer regionRank;
	private Double GCRevenue;
	private Integer totalCallsAnswered;
	private String supervisor;
	/**
	 * @return the supervisor
	 */
	public String getSupervisor() {
		return supervisor;
	}
	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	/**
	 * @return the callCenter
	 */
	public String getCallCenter() {
		return callCenter;
	}
	/**
	 * @param callCenter the callCenter to set
	 */
	public void setCallCenter(String callCenter) {
		this.callCenter = callCenter;
	}
	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the gRPC
	 */
	public Double getGRPC() {
		return GRPC;
	}
	/**
	 * @param gRPC the gRPC to set
	 */
	public void setGRPC(Double gRPC) {
		GRPC = gRPC;
	}
	/**
	 * @return the gRPO
	 */
	public Double getGRPO() {
		return GRPO;
	}
	/**
	 * @param gRPO the gRPO to set
	 */
	public void setGRPO(Double gRPO) {
		GRPO = gRPO;
	}
	/**
	 * @return the enterpriseRank
	 */
	public Integer getEnterpriseRank() {
		return enterpriseRank;
	}
	/**
	 * @param enterpriseRank the enterpriseRank to set
	 */
	public void setEnterpriseRank(Integer enterpriseRank) {
		this.enterpriseRank = enterpriseRank;
	}
	/**
	 * @return the callCenterRank
	 */
	public Integer getCallCenterRank() {
		return callCenterRank;
	}
	/**
	 * @param callCenterRank the callCenterRank to set
	 */
	public void setCallCenterRank(Integer callCenterRank) {
		this.callCenterRank = callCenterRank;
	}
	/**
	 * @return the regionRank
	 */
	public Integer getRegionRank() {
		return regionRank;
	}
	/**
	 * @param regionRank the regionRank to set
	 */
	public void setRegionRank(Integer regionRank) {
		this.regionRank = regionRank;
	}
	/**
	 * @return the gCRevenue
	 */
	public Double getGCRevenue() {
		return GCRevenue;
	}
	/**
	 * @param gCRevenue the gCRevenue to set
	 */
	public void setGCRevenue(Double gCRevenue) {
		if (gCRevenue != 0.0){
			//GCRevenue = gCRevenue / 5;	
			GCRevenue = gCRevenue;	
		}else {
			GCRevenue = gCRevenue;
		}
		
	}
	/**
	 * @return the totalCallsAnswered
	 */
	public Integer getTotalCallsAnswered() {
		return totalCallsAnswered;
	}
	/**
	 * @param totalCallsAnswered the totalCallsAnswered to set
	 */
	public void setTotalCallsAnswered(Integer totalCallsAnswered) {
		this.totalCallsAnswered = totalCallsAnswered;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgentVO [agentId=" + agentId + ", callCenter=" + callCenter + ", agentName=" + agentName + ", region="
				+ region + ", GRPC=" + GRPC + ", GRPO=" + GRPO + ", enterpriseRank=" + enterpriseRank
				+ ", callCenterRank=" + callCenterRank + ", regionRank=" + regionRank + ", GCRevenue=" + GCRevenue
				+ ", totalCallsAnswered=" + totalCallsAnswered + ", supervisor=" + supervisor + "]";
	}

}