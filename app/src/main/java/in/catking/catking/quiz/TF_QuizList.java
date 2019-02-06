package in.catking.catking.quiz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import in.catking.catking.ExpandableListAdapter;
import in.catking.catking.Function;
import in.catking.catking.MainActivity;
import in.catking.catking.MenuModel;
import in.catking.catking.R;
import in.catking.catking.activity_coming_soon;
import in.catking.catking.miCat_sa;
import in.catking.catking.pdf_SheetData;
import in.catking.catking.view_ArtAndCulture;
import in.catking.catking.view_BooksAndAuthor;
import in.catking.catking.view_ConstitutionOfIndia;
import in.catking.catking.view_DynamicGK;
import in.catking.catking.view_Economics;
import in.catking.catking.view_FunFacts;
import in.catking.catking.view_Geography;
import in.catking.catking.view_History;
import in.catking.catking.view_Miscellaneous;
import in.catking.catking.view_Olympics;
import in.catking.catking.view_Politics;
import in.catking.catking.view_Science;
import in.catking.catking.view_Sport_Achievement;


public class TF_QuizList extends AppCompatActivity {
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    String QUIZ_SOURCE = "https://script.google.com/macros/s/AKfycbxaw036z5nEof8HAU_ws5YC54o63tVh603cM9NDceyVkVd9Mts/exec?MnTYt0D2S0YhWZdXx2vSKPEwhP7cC3CUJ";

    private ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_NO = "no";
    static final String KEY_QUIZNAME = "quiz_name";
    static final String KEY_QUIZAPI = "quiz_api";
    public ListView listQuiz;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ql);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listQuiz = findViewById(R.id.listQuiz);
        loader = findViewById(R.id.Qloader);
        listQuiz.setEmptyView(loader);

        listQuiz.setClipToPadding(false);
        listQuiz.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listQuiz.setDividerHeight(px);
        listQuiz.setFadingEdgeLength(0);
        listQuiz.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listQuiz.setPadding(px, px, px, px);
        listQuiz.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            TF_QuizList.DownloadNews quizTask = new DownloadNews();
            quizTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }


        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View navFooter1 = findViewById(R.id.imageButton_f);
        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                    if (applicationInfo.enabled) {
                        // http://stackoverflow.com/a/24547437/1048340
                        Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/rahulcatking/");
                        Intent i = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(i);
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/rahulcatking/")));
                }
                //newFacebookIntent(getApplicationContext().getPackageManager(),"https://www.facebook.com/rahulcatking/");
            }
        });

        View navFooter2 = findViewById(R.id.imageButton_i);
        navFooter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/rahul_catking");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/rahul_catking")));
                }
            }
        });


        View navFooter3 = findViewById(R.id.imageButton_y);
        navFooter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + "k5PwQ1n2x4U"));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/channel/UC4eEFUtZeW6iOqH8e9e0CyQ"));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
            }
        });

        View navFooter4 = findViewById(R.id.imageButton_q);
        navFooter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.quora.com/profile/Rahul-Singh-6180"));
                startActivity(webIntent);
            }
        });

        View nav_Head = findViewById(R.id.nav_head_image);
        nav_Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
            }
        });

    }
    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            xml = Function.excuteGet(QUIZ_SOURCE,urlParameters);
            return  xml;
        }
        @Override
        protected void onPostExecute(String xml) {

            if(xml.length()>10){ // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("quiz");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_NO, jsonObject.optString(KEY_NO).toString());
                        map.put(KEY_QUIZNAME, jsonObject.optString(KEY_QUIZNAME).toString());
                        map.put(KEY_QUIZAPI, jsonObject.optString(KEY_QUIZAPI).toString());
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                TF_ListQuizAdapter adapter = new TF_ListQuizAdapter(getApplicationContext(), dataList);
                listQuiz.setAdapter(adapter);

                listQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getApplicationContext(), new_TF.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_QUIZAPI));
                        i.putExtra("UID",dataList.get(+position).get(KEY_NO));
                        startActivity(i);
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }



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
                        onBackPressed();
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
