package ma15.brickcollector.uiTest_espresso;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ma15.brickcollector.adapter.OnlineFetchedSetsAdapter;
import ma15.brickcollector.data.BrickSet;

import static java.util.Collections.swap;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by dan on 06/05/15.
 */
public class TestHelper {
    public static <T> List<List<T>> combination(List<T> values, int size) {

        if (0 == size) {
            return Collections.singletonList(Collections.<T> emptyList());
        }

        if (values.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<T>> combination = new LinkedList<List<T>>();

        T actual = values.iterator().next();

        List<T> subSet = new LinkedList<T>(values);
        subSet.remove(actual);

        List<List<T>> subSetCombination = combination(subSet, size - 1);

        for (List<T> set : subSetCombination) {
            List<T> newSet = new LinkedList<T>(set);
            newSet.add(0, actual);
            combination.add(newSet);
        }

        combination.addAll(combination(subSet, size));

        return combination;
    }

    public static <T> List<List<T>> getPowerSetOfArray(List<T> list) {
        List<List<T>> powerSet = new LinkedList<List<T>>();

        for (int i = 1; i <= list.size(); i++) {
            powerSet.addAll(combination(list, i));
        }

        return powerSet;
    }



    public static Matcher<View> matchBrickSetNumberInList(String batman) {
        return matchBrickSetNumberInList(equalTo(batman));
    }

    private static Matcher<View> matchBrickSetNumberInList(final Matcher<String> dataMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {

                if (!(view instanceof ListView)) {
                    return false;
                }

                ListView listView = (ListView) view;
                OnlineFetchedSetsAdapter adapter = (OnlineFetchedSetsAdapter) listView.getAdapter();
                List<BrickSet> data = adapter.getData();

                for (int i = 0; i < data.size(); i++) {

                    if (dataMatcher.matches(data.get(i).getNumber())) {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with content: " + description);
            }
        };
    }

    public static Matcher<View> matchBrickSetNameInList(String batman) {
        return matchBrickSetNameInList(equalTo(batman));
    }

    private static Matcher<View> matchBrickSetNameInList(final Matcher<String> dataMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {

                if (!(view instanceof ListView)) {
                    return false;
                }

                ListView listView = (ListView) view;
                OnlineFetchedSetsAdapter adapter = (OnlineFetchedSetsAdapter) listView.getAdapter();
                List<BrickSet> data = adapter.getData();

                for (int i = 0; i < data.size(); i++) {

                    if (dataMatcher.matches(data.get(i).getName())) {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with content: " + description);
            }
        };
    }
 }
