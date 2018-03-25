package client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdventureDeck extends CardCollection<AdventureCard>{
	private static final Logger logger = LogManager.getLogger(AdventureDeck.class);

	
	public AdventureDeck() {
		
		super();

		logger.info("Adventure Deck Created");
		
		// WEAPONS
		for(int i = 0; i < WeaponCard.NUMBER_SWORDS; ++i){
			cards.add(new WeaponCard(WeaponCard.SWORD_NAME, WeaponCard.SWORD_BATTLE_POINTS));
		}
		for(int i = 0; i < WeaponCard.NUMBER_DAGGER; ++i){
			cards.add(new WeaponCard(WeaponCard.DAGGER_NAME, WeaponCard.DAGGER_BATTLE_POINTS));
		}
		for(int i = 0; i < WeaponCard.NUMBER_HORSE; ++i){
			cards.add(new WeaponCard(WeaponCard.HORSE_NAME, WeaponCard.HORSE_BATTLE_POINTS));
		}
		for(int i = 0; i < WeaponCard.NUMBER_BATTLE_AX; ++i){
			cards.add(new WeaponCard(WeaponCard.BATTLE_AX_NAME, WeaponCard.BATTLE_AX_BATTLE_POINTS));
		}
		for(int i = 0; i < WeaponCard.NUMBER_LANCE; ++i){
			cards.add(new WeaponCard(WeaponCard.LANCE_NAME, WeaponCard.LANCE_BATTLE_POINTS));
		}
		for(int i = 0; i < WeaponCard.NUMBER_EXCALIBUR; ++i){
			cards.add(new WeaponCard(WeaponCard.EXCALIBUR_NAME, WeaponCard.EXCALIBUR_BATTLE_POINTS));
		}
		
		// FOES
		for(int i = 0; i < FoeCard.NUMBER_THIEVES; ++i){
			cards.add(new FoeCard(FoeCard.THIEVES_NAME, FoeCard.THIEVES_BATTLE_POINTS, FoeCard.THIEVES_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_BOAR; ++i){
			cards.add(new FoeCard(FoeCard.BOAR_NAME, FoeCard.BOAR_BATTLE_POINTS, FoeCard.BOAR_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_SAXONS; ++i){
			cards.add(new FoeCard(FoeCard.SAXONS_NAME, FoeCard.SAXONS_BATTLE_POINTS, FoeCard.SAXONS_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_ROBBER_KNIGHT; ++i){
			cards.add(new FoeCard(FoeCard.ROBBER_KNIGHT_NAME, FoeCard.ROBBER_KNIGHT_BATTLE_POINTS, FoeCard.ROBBER_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_SAXON_KNIGHT; ++i){
			cards.add(new FoeCard(FoeCard.SAXON_KNIGHT_NAME, FoeCard.SAXON_KNIGHT_BATTLE_POINTS, FoeCard.SAXON_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_EVIL_KNIGHT; ++i){
			cards.add(new FoeCard(FoeCard.EVIL_KNIGHT_NAME, FoeCard.EVIL_KNIGHT_BATTLE_POINTS, FoeCard.EVIL_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_BLACK_KNIGHT; ++i){
			cards.add(new FoeCard(FoeCard.BLACK_KNIGHT_NAME, FoeCard.BLACK_KNIGHT_BATTLE_POINTS, FoeCard.BLACK_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_GREEN_KNIGHT; ++i){
			cards.add(new FoeCard(FoeCard.GREEN_KNIGHT_NAME, FoeCard.GREEN_KNIGHT_BATTLE_POINTS, FoeCard.GREEN_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_MORDRED; ++i){
			cards.add(new FoeCard(FoeCard.MORDRED_NAME, FoeCard.MORDRED_BATTLE_POINTS, FoeCard.MORDRED_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_GIANT; ++i){
			cards.add(new FoeCard(FoeCard.GIANT_NAME, FoeCard.GIANT_BATTLE_POINTS, FoeCard.GIANT_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		for(int i = 0; i < FoeCard.NUMBER_DRAGON; ++i){
			cards.add(new FoeCard(FoeCard.DRAGON_NAME, FoeCard.DRAGON_BATTLE_POINTS, FoeCard.DRAGON_ALT_BATTLE_POINTS, new NoSpecial()));
		}
		
		// ALLIES
		cards.add(new AllyCard(AllyCard.SIR_GALAHAD_NAME, AllyCard.SIR_GALAHAD_BATTLE_POINTS, new NoSpecial()));
		cards.add(new AllyCard(AllyCard.SIR_LANCELOT_NAME, AllyCard.SIR_LANCELOT_BATTLE_POINTS, new NoSpecial()));
		cards.add(new AllyCard(AllyCard.KING_ARTHUR_NAME, AllyCard.KING_ARTHUR_BATTLE_POINTS, new BidSpecial(AllyCard.KING_ARTHUR_BIDS)));
		cards.add(new AllyCard(AllyCard.SIR_TRISTAN_NAME, AllyCard.SIR_TRISTAN_BATTLE_POINTS, new NoSpecial()));
		cards.add(new AllyCard(AllyCard.KING_PELLINORE_NAME, AllyCard.KING_PELLINORE_BATTLE_POINTS, new NoSpecial()));// SOMETIMES SPECIAL and name is different
		cards.add(new AllyCard(AllyCard.SIR_GAWAIN_NAME, AllyCard.SIR_GAWAIN_BATTLE_POINTS, new NoSpecial()));
		cards.add(new AllyCard(AllyCard.SIR_PERCIVAL_NAME, AllyCard.SIR_PERCIVAL_BATTLE_POINTS, new NoSpecial()));
		cards.add(new AllyCard(AllyCard.QUEEN_GUINEVERE_NAME, AllyCard.QUEEN_GUINEVERE_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_GUINEVERE_BIDS)));
		cards.add(new AllyCard(AllyCard.QUEEN_ISEULT_NAME, AllyCard.QUEEN_ISEULT_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_ISEULT_BIDS)));
		cards.add(new AllyCard(AllyCard.MERLIN_NAME, AllyCard.MERLIN_BATTLE_POINTS, new NoSpecial()));
		
		// TESTS
		for(int i = 0; i < TestCard.NUMBER_TEST; ++i){
			cards.add(new TestCard(TestCard.TEST_OF_VALOR_NAME));
		}
		for(int i = 0; i < TestCard.NUMBER_TEST; ++i){
			cards.add(new TestCard(TestCard.TEST_OF_TEMPTAION_NAME));
		}
		for(int i = 0; i < TestCard.NUMBER_TEST; ++i){
			cards.add(new TestCard(TestCard.TEST_OF_MORGAN_LE_FREY_NAME));
		}
		for(int i = 0; i < TestCard.NUMBER_TEST; ++i){
			cards.add(new TestCard(TestCard.TEST_OF_THE_QUESTING_BEAST_NAME));
		}
		
		// AMOURS
		for(int i = 0; i < AmourCard.NUMBER_AMOUR; ++i){
			cards.add(new AmourCard(new BidSpecial(AmourCard.AMOUR_BIDS)));
		}
	}

//	public static void main(String args[]){
//		AdventureDeck aD = new AdventureDeck();
//		
//		System.out.println("Adventure deck size (no Test Cards): " + aD.size());
//	}
	
//	public AdventureCard getByID(String ID){
//		
//		for (int i = 0; i < cards.size(); ++i){
//			if(ID.equals(cards.get(i).getID())){
//				return cards.get(i);
//			}
//		}
//		return null;
//	}
	
}

