package elemen;
import java.util.*;
import java.security.SecureRandom;



public class UNOFunctions{
    final String color_types[] = {"Red","Yellow","Green","Blue"};
    final String numbers[] = {"0","1","2","3","4","5","6","7","8","9"};
    final String SpecialCards[] = {"Jump next player","Switch direction","+2","Change color","+4 and change color"};
    ArrayList<CardsType>deck = new ArrayList<CardsType>();
    ArrayList<ArrayList<CardsType>>PlayersCards = new ArrayList<ArrayList<CardsType>>();
    ArrayList<CardsType> CardsPlayed = new ArrayList<CardsType>();
    SecureRandom Random = new SecureRandom();
    CardsType PlayingCard;
    Boolean GotCard;
    Boolean CardPlayed;
    String PlayingColor;
    int player;
    int card;
    int IteratorStart;
    
    public ArrayList<CardsType> BuildDeck(){
        
        for(int i=0; i<color_types.length; i++){
            for(int j=0; j<2; j++){
                for(int k=0; k<numbers.length; k++){
                    if(j == 1 && k == 0){
                        ;
                    }
                    else{
                        deck.add(new CardsType(color_types[i], numbers[k]));
                    }      
                }
                for(int k=0; k<SpecialCards.length; k++){
                    if(j == 1 && k == 3 || j == 1 && k == 4){
                        ;
                    }
                    else{
                        if(SpecialCards[k].equalsIgnoreCase("Change color")){
                            deck.add(new CardsType("Change color", SpecialCards[k]));
                        }
                        else if(SpecialCards[k].equals("+4 and change color")){
                            deck.add(new CardsType("+4 and change color", SpecialCards[k]));
                        }
                        else{
                            deck.add(new CardsType(color_types[i], SpecialCards[k]));
                        }    
                    }    
                }
            }
        }
        return this.deck;
    }

    public ArrayList<CardsType> Shuffle(){
        for(int i=0;i<this.deck.size();i++){
            Collections.shuffle(this.deck);
        }
        return this.deck;    
    }

    public ArrayList<ArrayList<CardsType>> DealCards(int NumberPlayers){
        for (int i = 0; i < NumberPlayers; i++){
            ArrayList<CardsType> a = new ArrayList<>();
            for(int j = 0; j < 8; j++){
                a.add(this.deck.get(0));
                this.deck.remove(0);
            }
            this.PlayersCards.add(a);
        }
        return PlayersCards;
    }

    public void SpecialCardNotPlayed(){
        if(this.PlayingCard.number.equals(this.SpecialCards[0])){
            ;
        }
        else if(this.PlayingCard.number.equals(this.SpecialCards[1])){
            Collections.reverse(PlayersCards);
        }
        else if(this.PlayingCard.number.equals(this.SpecialCards[2])){
            this.AddMultipleCards(2);
        }
        else if(this.PlayingCard.number.equals(this.SpecialCards[3])){
            this.SpecialCardAlreadyPlayed();
        }
        else if(this.PlayingCard.number.equals(this.SpecialCards[4])){
            this.AddMultipleCards(4);
        }
        this.CardPlayed = true;
    }

    public void AddMultipleCards(int NumberOfCards){
        for(int p = 0; p < NumberOfCards; p++){
            this.PlayersCards.get(player).add(this.deck.get(0));
            this.deck.remove(0);
        }
    }

    public void SpecialCardAlreadyPlayed(){
        IteratorStart = 0;
        FindCardToPlay(); 
        if(this.GotCard == false){
            this.PlayersCards.get(player).add(deck.get(0)); 
            this.deck.remove(0);
            IteratorStart = this.PlayersCards.get(player).size() - 1;
            FindCardToPlay();
        }
    }

    public void FindCardToPlay(){
        SearchForSameColorInDeck();
        if(this.GotCard == false){  
            SearchForSameNumberInDeck();
        }
        if(this.GotCard == false){ 
            SearchForSpecialCardsInDeck();
        }
    }

    public void SearchForSameColorInDeck(){
        for(card = IteratorStart; card < this.PlayersCards.get(player).size(); card++){
            if(this.PlayersCards.get(player).get(card).color.equals(this.PlayingColor)){
                this.PlayCard();
                break;
            }
        }
    }

    public void SearchForSameNumberInDeck(){
        for(card = IteratorStart; card < this.PlayersCards.get(player).size(); card++){
            if(this.PlayersCards.get(player).get(card).number.equals(this.PlayingCard.number)){
                this.PlayCard();
                break;
            }
        }
    }

    public void SearchForSpecialCardsInDeck(){
        for(card = IteratorStart; card < this.PlayersCards.get(player).size(); card++){
            if(this.PlayersCards.get(player).get(card).color.equals(this.SpecialCards[3]) || this.PlayersCards.get(player).get(card).color.equals(this.SpecialCards[4])){
                this.PlayCard();
                break;
            }
        }
    }

    public void PlayCard(){
        this.CardsPlayed.add(this.PlayingCard);
        this.PlayingCard = this.PlayersCards.get(player).get(card);
        this.AssingnPlayingColor();
        System.out.print(String.format("\n The player %s played %s   %s", player+1,this.PlayersCards.get(player).get(card).color,this.PlayersCards.get(player).get(card).number));
        this.PlayersCards.get(player).remove(card);
        this.GotCard = true;
        for(int y = 0;y<SpecialCards.length;y++){
            if(this.PlayingCard.number.equals(SpecialCards[y])){
                this.CardPlayed = false;
            }
        }
    }

    public void AssingnPlayingColor(){
        if(this.PlayingCard.number.equals(this.SpecialCards[3]) || this.PlayingCard.number.equals(this.SpecialCards[4])){
            this.PlayingColor = this.color_types[this.Random.nextInt(4)];
        }
        else{
            this.PlayingColor = this.PlayingCard.color;
        }
    }

    public void PlayYourself(){

    }
    public void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }
}

class CardsType{

    String color;
    String number;

    public CardsType(final String input_color, final String input_number){

        this.color = input_color;
        this.number = input_number;
    }
}
