package com.example.kevin.proyectofinal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView tvAdivinar, tvCurrentCat;
    private String letterChoice, hidenWord, currentGuess;
    private String [] arrComida, arrSalud, arrDep, arrNatu, arrEntret, arrVest;
    private Button btnClicked;
    private Integer nroErrores;
    private Boolean [] arrVisible;
    private Boolean Acierto, Ganador;
    private HangerView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        nroErrores=0;
        Acierto =Ganador= false;
        letterChoice = currentGuess="";
        myView = (HangerView) findViewById(R.id.HangManView);

        //se inicializan los arreglos que contienen las palabras que van a ser usadas como jueg
        arrComida = new String[]{"Pera", "Manzana", "Ceviche", "Parrillada", "Fanesca", "Colada", "Fritada", "Aceitunas",
                "Humita", "Cerveza"};
        arrDep = new String[] {"Futbol", "Ecuavoley", "Tenis", "Beisbol", "Karting", "Marcha", "Baloncesto", "Squash",
                "Gimnasia", "Atletismo" };
        arrEntret = new String[]{"Cine", "Television", "Netflix", "Videojuegos", "Musica", "Casino", "Naipe",
                "Nintendo", "Concierto", "Discoteca"};
        arrNatu = new String[]{"Rinoceronte", "Manzanilla", "Petroleo", "Aguila", "Mapache", "Cesped", "Manada",
                "Madriguera", "Savila", "Ortiga"};
        arrSalud = new String[] {"Estetoscopio", "Medicina", "Hipertension", "Anatomia", "Cerebro", "Corazon",
                "Ibuprofeno", "Vacuna", "Vitaminas", "Musculos"};
        arrVest = new String[]{"Camiseta", "Zapatos", "Correa", "Vestido", "Chaleco", "Sandalias", "Tacones", "Pantalones",
                "Sombrero", "Sudadera"};

        Random loteria = new Random();
        //de acuerdo a la categoria elegida se escoge el arreglo del que se va a sacar al azar una palabra para jugar
        if(singleton.getInstance().getSelectedCat().equalsIgnoreCase("comida")) //caso comida
            hidenWord = arrComida[loteria.nextInt(10)];
        if(singleton.getInstance().getSelectedCat().equalsIgnoreCase("salud")) //caso salud
            hidenWord = arrSalud[loteria.nextInt(10)];
        if(singleton.getInstance().getSelectedCat().equalsIgnoreCase("deportes")) // caso deportes
            hidenWord = arrDep[loteria.nextInt(10)];
        if(singleton.getInstance().getSelectedCat().equalsIgnoreCase("vestimenta")) //caso vestimenta
            hidenWord = arrVest[loteria.nextInt(10)];
        if(singleton.getInstance().getSelectedCat().equalsIgnoreCase("entretenimiento")) //caso entretenimiento
            hidenWord = arrEntret[loteria.nextInt(10)];
        if(singleton.getInstance().getSelectedCat().equalsIgnoreCase("naturaleza")) //caso naturaleza
            hidenWord = arrNatu[loteria.nextInt(10)];

        singleton.getInstance().setHiddenWord(hidenWord);
        arrVisible = new Boolean[hidenWord.length()];
        //se inicializa currentGuess, que es el txto del text view del juego y arr Visible que indica que caracteres ya se adivinaron
        for (int i =0; i<hidenWord.length();i++)
        {
            currentGuess = currentGuess+"_";
            arrVisible[i]=false;
        }
        //Se escribe el texto de la categoria en el textView que sirve como pista
        tvCurrentCat = (TextView) findViewById(R.id.tvCategory);
        tvCurrentCat.setText(singleton.getInstance().getSelectedCat());

        //Se escribe en el txview las palabras a adivinar
        tvAdivinar = (TextView) findViewById(R.id.txtPalabra);
        tvAdivinar.setLetterSpacing((float) 0.5);
        tvAdivinar.setText(currentGuess);
    }

    public void myLetterChoice(View v)
    {
        btnClicked = (Button) findViewById(v.getId());
        letterChoice = (String) btnClicked.getText();
        for(int j=0; j<hidenWord.length();j++)
        {
            String tempChar = String.valueOf(hidenWord.charAt(j));//se ecoge solo un caracter de la palabra escondida para comparar con la letra seleccionada
            if(letterChoice.equalsIgnoreCase(tempChar))
            {
                arrVisible[j]=true;
                Acierto= true;
            }
        }
        //se vuelve a construir el String currentGuess
        currentGuess = "";
        for (int k=0 ; k<arrVisible.length; k++)
        {
            if(arrVisible[k])
                currentGuess= currentGuess+hidenWord.charAt(k);
            else
                currentGuess= currentGuess+"_";
        }
        //se pinta el nuevo currentGuess en el Textview
        tvAdivinar.setText(currentGuess);

        //Si no se acerto, se suma 1 error
        if(!Acierto)
            nroErrores++;
        singleton.getInstance().setNroErrores(nroErrores);
        Acierto=false;
        btnClicked.setEnabled(false);

        //se comprueba si hay ganador
        for (int m = 0; m<arrVisible.length;m++)
        {
            //si uno de los miembros del arreglo es falso entonces el ganador queda como falso
            if (!arrVisible[m])
            {
                Ganador=false;
                break;
            }
            else
            {
                Ganador=true;
            }
        }
        if(Ganador)
        {
            finish();
            Intent nextActivity = new Intent(GameActivity.this,ResultActivity.class);//se lanza nueva actividad
            nextActivity.putExtra("Ganador",Ganador);
            startActivity(nextActivity);
        }

        //se comprueba si hay perdedor
        if(nroErrores>=6)
        {
            finish();
            Intent nextActivity = new Intent(GameActivity.this,ResultActivity.class); //se lanza nueva actividad
            nextActivity.putExtra("Ganador",Ganador);
            startActivity(nextActivity);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myView.pause();
    }
}
