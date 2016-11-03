package eclipseserver;

//Import required java libraries
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import formatter.EclipseCodeStyleFormatter;

public class SummaryEclipseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String FORMATTED_CODE_FILENAME = "./res/html/index.html";
	private static final String FORMATTED_CODE_PLACEHOLDER = "<!--***FORMATTED CODE***-->";
    private static final String UNFORMATTED_CODE_PLACEHOLDER = "<!--***CODE***-->";
    private static final String DEF_USE_PLACEHOLDER = "<!--***DEF-USE OUTPUT***-->";
	private static final String FORMATTED_CODE_TEMPLATE = "<pre class=\"prettyprint\">" 
	        + FORMATTED_CODE_PLACEHOLDER + "</pre>";
	
	enum Format {
		ui,
		text
	}	
	
	enum How {
		Compact,
		Eclipse,
		Vertical		
	}

    public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) 
            throws ServletException, IOException {
        doPost(aRequest, aResponse);
    }
	
	public void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws ServletException, IOException {
		String path = aRequest.getPathInfo();
		String url = aRequest.getRequestURL().toString();
		int colon = url.lastIndexOf(":") + 1;
		int end = url.indexOf("/", colon);
		String hostAndPort = url.substring("http://".length(), end);
		long time = System.currentTimeMillis();


		if( path.startsWith("/Formatter") ) { 
			
			String code =  aRequest.getParameter("code"); 
			How how = aRequest.getParameter("how") == null ? How.Compact : How.valueOf(aRequest.getParameter("how"));
			Format format = aRequest.getParameter("format") == null ? Format.ui : Format.valueOf(aRequest.getParameter("format")); 
			int lineLength = aRequest.getParameter("line-length") == null ? -1 : Integer.parseInt(aRequest.getParameter("line-length"));
			
            aResponse.setStatus(HttpServletResponse.SC_OK);
            ServletOutputStream out = aResponse.getOutputStream();
            
            String htmlString = FileUtils.readFileToString(
            		getWebFileFromBundle(FORMATTED_CODE_FILENAME));
            
            if( code == null ) {
                htmlString = htmlString.replace(UNFORMATTED_CODE_PLACEHOLDER, "");
            } else {
			    String formattedCode = "";
			    if( how == How.Compact ){
			    	formattedCode = EclipseCodeStyleFormatter.format(code, getCompactFile(), lineLength);
			    } else if( how == How.Eclipse ) {
			    	formattedCode = EclipseCodeStyleFormatter.format(code, getEclipseFile(), lineLength);
			    } else if( how == How.Vertical ) {
			    	formattedCode = EclipseCodeStyleFormatter.format(code, getVerticallyLongFile(), lineLength);
			    }
			    
			    if( format == Format.ui) {
		            aResponse.setContentType("text/html");
			    	htmlString = htmlString.replace(FORMATTED_CODE_PLACEHOLDER, 
			    			FORMATTED_CODE_TEMPLATE.replace(FORMATTED_CODE_PLACEHOLDER, formattedCode));
			    	htmlString = htmlString.replace(UNFORMATTED_CODE_PLACEHOLDER, code);
			    } else if( format == Format.text ) {
		            aResponse.setContentType("text/plain");
			    	htmlString = formattedCode;
			    }
            }

            out.print(htmlString);
			out.close();				
		} 
					

		long diff = (System.currentTimeMillis() - time)/1000;
		System.out.println("page loaded in " + diff + " s" );
	}
	
    public static File getWebFileFromBundle(String path) {
    	if ( SummaryEclipseServerApplication.TEST ) {
			URL url = Platform.getBundle("FormatterEclipseServer").getEntry(path);
		    	
			File file = null;
			if( url != null ) {
				try { 
					URL resolvedUrl = FileLocator.resolve(url);			
					file = new File(resolvedUrl.toURI());
				} catch( IOException e) {
					e.printStackTrace();
				} catch ( URISyntaxException e) {
					e.printStackTrace();
				}
			}
			return file;
    	} else {
    		return new File(path);
    	}
    }
    
    public static File getCodeStyleFileFromBundle(String path) {

    	if ( SummaryEclipseServerApplication.TEST ) {
		URL url = Platform.getBundle("Formatter").getEntry(path);
		File file = null;
		if( url != null ) {
			try { 
				URL resolvedUrl = FileLocator.resolve(url);			
				file = new File(resolvedUrl.toURI());
			} catch( IOException e) {
				e.printStackTrace();
			} catch ( URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return file;
    	} else {
    		return new File(path);
    	}
    }
    
    public static File getCompactFile() {
    	return getCodeStyleFileFromBundle(EclipseCodeStyleFormatter.FILE_COMPACT_STYLE);
    }
    
    public static File getVerticallyLongFile() {
    	return getCodeStyleFileFromBundle(EclipseCodeStyleFormatter.FILE_VERTICALLY_LONG_STYLE);
    }

    public static File getEclipseFile() {
    	return getCodeStyleFileFromBundle(EclipseCodeStyleFormatter.FILE_ECLIPSE_BUILT_IN_STYLE);
    }
}

