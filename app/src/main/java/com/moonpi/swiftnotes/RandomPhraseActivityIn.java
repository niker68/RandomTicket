package com.moonpi.swiftnotes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moonpi.swiftnotes.ColorPicker.ColorPickerDialog;

import java.util.regex.Pattern;

import static com.moonpi.swiftnotes.DataUtils.NEW_NOTE_REQUEST;
import static com.moonpi.swiftnotes.DataUtils.NOTE_BODY;
import static com.moonpi.swiftnotes.DataUtils.NOTE_COLOUR;
import static com.moonpi.swiftnotes.DataUtils.NOTE_FONT_SIZE;
import static com.moonpi.swiftnotes.DataUtils.NOTE_HIDE_BODY;
import static com.moonpi.swiftnotes.DataUtils.NOTE_REQUEST_CODE;
import static com.moonpi.swiftnotes.DataUtils.NOTE_TITLE;
import static com.moonpi.swiftnotes.DataUtils.TEXT_RESULT;

public class RandomPhraseActivityIn extends Activity {
    Button buttonRandomInQuick;
    private Bundle bundle;
    private String[] colourArr; // Colours string array
    private int[] colourArrResId; // colourArr to resource int array
    private int[] fontSizeArr; // Font sizes int array
    private String[] fontSizeNameArr; // Font size names string array

    // Defaults
    private String colour = "#FFFFFF"; // white default
    private int fontSize = 18; // Medium default
    private Boolean hideBody = false;
    private Button buttonHome;

    private AlertDialog fontDialog, saveChangesDialog;
    private ColorPickerDialog colorPickerDialog;
    private TextView titleEdit3;
    private RelativeLayout relativeLayoutEdit;
    private Toolbar toolbar;
    private MenuItem menuHideBody;
    private InputMethodManager imm;
    private TextView title;
    private TextView text;
    private String textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_phrasein);
        text = (TextView) findViewById(R.id.textView);
        title = (TextView) findViewById(R.id.titleEdit3);
        buttonHome = (Button) findViewById(R.id.buttonHome);
        bundle = getIntent().getExtras();

        if (bundle != null) {
            // If current note is not new -> initialize colour, font, hideBody and EditTexts
            if (bundle.getInt(NOTE_REQUEST_CODE) != NEW_NOTE_REQUEST) {
                colour = bundle.getString(NOTE_COLOUR);
                fontSize = bundle.getInt(NOTE_FONT_SIZE);
                hideBody = bundle.getBoolean(NOTE_HIDE_BODY);

                title.setText(bundle.getString(NOTE_TITLE));
                text.setText(bundle.getString(NOTE_BODY));
                text.setTextSize(TypedValue.COMPLEX_UNIT_SP, bundle.getInt(NOTE_FONT_SIZE));
                System.out.println(bundle.getString(NOTE_BODY));
                System.out.println(bundle.getString(TEXT_RESULT));
            }


                if (hideBody)
                    menuHideBody.setTitle(R.string.action_show_body);


            // If current note is new -> request keyboard focus to note title and show keyboard
            if (bundle.getInt(NOTE_REQUEST_CODE) == NEW_NOTE_REQUEST) {
                title.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }

            // Set background colour to note colour

        }


        text.setMovementMethod(new ScrollingMovementMethod());
        int fontSize = 18;
        String titlestr;
        buttonRandomInQuick = (Button) findViewById(R.id.buttonRandomInQuick);
        Intent intent = getIntent();
        if (intent.hasExtra(NOTE_FONT_SIZE)) {
            fontSize = intent.getIntExtra(NOTE_FONT_SIZE, 18);
            text.setTextSize(fontSize);
        }
        if (intent.hasExtra(NOTE_TITLE)) {
            titlestr = intent.getStringExtra(NOTE_TITLE);
            title.setText(titlestr);
        }
        text.setTextSize(bundle.getInt(NOTE_FONT_SIZE));
        text.setText(bundle.getString(TEXT_RESULT).toString());
        System.out.println("textresult = "+bundle.getString(TEXT_RESULT));
        System.out.println("text = "+text.getText());
        buttonRandomInQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arrayOfRandomPhrases;
                String text = bundle.getString(NOTE_BODY);
                System.out.println(bundle.getString(NOTE_BODY));
                int randomNumber;
                try {
                    if (text.contains("#")) {
                        arrayOfRandomPhrases = text.split("#");      //3
                        int lengthOfArray = arrayOfRandomPhrases.length-1; //2
                        if (Pattern.matches((".*[\\w].*"),arrayOfRandomPhrases[0])) {
                            System.out.println("length 0 = "+lengthOfArray);
                            randomNumber = Math.round((float) Math.random() * lengthOfArray); //0 1 2
                            textResult = arrayOfRandomPhrases[randomNumber];
                        } else {
                            lengthOfArray = lengthOfArray-1; //1
                            System.out.println("length 1 = "+lengthOfArray);
                            randomNumber = Math.round(((float) Math.random() * lengthOfArray)+1); //1 2
                            textResult = arrayOfRandomPhrases[randomNumber];
                        }
                    } else {
                        textResult = text;
                    }
                }
                catch (NullPointerException e){
                    System.out.println("NullPointerException in RandomPhraseActivityIn");
                    textResult = text;
                }
                Intent intent = new Intent(RandomPhraseActivityIn.this, RandomPhraseActivityIn.class);
                intent.putExtra(NOTE_BODY,bundle.getString(NOTE_BODY));
                intent.putExtra(NOTE_TITLE,bundle.getString(NOTE_TITLE));
                intent.putExtra(NOTE_FONT_SIZE, bundle.getInt(NOTE_FONT_SIZE));
                intent.putExtra(NOTE_TITLE, title.getText().toString());
                intent.putExtra(TEXT_RESULT, textResult);
                intent.putExtra(NOTE_COLOUR, colour);
                intent.putExtra(NOTE_HIDE_BODY, hideBody);
                startActivity(intent);
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomPhraseActivityIn.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


