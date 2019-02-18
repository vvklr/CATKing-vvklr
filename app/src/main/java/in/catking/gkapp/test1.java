package in.catking.gkapp;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.catking.gkapp.fragC.fragC_quiz_model;
import in.catking.gkapp.fragC.frag_quiz_ELV_adapter;
import in.catking.gkapp.menuItems.cmat_sa;
import in.catking.gkapp.menuItems.ibps_clerk_sa;
import in.catking.gkapp.menuItems.ibps_po_sa;
import in.catking.gkapp.menuItems.iift_sa;
import in.catking.gkapp.menuItems.mat_sa;
import in.catking.gkapp.menuItems.miCat_sa;
import in.catking.gkapp.menuItems.rbi_gbo_sa;
import in.catking.gkapp.menuItems.rbi_oa_sa;
import in.catking.gkapp.menuItems.rrb_oa_sa;
import in.catking.gkapp.menuItems.rrb_os_sa;
import in.catking.gkapp.menuItems.sbi_clerk_sa;
import in.catking.gkapp.menuItems.sbi_po_sa;
import in.catking.gkapp.menuItems.snap_sa;
import in.catking.gkapp.menuItems.staticGK_sa;
import in.catking.gkapp.menuItems.xat_sa;
import in.catking.gkapp.quiz.ListQuizAdapter;
import in.catking.gkapp.quiz.QuizList;
import in.catking.gkapp.quiz.month_quiz;
import in.catking.gkapp.quiz.newMCQ;


public class test1 extends AppCompatActivity{

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;

    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();


    String QUIZ_SOURCE = "https://script.google.com/macros/s/AKfycbwq2-UV7olkZ-lD9uOLOiFIsL0DgsKbPq2war_IIl2JTwhIs2Q/exec?MxDQFLFiPj2cyRTpxua1LJLAIaA47-59-";
    String MONTH_SOURCE ="https://script.google.com/macros/s/AKfycbxeh2q1lesRvM8HJWArjEikSVXxMtIpzaojuyIqLAW4w2Kim50Y/exec?MJ7kX2bt0vSBnySUntAL6VzSAfNTxgSUZ";

    private List<fragC_quiz_model> dataList = new ArrayList<>();
    HashMap<fragC_quiz_model, List<fragC_quiz_model>> mList = new HashMap<>();

    static final String KEY_NO = "no";
    static final String KEY_QUIZNAME = "quiz_name";
    static final String KEY_QUIZAPI = "quiz_api";
    frag_quiz_ELV_adapter exLQAdapter;
    public ExpandableListView listQuiz;
//    public ListView listQuiz;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_month_quiz);
        setContentView(R.layout.activity_month_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();


        listQuiz = findViewById(R.id.exListQuiz);
        loader = findViewById(R.id.Qloader);
        listQuiz.setEmptyView(loader);

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            test1.DownloadNews quizTask = new DownloadNews();
            quizTask.execute();
            test1.DownloadMonth monthTask = new DownloadMonth();
            monthTask.execute();
            populateList();
            Log.d("QAWASA", String.valueOf(dataList));
            Log.d("QAWASA", String.valueOf(mList));

        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        ImageView nav_Head = (ImageView) headerview.findViewById(R.id.nav_head_image);
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
                        String a = jsonObject.optString(KEY_NO).toString();
                        String b = jsonObject.optString(KEY_QUIZNAME).toString();
                        String c = jsonObject.optString(KEY_QUIZAPI).toString();

                        fragC_quiz_model mModel = new fragC_quiz_model(b, true, false, c);
                        dataList.add(mModel);
                        Log.d("QAWASA", String.valueOf(mModel));
                        if (!mModel.hasChildren) {
                            mList.put(mModel, null);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class DownloadMonth extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";//xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country="+NEWS_SOURCE+"&apiKey="+API_KEY, urlParameters);
            xml = Function.excuteGet(MONTH_SOURCE,urlParameters);
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
                        String a = jsonObject.optString(KEY_NO).toString();
                        String b = jsonObject.optString(KEY_QUIZNAME).toString();
                        String c = jsonObject.optString(KEY_QUIZAPI).toString();

                        fragC_quiz_model mModel = new fragC_quiz_model(b, true, true, c);
                        dataList.add(mModel);
                        List<fragC_quiz_model> childModelsList = new ArrayList<>();
                        fragC_quiz_model childModel = new fragC_quiz_model(b, false, false, c);
                        childModelsList.add(childModel);
                        if (mModel.hasChildren) {
                            mList.put(mModel, childModelsList);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No quiz found", Toast.LENGTH_SHORT).show();
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

        MenuModel menuModel = new MenuModel("Home", true, false, new MainActivity()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("MBA GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("SNAP", false, false, new snap_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("XAT", false, false,new xat_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("MICAT", false, false,new miCat_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("IIFT", false, false, new iift_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("CMAT", false, false, new cmat_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("MAT", false, false, new mat_sa());
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("RRB GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("RRB Officer Scale", false, false, new rrb_os_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("RRB Office Assistant", false, false, new rrb_oa_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("IBPS GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("IBPS PO", false, false, new ibps_po_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("IBPS Clerk", false, false, new ibps_clerk_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("RBI GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("RBI Grade B Officer", false, false, new rbi_gbo_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("RBI Office Assistant", false, false, new rbi_oa_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("SBI GK", true, true, new activity_coming_soon());
        headerList.add(menuModel);
        childModel = new MenuModel("SBI PO", false, false, new sbi_po_sa());
        childModelsList.add(childModel);

        childModel = new MenuModel("SBI Clerk", false, false, new sbi_clerk_sa());
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Static GK", true, false, new staticGK_sa()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Current Affairs", true, false, new activity_coming_soon()); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Buy GK Course", true, false, new buy_gk_course()); //Menu of Android Tutorial. No sub menus
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

    private void populateList() {

        exLQAdapter = new frag_quiz_ELV_adapter(this, dataList, mList);
        listQuiz.setAdapter(exLQAdapter);

        listQuiz.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (dataList.get(groupPosition).isGroup) {
                    if (!dataList.get(groupPosition).hasChildren) {
                        Intent intentC = new Intent(getApplicationContext(),month_quiz.class);
                        intentC.putExtra("month_url",dataList.get(groupPosition).api);
                        startActivity(intentC);
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        listQuiz.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (mList.get(dataList.get(groupPosition)) != null) {
                    fragC_quiz_model model = mList.get(dataList.get(groupPosition)).get(childPosition);
                    Intent intentC = new Intent(getApplicationContext(),newMCQ.class);
                    intentC.putExtra("month_url",model.api);
                    startActivity(intentC);
                    onBackPressed();
                }

                return false;
            }
        });
    }
}
