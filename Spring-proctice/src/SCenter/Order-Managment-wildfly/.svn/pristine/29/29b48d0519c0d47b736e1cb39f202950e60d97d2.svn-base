package com.AL.ome.mapping;

import java.util.List;

import org.apache.log4j.Logger;

import com.AL.xml.v4.DialogValueType;
import com.AL.xml.v4.LineItemType;
import com.AL.xml.v4.SelectedDialogsType;
import com.AL.xml.v4.TransientResponseContainerType;

public class ComcastOrderMappingUtil {

    private static final String DLG_COMCAST_ORDER_NUMBER = "ComcastOrderNumber";
    private static final Logger logger = Logger.getLogger(ComcastOrderMappingUtil.class);

	public static String retrieveVOrderNo(Boolean isSwivelOption,LineItemType liType) {
		logger.debug("Retrieving Comcast order no. from lineitem ...");
		String VOrderNo = "";

		if (isSwivelOption) {
			//Retrieve comcast swivel V order no. from dialogues

			if(liType != null && liType.getActiveDialogs() != null){

			    SelectedDialogsType selDlgsType = liType.getActiveDialogs();
			    if(selDlgsType.getDialogs() != null && selDlgsType.getDialogs().getDialogList() != null) {
				List<DialogValueType> dlgList = selDlgsType.getDialogs().getDialogList();
				for(DialogValueType dvType : dlgList) {
				    if(dvType.getExternalId().equalsIgnoreCase(DLG_COMCAST_ORDER_NUMBER)) {
					if(dvType.getValueList() != null && !dvType.getValueList().isEmpty()) {
					    VOrderNo = dvType.getValueList().get(0).getStringValue();
					    logger.debug("Comcast-swivel V order id :" + VOrderNo);
					    break;
					}
				    }
				}
			    }

			}

		}else{
			// For SYP, order submit response will have subscriber id in transient response container of comcast product
			//Retrieve comcast order id from transient response of g2b submit(SYP)
			if(liType != null && liType.getTransientResponseContainer() != null){
				TransientResponseContainerType trcType = liType.getTransientResponseContainer();
				if(trcType.getTransientResponse() != null){

					if(trcType.getTransientResponse().getProviderLineItemStatus() != null && trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode() != null){
						if(trcType.getTransientResponse().getProviderLineItemStatus().getProcessingStatusCode().toString().equalsIgnoreCase("SUCCESS")){
							VOrderNo = trcType.getTransientResponse().getOrderNumber();
							logger.debug("SYP - comcast order no. :" + VOrderNo);
						}
					}


				}
			}
		}
		return VOrderNo;
	}
}
