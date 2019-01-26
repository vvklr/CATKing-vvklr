package in.catking.catking;
/**Vishal Raut
*Email me on vr.iitb@gmail.com if you come across any problem*/
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class view_doc_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String Science_1;
    private String Geography_1;
    private String Books_and_Authors_1;
    private String Olympics_1;
    private String Sports_and_Achievements_1;
    private String Art_and_Culture_1;
    private String History_1;
    private String Politics_1;
    private String Constitution_of_India_1;
    private String Miscellaneous_1;
    private String Funfacts_1;
    private String DynamicGK_1;
    private String Economics_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doc_activity);

        String url = "https://script.google.com/macros/s/AKfycbwfvXAADSw7PCH36Rjiut9cqOOzOCjGXp2qg0S8jTMMa7eAaGU/exec?MQK1hyOY2ysqc29O-nnehdEwhP7cC3CUJ";
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("CATKing", "Successful JSON data collection " + response.toString());
                final String[] mySheetID_Data = pdf_SheetData.fromJson_pdfID(response);
                Science_1 = mySheetID_Data[0];
                Geography_1 = mySheetID_Data[1];
                Books_and_Authors_1 = mySheetID_Data[2];
                Olympics_1 = mySheetID_Data[3];
                Sports_and_Achievements_1 = mySheetID_Data[4];
                Art_and_Culture_1 = mySheetID_Data[5];
                History_1 = mySheetID_Data[6];
                Politics_1 = mySheetID_Data[7];
                Constitution_of_India_1 = mySheetID_Data[8];
                Miscellaneous_1 = mySheetID_Data[9];
                Funfacts_1 = mySheetID_Data[10];
                DynamicGK_1 = mySheetID_Data[11];
                Economics_1 = mySheetID_Data[12];
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce) {
                Log.e("CATKing", "Fail JSON" + e.toString());
                Log.d("CATKING", " Fail Status Code" + statusCode);
            }

        });

        WebView webview = (WebView) findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("sports")== "Sports"){
            String value = bundle.getString("Sports");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink + value);
        }else if(bundle.getString("geography")== "Geography"){
            String value = bundle.getString("Geography");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink + value);
        }else if(bundle.getString("history")== "History"){
            String value = bundle.getString("History");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if(bundle.getString("politics")== "Politics"){
            String value = bundle.getString("Politics");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("booksandauthor")== "BooksAndAuthor"){
            String value = bundle.getString("BooksAndAuthor");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("artandculture")== "ArtAndCulture"){
            String value = bundle.getString("ArtAndCulture");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("constitutionofindia")== "ConstitutionOfIndia"){
            String value = bundle.getString("ConstitutionOfIndia");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("dynamicgk")== "DynamicGk"){
            String value = bundle.getString("DynamicGk");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("olympics")== "Olympics"){
            String value = bundle.getString("Olympics");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("science")== "Science"){
            String value = bundle.getString("Science");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("economics")== "Economics"){
            String value = bundle.getString("Economics");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("miscellaneous")== "Miscellaneous"){
            String value = bundle.getString("Miscellaneous");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }else if (bundle.getString("funfacts")== "FunFacts"){
            String value = bundle.getString("FunFacts");
            String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
            webview.loadUrl(plink+value);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        String value = bundle.getString("Geography");
//        String plink = "https://drive.google.com/viewerng/viewer?embedded=true&url=https://drive.google.com/uc?id=";//permanent link
//
//        webview.loadUrl(plink + value);

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
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("booksandauthor","BooksAndAuthor");
            intent.putExtra("BooksAndAuthor","1eXqx6Ag3Xu7YG0_0og-O4ka32U8hG7Xn2SKBatyZCm4");
            this.startActivity(intent);
            // books and author
        } else if (id == R.id.menu_art_culture) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("artandculture","ArtAndCulture");
            intent.putExtra("ArtAndCulture","14Bg0L9GQ2pCWkNhIN3YBnh1wX9cmKhQhtIlVMb2D4m4");
            this.startActivity(intent);
            // art and culture
        } else if (id == R.id.menu_constitution_of_india) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("constitutionofindia","ConstitutionOfIndia");
            intent.putExtra("ConstitutionOfIndia","1vr7FfIbCjF9xDOqQAldCGlE31k5QqXBn");
            this.startActivity(intent);
            // constitution of india
        } else if (id == R.id.menu_dynamic_gk) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("dynamicgk","DynamicGk");
            intent.putExtra("DynamicGK","1dqb8s05FMqdHjycjY0B8K9w1feVRBQ74vbXKGiiAsUs");
            this.startActivity(intent);
            //dynamic gk
        } else if (id == R.id.menu_olympics) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("olympics","Olympics");
            intent.putExtra("Olympic","1XKYtbifHzaO4X64EFUJY53gBZlGB9_FsnjzMgmQ-pXE");
            this.startActivity(intent);
            //olympics
        }else if (id == R.id.menu_economics) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("economics","Economics");
            intent.putExtra("Economics","1Gu3G8qceESFqa9RUD2CndYlJkTp-kxgkvlAsBezhdNw");
            this.startActivity(intent);
            //economics
        }else if (id == R.id.menu_science) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("science","Science");
            intent.putExtra("Science","1DX1YAljmAc3aT0yijXdx8I1sWyxJz5ZvVAW3-43i0gY");
            this.startActivity(intent);
            //science
        }else if (id == R.id.menu_miscellaneous) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("miscellaneous","Miscellaneous");
            intent.putExtra("Miscellaneous","17mHR8DlPq-kbABzudZIt3Yx6L1fU5A9BL0M2OlkVF40");
            this.startActivity(intent);
            //miscellaneous
        }else if (id == R.id.menu_fun_facts) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("funfacts","FunFacts");
            intent.putExtra("FunFacts","1GPdpRmXKWKvaEnvWxEaWfi0Lg44aeWvkKA7x4BSqupc");
            this.startActivity(intent);
            //fun facts
        }else if (id == R.id.menu_geography) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("geography","Geography");
            intent.putExtra("Geography","1G0y4XaM-SG02c5I4PGncP-cWJDa4sr2a");
            this.startActivity(intent);
            //geography
        }else if (id == R.id.menu_politics) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("politics","Politics");
            intent.putExtra("Politics","1aluExCWCiRydM3VI8p32TPWueESoj7klpSdwmUE0568");
            //politics
        }else if (id == R.id.menu_history) {
            Intent intent = new Intent(this, view_History.class);
            intent.putExtra("history","History");
            intent.putExtra("History","1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v");//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
            this.startActivity(intent);
            //history
        }else if (id == R.id.menu_quiz_tf) {

            Intent intent = new Intent(this, test4.class);
            this.startActivity(intent);
            //true false quiz
        }else if (id == R.id.menu_quiz_mcq) {
            Intent intent = new Intent(this, test3.class);
            this.startActivity(intent);
            //multiple choice question quiz
        } else if (id == R.id.menu_sport_achievement) {
            Intent intent = new Intent(this, view_doc_activity.class);
            intent.putExtra("sports", "Sports");
            intent.putExtra("Sports","1wNJXKzxqi9k0US1L7qEeZk7DtENitswy");//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
            this.startActivity(intent);
            //sports and achievements 1wNJXKzxqi9k0US1L7qEeZk7DtENitswy
        } else if (id == R.id.nav_share) {
            Intent SLink = new Intent(Intent.ACTION_SEND);
            SLink.setType("text/plain");
            SLink.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL"); // Email 's Subject
            SLink.putExtra(Intent.EXTRA_TEXT, "https://www.catking.in/appDownload");  //Email 's Greeting text
            startActivity(Intent.createChooser(SLink, "CATKing App link"));
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
