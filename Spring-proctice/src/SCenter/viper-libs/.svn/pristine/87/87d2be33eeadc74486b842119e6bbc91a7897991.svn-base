package com.A.Vdao.transactional.dao.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.A.V.beans.entity.SelectedDialogue;
import com.A.Vdao.dao.SelectedDialogueDao;

@Component
public class SelectedDialogueDaoImpl extends BaseTransactionalJpaDao implements SelectedDialogueDao {
    private static final Logger logger = Logger.getLogger(SelectedDialogueDaoImpl.class);

    @Transactional(value = "transactional", propagation = Propagation.REQUIRED)
    public void persist(Set<SelectedDialogue> dialogueList) {
	logger.info("Persisting Active Dialogue information:dialogueList Size: " + dialogueList.size());
	long start = System.currentTimeMillis();
	if (dialogueList != null) {
	    for (SelectedDialogue dialogue : dialogueList) {
		if (dialogue.getId() == 0) {
		    getEntityManager().persist(dialogue);
		}
		else {
		    getEntityManager().merge(dialogue);
		}
	    }
	    getEntityManager().flush();
	}
	logger.info("Active Dialogue save took : " + (System.currentTimeMillis() - start) + "ms");
    }
}
