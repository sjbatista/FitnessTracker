package co.tiagoaguiar.codelab.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private View btnLinearLayoutImc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnLinearLayoutImc = findViewById(R.id.linear_layout_btn_imc);

//IMC Button Start
		btnLinearLayoutImc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ImcActivity.class);
				startActivity(intent);
			}
		});
//IMC Button End

	}
}