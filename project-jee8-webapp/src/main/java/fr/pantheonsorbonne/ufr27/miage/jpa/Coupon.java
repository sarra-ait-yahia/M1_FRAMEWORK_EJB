package fr.pantheonsorbonne.ufr27.miage.jpa;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AttributeOverrides(
		
		{ 
			@AttributeOverride(name = "code", column = @Column(name = "coupon_code")),
			@AttributeOverride(name = "discountRatio", column = @Column(name = "coupon_discount_ratio")) 
		}
		)
public class Coupon {

	String code;
	double discountRatio;

}
