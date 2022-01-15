package co.tiagoaguiar.codelab.fitnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MainItem> mainItems = new ArrayList<>();
        mainItems.add(new MainItem(1, android.R.color.darker_gray, R.drawable.vintage_flower_icon_24dp, R.string.imc_textStr));
        mainItems.add(new MainItem(2, android.R.color.darker_gray, R.drawable.tmb_icon_24, R.string.tmb_textStr));

        rvMain = findViewById(R.id.main_rv);
        rvMain.setLayoutManager(new GridLayoutManager(this, 2));
        MainAdapter adapter = new MainAdapter(mainItems);
        adapter.setListener(id -> {
            switch (id) {
                case 1:
                    startActivity(new Intent(MainActivity.this, ImcActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, TmbActivity.class));
                    break;
            }

        });
        rvMain.setAdapter(adapter);


    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

        private List<MainItem> mainItems;
        private OnItemClickListener listener;

        public MainAdapter(List<MainItem> mainItems) {
            this.mainItems = mainItems;
        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainHolder holder, int position) {
            MainItem currentMainItem = mainItems.get(position);
            holder.bind(currentMainItem);
        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        //Entenda como sendo a View da célula que está dentro do RecyclerView:
        private class MainHolder extends RecyclerView.ViewHolder {

            public MainHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(MainItem item) {
                TextView txtItemTitle = itemView.findViewById(R.id.txt_item_title);
                ImageView imgItemIcon = itemView.findViewById(R.id.img_icon_item);
                LinearLayout container = itemView.findViewById(R.id.linear_layout_btn_imc);

                container.setOnClickListener(view -> {
                    listener.onClick(item.getId());
                });

                txtItemTitle.setText(item.getTitleId());
                imgItemIcon.setImageResource(item.getDrawableId());
                container.setBackgroundColor(item.getColorId());

            }
        }
    }


}