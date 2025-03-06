// Language: java
package GestionEvenement3a16.Services;

import org.vosk.Model;
import org.vosk.Recognizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class openIAsevice {
    public static void main(String[] args) {
        try {
            // Path to the Vosk model folder (download the model first)
            String modelPath = "model";
            Model model = new Model(modelPath);

            // Path to the audio file (must be mono PCM WAV with 16000 Hz sample rate)
            File audioFile = new File("audio.wav");
            InputStream ais = new FileInputStream(audioFile);

            // Create a Recognizer with 16000 Hz sample rate
            Recognizer recognizer = new Recognizer(model, 16000);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = ais.read(buffer)) != -1) {
                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    System.out.println(recognizer.getResult());
                } else {
                    System.out.println(recognizer.getPartialResult());
                }
            }
            System.out.println(recognizer.getFinalResult());

            ais.close();
            recognizer.close();
            model.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}