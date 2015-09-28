package com.harlalka.contactlist;


import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
   /* public static ContactDetailsFragment newInstance(String param1, String param2) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    } */

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);


        Cursor cr = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]
                        {ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + "=?", new String[]{mParam1}, null);

        View v = inflater.inflate(R.layout.details_fragment_layout, container, false);

            EditText editText = (EditText)v.findViewById(R.id.editText);


            if((cr.moveToFirst())!=false){

                    int index = cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
                    String phoneNumber = cr.getString(index);


                    editText.setText(phoneNumber);


            }
        else{
                editText.setText("No contact number found");
            }
        cr.close();

        return v;
    }


}
