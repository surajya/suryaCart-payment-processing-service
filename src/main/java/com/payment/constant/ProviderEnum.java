package com.payment.constant;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum ProviderEnum {
	
	STRIPE(1,"STRIPE");
	
	private int id;
	private String name;
	
	ProviderEnum(int id,String name) {
		this.id = id;
		this.name = name;
	}
	
	
	// Method to get enum from name
    public static ProviderEnum getByName(String name) {
        for (ProviderEnum status : values()) {
            if (status.name.equalsIgnoreCase(name)) {
                return status;
            }
        }
        
        log.info("no enum is found for name: " + name);
        return null;
    }

    // Method to get enum from id
    public static ProviderEnum getById(int id) {
        for (ProviderEnum status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        
        log.info("no enum is found for id:  " + id);
        return null;
    }
	
}
