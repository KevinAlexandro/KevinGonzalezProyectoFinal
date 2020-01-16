package com.example.kevin.proyectofinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kevin on 27-Apr-17.
 */

public class HangerView extends SurfaceView implements Runnable {

    private Thread hilo = null;
    private SurfaceHolder holder;
    private Canvas myCanvas;
    private Boolean isItOk=false;
    private Bitmap Dictador;

    public HangerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder=getHolder();
        resume();
        Dictador = BitmapFactory.decodeResource(getResources(), R.drawable.rcorrea);
    }

    @Override
    public synchronized void run() {
        while(isItOk)
        {
            //Para solucionar error de mucho trabajo en l UI
            try {
                wait(100); //solo que se grafique 10 veces x segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //check si la surface esta lista, si no se sale del loop
            if(!holder.getSurface().isValid())
            {
                continue;
            }
            myCanvas=holder.lockCanvas();
            myCanvas.drawARGB(255,255,255,204);
            float ancho = myCanvas.getWidth();
            float largo = myCanvas.getHeight();
            Paint myPincel = new Paint();
            myPincel.setStrokeWidth(40);
            myPincel.setColor(Color.DKGRAY);
            //Dibujo del ahorcado
            //Base
            myCanvas.drawLine((float)0.1*ancho,(float)0.7*largo,
                    (float)0.9*ancho,(float)0.7*largo,myPincel);
            //altura
            myCanvas.drawLine((float)0.35*ancho,(float)0.7*largo,
                    (float)0.35*ancho,(float)0.1*largo,myPincel);
            //Soporte superior
            myCanvas.drawLine((float)0.35*ancho,(float)0.1*largo,
                    (float)0.7*ancho,(float)0.1*largo,myPincel);
            //Horca
            myCanvas.drawLine((float)0.7*ancho,(float)0.1*largo,
                    (float)0.7*ancho,(float)0.15*largo,myPincel);
            //-----------------------Parte del Dibujo que se va aumentando conforme el juego avanza------------------//

            if(singleton.getInstance().getNroErrores()>=1)
                //cabeza
            {
                myCanvas.drawCircle((float)0.7*ancho,(float)0.215*largo,(float)0.065*largo,myPincel);
                //Descomentar la siguiente linea para usar la cara de Correa
                //myCanvas.drawBitmap(Dictador,(float)0.7*ancho - Dictador.getWidth()/2,(float)0.215*largo - Dictador.getHeight()/2,null);
            }

            if(singleton.getInstance().getNroErrores()>=2)
                //Tronco
                myCanvas.drawLine((float)0.7*ancho,(float)0.28*largo,(float)0.7*ancho,(float)0.5*largo,myPincel);

            if(singleton.getInstance().getNroErrores()>=3)
                //Brazo 1
                myCanvas.drawLine((float)0.7*ancho,(float)0.32*largo,(float)0.5*ancho,(float)0.4*largo,myPincel);

            if(singleton.getInstance().getNroErrores()>=4)
                //Brazo 2
                myCanvas.drawLine((float)0.7*ancho,(float)0.32*largo,(float)0.9*ancho,(float)0.4*largo,myPincel);

            if(singleton.getInstance().getNroErrores()>=5)
                //Pierna 1
                myCanvas.drawLine((float)0.7*ancho,(float)0.5*largo,(float)0.55*ancho,(float)0.65*largo,myPincel);

            if(singleton.getInstance().getNroErrores()>=6)
            //Pierna 2
                myCanvas.drawLine((float)0.7*ancho,(float)0.5*largo,(float)0.85*ancho,(float)0.65*largo,myPincel);

            holder.unlockCanvasAndPost(myCanvas);
        }
    }

    public void resume()
    {
        isItOk=true;
        hilo = new Thread(this);
        hilo.start();
    }

    public void pause() //para terminar con el hilo y terminar la aplicacion
    {
        isItOk=false;
        while (true)
        {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        hilo=null;
        singleton.getInstance().setNroErrores(0); //cuando la vista se destruye, entonces se prepara el nuevo juego
    }
}
