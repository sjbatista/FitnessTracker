package co.tiagoaguiar.codelab.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ImcActivity extends AppCompatActivity {

    //Variaveis que recebem as referências dos componentes do layout 'activity_imc'
    private EditText etxtHeight;
    private EditText etxtWeight;
    private Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        //recebendo as referencias..
        etxtHeight = findViewById(R.id.etxt_height);
        etxtWeight = findViewById(R.id.etxt_weight);
        btnCalc = findViewById(R.id.btn_calculate_imc);

        //função lambda do botão imc

        btnCalc.setOnClickListener(v -> {

            //primeiro os dados são validados(função validate), nada de valores iniciados em '0'.
            if (!validate()) { //Caso seja falso sendo testado antes.
                Toast.makeText(ImcActivity.this, "Invalid values !", Toast.LENGTH_LONG).show();
                return;
            } else {
                int height = Integer.parseInt(etxtHeight.getText().toString());
                int weight = Integer.parseInt(etxtWeight.getText().toString());
                double result = calculateImc(height, weight); //recebe o calculo de imc da função
                Log.d("Imc ", " " + result);

                int idImcResponse = imcResponse(result); //para guardar o id de resposta do arquivo de strings, retornado pela função

                AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this) //nosso pop-up
                        .setTitle(getString(R.string.imc_responseStr, result))
                        .setPositiveButton(android.R.string.ok, ((dialog1, which) -> {
                        }))
                        .setNegativeButton(R.string.salvar,(dialog1, which) ->{
                         new Thread(() -> {
                                 long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc",result);
                             runOnUiThread(() -> {
                             if(calcId>0){
                                 Toast.makeText(ImcActivity.this,R.string.salvo,Toast.LENGTH_LONG).show();
                                 openListCalcActivity("imc");
                             }
                             });
                         }).start();
                        })
                        .create(); //Aqui acaba a criação do pop-up.
                //Outras alterações no pop-up já criado.
                dialog.setIcon(R.drawable.vintage_flower_icon_24dp);
                dialog.setMessage(getString(idImcResponse));
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
            openListCalcActivity("imc");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openListCalcActivity(String type){
        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @StringRes
    private int imcResponse(double imc) { //retorna um id(int de referência do arquivo strings) de acordo com o imc passado como argumento
        if (imc < 15) {
            return R.string.imc_abaixo_do_peso;
        } else if (imc < 20) {
            return R.string.imc_peso_controlado;
        } else {
            return R.string.imc_acima_do_peso;
        }
    }

    private boolean validate() { //Como dito antes... Nada de valores iniciados em '0'.
        return (!etxtHeight.getText().toString().startsWith("0")
                && !etxtWeight.getText().toString().startsWith("0")
                && !etxtHeight.getText().toString().isEmpty()
                && !etxtWeight.getText().toString().isEmpty());
    }

    private double calculateImc(int height, int weight) { //Nosso puro IMC
        return (weight / ((double) height / 100 * (double) height / 100));
    }

}
