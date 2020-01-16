package com.example.kevin.proyectofinal;

/**
 * Created by Kevin on 26-Apr-17.
 */

class singleton {
    private static final singleton ourInstance = new singleton();

    static singleton getInstance() {
        return ourInstance;
    }

    private String selectedCat; //guarda la categoria que el usuario seleccione
    private Integer nroErrores;
    private String HiddenWord; //palabra que se esta tratando de adivinar

    private singleton() {
        nroErrores=0; //cuenta el nro de errores cometidos en el juego
        selectedCat ="";
    }

    public void setHiddenWord(String hiddenWord) {
        HiddenWord = hiddenWord;
    }

    public String getHiddenWord() {
        return HiddenWord;
    }

    public String getSelectedCat() {
        return selectedCat;
    }

    public Integer getNroErrores() {
        return nroErrores;
    }

    public void setNroErrores(Integer nroErrores) {
        this.nroErrores = nroErrores;
    }

    public void setSelectedCat(String selectedCat) {
        this.selectedCat = selectedCat;
    }
}
