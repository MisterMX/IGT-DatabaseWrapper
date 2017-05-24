package com.igt.database.wrapper;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.igt.database.wrapper.ogm.OGMDatabaseService;

public class App {

	private static final String 
			INPUT_PREFIX = "=>",
			CMD_EXIT = "bye",
			CMD_DB = "db",
			CMD_DB_SET = "set",
			CMD_DB_LIST = "list",
			CMD_DB_LIST_CUSTOMER = "customer",
			CMD_DB_CREATE = "create",
			CMD_DB_CREATE_CUSTOMER = "customer",
			CMD_DB_CREATE_CUSTOMER_SURNAME = "--surName=",
			CMD_DB_CREATE_CUSTOMER_FORENAME = "--foreName=",
			CMD_DB_DELETE = "delete",
			CMD_DB_DELETE_CUSTOMER = "customer",
			CMD_DB_DELETE_CUSTOMER_ID = "--id=",
			MSG_NO_DB_ATTACHED = "No database attached.";

	private static final String[] DB_OGM_NAMES = { "neo4j", "mongodb" }, DB_ORM_NAMES = { "mysql", "oracle" };

	private static DatabaseService currentDatabase;
	private static Scanner scanner;

	/**
	 * Entry point of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Init
		scanner = new Scanner(System.in);

		// Run program
		while (runProgramTurn()) {
		}

		// Close open connections
		scanner.close();
		if (currentDatabase != null)
			currentDatabase.close();
	}

	private static boolean runProgramTurn() {
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
				System.out.println("Goodbye!");
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
				onListCommand(input);
				break;

			case CMD_DB_SET:
				setDb(input.length >= 3 ? input[2] : "");
				break;

			case CMD_DB_CREATE:
				createCommand(input);
				break;
				
			case CMD_DB_DELETE:
				deleteCommand(input);
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
			// currentDatabase = new ORMDatabaseService(argument);
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

	private static void onListCommand(String[] input) {
		if (input.length > 2) {
			switch (input[2]) {
			case CMD_DB_LIST_CUSTOMER:
				printCustomerList();
				break;
			
			default:
				printUnknownCommand(input[2]);
				break;
			}
		} else {
			printDbList();
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
	
	private static void printCustomerList() {
		if (currentDatabase != null) {			
			List<CustomerEntity> customers = currentDatabase.getCustomers();
			
			System.out.println(String.format("%d customer(s) found:", customers.size()));
			for (CustomerEntity entity : customers) {
				System.out.println(String.format("\t%d: %s, %s", entity.getId(), entity.getSurName(), entity.getForeName()));
			}
		} else {
			printCurrentDb();
		}
	}

	private static void printUnknownCommand(String command) {
		System.out.println(String.format("Unknwon command: %s", command));
	}

	private static void createCommand(String[] input) {
		if (input.length > 2) {
			switch (input[2]) {
			case CMD_DB_CREATE_CUSTOMER:
				createCustomerCommand(input);
				break;
			default:
				printUnknownCommand(input[2]);
				break;
			}
		}
	}

	private static void createCustomerCommand(String[] input) {
		String foreName = null, surName = null;

		for (int i = 3; i < input.length; i++) {
			if (input[i].startsWith(CMD_DB_CREATE_CUSTOMER_SURNAME)) {
				surName = input[i].substring(CMD_DB_CREATE_CUSTOMER_SURNAME.length());
			} else if (input[i].startsWith(CMD_DB_CREATE_CUSTOMER_FORENAME)) {
				foreName = input[i].substring(CMD_DB_CREATE_CUSTOMER_FORENAME.length());
			}
		}

		if (foreName == null || surName == null) {
			System.out.println(String.format("Need to define %s and %s.", CMD_DB_CREATE_CUSTOMER_FORENAME,
					CMD_DB_CREATE_CUSTOMER_SURNAME));
		} else if (currentDatabase != null) {
			currentDatabase.createCustomer(new CustomerEntity(surName, foreName, new Date(), null));
			System.out.println("Query ok.");
		} else {
			printCurrentDb();
		}
	}
	
	private static void deleteCommand(String[] input) {
		if (currentDatabase != null) {
			if (input.length >= 3) {
				switch (input[2]) {
				case CMD_DB_DELETE_CUSTOMER:
					deleteCustomerCommand(input);
					break;
				default:
					printUnknownCommand(input[2]);
					break;
				}
			}
		} else {
			printCurrentDb();
		}
	}
	
	private static void deleteCustomerCommand(String[] input) {
		if (input.length >= 4) {
			if (input[3].startsWith(CMD_DB_DELETE_CUSTOMER_ID)) {
				long id = Long.parseLong(input[3].substring(CMD_DB_DELETE_CUSTOMER_ID.length()));
				if (currentDatabase.removeCustomer(id)) {
					System.out.println("Query ok.");
				} else {
					System.out.println(String.format("Could not remove customer with ID %d", id));
				}
			} else {
				System.out.println(String.format("Missing %s", CMD_DB_DELETE_CUSTOMER_ID));
			}
		} else {
			System.out.println(String.format("Missing %s", CMD_DB_DELETE_CUSTOMER_ID));
		}
	}
}
