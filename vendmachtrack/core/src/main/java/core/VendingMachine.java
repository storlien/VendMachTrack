package core;

import java.util.HashMap;

public class VendingMachine implements IVendingMachine {

    private HashMap<String, Integer> status = new HashMap<>();

    @Override
    public HashMap<String, Integer> getStatus() {
        return new HashMap<>(this.status);
    }

    


    // public void addItem(IItem item, int number){ //fylle opp hvis noen kjøper, vil evt fyllle opp fra fil... dvs. lese fra fil

    //     if (!status.containsKey(item)){
    //         status.put(item, 0); 
    //     }
    //     status.put(item, status.get(item) + number);
    // }


    // public void removeItem(IItem name, int number){ //fjerne hvis sen kjøper kjøper et produkt
    //     if (!status.containsKey(name)){
    //         throw new IllegalArgumentException("there is no item to remove");
    //         }
    //     if (status.get(name) == number){
    //         status.remove(name);
    //         }
    //     if (status.get(name) > number){
    //         status.put(name, status.get(name) - number);
    //         }
        
    //     }


            
    }


