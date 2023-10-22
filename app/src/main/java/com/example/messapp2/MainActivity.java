    package com.example.messapp2;

    import androidx.annotation.RequiresApi;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Handler;
    import android.os.Looper;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.Switch;
    import android.widget.Toast;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.UnsupportedEncodingException;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
    import java.util.List;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Paths;

    public class MainActivity extends AppCompatActivity {

        private String reponseServeur;
        private String[] tabMessages;
        private static Switch user;
        private EditText editText;
        private static final String SERVER_URL = "A REMPLACER";
        private String userId;



        private static final String secretKey = "0123456789abcdef";
        String url = "A REMPLACER:8000/messages";
        private ArrayList<MessageModel> messageModels = new ArrayList<>();
        private Handler handler;
        private RecyclerView recyclerView;




        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            user = findViewById(R.id.switch2);
            editText = findViewById(R.id.editText);


            recyclerView = findViewById(R.id.mRecyclerView);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setReverseLayout(true);
            // Set the adapter for the RecyclerView here
            Messages_RecyclerViewAdapter adapter = new Messages_RecyclerViewAdapter(this, messageModels);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);

            // Fetch server data using AsyncTask
            fetchServerData();

            // Scroll to the bottom of the RecyclerView after setting up the data (at initialization)
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(messageModels.size() - 1);
                }
            });

            // Initialiser le Handler et exécuter la tâche de mise à jour toutes les secondes
            handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchServerData();
                    handler.postDelayed(this, 1000); // Exécuter cette tâche toutes les 1000 millisecondes (1 seconde)
                }
            }, 1000); // Démarrer la tâche après 1000 millisecondes (1 seconde)



        }

        public void openImportActivity(View view) {
            Intent intent = new Intent(this, ImportActivity.class);
            startActivity(intent);
        }

        public void openImageActivity(View view) {
            Intent intent = new Intent(this, ImagesActivity.class);
            startActivity(intent);
        }

        private void fetchServerData() {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        return HttpGetRequest.performGetRequest(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Erreur lors de la requête GET : " + e.getMessage();
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    reponseServeur = result; // Update the variable reponseServeur with the response obtained
                    System.out.println("ligne 82 " + reponseServeur);
                    try {
                        setUpMessageModels(reponseServeur); // Call setUpMessageModels() to update RecyclerView with the server response
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }.execute();
        }

        private void setUpMessageModels(String reponseHTTP) {
            // Check if the server response is not null and not empty
            if (reponseHTTP != null && !reponseHTTP.isEmpty()) {
                try {
                    String[] messageContents = MessageExtractor.extractMessages(reponseHTTP);
                    List<String> messageUsersID = MessageExtractor.extractUserIds(reponseHTTP);
                    List<String> messageTime = MessageExtractor.extractTime(reponseHTTP);
                    messageModels.clear();

                    // Repeat the server response 10 times in the RecyclerView
                    //for (int i = 0; i < 5; i++) {
                    int i=0;
                        for (String messageContent : messageContents) {
                            messageModels.add(new MessageModel(AES.decrypt(messageContent, secretKey), messageUsersID.get(i) ,messageTime.get(i)));
                        i++;
                        }
                    //}

                    recyclerView.getAdapter().notifyDataSetChanged();




                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle any exception that may occur while parsing server response
                    // You can log an error or display an appropriate message to the user
                }
            }
        }

        @Override
        protected void onDestroy() {
            // Assurez-vous d'arrêter le Handler lorsque l'activité est détruite pour éviter les fuites de mémoire
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            super.onDestroy();
        }

        public void onClickBtn(View v) {
            Toast.makeText(this, "Bouton cliqué", Toast.LENGTH_SHORT).show();
            new EnvoyerMessageTask().execute();
        }

        public void onCheckSwitch(View v){
            user = findViewById(R.id.switch2);
            if (user.isChecked()){
                userId = "1235";
            }
            else
                userId = "1234";
        }

        public static boolean userChecked(){
            if (user.isChecked()) {
                return true;
            } else {
                return false;
            }

        }

        private class EnvoyerMessageTask extends AsyncTask<Void, Void, String> {

            private static final String PARAM_USER_ID = "user_id";
            private static final String PARAM_MESSAGE_TEXT = "message_text";

            private String errorMessage = null;

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected String doInBackground(Void... params) {

                if (userChecked()) {
                    userId = "1234";
                } else {
                    userId = "1235";
                }
                String messageText = editText.getText().toString();
                String cipherText;
                try {
                    // Chiffrement
                    cipherText = AES.encrypt(messageText, secretKey);
                    System.out.println("Texte chiffré : " + cipherText);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String messageCrypted = String.valueOf(cipherText);

                try {
                    // Create the URL for the request
                    String urlString = SERVER_URL + "?" + PARAM_USER_ID + "=" + userId + "&" + PARAM_MESSAGE_TEXT + "=" + messageCrypted;
                    System.out.println(urlString);
                    URL serverUrl = new URL(urlString);

                    HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Send the request
                    connection.getOutputStream().close(); // No request body needed for this POST request

                    // Read the response from the server
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String response;
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuilder.append(response);
                    }
                    bufferedReader.close();

                    return stringBuilder.toString();
                } catch (IOException e) {
                    errorMessage = e.getMessage();
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    // Display the response in the TextView
                    //textView1.setText(result);
                    Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();

                    // Clear the EditText after successful submission
                    editText.setText("");
                } else {
                    // Show an error toast if the request failed
                    Toast.makeText(MainActivity.this, "Erreur lors de la requête: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
