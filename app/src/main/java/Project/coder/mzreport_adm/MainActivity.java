package Project.coder.mzreport_adm;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    PerfilFragment perfilFragment = new PerfilFragment();
    HomeFragment homeFragment = new HomeFragment();
    TerminateFragment terminateFragment = new TerminateFragment();
    ReportsFragment reportsFragment = new ReportsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                BottomNavigationView navegacion = findViewById(R.id.bottom_navigation);
        navegacion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(homeFragment);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Home_fragment:
                    loadFragment(homeFragment);
                    return true;
                case R.id.Terminate_fragment:
                    loadFragment(terminateFragment);
                    return true;
                case R.id.Reports_fragment:
                    loadFragment(reportsFragment);
                    return true;
                case  R.id.Perfil_fragment:
                    loadFragment(perfilFragment);
                    return true;

            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();
    }
}