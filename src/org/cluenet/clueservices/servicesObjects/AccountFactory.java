package org.cluenet.clueservices.servicesObjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class AccountFactory {
	private static AccountFactory factory = new AccountFactory();
	private static Map< Integer, Account > map = Collections.synchronizedMap( new HashMap< Integer, Account >() );
	
	public class Account {
		private Account() {
			
		}
	}
	
	private AccountFactory() {
		
	}

	public static Account find( Integer account ) {
		return map.get( account );
	}
}
