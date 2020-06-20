/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tablescoder.cursojavafb.MainApp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelos.Persona;

/**
 *
 * @author vicen
 */
public class CRUDFirebase 
{
    ///add delete Update
    //add
    private boolean key;
    private ObservableList<Persona> listaPersonas = FXCollections.observableArrayList();
    private Persona persona;
    public ObservableList<Persona> getListaPersonas() {
        return listaPersonas;
    }
    
    public boolean addFirebase(Persona persona) {
        key=false;
        // Create a Map to store the data we want to set
        //    key    value
        Map<String, Object> docPersona = new HashMap<>();
        docPersona.put("Nombre",persona.getNombre());
        docPersona.put("Apellido", persona.getApellido());
        
        // Add a new document (asynchronously) in collection "cities" with id "LA"
        ApiFuture<WriteResult> future = MainApp.bd.collection("Personas").document(persona.getPersonaID()).set(docPersona);
        try 
        {
            System.out.println("Add time : " + future.get().getUpdateTime());
            key=true;
            
        } 
        catch (InterruptedException | ExecutionException ex) 
        {
           ex.printStackTrace();
        }





        return key;
    }
    
    public boolean readFirebase()
    {
        key = false;

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future =  MainApp.bd.collection("Personas").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try 
        {
            documents = future.get().getDocuments();
            if(documents.size()>0)
            {
                System.out.println("Leyendo datos....");
                for (QueryDocumentSnapshot document : documents) 
                {
                    System.out.println(document.getId() + " => " + document.getData().get("Nombre"));
                    persona  = new Persona(document.getId(),String.valueOf(document.getData().get("Nombre")), document.getData().get("Apellido").toString());
                    listaPersonas.add(persona);
                }
            }
            else
            {
               System.out.println("No hay datos que leer"); 
            }
            key=true;
            
        }
        catch (InterruptedException | ExecutionException ex) 
        {
             ex.printStackTrace();
        }


        return key;
    }
    public boolean updateFirebase(String PersonaID,String Nombre,String Apellido)
    {
        key=false;

        // Update an existing document
        DocumentReference docRef = MainApp.bd.collection("Personas").document(PersonaID);
        try {
            // (async) Update one field
            ApiFuture<WriteResult> future = docRef.update(
                    //Campo       , Valor
                    "Nombre",Nombre,
                    "Apellido",Apellido
            );
            System.out.println("Update: " + future.get().getUpdateTime());
            key=true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        return key;
    }
    public boolean deleteFirebase(String PersonaID)
    {
        key=false;

        try {
            // asynchronously delete a document
            ApiFuture<WriteResult> writeResult = MainApp.bd.collection("Personas").document(PersonaID).delete();
            // ...
            System.out.println("Delete time : " + writeResult.get().getUpdateTime());
            key=true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return key;
    }























    /*
    *  DocumentReference docRef = MainApp.bd.collection("Personas").document("619f5bf7-f6a0-4535-8970-1293fdc199fc");

        // Add document data with an additional field ("middle")
        Map<String, Object> docPersona = new HashMap<>();
        docPersona.put("Nombre",persona.getNombre());
        docPersona.put("Apellido", persona.getApellido());


        ApiFuture<WriteResult> result = docRef.update("Nombre",persona.getNombre());
        try {
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    *
    *
    * */


   
           
}





















/*

public boolean updateFirebase(String documents, String nombre, String apellido)  {

         key=false;


         return key;
     }
ApiFuture<WriteResult> writeResult = MainApp.bd.collection("Personas").document(documents).delete();
// ...
         try {
             System.out.println("Update time : " + writeResult.get().getUpdateTime());
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }
 public boolean readFirabse()
    {
        boolean key=false;
         ApiFuture<QuerySnapshot> future = MainApp.bd.collection("Personas").get();
    // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents)
            {
                Persona persona = new Persona(document.getData().get("Nombre").toString(), document.getData().get("Apellido").toString()) ;
                System.out.println(document.getId() +"=>" +persona.getNombre());
            }
            key=true;
        } catch (InterruptedException ex)
        {
            System.out.println("Interr");
            ex.printStackTrace();
            key=false;
        } catch (ExecutionException ex) {
            System.out.println("Ex");
            ex.printStackTrace();
            key=false;
        }
      
        return key;
    }
*/