package com.sanketguru.myworth;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by sanket.sphere on 17-12-2018.
 */
public class HTMLPrintActivity extends Activity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.html);


        myWebView = new WebView(this);


        String htmlDocument =
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Customer Detail</title>\n" +
                        "</head>\n" +
                        "<style>\n" +
                        "\n" +
                        "body{\n" +
                        "    margin:30px;\n" +
                        "    padding:0;\n" +
                        "    color: #3E3E3E;\n" +
                        "}\n" +
                        "\n" +
                        "table.data {\n" +
                        "   width:100%;\n" +
                        "  border-collapse: collapse;\n" +
                        "}\n" +
                        "\n" +
                        "table.data  td {\n" +
                        "  border: 1px solid black;\n" +
                        "  padding:10px;\n" +
                        "  width:25%;\n" +
                        "}\n" +
                        ".pagebreak { page-break-before: always; } /* page-break-after works, as well */\n" +
                        "\n" +
                        "</style>\n" +
                        "<body>\n" +
                        "<table width=\"100%\">\n" +
                        "    <tr>\n" +
                        "        <td width=\"25%\">\n" +
                        "            <img width=\"100%\"\n" +
                        "                 src='data:image/png;base64,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'/>\n" +
                        "        </td>\n" +
                        "        <td style=\"text-align:center\" width=\"75%\">\n" +
                        "           <h1>\n" +
                        "                INSTAMF PROFILE\n" +
                        "          </h1>\n" +
                        "            (Paperless MF Account)\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "<h2>Client Details</h2>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td><b>Login ID</b></td>\n" +
                        "        <td>${loginid}</td>\n" +
                        "        <td><b>Moded :</b></td>\n" +
                        "        <td>${RTA}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Customer Name:</b></td>\n" +
                        "        <td>${custName}</td>\n" +
                        "        <td><b>Form No :</b></td>\n" +
                        "        <td>${formNo}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Father/Husband Name :</b></td>\n" +
                        "        <td>${fatherName}</td>\n" +
                        "        <td><b>Mother Name:</b></td>\n" +
                        "        <td>${motherName}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Date of Birth:</b></td>\n" +
                        "        <td> ${dob}</td>\n" +
                        "        <td><b>Gender:</b></td>\n" +
                        "        <td>${gender}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b> Marital Status:</b></td>\n" +
                        "        <td> ${maritalStatus}</td>\n" +
                        "        <td><b>Nationality:</b></td>\n" +
                        "        <td>${nationality}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Type Of Account:</b></td>\n" +
                        "        <td> ${accountType}</td>\n" +
                        "        <td><b>PAN Number:</b></td>\n" +
                        "        <td>${panNo}</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "<h2>Permanent Address</h2>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td><b>Address1:</b></td>\n" +
                        "        <td>${address1}</td>\n" +
                        "        <td><b>Address2:</b></td>\n" +
                        "        <td>${address2}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Address3:</b></td>\n" +
                        "        <td>${address3}</td>\n" +
                        "        <td><b>City:</b></td>\n" +
                        "        <td>${city}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Pincode:</b></td>\n" +
                        "        <td>${pinCode}</td>\n" +
                        "        <td><b> State:</b></td>\n" +
                        "        <td>${state}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Country:</b></td>\n" +
                        "        <td>${country}</td>\n" +
                        "        <td></td>\n" +
                        "        <td></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Email ID:</b></td>\n" +
                        "        <td>${emailId}</td>\n" +
                        "        <td><b>Mobile:</b></td>\n" +
                        "        <td>${mobilNo}</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "<h2>Correspondence Address</h2>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td><b>Address1:</b></td>\n" +
                        "        <td>${correspondenceAddress1}</td>\n" +
                        "        <td><b>Address2:</b></td>\n" +
                        "        <td>${correspondenceAddress2}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Address3:</b></td>\n" +
                        "        <td>${correspondenceAddress3}</td>\n" +
                        "        <td><b> City:</b></td>\n" +
                        "        <td>${city}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Pincode:</b></td>\n" +
                        "        <td>${pinCode}</td>\n" +
                        "        <td><b> State:</b></td>\n" +
                        "        <td>${state}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Country:</b></td>\n" +
                        "        <td>${country}</td>\n" +
                        "        <td></td>\n" +
                        "        <td></td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "<h2>Bank Details</h2>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td><b>Name of Bank:</b></td>\n" +
                        "        <td>${bankName}</td>\n" +
                        "        <td><b>Branch:</b></td>\n" +
                        "        <td>${branch}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>A/c number :</b></td>\n" +
                        "        <td>${accountNo}</td>\n" +
                        "        <td><b>A/c type:</b></td>\n" +
                        "        <td>${accountType}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>IFSC Code:</b></td>\n" +
                        "        <td>${ifsc}</td>\n" +
                        "        <td></td>\n" +
                        "        <td></td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "<h2>Other Details</h2>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td><b>Politically exposed person / Related to Politically exposed person etc.?:</b></td>\n" +
                        "        <td>${politiclStatus}</td>\n" +
                        "        <td></td>\n" +
                        "        <td></td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Income Tax Slab/Networth:</b></td>\n" +
                        "        <td>${inComeSlab}</td>\n" +
                        "        <td><b>Occupation Details:</b></td>\n" +
                        "        <td>${occupation}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Country of Birth :</b></td>\n" +
                        "        <td>${countryOfBirth}</td>\n" +
                        "        <td><b>Place of Birth:</b></td>\n" +
                        "        <td>${placeOfBirth}</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "<h2>Nominee Details</h2>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td><b>Nominee Name :</b></td>\n" +
                        "        <td>${nomineeName}</td>\n" +
                        "        <td><b>Nominee Date of Birth:</b></td>\n" +
                        "        <td>${nomineeDob}</td>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><b>Relationship:</b></td>\n" +
                        "        <td>${relationship}</td>\n" +
                        "        <td><b>Guardian Name (If Nominee is Minor):</b></td>\n" +
                        "        <td>${guardian}</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "<br/>\n" +
                        "<table class=\"data\">\n" +
                        "    <tr>\n" +
                        "        <td colspan=\"2\">\n" +
                        "            <b>Declaration:</b> I/We confirm that details provided by me/us are true and correct.\n" +
                        "            The ARN holder has disclosed to me/us all the commission (In the form of trail\n" +
                        "            commission or any other mode), payable to him for the different competing Schemes of\n" +
                        "            various Mutual Fund From amongst which the scheme is being recommended to me/us.\n" +
                        "        </td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "        <td><b>Date:</b> ${formDate}</td>\n" +
                        "        <td><b>Place:</b> ${formPlace}</td>\n" +
                        "    </tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";

        myWebView.loadDataWithBaseURL(null, htmlDocument,
                "text/HTML", "UTF-8", null);


    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            printAdapter = webView.createPrintDocumentAdapter("Cool");
        } else {
            printAdapter = webView.createPrintDocumentAdapter();
        }
        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    public void mPrint(View v) {
        createWebPrintJob(myWebView);
    }
}