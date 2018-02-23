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
		// Do this for all allies, amours,

//Story Deck --> Event - Quest -Tournament


//Tournament Test 3 2 1 0

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

		TournamentCard camelot = new TournamentCard(TournamentCard.CAMELOT_NAME,TournamentCard.CAMELOT_SHIELDS);
		assertEquals("AtCamelot", camelot.getName());
		assertEquals(3, camelot.getNumShields());

		TournamentCard orkney = new TournamentCard(TournamentCard.ORKNEY_NAME,TournamentCard.ORKNEY_SHIELDS);
		assertEquals("AtOrkney", orkney.getName());
		assertEquals(2, orkney.getNumShields());

		TournamentCard tintagel = new TournamentCard(TournamentCard.TINTAGEL_NAME,TournamentCard.TINTAGEL_SHIELDS);
		assertEquals("AtTintagel", tintagel.getName());
		assertEquals(1, tintagel.getNumShields());

		TournamentCard york = new TournamentCard(TournamentCard.YORK_NAME,TournamentCard.YORK_SHIELDS);
		assertEquals("AtYork", york.getName());
		assertEquals(0, york.getNumShields());


QuestCard holygrail = new QuestCard(QuestCard.HOLY_GRAIL,	QuestCard.HOLY_GRAIL_STAGES,	QuestCard.HOLY_GRAIL_FOE);
assertEquals("SearchForTheHolyGrail", holygrail.getName());
assertEquals(5, holygrail.getNumStages());
assertEquals("All", holygrail.getFoe());


QuestCard greenknight = new QuestCard(QuestCard.GREEN_KNIGHT,	QuestCard.GREEN_KNIGHT_STAGES,	QuestCard.GREEN_KNIGHT_FOE);
assertEquals("TestOfTheGreenKnight", greenknight.getName());
assertEquals(4, greenknight.getNumStages());
assertEquals("Green Knight", greenknight.getFoe());

QuestCard qbeast = new QuestCard(QuestCard.QUESTING_BEAST,	QuestCard.QUESTING_BEAST_STAGES,	QuestCard.QUESTING_BEAST_FOE);
assertEquals("SearchForTheQuestingBeast", qbeast.getName());
assertEquals(4, qbeast.getNumStages());
assertEquals("None", qbeast.getFoe());

QuestCard qhonor = new QuestCard(QuestCard.QUEENS_HONOR,	QuestCard.QUEENS_HONOR_STAGES,	QuestCard.QUEENS_HONOR_FOE);
assertEquals("DefendTheQueensHonor", qhonor.getName());
assertEquals(4, qhonor.getNumStages());
assertEquals("All", qhonor.getFoe());

QuestCard maiden = new QuestCard(QuestCard.FAIR_MAIDEN,	QuestCard.FAIR_MAIDEN_STAGES,	QuestCard.FAIR_MAIDEN_FOE);
assertEquals("RescueTheFairMaiden", maiden.getName());
assertEquals(3, maiden.getNumStages());
assertEquals("Black Knight", maiden.getFoe());

QuestCard enchanted = new QuestCard(QuestCard.ENCHANTED_FORST,	QuestCard.ENCHANTED_FORST_STAGES,	QuestCard.ENCHANTED_FORST_FOE);
assertEquals("JourneyThroughTheEnchantedForest", enchanted.getName());
assertEquals(3, enchanted.getNumStages());
assertEquals("Evil Knight", enchanted.getFoe());

QuestCard karthur = new QuestCard(QuestCard.ARTHURS_ENEMIES,	QuestCard.ARTHURS_ENEMIES_STAGES,	QuestCard.ARTHURS_ENEMIES_FOE);
assertEquals("VanquishKingArthursEnemies", karthur.getName());
assertEquals(3, karthur.getNumStages());
assertEquals("None", karthur.getFoe());

QuestCard slaygragon = new QuestCard(QuestCard.SLAY_DRAGON,	QuestCard.SLAY_DRAGON_STAGES,	QuestCard.SLAY_DRAGON_FOE);
assertEquals("SlayTheDragon", slaygragon.getName());
assertEquals(3, slaygragon.getNumStages());
assertEquals("Dragon", slaygragon.getFoe());

QuestCard boarhunt = new QuestCard(QuestCard.BOAR_HUNT,	QuestCard.BOAR_HUNT_STAGES,	QuestCard.BOAR_HUNT_FOE);
assertEquals("BoarHunt", boarhunt.getName());
assertEquals(2, boarhunt.getNumStages());
assertEquals("Boar", boarhunt.getFoe());

QuestCard saxonraiders = new QuestCard(QuestCard.SAXON_RAIDERS,	QuestCard.SAXON_RAIDERS_STAGES,	QuestCard.SAXON_RAIDERS_FOE);
assertEquals("RepelTheSaxonRaiders", saxonraiders.getName());
assertEquals(2, saxonraiders.getNumStages());
assertEquals("All Saxons", saxonraiders.getFoe());


