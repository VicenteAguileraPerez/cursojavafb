package com.tablescoder.cursojavafb;

import Firebase.CRUDFirebase;
import Notificaciones.Notificationes;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelos.Persona;

public class FXMLController implements Initializable {

    private Persona p=null;
    private ObservableList<Persona> listaPersonas = FXCollections.observableArrayList();
    private ObservableList<Persona> listaPersonasFiltrada = FXCollections.observableArrayList();
    private  Notificationes notificationes;
    @FXML
    private Label label_estado;
    @FXML
    private JFXTreeTableView<Persona> tabla;
    @FXML
    private TreeTableColumn<Persona, String> nombre;

    @FXML
    private TreeTableColumn<Persona, String> apellido;

    @FXML
    private JFXTextField textfield_nombre;

    @FXML
    private JFXTextField textfield_apellido;
    @FXML
    private JFXTextField textFieldBuscar;

    @FXML
    private JFXRadioButton radioButtonNombre;

    @FXML
    private ToggleGroup busqueda;

    @FXML
    private JFXRadioButton radioButtonApellido;
    
    private final CRUDFirebase cRUDFirebase = new CRUDFirebase();
    @FXML
    void add(ActionEvent event)
    {
       if(!textfield_nombre.getText().equals("") && !textfield_apellido.getText().equals(""))
        {
            //Vicente
            Persona persona = new Persona(UUID.randomUUID().toString(),textfield_nombre.getText().trim(), textfield_apellido.getText().trim());
            if(cRUDFirebase.addFirebase(persona))
            {
                notificationes.Notificacion("Éxito","Dato agregado",1);
                label_estado.setText("Dato agregado");
                listaPersonas.add(persona);
                setupTabla(listaPersonas);
                cleanFields();
            }
            else
            {
                notificationes.Notificacion("Error","Dato no agregado",2);
                label_estado.setText("Dato no agregado");
            }
            
        }
       else
       {
           notificationes.Notificacion("Información","Campos vacíos",3);
       }
    }

    @FXML
    void delete(ActionEvent event)
    {
        if(!textfield_nombre.getText().equals("") && !textfield_apellido.getText().equals("") )
        {
            if(p!=null)
            {

                if(cRUDFirebase.deleteFirebase(p.getPersonaID()))
                {
                    //refreshTabla();
                    listaPersonas.remove(tabla.getSelectionModel().getSelectedItem().getValue());
                    notificationes.Notificacion("Éxito","Dato eliminado",1);
                    label_estado.setText("Dato eliminado");
                }
                else
                {
                    notificationes.Notificacion("Error","Dato no eliminado",2);
                    label_estado.setText("Dato no eliminado");
                }

            }
            else
            {
                notificationes.Notificacion("Información","No Puedo editar, porque no ha seleccionado una fila en la tabla",3);
                System.out.println("No Puedo editar, porque no ha seleccionado una fila en la tabla");
            }
        }
        else
        {
            notificationes.Notificacion("Información","No Puedo editar, porque el campo nombre o el campo apellido están vacíos",3);
            System.out.println("No Puedo editar, porque el campo nombre o el campo apellido están vacíos");
        }
        cleanFields();
        p=null;
        tabla.getSelectionModel().clearSelection();
    }

    @FXML
    void edit(ActionEvent event)
    {

        if(!textfield_nombre.getText().equals("") && !textfield_apellido.getText().equals("") )
        {
            if(p!=null)
            {
                System.out.println("Puedo editar");
                if(cRUDFirebase.updateFirebase(p.getPersonaID(),textfield_nombre.getText().trim(),textfield_apellido.getText().trim()))
                {
                    System.out.println("Si se editó");
                    if(!textfield_nombre.getText().trim().equals(p.getNombre()) || !textfield_apellido.getText().trim().equals(p.getApellido()))
                    {
                        //refrecar los datos reeleer los datos de firebase (Internet)
                        //refreshTabla();

                        //local
                        //refreshTablaLocal(tabla.getSelectionModel().getSelectedIndex(),textfield_nombre.getText().trim(),textfield_apellido.getText().trim());

                        //forma local 2
                        p.setNombre(textfield_nombre.getText().trim());
                        p.setApellido(textfield_apellido.getText().trim());
                        listaPersonas.set(tabla.getSelectionModel().getSelectedIndex(),p);
                        notificationes.Notificacion("Éxito","Dato editado",1);
                        label_estado.setText("Dato editado");
                    }
                    else
                    {
                        notificationes.Notificacion("Información","Dato es el mismo",3);

                        label_estado.setText("Dato es el mismo");
                    }
                }
                else
                {
                    notificationes.Notificacion("Error","Dato no editado",2);
                    System.out.println("No se editó");
                }

                cleanFields();
                p=null;
                tabla.getSelectionModel().clearSelection();
            }
            else
            {
                notificationes.Notificacion("Información","No Puedo editar, porque no ha seleccionado una fila en la tabla",3);
                System.out.println("No Puedo editar, porque no ha seleccionado una fila en la tabla");
            }
        }
        else
        {
            notificationes.Notificacion("Información","No Puedo editar, porque el campo nombre o el campo apellido están vacíos",3);
            System.out.println("No Puedo editar, porque el campo nombre o el campo apellido están vacíos");
        }

    }

    private void refreshTablaLocal(int indice, String Nombre, String Apellido)
    {
        p.setNombre(Nombre);
        p.setApellido(Apellido);
        tabla.getTreeItem(indice).setValue(p);
        tabla.refresh();
    }

    private void refreshTabla()
    {
        listaPersonas.clear();
        read();
    }

