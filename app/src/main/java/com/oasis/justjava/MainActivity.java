package com.oasis.justjava;

import android.content.Intent;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int mQuantity = 1, mBasePricePerCup = 20, mWhippedCreamToppingPrice = 5,
            mChocolateToppingPrice = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view){

        //displayPrice(mQuantity * 5);
        Button orderButton = (Button) findViewById(R.id.order_button);
        if(mQuantity <=0){
            orderButton.setText(getString(R.string.sorry_no_order));
        }
        else{
            orderButton.setText(getString(R.string.order_placed));

            EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
            String name = nameEditText.getText().toString();


            CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
            boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

            CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
            boolean hasChocolate = chocolateCheckbox.isChecked();

            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("plain/text");
            sendIntent.setData(Uri.parse(getString(R.string.email_id)));
            sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            //sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java_order) + name);
            sendIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            startActivity(sendIntent);

            //displayMessage(priceMessage);

        }


    }

    /**
     * Creates order of the summary.
     * @param name is the name of the user
     * @param price is the total price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){

        String priceMessage = getString(R.string.name_java, name);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + mQuantity +
                "\n" + "Total: " + "₹" + price + "\nThank you!";

        return priceMessage;

    }



    /**
     * *This method resets the current order
     */

    public void resetOrder(View view){
        mQuantity = 1;
        display(mQuantity);
        //displayPrice(mQuantity);

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        nameEditText.setText("");

        CheckBox whippweCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        whippweCreamCheckbox.setChecked(false);

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolateCheckbox.setChecked(false);

        Button mOrderButton = (Button) findViewById(R.id.order_button);
        mOrderButton.setText("Order");
    }

    /**
     *This method is called when the + Button is clicked.
     * It increments the quantity by 1.
     */
    public void increment(View view){
        if(mQuantity >= 100) {
            Toast.makeText(this, "Exceeds the maximum limit", Toast.LENGTH_SHORT).show();
        }

        else{
            mQuantity = mQuantity + 1;
            display(mQuantity);
        }
        //displayPrice(mQuantity * 5);
    }

    /**
     * This method is called when the - Button is clicked.
     * It decrements the quantity by 1.
     */

    public void decrement(View view){
        if(mQuantity <= 1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }

        else{
            mQuantity = mQuantity - 1;
            display(mQuantity);
        }

        //displayPrice(mQuantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+ number);
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

    /**
     * This method calculates price of the coffee for given quantity and returns it.
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){


        if(hasWhippedCream){
            mBasePricePerCup = mBasePricePerCup + mWhippedCreamToppingPrice;

        }

        if(hasChocolate){
            mBasePricePerCup = mBasePricePerCup + mChocolateToppingPrice;
        }



        int price = mBasePricePerCup * mQuantity;
        return price;
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText("" + message);
//    }
}
