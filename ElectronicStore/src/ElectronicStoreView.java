import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class ElectronicStoreView extends Pane{
    private Pane StoreGUI;
    private Button reset, addCart, delCart, finishSale;
    private Label storeSum, storeStock, currentCart, totalSales, revenue, avgSale, popularItems;
    private ListView salesDisplay, revenueDisplay, avgDisplay, popularDisplay, stockDisplay, cartDisplay;
    public ElectronicStoreView(){
        //Settings up the store display
        StoreGUI = new Pane();

        //Settings up Labels title
        storeSum = new Label("Store Summary:");
        storeStock = new Label("Store Stock:");
        currentCart = new Label("Current Cart ($0.00)");
        totalSales = new Label("# Sales: ");
        revenue = new Label("Revenue: ");
        avgSale = new Label("$ / Sale:");
        popularItems = new Label("Most Popular Items:");

        //Setting up Buttons
        reset = new Button("Reset Store");
        addCart = new Button("Add to Cart");
        delCart = new Button("Remove from Cart");
        finishSale = new Button("Complete Sale");

        //Settings up ListView
        salesDisplay = new ListView<Integer>();
        revenueDisplay = new ListView<String>();
        avgDisplay= new ListView<String>();
        popularDisplay= new ListView<String>();
        stockDisplay= new ListView<Product>();
        cartDisplay= new ListView<String>();

        //Settings Size & Relocation
        storeSum.relocate(30,10);
        storeStock.relocate(300,10);
        currentCart.relocate(600,10);

        totalSales.relocate(25,40);
        salesDisplay.setPrefSize(90,25);
        salesDisplay.relocate(70,40);

        revenue.relocate(17,70);
        revenueDisplay.setPrefSize(90,25);
        revenueDisplay.relocate(70,70);


        avgSale.relocate(23,100);
        avgDisplay.setPrefSize(90,25);
        avgDisplay.relocate(70,100);


        popularItems.relocate(25,130);

        popularDisplay.setPrefSize(150,180);
        popularDisplay.relocate(10,160);

        stockDisplay.setPrefSize(300,300);
        stockDisplay.relocate(170,40);

        cartDisplay.setPrefSize(300,300);
        cartDisplay.relocate(480,40);

        reset.setPrefSize(120,40);
        reset.relocate(20,350);

        addCart.setPrefSize(120,40);
        addCart.relocate(260,350);

        delCart.setPrefSize(120,40);
        delCart.relocate(505,350);
        finishSale.setPrefSize(120,40);
        finishSale.relocate(635,350);

        StoreGUI.getChildren().addAll(storeSum,storeStock,currentCart,totalSales,revenue,avgSale,popularItems,popularDisplay,reset,salesDisplay,revenueDisplay,avgDisplay,stockDisplay,cartDisplay,addCart,delCart,finishSale);
        getChildren().addAll(StoreGUI);
    }
    //Displays the Stock Display
    public void setStockDisplay(ArrayList<Product> products) {
        stockDisplay.setItems(FXCollections.observableArrayList(products));
    }
    //Displays the popular Items
    public void setPopularItems(ArrayList<Product> products){
        popularDisplay.setItems(FXCollections.observableArrayList(products));
    }
    //Displays the items in the cart
    public void setCartDisplay(ArrayList<String> organized){
        cartDisplay.setItems(FXCollections.observableArrayList(organized));
    }


    //getters methods
    public Button getAddCart() {
        return addCart;
    }

    public Button getDelCart() {
        return delCart;
    }

    public Button getFinishSale() {
        return finishSale;
    }
    public Button getReset() {
        return reset;
    }


    public ListView getStockDisplay() {
        return stockDisplay;
    }

    public ListView getCartDisplay() {
        return cartDisplay;
    }

    //Sets the display for number of sales
    public void setSalesDisplay(int count) {
        ArrayList<String> sales = new ArrayList<String>();
        sales.add(count+"");
        salesDisplay.setItems(FXCollections.observableArrayList(sales));

    }
    //sets the display for total revenue
    public void setRevenueDisplay(String amount){
        revenueDisplay.getItems().clear();
        revenueDisplay.getItems().add(amount);

    }
    //Displays the avg revenue per sale
    public void setAvgDisplay(String avgSale) {
        avgDisplay.getItems().clear();
        avgDisplay.getItems().add(avgSale);
    }
    //Displays the current value of the cart
    public void setCurrentCart(String amount){
        currentCart.setText("Current Cart ($"+amount+")");
    }
}
