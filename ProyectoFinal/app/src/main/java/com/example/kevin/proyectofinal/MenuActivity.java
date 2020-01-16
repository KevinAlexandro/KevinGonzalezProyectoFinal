package com.example.kevin.proyectofinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private String selection="";
    private Button clickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Pide permisos de escribir en el storage en caso de que no hayan sido concedidos antes
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                Toast.makeText(getApplicationContext(),"Permiso para guardar",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }

    public void myClick(View v)
    {
        clickedButton = (Button) findViewById(v.getId());
        singleton.getInstance().setSelectedCat((String) clickedButton.getText());
        Intent myIntent = new Intent(MenuActivity.this, GameActivity.class);//crea intent para la siguiente actividad
        startActivity(myIntent); //inicia la siguiente actividad
    }
}