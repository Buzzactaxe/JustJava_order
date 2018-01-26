package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Find the users name
        EditText nameField = findViewById(R.id.name_text);
        String name = nameField.getText().toString();

        // Checkbox for cream
        CheckBox whippedCreamCheckBox = findViewById(R.id.check_Box_whippedCream);
        Boolean checkBoxCream = whippedCreamCheckBox.isChecked();

        // Checkbox for Chocolate
        CheckBox chocolateCheckBox = findViewById(R.id.check_box_chocolate);
        Boolean checkBoxChocolate = chocolateCheckBox.isChecked();

        // Calculates price
        int price = calculatePrice(checkBoxCream, checkBoxChocolate);
        String priceMessage = createOrederSummary(name, price, checkBoxCream, checkBoxChocolate);

        // Sends the order to Email screen
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     *
     * @param whippedCreamPrice if th customer chooses Whipped Cream or not
     * @param chocolatePrice    if th customer chooses chocolate or not
     */
    private int calculatePrice(boolean whippedCreamPrice, boolean chocolatePrice) {
        int basePrice = 5;

        if (whippedCreamPrice) {
            basePrice = basePrice + 1;
        }
        if (chocolatePrice) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * displays the order summery on the screen
     *
     * @param name            of customer
     * @param price           of order
     * @param addWhippedcream is weather or not a customer wants whipped cream
     * @param addChocolate    is weather or not a customer wants chocolate
     * @return text summary
     */
    private String createOrederSummary(String name, int price, boolean addWhippedcream, boolean addChocolate) {
        String PriceMessage = "Name: " + name;
        PriceMessage += "\n" + getString(R.string.Quantity) + quantity;
        PriceMessage += "\n" + getString(R.string.Add_whipped_cream) + addWhippedcream;
        PriceMessage += "\n" + getString(R.string.Add_chocolate) + addChocolate;
        PriceMessage += "\n"+ getString(R.string.total) + price;
        PriceMessage += "\n" + getString(R.string.Thank);
        return PriceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Shows an error message as a toast
            Context context = getApplicationContext();
            CharSequence text = "Sorry, you cannot order more than 100 coffees!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Exit the method early
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Shows an error message as a toast
            Context context = getApplicationContext();
            CharSequence text = "Sorry, you cannot order less than 1 coffee!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Exit the method early
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }


    //all about the changes

}

