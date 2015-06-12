package ma15.brickcollector.Test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.test.AndroidTestCase;
import android.widget.ImageView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import ma15.brickcollector.Utils.Constants;
import ma15.brickcollector.Utils.Settings;
import ma15.brickcollector.Utils.XmlParser;
import ma15.brickcollector.connection.PostRequest;
import ma15.brickcollector.data.BrickSet;
import ma15.brickcollector.image.FileCache;
import ma15.brickcollector.image.ImageLoader;
import ma15.brickcollector.image.MemoryCache;

public class ImageLoadertest extends AndroidTestCase {

    ImageLoader imageLoader = null;

    @Before
    public void setUp() throws Exception {
        imageLoader = new ImageLoader(getContext());
        assertTrue(FileCache.getInstance().getCacheDir().exists());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDisplayImage() throws Exception {
        String url = "http://images.brickset.com/sets/thumbs/tn_4526-1_jpg.jpg";
        ImageView imageView  = new ImageView(getContext());
        MemoryCache.getInstance().clear();
        assertNull(MemoryCache.getInstance().get(url));

        imageLoader.DisplayImage(url, imageView);

        Bitmap bitmap = MemoryCache.getInstance().get(url);
        Thread.sleep(1000);
        assertNotNull(bitmap);

        String file = "assets/bitmap.jpg";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);

        Bitmap map = BitmapFactory.decodeStream(in);
        assertNotNull(map);
        assertTrue(map.sameAs(bitmap));
    }

    @Test
    public void testDisplayImageFail() throws Exception {
        String url = "http://images.brickset.com/sets/thumbs/tn_4526-1_jpg.jpg";
        ImageView imageView  = new ImageView(getContext());
        MemoryCache.getInstance().clear();
        assertNull(MemoryCache.getInstance().get(url));

        imageLoader.DisplayImage(url, imageView);

        Bitmap bitmap = MemoryCache.getInstance().get(url);

        String file = "assets/invalidBitmap.jpg";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);

        Bitmap map = BitmapFactory.decodeStream(in);
        assertFalse(map.sameAs(bitmap));
    }
}
