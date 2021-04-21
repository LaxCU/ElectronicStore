
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;

public class ElectronicStoreApp extends Application {
     Pane aPane = new Pane();
     ElectronicStore techStore;
    ArrayList<Product> stock;
    public void start(Stage primaryStage){
        //Initilizing Store & storage methods
        ElectronicStoreView screenPanel = new ElectronicStoreView();
        techStore = ElectronicStore.createStore();
        stock = techStore.getStock();
        HashMap <Product, Integer> soldProducts = new HashMap<Product,Integer>();
        ArrayList<Product> cart = new ArrayList<Product>();
        ArrayList<Integer> cartQty = new ArrayList<Integer>();
        final int[] saleNum = {0};

        //Setting up the screen
        screenPanel.setStockDisplay(stockDisplaySorter());
        screenPanel.setPopularItems(popularRanking(soldProducts));
        screenPanel.getAddCart().setDisable(true);
        screenPanel.getDelCart().setDisable(true);
        screenPanel.getFinishSale().setDisable(true);
        screenPanel.setSalesDisplay(0);
        screenPanel.setAvgDisplay("N/A");
        screenPanel.setRevenueDisplay("0");

        //Starting the screen
        aPane.getChildren().addAll(screenPanel);
        primaryStage.setResizable(false);
        primaryStage.setTitle(techStore.getName());
        primaryStage.setScene(new Scene(aPane, 800,400));
        primaryStage.show();

        //When the a product in stock is clicked, add button is enabled.
    screenPanel.getStockDisplay().setOnMousePressed(mouseEvent -> {
        screenPanel.getAddCart().setDisable(false);
    });

    //Adds items to cart, updates screens as necessary
    screenPanel.getAddCart().setOnAction(actionEvent -> {
         Product p = (Product) screenPanel.getStockDisplay().getSelectionModel().getSelectedItem();
         int indexp;
         if (cart.contains(p)){
             indexp = cart.indexOf(p);
             cartQty.set(indexp, cartQty.get(indexp)+1);
         }
         else{
             cart.add(p);
             cartQty.add(1);
         }
        p.setAvailableQuanity(false);
        if (p.getAvailableQuanity() == 0) {
            screenPanel.getAddCart().setDisable(true);
            screenPanel.setStockDisplay(stockDisplaySorter());
        }
         screenPanel.setCartDisplay(cartDisplaySortyer(cart,cartQty));
         screenPanel.getFinishSale().setDisable(false);
        screenPanel.setCurrentCart(cartTotal(cart,cartQty));
    });

    screenPanel.getCartDisplay().setOnMousePressed(mouseEvent -> {
        screenPanel.getDelCart().setDisable(false);

    });

    //Removes items from cart, updates screens as necessary
    screenPanel.getDelCart().setOnAction(actionEvent -> {
        int index = screenPanel.getCartDisplay().getSelectionModel().getSelectedIndex();
        Product p = cart.get(index);
        cartQty.set(index, cartQty.get(index)-1);
        if (cartQty.get(index) == 0) {
            cart.remove(p);
            cartQty.remove(index);
            //screenPanel.getDelCart().setDisable(true);
        }
        p.setAvailableQuanity(true);
        if (p.getAvailableQuanity() == 1)
            screenPanel.setStockDisplay(stockDisplaySorter());
        if (cart.size() == 0)
            screenPanel.getFinishSale().setDisable(true);

        screenPanel.setCartDisplay(cartDisplaySortyer(cart,cartQty));
        screenPanel.getDelCart().setDisable(true);
        screenPanel.setCurrentCart(cartTotal(cart,cartQty));

    });

    //Completes Sale , updates screens and resets various variables
    screenPanel.getFinishSale().setOnAction(actionEvent -> {
        for (Product p: cart){
            int index = techStore.getIndex(p);
            techStore.sellProducts(index, cartQty.get(cart.indexOf(p)));

            if (soldProducts.containsKey(p)){
                soldProducts.put(p,soldProducts.get(p)+cartQty.get(cart.indexOf(p)));
            }
            else
                soldProducts.put(p,cartQty.get(cart.indexOf(p)));
        }
        cart.clear();
        cartQty.clear();
        saleNum[0]++;
        screenPanel.setPopularItems(popularRanking(soldProducts));
        screenPanel.setSalesDisplay(saleNum[0]);
        Double revenue = techStore.getRevenue();
        screenPanel.setRevenueDisplay(String.format("%.2f", revenue));

        double sum = techStore.getRevenue()/saleNum[0];
        screenPanel.setAvgDisplay(String.format("%.2f", sum));
        screenPanel.setCartDisplay(cartDisplaySortyer(cart,cartQty));
        screenPanel.getFinishSale().setDisable(true);
        screenPanel.getDelCart().setDisable(true);
        screenPanel.setCurrentCart(cartTotal(cart,cartQty));
    });

    //Sets the screen to initial screen
    screenPanel.getReset().setOnAction(actionEvent -> {
        saleNum[0] = 0;

        cart.clear();
        cartQty.clear();
        soldProducts.clear();
        soldProducts.keySet().clear();
        soldProducts.values().clear();

        screenPanel.setPopularItems(popularRanking(soldProducts));
        screenPanel.getAddCart().setDisable(true);
        screenPanel.getDelCart().setDisable(true);
        screenPanel.getFinishSale().setDisable(true);
        screenPanel.setSalesDisplay(0);
        screenPanel.setAvgDisplay("N/A");
        screenPanel.setRevenueDisplay("0");
        screenPanel.setCurrentCart(cartTotal(cart,cartQty));
        cart.clear();
        cartQty.clear();
        techStore = ElectronicStore.createStore();
        stock = techStore.getStock();
        screenPanel.setStockDisplay(stockDisplaySorter());
        screenPanel.setCartDisplay(cartDisplaySortyer(cart,cartQty));
        saleNum[0] = 0;

    });

    }

