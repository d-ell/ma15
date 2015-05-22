package ma15.brickcollector.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ma15.brickcollector.R;
import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.Settings;
import ma15.brickcollector.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link android.view.View.OnClickListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_TITLE = "title";

    final static String TAG = SettingsFragment.class.getName();

    private String mTitle;
    private EditText mPageSize;
    private CheckBox mKeepLogin;
    private String strPageSize = "";
    private String strKeepLogin = "";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    public static SettingsFragment newInstance(String title) {
        SettingsFragment fragment = new SettingsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        strPageSize = Settings.getPageSize();
        strKeepLogin = Settings.getKeepLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mPageSize = (EditText) view.findViewById(R.id.txtPageSize);
        mPageSize.setText(strPageSize, TextView.BufferType.EDITABLE);

        mKeepLogin = (CheckBox) view.findViewById(R.id.chbxKeepLogin);
        mKeepLogin.setChecked(Boolean.parseBoolean(strKeepLogin));

        final Button button = (Button) view.findViewById(R.id.btnSave);
        button.setOnClickListener(this);

        /* Logic for disabling button when no input is given */
        if (mPageSize.getText().toString().trim().length() == 0) {
            button.setEnabled(false);
        }
        mPageSize.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (mPageSize.getText().toString().trim().length() > 0)
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
            mTitle = getArguments().getString(ARG_TITLE);
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
            case R.id.btnSave:

                int iPageSize = Integer.parseInt(mPageSize.getText().toString());

                if(iPageSize <= 0 || iPageSize > 100) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.errorSettingsPageSize), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    strPageSize = mPageSize.getText().toString();
                    Settings.setPageSize(strPageSize);

                    strKeepLogin = String.valueOf(mKeepLogin.isChecked());
                    Settings.setKeepLogin(strKeepLogin);

                    Settings.saveSettings(getActivity());

                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.successSettings), Toast.LENGTH_SHORT)
                            .show();
                }
        }
    }



}
