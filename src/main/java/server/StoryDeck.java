package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StoryDeck extends CardCollection<StoryCard>{

	private static final Logger logger = LogManager.getLogger(StoryDeck.class);

	
	public StoryDeck() {
		super();
		
		logger.info("New Story Deck Created");
		// Quest
		cards.add(new QuestCard(QuestCard.BOAR_HUNT,QuestCard.BOAR_HUNT_STAGES,QuestCard.BOAR_HUNT_FOE));
		cards.add(new EventCard(EventCard.PROSPERITY_NAME));
		cards.add(new EventCard(EventCard.CHIVALROUS_DEED_NAME));
		cards.add(new QuestCard(QuestCard.HOLY_GRAIL,QuestCard.HOLY_GRAIL_STAGES,QuestCard.HOLY_GRAIL_FOE));
		cards.add(new QuestCard(QuestCard.BOAR_HUNT,QuestCard.BOAR_HUNT_STAGES,QuestCard.BOAR_HUNT_FOE));
		cards.add(new QuestCard(QuestCard.GREEN_KNIGHT,QuestCard.GREEN_KNIGHT_STAGES,QuestCard.GREEN_KNIGHT_FOE));
		cards.add(new QuestCard(QuestCard.QUESTING_BEAST,QuestCard.QUESTING_BEAST_STAGES,QuestCard.QUESTING_BEAST_FOE));
		cards.add(new QuestCard(QuestCard.QUEENS_HONOR,QuestCard.QUEENS_HONOR_STAGES,QuestCard.QUEENS_HONOR_FOE));
		cards.add(new QuestCard(QuestCard.FAIR_MAIDEN,QuestCard.FAIR_MAIDEN_STAGES,QuestCard.FAIR_MAIDEN_FOE));
		cards.add(new QuestCard(QuestCard.ENCHANTED_FORST,QuestCard.ENCHANTED_FORST_STAGES,QuestCard.ENCHANTED_FORST_FOE));
		cards.add(new QuestCard(QuestCard.ARTHURS_ENEMIES,QuestCard.ARTHURS_ENEMIES_STAGES,QuestCard.ARTHURS_ENEMIES_FOE));
		cards.add(new QuestCard(QuestCard.ARTHURS_ENEMIES,QuestCard.ARTHURS_ENEMIES_STAGES,QuestCard.ARTHURS_ENEMIES_FOE));
		cards.add(new QuestCard(QuestCard.SLAY_DRAGON,QuestCard.SLAY_DRAGON_STAGES,QuestCard.SLAY_DRAGON_FOE));
		cards.add(new QuestCard(QuestCard.SAXON_RAIDERS,QuestCard.SAXON_RAIDERS_STAGES,QuestCard.SAXON_RAIDERS_FOE));
		cards.add(new QuestCard(QuestCard.SAXON_RAIDERS,QuestCard.SAXON_RAIDERS_STAGES,QuestCard.SAXON_RAIDERS_FOE));
		
		// Tournament
		cards.add(new TournamentCard(TournamentCard.CAMELOT_NAME, TournamentCard.CAMELOT_SHIELDS));
		cards.add(new TournamentCard(TournamentCard.ORKNEY_NAME, TournamentCard.ORKNEY_SHIELDS));
		cards.add(new TournamentCard(TournamentCard.TINTAGEL_NAME, TournamentCard.TINTAGEL_SHIELDS));
		cards.add(new TournamentCard(TournamentCard.YORK_NAME, TournamentCard.YORK_SHIELDS));

		// Event
		cards.add(new EventCard(EventCard.KINGS_RECOGNITION_NAME));
		cards.add(new EventCard(EventCard.KINGS_RECOGNITION_NAME));
		cards.add(new EventCard(EventCard.QUEENS_FAVOR_NAME));
		cards.add(new EventCard(EventCard.QUEENS_FAVOR_NAME));
		cards.add(new EventCard(EventCard.COURT_CAMELOT_NAME));
		cards.add(new EventCard(EventCard.COURT_CAMELOT_NAME));
		cards.add(new EventCard(EventCard.POX_NAME));
		cards.add(new EventCard(EventCard.PLAGUE_NAME));
//		cards.add(new EventCard(EventCard.CHIVALROUS_DEED_NAME));
//		cards.add(new EventCard(EventCard.PROSPERITY_NAME));
		cards.add(new EventCard(EventCard.CALL_ARMS_NAME));

	}

	public static void main(String args[]){
		StoryDeck aD = new StoryDeck();
	}
	
}

