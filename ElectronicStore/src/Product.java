//Base class for all products the store will sell
public class Product{
 private double price;
 private int stockQuantity;
 private int soldQuantity;
 private int availableQuanity;  //Measures the total available quanitty by accounting items sold & in cart.
 
 public Product(double initPrice, int initQuantity){
   price = initPrice;
   stockQuantity = initQuantity;
   availableQuanity = initQuantity;
 }
 
 public int getStockQuantity(){
   return stockQuantity;
 }
 
 public int getSoldQuantity(){
   return soldQuantity;
 }
 
 public double getPrice(){
   return price;
 }
 
 //Returns the total revenue (price * amount) if there are at least amount items in stock
 //Return 0 otherwise (i.e., there is no sale completed)
 public double sellUnits(int amount){
   if(amount > 0 && stockQuantity >= amount){
     stockQuantity -= amount;
     soldQuantity += amount;
     return price * amount;
   }
   return 0.0;
 }

 public int getAvailableQuanity() {
     return availableQuanity;
 } //Returns availableQuantity

 public void setAvailableQuanity(boolean b){ //Increases or decreases available quanitty
     if (b) {
         availableQuanity++;
     }
     else
         availableQuanity--;
 }


}