    public static void main(String[] args) {
        launch(args);
    }

    //Method to rank the 3 most popular product based on final sale
    public ArrayList<Product> popularRanking(HashMap<Product,Integer> items) {
        ArrayList<Pair<Integer, Product>> popular = new ArrayList<Pair<Integer, Product>>();
        Pair<Integer, Product> p1;
        for (Product p : items.keySet()) {
            p1 = new Pair<Integer, Product>(items.get(p), p);
            popular.add(p1);
        }
        Collections.sort(popular, new Comparator<Pair<Integer, Product>>() {
            public int compare(Pair<Integer, Product> p1, Pair<Integer, Product> p2) {
                // PUT YOUR CODE IN HERE
                return p2.getKey().compareTo(p1.getKey());
            }
        });
        int count = 3;
        ArrayList<Product> productRank = new ArrayList<Product>();
            for (Pair<Integer, Product> pairs : popular) {
                if (count > 0) {
                    productRank.add(pairs.getValue());
                    count--;
                }
            }
        if (popular.size() < 3){
            int extra = 3 - productRank.size();
            for (Product p: productRank){
                stock.remove(p);
            }
            for (int i = 0; i < extra; i++){
                productRank.add(stock.get(i));
            }
        }
        return productRank;
    }

    //Returns the total of products in cart
    public String cartTotal(ArrayList<Product> cart, ArrayList<Integer> cartQty){
        double revenue = 0.00;
        for (Product p: cart){
            revenue += p.getPrice() * cartQty.get(cart.indexOf(p));
        }
        String revenueFormatted = String.format("%.2f", revenue);

        return revenueFormatted;
    }
    //Returns an arraylist of products to displays the stock correctly by accounting for items in cart
    public ArrayList<Product> stockDisplaySorter(){
        ArrayList<Product> sorted = new ArrayList<Product>();
        for (Product p : stock) {
            if (p.getAvailableQuanity() > 0) {
                sorted.add(p);
            }
        }
        return sorted;
    }

    //COnverts the items in the cart to a string to stack multiple occurance of a product
    public ArrayList<String> cartDisplaySortyer(ArrayList<Product> cart, ArrayList cartQty){
        ArrayList<String> organized = new ArrayList<String>();
        for (Product p: cart)
            organized.add(cartQty.get(cart.indexOf(p))+" x "+p.toString());
        return organized;
    }
}



