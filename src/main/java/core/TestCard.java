package core;

public class TestCard extends AdventureCard{
	
	public static final int NUMBER_TEST = 2;	
	
	public static final String TEST_OF_VALOR_NAME = "Test of Valor";
	public static final String TEST_OF_TEMPTAION_NAME = "Test of Temptation";
	public static final String TEST_OF_MORGAN_LE_FREY_NAME = "Test of Morgan Le Frey";
	public static final String TEST_OF_THE_QUESTING_BEAST_NAME = "Test of the Questing Beast";
	
	public String getName(){return this.name;}
	
	public TestCard(String name){
		super(TEST);
	
		this.name = name;
	}

	public String toString() {
		return "ID: " + this.id 
		   + ", type: " + this.type 
		   + ", subtype: " + this.subType 
		   + ", name: " + this.getName();
	}
}
