package core;
public class Deck extends CardCollection{
	
//	Deck(){
//		for(int i = 0; i < DISTICTADVCARDS; ++i){
//			
//		}
//	}


	public Deck() {
		for(int i = 0; i < WeaponCard.NUMBER_SWORDS; ++i){
			cards.add(new WeaponCard(WeaponCard.SWORD, WeaponCard.SWORD_BATTLE_POINTS));
		}
		
		for(int i = 0; i < WeaponCard.NUMBER_DAGGER; ++i){
			cards.add(new WeaponCard(WeaponCard.DAGGER, WeaponCard.DAGGER_BATTLE_POINTS));
		}
		
		for(int i = 0; i < WeaponCard.NUMBER_HORSE; ++i){
			cards.add(new WeaponCard(WeaponCard.HORSE, WeaponCard.HORSE_BATTLE_POINTS));
		}
		
		for(int i = 0; i < WeaponCard.NUMBER_BATTLE_AX; ++i){
			cards.add(new WeaponCard(WeaponCard.BATTLE_AX, WeaponCard.BATTLE_AX_BATTLE_POINTS));
		}
		
		for(int i = 0; i < WeaponCard.NUMBER_LANCE; ++i){
			cards.add(new WeaponCard(WeaponCard.LANCE, WeaponCard.LANCE_BATTLE_POINTS));
		}
		
		for(int i = 0; i < WeaponCard.NUMBER_EXCALIBUR; ++i){
			cards.add(new WeaponCard(WeaponCard.EXCALIBUR, WeaponCard.EXCALIBUR_BATTLE_POINTS));
		}
		
		
	}
	
}
