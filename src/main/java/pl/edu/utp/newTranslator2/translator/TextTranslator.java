package pl.edu.utp.newTranslator2.translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextTranslator {
    private String translatedText = null;
    private static final String API_KEY="trnsl.1.1.20190811T161750Z.e324b11d8a02bfb0.a0b0eccf91cffc38f4eb74253904879fc875c4fd";

    public static String translate(String sourceLanguage, String targetLanguage, String text) {
        try {
            text = text.replace(" ", "%20");
            String urlString = "https://translate.yandex.net/api/v1.5/tr/translate?key="+API_KEY+"&lang=" + sourceLanguage + "-" + targetLanguage + "&text=" + text;
            URL url = new URL(urlString);
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            String result = response.toString();
            result = result.substring(result.indexOf("<text>") + 6, response.indexOf("</text>"));
            return result;
        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println("TextTranslator.translate()\nsourceLanguage: "+sourceLanguage+"\ntargetLanguage: "+targetLanguage+"\ntext: "+text+"\n"+ex.toString());
            return null;
        }
    }

    public TextTranslator(String sourceLanguage, String targetLanguage, String text) {
        translatedText = translate(sourceLanguage, targetLanguage, text);
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {

    }
}
