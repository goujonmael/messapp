package com.example.messapp2;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Messages {
    public static String[] messagesContent = {
            "Personne 1: Salut ! Comment ça va ?",
            "Personne 2: Hey ! Ça va bien, merci. Et toi ?",
            "Personne 1: Super, je viens de rentrer de vacances.",
            "Personne 2: Génial ! Où es-tu parti cette fois-ci ?",
            "Personne 1: J'ai visité Paris pendant une semaine.",
            "Personne 2: Paris, c'est magnifique ! Quels endroits as-tu visité là-bas ?",
            "Personne 1: J'ai vu la Tour Eiffel, le Louvre, et Montmartre. C'était incroyable !",
            "Personne 2: Je suis jaloux ! J'aimerais tant visiter Paris un jour.",
            "Personne 1: Tu devrais le faire, ça en vaut vraiment la peine.",
            "Personne 2: Oui, j'espère pouvoir le faire bientôt. Merci pour tes conseils !"
    };

    private static String reponseServeur;

    public static String getReponseServeur() {
        return reponseServeur;
    }
    public static class RequeteGETTask extends AsyncTask<Void, Void, String> {

        private String urlStr = "A REMPLACER:8000";

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(urlStr+"/messages");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return response.toString();
                } else {
                    return "La requête GET n'a pas abouti : " + responseCode;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "Erreur lors de la requête GET : " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            reponseServeur = result; // Met à jour la variable reponseServeur avec la réponse obtenue
        }
    }
}