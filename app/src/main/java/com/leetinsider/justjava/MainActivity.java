package com.leetinsider.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity{
    int quantity = 0;
    boolean orderButtonPressed = false;
    String fullOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText customerName = (EditText) findViewById(R.id.nameText);
        String name = customerName.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        displayMessage(priceMessage);

    }

    public void sendOrder(View view){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { "finaltrigger1337@gmail.com" });
        email.putExtra(Intent.EXTRA_SUBJECT, "Coffee order");
        email.putExtra(Intent.EXTRA_TEXT, fullOrder);
        email.setType("message/rfc822");
        // Verify that the intent will resolve to an activity
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        Intent email = new Intent(Intent.ACTION_SEND);
//        email.putExtra(Intent.EXTRA_EMAIL, new String[] { "finaltrigger1337@gmail.com" });
//        email.putExtra(Intent.EXTRA_SUBJECT, "Coffee order");
//        email.putExtra(Intent.EXTRA_TEXT, message);
//        email.setType("message/rfc822");
//        startActivity(email);
        orderSummaryTextView.setText(message);
        orderButtonPressed = true;
    }

    /**
     * This method increments the quantity of coffee ordered
     */
    public void increment(View view) {
        if (quantity >=100){
            Toast.makeText(this, "We cant make that many!", Toast.LENGTH_SHORT).show();
        }
        else {
            quantity++;
            displayQuantity(quantity);
        }
    }

    /**
     * This method decrements the quantity of coffee ordered
     */
    public void decrement(View view) {
        if (quantity <= 0){
            Toast.makeText(this, "Please order at least 1 cup of coffee", Toast.LENGTH_SHORT).show();
        }
        else {
            quantity--;
            displayQuantity(quantity);
        }
    }

    /**
     * Calculates the price of the order.
     * @return total price
     */
    private int calculatePrice(boolean creamTopping, boolean chocolateTopping){
        int basePrice = 5;
        if(creamTopping){
            basePrice += +1;
        }
        if(chocolateTopping){
            basePrice += 2;
        }
        int price = quantity * basePrice;
        return price;
    }

    /**
     * Creates an order summary
     * @return order summary message
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage = "Name: " + name;
        priceMessage += "\nQuantity: " + quantity + "\n";
        priceMessage += "Add whipped cream: " + addWhippedCream + "\n";
        priceMessage += "Add chocolate: " + addChocolate + "\n";
        priceMessage += "\nTotal: $ " + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\nThank You!";
        fullOrder = priceMessage; //equal fullOrder to priceMessage to be used in Sending email order

        return priceMessage;
    }
}