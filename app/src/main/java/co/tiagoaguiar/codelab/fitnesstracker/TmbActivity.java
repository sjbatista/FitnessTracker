package co.tiagoaguiar.codelab.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TmbActivity extends AppCompatActivity {

    private EditText etxtHeight;
    private EditText etxtWeight;
    private EditText etxtAge;
    private Spinner  tmbSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        etxtHeight = findViewById(R.id.tmb_etxt_height);
        etxtWeight = findViewById(R.id.tmb_etxt_weight);
        etxtAge = findViewById(R.id.tmb_age);
        tmbSpinner = findViewById(R.id.spinner_tmb_life_style);

        Button btnCalc = findViewById(R.id.btn_calculate_tmb);

        btnCalc.setOnClickListener(v -> {

            //primeiro os dados são validados(função validate), nada de valores iniciados em '0'.
            if (!validate()) { //Caso seja falso sendo testado antes.
                Toast.makeText(TmbActivity.this, "Invalid values !", Toast.LENGTH_LONG).show();
                return;
            } else {

                int height = Integer.parseInt(etxtHeight.getText().toString());
                int weight = Integer.parseInt(etxtWeight.getText().toString());
                int age = Integer.parseInt(etxtAge.getText().toString());

                double result = calculateTmb(height, weight, age); //recebe o calculo de tmb da função
                double tmb = tmbResponse(result);
                Log.d("Tmb ", " " + tmb);

                AlertDialog dialog = new AlertDialog.Builder(TmbActivity.this) //nosso pop-up
                        .setTitle(getString(R.string.tmb_responseStr, result))
                        .setPositiveButton(android.R.string.ok, ((dialog1, which) -> {
                        }))
                        .setNegativeButton(R.string.salvar,(dialog1, which) ->{
                            new Thread(() -> {
                                long calcId = SqlHelper.getInstance(TmbActivity.this).addItem("tmb",tmb);
                                runOnUiThread(() -> {
                                    if(calcId>0){
                                        Toast.makeText(TmbActivity.this,R.string.salvo,Toast.LENGTH_LONG).show();
                                        openListCalcActivity("tmb");
                                    }
                                });
                            }).start();
                        })
                        .create(); //Aqui acaba a criação do pop-up.
                //Outras alterações no pop-up já criado.
                dialog.setIcon(R.drawable.vintage_flower_icon_24dp);
                dialog.show();
            }
        });//fim da função do botão...

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_list){
            openListCalcActivity("tmb");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openListCalcActivity(String type){
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    private double calculateTmb(int height, int weight, int age){
        return 66 + (weight * 13.8) + (5 * height) - (6.8 * age);
    }

    private double tmbResponse(double tmb){
        int index = tmbSpinner.getSelectedItemPosition();
        switch(index){
            case 0 : return tmb * 1.2;
            case 1 : return tmb * 1.375;
            case 2 : return tmb * 1.55;
            case 3 : return tmb * 1.725;
            case 4 : return tmb * 1.9;
            default: return 0;
        }
    }

    private boolean validate() { //Como dito antes... Nada de valores iniciados em '0'.
        return (!etxtHeight.getText().toString().startsWith("0")
                && !etxtWeight.getText().toString().startsWith("0")
                && !etxtHeight.getText().toString().isEmpty()
                && !etxtWeight.getText().toString().isEmpty())
                && !etxtAge.getText().toString().startsWith("0")
                && !etxtAge.getText().toString().isEmpty();
    }
}