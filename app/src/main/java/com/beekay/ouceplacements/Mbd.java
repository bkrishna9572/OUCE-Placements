package com.beekay.ouceplacements;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Mbd extends AppCompatActivity {
    Toolbar toolbar;
    ImageView image;
    String title;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    ArrayList<Details> detailList;
    Bitmap bitmap;
    HashMap<String,String> cookies;
    ProgressDialog progressDialog;

    public String getMyTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbd);
        ArrayList<HashMap<String,String>> cooks= Cooks.getCookies();
        try {
            setCookies(cooks.get(0));
            new LoadMBD().execute("");
        }catch (NullPointerException ex){
            finish();
        }
        toolbar=(Toolbar)findViewById(R.id.anim_toolbar);
        image=(ImageView)findViewById(R.id.image);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
    }

    @Override
    protected void onRestart() {
        try {
            HashMap<String,String> cookies = Cooks.getCookies().get(0);
        }catch (NullPointerException ex){
            if(progressDialog!=null)
                progressDialog.dismiss();
            finish();
        }
        super.onRestart();
    }

    public class LoadMBD extends AsyncTask<String,String,ArrayList<Details>>{

        @Override
        protected ArrayList<Details> doInBackground(String... params) {
            publishProgress("");
            detailList=new ArrayList<>();
            Details details=new Details();
            details.roll=NetCheck.getUser();
            try{
                Document doc= Jsoup.connect("http://oucecareers.org/students/showMbd.php?rollno=" + NetCheck.getUser()).cookies(getCookies()).timeout(50000).get();
                String Url=doc.select("img").attr("src").toString();
                Connection.Response res=Jsoup.connect("http://oucecareers.org/"+Url.substring(3)).followRedirects(false).ignoreContentType(true).timeout(50000).ignoreHttpErrors(true).execute();
                if(res.statusCode()==200) {
                    BufferedInputStream in = new BufferedInputStream((new URL("http://oucecareers.org/"+Url.substring(3))).openStream());
                    setBitmap(BitmapFactory.decodeStream(in));
                    in.close();
                }
                details.gender=doc.select("select > option[selected=\"selected\"").text();
                details.dream=doc.select("div#companyholder").text();
                for(Element  e: doc.select("input")){
                    switch(e.attr("name").toString()){
                        case "yoc":
                            details.year=e.val();
                            break;
                        case "name":
                            setTitle(e.val());
                            break;
                        case "course":
                            details.course=e.val();
                            break;
                        case "branch":
                            details.branch=e.val();
                            break;
                        case "dob":
                            details.dob = e.val();
                            break;
                        case "pob":
                            details.placeOfBirth = e.val();
                            break;
                        case "age":
                            details.age = e.val();
                            break;
                        case "nationality":
                            details.nationality = e.val();
                            break;
                        case "height":
                            details.height = e.val();
                            break;
                        case "eyesight":
                            details.eyesight = e.val();
                            break;
                        case "weight":
                            details.weight=e.val();
                            break;
                        case "fname":
                            details.father=e.val();
                            break;
                        case "occupation":
                            details.occupation=e.val();
                            break;
                        case "annualincome":
                            details.income = e.val();
                            break;
                        case "city1":
                            details.presentCity=e.val();
                            break;
                        case "city2":
                            details.permanentCity=e.val();
                            break;
                        case "state1":
                            details.presentState=e.val();
                            break;
                        case "state2":
                            details.permanentState=e.val();
                            break;
                        case "country1":
                            details.presentCountry=e.val();
                            break;
                        case "country2":
                            details.permanentCountry=e.val();
                            break;
                        case "pin1":
                            details.presentPin = e.val();
                            break;
                        case "pin2":
                            details.permanentPin = e.val();
                            break;
                        case "phone1":
                            details.phone1=e.val();
                            break;
                        case "phone2":
                            details.phone2=e.val();
                            break;
                        case "emailid1":
                            details.mail1=e.val();
                            break;
                        case "emailid2":
                            details.mail2=e.val();
                            break;
                        case "sscboard":
                            details.sboard=e.val();
                            break;
                        case "ssccollege":
                            details.scollege=e.val();
                            break;
                        case "sscaddress":
                            details.saddress=e.val();
                            break;
                        case "sscyop":
                            details.syop=e.val();
                            break;
                        case "sscgpa":
                            details.scgpa=e.val();
                            break;
                        case "ipboard":
                            details.iboard=e.val();
                            break;
                        case "ipcollege":
                            details.icollege=e.val();
                            break;
                        case "ipaddress":
                            details.iaddress=e.val();
                            break;
                        case "ipyop":
                            details.iyop=e.val();
                            break;
                        case "ipgpa":
                            details.igpa=e.val();
                            break;
                        case "diplomaboard":
                            details.dboard=e.val();
                            break;
                        case "diplomacollege":
                            details.dcollege=e.val();
                            break;
                        case "diplomaaddress":
                            details.daddress=e.val();
                            break;
                        case "diplomayop":
                            details.dyop=e.val();
                            break;
                        case "diplomagpa":
                            details.dgpa=e.val();
                            break;
                        case "bscboard":
                            details.bboard=e.val();
                            break;
                        case "bsccollege":
                            details.bcollege=e.val();
                            break;
                        case "bscaddress":
                            details.baddress=e.val();
                            break;
                        case "bscyop":
                            details.byop=e.val();
                            break;
                        case "bscgpa":
                            details.bgpa=e.val();
                            break;
                        case "eiecetrank":
                            details.eamcet=e.val();
                            break;
                        case "avggpa":
                            details.aggregate=e.val();
                            break;
                        case "semister":
                            details.sem=e.val();
                            break;
                        case "titleofproject":
                            details.project=e.val();
                            break;
                        case "gatescore":
                            details.gatescore=e.val();
                            break;
                        case "pgavggpa":
                            details.pgscore=e.val();
                            break;
                        case "pgsem3title":
                            details.thesis=e.val();
                            break;
                        case "itdname":
                            details.itdname=e.val();
                            break;
                        case "itdduration":
                            details.itdduration=e.val();
                            break;
                        case "itdtypeoftraining":
                            details.itdtype=e.val();
                            break;
                    }
                }
                Elements addresses=doc.select("textarea");
                for(Element e: addresses){
                    if(e.attr("name").toString().equals("address1")){
                        details.present=e.val();
                    }
                    else if(e.attr("name").toString().equals("address2")){
                        details.permanent=e.val();
                    }
                    else if(e.attr("name").toString().equals("extracirculars")){
                        details.extras=e.val();
                    }
                    detailList.add(details);
                }


                return detailList;
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog=new ProgressDialog(Mbd.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading MBD");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(ArrayList<Details> s) {
            progressDialog.dismiss();
            if(s!=null){
                image.setImageBitmap(getBitmap());
                getSupportActionBar().setTitle(getMyTitle());
                collapsingToolbarLayout.setTitle(getMyTitle());
                recyclerView=(RecyclerView)findViewById(R.id.mbdcardlist);
                LinearLayoutManager llm=new LinearLayoutManager(Mbd.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                RecyclerView.Adapter adapter=new DetailAdapter(s);
                recyclerView.setAdapter(adapter);
            }
            else{
                Toast.makeText(Mbd.this,"Timed out while connecting",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }
}
