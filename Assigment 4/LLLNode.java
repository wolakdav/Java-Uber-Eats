import java.util.Scanner;//Imports Scanner
/*
 * David Wolak
 * CS 202
 * Assignment 4
 * 11/29/17
 * 
 * This file contains all the information for the linked list, LLLNode class is a class that contains all the information for the node. The listManager is the acutal list itself
 * The linked list contains special request that are just strings that the user writes their request into
 */

public class LLLNode {
	
	protected static Scanner input = new Scanner(System.in);//Scanner for data input
	private LLLNode next;//Next pointer
	private String instruction;//This is the special instructions for the order
	
	LLLNode(String distance)
	{
		instruction = "Driver is " + distance + " kilometers away from the resturant";
	}
	
	protected void setInstruction()//Gets the information from the user of what the user wants
	{
		System.out.print("Enter the Speical Instruction for the meal: \n");//Prompt for input
		this.instruction = input.nextLine();//Gets the input
	}
	protected LLLNode goForward()//Getter for the next pointer
	{
		return this.next;
	}
	protected void connectNode(LLLNode connect)//Connects a node after the current node

	{
		this.next = connect;
	}


	LLLNode()//Constructor that makes everything null
	{
		next = null;
		instruction = null;
	}
	public void display()//Displays the instructions
	{
		System.out.print(instruction);
	}
	
}


class listManager //This is the class that contains the linearly linked list itself
{
	protected LLLNode head = null;//Head node thats null

	
	public void addNode(LLLNode toAdd)//Adds a node to the list
	{
		if(head == null)//Case 1, if head is empty sets the head to the toAdd
		{
			head = toAdd;
			
		}else if(head.goForward() == null){//If head is the only node adds the new node to the next node
			head.connectNode(toAdd);
		}else {
			addNode(head.goForward(),toAdd);//Otherwise traverse the list until it gets to a point it can add it
		}
	}
	private void addNode(LLLNode head,LLLNode toAdd)//Private method for adding nodes, traverse the list until it finds a spot
	{
		if(head.goForward() == null)//If at end of the list
		{
			head.connectNode(toAdd);//Adds the node
		}else {
			addNode(head.goForward(),toAdd);//Otherwise it keeps going
		}
	}

	public void display()//Displays everything
	{
		while(head != null)//Made it a loop just to keep it simple,loops though the loop until it gets the end, displays everything
		{
			head.display();
			System.out.print("\n \t");
			head = head.goForward();
		}
	}
	/*
	 * Used as a test function to make sure everything worked
	public static void main(String[] args)
	{
		listManager test = new listManager();
		LLLNode toAdd = new LLLNode();
		toAdd.setInstruction();
		test.addNode(toAdd);
		
		LLLNode toAdd2 = new LLLNode();
		toAdd2.setInstruction();
		
		test.addNode(toAdd2);
		
		LLLNode toAdd3 = new LLLNode();
		toAdd3.setInstruction();
		
		test.addNode(toAdd3);
		test.display();
		//test.displayList();
	}
*/
}