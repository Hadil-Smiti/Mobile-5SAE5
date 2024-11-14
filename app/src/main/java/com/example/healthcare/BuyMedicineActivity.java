package com.example.healthcare;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {


    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Database db;
    Button btnBack, btnGoToCart, buttonAddMedicine;
    EditText editTextMedicineName, editTextMedicinePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        lst = findViewById(R.id.listViewDD);
        btnBack = findViewById(R.id.buttonBMBack);
        btnGoToCart = findViewById(R.id.buttonLTDAddToCart);
        buttonAddMedicine = findViewById(R.id.buttonAddMedicine);
        editTextMedicineName = findViewById(R.id.editTextMedicineName);
        editTextMedicinePrice = findViewById(R.id.editTextMedicinePrice);

        db = new Database(this, "healthcare", null, 1);

        btnBack.setOnClickListener(view -> startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class)));
        btnGoToCart.setOnClickListener(view -> startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class)));

        buttonAddMedicine.setOnClickListener(view -> addMedicine());

        list = new ArrayList<>();
        refreshListView();
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = (HashMap<String, String>) list.get(position);
                String medicineName = selectedItem.get("line1");
                String totalCost = selectedItem.get("line5").replace("Total Cost: ", "").replace("/-", "");

                Intent intent = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
                intent.putExtra("text1", medicineName);
                intent.putExtra("text2", "Details about " + medicineName);
                intent.putExtra("text3", totalCost);
                startActivity(intent);
            }
        });

    }

    private void addMedicine() {
        String name = editTextMedicineName.getText().toString();
        float price = Float.parseFloat(editTextMedicinePrice.getText().toString());
        db.addCart("default_user", name, price, "medicine");
        refreshListView();
    }


    private void deleteMedicine(String medicineName) {
        db.removeCart("default_user", medicineName);
        refreshListView();
    }


    private void refreshListView() {
        list.clear();
        ArrayList<String> cartData = db.getCartData("default_user", "medicine");
        for (String data : cartData) {
            String[] parts = data.split("\\$");
            item = new HashMap<>();
            item.put("line1", parts[0]);
            item.put("line5", "Total Cost: " + parts[1] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line5"},
                new int[]{R.id.line_a, R.id.line_e}) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView trashIcon = view.findViewById(R.id.trash_icon);

                // Set OnClickListener for the trash icon
                trashIcon.setOnClickListener(v -> {
                    HashMap<String, String> selectedItem = (HashMap<String, String>) list.get(position);
                    String medicineName = selectedItem.get("line1");
                    showConfirmationDialog(medicineName);
                });

                return view;
            }
        };

        lst.setAdapter(sa);
    }
    private void showConfirmationDialog(String medicineName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Medicine")
                .setMessage("Are you sure you want to delete " + medicineName + " from the list?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteMedicine(medicineName);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

}