package com.charikleia.petrou.personalisedtravelguide;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
//import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewTripFragment extends Fragment {
    @Nullable

    //for location permission - google places variables CONSTANTS
    public static final String TAG = NewTripFragment.class.getSimpleName();
    private static  final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static  final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static  final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    FindAutocompletePredictionsRequest.Builder autocompleteFragment = null;
    List<Place.Field> fields;
    PlacesClient placesClient;
    Place place;
    String apiKey = "AIzaSyDraeLcgw8liZ0ZJjwapMo07w8rrrL3SB0";
    Fragment myFrag;


    String placeId, placeName;
    private Boolean mLocationPermissionGranted = false;
    public RecyclerView recyclerView;
//    private LinearLayout Category;
    private CheckBox[] checkBox, scheckBox;//for category & subcategory
    private RadioButton[] radiobtn;
    private Connection connect;
    private String records="";
    private String categoryStr = "Shopping & Fashion\n" + "Tours & Sightseeing\n" + "Water Sports\n" + "Wedding & Honeymoons\n" +
            "Family Friendly Activities\n" +
            "Spa & Massage\n" +
            "Gym\n" +
            "Museum \n" +
            "restaurants\n" +
            "lunch \n" +
            "dinner \n" +
            "cafe\n" +
            "brunch\n" +
            "groceries\n" +
            "cocktail bar\n" +
            "night club\n" +
            "beaches\n" +
            "parks\n" +
            "mountains\n" +
            "deserts and forests\n" +
            "historical place\n" +
            "historical landmark\n" +
            "attraction\n" +
            "monument\n" +
            "ancient temple\n" +
            "zoo\n" +
            "aquaria\n" +
            "botanical gardens\n" +
            "buildings and structures \n" +
            "fort \n" +
            "castle \n" +
            "libraries\n" +
            "former prisons\n" +
            "skyscraper\n" +
            "bridge\n" +
            "theme parks and carnivals\n" +
            "living history museums\n" +
            "public art\n" +
            "sculptures\n" +
            "statues\n" +
            "murals\n" +
            "Other";
    List<String> CategoryList, selectedCategory =new ArrayList<String>();
    List<String> SubcategoryList =new ArrayList<String>();
    List<String> locations=new ArrayList<String>();
    String other;//additional options in category
    LinearLayout ll;
    DatePickerDialog.OnDateSetListener setListener;
    public Date d1 = new Date();
    public Date d2 = new Date();
    public int  hour, bdg=-1, minutes =-1;
    Date tempTime;


    List<String> cat =new ArrayList<String>();
    List<String> subcat =new ArrayList<String>();
    List<String> locs =new ArrayList<String>();
    List<Date> dates=new ArrayList<Date>();
    List<Integer> times =new ArrayList<Integer>();
//    List<String> questions =new ArrayList<String>();
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
//        ll.setId(Integer.parseInt("1"));

        CategoryList = Arrays.asList(categoryStr.split("\n"));
        System.out.println(CategoryList);


        // Initialize the SDK
        Places.initialize(getActivity().getApplicationContext(), apiKey);
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }



        return chooseDay(view);
//        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
//            Place place = Autocomplete.getPlaceFromIntent(data);
//            if (place == null) {
//                System.out.println("Place: " + place.getName() + ", " + place.getId());
//                return;
//            }
//        }
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = Autocomplete.getPlaceFromIntent(data);
                placeName = place.getName();
                placeId = place.getId();
