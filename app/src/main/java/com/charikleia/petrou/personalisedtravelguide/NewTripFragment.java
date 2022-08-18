package com.charikleia.petrou.personalisedtravelguide;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewTripFragment extends Fragment {
    @Nullable

//    private LinearLayout Category;
    private CheckBox[] checkBox, scheckBox;//for category & subcategory
    private Connection connect;
    private String records="";
    List<String> CategoryList =new ArrayList<String>();
    List<String> SubcategoryList =new ArrayList<String>();
    List<String> locations=new ArrayList<String>();
    LinearLayout ll;
    DatePickerDialog.OnDateSetListener setListener;
    public Date d1 = new Date();
    public Date d2 = new Date();

    List<String> cat =new ArrayList<String>();
    List<String> subcat =new ArrayList<String>();
    List<String> locs =new ArrayList<String>();
    List<Date> dates=new ArrayList<Date>();
//    List<String> =new ArrayList<String>();
//    List<String> =new ArrayList<String>();
//    List<String> =new ArrayList<String>();
//    List<String> =new ArrayList<String>();
//    List<String> =new ArrayList<String>();
//    List<String> =new ArrayList<String>();


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_new_trip, container, false);

        ll = (LinearLayout) view.findViewById(R.id.LinearLayout); //initialize layout for pickers

        return chooseDay(view);
    }



    public View chooseCategory(View view){
        cat.clear();//clears the choices if called again for change
        try {//connection to db
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null){
                System.out.println("Trying connection ");
                String query1 = "Select distinct CATEGORY from greecepois";
//                String query2 = "select distinct SUBCATEGORY from greecepois";
                Statement st = connect.createStatement();
                ResultSet rs1 = st.executeQuery(query1);
                while(rs1.next()) {
//                    records += rs.getString(1) + " " + rs.getString(2) + "\n";
                    CategoryList.add(rs1.getString(1));
                    System.out.println("Cat: "+CategoryList);

                }
                rs1.close();
//                ResultSet rs2 = st.executeQuery(query2);
//                while(rs2.next()) {
////                    records += rs.getString(1) + " " + rs.getString(2) + "\n";
//                    SubcategoryList.add(rs2.getString(1));
//                    System.out.println("Subcat: "+SubcategoryList);
//
//                }
//                rs2.close();
            }else{
//                ConnectionResult.add("Check Connection!");
            }


        }catch (Exception e){
            System.out.println("Exception:" + e);
        }

        // Initialize RelativeLayout
        System.out.println(R.id.LinearLayout);
//        ll = (LinearLayout) view.findViewById(R.id.LinearLayout);
        TextView textView = new TextView(getActivity());
        textView.setText("Choose Category: \n");
        ll.addView(textView);


        checkBox = new CheckBox[CategoryList.size()];
        for (int i  = 0; i<CategoryList.size(); i++){
//            checkBox = new CheckBox(getActivity());
            checkBox[i] = new CheckBox(getActivity());
            checkBox[i].setText(CategoryList.get(i));
//            checkBox[i].setOnClickListener(getOnClickDoSomething(checkBox[i]));
            ll.addView(checkBox[i]);
        }

        Button myButton = new Button(getActivity());
        myButton.setText("Push Me");
        ll.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll.setVisibility(ll.GONE);
                for (int i  = 0; i<CategoryList.size(); i++){
//                   checkBox = new CheckBox(getActivity());
                    if (checkBox[i].isChecked()){
                        cat.add(checkBox[i].getText().toString());
                    }
                }
                System.out.println("Selevted Categories: "+cat);
                if (cat.isEmpty()){
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Choose Category");
                    alertbld.setMessage("You need to choose at least one category.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
                }else{
                    ll.removeAllViews();
                    chooseSubcategory(view);
                }

            }
        });

        return  view;
    }



    public View chooseSubcategory(View view){
        subcat.clear();//clears the choices if called again for change
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null){
                System.out.println("Trying connection ");
//                String query1 = "Select distinct CATEGORY from greecepois";
                String query2 = "select distinct SUBCATEGORY from greecepois";
                Statement st = connect.createStatement();

                ResultSet rs2 = st.executeQuery(query2);
                while(rs2.next()) {
//                    records += rs.getString(1) + " " + rs.getString(2) + "\n";
                    SubcategoryList.add(rs2.getString(1));
                    System.out.println("Subcat: "+SubcategoryList);

                }
                rs2.close();
            }else{
//                ConnectionResult.add("Check Connection!");
            }


        }catch (Exception e){
            System.out.println("Exception:" + e);
        }

        // Initialize RelativeLayout
        System.out.println(R.id.LinearLayout);
