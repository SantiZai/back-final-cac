package domain.models;

import java.util.ArrayList;

public class Order {

    private int id;
    private String foods;
    private int total;

    public Order(int id, String foods, int total) {
        this.id = id;
        this.foods = foods;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
