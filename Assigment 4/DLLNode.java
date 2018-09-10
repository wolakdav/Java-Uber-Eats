import java.util.Scanner;
/*
 * David Wolak
 * CS 202
 * Assignment 4
 * 11/29/17
 * 
 * 
 */
//This file contains the class for the double linked node and then the manager for it, the DLL itself has a linearly linked list that contains anything else the user may want
public class DLLNode {
	
	protected static Scanner input = new Scanner(System.in);//Scanner used for inputs from the user
	private Food item;//Holds the food item that the user ordered
	private listManager list = new listManager();//This is the LLL that holds all requests
	
	private DLLNode next;
	private DLLNode prev;
	
	
	public void addRequest()//Asks the user if they want a request on their order and adds it to the list
	{
		int choice = 0;//Used for storing the users choice
		do {
		System.out.print("Would you like to add another a request? (1 for yes 2 for no) \n");//I did get a little lazy here and use a int instead of a char or string
		choice = input.nextInt();
		
		if(choice == 1) {//If the user wants to add a request it does this
			LLLNode toAdd = new LLLNode();//Creates new node
			toAdd.setInstruction();//Adds the request to the node
			list.addNode(toAdd);//Adds the node to the list
		}
		}while(choice == 1);//Do while so the user can add multiple request if desired
		
	}
	
	protected void connectNode(DLLNode connect)//Setter for the next point in the DLL,this.next should always be null so its prev isnt worried about
	{
		DLLNode temp = this.next;//Temp is used so that list can used forward and backward
		this.next = connect;//Connects connect to the list
		connect.prev = this;//This and next line connect connects pointers to the list
		connect.next = temp;	
	}
	
	public DLLNode goForward()//Getter that gets the next node in the list
	{
		return this.next;
	}
	
	public DLLNode goBackward()//Getter that gets the nodes previous node
	{
		return this.prev;
	}
	
	protected DLLNode AddFood(Food toAdd)//Adds a food to the node
	{
		if(toAdd != null) {//Checks to see if its null
			this.item = toAdd;//If its not null it adds the food and asks for requests
			this.addRequest();
		}
		return this;

	}
	
	public void display()//Public part of the display function,displays the food first, then the requests
	{
		this.item.displayFood();
		System.out.print("\n \t");//This is to look nice
		this.list.display();
		System.out.print("\n");//See above comment
	}
	
}

 
class orderManager {//This class manages the DLL and is responsible for making a acutal list from the nodes
	
	private DLLNode head;
	private DLLNode tail;

	
	public void addNode(DLLNode toAdd)//Public facing section of add node that takes a node to add as a arguement
	{
		if(head == null)//Case 1: Head is null. In this case head is now the new node and tail is head
		{
			head = toAdd;
			tail = head;
			
		}else if(head.goForward() == null){//If head is the only node, connects the new node to head and sets tail equal to the new node
			head.connectNode(toAdd);
			tail = head.goForward();
		}else {
			addNode(toAdd,head.goForward());//Otherwise the list has to traversed to find the node
		}
	}
	private void addNode(DLLNode toAdd,DLLNode head)//Private add node function that only orderManager is allowed to use
	{
		if(head.goForward() == null)//If at end of list, add the node
		{
			head.connectNode(toAdd);
			tail = head;//Make the new node tail
		}else {//Otherwise keep going forward
			addNode(toAdd.goForward(),head.goForward());
		}
		
	}
	
	
	public void displayForward()//Displays the list forward
	{
		{
			if(head == null)//If the list is empty tells the user
			{
				System.out.print("No orders to display");
			}else {//Otherwise displays the food, request, and moves on with the private display function
				head.display();
				displayForward(head.goForward());
				
			}
		}
	}
	private void displayForward(DLLNode head)//Private display funciton that traverses the list to display
	{
		if(head != null)//Checks to see if node exist
		{
			head.display();//Displays the order with request
			displayForward(head.goForward());//Moves forward
		
		}
	}
	
	
	public void displayBackward()//Display backwards public and private section function exactly the same as display forward, but replace head with tail and goForward with goBackward
	{
		if(tail == null)
		{
			System.out.print("No orders to display");
		}else {
			tail.display();
			displayBackward(tail.goBackward());
			
		}
	}
	private void displayBackward(DLLNode tail)
	{
		if(tail != null)
		{
			tail.display();
			displayBackward(tail.goBackward());
			
		}
	}
	


/*	This was used as a test for the double linked list, I really like that fact you can do this
	public static void main(String[] args) {
		orderManager test = new orderManager();
		DLLNode add1 = new DLLNode();
		DLLNode add2 = new DLLNode();
		
		Food food1 = new coldFood("test",1,1,1);
		Food food2 = new hotFood("test2",1,1,1);
		
		
		add1.AddFood(food1);
	
		
		
		add2.AddFood(food2);
		
		test.addNode(add1);
		test.addNode(add2);
	//	test.displayBackward();
		System.out.print("\n");
		test.displayForward();
		}
*/		
		
		
	
	
	
}