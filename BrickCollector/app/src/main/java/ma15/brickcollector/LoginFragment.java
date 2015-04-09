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

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.Util;
import ma15.brickcollector.adapter.SetXmlParser;
import ma15.brickcollector.connection.Callback;
import ma15.brickcollector.connection.HTTPDispatcher;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, Callback {

    private static final String ARG_TITLE = "title";
    final static String TAG = LoginFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mTitle;
    private EditText mUser;
    private EditText mPassword;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String title) {
        LoginFragment fragment = new LoginFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUser = (EditText) view.findViewById(R.id.txtUser);
        mPassword = (EditText) view.findViewById(R.id.txtPassword);

        final Button button = (Button) view.findViewById(R.id.btnLogin);
        button.setOnClickListener(this);

        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        /* Logic for disabling button when no input is given */
        if (mUser.getText().toString().trim().length() == 0 && mPassword.getText().toString().trim().length() == 0) {
            button.setEnabled(false);
        }
        mUser.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mUser.getText().toString().trim().length() > 0 && mPassword.getText().toString().trim().length() > 0)
                    button.setEnabled(true);
                else
                    button.setEnabled(false);
            }

        });
        mPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mUser.getText().toString().trim().length() > 0 && mPassword.getText().toString().trim().length() > 0)
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
    public void handleResponse(String requestMethod, String xml) {
        Log.d(TAG, "Login result: " + xml);

        String user_hash = SetXmlParser.getUserHash(xml);
        Log.d(TAG, "User hash: " + user_hash);

        if(user_hash == null || user_hash.indexOf(Constants.ERROR) != -1 ||
                user_hash.indexOf(Constants.INVALID_KEY) != -1) {
            Toast.makeText(getActivity(), "Login was not successful",Toast.LENGTH_SHORT).show();
            UserManager.getInstance().setUserHash(null);
            return;
        }

        UserManager.getInstance().setUserHash(user_hash);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

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
                dispatcher.new PostRequest(getActivity(), this, Constants.LOGIN, progress).execute(mUser.getText().toString(),
                        mPassword.getText().toString(),
                        null);
                break;
        }
    }
}
