//package com.charikleia.petrou.personalisedtravelguide;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatDialogFragment;
//
//public class MessageDialog extends DialogFragment {
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                toast.makeText(this,"enter a text here",Toast.LENTH_SHORT).show();
//            }
//        })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        finish();
//                    });
//                    // Create the AlertDialog object and return it
//         return builder.create();
//                }
//    }
//
////        extends AppCompatDialogFragment {
////
////
////    public void messageDialog(String title, String message){
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////        builder.setTitle("Information")
////                .setMessage("This is a Dialog")
////                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////
////                    }
////                });
////
////        builder.create();
////    }
//
//
//}
