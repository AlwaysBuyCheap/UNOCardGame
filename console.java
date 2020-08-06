package elemen;


public class console{
    public static void main(String[] args) {
        UNOFunctions functions = new UNOFunctions();
        functions.BuildDeck();
        functions.Shuffle();
        functions.DealCards(6);
        Boolean end = false;
    

        functions.PlayingCard = functions.deck.get(0);
        
        if(functions.deck.get(0).number.equals(functions.SpecialCards[3]) || functions.deck.get(0).number.equals(functions.SpecialCards[4])){
            functions.PlayingColor = functions.color_types[functions.Random.nextInt(4)];
        }
        functions.CardPlayed = false;
        
        while(true){
            
            
            for(functions.player = 0; functions.player < functions.PlayersCards.size(); functions.player++){
                System.out.print("\n playing card" + functions.PlayingCard.color + "   " + functions.PlayingCard.number);
                System.out.println("\n player" + (functions.player+1) + "\n");
                
                for(int i = 0; i < functions.PlayersCards.get(functions.player).size(); i++){
                    System.out.println(functions.PlayersCards.get(functions.player).get(i).color + "   " + functions.PlayersCards.get(functions.player).get(i).number);
                }
                functions.GotCard = false;
                if(functions.CardPlayed == true){
                    functions.SpecialCardAlreadyPlayed();
                }
                else{
                    
                    functions.SpecialCardNotPlayed();
                }
            
                functions.sleep(0);
            }        
            for(int z = 0; z < functions.PlayersCards.size(); z++){
                
                
                if(functions.PlayersCards.get(z).size() == 1){
                    System.out.println(String.format("\n the player %s has 1 card left",z+1));
                }
                if(functions.PlayersCards.get(z).size() == 0){
                    System.out.println(String.format("\n the player %s won",z+1));
                    end = true;
                }
            }
            if(end == true){
                break;
            }

            if(functions.deck.size()<10){
                for(int e= 0 ; e<functions.CardsPlayed.size();e++){
                    functions.deck.add(functions.CardsPlayed.get(0));
                    functions.CardsPlayed.remove(0);
                }
            }
        }
        
    }    
}    