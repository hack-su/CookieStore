package com.hacksu.CookieStore.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.hacksu.CommunicationLibrary.UpdateOrderApiCall;
import com.hacksu.CookieStore.FragmentHelper;
import com.hacksu.CookieStore.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class DetailsFragment extends Fragment
{
    private View rootView;
    private Bundle args;
    TextView nameTextView;
    TextView descriptionTextView;
    TextView priceTextView;
    EditText quantityText;
    Button addButton;
    private int textBoxValue;
    private boolean textIsEmpty;
    private int iD;
    private JSONObject jsonAddToCart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = setRootView(inflater, container);
        args = setDataFromHomeScreen();
        linkTextViewsToVariables();
        setTextViewDisplay();
        linkTextBoxViewToVariables();
        linkButtonToVariables();

        return rootView;
    }

    private View setRootView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(R.layout.details_layout, container, false);
    }
    private Bundle setDataFromHomeScreen()
    {
        return getArguments();
    }
    private void linkTextViewsToVariables()
    {
        nameTextView= (TextView) rootView.findViewById(R.id.name);
        descriptionTextView= (TextView) rootView.findViewById(R.id.description);
        priceTextView= (TextView) rootView.findViewById(R.id.price);
        iD = Integer.parseInt(args.getString("id"));
    }
    private void setTextViewDisplay()
    {
        nameTextView.setText(args.getString("name"));
        descriptionTextView.setText(args.getString("description"));
        priceTextView.setText(args.getString("price"));
    }
    private void linkTextBoxViewToVariables()
    {
        quantityText= (EditText) rootView.findViewById(R.id.quantityNumber);
    }
    private void linkButtonToVariables()
    {
        addButton = (Button) rootView.findViewById(R.id.addButton);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        addButton.setOnClickListener(addToCart);
    }
    private boolean isTextBoxEmpty()
    {
        return quantityText.getText().toString().isEmpty();
    }
    private View.OnClickListener addToCart = new OnClickListener() {
        @Override
        public void onClick(View v) {
            textIsEmpty = isTextBoxEmpty();
            if(textIsEmpty)
                showWarningMessage();
            else
            {
                try {
                    sendDataToApi();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                returnToHomeScreen();
            }
        }
        private void showWarningMessage()
        {
            AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
            ad.setCancelable(false);
            ad.setTitle("Missing Quantity");
            ad.setMessage("Please pick a quantity number");
            ad.setButton(getActivity().getString(R.string.ok_text), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ad.show();
        }
        private void returnToHomeScreen()
        {
            HomeFragment fragment = new HomeFragment();
            FragmentHelper.replaceFragment(R.id.homeRoot,"home",fragment, getFragmentManager());
        }

       private void sendDataToApi() throws MalformedURLException {
           URL url = new URL("http://cookieapidev1.cloudapp.net/ksuapi/api/orders/UpdateOrder");
            textBoxValue = Integer.parseInt(quantityText.getText().toString());
            jsonAddToCart = writeJSON(textBoxValue);
           UpdateOrderApiCall client = new UpdateOrderApiCall(url.toString(), jsonAddToCart);
           client.execute();
        }
        public JSONObject writeJSON(int textBoxValue) {
            JSONObject object = new JSONObject();
            try {
                object.accumulate("ProductId", iD);
                object.accumulate("Quantity", textBoxValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }
    };
}
