package core; 
import java.util.HashMap;

public interface IVendingMachine {

    /**
	 *
	 * @return a hashmap of every item in the vendingmachine and quantity of every item in the machine
	 */
    public HashMap<String, Integer> getStatus(); 

    /**
	 *
	 * @return the id to the vendingmachine
	 */
    public int getId();

    /**
	 *
	 * @return the location to the vendingmachine
	 */
    public String getLocation();

    /**
	 * @param item
	 * @param number
	 * adds the number of the item to the vendingmachine
	 */
    public void addItem(String item, int number);

    /**
	 * @param item
	 * @param number
	 * removes the number of the item from the vendingmachine
	 */
    public boolean removeItem(String item, int number);


    /**
	 * sets the status
     * @param status
	 */
    public void setStatus(HashMap<String, Integer> status);
    

    /**
	 * sets the id
     * @param id
	 */
    public void setId(int id);

   
    /**
	 * sets the location
     * @param location
	 */
    public void setLocation(String location);


}
