package com.asn.newcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import org.mariuszgromada.math.mxparser.*;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Executable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView sonuç;
    Cursor cursor;
  //  Intent intent;
 //   String pattern = "MM-dd-yyyy";
 //   SimpleDateFormat simpleDateFormat;
//    String date1;
    int değerIndex;
//  ayrı yetten ekran dönmesini engeller android:screenOrientation="portrait"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sonuç = findViewById(R.id.textView);
        sonuç = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        editText.setShowSoftInputOnFocus(false);
      //  simpleDateFormat = new SimpleDateFormat(pattern);
       //  date1 = simpleDateFormat. format(new Date());
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().toLowerCase().equals("tab here")) {
                    editText.setText("");
                }
            }
        });

    }

    public void tıkla(View view) {
        switch (view.getId()) {
            case R.id.sil:
                editText.setText("");
                sonuç.setText("");
                break;
            case R.id.bir:
                display("1");
                break;
            case R.id.iki:
                display("2");
                break;
            case R.id.üç:
                display("3");
                break;
            case R.id.dört:
                display("4");
                break;
            case R.id.beş:
                display("5");
                break;
            case R.id.altı:
                display("6");
                break;
            case R.id.yedi:
                display("7");
                break;
            case R.id.sekiz:
                display("8");
                break;
            case R.id.dokuz:
                display("9");
                break;
            case R.id.sıfır:
                secondControl("0");
                break;
            case R.id.nokta:
                control(".");
                break;
            case R.id.üçsıfır:
                update();
                break;
            case R.id.topla:
                control("+");
                break;
            case R.id.çıkar:
                control("-");
                break;
            case R.id.çarp:
                control("x");
                break;
            case R.id.bölü:
                control("/");
                break;
            case R.id.parantez:
                setParantez();
                break;

            case R.id.yüzde:
                control("%");
                break;
        }
    }

    private void secondControl(String s) {
        String temp = editText.getText().toString().toLowerCase(Locale.ROOT);


        if (temp.equals("0")) {

        } else if (temp.length() >= 2) {
            if (temp.substring(temp.length() - 1).equals("0") && (temp.substring(temp.length() - 2, temp.length() - 1).equals("x") ||
                    temp.substring(temp.length() - 2, temp.length() - 1).equals("+") || temp.substring(temp.length() - 2, temp.length() - 1).equals("/") ||
                    temp.substring(temp.length() - 2, temp.length() - 1).equals("-") || temp.substring(temp.length() - 2, temp.length() - 1).equals("%"))) {

            } else {
                display(s);
            }
        } else {
            display(s);
        }

    }

    public void setParantez() {
        String text = editText.getText().toString();
        int parantezSayısı = 0;
        int cursorPos = editText.getSelectionStart();
        if (text.equals("")) {
            display("(");
            parantezSayısı+=1;

        } else {


            for (int i = 0; i < text.length(); i++) {
                if (text.substring(i, i + 1).equalsIgnoreCase("(")) {
                    parantezSayısı += 1;
                }
                if (text.substring(i, i + 1).equalsIgnoreCase(")")) {
                    parantezSayısı -= 1;
                }
            }
            String sonKarakter = text.substring(text.length() - 1);
            if (parantezSayısı == 0 || sonKarakter.equals("(")) {
                display("(");
                editText.setSelection(cursorPos += 1);
            } else if (parantezSayısı > 0 && (!sonKarakter.equals("(") && !sonKarakter.equals(")"))) {
                display(")");
                editText.setSelection(cursorPos += 1);
            } else {
                display("");
                editText.setSelection(cursorPos);
            }

        }
    }

    public void settextView(View view) {
        editText.setText(sonuç.getText());
        int a = editText.getSelectionStart();
        editText.setSelection(a + sonuç.getText().length());

    }

    public void eşitir(View view) {
        String display = editText.getText().toString();
        int parantezSayısı=0;
        for(int i=0;i<display.length();i++){
            if(display.substring(i,i+1).equals("(")){
                parantezSayısı++;
            } if(display.substring(i,i+1).equals(")")){
                parantezSayısı--;
            }
        }
        while (parantezSayısı>0){
            display +=")";
            parantezSayısı--;
        }
        editText.setText(display);
        String temp = display.replaceAll("x", "*");
        temp = temp.replaceAll("÷", "/");
        temp = temp.replaceAll("%", "[%]");

        Expression ifade = new Expression(temp);
        String resault = String.valueOf(ifade.calculate());



        if (display.equals("")) {
            Toast.makeText(MainActivity.this, "Bir işlem yapınız", Toast.LENGTH_SHORT).show();
        } else {
            if (!resault.equals("NaN")) {
                sonuç.setText(resault);
            } else {
                Toast.makeText(MainActivity.this, "hatalı giriş", Toast.LENGTH_LONG).show();
                sonuç.setText("Hatalı giriş ");
            }
        }


    }

    public void tekSil(View view) {
        int cursorPos = editText.getSelectionStart();
        if (cursorPos > 0) {
            String başlangıç = editText.getText().toString();
            String eski = başlangıç.substring(0, cursorPos - 1);
            String yeni = başlangıç.substring(cursorPos);
            String newText = eski + yeni;
            editText.setText(newText);
            editText.setSelection(cursorPos - 1);
        }
    }

    private void update() {
        int cursorPosition = editText.getSelectionStart();
        String tempEditText = editText.getText().toString().toLowerCase(Locale.ROOT);
        if (tempEditText.equals("tab here")) {
            editText.setText("");
        } else if (tempEditText.substring(0).equals("") || tempEditText.substring(tempEditText.length() - 1).equals("+")
                || tempEditText.substring(tempEditText.length() - 1).equals("-") || tempEditText.substring(tempEditText.length() - 1).equals("x")
                || tempEditText.substring(tempEditText.length() - 1).equals("/") || tempEditText.substring(tempEditText.length() - 1).equals("%") ) {

        } else {
            String başlangıç = editText.getText().toString();
            String eski = başlangıç.substring(0, cursorPosition);
            String yeni = başlangıç.substring(cursorPosition);
            String newtext = eski + "000" + yeni;
            editText.setText(newtext);
            editText.setSelection(cursorPosition + 3);
        }

    }


    public void display(String a) {
        // burada en fazla 15 karakter etmesini kontorol etmek için yaapıldı ama başarısıx
        // 13/10/2022
        int cursorPosition = editText.getSelectionStart();

        if (editText.getText().toString().toLowerCase().equals("tab here")) {
            editText.setText("" + a);

        } else {
            String başlangıç = editText.getText().toString();
            String eski = başlangıç.substring(0, cursorPosition);
            String yeni = başlangıç.substring(cursorPosition);
            String newText = eski + a + yeni;
            editText.setText(newText);
        }

        editText.setSelection(cursorPosition + a.length());
    }


    public void control(String a) {
        String temp = editText.getText().toString().toLowerCase(Locale.ROOT);
        if (temp.equals("tab here") || temp.equals("")) {

        } else if (temp.substring(temp.length() - 1).equals("+") || temp.substring(temp.length() - 1).equals("-")
                || temp.substring(temp.length() - 1).equals("/") || temp.substring(temp.length() - 1).equals("x")
                || temp.substring(temp.length() - 1).equals(".")
                || temp.substring(temp.length() - 1).equals("%")) {
        } else {
            if (a.equals("%")) {
                a.replaceAll("%", "[%]");
            }
            display(a);
        }
    }


    public void History(View view) {
        //intent = new Intent(MainActivity.this, MainActivity2Detail.class);
       // startActivity(intent);
    }
}

