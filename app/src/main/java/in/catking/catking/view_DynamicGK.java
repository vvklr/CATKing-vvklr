package in.catking.catking;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class view_DynamicGK extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doc_activity);
        WebView webview = (WebView) findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);
        Bundle bundle = getIntent().getExtras();
        String value = bundle.getString("DynamicGK");
        String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
        webview.loadUrl(plink + value);


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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            Intent intent = new Intent(this, view_BooksAndAuthor.class);
            intent.putExtra("booksandauthor","BooksAndAuthor");
            intent.putExtra("BooksAndAuthor","17YRfCrqsBxLSpGlkc9lQ7wwAweQ_r_9E");
            this.startActivity(intent);
            // books and author
        } else if (id == R.id.menu_art_culture) {
            Intent intent = new Intent(this, view_ArtAndCulture.class);
            intent.putExtra("artandculture","ArtAndCulture");
            intent.putExtra("ArtAndCulture","1nOpUaaflH1QvyzFif19iWcQ4PXgHOoWS");
            this.startActivity(intent);
            // art and culture
        } else if (id == R.id.menu_constitution_of_india) {
            Intent intent = new Intent(this, view_ConstitutionOfIndia.class);
            intent.putExtra("constitutionofindia","ConstitutionOfIndia");
            intent.putExtra("ConstitutionOfIndia","1vr7FfIbCjF9xDOqQAldCGlE31k5QqXBn");
            this.startActivity(intent);
            // constitution of india
        } else if (id == R.id.menu_dynamic_gk) {
            Intent intent = new Intent(this, view_DynamicGK.class);
            intent.putExtra("dynamicgk","DynamicGk");
            intent.putExtra("DynamicGK","1cP6gSHywUU5TdpOfsYqzuPmsHl2PTIa-");
            this.startActivity(intent);
            //dynamic gk
        } else if (id == R.id.menu_olympics) {
            Intent intent = new Intent(this, view_Olympics.class);
            intent.putExtra("olympics","Olympics");
            intent.putExtra("Olympics","1jz3WLEgolbwbnq_RMsIKv2An1sXIQNun");
            this.startActivity(intent);
            //olympics
        }else if (id == R.id.menu_economics) {
            Intent intent = new Intent(this, view_Economics.class);
            intent.putExtra("economics","Economics");
            intent.putExtra("Economics","1tFeb7Wg7E2hpKAo7SMKk1YPntREx2VEE");
            this.startActivity(intent);
            //economics
        }else if (id == R.id.menu_science) {
            Intent intent = new Intent(this, view_Science.class);
            intent.putExtra("science","Science");
            intent.putExtra("Science","1Oal6x5C7qrEU1mB7yiBgDCFbKYVdAwIE");
            this.startActivity(intent);
            //science
        }else if (id == R.id.menu_miscellaneous) {
            Intent intent = new Intent(this, view_Miscellaneous.class);
            intent.putExtra("miscellaneous","Miscellaneous");
            intent.putExtra("Miscellaneous","1n7ol13ZnhaZPwnuoeIDKBPkmXSLPEBz3");
            this.startActivity(intent);
            //miscellaneous
        }else if (id == R.id.menu_fun_facts) {
            Intent intent = new Intent(this, view_FunFacts.class);
            intent.putExtra("funfacts","FunFacts");
            intent.putExtra("FunFacts","18EyP4wKejUTDxrVNQxJjwMdBPVerGF-3");
            this.startActivity(intent);
            //fun facts
        }else if (id == R.id.menu_geography) {
            Intent intent = new Intent(this, view_Geography.class);
            intent.putExtra("geography","Geography");
            intent.putExtra("Geography","1G0y4XaM-SG02c5I4PGncP-cWJDa4sr2a");
            this.startActivity(intent);
            //geography
        }else if (id == R.id.menu_politics) {
            Intent intent = new Intent(this, view_Politics.class);
            intent.putExtra("politics","Politics");
            intent.putExtra("Politics","1d0K5-2jCWohd3BPFRMPM_Zg6YZWvsgM9");
            this.startActivity(intent);
            //politics
        }else if (id == R.id.menu_history) {
            Intent intent = new Intent(this, view_History.class);
            intent.putExtra("history","History");
            intent.putExtra("History","1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v");//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
            this.startActivity(intent);
            //history
        }else if (id == R.id.menu_quiz_tf) {

            Intent intent = new Intent(this, test1.class);
            this.startActivity(intent);
            //true false quiz
        }else if (id == R.id.menu_quiz_mcq) {
            Intent intent = new Intent(this, test2.class);
            this.startActivity(intent);
            //multiple choice question quiz
        } else if (id == R.id.menu_sport_achievement) {
            Intent intent = new Intent(this, view_Sport_Achievement.class);
            intent.putExtra("sports", "Sports");
            intent.putExtra("Sports","1wNJXKzxqi9k0US1L7qEeZk7DtENitswy");//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
            this.startActivity(intent);
            //sports and achievements 1wNJXKzxqi9k0US1L7qEeZk7DtENitswy
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"vronpc@gmail.com"});  //developer 's email
            Email.putExtra(Intent.EXTRA_SUBJECT,
                    "My Suggestions to Admin"); // Email 's Subject
            Email.putExtra(Intent.EXTRA_TEXT, "Dear CATKing," + "");  //Email 's Greeting text
            startActivity(Intent.createChooser(Email, "Send Feedback:"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

