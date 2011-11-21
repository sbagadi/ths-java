package ths.web.util;

import javax.servlet.http.HttpServletRequest;

import ths.commons.util.StringUtils;

public class BrowserDetector 
{
    public static final String MSIE      = "MSIE";
    public static final String OPERA     = "Opera";
    public static final String MOZILLA   = "Mozilla";
    public static final String FIREFOX   = "Firefox";
    public static final String SAFARI    = "Safari";
    public static final String OTHER     = "Other";

    public static final String[] ALL_BROWSER_NAMES = {MOZILLA, MSIE, OPERA, FIREFOX, SAFARI};

    public static final String WINDOWS   = "Windows";
    public static final String LINUX     = "Linux";
    public static final String UNIX      = "Unix";
    public static final String MACINTOSH = "Macintosh";
    
    public static final String VERSION   = "Version";

    /** The user agent string. */
    private String userAgentString = "";

    /** The browser name specified in the user agent string. */
    private String browserName = "";

    /**
     * The browser version specified in the user agent string. If we can't parse
     * the version just assume an old browser.
     */
    private float browserVersion = (float) 1.0;

    /**
     * The browser platform specified in the user agent string.
     */
    private String browserPlatform = "Unknown";

    /**
     * The browser language.
     */
    private String browserLanguage = "en-us";

    /** Whether or not JavaScript works in this browser. */
    private boolean javascriptOK = false;

    /** Whether or not CSS works in this browser. */
    private boolean cssOK = false;

    /** Whether or not file upload works in this browser. */
    private boolean fileUploadOK = false;

    /**
     * Constructor used to initialize this class.
     * 
     * @param userAgentString
     *          A String with the user agent field.
     */
    public BrowserDetector(String userAgentString) {

        this.userAgentString = userAgentString;
        if (this.userAgentString == null) {
            this.userAgentString = "";
        }
        
        parse();
    }

    /**
     * Constructor used to initialize this class.
     */
    public BrowserDetector(HttpServletRequest req) {

        this(req.getHeader("User-Agent"));

        String accept_language = req.getHeader("Accept-Language");
        if (accept_language != null) {
            this.browserLanguage = accept_language.toLowerCase().split(";")[0].split(",")[0]; 
        }
    }

    /**
     * Whether or not CSS works in this browser.
     * 
     * @return True if CSS works in this browser.
     */
    public boolean isCssOK() {
        return cssOK;
    }

    /**
     * Whether or not file upload works in this browser.
     * 
     * @return True if file upload works in this browser.
     */
    public boolean isFileUploadOK() {
        return fileUploadOK;
    }

    /**
     * Whether or not JavaScript works in this browser.
     * 
     * @return True if JavaScript works in this browser.
     */
    public boolean isJavascriptOK() {
        return javascriptOK;
    }

    /**
     * The browser name specified in the user agent string.
     * 
     * @return A String with the browser name.
     */
    public String getBrowserName() {
        return browserName;
    }

    /**
     * The browser platform specified in the user agent string.
     * 
     * @return A String with the browser platform.
     */
    public String getBrowserPlatform() {
        return browserPlatform;
    }

    /**
     * The browser language specified.
     * 
     * @return A String with the browser language.
     */
    public String getBrowserLanguage() {
        return browserLanguage;
    }

    /**
     * The browser version specified in the user agent string.
     * 
     * @return A String with the browser version.
     */
    public float getBrowserVersion() {
        return browserVersion;
    }

    /**
     * The user agent string for this class.
     * 
     * @return A String with the user agent.
     */
    public String getUserAgentString() {
        return userAgentString;
    }

