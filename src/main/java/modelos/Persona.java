/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.StringProperty;

/**
 *
 * @author vicen
 */
public class Persona extends RecursiveTreeObject<Persona>
{
    private StringProperty PersonaID,Nombre,Apellido;

    public Persona(String PersonaID,String Nombre, String Apellido)
    {
        this.PersonaID = new SimpleStringProperty(PersonaID);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Apellido = new SimpleStringProperty(Apellido);
    }

    public String getPersonaID() {
        return PersonaID.get();
    }

    public void  setpersonaID(String PersonaID) {
        this.PersonaID = new SimpleStringProperty(PersonaID);
    }



    public String getNombre() {
        return Nombre.get();
    }

    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }

    public String getApellido() {
        return Apellido.get();
    }

    public void setApellido(String Apellido) {
        this.Apellido = new SimpleStringProperty(Apellido);
    }
    
    
    
}