    @FXML
    void getRow(MouseEvent event)
    {
        int indice = tabla.getSelectionModel().getSelectedIndex();
        System.out.println(indice);
        if(indice>-1) {
            p = tabla.getTreeItem(indice).getValue();
            textfield_nombre.setText(p.getNombre());
            textfield_apellido.setText(p.getApellido());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        notificationes = new Notificationes();

       setupColumns();
       textFieldBuscarListener();
        read();
    }

    private void textFieldBuscarListener()
    {
        textFieldBuscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->
        {

            tabla.setRoot(null);
            if (newValue.equals(""))
            {
                setupTabla(listaPersonas);
                System.out.println("vacio");
            }
            else
            {
                System.out.println(newValue);
                listaPersonasFiltrada.clear();
                switch (busqueda())
                {
                    case 'a':
                        for (Persona persona : listaPersonas)
                        {
                            if(persona.getApellido().toLowerCase().startsWith(newValue.toLowerCase()))
                            {
                                listaPersonasFiltrada.add(persona);
                            }
                        }
                        setupTabla(listaPersonasFiltrada);
                        break;
                    case 'n':
                        for (Persona persona : listaPersonas)
                        {
                            if(persona.getNombre().toLowerCase().startsWith(newValue.toLowerCase()))
                            {
                                listaPersonasFiltrada.add(persona);
                            }
                        }
                        setupTabla(listaPersonasFiltrada);
                        break;
                    default:
                         setupTabla(listaPersonas);
                        notificationes.Notificacion("Información","Seleccione por qué desea buscar.",3);

                        System.out.println("Seleccione un radio button");
                         break;
                }
            }
        });
    }

    private char busqueda()
    {
       if(radioButtonApellido.isSelected())
       {
            return 'a';
       }
       else if(radioButtonNombre.isSelected())
       {
            return 'n';
       }
       else
       {
           return 's';
       }
    }



    ///metodos
    private void read()
    {
        if(cRUDFirebase.readFirebase())
        {
           System.out.println("No hubo errores");
           ObservableList<Persona> listaPersonas = cRUDFirebase.getListaPersonas();

           if(listaPersonas.size()>0)
           {

               this.listaPersonas=listaPersonas;
               setupTabla(this.listaPersonas);

           }

       }
       else
       {
           notificationes.Notificacion("Error","Error de conexión, verifique conexión a internet",2);
           System.out.println("Error");
       }
    }
    private void setupColumns() {
       nombre.setCellValueFactory(new TreeItemPropertyValueFactory<Persona,String>("Nombre"));
       apellido.setCellValueFactory(new TreeItemPropertyValueFactory<Persona,String>("Apellido"));
    }

    private void setupTabla(ObservableList<Persona> listaPersonas) {
        TreeItem<Persona> value = new RecursiveTreeItem<>(listaPersonas, (RecursiveTreeObject<Persona> recursiveTreeObject) -> recursiveTreeObject.getChildren());
        tabla.setRoot(value);
        tabla.setShowRoot(false);
    }
    private void cleanFields()
    {
        textfield_nombre.setText("");
        textfield_apellido.setText("");
    }
    
}






/**
 * tabla.setRoot(null);
 *                 listaPersonasFiltrada.clear();
 *
 *                 for(Models.Servicio servicioActual : listaServicios)
 *                 {
 *                 //if(newValue.contains( servicioActual.getId_servicio()+"" ) )
 *                 if(servicioActual.getTelefono().contains(newValue))
 *                 {
 *                 listaServiciosFiltro.add(servicioActual);
 *                 }
 *                 }
 *                 TreeItem<Models.Servicio> root1 = new RecursiveTreeItem<>(listaServiciosFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
 *                 table_servicios.setRoot(root1);
 *                 table_servicios.setShowRoot(false);
 */














/*
 p.setApellido(textfield_apellido.getText().trim());
                        p.setNombre(textfield_nombre.getText().trim());


                        tabla.getTreeItem(tabla.getSelectionModel().getSelectedIndex()).setValue(p);
                        tabla.refresh();
if(!textfield_nombre.getText().equals("") && !textfield_apellido.getText().equals("") )
        {
            if(indice>-1)
            {

               // Persona p = tabla.getTreeItem(indice).getValue();

                if(cRUDFirebase.updateFirebase(p.getPersonaID(),p.getNombre(),p.getApellido()))
                {
                    System.out.println("Puedo editar");
                }
                else
                {
                    System.out.println("Error");
                }
                tabla.getSelectionModel().clearSelection();
            }
            else
            {
                System.out.println("No Puedo editar, porque no se selecciono fila");
            }
        }
        else
        {
            System.out.println("No Puedo editar, porque el campo nombre o apellido está vacío");
        }

 if(!textfield_nombre.getText().equals("") && !textfield_apellido.getText().equals(""))
        {
            // Vicente
            Persona persona = new Persona(textfield_nombre.getText().toUpperCase().trim(), textfield_apellido.getText().toUpperCase().trim());
            if(cRUDFirebase.addFirebase(persona))
            {
                label_estado.setText("Dato agregado");
            }
            else
            {   
                label_estado.setText("Dato no agregado");
            }
            
        }
*/
/*
 private ObservableList<?> lista = FXCollections.observableArrayList();


     edit buttton
     if(tabla.getSelectionModel().getSelectedIndex()>-1)
        {
            System.out.println(tabla.getTreeItem(tabla.getSelectionModel().getSelectedIndex()).getValue().getNombre());
            tabla.getSelectionModel().clearSelection();
        }
       mousecliked

       @FXML
    void getRow(MouseEvent event) {
        int indice = tabla.getSelectionModel().getSelectedIndex();
        System.out.println(indice);
        textfield_nombre.setText(tabla.getTreeItem(indice).getValue().getNombre());
        textfield_apellido.setText(tabla.getTreeItem(indice).getValue().getApellido());

    }
    css
     -fx-text-fill:-color-white;

       */

