package com.example.sonymz1;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

/**
 * The main activity for the application. Uses a navigation drawer to navigate to different pages.
 * @author Wendy Pau
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // item id is being passed into the method here
        displaySelectedFragment(item.getItemId());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Navigates to the fragment thats clicked in the navigation drawer.
     * @param item_id the selected menuitem.
     */
    public void displaySelectedFragment(int item_id){
        Fragment fragment = null;

        switch (item_id){
            case R.id.nav_home: fragment = new FirstFragment(); break;
            case R.id.nav_explore: //fragment = new Explore(); break;
            case R.id.nav_contacts: //fragment = new Contacts(); break;
            case R.id.nav_about: //fragment = new AboutUs(); break;
            case R.id.nav_faq: //fragment = new FAQ(); break;
            case R.id.nav_settings: //fragment = new Settings(); break;
        }

        if(fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //this is where the id(R.id.container) of the ConstraintLayout in content_main.xml
            // is being mentioned. Hence the fragment would be loaded into the layout
            ft.replace(R.id.container, fragment);
            ft.commit();
        }

    }
}