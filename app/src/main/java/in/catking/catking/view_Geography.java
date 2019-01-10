package in.catking.catking;
//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class view_Geography extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doc_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        WebView webview = (WebView) findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);
        Bundle bundle = getIntent().getExtras();
        String value = bundle.getString("Geography");
        String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link

        webview.loadUrl(plink + value);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        WebView webview = (WebView) findViewById(R.id.webView1);
//        webview.getSettings().setJavaScriptEnabled(true);
//        Bundle bundle = getIntent().getExtras();
//        String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";
//        //String value = "1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v";
//        //webview.loadUrl(plink + value);
//        String id = bundle.getString("id_pass");
//        String value = bundle.getString(id);
//
//
//       if(id == "History"){
//           webview.loadUrl(plink + value);
//           System.out.println(id);
//       }else if (id == "Geography"){
//            webview.loadUrl(plink+ value);
//           System.out.println(id);
//       }
//        WebView myWebView = (WebView) findViewById(R.id.webView1);
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.loadUrl("http://www.google.com");
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        myWebView.setWebViewClient(new WebViewClient());
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
            Intent intent = new Intent(this, view_Geography.class);
            intent.putExtra("Geography","1G0y4XaM-SG02c5I4PGncP-cWJDa4sr2a");
            this.startActivity(intent);
            //geography
        }else if (id == R.id.menu_politics) {
            //history
        }else if (id == R.id.menu_history) {
            Intent intent = new Intent(this, view_History.class);
            intent.putExtra("History","1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v");
            this.startActivity(intent);
            //history
        }else if (id == R.id.menu_quiz_tf) {

            //            Intent intent = new Intent(this, quiz_true_false.class);
            //            this.startActivity(intent);
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
//    private void showDoc(){
//        WebView webview = (WebView) findViewById(R.id.webView1);
//        webview.getSettings().setJavaScriptEnabled(true);
//        Bundle bundle = getIntent().getExtras();
//        String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
//        String value = "1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v";
//        webview.loadUrl(plink + value);

//    }

}
