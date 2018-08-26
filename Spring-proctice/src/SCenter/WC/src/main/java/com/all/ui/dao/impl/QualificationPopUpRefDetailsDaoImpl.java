package com.A.ui.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.A.ui.dao.QualificationPopUpRefDetailsDao;
import com.A.ui.domain.QualificationPopUpRefDetails;

@Component
public class QualificationPopUpRefDetailsDaoImpl extends BaseTransactionalJpaDao implements QualificationPopUpRefDetailsDao {
	
	private static final Logger logger = Logger.getLogger(QualificationPopUpRefDetailsDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional(value = "transactional", propagation = Propagation.REQUIRED)
	public Map<String, Map<String, QualificationPopUpRefDetails>> getAllQualificationPopUpRefDetails() {
		logger.info("In getAllQualificationPopUpRefDetails");
		Map<String, Map<String, QualificationPopUpRefDetails>> qualificationPopUpRefDetailsMap = new HashMap<String, Map<String,QualificationPopUpRefDetails>>();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append((new StringBuilder()).append("select qprd.referrer as referrer, qprd.providerName as providerName , " +
					"qprd.popupMessage as popupMessage, qprd.singlePlayPoints as singlePlayPoints, " +
					"qprd.doublePlayPoints as doublePlayPoints,qprd.triplePlayPoints as triplePlayPoints, " +
					"qprd.doublePlayPIPoints as doublePlayPIPoints,qprd.doublePlayPVPoints as doublePlayPVPoints, " +
					"qprd.doublePlayVIPoints as doublePlayVIPoints,qprd.phonePoints as phonePoints, " +
					"qprd.videoPoints as videoPoints,qprd.internetPoints as internetPoints, " +
					"prz.zipCode as zipCode from QualificationPopUpRefDetails qprd, " +
					"PopUpRefZipCode prz where qprd.id = prz.popupRefId").toString());
			Query q = getEntityManager().createQuery(sb.toString());
			Object obj = q.getResultList();
			List<QualificationPopUpRefDetails> qualPopUpRefDetailsList = new ArrayList<QualificationPopUpRefDetails>();
			if ((obj != null) && (obj instanceof List )) {
				qualPopUpRefDetailsList = (List<QualificationPopUpRefDetails>) obj;
			}
			Iterator it = qualPopUpRefDetailsList.iterator();
			while(it.hasNext()){
				Object fields[] = (Object[])it.next();
					String referrer = fields[0].toString();
					String providerName = fields[1].toString();
					if(qualificationPopUpRefDetailsMap.get(referrer) != null ){
						Map<String, QualificationPopUpRefDetails> qualPopUpDetailsMap = qualificationPopUpRefDetailsMap.get(referrer);
						if(qualPopUpDetailsMap.get(providerName) != null){
							QualificationPopUpRefDetails popupDetails = qualPopUpDetailsMap.get(providerName);
							popupDetails.getZipCodesList().add(fields[12].toString());
						}
						else{
							QualificationPopUpRefDetails details = new QualificationPopUpRefDetails();
							details.getZipCodesList().add(fields[12].toString());
							details.setReferrer(referrer);
							details.setProviderName(providerName);
							details.setPopupMessage(fields[2].toString());
							details.setSinglePlayPoints(Float.valueOf(fields[3].toString()));
							details.setDoublePlayPoints(Float.valueOf(fields[4].toString()));
							details.setTriplePlayPoints(Float.valueOf(fields[5].toString()));
							details.setDoublePlayPIPoints(Float.valueOf(fields[6].toString()));
							details.setDoublePlayPVPoints(Float.valueOf(fields[7].toString()));
							details.setDoublePlayVIPoints(Float.valueOf(fields[8].toString()));
							details.setPhonePoints(Float.valueOf(fields[9].toString()));
							details.setVideoPoints(Float.valueOf(fields[10].toString()));
							details.setInternetPoints(Float.valueOf(fields[11].toString()));
							details.setZipCode(fields[12].toString());
							qualPopUpDetailsMap.put(providerName, details);
						}
					}
					else{
						Map<String, QualificationPopUpRefDetails> qualificationPopUpDetailsMap = new HashMap<String, QualificationPopUpRefDetails>();
						QualificationPopUpRefDetails details = new QualificationPopUpRefDetails();
						details.getZipCodesList().add(fields[12].toString());
						details.setReferrer(referrer);
						details.setProviderName(providerName);
						details.setPopupMessage(fields[2].toString());
						details.setSinglePlayPoints(Float.valueOf(fields[3].toString()));
						details.setDoublePlayPoints(Float.valueOf(fields[4].toString()));
						details.setTriplePlayPoints(Float.valueOf(fields[5].toString()));
						details.setDoublePlayPIPoints(Float.valueOf(fields[6].toString()));
						details.setDoublePlayPVPoints(Float.valueOf(fields[7].toString()));
						details.setDoublePlayVIPoints(Float.valueOf(fields[8].toString()));
						details.setPhonePoints(Float.valueOf(fields[9].toString()));
						details.setVideoPoints(Float.valueOf(fields[10].toString()));
						details.setInternetPoints(Float.valueOf(fields[11].toString()));
						details.setZipCode(fields[12].toString());
						qualificationPopUpDetailsMap.put(providerName, details);
						qualificationPopUpRefDetailsMap.put(referrer, qualificationPopUpDetailsMap);
					}
			
			}
			logger.info("qualificationPopUpRefDetailsMap size="+qualificationPopUpRefDetailsMap.size());
		} catch (NoResultException nre) {
			logger.error(nre.getMessage());
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		return qualificationPopUpRefDetailsMap;
	}

}