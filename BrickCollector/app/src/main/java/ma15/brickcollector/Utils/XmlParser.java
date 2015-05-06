package ma15.brickcollector.Utils;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import ma15.brickcollector.data.BrickSet;

public class XmlParser {

    final static String TAG = XmlParser.class.getName();

    private static final String ns = null;
    private static ArrayList<String> tagNames = new ArrayList<>();

    static {
        for (Field field : BrickSet.class.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                tagNames.add(field.getName());
            }
        }
    }

    public static String getUserHash(String xml) {
        return getXMLResultString(xml);
    }

    public static String getXMLResultString(String xml) {
        InputStream in = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            in = new ByteArrayInputStream(xml.getBytes());
            parser.setInput(in, null);
            parser.nextTag();
            return readTag(parser,"string");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static ArrayList<BrickSet> getSets(String xml) {
        InputStream in = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            in = new ByteArrayInputStream(xml.getBytes());
            parser.setInput(in, null);
            parser.nextTag();
            return readSets(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static ArrayList<BrickSet> readSets(XmlPullParser parser) throws XmlPullParserException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ArrayList<BrickSet> sets = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "ArrayOfSets");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("sets")) {
                sets.add(readSet(parser));
            } else {
                skip(parser);
            }
        }
        return sets;
    }

    private static BrickSet readSet(XmlPullParser parser) throws IOException, XmlPullParserException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrickSet brickSet = new BrickSet();

        parser.require(XmlPullParser.START_TAG, ns, "sets");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (tagNames.contains(name)) {
                String tag = readTag(parser, name);
                String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
                brickSet.getClass().getMethod(methodName, String.class).invoke(brickSet, tag);
            } else {
                skip(parser);
            }
        }
        return brickSet;
    }

    private static String readTag(XmlPullParser parser, String tag_name) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag_name);
        String tag = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag_name);
        return tag;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
