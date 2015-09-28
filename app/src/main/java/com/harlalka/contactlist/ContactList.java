package com.harlalka.contactlist;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;


public class ContactList extends Activity implements ContactListFragment.OnItemClick{

    boolean mDualPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);
        if(findViewById(R.id.details_in_landscape)!=null) {
            mDualPane = true;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String display_name) {
        Bundle args = new Bundle();
        args.putString(ContactDetailsFragment.ARG_PARAM1, display_name);

        if(mDualPane == true){


            ContactDetailsFragment detailsFragment = new ContactDetailsFragment();
            detailsFragment.setArguments(args);

            getFragmentManager().beginTransaction().replace(R.id.details_in_landscape, detailsFragment)
                    .commit();

            }
        else{
                //start new activity
            Intent intent =  new Intent(this, DetailsActivity.class);
            intent.putExtra(ContactDetailsFragment.ARG_PARAM1, display_name);
            startActivity(intent);



        }

    }
}
