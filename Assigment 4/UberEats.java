/**
 * 
 *  @author David Wolak
 * CS 202
 * Assignment 4
 * 11/29/17
 * 
 * This information contains everything about the restaurants
 * 
 *
 * CS 202
 * Assignment 4
 * 11/29/17
 * This file has the ubereats class which is the "main file". 
 * It reads from the file and gets what the user wants to do and then does it
 * 
 */
import java.io.*;
import java.util.*;

public class UberEats {

	private Scanner read;//This is used for reading from the file
	protected static Scanner input = new Scanner(System.in);//This is used for inputing 
	
	
	private int resturantAmount;//Contains the amount of resturants
	private Resturant resturants[];//The array of resturants, since its going to be a fixed amount a array makes sense
	
	private orderManager orders = new orderManager();//This is for managing the orders
	private String fileName;//Contains the file name that contains the resturant and food information
	
	private AVLTree drivers = new AVLTree();
	
	protected void openFile()//This function just opens the file and gets it ready to read
	{
		try {//This try catch is incase the file doesnt exist
			read = new Scanner(new File(fileName));
			
		}
		catch(Exception e)
		{
			System.out.println("Error: No File Dectected");
		}
		
	}
	protected void readFile()//This reads from the file 
	{
		if(read.hasNext())//If it exists
		{
			setResturant();//Creates the resturant arrays
			int i = 0;	//i is used to keep track of which resturant its in
			while(read.hasNext())//While something is still in the file
			{//Next couple of lines read in somethings from the file and stores them in varibles
				String nameS = read.next();//Name of the resturant
				int amount = read.nextInt();//Amount of food items in the resutrant
				double distance = read.nextDouble();//How far it is
				int cost = read.nextInt();//How expensive is it from 1-3
				
				resturants[i] = new Resturant(amount,nameS,cost,distance);//Sets the resturant in the section i  
			
				for(int u = 0;u < amount;u++)//This loop adds food items
				{
					try {//Trys to see the food 
						setFood(i,u);//Reads in the food into the resturant i
					}
					catch(Exception e)//If something goes wrong it catches the error and force closes the program
					{
						System.out.print("Something went wrong reading from the file Please send a message to the dev team");
						System.exit(0);//<-- Something went bad so the program is force closed much like the PSU app
					}
	
				}
				i = i + 1;//Moves onto the next resturant
				
			}
		}
		read.close();//Closes the file
	}
	
	protected void setResturant()//Sets the resturant from the file
	{
		String sizeS = read.next();//Reads in the size of the resturant(how many items)
		int size = Integer.parseInt(sizeS);//Changes size from a string to a int, used for testing of how parse worked
		resturantAmount = size;//Sets the resturant amount
		resturants = new Resturant[size];//Creates the array of resturants
	}
	protected void setFood(int i,int u)//Takes in i and u and sets the food, i being the resturant number and u being the food number
	{//These next few lines read in information about the food from the file
		int type = read.nextInt();
		int calories = read.nextInt();
		String name = read.next();
		int temp = read.nextInt();
		double price = read.nextDouble();
		
		resturants[i].addItem(u, type, calories, name, temp, price);//Adds the item to the resturant
		
		//These lines read in more information about the food from the file
		double arg1 = read.nextDouble();
		int arg2 = read.nextInt();
		boolean arg3 = read.nextBoolean();
		boolean arg4 = read.nextBoolean();
		boolean arg5 = read.nextBoolean();
		boolean arg6 = read.nextBoolean();
		resturants[i].setFood(u, arg1, arg2, arg3, arg4, arg5, arg6);//Adds more information to the food
	}
	
	public void displayAll()//Displays all the resturants AND their food
	{
		int size = resturants.length;	
		for(int i = 0;i<size;i++) //Loops thoourgh the resturants
		{
			resturants[i].displayMenu();//This is the part that acutally displays everything
			System.out.print("\n \n");//Formatting
			
		}
	}
	public void displayResturants()//Displays just the resturants
	{
		int size = resturants.length;
		for(int i = 0;i<size;i++)//Loops thought the resturant arrays and displays just their informaiton
		{
			resturants[i].displayResturant();
		}
	}
	