//        ll = (LinearLayout) view.findViewById(R.id.LinearLayout);
        TextView textView = new TextView(getActivity());
        textView.setText("Choose Subategory: \n");
        ll.addView(textView);


        checkBox = new CheckBox[SubcategoryList.size()];
        for (int i  = 0; i<SubcategoryList.size(); i++){
//            checkBox = new CheckBox(getActivity());
            checkBox[i] = new CheckBox(getActivity());
            checkBox[i].setText(SubcategoryList.get(i));
//            checkBox[i].setOnClickListener(getOnClickDoSomething(checkBox[i]));
            ll.addView(checkBox[i]);
        }

        Button myButton = new Button(getActivity());
        myButton.setText("NEXT");
        ll.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll.setVisibility(ll.GONE);
                for (int i  = 0; i<SubcategoryList.size(); i++){
//                   checkBox = new CheckBox(getActivity());
                    if (checkBox[i].isChecked()){
                        subcat.add(checkBox[i].getText().toString());
                    }
                }
                System.out.println("Selected Subcategories: "+subcat);
                if (subcat.isEmpty()){
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Choose Subcategory");
                    alertbld.setMessage("You need to choose at least one subcategory.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
                }else{

                }

            }
        });


        return  view;
    }


    public View chooseDay(View view){
        dates.clear();//clears the choices if called again for change
        // Initialize RelativeLayout
        System.out.println(R.id.LinearLayout);
//        ll = (LinearLayout) view.findViewById(R.id.LinearLayout);

        TextView startDate = new TextView(getActivity()); // create a new textview
        startDate.setText("Choose start day: \n");
        ll.addView(startDate);
        EditText sDate = new EditText(getActivity()); // create a new edittext
        sDate.setClickable(false);
        sDate.setFocusable(false);
        sDate.setEnabled(false);
        ll.addView(sDate);
        TextView endDate = new TextView(getActivity()); // create a new textview
        endDate.setText("Choose end day: \n");
        ll.addView(endDate);
        EditText eDate = new EditText(getActivity()); // create a new edittext
        eDate.setClickable(false);
        eDate.setFocusable(false);
        eDate.setEnabled(false);
        ll.addView(eDate);
//        DatePickerDialog.OnDateSetListener setListener;
        Calendar calendar = Calendar.getInstance();
//        Calendar calendar1 = Calendar.getInstance();



        //listener for start day
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updatestartCalendar();
            }
            private  void updatestartCalendar(){
                String format = "yyyy/MM/dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                sDate.setText(dateFormat.format(calendar.getTime()));
//                System.out.println("aaa: "+sDate.getText());
                String day1 = sDate.getText().toString();// temporary variable
//                System.out.println("sss: "+day1);
                try {
                    d1= dateFormat.parse(day1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("Check: "+d1);

            }
        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //listener for end date
        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateendCalendar();
            }

            private  void updateendCalendar(){
                String format = "yyyy/MM/dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//                sDate.setText(dateFormat.format(calendar.getTime()));
                eDate.setText(dateFormat.format(calendar.getTime()));
//                System.out.println("aaa: "+eDate.getText());
                String day2 = eDate.getText().toString();// temporary variable
//                System.out.println("sss: "+day2);
                try {
                    d2= dateFormat.parse(day2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("Check: "+d1+d2);
            }
        };
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date1, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Button myButton = new Button(getActivity());
        myButton.setText("NEXT");
        ll.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(d1);
//                ll.setVisibility(ll.GONE);

                //check: if dates are wrong does not load the next layout components and shows message
                if (d1.compareTo(d2) > 0){
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Wrong dates!");
                    alertbld.setMessage("Check the range of the selected dates.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();

                }
                else if(d1 == null & d2 == null ){
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Select Dates");
                    alertbld.setMessage("You need to select dates to to the next step.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
                }else if (d1.compareTo(d2) <= 0) {
                    dates.add(d1);
                    dates.add(d2);
                    ll.removeAllViews();
                    chooseLocation(view);
                }
            }
        });

        return  view;
    }

//    View.OnClickListener getOnClickDoSomething(final Button btn){
//        return new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                System.out.println("Checkbox ID: "+ btn.getId() +"Text: "+ btn.getText().toString());
//            }
//        };
//    }


    public View chooseLocation(View view){
        locs.clear();//clears the choices if called again for change
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null){
                System.out.println("Trying connection ");
                String query1 = "Select distinct LOCATION from greecepois";
                Statement st = connect.createStatement();
                ResultSet rs1 = st.executeQuery(query1);
                while(rs1.next()) {
//                    records += rs.getString(1) + " " + rs.getString(2) + "\n";
                    locations.add(rs1.getString(1));
                    System.out.println("Locations: "+locations);

                }
                rs1.close();

            }else{
                System.out.println("Connection Error!");
            }


        }catch (Exception e){
            System.out.println("Exception:" + e);
        }

        TextView textView = new TextView(getActivity());
        textView.setText("Choose Location: \n");
        ll.addView(textView);


        checkBox = new CheckBox[locations.size()];
        for (int i  = 0; i<locations.size(); i++){
//            checkBox = new CheckBox(getActivity());
            checkBox[i] = new CheckBox(getActivity());
            checkBox[i].setText(locations.get(i));
//            checkBox[i].setOnClickListener(getOnClickDoSomething(checkBox[i]));
            ll.addView(checkBox[i]);
        }

        Button myButton = new Button(getActivity());
        myButton.setText("NEXT");
        ll.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ll.setVisibility(ll.GONE);
                for (int i = 0; i< locations.size(); i++){
//                   checkBox = new CheckBox(getActivity());
                    if (checkBox[i].isChecked()){
                        locs.add(checkBox[i].getText().toString());
                    }
                }
                System.out.println("Selected Locations: "+locs);

                if (locs.isEmpty()){
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Select Location!");
                    alertbld.setMessage("You need to select at least one location to get to the next step.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
                }else {
                    ll.removeAllViews();
                    chooseCategory(view);
                }


            }
        });

        return  view;
    }

    public View showFinalChoices(View view){



        return view;
    }

}


