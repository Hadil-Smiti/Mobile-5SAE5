package tn.esprit.espritclubs.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tn.esprit.espritclubs.R;
import tn.esprit.espritclubs.fragments.hadil.main.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout main_fragment_container;
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        main_fragment_container = findViewById(R.id.main_fragment_container);
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new WelcomeFragment()).addToBackStack(null).commit();
    }
}