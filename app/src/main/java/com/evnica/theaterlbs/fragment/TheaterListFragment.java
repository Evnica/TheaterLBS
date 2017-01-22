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

import com.evnica.theaterlbs.R;
import com.evnica.theaterlbs.TheaterArrayAdapter;
import com.evnica.theaterlbs.TheaterDetailActivity;
import com.evnica.theaterlbs.connect.RestConnector;
import com.evnica.theaterlbs.model.Theater;

import java.util.ArrayList;

/**
 * Created by: Evnica
 * Date: 20.01.2017
 * Version: 0.1
 * Project: TheaterLBS
 */

public class TheaterListFragment extends Fragment {

    private ListView mMainListView = null;
    private static ArrayList<Theater> theaters;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View listFragmentView = inflater.inflate(R.layout.fragment_theaterlist, container, false);

        mMainListView = (ListView) listFragmentView.findViewById(R.id.mainListView);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO: open DetailActivity on item click
                Theater theater = theaters.get(position);
                        //(Theater)mMainListView.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(), TheaterDetailActivity.class);
                intent.putExtra("theaterAsArray", theater.toStringArray());
                startActivity(intent);
            }
        });

        new DownloadTask().execute();

        return listFragmentView;
    }

    private class DownloadTask extends AsyncTask<String, Void, ArrayList<Theater>>
    {

        @Override
        protected ArrayList<Theater> doInBackground(String... params) {

            theaters = RestConnector.getsInstance().getData();
            return theaters;
        }

        @Override
        protected void onPostExecute(ArrayList<Theater> theaters) {
            mMainListView.setAdapter(new TheaterArrayAdapter(getActivity(), theaters));
        }
    }

}
