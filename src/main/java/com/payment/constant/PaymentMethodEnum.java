package com.payment.constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum PaymentMethodEnum {
	
	APM(1,"APM");
	
	private int id;
	private String name;
	
	PaymentMethodEnum(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	
	// Method to get enum from name
    public static PaymentMethodEnum getByName(String name) {
        for (PaymentMethodEnum status : values()) {
            if (status.name.equalsIgnoreCase(name)) {
                return status;
            }
        }
        
        log.info("no enum is found for name: " + name);
        return null;
    }

    // Method to get enum from id
    public static PaymentMethodEnum getById(int id) {
        for (PaymentMethodEnum status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        
        log.info("no enum is found for id:  " + id);
        return null;
    }
	
}
