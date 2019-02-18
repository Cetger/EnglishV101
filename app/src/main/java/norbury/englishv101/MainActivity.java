package norbury.englishv101;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentTemp = null;
    FrameLayout fmalan;

    public DatabaseHelper dbhelper;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTemp = new Fragment_Add();
                    Fragment_Add_Func(fragmentTemp);
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTemp = new Fragment_test();
                    Fragment_Add_Func(fragmentTemp);
                    return true;
                case R.id.navigation_notifications:
                    fragmentTemp = new Fragment_List();
                    Fragment_Add_Func(fragmentTemp);
                    return true;
            }
            return false;
        }

    };







    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbhelper = new DatabaseHelper(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fmalan = (FrameLayout) findViewById(R.id.alan);


        fragmentTemp = new Fragment_Add();
        Fragment_Add_Func(fragmentTemp);




    }






    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }




    public void Fragment_Add_Func(Fragment fragment) {
        if (fmalan != null) {
            fmalan.removeAllViews();
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.alan, fragment);
        fragmentTransaction.commit();


    }






}