	protected Food chooseItem()//Allows a user to choose  aitem
	{
		System.out.print("Please choose a resturant: \n");//Prompt
		for(int i = 0;i<resturantAmount;i++)//Displays all the information that the user can choose from
		{
			System.out.print(i+1);
			System.out.print(". \t");
			resturants[i].displayResturant();
			System.out.print("\n");
			
		}
		int resChoice = chooseResturant();//Gets what resturant the user wants
		int foodChoice = chooseFood(resChoice);//Gets what food item the user wants
	
		
		return resturants[resChoice].getFood(foodChoice);//Gets back the one item the user wants to put it in a list
	}
	protected int chooseResturant()//Lets the user choose a resturant to get food at
	{
		int resChoice = 0;
		do {//This loop is to catch any errors and let the user retry
			
			try {
				System.out.print("Please enter a valid resChoice: \t");//Prompt
				resChoice = input.nextInt();//Gets the input from the user
				
				if(resChoice < 1 || resChoice > resturantAmount)//If the input is not valid
					System.out.print("That resturant number does not exist, try again \n");//Error message
				
			}catch(InputMismatchException e){//If the wrong input is used it displays a error message and goes again
				
				System.out.print("Invalid input Try again \n");
			
			}
			input.nextLine();//Clears buffer
			
		}while(resChoice < 1 || resChoice > resturantAmount );//If the choice is valid it moves on
		resChoice -= 1;//This is so that the resturant is out of range
		
		return resChoice;//Returns the choice
	}
	protected int chooseFood(int resChoice)//Chooses a food given a returant
	{
	
		int foodChoice = 0;//Use for input
	
		System.out.print("Choose a valid item \n");//Prompt
		resturants[resChoice].displayMenu();//This part shows the menu to the user
		int amount = resturants[resChoice].amountItems();//Gets the amount of items
		
		do {//This loop goes checks if the input is within a valid range AND that it is a valid input by catching any error message
			
			try {
				System.out.print("\n Please enter a valid resChoice: \t");
				foodChoice = input.nextInt();
				
				if(foodChoice < 1 || foodChoice > amount)
					System.out.print("That resturant number does not exist, try again \n");
				
			}catch(InputMismatchException e){
				
				System.out.print("Invalid input Try again \n");
			
			}
			input.nextLine();
			
		}while(foodChoice < 1 || foodChoice > amount );
		return foodChoice-1;//-1 so that the food choice isnt out of range
	
	}
	
	UberEats()//Just sets filename equal to the file name
	{
		fileName = "resturants.txt";
		
	}
	
	public int menu()//Menu that gives the user options of what to do and then takes their input
	{
		int choice = 0;
		System.out.print("\n");
		System.out.print("Please select a option: \n \t");
		System.out.print("1.Display all resturants \n \t");
		System.out.print("2.Display everything \n \t");
		System.out.print("3.Order some food \n \t");
		System.out.print("4.Look at previous orders \n \t");
		System.out.print("5. See how far drivers are from resturants" + "\n \t");
		System.out.print("6. Exit Uber Eats \n");
		do {//This loop is to A)Make sure the answer is within range and B) make sure that the answer is valid
			try {
				choice = input.nextInt();
				input.nextLine();
				
			}catch(InputMismatchException e){
				
				System.out.print("Invalid input Try again \n");
				
			}

		}while(choice < 1 || choice > 6);
		
		return choice;//Returns what the user wants to do
	}
	protected void addDrivers()
	{
		for(int i = 0;i < resturantAmount;i++)
		{
			System.out.print(i+"\n");
			drivers.root = drivers.insert(resturants[i].resturantName);
		}
	}
	public static void main(String[] args) {//Main function that runs everything
		// TODO Auto-generated method stub
		int choice;
		UberEats app = new UberEats();
		app.openFile();//This and the next line opens and reads from the file to create the resturant and food items
		app.readFile();
		app.addDrivers();//Reads in the restaurants and creats drivers certain distances from them
		System.out.print("Welcome to Uber Eats beta! Please choose one of the following options \n");
		do//Loop that keeps the user in the app until they're done
		{
			
			choice = app.menu();
			switch(choice)
			{
			case 1:
				app.displayResturants();
				break;
			case 2:
				app.displayAll();
				break;
			case 3://If the user wants to order some food 
				Food order = null;//Creates food object 
				order = app.chooseItem();//Gets which food item the user wants and places it in order
				DLLNode toAdd = new DLLNode();//Creates a new DLLNode and places order inside of ti
				toAdd.AddFood(order);
				app.orders.addNode(toAdd);	//Adds that food item to the user

				break;
			case 4:
				app.orders.displayForward();
				break;
			case 5://Displays all the drivers and their distances from the restaurants
				app.drivers.display();
				break;
			case 6:
				System.out.print("Thank you for testing Uber Eats beta! Please send all feed back to the devolpers \n");
				break;
			default:
				System.out.print("That is not a valid option, try again \n");
				break;
			}
		}while(choice != 6);
		
		
	}
	
	
	
	
	

}
