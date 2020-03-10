package com.example.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText vHeight, vWeight;
    Button submitButton;
    Button aboutButton;
    ImageView mImageView;

    public void savePreferences(String h, String w) {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        pref.edit().putString("height", h).apply();
        pref.edit().putString("weight", w).apply();
    }

    public void loadPreferences() {
        SharedPreferences pref = getSharedPreferences("BMI", MODE_PRIVATE);
        vHeight.setText(pref.getString("height", "0"));
        vWeight.setText(pref.getString("weight", "0"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPreferences();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-- get views 
        vHeight = (EditText) findViewById(R.id.heightET);
        vWeight = (EditText) findViewById(R.id.weightET);
        submitButton = (Button) findViewById(R.id.reportBtn);
        aboutButton = (Button) findViewById(R.id.aboutBtn);
        mImageView = (ImageView) findViewById(R.id.imageView);
        registerForContextMenu(mImageView);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = vHeight.getText().toString();
                String weight = vWeight.getText().toString();
                if (height.equals("") || weight.equals("")) {
                    Toast.makeText(MainActivity.this, R.string.bmi_warning,
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("height", height);
                    bundle.putString("weight", weight);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                savePreferences(height, weight);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.about_button);
                builder.setMessage(R.string.about_msg);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    // --- Option Menu ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                openOptionsDialog();
                return true;
            case R.id.menu_wiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return false;
    }


    public void openOptionsDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.menu_about)
                .setMessage(R.string.about_msg)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                            }
                        }).show();
    }

    // --- Context Menu ---
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                openOptionsDialog();
                return true;
            case R.id.menu_wiki:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://en.wikipedia.org/wiki/Body_mass_index"));
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}


