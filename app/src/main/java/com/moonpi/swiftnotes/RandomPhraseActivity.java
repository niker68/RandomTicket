package com.moonpi.swiftnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.moonpi.swiftnotes.DataUtils.NOTE_BODY;
import static com.moonpi.swiftnotes.DataUtils.NOTE_FONT_SIZE;
import static com.moonpi.swiftnotes.DataUtils.NOTE_TITLE;

public class RandomPhraseActivity extends Activity {
    Button buttonHome1;
    Button buttonRandomQuick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_phrase);
        TextView text = (TextView) findViewById(R.id.textView);
        TextView title = (TextView) findViewById(R.id.titleEdit2);
        buttonHome1 = (Button)findViewById(R.id.buttonHome1);
        buttonRandomQuick = (Button)findViewById(R.id.buttonRandomQuick);
        text.setMovementMethod(new ScrollingMovementMethod());
        int fontSize = 18;
        String titlestr;
        int numberOfNotes = MainActivity.notes.length()-1;
        try{
        JSONObject note = MainActivity.notes.getJSONObject(Math.round((float)Math.random()*numberOfNotes));
            if (note.has(NOTE_FONT_SIZE)){
                fontSize = note.getInt(NOTE_FONT_SIZE);
                text.setTextSize(fontSize);}
            if (note.has(NOTE_TITLE)){
                titlestr = note.getString(NOTE_TITLE);
                title.setText(titlestr);}
        text.setText(note.getString(NOTE_BODY));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        buttonHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomPhraseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonRandomQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomPhraseActivity.this, RandomPhraseActivity.class);
                startActivity(intent);
            }
        });
    }

}
