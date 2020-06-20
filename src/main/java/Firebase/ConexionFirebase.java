/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.IOException;

/**
 *
 * @author vicen
 */
public class ConexionFirebase
{
    
   

    
    
    public Firestore iniciarFirebase()
    {
        try 
        {
            FirebaseOptions options = new FirebaseOptions.Builder()
		    //add the file to your project into the directory /cursojavafb/src/main/resources/json	
                    .setCredentials(GoogleCredentials.fromStream(getClass().getResourceAsStream("")))// /json/name_of_json_created_by_firebase.json
                    .setDatabaseUrl("")//  add the url of your firebase project example...... https://cursojavafb.firebaseio.com
                    .build();
            
            FirebaseApp.initializeApp(options);
            
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return FirestoreClient.getFirestore();
    }
    
}
