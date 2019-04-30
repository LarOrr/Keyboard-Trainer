package com.example.lar.keyboardtrainer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.about){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void doTestMenu(MenuItem item) {
//        TextView tt = findViewById(R.id.testText);
//        tt.setText("sett done");
//    }


    public void openSettings(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,
                SettingsActivity.class);
        startActivity(intent);
    }

    public void showAbout(MenuItem item) {
    // ToDO add eng translation!
        AlertDialog.Builder dialog = new
                AlertDialog.Builder(MainActivity.this);
        try {
            dialog.setMessage(getTitle().toString()+ " версия "+
                            getPackageManager().getPackageInfo(getPackageName(),0).versionName + "\r\n\n" +
                    "Автор:\nОралин Илларион Владимирович\n ВШЭ ФКН ПИ 2 курс" +
                    "\n\n2019");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        dialog.setTitle("О программе");
        dialog.setNeutralButton("OK", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.setIcon(R.mipmap.ic_launcher_round);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Created by Oralin", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @OnClick(R.id.buttonPlay)
    public void openChooseMode(View view) {
        Intent intent = new Intent(MainActivity.this,
                ChooseModeActivity.class);
        startActivity(intent);
    }
}
