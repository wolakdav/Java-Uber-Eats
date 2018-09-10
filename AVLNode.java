
/**
 *
 *  @author David Wolak
 * CS 202
 * Assignment 4
 * 11/29/17
 *
 * This class contains the AVLNodes and the AVL tree itself
 *
 */
import java.util.Random;//Used to determine random distances

public class AVLNode {
	Random random = new Random();//Used for distances


	private listManager list = new listManager();//Linked list to hold the distance, uses a string to hold a int value, while not the most efficent it saves a ton of time
	protected AVLNode left;//Node for going left, made protected to make it ALOT easier to do this stupid tree
	protected AVLNode right;//Same as above but right
	protected String name;//Holds the name of the restaurant
	private int height = 0;//Holds the height of the node


	AVLNode(String copy)//Constructor for a avl node that takes in a name and creates random drivers
	{
		int distance;//Holds distance
		String distanceS;//Distance as a string

		name = copy;//Adds the name of the resturant to the node
		int amtDrivers = random.nextInt(5)+1;//Creates a random amount of drivers
		for(int i = 0;i<amtDrivers;i++)//Loops thourgh the amount of random drivers
		{
			distance = random.nextInt(10) + 1;//Gets a random int between 1 and 10 for a distance
			distanceS = Integer.toString(distance);//Parse it to a string to hold in the linked list
			LLLNode node = new LLLNode(distanceS);//Creates a new LLLNode with the distance
			list.addNode(node);//Adds the node to the list

		}
	}

	public int height()//Gets the height of the node
	{
		return height;
	}

	protected void height(int copy)//Sets the nodes height
	{
		height = copy;
	}

	protected AVLNode connectLeft(AVLNode node)//Connects the nodesleft
	{
		this.left = node;
		return this;
	}

	protected AVLNode connectRight(AVLNode node)//Connects the nodes right
	{
		this.right = node;
		return this;
	}

	protected AVLNode goLeft()//Returns the nodes left
	{
		return this.left;
	}

	protected AVLNode goRight()//Returns the nodes right
	{
		return this.right;
	}

	protected void setResturant(String res)//Sets the restaurant if I dont want to use a linked list for some reason
	{
		this.name = res;
	}

	public void display()//Displays the node, first the name, then how far away each driver is
	{
		System.out.print("Resturant Name: " + name + "\n \t");
		list.display();
		System.out.print("\n");
	}

}



class AVLTree{//This is the class thats hold the AVL tree itself, it deals with balancing the tree and adding to it

	protected AVLNode root;//Root nod for the tree


	private int maxHeight(int left,int right)//Determines which node is higher up,saves me time and energy by writing a function to do it
	{
		if(left > right)
		{
			return left;
		}else {
			return right;
		}
	}

	private AVLNode rotateRight(AVLNode current)//Function to rotate the tree right
	{
		AVLNode left = current.goLeft();//Sets some vars
		AVLNode leftRight = left.goRight();//These names are a little confusing but I tried the best I could, this is the left child RIGHT child

		left.right = current;//Moves current to lefts right child
		current.left = leftRight;//Sets currents left child to leftRight

		int leftH = current.goLeft().height();//These three lines just update the height of current, first two get variables, third updates height
		int rightH = current.goRight().height();
		current.height(maxHeight(leftH,rightH)+1);//I could make this one line, but that would look horrible

		int leftleftH = left.goLeft().height();//Same as above, updates lefts height
		int leftRightH = left.goRight().height();
		left.height(maxHeight(leftleftH,leftRightH)+1);


		return left;		//Returns the new node due to the wonders of Java
	}


	private AVLNode leftRotate(AVLNode current)//The left rotate
	{
		AVLNode right = current.goRight();//Gets currents right child
		AVLNode rightLeft = right.goLeft();//Gets rights left child

		right.left = current;//Replaces rights left with current
		current.right = rightLeft;//Replaces currents right with rights old left


		int leftH = current.goLeft().height();//Same as right rotate, next two lines get height variables, third updates currents height
		int rightH = current.goRight().height();
		current.height(maxHeight(leftH,rightH)+1);

		int rightLeftH = right.goLeft().height();//Same as above but updates rights height
		int rightRightH = right.goRight().height();
		right.height(maxHeight(rightLeftH,rightRightH)+1);

		return right;
	}


