package co.tiagoaguiar.codelab.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle extras = getIntent().getExtras();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);

        if(extras != null){
            String type = extras.getString("type");
            new Thread(() -> {
                List<Register> registers = SqlHelper.getInstance(this).getRegisterBy(type);

                runOnUiThread(() -> {
                    Log.d("test",registers.toString());
                    ListCalcAdapter adapter = new ListCalcAdapter(registers);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(adapter);

                });
            }).start();
        }
    }

    private class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcHolder> {

        private List<Register> datas;
        private OnItemClickListener listener;

        public ListCalcAdapter(List<Register> datas) {
            this.datas = datas;
        }


        @NonNull
        @Override
        public ListCalcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListCalcAdapter.ListCalcHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcAdapter.ListCalcHolder holder, int position) {
            Register data = datas.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        //Entenda como sendo a View da célula que está dentro do RecyclerView:
        private class ListCalcHolder extends RecyclerView.ViewHolder {

            public ListCalcHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(Register data) {

                String formatted = "";
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt","BR"));
                    Date dateSaved = sdf.parse(data.createdDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("pt","BR"));
                    formatted = dateFormat.format(dateSaved);

                }catch (ParseException e){
                }

                ((TextView) itemView).setText(
                        getString(R.string.list_responseStr, data.response, formatted)
                );

            }
        }
    }

}