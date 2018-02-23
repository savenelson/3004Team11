package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardInstantiationTest {

	@Test
	public void test() {
		
		// WEAPONS
		WeaponCard sword = new WeaponCard(WeaponCard.SWORD_NAME, WeaponCard.SWORD_BATTLE_POINTS);
		assertEquals("Sword", sword.getName());
		assertEquals(10, sword.getBattlePoints());

		WeaponCard dagger = new WeaponCard(WeaponCard.DAGGER_NAME, WeaponCard.DAGGER_BATTLE_POINTS);
		assertEquals("Dagger", dagger.getName());
		assertEquals(5, dagger.getBattlePoints());

		WeaponCard horse = new WeaponCard(WeaponCard.HORSE_NAME, WeaponCard.HORSE_BATTLE_POINTS);
		assertEquals("Horse", horse.getName());
		assertEquals(10, horse.getBattlePoints());

		WeaponCard battle_ax = new WeaponCard(WeaponCard.BATTLE_AX_NAME, WeaponCard.BATTLE_AX_BATTLE_POINTS);
		assertEquals("BattleAx", battle_ax.getName());
		assertEquals(15, battle_ax.getBattlePoints());

		WeaponCard lance = new WeaponCard(WeaponCard.LANCE_NAME, WeaponCard.LANCE_BATTLE_POINTS);
		assertEquals("Lance", lance.getName());
		assertEquals(20, lance.getBattlePoints());

		WeaponCard excalibur = new WeaponCard(WeaponCard.EXCALIBUR_NAME, WeaponCard.EXCALIBUR_BATTLE_POINTS);
		assertEquals("Excalibur", excalibur.getName());
		assertEquals(30, excalibur.getBattlePoints());

		
		// FOES
		FoeCard thieves = new FoeCard(FoeCard.THIEVES_NAME, FoeCard.THIEVES_BATTLE_POINTS, FoeCard.THIEVES_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("Thieves", thieves.getName());
		assertEquals(5, thieves.getBattlePoints());
		assertEquals(5, thieves.getAltBattlePoints());

		FoeCard boar = new FoeCard(FoeCard.BOAR_NAME, FoeCard.BOAR_BATTLE_POINTS, FoeCard.BOAR_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("Boar", boar.getName());
		assertEquals(5, boar.getBattlePoints());
		assertEquals(15, boar.getAltBattlePoints());
		
		FoeCard saxons = new FoeCard(FoeCard.SAXONS_NAME, FoeCard.SAXONS_BATTLE_POINTS, FoeCard.SAXONS_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("Saxons", saxons.getName());
		assertEquals(10, saxons.getBattlePoints());
		assertEquals(20, saxons.getAltBattlePoints());
		
		FoeCard robberKnight = new FoeCard(FoeCard.ROBBER_KNIGHT_NAME, FoeCard.ROBBER_KNIGHT_BATTLE_POINTS, FoeCard.ROBBER_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("RobberKnight", robberKnight.getName());
		assertEquals(15, robberKnight.getBattlePoints());
		assertEquals(15, robberKnight.getAltBattlePoints());

		FoeCard saxonKnight = new FoeCard(FoeCard.SAXON_KNIGHT_NAME, FoeCard.SAXON_KNIGHT_BATTLE_POINTS, FoeCard.SAXON_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("SaxonKnight", saxonKnight.getName());
		assertEquals(15, saxonKnight.getBattlePoints());
		assertEquals(25, saxonKnight.getAltBattlePoints());

		FoeCard evilKnight = new FoeCard(FoeCard.EVIL_KNIGHT_NAME, FoeCard.EVIL_KNIGHT_BATTLE_POINTS, FoeCard.EVIL_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("EvilKnight", evilKnight.getName());
		assertEquals(20, evilKnight.getBattlePoints());
		assertEquals(30, evilKnight.getAltBattlePoints());

		FoeCard blackKnight = new FoeCard(FoeCard.BLACK_KNIGHT_NAME, FoeCard.BLACK_KNIGHT_BATTLE_POINTS, FoeCard.BLACK_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("BlackKnight", blackKnight.getName());
		assertEquals(25, blackKnight.getBattlePoints());
		assertEquals(35, blackKnight.getAltBattlePoints());

		FoeCard greenKnight = new FoeCard(FoeCard.GREEN_KNIGHT_NAME, FoeCard.GREEN_KNIGHT_BATTLE_POINTS, FoeCard.GREEN_KNIGHT_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("GreenKnight", greenKnight.getName());
		assertEquals(25, greenKnight.getBattlePoints());
		assertEquals(40, greenKnight.getAltBattlePoints());

		FoeCard mordred = new FoeCard(FoeCard.MORDRED_NAME, FoeCard.MORDRED_BATTLE_POINTS, FoeCard.MORDRED_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("Mordred", mordred.getName());
		assertEquals(30, mordred.getBattlePoints());
		assertEquals(30, mordred.getAltBattlePoints());

		FoeCard giant = new FoeCard(FoeCard.GIANT_NAME, FoeCard.GIANT_BATTLE_POINTS, FoeCard.GIANT_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("Giant", giant.getName());
		assertEquals(40, giant.getBattlePoints());
		assertEquals(40, giant.getAltBattlePoints());

		FoeCard dragon = new FoeCard(FoeCard.DRAGON_NAME, FoeCard.DRAGON_BATTLE_POINTS, FoeCard.DRAGON_ALT_BATTLE_POINTS, new NoSpecial());
		assertEquals("Dragon", dragon.getName());
		assertEquals(50, dragon.getBattlePoints());
		assertEquals(70, dragon.getAltBattlePoints());

	
	
		//INSTRUCTIONS:
					// give cards variable name ie AllyCard sirGalahad = new AllyCard(...);
					// assert all relevant attributes (add getters to Class if necessary)
					// Do this for all allies, amours, story deck and rank cards
		
		// ALLIES
		new AllyCard(AllyCard.SIR_GALAHAD_NAME, AllyCard.SIR_GALAHAD_BATTLE_POINTS, new NoSpecial());
		new AllyCard(AllyCard.SIR_LANCELOT_NAME, AllyCard.SIR_LANCELOT_BATTLE_POINTS, new NoSpecial());
		new AllyCard(AllyCard.KING_ARTHUR_NAME, AllyCard.KING_ARTHUR_BATTLE_POINTS, new BidSpecial(AllyCard.KING_ARTHUR_BIDS));
		new AllyCard(AllyCard.SIR_TRISTAN_NAME, AllyCard.SIR_TRISTAN_BATTLE_POINTS, new NoSpecial());
		new AllyCard(AllyCard.KING_PELLINORE_NAME, AllyCard.KING_PELLINORE_BATTLE_POINTS, new NoSpecial());// SOMETIMES SPECIAL and name is different
		new AllyCard(AllyCard.SIR_GAWAIN_NAME, AllyCard.SIR_GAWAIN_BATTLE_POINTS, new NoSpecial());
		new AllyCard(AllyCard.SIR_PERCIVAL_NAME, AllyCard.SIR_PERCIVAL_BATTLE_POINTS, new NoSpecial());
		new AllyCard(AllyCard.QUEEN_GUINEVERE_NAME, AllyCard.QUEEN_GUINEVERE_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_GUINEVERE_BIDS));
		new AllyCard(AllyCard.QUEEN_ISEULT_NAME, AllyCard.QUEEN_ISEULT_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_ISEULT_BIDS));
		new AllyCard(AllyCard.MERLIN_NAME, AllyCard.MERLIN_BATTLE_POINTS, new NoSpecial());
		
		// AMOUR
		new AmourCard(new BidSpecial(AmourCard.AMOUR_BIDS));
		
		//fail("Not yet implemented");
	}

}