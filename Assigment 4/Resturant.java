/**
 * 
 */

/**
 * 
 *  @author David Wolak
 * CS 202
 * Assignment 4
 * 11/29/17
 * 
 * This information contains everything about the restaurants
 * 
 */

public class Resturant {//Restaurant class, each one contains a array of food

	private Food item[];//This contains the food, its a array because the menu doesn't need to expand dynamically and this is more memory efficient 
	private int amount;//Amount of food items
	protected String resturantName;//The name of the restaurant
	private double distance;//How far it is in km
	private int cost;//From 1-3 how expensive is it
	
	Resturant(int size,String name,int copyCost, double copyDistance)//Constructor for restaurant,copies the data into the object
	{
		item = new Food[size];//Creates a array of size
		amount = size;//Next four lines copies the data into the correct posisiton
		resturantName = name;
		distance = copyDistance;
		cost = copyCost;
	
	}
	
	public void displayResturant()//Displays the resturant and its information
	{
		System.out.printf("Name: %s , %.2f m away, %d Expensive  \n",resturantName,distance,cost);
	}
	public void displayMenu()//Loops thourgh the food array and displays it
	{
		System.out.printf("%s  %.2f km away, %d Expensive",resturantName,distance,cost);//First displays the resturant name and information
		int size = item.length;//Gets the length of the food array, this is before I started using amount
		for(int i = 0;i<size;i++)
		{
			if(item[i] != null)//If the item is not null it displays them
				System.out.print("\n Number: " + (i+1) + "\n \t");
				item[i].displayFood();
				System.out.print("\t");//This is to make everything look good
		}
	}
	public void displayItem(int location)//Displays a single item given a location in the array
	{
		if(location >= 0 & item[location] != null)//If the number exists
			item[location].displayFood();
	}
	public void addItem(int number, int type,int calories,String name,int temp,double price)//Adds a item to the resturant, either coldfood,hotfood, or speical food
	{
		switch(type) {//Used a switch because I know which one I want and to be as quick as possible
		case(1):
			item[number] = new coldFood(name,temp,price,calories);//Each case creates the right object
			break;
		case(2):
			item[number] = new hotFood(name,temp,price,calories);
			break;
		case(3):
			item[number] = new speical(name,temp,price,calories);	
			break;
		}
	}
	public void setFood(int number,double arg1,int arg2,boolean arg3, boolean arg4,boolean arg5, boolean arg6)//Basiclly a wrapper for the foods setFood
	{
		if(item[number] != null)//If its a valid object
			item[number].setFood(arg1,arg2,arg3, arg4,arg5, arg6);
	}
	protected int amountItems()//Getter for the amount fo items
	{
		return amount;
	}
	protected Food getFood(int choice)//Getter for a certain food
	{
		return item[choice];
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Used for testing this class
		 * 
		Resturant obj = new Resturant(2,"McDonalds");
		obj.addItem(0,1,200,"Ice cream",30,1);
		obj.addItem(1,2,200,"Snack Melt",90,1.5);
		
		obj.setFood(1, 1.00, 1, true, true, true, true);
		obj.setFood(0,1.00,1,true,true,true,true);
		obj.displayMenu();
		*/
	}
	
}
