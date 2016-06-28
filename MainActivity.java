package net.opentrends.expandableFab;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.first_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("first");
            }
        });
        findViewById(R.id.second_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("second");
            }
        });
        findViewById(R.id.third_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("third");
            }
        });
        findViewById(R.id.forth_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("forth");
            }
        });

    }

    private void log(String text) {
        Snackbar.make(findViewById(android.R.id.content), "Clicked item: "+text, Snackbar.LENGTH_SHORT).show();
    }
}
