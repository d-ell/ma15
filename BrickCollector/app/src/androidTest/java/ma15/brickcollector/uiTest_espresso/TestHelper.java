package ma15.brickcollector.uiTest_espresso;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.swap;

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
 }