//Rank Card
/*
RankCard knight = new RankCard(RankCard.KNIGHT);
// RankCard knight = new RankCard("Knight");
assertEquals(10,knight.getBattlePoints());

RankCard squire = new RankCard(RankCard.SQUIRE);
//RankCard squire = new RankCard("Squire");
assertEquals(5,squire.getBattlePoints());


RankCard championknight = new RankCard(RankCard.CHAMPION_KNIGHT);
//RankCard championknight = new RankCard("ChampionKnight");
assertEquals(20,championknight.getBattlePoints());


*/

// ALLIES
//new AllyCard(AllyCard.SIR_GALAHAD_NAME, AllyCard.SIR_GALAHAD_BATTLE_POINTS, new NoSpecial());
AllyCard galahad = new AllyCard(AllyCard.SIR_GALAHAD_NAME, AllyCard.SIR_GALAHAD_BATTLE_POINTS, new NoSpecial());
assertEquals("SirGalahad", galahad.getName());
assertEquals(15, galahad.getBattlePoints());

//	new AllyCard(AllyCard.SIR_LANCELOT_NAME, AllyCard.SIR_LANCELOT_BATTLE_POINTS, new NoSpecial());
AllyCard lancelot = new AllyCard (AllyCard.SIR_LANCELOT_NAME, AllyCard.SIR_LANCELOT_BATTLE_POINTS, new NoSpecial());
assertEquals("SirLancelot", lancelot.getName());
assertEquals(15, lancelot.getBattlePoints());

//	 new AllyCard(AllyCard.KING_ARTHUR_NAME, AllyCard.KING_ARTHUR_BATTLE_POINTS, new BidSpecial(AllyCard.KING_ARTHUR_BIDS));
AllyCard arthur = new AllyCard(AllyCard.KING_ARTHUR_NAME, AllyCard.KING_ARTHUR_BATTLE_POINTS, new BidSpecial(AllyCard.KING_ARTHUR_BIDS));
 assertEquals("KingArthur", arthur.getName());
 assertEquals(10, arthur.getBattlePoints());



//	 new AllyCard(AllyCard.SIR_TRISTAN_NAME, AllyCard.SIR_TRISTAN_BATTLE_POINTS, new NoSpecial());
AllyCard tristan = new AllyCard(AllyCard.SIR_TRISTAN_NAME, AllyCard.SIR_TRISTAN_BATTLE_POINTS,new NoSpecial());
 assertEquals("SirTristan", tristan.getName());
 assertEquals(10, tristan.getBattlePoints());

//	new AllyCard(AllyCard.SIR_PELLINORE_NAME, AllyCard.SIR_PELLINORE_BATTLE_POINTS, new NoSpecial());// SOMETIMES SPECIAL and name is different
AllyCard pellinore = new AllyCard(AllyCard.KING_PELLINORE_NAME, AllyCard.KING_PELLINORE_BATTLE_POINTS, new NoSpecial());// SOMETIMES SPECIAL and name is different
 assertEquals("KingPellinore", pellinore.getName());
 assertEquals(10, pellinore.getBattlePoints());

//	new AllyCard(AllyCard.SIR_GAWAIN_NAME, AllyCard.SIR_GAWAIN_BATTLE_POINTS, new NoSpecial());
 AllyCard gawain = new AllyCard(AllyCard.SIR_GAWAIN_NAME, AllyCard.SIR_GAWAIN_BATTLE_POINTS, new NoSpecial());
 assertEquals("SirGawain", gawain.getName());
 assertEquals(10, gawain.getBattlePoints());


//	new AllyCard(AllyCard.SIR_PERCIVAL_NAME, AllyCard.SIR_PERCIVAL_BATTLE_POINTS, new NoSpecial());
 AllyCard percival = new AllyCard(AllyCard.SIR_PERCIVAL_NAME, AllyCard.SIR_PERCIVAL_BATTLE_POINTS, new NoSpecial());
 assertEquals("SirPercival", percival.getName());
 assertEquals(5, percival.getBattlePoints());

//	new AllyCard(AllyCard.QUEEN_GUINEVERE_NAME, AllyCard.QUEEN_GUINEVERE_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_GUINEVERE_BIDS));
 AllyCard guinevere = new AllyCard(AllyCard.QUEEN_GUINEVERE_NAME, AllyCard.QUEEN_GUINEVERE_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_GUINEVERE_BIDS));
 assertEquals("QueenGuinevere", guinevere.getName());
 assertEquals(0, guinevere.getBattlePoints());


//new AllyCard(AllyCard.QUEEN_ISEULT_NAME, AllyCard.QUEEN_ISEULT_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_ISEULT_BIDS));
 AllyCard iseult = new AllyCard(AllyCard.QUEEN_ISEULT_NAME, AllyCard.QUEEN_ISEULT_BATTLE_POINTS, new BidSpecial(AllyCard.QUEEN_ISEULT_BIDS));
 assertEquals("QueenIseult", iseult.getName());
 assertEquals(0, iseult.getBattlePoints());


// new AllyCard(AllyCard.MERLIN_NAME, AllyCard.MERLIN_BATTLE_POINTS, new NoSpecial());
 AllyCard merlin =  new AllyCard(AllyCard.MERLIN_NAME, AllyCard.MERLIN_BATTLE_POINTS, new NoSpecial());
 assertEquals("Merlin", merlin.getName());
 assertEquals(0, merlin.getBattlePoints());



	}

}
