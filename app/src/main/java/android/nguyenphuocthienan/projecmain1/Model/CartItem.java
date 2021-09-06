package android.nguyenphuocthienan.projecmain1.Model;

import java.util.Objects;

public class CartItem {
    private Drink drink;
    private int quantity;

    public CartItem(Drink drink, int quantity) {
        this.drink = drink;
        this.quantity = quantity;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "drink=" + drink +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return getQuantity() == cartItem.getQuantity() && getDrink().equals(cartItem.getDrink());
    }

}
