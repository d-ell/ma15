package ma15.brickcollector.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ma15.brickcollector.connection.PostRequest;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.listener.EndlessScrollListener;
import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;
import ma15.brickcollector.Utils.UserManager;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.activity.DetailSetsActivity;
import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.connection.Callback;
import ma15.brickcollector.connection.HTTPDispatcher;
import ma15.brickcollector.connection.LoadSets;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link android.view.View.OnClickListener} interface
 * to handle interaction events.
 * Use the {@link SetListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetListFragment extends Fragment implements Callback, LoadSets {

    private static final String ARG_TITLE = "title";
    final static String TAG = SetListFragment.class.getName();
    private static final String ARG_OWN = "own";
    private static final String ARG_WANT = "want";

    private String mTitle;
    private boolean mOwn;
    private boolean mWant;

    List<BrickSet> sets = null;
    ListView list;
    OnlineFetchedSetsAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter.
     * @return A new instance of fragment LoginFragment.
     */
    public static SetListFragment newInstance(String title, boolean own, boolean want) {
        SetListFragment fragment = new SetListFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putBoolean(ARG_OWN, own);
        args.putBoolean(ARG_WANT, want);
        fragment.setArguments(args);

        return fragment;
    }

    public SetListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list_sets, container, false);

        sets = new ArrayList<>();

        list = (ListView) view.findViewById(R.id.listview);

        // Getting adapter by passing xml data ArrayList
        adapter = new OnlineFetchedSetsAdapter(getActivity(), sets);
        list.setAdapter(adapter);

        list.setOnScrollListener(new EndlessScrollListener(this,0));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getActivity(), "Clicked.", Toast.LENGTH_SHORT).show();



                BrickSet brickSet = null;
                List<BrickSet> tmp = sets;

                try {
                    brickSet = tmp.get(position);
                } catch (ClassCastException e) {
                    System.out.println("Could not cast!");
                    e.printStackTrace();
                }

                Intent intent = new Intent(getActivity().getApplicationContext(),
                        DetailSetsActivity.class);
                // sending data to new activity
                intent.putExtra("set", brickSet);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            if (getArguments() != null) {
                mTitle = getArguments().getString(ARG_TITLE);
                mOwn = getArguments().getBoolean(ARG_OWN);
                mWant = getArguments().getBoolean(ARG_WANT);
            }
            ((MainActivity) activity).onSectionAttached(mTitle);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void handleResponse(String requestMethod, String xml) {
        if (xml == null || xml.isEmpty()) {
            Toast.makeText(getActivity(), "XML is empty. Should never happen", Toast.LENGTH_SHORT).show();
        }

        ArrayList<BrickSet> results = XmlParser.getSets(xml);
        if (results == null || results.isEmpty()) {
            Toast.makeText(getActivity(), "No more data to load.", Toast.LENGTH_SHORT).show();
        } else {
            for(BrickSet set : results) {
                sets.add(set);
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadSets(int pageNumber) {
        if(!HTTPDispatcher.isConnected(getActivity())) {
            Toast.makeText(getActivity(),
                    "Not connected.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        ProgressBar progress = (ProgressBar) getActivity().findViewById(R.id.progressBar);

        // start asynchronous search => doGetRequest makes callback
        // to handleResponse()
        HTTPDispatcher dispatcher = new HTTPDispatcher();
        new PostRequest(getActivity(), this, Constants.BROWSE, progress).execute("", "", "",
                UserManager.getInstance().getUserHash(),
                mOwn ? "1" : "",
                mWant ? "1" : "",
                Integer.toString(pageNumber));
    }
}
