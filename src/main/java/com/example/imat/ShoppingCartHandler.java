package com.example.imat;

import se.chalmers.cse.dat216.project.*;

import java.util.List;

public class ShoppingCartHandler {
    IMatDataHandler dataHandler;

    public ShoppingCartHandler() {
        dataHandler = IMatDataHandler.getInstance();
    }

    public int getAmountInCart(Product product){
        int ctr = 0;
        for (ShoppingItem shoppingItem :
                dataHandler.getShoppingCart().getItems()) {
            if(shoppingItem.getProduct() == product){
                ctr+=shoppingItem.getAmount();
            }
        }
        return ctr;
    }

    public void addProductToCart(Product product){
        Boolean founditem = false;
        for (ShoppingItem shoppingItem :
                dataHandler.getShoppingCart().getItems()) {
            if(shoppingItem.getProduct()==product) {
                shoppingItem.setAmount(shoppingItem.getAmount()+1);
                founditem = true;
            }
        }
        if(!founditem){
            dataHandler.getShoppingCart().addProduct(product);
        }
    }

    public void removeProductFromCart(Product product, Boolean cartIsOpen){
        for (ShoppingItem shoppingItem :
                dataHandler.getShoppingCart().getItems()) {
            if(shoppingItem.getProduct() == product){
                shoppingItem.setAmount(shoppingItem.getAmount()-1);
                if(shoppingItem.getAmount() <= 0){
                    if(cartIsOpen)
                        shoppingItem.setAmount(0);
                    else
                        dataHandler.getShoppingCart().removeItem(shoppingItem);
                }
            }
        }
    }

    public void updateShoppingCart(){
        List<ShoppingItem> shoppingItems = dataHandler.getShoppingCart().getItems();

        for (ShoppingItem shoppingItem: shoppingItems) {
            if(shoppingItem.getAmount() <= 0){
                dataHandler.getShoppingCart().removeItem(shoppingItem);
            }
        }
    }
}
