package ma15.brickcollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.adapter.SetXmlParser;
import ma15.brickcollector.connection.Callback;
import ma15.brickcollector.connection.HTTPDispatcher;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrowseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BrowseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseFragment extends Fragment implements View.OnClickListener, Callback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    final static String TAG = BrowseFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mTitle;
    private EditText mQuery;
    private EditText mTheme;
    private EditText mYear;

    // private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment BrowseFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static BrowseFragment newInstance(String title) {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public BrowseFragment() {
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
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        mQuery = (EditText) view.findViewById(R.id.txtQuery);
        mTheme = (EditText) view.findViewById(R.id.txtTheme);
        mYear = (EditText) view.findViewById(R.id.txtYear);

        final Button button = (Button) view.findViewById(R.id.btnGo);
        button.setOnClickListener(this);

        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        /* Logic for disabling button when no input is given */
        if (mQuery.getText().toString().trim().length() == 0 && mTheme.getText().toString().trim().length() == 0
                && mYear.getText().toString().trim().length() == 0) {
            button.setEnabled(false);
        }
        mQuery.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mQuery.getText().toString().trim().length() > 0 || mTheme.getText().toString().trim().length() > 0 || mYear.getText().toString().trim().length() > 0)
                    button.setEnabled(true);
                else
                    button.setEnabled(false);
            }

        });
        mTheme.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mQuery.getText().toString().trim().length() > 0 || mTheme.getText().toString().trim().length() > 0 || mYear.getText().toString().trim().length() > 0)
                    button.setEnabled(true);
                else
                    button.setEnabled(false);
            }

        });
        mYear.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mQuery.getText().toString().trim().length() > 0 || mTheme.getText().toString().trim().length() > 0 || mYear.getText().toString().trim().length() > 0)
                    button.setEnabled(true);
                else
                    button.setEnabled(false);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGo:
                Toast.makeText(getActivity(),
                        "Button Clicked.", Toast.LENGTH_SHORT)
                        .show();

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
                dispatcher.new PostRequest(getActivity(), this, Constants.BROWSE, progress).execute(mQuery.getText().toString(),
                        mTheme.getText().toString(),
                        mYear.getText().toString(),
                        UserManager.getInstance().getUserHash());
                break;
        }
    }

    public void handleResponse(String requestMethod, String xml) {

        if (xml.isEmpty()) {
            Toast.makeText(getActivity(), "XML is empty. Should never happen", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getActivity(), xml, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent().setClass(getActivity().getBaseContext(), ListOnlineFetchedSetsActivity.class);

        // TODO: we should probably do the parsing asynchron

        ArrayList<BrickSet> results = SetXmlParser.getSets(xml);
        if (results == null || results.isEmpty()) {
            Toast.makeText(getActivity(), "Could not find any data.", Toast.LENGTH_SHORT).show();
        }


        // INFO: we want to pass the brickset to the new activity therefore the entity BrickSet must
        // implement the Parceable interface to pass via params
        ArrayList<BrickSet> addyExtras = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            addyExtras.add(results.get(i));
        }

        if (results.size() == 0) {
            Toast.makeText(getActivity(), "Nothing to display.", Toast.LENGTH_SHORT).show();
        }

        intent.putParcelableArrayListExtra("mylist", addyExtras);
        startActivity(intent);
    }

}
