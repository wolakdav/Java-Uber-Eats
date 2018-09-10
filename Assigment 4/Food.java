/**
 * 
 */

/**
 * @author David Wolak
 * CS 202
 * Assignment 4
 * 11/29/17
 * 
 * This file contains all the food.
 * Class food is a abstract base class for cold food, hot food, and special which all serve as types of food.
 * 
 */
public abstract class Food {

		Food(String copyName,int copyTemp,double copyPrice,int copyCalories)//Constructor for food, sets all the values
		{	
			name =copyName;
			temp = copyTemp;
			price = copyPrice;
			calories = copyCalories;
		}
		protected int temp;//This is the temperature that it leaves the kicthen at
		protected String name;//Name of the dish
		protected double price;//Price of the food
		protected int calories;//How many calories

		
		public abstract void setFood(double arg1,int arg2,boolean arg3,boolean arg4,boolean arg5,boolean arg6);//Virtual function for setFood, sets the food with values
		protected abstract double timeToDeliver();//Virtual function that calculates the delivery time for the food
		public abstract void displayFood();//Virtual function that displays the food
			
			
}


class coldFood extends Food {//Class for cold food such as ice cream, ice, milkshakes, ect

		protected double heatSpeed;//How fast it takes to heat up in degrees per minute
		protected int maxTemp;//What is the maximum temp this can be and still be good?
	
		coldFood(String copyName,int copyTemp,double copyPrice,int copyCalories)//Constructor that calls the super constructor
		{
			super(copyName,copyTemp,copyPrice,copyCalories);	
		}
	
		public void setFood(double copyHeatSpeed,int copyMaxTemp,boolean arg3,boolean arg4,boolean arg5,boolean arg6)//Sets the food, each food type with the proper item while still being able to be used on all food types. This means that several varibles are not used in each type
		{
			heatSpeed = copyHeatSpeed;//This and next line copies over the data
			maxTemp = copyMaxTemp;
		
		}
		
		protected double timeToDeliver()//How long can the item be out for and still good
		{
			int range = maxTemp - temp;//range between served temp and allowed delivered temp
			return range/heatSpeed;//Returns how long is should take in minutes
		}
		
		public void displayFood()//Displays the item
		{
			double ttd = timeToDeliver();//Calculates the time to deliver
			System.out.printf("Name: %s  Price: %.2f  Calories: %d Maximum time for delivery: %.2f Minutes ",name,price,calories,ttd);
		}
		
}

class hotFood extends Food {//Hot food class for items such as burgers, tacos, or any food made in a oven
		
	protected double coolSpeed;//How fast does it cool in degrees per minutes
	protected int minTemp;//Whats the minimum temperature it can be and still be good
	protected boolean reheat;//Can the item be reheated
	
	hotFood(String copyName,int copyTemp,double copyPrice,int copyCalories)//Constructor works the same as cold food
	{
		super(copyName,copyTemp,copyPrice,copyCalories);

	}
	public void setFood(double copyCoolSpeed,int copyMinTemp,boolean copyReheat,boolean arg4,boolean arg5, boolean arg6)//This works as the same as coldfoods
	{
		coolSpeed = copyCoolSpeed;//This and next line copies over the variables into the food item
		minTemp = copyMinTemp;
	
	}
	protected double timeToDeliver() {//Calculates the time to deliver
		int range = temp - minTemp;//The range between served temperature and minimum good temp
		return range/coolSpeed;
	}
	
	public void displayFood() {//Displays all the food
		double ttd = timeToDeliver();//Calculates the time to deliver
		System.out.printf("Name: %s  Price: %.2f  Calories: %d Maximum time for delivery: %.2f  Reheatable: %b ",name,price,calories, ttd,reheat);
	}
}

class speical extends Food {//The special food, stuff like salads or vegan meals

		protected boolean allergyFree; //Is this food allergy free
		protected boolean glutenFree;//Is this gluten free
		protected boolean vegan;//Is this a vegan item
		protected boolean vegatarian;//Is this a vegatarian item
		
		speical(String copyName,int copyTemp,double copyPrice,int copyCalories)//Constructor wrapper for the super class constructor
		{
			super(copyName,copyTemp,copyPrice,copyCalories);

		}
		public void setFood(double arg1,int arg2,boolean copyAllergy,boolean copyGluten,boolean copyVegan,boolean copyVegetarian)//Sets the food by copying the data
		{
			allergyFree = copyAllergy;//These next four lines copies over the data into this speical food
			glutenFree = copyGluten;
			vegan = copyVegan;
			vegatarian = copyVegetarian;
		}
		protected double timeToDeliver()//Calculates the time to deliver by finding the range 
		{
			double range = temp - 70;//The range of temperatures allowed
			return (double) (range* 0.2);//The default cool/heat value is 0.2 degree per minute
		} 
		public void displayFood()//Displays the food and all its speical stuff
		{
			double ttd = timeToDeliver();
			System.out.printf("Name: %s  Price: %.2f  Calories: %d  Maximum time for delivery: %.2f",name,price,calories,ttd);
			if(allergyFree)//Four if statements that show is the item is ____ free
			{
				System.out.print(" Allergy Free  ");
			}
			if(glutenFree)
			{
				System.out.print(" Gluten Free ");
			}
			if(vegan)
			{
				System.out.print(" Vegan ");
			}
			if(vegatarian)
			{
				System.out.print( "Vegatrian ");
			}
			//System.out.print("\n");
		}
		
}



