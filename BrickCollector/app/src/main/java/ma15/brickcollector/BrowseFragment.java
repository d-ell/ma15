package ma15.brickcollector;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import ma15.brickcollector.connection.Callback;
import ma15.brickcollector.connection.ConnectionHandler;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
//            mListener = (OnFragmentInteractionListener) activity;
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
//        mListener = null;
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
                dispatcher.new doPostRequest(getActivity(), this, progress).execute(mQuery.getText().toString(),
                        mTheme.getText().toString(),
                        mYear.getText().toString(),
                        null);

                /*
                ConnectionHandler.getSets(mQuery.getText().toString(),
                        mTheme.getText().toString(),
                        mYear.getText().toString(),
                        null);
                */

                break;
        }
    }

    public void handleResponse(String xml) {

        Toast.makeText(getActivity(), xml, Toast.LENGTH_SHORT).show();

        /*

        if (html == null) {
            Toast.makeText(this, "Search cancelled", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = null;
        Movie movie = null;

        try {
            // EAN => we can omit the listitems activity
            if (parsing_method == MOVIE_DETAIL_PARSING) {
                intent = new Intent().setClass(getBaseContext(),
                        DetailEditMovieActivity.class);

                MovieDetailHTMLParser html_parser = new MovieDetailHTMLParser(
                        html);
                movie = html_parser.getMovie();

                intent.putExtra("movie", movie);
            }
            // TITLE SEARCH => show list of movies
            else if (parsing_method == MOVIE_LIST_PARSING) {
                intent = new Intent().setClass(getBaseContext(),
                        ListOnlineFetchedMoviesActivity.class);

                MovieListHTMLParser html_parser = new MovieListHTMLParser(html);

                results = html_parser.getMovies();

                ArrayList<Movie> addyExtras = new ArrayList<Movie>();

                for (int i = 0; i < results.size(); i++) {
                    if (!results.get(i).getBarcode().equals(""))
                        addyExtras.add(results.get(i));
                }

                if (results.size() == 0)
                    throw new NotParseableException(
                            "blabla sollte in der ListHTML geworfen werden");

                intent.putParcelableArrayListExtra("mylist", addyExtras);
            }
            startActivity(intent);
        } catch (NotParseableException e) {
            Toast.makeText(this, "Barcode/Title could not be recognized!",
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
