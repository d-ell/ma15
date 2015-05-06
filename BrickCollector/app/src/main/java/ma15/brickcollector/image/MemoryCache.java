package ma15.brickcollector.image;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
 
public class MemoryCache {
    private Map<String, SoftReference<Bitmap>> cache = Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
    
    private static MemoryCache instance = null;
    
    public static MemoryCache getInstance()
    {
    	if(instance == null)
    		instance = new MemoryCache();
    	return instance;
    }
    
    
    private MemoryCache()
    {
    	
    }
    
    public Bitmap get(String id){
        if(!cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref = cache.get(id);
        return ref.get();
    }
 
    public void put(String id, Bitmap bitmap){
        cache.put(id, new SoftReference<Bitmap>(bitmap));
    }
 
    public void clear() {
        cache.clear();
    }
}