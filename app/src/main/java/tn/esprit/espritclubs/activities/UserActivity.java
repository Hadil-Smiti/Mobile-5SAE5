package tn.esprit.espritclubs.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import tn.esprit.espritclubs.R;
import tn.esprit.espritclubs.database.AppDatabase;
import tn.esprit.espritclubs.entities.User;
import tn.esprit.espritclubs.fragments.daly.DalyFragment;
import tn.esprit.espritclubs.fragments.aziz.AzizFragment;
import tn.esprit.espritclubs.fragments.hadil.user.HadilFragment;
import tn.esprit.espritclubs.fragments.hadil.user.UserSettingsFragment;
import tn.esprit.espritclubs.fragments.oumaima.OumaimaFragment;
import tn.esprit.espritclubs.fragments.hadil.user.UserHomeFragment;
import tn.esprit.espritclubs.fragments.hadil.user.UserProfileFragment;

public class UserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment currentFragment;
    private FrameLayout main_content;

    private ImageView mylogo;
    private TextView myname;
    private TextView myaccount;

    private AppDatabase database;

    private Uri imageUri;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        main_content = findViewById(R.id.main_content);

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        ImageView btnMenu = findViewById(R.id.menu_icon);

        android.view.View headerView = navigationView.getHeaderView(0);
        mylogo = headerView.findViewById(R.id.mylogo);
        myname = headerView.findViewById(R.id.myname);
        myaccount = headerView.findViewById(R.id.myaccount);

        UserProfileFragment profileFragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("userid", getIntent().getIntExtra("userid", -1));
        profileFragment.setArguments(bundle);

        database= AppDatabase.getDatabase(this);
        currentUser = database.userDao().getUserByUid(getIntent().getIntExtra("userid", -1));

        loadProfileImage();
        mylogo.setImageURI(imageUri);
        myname.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        myaccount.setOnClickListener(hadil->{
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.main_content, profileFragment).addToBackStack(null).commit();
            drawerLayout.closeDrawers();
        });



        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(navigationView));

        currentFragment = new UserHomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, currentFragment)
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;



                // If statements to handle navigation item selection
                if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new UserHomeFragment();
                } else if (item.getItemId() == R.id.bacem) {
                    selectedFragment = new AzizFragment();
                } else if (item.getItemId() == R.id.manef) {
                    selectedFragment = new OumaimaFragment();
                } else if (item.getItemId() == R.id.anas) {
                    selectedFragment = new DalyFragment();
                } else if (item.getItemId() == R.id.houssem) {
                    selectedFragment = new HadilFragment();
                } else if (item.getItemId() == R.id.settings) {
                    selectedFragment = new UserSettingsFragment();
                } else if (item.getItemId() == R.id.logout) {
                    finish();
                }

                // Replace fragment only if it's different from the current one
                if (selectedFragment != null && !selectedFragment.getClass().equals(currentFragment.getClass())) {
                    currentFragment = selectedFragment;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content, selectedFragment)
                            .commit();
                    drawerLayout.closeDrawers(); // Close the drawer after selection
                }
                return true;
            }
        });
    }

    private void loadProfileImage() {
        String profileImageUri = currentUser.getProfilePicture();
        if (profileImageUri != null && !profileImageUri.isEmpty()) {
            imageUri = Uri.parse(profileImageUri);
            mylogo.setImageURI(imageUri);
        } else {
            if ("male".equalsIgnoreCase(currentUser.getGender()))
                mylogo.setImageResource(R.drawable.avatar);
            if ("female".equalsIgnoreCase(currentUser.getGender()))
                mylogo.setImageResource(R.drawable.avatara);
        }
    }
}