package ma15.brickcollector.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import ma15.brickcollector.activity.MainActivity;
import ma15.brickcollector.R;
import ma15.brickcollector.Utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link android.view.View.OnClickListener} interface
 * to handle interaction events.
 * Use the {@link ma15.brickcollector.fragment.LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    final static String TAG = RegisterFragment.class.getName();

    private String mTitle;

    private ProgressDialog progress;
    private WebView webView;
    private boolean is_loaded = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter.
     * @return A new instance of fragment LoginFragment.
     */
    public static RegisterFragment newInstance(String title) {
        RegisterFragment fragment = new RegisterFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // acc. to so, enables actionbar items
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Log.d("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.action_stop:
                Toast.makeText(getActivity(), "Action in Register.", Toast.LENGTH_SHORT).show();
                webView.stopLoading();
                //webView.ca
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.register, menu);

        //menu.findItem(R.id.action_stop).setEnabled(!is_loaded);
        menu.findItem(R.id.action_stop).setVisible(!is_loaded);
       // menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        webView = (WebView) view.findViewById(R.id.webview);
        webView.clearCache(true);

        // webView.setInitialScale(0);
        webView.getSettings().setJavaScriptEnabled(true);
        // webView.getSettings().setLoadWithOverviewMode(true);
        // webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        //webView.setWebViewClient(new WebViewClient());

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), "Oh no! Error " + description, Toast.LENGTH_SHORT).show();
            }
            public void onPageFinished(WebView view, String url) {
                is_loaded = true;
                getActivity().invalidateOptionsMenu();
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(Constants.REGISTER_URL);

        /*
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 0) {
                    showProgressDialog("Please Wait...");
                }
                if (newProgress >= 100) {
                    hideProgressDialog();
                }
            }
        });*/

        return view;
    }

    public void showProgressDialog(final String msg) {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (progress == null || !progress.isShowing())
                    progress = ProgressDialog.show(getActivity(), "Loading", msg);
            }
        });
    }
    public void hideProgressDialog() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (progress.isShowing())
                        progress.dismiss();
                } catch (Throwable e) {

                }
            }
        });
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

}
