package it.linux.cuneo.demo;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity implements TextToSpeech.OnInitListener, RecognitionListener {

    TextToSpeech tts;
    SpeechRecognizer speech;

    String testo_da_pronunciare = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        tts = new TextToSpeech(this, this);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
    }

    /**
     * EVENTO onClick del pulsante LISTEN
     * @param view
     */
    public void listen_click(View view) {
        Intent intent = new Intent();

        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "parla");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "it.linux.cuneo.demo");

        speech.startListening( intent);
    }

    /**
     * EVENTO onClick del pulsante SPEAK
     * Verrà ripetuta l'ultima frase ascoltata
     * @param view
     */
    public void speak_click(View view) {

        runOnUiThread(new Runnable() {
            public void run()
            {
                HashMap<String, String> params = new HashMap<String, String>();
                tts.speak(testo_da_pronunciare, TextToSpeech.QUEUE_FLUSH, params);
            }
        });
    }

    //-- IMPLEMENTAZIONE DEI METODI PER IL TextToSpeech
    @Override
    public void onInit(int i) {

    }


    //-- IMPLEMENTAZIONE DEI METODI PER LO SpeechRecognizer
    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {

        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (matches.size() > 0) {
            testo_da_pronunciare =  matches.get(0).toLowerCase();

            runOnUiThread(new Runnable() {
                public void run()
                {
                    HashMap<String, String> params = new HashMap<String, String>();
                    tts.speak(testo_da_pronunciare, TextToSpeech.QUEUE_FLUSH, params);
                }
            });
        }

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
