package com.harlalka.contactlist;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;



import com.harlalka.contactlist.dummy.DummyContent;

import org.w3c.dom.Text;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link //OnFragmentInteractionListener}
 * interface.
 */
public class ContactListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>/*, View.OnClickListener*/ {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
   // private String mParam1;
   // private String mParam2;

   // private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
   // private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private SimpleCursorAdapter mAdapter;
    private OnItemClick mCallback = sDummyCallback;

    private static OnItemClick sDummyCallback = new OnItemClick() {

        public void onItemSelected(String D){}

    };

    // TODO: Rename and change types of parameters
   /* public static ContactListFragment newInstance(String param1, String param2) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactListFragment() {
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
   // @Override
    /*public void onClick(View v) {

        mCallback.onItemSelected(v.findViewById(android.R.id.text1).toString());



    }*/


    public interface OnItemClick{
         void onItemSelected(String display_name);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    */}

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contact_list, container, false);

        // Set the adapter
       // mListView = (AbsListView) view.findViewById(android.R.id.list);
        //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
      //  mListView.setOnItemClickListener(this);

        return view;
    }*/

    @Override
    public void onActivityCreated(Bundle bundle){

        super.onActivityCreated(bundle);
        //DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");



        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, new String[]
        {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY}, new int [] {android.R.id.text1}, 0);

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (columnIndex == 1) {
                    Long millis = cursor.getLong(columnIndex);
                    if (millis == 0) {
                        ((TextView) view).setText("No calls made");
                        return true;
                    }
                    //millis = millis * 1000;
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                    Date date = new Date(millis);
                    String fdt = sdf.format(date);
                    ((TextView) view).setText(fdt);
                    return true;
                }
                return false;
            }
        });

        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);




    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       try {
            mCallback = (OnItemClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        mCallback = (OnItemClick) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = sDummyCallback;
       // mListener = null;
    }



    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
  /*  public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        Uri baseUri = ContactsContract.Contacts.CONTENT_URI;
        String projection[] = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,ContactsContract.Contacts.LAST_TIME_CONTACTED, ContactsContract.Contacts._ID};
        String select = "((" +ContactsContract.Contacts.DISPLAY_NAME_PRIMARY+" NOTNULL) AND( " + ContactsContract.
                Contacts.IN_VISIBLE_GROUP+"=1))";



        return new CursorLoader(getActivity(), baseUri, projection, select,null,ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        +" COLLATE LOCALIZED ASC");



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mAdapter.swapCursor(null);
  }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        String name =   ((TextView)v.findViewById(android.R.id.text1)).getText().toString();
        mCallback.onItemSelected(name);
    }
}