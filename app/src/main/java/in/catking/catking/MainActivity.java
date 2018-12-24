package in.catking.catking;
//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new LetsCollectJSON("https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
//        MenuInflater inflater = getMenuInflater();        //this inflates navigation in setting menu
//        inflater.inflate(R.menu.activity_main_drawer, menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.menu_quiz_tf:
//                Intent intent = new Intent(this, quiz_true_false.class);
//                this.startActivity(intent);
//                break;
////            case R.id.menu_quiz_mcq:
////                // another startActivity, this is for item with id "menu_item2"
////                break;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//        return true;
         //Handle action bar item clicks here. The action bar will
         //automatically handle clicks on the Home/Up button, so long
       //  as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_books_author) {
            // Handle the camera action
        } else if (id == R.id.menu_art_culture) {
            // art and culture
        } else if (id == R.id.menu_constitution_of_india) {
            // constitution of india
        } else if (id == R.id.menu_dynamic_gk) {
            //dynamic gk
        } else if (id == R.id.menu_olympics) {
            //olympics
        }else if (id == R.id.menu_economics) {
            //economics
        }else if (id == R.id.menu_science) {
            //science
        }else if (id == R.id.menu_miscellaneous) {
            //miscellaneous
        }else if (id == R.id.menu_fun_facts) {
            //fun facts
        }else if (id == R.id.menu_geography) {
            //geography
        }else if (id == R.id.menu_politics) {
            //history
        }else if (id == R.id.menu_history) {
            //history
        }else if (id == R.id.menu_quiz_tf) {
            Intent intent = new Intent(this, quiz_true_false.class);
            this.startActivity(intent);
            //true false quiz
        }else if (id == R.id.menu_quiz_mcq) {
            //multiple choice question quiz
        } else if (id == R.id.menu_sport_achievement) {
            //sports and achievements
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //TODO:let collect json data for questions

}
