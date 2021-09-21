package co.tiagoaguiar.codelab.fitnesstracker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ImcActivity extends AppCompatActivity {

    private EditText etxtHeight;
    private EditText etxtWeight;
    private Button btnCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        etxtHeight = findViewById(R.id.etxt_height);
        etxtWeight = findViewById(R.id.etxt_weight);
        btnCalc = findViewById(R.id.btn_calculate_imc);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) { //Caso os campos não estejam validados com valores acima de zero
                    Toast.makeText(ImcActivity.this, "Invalid values !", Toast.LENGTH_LONG).show();
                    return;
                } else { //Caso os campos estejam validados com valores acima de zero
                    int height = Integer.parseInt(etxtHeight.getText().toString());
                    int weight = Integer.parseInt(etxtWeight.getText().toString());
                    double result = calculateImc(height, weight);
                    Log.d("Imc ", " " + result);
                    //retorna a referencia correta de acordo com o imc, no arquivo de string(R.string...):
                    int idImcResponse = imcResponse(result);
                    //Cria um AlerdDialog(um alerta)
                    AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                            .setTitle(getString(R.string.imc_responseStr,result))
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create();//Após setar as configurações, cria o alerta
                    dialog.setIcon(R.drawable.vintage_flower_icon_24dp);
                    dialog.setMessage(getString(idImcResponse));
                    dialog.show(); //mostra o alerta

                    //Usando InputMethodManager para esconder o teclado após aparecer o alerta:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //O imm é agora um gerenciador de metodo de entrada
                    //Escondendo teclado do Height
                    imm.hideSoftInputFromWindow(etxtHeight.getWindowToken(),0);
                    //Escondendo teclado do Height
                    imm.hideSoftInputFromWindow(etxtWeight.getWindowToken(),0);

                }
            }
        });
    }

    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15) {
            return R.string.imc_abaixo_do_peso;
        } else if (imc < 20) {
            return R.string.imc_peso_controlado;
        } else {
            return R.string.imc_acima_do_peso;
        }
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean validate() {
        return (!etxtHeight.getText().toString().startsWith("0")
                && !etxtWeight.getText().toString().startsWith("0")
                && !etxtHeight.getText().toString().isEmpty()
                && !etxtWeight.getText().toString().isEmpty());
    }

    private double calculateImc(int height, int weight) {
        return (weight / ((double) height / 100 * (double) height / 100));
    }

}
