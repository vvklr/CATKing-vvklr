package in.catking.catking;
//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ExpandableListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import in.catking.catking.pdf_sheet_name;
import in.catking.catking.quiz.QuizList;
import in.catking.catking.quiz.TF_QuizList;

public class MainActivity extends AppCompatActivity {
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
//    private String Science_1;
//    private String Geography_1;
//    private String Books_and_Authors_1;
//    private String Olympics_1;
//    private String Sports_and_Achievements_1;
//    private String Art_and_Culture_1;
//    private String History_1;
//    private String Politics_1;
//    private String Constitution_of_India_1;
//    private String Miscellaneous_1;
//    private String Funfacts_1;
//    private String DynamicGK_1;
//    private String Economics_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

//        String url = "https://script.google.com/macros/s/AKfycbwfvXAADSw7PCH36Rjiut9cqOOzOCjGXp2qg0S8jTMMa7eAaGU/exec?MQK1hyOY2ysqc29O-nnehdEwhP7cC3CUJ";
//        AsyncHttpClient client = new AsyncHttpClient();
//        final RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("CATKing", "Successful JSON data collection " + response.toString());
//                final String[] mySheetID_Data = pdf_SheetData.fromJson_pdfID(response);
//                Science_1 = mySheetID_Data[0];
//                Geography_1 = mySheetID_Data[1];
//                Books_and_Authors_1 = mySheetID_Data[2];
//                Olympics_1 = mySheetID_Data[3];
//                Sports_and_Achievements_1 = mySheetID_Data[4];
//                Art_and_Culture_1 = mySheetID_Data[5];
//                History_1 = mySheetID_Data[6];
//                Politics_1 = mySheetID_Data[7];
//                Constitution_of_India_1 = mySheetID_Data[8];
//                Miscellaneous_1 = mySheetID_Data[9];
//                Funfacts_1 = mySheetID_Data[10];
//                DynamicGK_1 = mySheetID_Data[11];
//                Economics_1 = mySheetID_Data[12];
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject responce) {
//                Log.e("CATKing", "Fail JSON" + e.toString());
//                Log.d("CATKING", " Fail Status Code" + statusCode);
//            }
//
//        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"vronpc@gmail.com"});  //developer 's email
                Email.putExtra(Intent.EXTRA_SUBJECT,
                        "My Suggestions to Admin"); // Email 's Subject
                Email.putExtra(Intent.EXTRA_TEXT, "Dear CATKing," + "");  //Email 's Greeting text
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tab_gk);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.menu_books_author) {
//            Intent intent = new Intent(this, view_BooksAndAuthor.class);
//            //intent.putExtra("BooksAndAuthor","https://docs.google.com/document/d/1GVmTKAFss3FJYLjpq_cKOhiywxqeHDKOE3eyO3kWMjs/");
//            intent.putExtra("booksandauthor","BooksAndAuthor");
//            Log.d("CAT_PDF BandA",Books_and_Authors_1);
//            //https://docs.google.com/document/d/1GVmTKAFss3FJYLjpq_cKOhiywxqeHDKOE3eyO3kWMjs/
//            intent.putExtra("BooksAndAuthor",Books_and_Authors_1);
//            this.startActivity(intent);
//            // books and author
//        } else if (id == R.id.menu_art_culture) {
//            Log.d("CAT_PDF AandC",Art_and_Culture_1);
//            Intent intent = new Intent(this, view_ArtAndCulture.class);
//            intent.putExtra("artandculture","ArtAndCulture");
//            intent.putExtra("ArtAndCulture",Art_and_Culture_1);
//            this.startActivity(intent);
//            // art and culture
//        } else if (id == R.id.menu_constitution_of_india) {
//            Log.d("CAT_PDF ConstiofI",Constitution_of_India_1);
//            Intent intent = new Intent(this, view_ConstitutionOfIndia.class);
//            intent.putExtra("constitutionofindia","ConstitutionOfIndia");
//            intent.putExtra("ConstitutionOfIndia",Constitution_of_India_1);
//            this.startActivity(intent);
//            // constitution of india
//        } else if (id == R.id.menu_dynamic_gk) {
//            Log.d("CAT_PDF Dgk",DynamicGK_1);
//            Intent intent = new Intent(this, view_DynamicGK.class);
//            intent.putExtra("dynamicgk","DynamicGk");
//            intent.putExtra("DynamicGK",DynamicGK_1);
//            this.startActivity(intent);
//            //dynamic gk
//        } else if (id == R.id.menu_olympics) {
//            Log.d("CAT_PDF olym",Olympics_1);
//            Intent intent = new Intent(this, view_Olympics.class);
//            intent.putExtra("olympics","Olympics");
//            intent.putExtra("Olympics",Olympics_1);
//            this.startActivity(intent);
//            //olympics
//        }else if (id == R.id.menu_economics) {
//            Log.d("CAT_PDF eco",Economics_1);
//            Intent intent = new Intent(this, view_Economics.class);
//            intent.putExtra("economics","Economics");
//            intent.putExtra("Economics",Economics_1);
//            this.startActivity(intent);
//            //economics
//        }else if (id == R.id.menu_science) {
//            Log.d("CAT_PDF science",Science_1);
//            Intent intent = new Intent(this, view_Science.class);
//            intent.putExtra("science","Science");
//            //intent.putExtra("Science","1Oal6x5C7qrEU1mB7yiBgDCFbKYVdAwIE");
//            intent.putExtra("Science",Science_1);
//            this.startActivity(intent);
//            //science
//        }else if (id == R.id.menu_miscellaneous) {
//            Log.d("CAT_PDF misclle",Miscellaneous_1);
//            Intent intent = new Intent(this, view_Miscellaneous.class);
//            intent.putExtra("miscellaneous","Miscellaneous");
//            intent.putExtra("Miscellaneous",Miscellaneous_1);
//            this.startActivity(intent);
//            //miscellaneous
//        }else if (id == R.id.menu_fun_facts) {
//            Log.d("CAT_PDF FF",Funfacts_1);
//            Intent intent = new Intent(this, view_FunFacts.class);
//            intent.putExtra("funfacts","FunFacts");
//            intent.putExtra("FunFacts",Funfacts_1);
//            this.startActivity(intent);
//            //fun facts
//        }else if (id == R.id.menu_geography) {
//            Log.d("CAT_PDF geo",Geography_1);
//            Intent intent = new Intent(this, view_Geography.class);
//            intent.putExtra("geography","Geography");
//            intent.putExtra("Geography",Geography_1);
//            this.startActivity(intent);
//            //geography
//        }else if (id == R.id.menu_politics) {
//            Log.d("CAT_PDF poli",Politics_1);
//            Intent intent = new Intent(this, view_Politics.class);
//            intent.putExtra("politics","Politics");
//            intent.putExtra("Politics",Politics_1);
//            this.startActivity(intent);
//            //politics
//        }else if (id == R.id.menu_history) {
//            Log.d("CAT_PDF History",History_1);
//            Intent intent = new Intent(this, view_History.class);
//            intent.putExtra("history","History");
//            intent.putExtra("History",History_1);//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
//            this.startActivity(intent);
//            //history
//        }else if (id == R.id.menu_quiz_tf) {
//
//            Intent intent = new Intent(this, TF_QuizList.class);
//            this.startActivity(intent);
//            //true false quiz
//        }else if (id == R.id.menu_quiz_mcq) {
//            Intent intent = new Intent(this, QuizList.class);
//            this.startActivity(intent);
//            //multiple choice question quiz
//        } else if (id == R.id.menu_sport_achievement) {
//            Log.d("CAT_PDF SaA",Sports_and_Achievements_1);
//            Intent intent = new Intent(this, view_Sport_Achievement.class);
//            intent.putExtra("sports", "Sports");
//            intent.putExtra("Sports",Sports_and_Achievements_1);//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
//            this.startActivity(intent);
//            //sports and achievements 1wNJXKzxqi9k0US1L7qEeZk7DtENitswy
//        } else if (id == R.id.nav_share) {
//            Intent SLink = new Intent(Intent.ACTION_SEND);
//            SLink.setType("text/plain");
//            SLink.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL"); // Email 's Subject
//            SLink.putExtra(Intent.EXTRA_TEXT, "https://www.catking.in/appDownload");  //Email 's Greeting text
//            startActivity(Intent.createChooser(SLink, "CATKing App link"));
//        } else if (id == R.id.nav_send) {
//            Intent Email = new Intent(Intent.ACTION_SEND);
//            Email.setType("text/email");
//            Email.putExtra(Intent.EXTRA_EMAIL,
//                    new String[]{"vronpc@gmail.com"});  //developer 's email
//            Email.putExtra(Intent.EXTRA_SUBJECT,
//                    "My Suggestions to Admin"); // Email 's Subject
//            Email.putExtra(Intent.EXTRA_TEXT, "Dear CATKing," + "");  //Email 's Greeting text
//            startActivity(Intent.createChooser(Email, "Send Feedback:"));
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("MBA GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("SNAP", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("XAT", false, false,new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("IIFT", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("CMAT", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("MAT", false, false, new activity_coming_soon());
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("MICAT", true, false, new miCat_sa());
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }



        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("RRB GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("RRB Officer Scale", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("RRB Office Assistant", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("IBPS GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("IBPS PO", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("IBPS Clerk", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("RBI GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("RBI Grade B Officer", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("RBI Office Assistant", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("SBI GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("SBI PO", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        childModel = new MenuModel("SBI Clerk", false, false, new activity_coming_soon());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Statick GK", true, false, new activity_coming_soon()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Current Affairs", true, false, new activity_coming_soon()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Buy GK Course", true, false, new activity_coming_soon()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        Intent intentC = new Intent(getApplicationContext(),headerList.get(groupPosition).activity.getClass());
                        startActivity(intentC);
                        //webView.loadUrl(model.url);
                        onBackPressed();
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(headerList.get(groupPosition).url);
//                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    Intent intentC = new Intent(getApplicationContext(),model.activity.getClass());
                    startActivity(intentC);
                        //webView.loadUrl(model.url);
                        onBackPressed();
                }

                return false;
            }
        });
    }
}
