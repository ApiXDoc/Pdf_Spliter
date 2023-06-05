package com.mradking.pdfspliter.other;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.activity.MainActivity;
import com.mradking.pdfspliter.activity.Preview_pdf_view_act;
import com.mradking.pdfspliter.activity.SaveFile_list;
import com.mradking.pdfspliter.database.DatabaseHelper;
import com.mradking.pdfspliter.modal.Modal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utility extends Activity {
    private static Context context;
    private static final int UPI_PAYMENT = 0;
    private UpiPaymentCallback upiPaymentCallback;

    public  void splitPdfFile(Context context, Uri inputUri, String outputFile, int startPage, int endPage, ContentResolver contentResolver,String file_name) throws Exception {
        InputStream inputStream = contentResolver.openInputStream(inputUri);
        PdfReader reader = new PdfReader(inputStream);
        int n = reader.getNumberOfPages();
        if (endPage > n) {
            endPage = n;
        }
        // Creating document and output stream
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFile));
        document.open();
        // Iterating through the selected pages
        for (int i = startPage; i <= endPage; i++) {
            copy.addPage(copy.getImportedPage(reader, i));
        }
        document.close();
        reader.close();

        Uri pdfUri_1 = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(outputFile));

        Intent intent=new Intent(context, Preview_pdf_view_act.class);
        intent.putExtra("key",file_name);
        intent.putExtra("path",pdfUri_1);
        intent.putExtra("output_path",outputFile);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);





    }


    public static void save_file_in_sql(String file_name,String outputFile,Context context){

        long timestamp = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String timestampString = dateFormat.format(new Date(timestamp));

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        databaseHelper.insertData(new Modal(file_name, outputFile,timestampString));


        Intent intent=new Intent(context, SaveFile_list.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);



    }

    public String getRandomString(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * allowedChars.length());
            sb.append(allowedChars.charAt(randomIndex));
        }

        return sb.toString();
    }


    public void requestStoragePermission(Context context) {
        Dexter.withActivity((Activity) context)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE

                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {




                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(context);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    public void showSettingsDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(context);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    public void openSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
        
    }

    public static  void Rating_us_dailoag(Context context){

        final RatingDialog ratingDialog = new RatingDialog.Builder(context)
                .threshold(5)

                .title("Please Gives Five Star Rating And Appreciate Our Hardwork and Thanks to Rate Us")
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                        Uri data = Uri.parse("mailto:?subject=" + "My Valuable Feedback"+ "&body=" + feedback + "&to=" + "Powerx4l5@gmail.com");
                        mailIntent.setData(data);
                       context.startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                    }
                }).build();

        ratingDialog.show();


    }

    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            String appLink = "https://play.google.com/store/apps/details?id=" + appPackageName;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, " 10th class Science Notes ");
            String shareMessage="PDF Spliter is one of best App" +
                    "\uD83D\uDC49\uD83C\uDFFBApp Link:-"+appLink +
                    "\n";
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            } catch(Exception e) {

            String appLink = "https://play.google.com/store/apps/details?id=" + appPackageName;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, " 10th class Science Notes ");
            String shareMessage="PDF Spliter is one of best App" +
                    "\uD83D\uDC49\uD83C\uDFFBApp Link:-"+appLink +
                    "\n";
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        }

    }

    public static void rateApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void MoreApp(Context context) {
        final String appName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }


    public static void payUsingUpi(Activity activity, String upiId, String name, String note, String amount) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("pn", "PhonePe")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        upiPayIntent.setPackage("com.phonepe.app");

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        if (chooser.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(activity, "No UPI app found, please install PhonePe to continue", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPI_PAYMENT) {
            if (resultCode == RESULT_OK || resultCode == 11) {
                if (data != null) {
                    String transactionId = data.getStringExtra("txnId");
                    Toast.makeText(context, "this sussess", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
