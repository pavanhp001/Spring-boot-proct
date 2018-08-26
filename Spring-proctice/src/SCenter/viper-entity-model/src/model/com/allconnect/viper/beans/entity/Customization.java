package com.A.V.beans.entity;

import java.util.List;

import com.A.V.beans.ProductCustomizationBase;

public interface Customization {

	ProductCustomizationBase getProductCustomizationBase();


	List<? extends ProdChoice> getChoices();


}