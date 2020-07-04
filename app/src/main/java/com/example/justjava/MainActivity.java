package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int numberOfCoffees = 0;
    private int availableNumberOfCoffees = 100;
    private Boolean addChocolate = false;
    private Boolean addWhippedCream = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void incrementQuantity(View view)
    {
        if(availableNumberOfCoffees>0) {
            numberOfCoffees += 1;
            availableNumberOfCoffees -=1;
        }
        displayQuantity(numberOfCoffees);
    }

    public void decrementQuantity(View view)
    {
        if(numberOfCoffees>=1) {
            numberOfCoffees -= 1;
            availableNumberOfCoffees += 1;
        }
        displayQuantity(numberOfCoffees);
    }

    public void submitOrder(View view)
    {
        if(numberOfCoffees>0) {
            EditText nameTextView = findViewById(R.id.name);
            CheckBox addWhippedCreamCheckBox = findViewById(R.id.addWhippedCream);
            CheckBox addChocolateCheckBox = findViewById(R.id.addChocolate);
            addChocolate = addChocolateCheckBox.isChecked();
            addWhippedCream = addWhippedCreamCheckBox.isChecked();
            String price = calculatePrice(numberOfCoffees);
            String message = "Name: " + nameTextView.getText().toString();
            message += "\nAdd whipped cream? " + addWhippedCream;
            message += "\nAdd chocolate? " + addChocolate;
            message += "\nQuantity: " + numberOfCoffees;
            message += "\nTotal: " + price;
            message += "\nThank you!";
//            displayMessage(message);
            Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
            mailIntent.setData(Uri.parse("mailto:"));
            String[] toAddress = {"praneeth.74064@gmail.com"};
            mailIntent.putExtra(Intent.EXTRA_EMAIL, toAddress);
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order details");
            mailIntent.putExtra(Intent.EXTRA_TEXT, message);
            if (mailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mailIntent);
            } else
                Log.e("mail intent:", "no intent found");
        }
    }

    private void displayQuantity(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText(String.format(Locale.getDefault(),"%d", number));
    }

//    public void displayMessage(String message)
//    {
//        TextView priceTextView = (TextView) findViewById(R.id.price);
//        priceTextView.setText(message);
//    }

    public String calculatePrice(int number)
    {
        int chocolatePrice = 2;
        int whippedCreamPrice = 1;
        int coffeePrice = 5;
        int total = coffeePrice*number;
        if(addWhippedCream)
            total += whippedCreamPrice*number;
        if(addChocolate)
            total += chocolatePrice*number;
        if(total==0)
            return "Free";
        return NumberFormat.getCurrencyInstance().format(total);
    }
}