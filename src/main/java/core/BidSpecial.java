package core;

public class BidSpecial implements Special{

	private int numBids;
	
	public BidSpecial(int numBids) {

		this.numBids = numBids;
	}
	
	
	public void doSpecial() {
		// TODO Create behavior for extra bids
		
	}

	public String toString(){
		return "<Bid Special: adds " + this.numBids + " extra bid(s)>";
	}
	
}
