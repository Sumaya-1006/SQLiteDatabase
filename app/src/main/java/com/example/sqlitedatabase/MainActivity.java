package com.example.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDBHelper myDBHelper;
    private TextView nameEditText, ageEditText, genderEditText, idEditText;
    private Button addButton, displayAllDataButton, updateDataButton, deleteDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditTextId);
        ageEditText = findViewById(R.id.ageEditTextId);
        genderEditText = findViewById(R.id.genderEditTextId);
        addButton = findViewById(R.id.addButtonId);
        displayAllDataButton = findViewById(R.id.displayAllDataButtonId);
        updateDataButton = findViewById(R.id.updateDataButtonId);
        deleteDataButton = findViewById(R.id.deleteDataButtonId);
        idEditText = findViewById(R.id.idEditTextId);

        MyDBHelper myDBHelper = new MyDBHelper(this);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String gender = genderEditText.getText().toString();

                if (view.getId() == R.id.addButtonId) {
                    long rowId = myDBHelper.insertData(name, age, gender);

                    if (rowId > 0) {
                        Toast.makeText(getApplicationContext(), "Row " + rowId + " is Successfully inserted", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
                displayAllDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = nameEditText.getText().toString();
                        String age = ageEditText.getText().toString();
                        String gender = genderEditText.getText().toString();
                        if (view.getId() == R.id.displayAllDataButtonId);
                        {
                         Cursor cursor  = myDBHelper.displayAllData();

                         if(cursor.getCount()==0){
                             //there is no data
                             showData("Error","No Data Found");
                              return;
                         }
                         StringBuffer stringBuffer = new StringBuffer();
                         while(cursor.moveToNext()){

                             stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                             stringBuffer.append("name : "+cursor.getString(1)+"\n");
                             stringBuffer.append("age : "+cursor.getString(2)+"\n");
                             stringBuffer.append("gender : "+cursor.getString(3)+"\n\n\n");
                         }
                         showData("ResultSet",stringBuffer.toString());
                        }

                       updateDataButton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               String name = nameEditText.getText().toString();
                               String age = ageEditText.getText().toString();
                               String gender = genderEditText.getText().toString();
                               String id = idEditText.getText().toString();
                               if(view.getId() == R.id.displayAllDataButtonId);
                               boolean isUpdated = myDBHelper.updateData(id,name,age,gender);

                               if (isUpdated==true){
                                   Toast.makeText(getApplicationContext(), "successfully updated", Toast.LENGTH_SHORT).show();
                               }else{
                                   Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                               }


                               deleteDataButton.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       String name = nameEditText.getText().toString();
                                       String age = ageEditText.getText().toString();
                                       String gender = genderEditText.getText().toString();
                                       String id = idEditText.getText().toString();
                                       if(view.getId() == R.id.deleteDataButtonId);

                                       boolean isDeleted = myDBHelper.deleteData(id);

                                       if (isDeleted==true){
                                           Toast.makeText(getApplicationContext(), "successfully deleted", Toast.LENGTH_SHORT).show();
                                       }else{
                                           Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();

                                       }

                                   }
                               });

                           }
                });


                    }
                });

            }
        });
    }


    private void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }

}