//                Place place = PlacePicker.getPlace(this, data);
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                System.out.println("Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.i(TAG, status.getStatusMessage());
                System.out.println("Status mesage: " + status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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


        //shows the choices of category in checkbox
        checkBox = new CheckBox[CategoryList.size()];
        for (int i  = 0; i<CategoryList.size(); i++){
//            checkBox = new CheckBox(getActivity());
            checkBox[i] = new CheckBox(getActivity());
            checkBox[i].setText(CategoryList.get(i));
//            checkBox[i].setOnClickListener(getOnClickDoSomething(checkBox[i]));
            ll.addView(checkBox[i]);



//            for (int k  = 0; i<CategoryList.size(); k++){
//                //checks if other is selected in order for the user to write other options
//                if (checkBox[i].getText() == "Other"){
//                    checkBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                       @Override
//                       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//                           EditText editText = new EditText(getActivity());ll.addView(editText);
//                           editText.setHint("Acceptable Characters:letters, numbers and commas");
//                           editText.addTextChangedListener(new TextWatcher() {
//
//                               public void afterTextChanged(Editable s) {
//                                   other = String.valueOf(editText.getText());
//                               }
//
//                               public void beforeTextChanged(CharSequence s, int start,
//                                                             int count, int after) {
//                               }
//
//                               public void onTextChanged(CharSequence s, int start,
//                                                         int before, int count) {
//                               }
//                           });
////                           other = editText.getText().split(",");
////                           setOnClickListener(new View.OnClickListener() {
////                               @Override
////                               public void onClick(View view) {
////                                   AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
////                                   alertbld.setTitle("Acceptable Characters");
////                                   alertbld.setMessage("Check your input in Other. \nOnly letters, numbers and commas are acceptable");
////                                   alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
////                                       @Override
////                                       public void onClick(DialogInterface dialogInterface, int i) {
////
////                                       }
////                                   });
////                                   alertbld.show();
////                               }
////                           });
//                                                               }
//                                                           }
//                    );
//                }
//            }

        }
        EditText editText = new EditText(getActivity());
        editText.setHint("Acceptable Characters:letters, numbers and commas");
//        editText.setVisibility(View.INVISIBLE);
        ll.addView(editText);



        Button myButton = new Button(getActivity());
        myButton.setText("Next");
        ll.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (CheckBox checkbox : checkBox){
                    if(checkbox.isChecked())
                        cat.add(checkbox.getText().toString());
                }
                System.out.println("Selected Categories: "+cat);
//                ll.setVisibility(ll.GONE);

//                for (int i  = 0; i<CategoryList.size(); i++){
////                   checkBox = new CheckBox(getActivity());
//                    if (checkBox[i].isChecked()){//fills from predefined options
//                        cat.add(checkBox[i].getText().toString());
//                    }
//
//                    if (checkBox[i].getText() == "Other" && checkBox[i].isChecked()){//fills from user's input
//                        cat.add(String.valueOf(other.split(",")));
//                    }
//                }
//                System.out.println("Selected Categories: "+cat);

                //alert user that at least one must be chosen
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
                    showFinalChoices(view);
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
                    ll.removeAllViews();
                    showFinalChoices(view);
                }
            }
        });


        return  view;
    }


    public View chooseDay(View view){
        dates.clear();//clears the choices if called again for change
        times.clear();//clears the choices if called again for change
        // Initialize RelativeLayout
        System.out.println(R.id.LinearLayout);
//        ll = (LinearLayout) view.findViewById(R.id.LinearLayout);

// code for dates
        TextView startDate = new TextView(getActivity()); // create a new textview
        startDate.setText("Choose start day: \n");
        ll.addView(startDate);
        EditText sDate = new EditText(getActivity()); // create a new edittext
//        sDate.setClickable(false);
        sDate.setFocusable(false);
        sDate.setEnabled(false);
        ll.addView(sDate);
        TextView endDate = new TextView(getActivity()); // create a new textview
        endDate.setText("Choose end day: \n");
        ll.addView(endDate);
        EditText eDate = new EditText(getActivity()); // create a new edittext
//        eDate.setClickable(false);
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

// code for times
        TextView startTime = new TextView(getActivity()); // create a new textview
        startTime.setText("What time to begin: \n");
        ll.addView(startTime);
        EditText sTime = new EditText(getActivity()); // create a new edittext
//        sTime.setClickable(false);
        sTime.setFocusable(false);
        sTime.setEnabled(false);
        ll.addView(sTime);

        TextView endTime = new TextView(getActivity()); // create a new textview
        endTime.setText("What time to end the trip: \n");
        ll.addView(endTime);
        EditText eTime = new EditText(getActivity()); // create a new edittext
//        eDate.setClickable(false);
        eTime.setFocusable(false);
        eTime.setEnabled(false);
        ll.addView(eTime);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minutes = selectedMinute;
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour );
                calendar.set(Calendar.MINUTE, selectedMinute);

                String format = "HH:mm";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//                eDate.setText(dateFormat.format(calendar.getTime()));
