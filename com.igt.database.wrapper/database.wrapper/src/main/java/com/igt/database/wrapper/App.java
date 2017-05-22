package com.igt.database.wrapper;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.igt.database.wrapper.ogm.OGMDatabaseService;
import com.igt.database.wrapper.orm.ORMDatabaseService;

public class App {
	
	private static final String INPUT_PREFIX = "=>",
								CMD_EXIT =  "bye",
								CMD_DB = "db",
								CMD_DB_SET = "set",
								CMD_DB_LIST = "list",
								CMD_LIST_DB = "listdb",
								MSG_NO_DB_ATTACHED = "No database attached.";
	
	private static final String[] DB_OGM_NAMES = { "ogm-neo4j" },
								  DB_ORM_NAMES = { "mysql", "oracle" };
	
	
	private static DatabaseService currentDatabase;
	private static Scanner scanner;
	
	/**
	 * Entry point of the program.
	 * @param args
	 */
	public static void main(String[] args) {
		// Init
		scanner = new Scanner(System.in);
		
		// Run program
		while (runProgramTurn()) { }
		
		// Close open connections
		scanner.close();
		if (currentDatabase != null)
			currentDatabase.close();
	}
	
	private static boolean runProgramTurn() {
//		DatabaseService databaseService = new OGMDatabaseService("ogm-neo4j");
//		
//		databaseService.createCustomer(new CustomerEntity("Eri", "Cartman", new Date(), null));
//		
//		List<CustomerEntity> customers =  databaseService.getCustomers();
//		
//		System.out.println(String.format("%d customer(s)", customers.size()));
//		databaseService.close();
		
		boolean run = true;
		
		System.out.print(INPUT_PREFIX);
		
		String inputText = scanner.nextLine();
		String[] split = inputText.split("\\s");
		
		if (split.length > 0) {
			switch (split[0]) {
			case CMD_DB:
				onDbCommand(split);
				break;
				
			case CMD_EXIT:
				run = false;
				break;
				
			default:
				printUnknownCommand(inputText);
				break;
			}
		}
		
		return run;
	}
	
	private static void onDbCommand(String[] input) {
		if (input.length > 1) {
			switch (input[1]) {
			case CMD_DB_LIST:
				printDbList();
				break;
				
			case CMD_DB_SET:
				setDb(input.length >= 3 ? input[2] : "");
				break;
				
			default:
				printUnknownCommand(input[1]);
				break;
			}
		} else {
			printCurrentDb();
		}
	}
	
	private static void setDb(String argument) {
		if (isValidOgmName(argument)) {
			if (currentDatabase != null) {
				currentDatabase.close();
			}
			
			currentDatabase = new OGMDatabaseService(argument);
		} else if (isValidOrmName(argument)) {
//			currentDatabase = new ORMDatabaseService(argument);
		} else {
			System.out.println(String.format("Unknown DB name: %s", argument));
		}
	}
	
	private static boolean isValidOgmName(String name) {
		for (String dbName : DB_OGM_NAMES) {
			if (dbName.equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean isValidOrmName(String name) {
		for (String dbName : DB_ORM_NAMES) {
			if (dbName.equals(name)) {
				return true;
			}
		}
		
		return false;
	} 
	
	private static void printCurrentDb() {
		if (currentDatabase != null) {
			System.out.println(String.format("Current attached DB: %s", currentDatabase.toString()));
		} else {
			System.out.println(MSG_NO_DB_ATTACHED);
		}
	}
	
	private static void printDbList() {
		for (String dbName : DB_OGM_NAMES) {
			System.out.println(String.format("\t%s", dbName));
		}
		
		for (String dbName : DB_ORM_NAMES) {
			System.out.println(String.format("\t%s", dbName));
		}
	}
	
	private static void printUnknownCommand(String command) {
		System.out.println(String.format("Unknwon command: %s", command));
	}
}
