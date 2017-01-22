package com.evnica.theaterlbs.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evnica.theaterlbs.GlobalIds;
import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.TheaterArrayAdapter;
import com.evnica.theaterlbs.activity.DBDetailActivity;
import com.evnica.theaterlbs.connect.LocalDBHandler;
import com.evnica.theaterlbs.model.Theater;

import java.util.ArrayList;

/**
 * Created by: Evnica
 * Date: 22.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class DbListFragment extends Fragment
{

    private ListView mListView = null;
/*    private boolean mNeedsRefreshment = false;

    public void setNeedsRefreshment(boolean needsRefreshment) {
        this.mNeedsRefreshment = needsRefreshment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_db_list, container, false);
        GlobalIds.dbListId = this.getId();
        mListView = (ListView)fragmentView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theater theater = (Theater)mListView.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), DBDetailActivity.class);
                intent.putExtra("theaterAsArray", theater.toStringArray());
                startActivity(intent);
            }
        });

        new DbTask().execute();

        return fragmentView;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if (mNeedsRefreshment)
        {
            mNeedsRefreshment = false;

        }
    }*/

    class DbTask extends AsyncTask<String, Void, ArrayList<Theater>>
    {

        @Override
        protected ArrayList<Theater> doInBackground(String... params) {

            LocalDBHandler dbHelper = new LocalDBHandler(getActivity());
            return new ArrayList<>(dbHelper.getTheaters());

        }

        @Override
        protected void onPostExecute(ArrayList<Theater> theaters) {
            TheaterArrayAdapter listViewAdapter = new TheaterArrayAdapter(getActivity(), theaters);
            mListView.setAdapter(listViewAdapter);
        }
    }

}