	private int getBalance(AVLNode current)//Get the balance level of any given node
	{
		if(current == null)
		{
			return 0;//Turns out if theres no node its perfectly balanced

		}else {
			return current.left.height() - current.right.height();//Otherwise just sub left and rights height to get the balance factor
		}
	}
	public AVLNode insert(String name)//Public insert function that only needs string, didnt want to make root public but wanted people in the package to add to it
	{
		return insert(root,name);//Wrapper for the real insert, adds the root
	}

	private AVLNode insert(AVLNode node,String name)//Inserts the node and deals with all the fun rotation logic, if something goes wrong good chance its here
	{
	//	System.out.print("Inserting " + name +"\n"); Old debug statement
		if(node == null)//If node is null gotta insert into it
		{

			node = new AVLNode(name);
			return node;
		}

		int compare = name.compareTo(node.name);//Gets a compare factor that see ifs the node needs to go left or right
		if(compare > 0 )//Name less then the node
		{
			node.right = insert(node.right,name);//Then it goes right
		}else if(compare < 0) {
			node.left = insert(node.left,name);//If the name is more then the node it goes left
		}else if(compare == 0) {//NO DUPLICATES THIS SHOULDNT HAPPEN SO IT JUST REJECTED
			return node;
		}
		if(node.left != null && node.right != null) {//If theres a left and a right then balancing can begin, this caused me a good amount of grief actually.
			node.height(1 + maxHeight(node.left.height(),node.right.height()));//Updates the height of node since it should be added to now

			int balance = getBalance(node);//gets the new balance of the node

			int compareLeft = name.compareTo(node.left.name);//Compares the names on the left
			int compareRight = name.compareTo(node.right.name);//Compares the names on the right

			if(balance > 1 && compareLeft > 0)//If the balance is towards the right AND the node needs to go right
			{
				return rotateRight(node);//Then you gotta rotate right
			}

			if(balance > 1 && compareLeft < 0)//If your balance is right BUT you need to go left
			{
				node.left = leftRotate(node.left);//First change lefts node by doing a left rotation
				return rotateRight(node);//Then you gotta do a right rotation, a left right rotation
			}

			if(balance < -1 && compareRight < 0)//If the balance is towards the left AND you gotta go left
			{
				return leftRotate(node);//Then do a basic left rotation
			}
			if(balance < -1 && compareRight > 0)//But if your balance is left and you need to go right
			{
				node.right = rotateRight(node.right);//First right rotate the right node
				return leftRotate(node);//Then left rotate the whole thing, creating a right left rotation
			}



		}
		return node;//And to keep java happy return the node to update it
	}

    public void display()//Public display that can be called without a root or node
        {

    	display(root);//Wrapper for the real display which needs the root to function
        }
    private void display(AVLNode node)//The real display, does it in pre order
    {

    		if (node != null) {//If the node exist
	            System.out.print(node.name + " ");//Displays first the name
	            node.display();//Then it displays the drivers and their distances from the returant
	            display(node.left);//Displays the left tree first
	            display(node.right);//Then the right
        }
    }
    public AVLNode retrive(String retrive)//Public retrive function, not acutaly used in Uber Eats
    {
    	return retrive(root,retrive);//Wrapper for retrive which needs the root
    }
    private AVLNode retrive(AVLNode current,String retrive)//Retrives a node given a name
    {
    	if(current != null)//If the node exists
    	{
    		int compare = retrive.compareTo(current.name);//Gets a compare factor to figure out where to go
    		if(compare == 0)//If they match its at the right node
    		{
    			return current;//Returns the node
    		}else if(compare > 0)//If its bigger, then you gotta go right
    		{
    			return retrive(current.right,retrive);//Moves down the right subtree
    		}else {//Otherwise its gonna be smaller
    			return retrive(current.left,retrive);//So you have to move down the left subtree
    		}

    	}else {//If its reached the end of the tree without finding the node then it can be assumed the node doesnt exist
    		System.out.println("This item does not exist in the data base! \n");//Message for the user
    		return null;//Returns nothing
    	}
    }

    protected AVLNode delete()//There had to be a delete function so I included one? Even though its java?
    {
    	root = null;//Sets the root to null
    	return root;//Returns root so Java can do its thing
    }

    public static void main(String[] args) {
     /*
     	This was ued just to test out the tree without slapping it into Uber Eats.
     AVLTree balance = new AVLTree();

        test.root = test.insert( "1");
        test.root = test.insert("2");
        test.root = test.insert("4");
        balance.root = test.insert("7");
        test.root = test.insert( "10");
        test.root = test.insert( "0");


        test.display();*/
    }
}
