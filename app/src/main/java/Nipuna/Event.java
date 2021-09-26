package Nipuna;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wikings.R;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

public class Event extends AppCompatActivity {
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        button = (Button) findViewById(R.id.btnone);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Event.this,Kite.class);
                startActivity(intent);
            }
        });

    }
}