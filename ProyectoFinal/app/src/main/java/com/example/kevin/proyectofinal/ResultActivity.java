package com.example.kevin.proyectofinal;


import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView LogoWinner, LogoLoser, tvResultado, tvHidenWord;
    private Button btnNewGame, btnShare;
    private Boolean Resultado;
    private Intent sharingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Se recibe el boolean que indica si hubo o no ganador
        Resultado = getIntent().getExtras().getBoolean("Ganador");
        LogoLoser = (TextView) findViewById(R.id.tvLoser);
        LogoWinner = (TextView) findViewById(R.id.tvWinner);
        tvResultado = (TextView) findViewById(R.id.tvResultado);

        //Se escribe la palabra que estaba tratando de ser adivinada
        tvHidenWord = (TextView) findViewById(R.id.tvHiddenWord);
        tvHidenWord.setText(singleton.getInstance().getHiddenWord());

        //Se oculta la imagen que no corresponde
        if(Resultado)
        {
            tvResultado.setText("Felicidades Ganaste");
            LogoLoser.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvResultado.setText("Mejor Suerte la Proxima");
            LogoWinner.setVisibility(View.INVISIBLE);
        }

        //Configuracion del btn share
        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get root view
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                //Capturar screenshot del root view
                View scView = rootView.getRootView();
                scView.setDrawingCacheEnabled(true);
                Bitmap screenShot = Bitmap.createBitmap(scView.getDrawingCache());
                scView.setDrawingCacheEnabled(false);
                //crea el file y guarda la imagen
                final String name = "hangman" + System.currentTimeMillis() + ".jpg";
                String Location = MediaStore.Images.Media.insertImage(getContentResolver(), screenShot, name, "hangmanGame");
                //set el share string
                //1 se carga la imagen con URI
                Uri myUri = null;
                try {
                    myUri = Uri.parse(Location);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //2se crea el intent
                sharingIntent = new Intent();
                sharingIntent.setAction(Intent.ACTION_SEND);
                sharingIntent.setType("*/*");

                String shareBody = "Estoy jugando El Ahorcado by Kevin. Juega tu tambien.";
                if (Resultado) {
                    shareBody = shareBody + " He ganado adivinando la palabra: " + singleton.getInstance().getHiddenWord();
                } else {
                    shareBody = shareBody + " He perdido adivinando la palabra: " + singleton.getInstance().getHiddenWord();
                }
                //se agrega texto Subject y Body del mensaje
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "El Ahorcado");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                // se agrega la imagen al intent
                sharingIntent.putExtra(Intent.EXTRA_STREAM, myUri);
                //se crea app chooser si hay permiso de write on external storage
                LaunchChooser();
            }
        });
        //Configuracion del btn new Game
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void LaunchChooser()
    {
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
