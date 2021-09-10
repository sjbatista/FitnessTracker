package co.tiagoaguiar.codelab.fitnesstracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
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
                if (!validate()) {
                    Toast.makeText(ImcActivity.this, "Invalid values !", Toast.LENGTH_LONG).show();
                } else {
                    int height = Integer.parseInt(etxtHeight.getText().toString());
                    int weight = Integer.parseInt(etxtWeight.getText().toString());
                    double result = calculateImc(height, weight);
                    Log.d("Imc ", " " + result);

                    int idImcResponse = imcResponse(result);

                    Toast.makeText(ImcActivity.this, idImcResponse, Toast.LENGTH_LONG).show();
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
