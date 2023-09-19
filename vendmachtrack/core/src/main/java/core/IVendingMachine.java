package core; 
import java.util.HashMap;

public interface IVendingMachine {
    public HashMap<IItem, Integer> getStatus(); //iterer gjennom hashmap og skriver nedover med tilhørende antall
    public String getLocation(); 
    
    //public void refillItem(); //oppdaterer antall colaer, eks. fra lager hvis tomt. evt hvis vi venter med lageret så dropper vi dette
    //public void removeItem(String item); //
    //public double getIncome(String item); //henter inn samlet inntekt på brusautomaten

    //oppdatere hvis 
    //se inntjening
    //legge til brusautomat (navn lokasjon osv)
    //privat metode i denne klassen som heter refill som fyller på fra lager når det er tomtx
    
}
