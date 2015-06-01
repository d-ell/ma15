package ma15.brickcollector.image;

import java.io.File;

import android.content.Context;
 
public class FileCache {
 
    private File cacheDir;
    private static FileCache instance = null;
 
    private FileCache()
    {

    }
    
    public void createCacheDir(Context context)
    {
	    //Find the dir to save cached images
	    if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	        cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
	    else
	        cacheDir=context.getCacheDir();
	    if(!cacheDir.exists())
	        cacheDir.mkdirs();
    }
    
    public static FileCache getInstance()
    {
    	if(instance == null) {
            instance = new FileCache();
        }
    	return instance;
    }
 
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        return new File(cacheDir, filename);
 
    }
 
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

    public final File getCacheDir() {
        return cacheDir;
    }
 
}