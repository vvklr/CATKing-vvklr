package in.catking.catking;
//Vishal Raut
//Email me on vr.iitb@gmail.com if you come across any problem
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
import in.catking.catking.pdf_sheet_name;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
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
//        pdf_sheet_name cls = new pdf_sheet_name();
//        cls.pdf_sheet_name_fun();
//
//
//        Science_1 = cls.getScience();
//        Geography_1 = pdf_sheet_name.getGeography();
//        Books_and_Authors_1 = pdf_sheet_name.getBooks_and_Authors();
//        Olympics_1 = pdf_sheet_name.getOlympics();
//        Sports_and_Achievements_1 = pdf_sheet_name.getSports_and_Achievements();
//        Art_and_Culture_1 = pdf_sheet_name.getArt_and_Culture();
//        History_1 = pdf_sheet_name.getHistory();
//        Politics_1 = pdf_sheet_name.getPolitics();
//        Constitution_of_India_1 = pdf_sheet_name.getConstitution_of_India();
//        Miscellaneous_1 = pdf_sheet_name.getMiscellaneous();
//        Funfacts_1 = pdf_sheet_name.getFunfacts();
//        DynamicGK_1 = pdf_sheet_name.getDynamicGK();
//        Economics_1 = pdf_sheet_name.getEconomics();
//
//        Log.d("CAT_PDF","This is for gk: "+DynamicGK_1);
//        Log.d("CAT_PDF","This is for Science: "+Science_1);

//        new LetsCollectJSON("https://script.google.com/macros/s/AKfycbxlRWQK1ypdlapRhFWoI1WY3B5ccQsYduZp7EVCIiBGz1vrcNI/exec?MAT477-BuRhAq-xS2vtUhjkwhP7cC3CUJ");

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
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

            Intent intent = new Intent(this, test4.class);
            this.startActivity(intent);
            //true false quiz
        }else if (id == R.id.menu_quiz_mcq) {
            Intent intent = new Intent(this, test3.class);
            this.startActivity(intent);
            //multiple choice question quiz
        } else if (id == R.id.menu_sport_achievement) {
            Intent intent = new Intent(this, view_Sport_Achievement.class);
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
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.menu_books_author) {
//            Intent intent = new Intent(this, view_BooksAndAuthor.class);
//            intent.putExtra("booksandauthor","BooksAndAuthor");
//            intent.putExtra("BooksAndAuthor",Books_and_Authors_1);
//            this.startActivity(intent);
//            // books and author
//        } else if (id == R.id.menu_art_culture) {
//            Intent intent = new Intent(this, view_ArtAndCulture.class);
//            intent.putExtra("artandculture","ArtAndCulture");
//            intent.putExtra("ArtAndCulture",Art_and_Culture_1);
//            this.startActivity(intent);
//            // art and culture
//        } else if (id == R.id.menu_constitution_of_india) {
//            Intent intent = new Intent(this, view_ConstitutionOfIndia.class);
//            intent.putExtra("constitutionofindia","ConstitutionOfIndia");
//            intent.putExtra("ConstitutionOfIndia",Constitution_of_India_1);
//            this.startActivity(intent);
//            // constitution of india
//        } else if (id == R.id.menu_dynamic_gk) {
//            Intent intent = new Intent(this, view_DynamicGK.class);
//            intent.putExtra("dynamicgk","DynamicGk");
//            intent.putExtra("DynamicGK",DynamicGK_1);
//            this.startActivity(intent);
//            //dynamic gk
//        } else if (id == R.id.menu_olympics) {
//            Intent intent = new Intent(this, view_Olympics.class);
//            intent.putExtra("olympics","Olympics");
//            intent.putExtra("Olympics",Olympics_1);
//            this.startActivity(intent);
//            //olympics
//        }else if (id == R.id.menu_economics) {
//            Intent intent = new Intent(this, view_Economics.class);
//            intent.putExtra("economics","Economics");
//            intent.putExtra("Economics",Economics_1);
//            this.startActivity(intent);
//            //economics
//        }else if (id == R.id.menu_science) {
//            Intent intent = new Intent(this, view_Science.class);
//            intent.putExtra("science","Science");
//            intent.putExtra("Science",Science_1);
//            Log.d("View_science","This is the value of science: "+Science_1);
//            this.startActivity(intent);
//            //science
//        }else if (id == R.id.menu_miscellaneous) {
//            Intent intent = new Intent(this, view_Miscellaneous.class);
//            intent.putExtra("miscellaneous","Miscellaneous");
//            intent.putExtra("Miscellaneous",Miscellaneous_1);
//            this.startActivity(intent);
//            //miscellaneous
//        }else if (id == R.id.menu_fun_facts) {
//            Intent intent = new Intent(this, view_FunFacts.class);
//            intent.putExtra("funfacts","FunFacts");
//            intent.putExtra("FunFacts",Funfacts_1);
//            this.startActivity(intent);
//            //fun facts
//        }else if (id == R.id.menu_geography) {
//            Intent intent = new Intent(this, view_Geography.class);
//            intent.putExtra("geography","Geography");
//            intent.putExtra("Geography",Geography_1);
//            this.startActivity(intent);
//            //geography
//        }else if (id == R.id.menu_politics) {
//            Intent intent = new Intent(this, view_Politics.class);
//            intent.putExtra("politics","Politics");
//            intent.putExtra("Politics",Politics_1);
//            this.startActivity(intent);
//            //politics
//        }else if (id == R.id.menu_history) {
//            Intent intent = new Intent(this, view_History.class);
//            intent.putExtra("history","History");
//            intent.putExtra("History",History_1);//1cdiOhKalAJgP2jVixBWJwnuGKdDH4m5v
//            this.startActivity(intent);
//            //history
//        }else if (id == R.id.menu_quiz_tf) {
//
//            Intent intent = new Intent(this, test4.class);
//            this.startActivity(intent);
//            //true false quiz
//        }else if (id == R.id.menu_quiz_mcq) {
//            Intent intent = new Intent(this, test3.class);
//            this.startActivity(intent);
//            //multiple choice question quiz
//        } else if (id == R.id.menu_sport_achievement) {
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
//
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
    //TODO:let collect json data for questions

}
