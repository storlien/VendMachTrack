package core;

public class Item implements IItem{

    private String name;
    private double price;


    public Item(String name, double price) {
        if (price <= 0){
            throw new IllegalArgumentException("price can't be negative");
        }
        this.name = name;
        this.price = price;
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public double getPrice() {
        return this.price;
    }



    
}
