
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
    
    ArrayList<CardsType> BuildDeck(){
        
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

    ArrayList<CardsType> Shuffle(){
        for(int i=0;i<this.deck.size();i++){
            Collections.shuffle(this.deck);
        }
        return this.deck;    
    }

    ArrayList<ArrayList<CardsType>> DealCards(int NumberPlayers){
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

    void SpecialCardNotPlayed(){
        if(this.PlayingCard.number.equals(this.SpecialCards[0])){
            ;
        }
        else if(this.PlayingCard.number.equals(this.SpecialCards[1])){
            Collections.reverse(this.PlayersCards);
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

    void AddMultipleCards(int NumberOfCards){
        for(int p = 0; p < NumberOfCards; p++){
            this.PlayersCards.get(player).add(this.deck.get(0));
            this.deck.remove(0);
        }
    }

    void SpecialCardAlreadyPlayed(){
        IteratorStart = 0;
        FindCardToPlay(); 
        if(this.GotCard == false){
            GrabCardAndCheckIfICanPlayIt();
        }
    }

    void GrabCardAndCheckIfICanPlayIt(){
        this.PlayersCards.get(player).add(deck.get(0)); 
        System.out.println("No card to play, card grabbed " + deck.get(0).color + "   " + deck.get(0).number);
        this.deck.remove(0);
        IteratorStart = this.PlayersCards.get(player).size() - 1;
        FindCardToPlay();
    }

    void FindCardToPlay(){
        SearchForSameColorInDeck();
        if(this.GotCard == false){  
            SearchForSameNumberInDeck();
        }
        if(this.GotCard == false){ 
            SearchForSpecialCardsInDeck();
        }
    }

    void SearchForSameColorInDeck(){
        for(card = IteratorStart; card < this.PlayersCards.get(player).size(); card++){
            CheckIfCardHasSameColor(this.PlayersCards.get(player).get(card).color);
            if(GotCard == true){
                break;
            }
        }
    }

    void CheckIfCardHasSameColor(String color){
        if(color.equals(this.PlayingColor)){
            this.PlayCard();
        }
    }

    void SearchForSameNumberInDeck(){
        for(card = IteratorStart; card < this.PlayersCards.get(player).size(); card++){
            CheckIfCardHasSameNumber(this.PlayersCards.get(player).get(card).number);
            if(GotCard == true){
                break;
            }    
        }
    }

    void CheckIfCardHasSameNumber(String number){
        if(number.equals(this.PlayingCard.number)){
            this.PlayCard();
        }
    }

    void SearchForSpecialCardsInDeck(){
        for(card = IteratorStart; card < this.PlayersCards.get(player).size(); card++){
            CheckIfCardIsSpecial(this.PlayersCards.get(player).get(card).color);
            if(GotCard == true){
                break;
            }    
        }
    }
    void CheckIfCardIsSpecial(String card){
        if(card.equals(this.SpecialCards[3]) || card.equals(this.SpecialCards[4])){
            this.PlayCard();
        }
    }

    void PlayCard(){
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

    void AssingnPlayingColor(){
        if(this.PlayingCard.number.equals(this.SpecialCards[3]) || this.PlayingCard.number.equals(this.SpecialCards[4])){
            this.PlayingColor = this.color_types[this.Random.nextInt(4)];
        }
        else{
            this.PlayingColor = this.PlayingCard.color;
        }
    }

    void PlayYourself(){
        if(CardPlayed == true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select the card you want to play:");
            int CardNumber = Integer.parseInt(scanner.nextLine());
            //scanner.close();
            card = CardNumber;
            
            CheckIfCardHasSameColor(this.PlayersCards.get(player).get(CardNumber).color);
            if(GotCard == false){
                CheckIfCardHasSameNumber(this.PlayersCards.get(player).get(CardNumber).number);
            }
            if(GotCard == false){
                CheckIfCardIsSpecial(this.PlayersCards.get(player).get(CardNumber).color);
            }
            if(GotCard == false){
                GrabCardAndCheckIfICanPlayIt();
            }
        }
        else{
            SpecialCardNotPlayed();
        }
        
        


    }
    void sleep(int seconds){
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

    CardsType(final String input_color, final String input_number){

        this.color = input_color;
        this.number = input_number;
    }
}