    /**
     * Helper method to initialize this class.
     */
    private void parse() {

        int versionStartIndex = userAgentString.indexOf("/");
        int versionEndIndex = userAgentString.indexOf(" ");

        // Get the browser name and version.
        if (versionStartIndex != -1) {
            browserName = userAgentString.substring(0, versionStartIndex);
            boolean canDetectBrowser = false;
            for (int i = 0; i < ALL_BROWSER_NAMES.length; i++) {
                if (ALL_BROWSER_NAMES[i].equals(browserName)) {
                    canDetectBrowser = true;
                    break;
                }
            }
            if (canDetectBrowser == false) {
                browserName = OTHER;
            }
        }

        try {

            // Not all user agents will have a space in the reported
            // string.
            String agentSubstring = null;

            if (versionEndIndex < 0) {
                agentSubstring = userAgentString.substring(versionStartIndex + 1);
            } else {
                agentSubstring = userAgentString.substring(versionStartIndex + 1, versionEndIndex);
            }

            browserVersion = toFloat(agentSubstring);
        } catch (NumberFormatException e) {
            // Just use the default value.
        }

        // MSIE lies about its name. Of course...
        if (userAgentString.indexOf(MSIE) != -1) {

            // Ex: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)
            versionStartIndex = (userAgentString.indexOf(MSIE) + MSIE.length() + 1);
            versionEndIndex = userAgentString.indexOf(";", versionStartIndex);
            browserName = MSIE;

            try {
                browserVersion = toFloat(StringUtils.removeNonDigits(userAgentString.substring(versionStartIndex, versionEndIndex)));
            } catch (NumberFormatException e) {
                // Just use the default value.
            }
        }

        if (userAgentString.indexOf(FIREFOX) != -1) {

            // Ex: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12
            versionStartIndex = (userAgentString.indexOf(FIREFOX) + FIREFOX.length() + 1);
            versionEndIndex = userAgentString.indexOf(".", versionStartIndex);
            browserName = FIREFOX;

            try {
                browserVersion = toFloat(StringUtils.removeNonDigits(userAgentString.substring(versionStartIndex, versionEndIndex)));
            } catch (NumberFormatException e) {
                // Just use the default value.
            }
        }

        if (userAgentString.indexOf(SAFARI) != -1) {

            // Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1 Safari/525.13
            versionStartIndex = (userAgentString.indexOf(VERSION) + VERSION.length() + 1);
            versionEndIndex = userAgentString.indexOf(" ", versionStartIndex);
            browserName = SAFARI;

            try {
                browserVersion = toFloat(StringUtils.removeNonDigits(userAgentString.substring(versionStartIndex, versionEndIndex)));
            } catch (NumberFormatException e) {
                // Just use the default value.
            }
        }

        // Opera isn't completely honest, either ...
        // Modificaton by Chris Mospaw <mospaw@polk-county.com>
        if (userAgentString.indexOf(OPERA) != -1) {

            //Ex: Mozilla/4.0 (Windows NT 4.0;US) Opera 3.61 [en]
            versionStartIndex = (userAgentString.indexOf(OPERA) + OPERA.length() + 1);
            versionEndIndex = userAgentString.indexOf(" ", versionStartIndex);
            browserName = OPERA;

            try {
                browserVersion = toFloat(userAgentString.substring(versionStartIndex, versionEndIndex));
            } catch (NumberFormatException e) {
                // Just use the default value.
            }

            // PHP code
            //$Browser_Name = "Opera";
            //$Browser_Version = strtok("Opera");
            //$Browser_Version = strtok("/");
            //$Browser_Version = strtok(";");
        }

        // Try to figure out what platform.
        if ((userAgentString.indexOf("Windows") != -1) || (userAgentString.indexOf("WinNT") != -1)
                || (userAgentString.indexOf("Win98") != -1) || (userAgentString.indexOf("Win95") != -1)) {
            browserPlatform = WINDOWS;
        }

        if (userAgentString.indexOf("Mac") != -1) {
            browserPlatform = MACINTOSH;
        }

        if (userAgentString.indexOf("X11") != -1) {
            browserPlatform = LINUX;
        }

        if (userAgentString.indexOf("Unix") != -1) {
            browserPlatform = UNIX;
        }

        if (browserPlatform.equals(WINDOWS)) {
            if (browserName.equals(MOZILLA)) {
                if (browserVersion >= 3.0) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }

                if (browserVersion >= 4.0) {
                    cssOK = true;
                }
            } else if (browserName.equals(MSIE)) {
                if (browserVersion >= 4.0) {
                    javascriptOK = true;
                    fileUploadOK = true;
                    cssOK = true;
                }
            } else if (browserName.equals(OPERA)) {
                if (browserVersion >= 3.0) {
                    javascriptOK = true;
                    fileUploadOK = true;
                    cssOK = true;
                }
            }
        } else if (browserPlatform.equals(MACINTOSH)) {
            if (browserName.equals(MOZILLA)) {
                if (browserVersion >= 3.0) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }

                if (browserVersion >= 4.0) {
                    cssOK = true;
                }
            } else if (browserName.equals(MSIE)) {
                if (browserVersion >= 4.0) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }

                if (browserVersion > 4.0) {
                    cssOK = true;
                }
            }
        } else if (browserPlatform.equals(LINUX)) {
            if (browserName.equals(MOZILLA)) {
                if (browserVersion >= 3.0) {
                    javascriptOK = true;
                    fileUploadOK = true;
                }

                if (browserVersion >= 4.0) {
                    cssOK = true;
                }
            }
        }
    }

    /**
     * Helper method to convert String to a float.
     * 
     * @param s 
     *          A String.
     * @return The String converted to float.
     */
    private static final float toFloat(String s) {
        return Float.parseFloat(s);
    }	
}
