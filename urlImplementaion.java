package at.technikum.rh.Interfaces;
import java.net.*;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;


public class urlImplementaion implements Url{
    	/**
	 * @return Returns the raw url.
	 */
        private String rawUrl;
        @Override
	public String getRawUrl(){
            return rawUrl;
            
        }
        
        public urlImplementaion(String _rawUrl)
            {
                this.rawUrl = _rawUrl;
            }
        
	/**
	 * @return Returns the path of the url, without parameter.
	 */
        @Override
	public String getPath(){
           int Slash = rawUrl.indexOf("/");
           int doubleSlash = rawUrl.indexOf("//");
           int qMark = rawUrl.indexOf("?");
           
           if(Slash < 0)
           {
               return "";
           }
           else
           {
                if(doubleSlash > 0) //if there is a double slash (//) 
                {
                   String[] firstSplit = rawUrl.split("//"); //splits into two parts
                   String[] Path = firstSplit[1].split("/", 2);
                   return "/" + Path[1]; //returns the second part of the split
                }
                else if(qMark > 0) //if there is a question mark (?)
                {
                   String[] firstSplit = rawUrl.split("\\?"); //splits into two parts
                   String[] Path = firstSplit[0].split("/", 2);
                   return "/" + Path[1]; //returns the second part of the split
                }
                else if(doubleSlash > 0 && qMark > 0) //if protocol and parameters available
               {
                   String[] firstSplit = rawUrl.split("\\?");  // splits into two parts, where the ? is
                   String[] secondSplit = firstSplit[0].split("//"); // splits again into two parts
                   String[] Path = secondSplit[1].split("/", 2);
                   return "/" + Path[1]; //returns the second part of the second split
               }
               return rawUrl; //if none of them, returns the rawUrl
           }          
        }

	/**
	 * @return Returns a dictionary with the parameter of the url. Never returns
	 *         null.
	 */
        @Override
	public Map<String, String> getParameter(){
            Map<String, String> parameters = new HashMap<>();
            if(rawUrl.indexOf("?") > 0)
            {
                String[] param= rawUrl.split("\\?");
                String[] pairs = param[1].split("&"); //A 2
                for(int i=0; i<=pairs.length; i++ )
                {
                    String[] p= pairs[i].split("=");
                    String key = "";
                    String value = "";
                    if(p.length > 1){
                        key = p[0];
                        value = p[1];
                    }
                    parameters.put(key, value);
                }
            } 
            return parameters;
        }
	
	/**
	 * @return Returns the number of parameter of the url. Returns 0 if there are no parameter.
	 */
        @Override
	public int getParameterCount(){
            if(rawUrl.indexOf("?") > 0)
            {
                String[] firsSplit = rawUrl.split("\\?");
                String[] secondSplit = firsSplit[1].split("&");
                return  (int)secondSplit.length;   
            }
            else 
            {
                return 0;
            }
        }

	/**
	 * @return Returns the segments of the url path. A segment is divided by '/'
	 *         chars. Never returns null.
	 */
        @Override
	public String[] getSegments(){
            String getpath = getPath();
            String[] segments = getpath.split("/");
            String[] fsegments = Arrays.copyOfRange(segments, 1,  segments.length);
            return fsegments;
        }

	/**
	 * @return Returns the filename (with extension) of the url path. If the url
	 *         contains no filename, a empty string is returned. Never returns
	 *         null. A filename is present in the url, if the last segment
	 *         contains a name with at least one dot.
	 */
	public String getFileName(){
            String[] getsegments = getSegments();
            return getsegments[getsegments.length-1];  
        }

	/**
	 * @return Returns the extension of the url filename, including the leading
	 *         dot. If the url contains no filename, a empty string is returned.
	 *         Never returns null.
	 */
        @Override
	public String getExtension(){
            String getname = getFileName();
            if(getname.indexOf(".") > 0)
            {
                String[] getExt = getname.split(".");
                return getExt[1];
            }
            else
            {
               return ""; 
            }   
        }

	/**
	 * @return Returns the url fragment. A fragment is the part after a '#' char
	 *         at the end of the url. If the url contains no fragment, a empty
	 *         string is returned. Never returns null.
	 */
        @Override
	public String getFragment(){
           
            if(rawUrl.indexOf("#") > 0)
            {
                String[] fragment = rawUrl.split("#");
                return fragment[1];
            }else
            {
                return "";
            }
        }
}
