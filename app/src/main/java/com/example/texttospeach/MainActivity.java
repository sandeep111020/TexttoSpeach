package com.example.texttospeach;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnInitListener {

    private int MY_DATA_CHECK_CODE = 0;

    private TextToSpeech tts;

    private EditText inputText;
    private Button speakButton;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText) findViewById(R.id.EditText1);
        speakButton = (Button) findViewById(R.id.button1);




        speakButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String text = inputText.getText().toString();
                if (text!=null && text.length()>0) {
                    Toast.makeText(MainActivity.this, "Saying: " + text, Toast.LENGTH_LONG).show();
                    tts.speak(text, TextToSpeech.QUEUE_ADD, null);

                }
            }
        });




        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                tts = new TextToSpeech(this, this);
            }
            else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

    }





    @Override
    public void onInit(int status ) {


        if (status == TextToSpeech.SUCCESS) {
            Toast.makeText(MainActivity.this,
                    "Text-To-Speech engine is initialized", Toast.LENGTH_LONG).show();


        }
        else if (status == TextToSpeech.ERROR) {
            Toast.makeText(MainActivity.this,
                    "Error occurred while initializing Text-To-Speech engine", Toast.LENGTH_LONG).show();
        }
    }


}