//                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
//                String format = "hh:mm";
//                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//                eDate.setText(dateFormat.format(calendar.getTime()));


                //checks which edittext is not filled in order to set Text respectively
                if (sTime.getText().toString().matches("")){
//                    sTime.setText(String.format(Locale.getDefault(), "%2d:%2d", hour, minutes));
                    sTime.setText(dateFormat.format(calendar.getTime()));
                    tempTime = calendar.getTime();
                }else {
//                    eTime.setText(String.format(Locale.getDefault(), "%2d:%2d", hour, minutes));
                    if (tempTime.compareTo(calendar.getTime())<0){
                        eTime.setText(dateFormat.format(calendar.getTime()));
                    }else{
                        AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                        alertbld.setTitle("Wrong times!");
                        alertbld.setMessage("Check the range of the selected times.");
                        alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertbld.show();
                    }
                }
            }
        };

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sTime.getText().clear();
//                new TimePickerDialog(getActivity(), onTimeSetListener, hour, minutes, true);
                TimePickerDialog timePickerDialog =new TimePickerDialog(getActivity(), onTimeSetListener, hour, minutes, true);
                timePickerDialog.setTitle("Select Start Time");
                timePickerDialog.show();
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");

            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eTime.getText().clear();
//                new TimePickerDialog(getActivity(), onTimeSetListener, hour, minutes, true);
                TimePickerDialog timePickerDialog =new TimePickerDialog(getActivity(), onTimeSetListener, hour, minutes, true);
                timePickerDialog.setTitle("Select End Time");
                timePickerDialog.show();

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

                //code below is to get current date & time to compare to user input later
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date currentdate = new Date();
                System.out.println(formatter.format(currentdate));
                //check: if dates and times are wrong does not load the next layout components and shows message
                if (d1.compareTo(d2) > 0){//checks the two user inputs
//                    System.out.println("if1");
                    if (currentdate.compareTo(d1) > 0){//checks current with user input
//                        System.out.println("if11");
                        AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                        alertbld.setTitle("Wrong dates!");
                        alertbld.setMessage("Check the range of the selected dates.");
                        alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertbld.show();
                    }else{
                        System.out.println("here?");
                    }

                }
                else if(d1 == null || d2 == null || hour == -1 || minutes == -1 || sTime.getText().equals("") || eTime.getText().equals("")){
                    System.out.println("if2");
                    System.out.println("d1: "+ d1);
                    System.out.println("d2: "+d2);
                    System.out.println("hour: "+hour);
                    System.out.println("min: "+minutes);
                    System.out.println("sTime: "+sTime.getText());
                    System.out.println("eTime: "+eTime.getText());
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Select Dates");
                    alertbld.setMessage("You need to select dates for the next step.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
                }else if (d1.compareTo(d2) < 0) {//checks the two user input dates
                    System.out.println("if3");
                    System.out.println(currentdate);
                    System.out.println(d1);
                    if ( currentdate.compareTo(d1) < 0 || formatter.format(currentdate).matches(formatter.format(d1)) ){//checks current date with user input
                        System.out.println("if33");
                        dates.add(d1);
                        dates.add(d2);
                        times.add(hour);
                        times.add(minutes);
                        ll.removeAllViews();
                        chooseLocationBdg(view);
                    }else{
                        System.out.println("why?");
                        AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                        alertbld.setTitle("Wrong dates!");
                        alertbld.setMessage("Check the selected start date.");
                        alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertbld.show();
                    }
                }else{
                    System.out.println(d1);
                    System.out.println(d2);
                    System.out.println(sTime.getText());
                    System.out.println(eTime.getText());
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Empty Fields!");
                    alertbld.setMessage("Fill in the fields.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
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


    public View chooseLocationBdg(View view){
//        locs.clear();//clears the choices if called again for change
//        try {
//            ConnectionHelper connectionHelper = new ConnectionHelper();
//            connect = connectionHelper.connectionclass();
//            if (connect != null){
//                System.out.println("Trying connection ");
//                String query1 = "Select distinct LOCATION from greecepois";
//                Statement st = connect.createStatement();
//                ResultSet rs1 = st.executeQuery(query1);
//                while(rs1.next()) {
////                    records += rs.getString(1) + " " + rs.getString(2) + "\n";
//                    locations.add(rs1.getString(1));
//                    System.out.println("Locations: "+locations);
//
//                }
//                rs1.close();
//
//            }else{
//                System.out.println("Connection Error!");
//            }
//
//
//        }catch (Exception e){
//            System.out.println("Exception:" + e);
//        }
//
//        TextView textView = new TextView(getActivity());
//        textView.setText("Choose Location: \n");
//        ll.addView(textView);
//
//
//        checkBox = new CheckBox[locations.size()];
//        for (int i  = 0; i<locations.size(); i++){
////            checkBox = new CheckBox(getActivity());
//            checkBox[i] = new CheckBox(getActivity());
//            checkBox[i].setText(locations.get(i));
////            checkBox[i].setOnClickListener(getOnClickDoSomething(checkBox[i]));
//            ll.addView(checkBox[i]);
//        }

//        Button myButton = new Button(getActivity());
//        myButton.setText("NEXT");
//        ll.addView(myButton);
//
//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                ll.setVisibility(ll.GONE);
//                for (int i = 0; i< locations.size(); i++){
////                   checkBox = new CheckBox(getActivity());
//                    if (checkBox[i].isChecked()){
//                        locs.add(checkBox[i].getText().toString());
//                    }
//                }
//                System.out.println("Selected Locations: "+locs);
//
//                if (locs.isEmpty()){
//                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
//                    alertbld.setTitle("Select Location!");
//                    alertbld.setMessage("You need to select at least one location to get to the next step.");
//                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    alertbld.show();
//                }else {
//                    ll.removeAllViews();
//                    chooseCategory(view);
//                }
//
//
//            }
//        });

//        //Initialize Places
////        Places.initialize(getActivity().getApplicationContext(), apiKey);
//        String apiKey = "AIzaSyDraeLcgw8liZ0ZJjwapMo07w8rrrL3SB0";
//
//        // Start the autocomplete intent.
//        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .build(getActivity());
//        getActivity().startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

//        // Set the fields to specify which types of place data to
//        // return after the user has made a selection.
//        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
//
//        // Start the autocomplete intent.
//        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .setTypeFilter(TypeFilter.CITIES)
//                .build(getActivity());
//        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

        TextView textView = new TextView(getActivity());
        textView.setText("Click to choose City");
        ll.addView(textView);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .setTypeFilter(TypeFilter.CITIES)
                        .build(getActivity());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

//                textView.setText("Chosen City: "+place.getName());

//                if (!(textView.getText() == "Click to choose City")){
//                    textView.setText(place.getName());
//                }


            }
        });

        //textView for budget
        TextView bdgtextView = new TextView(getActivity());
        bdgtextView.setText("Choose your Budget:");
        ll.addView(bdgtextView);


        RadioGroup price_rg = new RadioGroup(getActivity()); //create the RadioGroup

        radiobtn = new RadioButton[5];
        for (int i  = 0; i<5; i++){
            System.out.println("inloop");
            radiobtn[i] = new RadioButton(getActivity());
            radiobtn[i].setId(i);
            if (i==0){
                System.out.println("in0");
                radiobtn[i].setText("$");
            }else{
                System.out.println("in>0");
                radiobtn[i].setText(radiobtn[i-1].getText() +"$");
            }
            price_rg.addView(radiobtn[i]);
            System.out.println("outifs");
        }
        ll.addView(price_rg);//add the whole RadioGroup to the layout


        Button myButton = new Button(getActivity());
        myButton.setText("NEXT");
        ll.addView(myButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (bdg ==-1){//checks if budget
//                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
//                    alertbld.setTitle("Choose budget!");
//                    alertbld.setMessage("You need to choose your budget.");
//                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    alertbld.show();
//                }
                bdg = price_rg.getCheckedRadioButtonId();
                System.out.println(bdg);

                if (textView.getText() == "Click to choose City"){
                    AlertDialog.Builder alertbld = new AlertDialog.Builder(getActivity());
                    alertbld.setTitle("Choose location");
                    alertbld.setMessage("You need to choose a location.");
                    alertbld.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertbld.show();
                }
                else if (textView.getText() == ""){
                    textView.setText("Chosen City: "+place.getName());
                }else{
                    ll.removeAllViews();
                    chooseCategory(view);
                }


            }
        });

        return  view;
    }

    public View showFinalChoices(View view){
        //shows selected dates
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        TextView tvDates = new TextView(getActivity());
        tvDates.setText("Dates between: "+ DateFormat.format("yyyy/MM/dd", dates.get(0)) +" and "+DateFormat.format("yyyy/MM/dd", dates.get(1)) + "\n");
        ll.addView(tvDates);

        //shows selected locations
//        TextView location = new TextView(getActivity());
//        tvDates.setText("Location: "+ );
//        ll.addView(tvDates);


        return view;
    }



    //function that ask for location permission
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
            }else{
                ActivityCompat.requestPermissions(getActivity(), permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



//    public View initialQuestions(View view){
//        questions.clear();
//
//        RadioButton rdbtn = new RadioButton(getActivity());
//        rdbtn.setId(View.generateViewId());
//        rdbtn.setText("Radio " + rdbtn.getId());
////        rdbtn.setOnClickListener(getActivity());
//        ll.addView(rdbtn);
//
//
//
//        return view;
//    }



}


