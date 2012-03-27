package iControl;

import java.net.URL;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.axis.client.Stub;
import org.apache.axis.client.Service;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.configuration.BasicClientConfig;
import org.apache.axis.SimpleTargetedChain;
import iControl.utils.IControlHTTPSender;



public class BigIP {

   private Log log = LogFactory.getLog(getClass());
   java.util.HashMap<String, Service> services = new java.util.HashMap<String, Service>();
   java.util.HashMap<String, Stub> stubs = new java.util.HashMap<String, Stub>();
   java.util.Hashtable<String, String> httpHeaders = new java.util.Hashtable<String,String>();
   java.util.ArrayList<String> interfaces = new java.util.ArrayList<String>();
   private URL url;
   private String username = "admin";
   private String password = "admin";
   private boolean ignoreInvalidCert = false;
   private SimpleProvider config = new SimpleProvider(new BasicClientConfig());
   private long session_id = -1;
   private String currentFolder = "Common";
   private boolean inTransaction = false;



   public BigIP() {
     log.trace("entering default constructor BigIP().");
     this.url = getUrl();
     initTransport();
   }
   public BigIP(String username, String password) {
     log.trace("entering constructor BigIP(username,password).");
     this.username = username;
     this.password = password;
     this.url = getUrl();
     initTransport();
   }
   public BigIP(String hostname, String username, String password) {
     log.trace("entering constructor BigIP(username,password).");
     this.username = username;
     this.password = password;
     initTransport();
     try {
       setHost(hostname);
     } catch (MalformedURLException e) {
       log.error("Exception",e);
       e.printStackTrace();
     }
   }
   public BigIP(URL url, String username, String password) {
     log.trace("entering constructor BigIP(url,username,password).");
     this.url = url;
     setUsername(username);
     setPassword(password);
     initTransport();
   }
   private void initTransport() {
      SimpleTargetedChain c = new SimpleTargetedChain(new IControlHTTPSender());
      config.deployTransport("http", c);
      config.deployTransport("https", c);
   }
   public URL getUrl() {
     log.trace("entering getUrl()");
	  if(url == null) {
			try {
				url = new URL("https://192.168.100.245:443/iControl/iControlPortal.cgi");
			} catch (MalformedURLException e) {
               log.error("Exception",e);
               e.printStackTrace();
			}
	  }
	  return url;
   }
   public void setUrl(URL url) {
     log.trace("entering setUrl(url).");
     this.url = url;
   }
   public String getUsername() {
     log.trace("entering getUsername().");
     return username;
   }
   public void setUsername(String username) {
     log.trace("entering setUsername(username).");
     this.username = username;
   }
   public String getPassword() {
     log.trace("entering getPassword().");
     return password;
   }
   public void setPassword(String password) {
     log.trace("entering setPassword(password).");
     this.password = password;
   }
   public void setHost(String host) throws MalformedURLException {
     log.trace("entering setHost(host).");
     url = getUrl();
     url = new URL(url.getProtocol()+"://"+host+":"+url.getPort()+"/iControl/iControlPortal.cgi");
   }
   public boolean isIgnoreInvalidCert() {
     log.trace("entering isIgnoreInvalidCert().");
     return ignoreInvalidCert;
   }
   public void setIgnoreInvalidCert(boolean ignoreInvalidCert) {
     log.trace("entering setIgnoreInvalidCert("+ignoreInvalidCert+").");
     this.ignoreInvalidCert = ignoreInvalidCert;
     if(ignoreInvalidCert) {
            log.info("install iControl.utils.XTrustProvider");
            iControl.utils.XTrustProvider.install();
     }
   }
   public void addHTTPHeader(String headerName, String headerValue) {
     log.trace("entering addHTTPHeader("+headerName+","+headerValue+").");
     httpHeaders.put(headerName, headerValue);
   }
   public void removeHTTPHeader(String headerName) {
     log.trace("entering removeHTTPHeader("+headerName+").");
     httpHeaders.remove(headerName);
   }
   public java.util.Hashtable<String,String> getHTTPHeaders() {
     log.trace("entering getHTTPHeaders().");
     return httpHeaders;
   }


   /**
   * Creates the service interface object for the ASMLoggingProfile iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ASMLoggingProfileBindingStub ASMLoggingProfile() throws RemoteException, ServiceException {
      log.trace("entering ASMLoggingProfile");
      if(stubs.containsKey("ASMLoggingProfile")){
           log.info("returning ASMLoggingProfile binding stub from cache.");
           iControl.services.ASMLoggingProfileBindingStub binding = (iControl.services.ASMLoggingProfileBindingStub) stubs.get("ASMLoggingProfile");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/LoggingProfile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ASMLoggingProfile binding stub for "+getUrl());
           iControl.services.ASMLoggingProfileLocator locator = new iControl.services.ASMLoggingProfileLocator(config);
           iControl.services.ASMLoggingProfileBindingStub binding = (iControl.services.ASMLoggingProfileBindingStub) locator.getASMLoggingProfilePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/LoggingProfile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ASMLoggingProfile binding stub to cache.");
           services.put("ASMLoggingProfile",locator);
           stubs.put("ASMLoggingProfile",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ASMObjectParams iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ASMObjectParamsBindingStub ASMObjectParams() throws RemoteException, ServiceException {
      log.trace("entering ASMObjectParams");
      if(stubs.containsKey("ASMObjectParams")){
           log.info("returning ASMObjectParams binding stub from cache.");
           iControl.services.ASMObjectParamsBindingStub binding = (iControl.services.ASMObjectParamsBindingStub) stubs.get("ASMObjectParams");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/ObjectParams", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ASMObjectParams binding stub for "+getUrl());
           iControl.services.ASMObjectParamsLocator locator = new iControl.services.ASMObjectParamsLocator(config);
           iControl.services.ASMObjectParamsBindingStub binding = (iControl.services.ASMObjectParamsBindingStub) locator.getASMObjectParamsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/ObjectParams", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ASMObjectParams binding stub to cache.");
           services.put("ASMObjectParams",locator);
           stubs.put("ASMObjectParams",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ASMPolicy iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ASMPolicyBindingStub ASMPolicy() throws RemoteException, ServiceException {
      log.trace("entering ASMPolicy");
      if(stubs.containsKey("ASMPolicy")){
           log.info("returning ASMPolicy binding stub from cache.");
           iControl.services.ASMPolicyBindingStub binding = (iControl.services.ASMPolicyBindingStub) stubs.get("ASMPolicy");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/Policy", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ASMPolicy binding stub for "+getUrl());
           iControl.services.ASMPolicyLocator locator = new iControl.services.ASMPolicyLocator(config);
           iControl.services.ASMPolicyBindingStub binding = (iControl.services.ASMPolicyBindingStub) locator.getASMPolicyPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/Policy", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ASMPolicy binding stub to cache.");
           services.put("ASMPolicy",locator);
           stubs.put("ASMPolicy",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ASMSystemConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ASMSystemConfigurationBindingStub ASMSystemConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ASMSystemConfiguration");
      if(stubs.containsKey("ASMSystemConfiguration")){
           log.info("returning ASMSystemConfiguration binding stub from cache.");
           iControl.services.ASMSystemConfigurationBindingStub binding = (iControl.services.ASMSystemConfigurationBindingStub) stubs.get("ASMSystemConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/SystemConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ASMSystemConfiguration binding stub for "+getUrl());
           iControl.services.ASMSystemConfigurationLocator locator = new iControl.services.ASMSystemConfigurationLocator(config);
           iControl.services.ASMSystemConfigurationBindingStub binding = (iControl.services.ASMSystemConfigurationBindingStub) locator.getASMSystemConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/SystemConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ASMSystemConfiguration binding stub to cache.");
           services.put("ASMSystemConfiguration",locator);
           stubs.put("ASMSystemConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ASMWebApplication iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ASMWebApplicationBindingStub ASMWebApplication() throws RemoteException, ServiceException {
      log.trace("entering ASMWebApplication");
      if(stubs.containsKey("ASMWebApplication")){
           log.info("returning ASMWebApplication binding stub from cache.");
           iControl.services.ASMWebApplicationBindingStub binding = (iControl.services.ASMWebApplicationBindingStub) stubs.get("ASMWebApplication");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/WebApplication", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ASMWebApplication binding stub for "+getUrl());
           iControl.services.ASMWebApplicationLocator locator = new iControl.services.ASMWebApplicationLocator(config);
           iControl.services.ASMWebApplicationBindingStub binding = (iControl.services.ASMWebApplicationBindingStub) locator.getASMWebApplicationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/WebApplication", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ASMWebApplication binding stub to cache.");
           services.put("ASMWebApplication",locator);
           stubs.put("ASMWebApplication",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ASMWebApplicationGroup iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ASMWebApplicationGroupBindingStub ASMWebApplicationGroup() throws RemoteException, ServiceException {
      log.trace("entering ASMWebApplicationGroup");
      if(stubs.containsKey("ASMWebApplicationGroup")){
           log.info("returning ASMWebApplicationGroup binding stub from cache.");
           iControl.services.ASMWebApplicationGroupBindingStub binding = (iControl.services.ASMWebApplicationGroupBindingStub) stubs.get("ASMWebApplicationGroup");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/WebApplicationGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ASMWebApplicationGroup binding stub for "+getUrl());
           iControl.services.ASMWebApplicationGroupLocator locator = new iControl.services.ASMWebApplicationGroupLocator(config);
           iControl.services.ASMWebApplicationGroupBindingStub binding = (iControl.services.ASMWebApplicationGroupBindingStub) locator.getASMWebApplicationGroupPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:ASM/WebApplicationGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ASMWebApplicationGroup binding stub to cache.");
           services.put("ASMWebApplicationGroup",locator);
           stubs.put("ASMWebApplicationGroup",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBApplication iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBApplicationBindingStub GlobalLBApplication() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBApplication");
      if(stubs.containsKey("GlobalLBApplication")){
           log.info("returning GlobalLBApplication binding stub from cache.");
           iControl.services.GlobalLBApplicationBindingStub binding = (iControl.services.GlobalLBApplicationBindingStub) stubs.get("GlobalLBApplication");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Application", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBApplication binding stub for "+getUrl());
           iControl.services.GlobalLBApplicationLocator locator = new iControl.services.GlobalLBApplicationLocator(config);
           iControl.services.GlobalLBApplicationBindingStub binding = (iControl.services.GlobalLBApplicationBindingStub) locator.getGlobalLBApplicationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Application", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBApplication binding stub to cache.");
           services.put("GlobalLBApplication",locator);
           stubs.put("GlobalLBApplication",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBDNSSECKey iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBDNSSECKeyBindingStub GlobalLBDNSSECKey() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBDNSSECKey");
      if(stubs.containsKey("GlobalLBDNSSECKey")){
           log.info("returning GlobalLBDNSSECKey binding stub from cache.");
           iControl.services.GlobalLBDNSSECKeyBindingStub binding = (iControl.services.GlobalLBDNSSECKeyBindingStub) stubs.get("GlobalLBDNSSECKey");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/DNSSECKey", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBDNSSECKey binding stub for "+getUrl());
           iControl.services.GlobalLBDNSSECKeyLocator locator = new iControl.services.GlobalLBDNSSECKeyLocator(config);
           iControl.services.GlobalLBDNSSECKeyBindingStub binding = (iControl.services.GlobalLBDNSSECKeyBindingStub) locator.getGlobalLBDNSSECKeyPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/DNSSECKey", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBDNSSECKey binding stub to cache.");
           services.put("GlobalLBDNSSECKey",locator);
           stubs.put("GlobalLBDNSSECKey",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBDNSSECZone iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBDNSSECZoneBindingStub GlobalLBDNSSECZone() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBDNSSECZone");
      if(stubs.containsKey("GlobalLBDNSSECZone")){
           log.info("returning GlobalLBDNSSECZone binding stub from cache.");
           iControl.services.GlobalLBDNSSECZoneBindingStub binding = (iControl.services.GlobalLBDNSSECZoneBindingStub) stubs.get("GlobalLBDNSSECZone");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/DNSSECZone", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBDNSSECZone binding stub for "+getUrl());
           iControl.services.GlobalLBDNSSECZoneLocator locator = new iControl.services.GlobalLBDNSSECZoneLocator(config);
           iControl.services.GlobalLBDNSSECZoneBindingStub binding = (iControl.services.GlobalLBDNSSECZoneBindingStub) locator.getGlobalLBDNSSECZonePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/DNSSECZone", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBDNSSECZone binding stub to cache.");
           services.put("GlobalLBDNSSECZone",locator);
           stubs.put("GlobalLBDNSSECZone",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBDataCenter iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBDataCenterBindingStub GlobalLBDataCenter() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBDataCenter");
      if(stubs.containsKey("GlobalLBDataCenter")){
           log.info("returning GlobalLBDataCenter binding stub from cache.");
           iControl.services.GlobalLBDataCenterBindingStub binding = (iControl.services.GlobalLBDataCenterBindingStub) stubs.get("GlobalLBDataCenter");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/DataCenter", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBDataCenter binding stub for "+getUrl());
           iControl.services.GlobalLBDataCenterLocator locator = new iControl.services.GlobalLBDataCenterLocator(config);
           iControl.services.GlobalLBDataCenterBindingStub binding = (iControl.services.GlobalLBDataCenterBindingStub) locator.getGlobalLBDataCenterPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/DataCenter", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBDataCenter binding stub to cache.");
           services.put("GlobalLBDataCenter",locator);
           stubs.put("GlobalLBDataCenter",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBGlobals iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBGlobalsBindingStub GlobalLBGlobals() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBGlobals");
      if(stubs.containsKey("GlobalLBGlobals")){
           log.info("returning GlobalLBGlobals binding stub from cache.");
           iControl.services.GlobalLBGlobalsBindingStub binding = (iControl.services.GlobalLBGlobalsBindingStub) stubs.get("GlobalLBGlobals");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Globals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBGlobals binding stub for "+getUrl());
           iControl.services.GlobalLBGlobalsLocator locator = new iControl.services.GlobalLBGlobalsLocator(config);
           iControl.services.GlobalLBGlobalsBindingStub binding = (iControl.services.GlobalLBGlobalsBindingStub) locator.getGlobalLBGlobalsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Globals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBGlobals binding stub to cache.");
           services.put("GlobalLBGlobals",locator);
           stubs.put("GlobalLBGlobals",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBLink iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBLinkBindingStub GlobalLBLink() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBLink");
      if(stubs.containsKey("GlobalLBLink")){
           log.info("returning GlobalLBLink binding stub from cache.");
           iControl.services.GlobalLBLinkBindingStub binding = (iControl.services.GlobalLBLinkBindingStub) stubs.get("GlobalLBLink");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Link", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBLink binding stub for "+getUrl());
           iControl.services.GlobalLBLinkLocator locator = new iControl.services.GlobalLBLinkLocator(config);
           iControl.services.GlobalLBLinkBindingStub binding = (iControl.services.GlobalLBLinkBindingStub) locator.getGlobalLBLinkPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Link", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBLink binding stub to cache.");
           services.put("GlobalLBLink",locator);
           stubs.put("GlobalLBLink",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBMonitor iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBMonitorBindingStub GlobalLBMonitor() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBMonitor");
      if(stubs.containsKey("GlobalLBMonitor")){
           log.info("returning GlobalLBMonitor binding stub from cache.");
           iControl.services.GlobalLBMonitorBindingStub binding = (iControl.services.GlobalLBMonitorBindingStub) stubs.get("GlobalLBMonitor");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Monitor", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBMonitor binding stub for "+getUrl());
           iControl.services.GlobalLBMonitorLocator locator = new iControl.services.GlobalLBMonitorLocator(config);
           iControl.services.GlobalLBMonitorBindingStub binding = (iControl.services.GlobalLBMonitorBindingStub) locator.getGlobalLBMonitorPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Monitor", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBMonitor binding stub to cache.");
           services.put("GlobalLBMonitor",locator);
           stubs.put("GlobalLBMonitor",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBPool iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBPoolBindingStub GlobalLBPool() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBPool");
      if(stubs.containsKey("GlobalLBPool")){
           log.info("returning GlobalLBPool binding stub from cache.");
           iControl.services.GlobalLBPoolBindingStub binding = (iControl.services.GlobalLBPoolBindingStub) stubs.get("GlobalLBPool");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Pool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBPool binding stub for "+getUrl());
           iControl.services.GlobalLBPoolLocator locator = new iControl.services.GlobalLBPoolLocator(config);
           iControl.services.GlobalLBPoolBindingStub binding = (iControl.services.GlobalLBPoolBindingStub) locator.getGlobalLBPoolPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Pool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBPool binding stub to cache.");
           services.put("GlobalLBPool",locator);
           stubs.put("GlobalLBPool",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBPoolMember iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBPoolMemberBindingStub GlobalLBPoolMember() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBPoolMember");
      if(stubs.containsKey("GlobalLBPoolMember")){
           log.info("returning GlobalLBPoolMember binding stub from cache.");
           iControl.services.GlobalLBPoolMemberBindingStub binding = (iControl.services.GlobalLBPoolMemberBindingStub) stubs.get("GlobalLBPoolMember");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/PoolMember", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBPoolMember binding stub for "+getUrl());
           iControl.services.GlobalLBPoolMemberLocator locator = new iControl.services.GlobalLBPoolMemberLocator(config);
           iControl.services.GlobalLBPoolMemberBindingStub binding = (iControl.services.GlobalLBPoolMemberBindingStub) locator.getGlobalLBPoolMemberPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/PoolMember", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBPoolMember binding stub to cache.");
           services.put("GlobalLBPoolMember",locator);
           stubs.put("GlobalLBPoolMember",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBProberPool iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBProberPoolBindingStub GlobalLBProberPool() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBProberPool");
      if(stubs.containsKey("GlobalLBProberPool")){
           log.info("returning GlobalLBProberPool binding stub from cache.");
           iControl.services.GlobalLBProberPoolBindingStub binding = (iControl.services.GlobalLBProberPoolBindingStub) stubs.get("GlobalLBProberPool");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/ProberPool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBProberPool binding stub for "+getUrl());
           iControl.services.GlobalLBProberPoolLocator locator = new iControl.services.GlobalLBProberPoolLocator(config);
           iControl.services.GlobalLBProberPoolBindingStub binding = (iControl.services.GlobalLBProberPoolBindingStub) locator.getGlobalLBProberPoolPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/ProberPool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBProberPool binding stub to cache.");
           services.put("GlobalLBProberPool",locator);
           stubs.put("GlobalLBProberPool",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBRegion iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBRegionBindingStub GlobalLBRegion() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBRegion");
      if(stubs.containsKey("GlobalLBRegion")){
           log.info("returning GlobalLBRegion binding stub from cache.");
           iControl.services.GlobalLBRegionBindingStub binding = (iControl.services.GlobalLBRegionBindingStub) stubs.get("GlobalLBRegion");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Region", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBRegion binding stub for "+getUrl());
           iControl.services.GlobalLBRegionLocator locator = new iControl.services.GlobalLBRegionLocator(config);
           iControl.services.GlobalLBRegionBindingStub binding = (iControl.services.GlobalLBRegionBindingStub) locator.getGlobalLBRegionPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Region", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBRegion binding stub to cache.");
           services.put("GlobalLBRegion",locator);
           stubs.put("GlobalLBRegion",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBRule iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBRuleBindingStub GlobalLBRule() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBRule");
      if(stubs.containsKey("GlobalLBRule")){
           log.info("returning GlobalLBRule binding stub from cache.");
           iControl.services.GlobalLBRuleBindingStub binding = (iControl.services.GlobalLBRuleBindingStub) stubs.get("GlobalLBRule");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Rule", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBRule binding stub for "+getUrl());
           iControl.services.GlobalLBRuleLocator locator = new iControl.services.GlobalLBRuleLocator(config);
           iControl.services.GlobalLBRuleBindingStub binding = (iControl.services.GlobalLBRuleBindingStub) locator.getGlobalLBRulePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Rule", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBRule binding stub to cache.");
           services.put("GlobalLBRule",locator);
           stubs.put("GlobalLBRule",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBServer iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBServerBindingStub GlobalLBServer() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBServer");
      if(stubs.containsKey("GlobalLBServer")){
           log.info("returning GlobalLBServer binding stub from cache.");
           iControl.services.GlobalLBServerBindingStub binding = (iControl.services.GlobalLBServerBindingStub) stubs.get("GlobalLBServer");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Server", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBServer binding stub for "+getUrl());
           iControl.services.GlobalLBServerLocator locator = new iControl.services.GlobalLBServerLocator(config);
           iControl.services.GlobalLBServerBindingStub binding = (iControl.services.GlobalLBServerBindingStub) locator.getGlobalLBServerPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Server", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBServer binding stub to cache.");
           services.put("GlobalLBServer",locator);
           stubs.put("GlobalLBServer",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBTopology iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBTopologyBindingStub GlobalLBTopology() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBTopology");
      if(stubs.containsKey("GlobalLBTopology")){
           log.info("returning GlobalLBTopology binding stub from cache.");
           iControl.services.GlobalLBTopologyBindingStub binding = (iControl.services.GlobalLBTopologyBindingStub) stubs.get("GlobalLBTopology");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Topology", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBTopology binding stub for "+getUrl());
           iControl.services.GlobalLBTopologyLocator locator = new iControl.services.GlobalLBTopologyLocator(config);
           iControl.services.GlobalLBTopologyBindingStub binding = (iControl.services.GlobalLBTopologyBindingStub) locator.getGlobalLBTopologyPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/Topology", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBTopology binding stub to cache.");
           services.put("GlobalLBTopology",locator);
           stubs.put("GlobalLBTopology",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBVirtualServer iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBVirtualServerBindingStub GlobalLBVirtualServer() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBVirtualServer");
      if(stubs.containsKey("GlobalLBVirtualServer")){
           log.info("returning GlobalLBVirtualServer binding stub from cache.");
           iControl.services.GlobalLBVirtualServerBindingStub binding = (iControl.services.GlobalLBVirtualServerBindingStub) stubs.get("GlobalLBVirtualServer");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/VirtualServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBVirtualServer binding stub for "+getUrl());
           iControl.services.GlobalLBVirtualServerLocator locator = new iControl.services.GlobalLBVirtualServerLocator(config);
           iControl.services.GlobalLBVirtualServerBindingStub binding = (iControl.services.GlobalLBVirtualServerBindingStub) locator.getGlobalLBVirtualServerPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/VirtualServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBVirtualServer binding stub to cache.");
           services.put("GlobalLBVirtualServer",locator);
           stubs.put("GlobalLBVirtualServer",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBVirtualServerV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBVirtualServerV2BindingStub GlobalLBVirtualServerV2() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBVirtualServerV2");
      if(stubs.containsKey("GlobalLBVirtualServerV2")){
           log.info("returning GlobalLBVirtualServerV2 binding stub from cache.");
           iControl.services.GlobalLBVirtualServerV2BindingStub binding = (iControl.services.GlobalLBVirtualServerV2BindingStub) stubs.get("GlobalLBVirtualServerV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/VirtualServerV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBVirtualServerV2 binding stub for "+getUrl());
           iControl.services.GlobalLBVirtualServerV2Locator locator = new iControl.services.GlobalLBVirtualServerV2Locator(config);
           iControl.services.GlobalLBVirtualServerV2BindingStub binding = (iControl.services.GlobalLBVirtualServerV2BindingStub) locator.getGlobalLBVirtualServerV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/VirtualServerV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBVirtualServerV2 binding stub to cache.");
           services.put("GlobalLBVirtualServerV2",locator);
           stubs.put("GlobalLBVirtualServerV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the GlobalLBWideIP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.GlobalLBWideIPBindingStub GlobalLBWideIP() throws RemoteException, ServiceException {
      log.trace("entering GlobalLBWideIP");
      if(stubs.containsKey("GlobalLBWideIP")){
           log.info("returning GlobalLBWideIP binding stub from cache.");
           iControl.services.GlobalLBWideIPBindingStub binding = (iControl.services.GlobalLBWideIPBindingStub) stubs.get("GlobalLBWideIP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/WideIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating GlobalLBWideIP binding stub for "+getUrl());
           iControl.services.GlobalLBWideIPLocator locator = new iControl.services.GlobalLBWideIPLocator(config);
           iControl.services.GlobalLBWideIPBindingStub binding = (iControl.services.GlobalLBWideIPBindingStub) locator.getGlobalLBWideIPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:GlobalLB/WideIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding GlobalLBWideIP binding stub to cache.");
           services.put("GlobalLBWideIP",locator);
           stubs.put("GlobalLBWideIP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LTConfigClass iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LTConfigClassBindingStub LTConfigClass() throws RemoteException, ServiceException {
      log.trace("entering LTConfigClass");
      if(stubs.containsKey("LTConfigClass")){
           log.info("returning LTConfigClass binding stub from cache.");
           iControl.services.LTConfigClassBindingStub binding = (iControl.services.LTConfigClassBindingStub) stubs.get("LTConfigClass");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LTConfig/Class", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LTConfigClass binding stub for "+getUrl());
           iControl.services.LTConfigClassLocator locator = new iControl.services.LTConfigClassLocator(config);
           iControl.services.LTConfigClassBindingStub binding = (iControl.services.LTConfigClassBindingStub) locator.getLTConfigClassPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LTConfig/Class", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LTConfigClass binding stub to cache.");
           services.put("LTConfigClass",locator);
           stubs.put("LTConfigClass",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LTConfigField iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LTConfigFieldBindingStub LTConfigField() throws RemoteException, ServiceException {
      log.trace("entering LTConfigField");
      if(stubs.containsKey("LTConfigField")){
           log.info("returning LTConfigField binding stub from cache.");
           iControl.services.LTConfigFieldBindingStub binding = (iControl.services.LTConfigFieldBindingStub) stubs.get("LTConfigField");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LTConfig/Field", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LTConfigField binding stub for "+getUrl());
           iControl.services.LTConfigFieldLocator locator = new iControl.services.LTConfigFieldLocator(config);
           iControl.services.LTConfigFieldBindingStub binding = (iControl.services.LTConfigFieldBindingStub) locator.getLTConfigFieldPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LTConfig/Field", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LTConfigField binding stub to cache.");
           services.put("LTConfigField",locator);
           stubs.put("LTConfigField",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBClass iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBClassBindingStub LocalLBClass() throws RemoteException, ServiceException {
      log.trace("entering LocalLBClass");
      if(stubs.containsKey("LocalLBClass")){
           log.info("returning LocalLBClass binding stub from cache.");
           iControl.services.LocalLBClassBindingStub binding = (iControl.services.LocalLBClassBindingStub) stubs.get("LocalLBClass");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Class", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBClass binding stub for "+getUrl());
           iControl.services.LocalLBClassLocator locator = new iControl.services.LocalLBClassLocator(config);
           iControl.services.LocalLBClassBindingStub binding = (iControl.services.LocalLBClassBindingStub) locator.getLocalLBClassPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Class", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBClass binding stub to cache.");
           services.put("LocalLBClass",locator);
           stubs.put("LocalLBClass",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBDNSExpress iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBDNSExpressBindingStub LocalLBDNSExpress() throws RemoteException, ServiceException {
      log.trace("entering LocalLBDNSExpress");
      if(stubs.containsKey("LocalLBDNSExpress")){
           log.info("returning LocalLBDNSExpress binding stub from cache.");
           iControl.services.LocalLBDNSExpressBindingStub binding = (iControl.services.LocalLBDNSExpressBindingStub) stubs.get("LocalLBDNSExpress");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/DNSExpress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBDNSExpress binding stub for "+getUrl());
           iControl.services.LocalLBDNSExpressLocator locator = new iControl.services.LocalLBDNSExpressLocator(config);
           iControl.services.LocalLBDNSExpressBindingStub binding = (iControl.services.LocalLBDNSExpressBindingStub) locator.getLocalLBDNSExpressPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/DNSExpress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBDNSExpress binding stub to cache.");
           services.put("LocalLBDNSExpress",locator);
           stubs.put("LocalLBDNSExpress",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBDataGroupFile iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBDataGroupFileBindingStub LocalLBDataGroupFile() throws RemoteException, ServiceException {
      log.trace("entering LocalLBDataGroupFile");
      if(stubs.containsKey("LocalLBDataGroupFile")){
           log.info("returning LocalLBDataGroupFile binding stub from cache.");
           iControl.services.LocalLBDataGroupFileBindingStub binding = (iControl.services.LocalLBDataGroupFileBindingStub) stubs.get("LocalLBDataGroupFile");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/DataGroupFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBDataGroupFile binding stub for "+getUrl());
           iControl.services.LocalLBDataGroupFileLocator locator = new iControl.services.LocalLBDataGroupFileLocator(config);
           iControl.services.LocalLBDataGroupFileBindingStub binding = (iControl.services.LocalLBDataGroupFileBindingStub) locator.getLocalLBDataGroupFilePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/DataGroupFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBDataGroupFile binding stub to cache.");
           services.put("LocalLBDataGroupFile",locator);
           stubs.put("LocalLBDataGroupFile",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBMonitor iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBMonitorBindingStub LocalLBMonitor() throws RemoteException, ServiceException {
      log.trace("entering LocalLBMonitor");
      if(stubs.containsKey("LocalLBMonitor")){
           log.info("returning LocalLBMonitor binding stub from cache.");
           iControl.services.LocalLBMonitorBindingStub binding = (iControl.services.LocalLBMonitorBindingStub) stubs.get("LocalLBMonitor");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Monitor", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBMonitor binding stub for "+getUrl());
           iControl.services.LocalLBMonitorLocator locator = new iControl.services.LocalLBMonitorLocator(config);
           iControl.services.LocalLBMonitorBindingStub binding = (iControl.services.LocalLBMonitorBindingStub) locator.getLocalLBMonitorPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Monitor", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBMonitor binding stub to cache.");
           services.put("LocalLBMonitor",locator);
           stubs.put("LocalLBMonitor",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBNAT iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBNATBindingStub LocalLBNAT() throws RemoteException, ServiceException {
      log.trace("entering LocalLBNAT");
      if(stubs.containsKey("LocalLBNAT")){
           log.info("returning LocalLBNAT binding stub from cache.");
           iControl.services.LocalLBNATBindingStub binding = (iControl.services.LocalLBNATBindingStub) stubs.get("LocalLBNAT");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NAT", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBNAT binding stub for "+getUrl());
           iControl.services.LocalLBNATLocator locator = new iControl.services.LocalLBNATLocator(config);
           iControl.services.LocalLBNATBindingStub binding = (iControl.services.LocalLBNATBindingStub) locator.getLocalLBNATPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NAT", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBNAT binding stub to cache.");
           services.put("LocalLBNAT",locator);
           stubs.put("LocalLBNAT",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBNATV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBNATV2BindingStub LocalLBNATV2() throws RemoteException, ServiceException {
      log.trace("entering LocalLBNATV2");
      if(stubs.containsKey("LocalLBNATV2")){
           log.info("returning LocalLBNATV2 binding stub from cache.");
           iControl.services.LocalLBNATV2BindingStub binding = (iControl.services.LocalLBNATV2BindingStub) stubs.get("LocalLBNATV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NATV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBNATV2 binding stub for "+getUrl());
           iControl.services.LocalLBNATV2Locator locator = new iControl.services.LocalLBNATV2Locator(config);
           iControl.services.LocalLBNATV2BindingStub binding = (iControl.services.LocalLBNATV2BindingStub) locator.getLocalLBNATV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NATV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBNATV2 binding stub to cache.");
           services.put("LocalLBNATV2",locator);
           stubs.put("LocalLBNATV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBNodeAddress iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBNodeAddressBindingStub LocalLBNodeAddress() throws RemoteException, ServiceException {
      log.trace("entering LocalLBNodeAddress");
      if(stubs.containsKey("LocalLBNodeAddress")){
           log.info("returning LocalLBNodeAddress binding stub from cache.");
           iControl.services.LocalLBNodeAddressBindingStub binding = (iControl.services.LocalLBNodeAddressBindingStub) stubs.get("LocalLBNodeAddress");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NodeAddress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBNodeAddress binding stub for "+getUrl());
           iControl.services.LocalLBNodeAddressLocator locator = new iControl.services.LocalLBNodeAddressLocator(config);
           iControl.services.LocalLBNodeAddressBindingStub binding = (iControl.services.LocalLBNodeAddressBindingStub) locator.getLocalLBNodeAddressPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NodeAddress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBNodeAddress binding stub to cache.");
           services.put("LocalLBNodeAddress",locator);
           stubs.put("LocalLBNodeAddress",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBNodeAddressV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBNodeAddressV2BindingStub LocalLBNodeAddressV2() throws RemoteException, ServiceException {
      log.trace("entering LocalLBNodeAddressV2");
      if(stubs.containsKey("LocalLBNodeAddressV2")){
           log.info("returning LocalLBNodeAddressV2 binding stub from cache.");
           iControl.services.LocalLBNodeAddressV2BindingStub binding = (iControl.services.LocalLBNodeAddressV2BindingStub) stubs.get("LocalLBNodeAddressV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NodeAddressV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBNodeAddressV2 binding stub for "+getUrl());
           iControl.services.LocalLBNodeAddressV2Locator locator = new iControl.services.LocalLBNodeAddressV2Locator(config);
           iControl.services.LocalLBNodeAddressV2BindingStub binding = (iControl.services.LocalLBNodeAddressV2BindingStub) locator.getLocalLBNodeAddressV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/NodeAddressV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBNodeAddressV2 binding stub to cache.");
           services.put("LocalLBNodeAddressV2",locator);
           stubs.put("LocalLBNodeAddressV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBPool iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBPoolBindingStub LocalLBPool() throws RemoteException, ServiceException {
      log.trace("entering LocalLBPool");
      if(stubs.containsKey("LocalLBPool")){
           log.info("returning LocalLBPool binding stub from cache.");
           iControl.services.LocalLBPoolBindingStub binding = (iControl.services.LocalLBPoolBindingStub) stubs.get("LocalLBPool");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Pool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBPool binding stub for "+getUrl());
           iControl.services.LocalLBPoolLocator locator = new iControl.services.LocalLBPoolLocator(config);
           iControl.services.LocalLBPoolBindingStub binding = (iControl.services.LocalLBPoolBindingStub) locator.getLocalLBPoolPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Pool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBPool binding stub to cache.");
           services.put("LocalLBPool",locator);
           stubs.put("LocalLBPool",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBPoolMember iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBPoolMemberBindingStub LocalLBPoolMember() throws RemoteException, ServiceException {
      log.trace("entering LocalLBPoolMember");
      if(stubs.containsKey("LocalLBPoolMember")){
           log.info("returning LocalLBPoolMember binding stub from cache.");
           iControl.services.LocalLBPoolMemberBindingStub binding = (iControl.services.LocalLBPoolMemberBindingStub) stubs.get("LocalLBPoolMember");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/PoolMember", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBPoolMember binding stub for "+getUrl());
           iControl.services.LocalLBPoolMemberLocator locator = new iControl.services.LocalLBPoolMemberLocator(config);
           iControl.services.LocalLBPoolMemberBindingStub binding = (iControl.services.LocalLBPoolMemberBindingStub) locator.getLocalLBPoolMemberPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/PoolMember", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBPoolMember binding stub to cache.");
           services.put("LocalLBPoolMember",locator);
           stubs.put("LocalLBPoolMember",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileAnalytics iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileAnalyticsBindingStub LocalLBProfileAnalytics() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileAnalytics");
      if(stubs.containsKey("LocalLBProfileAnalytics")){
           log.info("returning LocalLBProfileAnalytics binding stub from cache.");
           iControl.services.LocalLBProfileAnalyticsBindingStub binding = (iControl.services.LocalLBProfileAnalyticsBindingStub) stubs.get("LocalLBProfileAnalytics");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileAnalytics", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileAnalytics binding stub for "+getUrl());
           iControl.services.LocalLBProfileAnalyticsLocator locator = new iControl.services.LocalLBProfileAnalyticsLocator(config);
           iControl.services.LocalLBProfileAnalyticsBindingStub binding = (iControl.services.LocalLBProfileAnalyticsBindingStub) locator.getLocalLBProfileAnalyticsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileAnalytics", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileAnalytics binding stub to cache.");
           services.put("LocalLBProfileAnalytics",locator);
           stubs.put("LocalLBProfileAnalytics",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileAuth iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileAuthBindingStub LocalLBProfileAuth() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileAuth");
      if(stubs.containsKey("LocalLBProfileAuth")){
           log.info("returning LocalLBProfileAuth binding stub from cache.");
           iControl.services.LocalLBProfileAuthBindingStub binding = (iControl.services.LocalLBProfileAuthBindingStub) stubs.get("LocalLBProfileAuth");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileAuth", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileAuth binding stub for "+getUrl());
           iControl.services.LocalLBProfileAuthLocator locator = new iControl.services.LocalLBProfileAuthLocator(config);
           iControl.services.LocalLBProfileAuthBindingStub binding = (iControl.services.LocalLBProfileAuthBindingStub) locator.getLocalLBProfileAuthPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileAuth", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileAuth binding stub to cache.");
           services.put("LocalLBProfileAuth",locator);
           stubs.put("LocalLBProfileAuth",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileClientSSL iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileClientSSLBindingStub LocalLBProfileClientSSL() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileClientSSL");
      if(stubs.containsKey("LocalLBProfileClientSSL")){
           log.info("returning LocalLBProfileClientSSL binding stub from cache.");
           iControl.services.LocalLBProfileClientSSLBindingStub binding = (iControl.services.LocalLBProfileClientSSLBindingStub) stubs.get("LocalLBProfileClientSSL");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileClientSSL", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileClientSSL binding stub for "+getUrl());
           iControl.services.LocalLBProfileClientSSLLocator locator = new iControl.services.LocalLBProfileClientSSLLocator(config);
           iControl.services.LocalLBProfileClientSSLBindingStub binding = (iControl.services.LocalLBProfileClientSSLBindingStub) locator.getLocalLBProfileClientSSLPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileClientSSL", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileClientSSL binding stub to cache.");
           services.put("LocalLBProfileClientSSL",locator);
           stubs.put("LocalLBProfileClientSSL",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileDNS iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileDNSBindingStub LocalLBProfileDNS() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileDNS");
      if(stubs.containsKey("LocalLBProfileDNS")){
           log.info("returning LocalLBProfileDNS binding stub from cache.");
           iControl.services.LocalLBProfileDNSBindingStub binding = (iControl.services.LocalLBProfileDNSBindingStub) stubs.get("LocalLBProfileDNS");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileDNS", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileDNS binding stub for "+getUrl());
           iControl.services.LocalLBProfileDNSLocator locator = new iControl.services.LocalLBProfileDNSLocator(config);
           iControl.services.LocalLBProfileDNSBindingStub binding = (iControl.services.LocalLBProfileDNSBindingStub) locator.getLocalLBProfileDNSPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileDNS", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileDNS binding stub to cache.");
           services.put("LocalLBProfileDNS",locator);
           stubs.put("LocalLBProfileDNS",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileDiameter iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileDiameterBindingStub LocalLBProfileDiameter() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileDiameter");
      if(stubs.containsKey("LocalLBProfileDiameter")){
           log.info("returning LocalLBProfileDiameter binding stub from cache.");
           iControl.services.LocalLBProfileDiameterBindingStub binding = (iControl.services.LocalLBProfileDiameterBindingStub) stubs.get("LocalLBProfileDiameter");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileDiameter", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileDiameter binding stub for "+getUrl());
           iControl.services.LocalLBProfileDiameterLocator locator = new iControl.services.LocalLBProfileDiameterLocator(config);
           iControl.services.LocalLBProfileDiameterBindingStub binding = (iControl.services.LocalLBProfileDiameterBindingStub) locator.getLocalLBProfileDiameterPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileDiameter", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileDiameter binding stub to cache.");
           services.put("LocalLBProfileDiameter",locator);
           stubs.put("LocalLBProfileDiameter",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileFTP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileFTPBindingStub LocalLBProfileFTP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileFTP");
      if(stubs.containsKey("LocalLBProfileFTP")){
           log.info("returning LocalLBProfileFTP binding stub from cache.");
           iControl.services.LocalLBProfileFTPBindingStub binding = (iControl.services.LocalLBProfileFTPBindingStub) stubs.get("LocalLBProfileFTP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileFTP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileFTP binding stub for "+getUrl());
           iControl.services.LocalLBProfileFTPLocator locator = new iControl.services.LocalLBProfileFTPLocator(config);
           iControl.services.LocalLBProfileFTPBindingStub binding = (iControl.services.LocalLBProfileFTPBindingStub) locator.getLocalLBProfileFTPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileFTP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileFTP binding stub to cache.");
           services.put("LocalLBProfileFTP",locator);
           stubs.put("LocalLBProfileFTP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileFastHttp iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileFastHttpBindingStub LocalLBProfileFastHttp() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileFastHttp");
      if(stubs.containsKey("LocalLBProfileFastHttp")){
           log.info("returning LocalLBProfileFastHttp binding stub from cache.");
           iControl.services.LocalLBProfileFastHttpBindingStub binding = (iControl.services.LocalLBProfileFastHttpBindingStub) stubs.get("LocalLBProfileFastHttp");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileFastHttp", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileFastHttp binding stub for "+getUrl());
           iControl.services.LocalLBProfileFastHttpLocator locator = new iControl.services.LocalLBProfileFastHttpLocator(config);
           iControl.services.LocalLBProfileFastHttpBindingStub binding = (iControl.services.LocalLBProfileFastHttpBindingStub) locator.getLocalLBProfileFastHttpPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileFastHttp", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileFastHttp binding stub to cache.");
           services.put("LocalLBProfileFastHttp",locator);
           stubs.put("LocalLBProfileFastHttp",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileFastL4 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileFastL4BindingStub LocalLBProfileFastL4() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileFastL4");
      if(stubs.containsKey("LocalLBProfileFastL4")){
           log.info("returning LocalLBProfileFastL4 binding stub from cache.");
           iControl.services.LocalLBProfileFastL4BindingStub binding = (iControl.services.LocalLBProfileFastL4BindingStub) stubs.get("LocalLBProfileFastL4");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileFastL4", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileFastL4 binding stub for "+getUrl());
           iControl.services.LocalLBProfileFastL4Locator locator = new iControl.services.LocalLBProfileFastL4Locator(config);
           iControl.services.LocalLBProfileFastL4BindingStub binding = (iControl.services.LocalLBProfileFastL4BindingStub) locator.getLocalLBProfileFastL4Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileFastL4", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileFastL4 binding stub to cache.");
           services.put("LocalLBProfileFastL4",locator);
           stubs.put("LocalLBProfileFastL4",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileHttp iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileHttpBindingStub LocalLBProfileHttp() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileHttp");
      if(stubs.containsKey("LocalLBProfileHttp")){
           log.info("returning LocalLBProfileHttp binding stub from cache.");
           iControl.services.LocalLBProfileHttpBindingStub binding = (iControl.services.LocalLBProfileHttpBindingStub) stubs.get("LocalLBProfileHttp");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileHttp", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileHttp binding stub for "+getUrl());
           iControl.services.LocalLBProfileHttpLocator locator = new iControl.services.LocalLBProfileHttpLocator(config);
           iControl.services.LocalLBProfileHttpBindingStub binding = (iControl.services.LocalLBProfileHttpBindingStub) locator.getLocalLBProfileHttpPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileHttp", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileHttp binding stub to cache.");
           services.put("LocalLBProfileHttp",locator);
           stubs.put("LocalLBProfileHttp",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileHttpClass iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileHttpClassBindingStub LocalLBProfileHttpClass() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileHttpClass");
      if(stubs.containsKey("LocalLBProfileHttpClass")){
           log.info("returning LocalLBProfileHttpClass binding stub from cache.");
           iControl.services.LocalLBProfileHttpClassBindingStub binding = (iControl.services.LocalLBProfileHttpClassBindingStub) stubs.get("LocalLBProfileHttpClass");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileHttpClass", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileHttpClass binding stub for "+getUrl());
           iControl.services.LocalLBProfileHttpClassLocator locator = new iControl.services.LocalLBProfileHttpClassLocator(config);
           iControl.services.LocalLBProfileHttpClassBindingStub binding = (iControl.services.LocalLBProfileHttpClassBindingStub) locator.getLocalLBProfileHttpClassPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileHttpClass", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileHttpClass binding stub to cache.");
           services.put("LocalLBProfileHttpClass",locator);
           stubs.put("LocalLBProfileHttpClass",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileHttpCompression iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileHttpCompressionBindingStub LocalLBProfileHttpCompression() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileHttpCompression");
      if(stubs.containsKey("LocalLBProfileHttpCompression")){
           log.info("returning LocalLBProfileHttpCompression binding stub from cache.");
           iControl.services.LocalLBProfileHttpCompressionBindingStub binding = (iControl.services.LocalLBProfileHttpCompressionBindingStub) stubs.get("LocalLBProfileHttpCompression");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileHttpCompression", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileHttpCompression binding stub for "+getUrl());
           iControl.services.LocalLBProfileHttpCompressionLocator locator = new iControl.services.LocalLBProfileHttpCompressionLocator(config);
           iControl.services.LocalLBProfileHttpCompressionBindingStub binding = (iControl.services.LocalLBProfileHttpCompressionBindingStub) locator.getLocalLBProfileHttpCompressionPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileHttpCompression", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileHttpCompression binding stub to cache.");
           services.put("LocalLBProfileHttpCompression",locator);
           stubs.put("LocalLBProfileHttpCompression",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileIIOP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileIIOPBindingStub LocalLBProfileIIOP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileIIOP");
      if(stubs.containsKey("LocalLBProfileIIOP")){
           log.info("returning LocalLBProfileIIOP binding stub from cache.");
           iControl.services.LocalLBProfileIIOPBindingStub binding = (iControl.services.LocalLBProfileIIOPBindingStub) stubs.get("LocalLBProfileIIOP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileIIOP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileIIOP binding stub for "+getUrl());
           iControl.services.LocalLBProfileIIOPLocator locator = new iControl.services.LocalLBProfileIIOPLocator(config);
           iControl.services.LocalLBProfileIIOPBindingStub binding = (iControl.services.LocalLBProfileIIOPBindingStub) locator.getLocalLBProfileIIOPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileIIOP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileIIOP binding stub to cache.");
           services.put("LocalLBProfileIIOP",locator);
           stubs.put("LocalLBProfileIIOP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileOneConnect iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileOneConnectBindingStub LocalLBProfileOneConnect() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileOneConnect");
      if(stubs.containsKey("LocalLBProfileOneConnect")){
           log.info("returning LocalLBProfileOneConnect binding stub from cache.");
           iControl.services.LocalLBProfileOneConnectBindingStub binding = (iControl.services.LocalLBProfileOneConnectBindingStub) stubs.get("LocalLBProfileOneConnect");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileOneConnect", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileOneConnect binding stub for "+getUrl());
           iControl.services.LocalLBProfileOneConnectLocator locator = new iControl.services.LocalLBProfileOneConnectLocator(config);
           iControl.services.LocalLBProfileOneConnectBindingStub binding = (iControl.services.LocalLBProfileOneConnectBindingStub) locator.getLocalLBProfileOneConnectPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileOneConnect", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileOneConnect binding stub to cache.");
           services.put("LocalLBProfileOneConnect",locator);
           stubs.put("LocalLBProfileOneConnect",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfilePersistence iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfilePersistenceBindingStub LocalLBProfilePersistence() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfilePersistence");
      if(stubs.containsKey("LocalLBProfilePersistence")){
           log.info("returning LocalLBProfilePersistence binding stub from cache.");
           iControl.services.LocalLBProfilePersistenceBindingStub binding = (iControl.services.LocalLBProfilePersistenceBindingStub) stubs.get("LocalLBProfilePersistence");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfilePersistence", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfilePersistence binding stub for "+getUrl());
           iControl.services.LocalLBProfilePersistenceLocator locator = new iControl.services.LocalLBProfilePersistenceLocator(config);
           iControl.services.LocalLBProfilePersistenceBindingStub binding = (iControl.services.LocalLBProfilePersistenceBindingStub) locator.getLocalLBProfilePersistencePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfilePersistence", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfilePersistence binding stub to cache.");
           services.put("LocalLBProfilePersistence",locator);
           stubs.put("LocalLBProfilePersistence",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileRADIUS iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileRADIUSBindingStub LocalLBProfileRADIUS() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileRADIUS");
      if(stubs.containsKey("LocalLBProfileRADIUS")){
           log.info("returning LocalLBProfileRADIUS binding stub from cache.");
           iControl.services.LocalLBProfileRADIUSBindingStub binding = (iControl.services.LocalLBProfileRADIUSBindingStub) stubs.get("LocalLBProfileRADIUS");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileRADIUS", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileRADIUS binding stub for "+getUrl());
           iControl.services.LocalLBProfileRADIUSLocator locator = new iControl.services.LocalLBProfileRADIUSLocator(config);
           iControl.services.LocalLBProfileRADIUSBindingStub binding = (iControl.services.LocalLBProfileRADIUSBindingStub) locator.getLocalLBProfileRADIUSPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileRADIUS", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileRADIUS binding stub to cache.");
           services.put("LocalLBProfileRADIUS",locator);
           stubs.put("LocalLBProfileRADIUS",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileRTSP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileRTSPBindingStub LocalLBProfileRTSP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileRTSP");
      if(stubs.containsKey("LocalLBProfileRTSP")){
           log.info("returning LocalLBProfileRTSP binding stub from cache.");
           iControl.services.LocalLBProfileRTSPBindingStub binding = (iControl.services.LocalLBProfileRTSPBindingStub) stubs.get("LocalLBProfileRTSP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileRTSP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileRTSP binding stub for "+getUrl());
           iControl.services.LocalLBProfileRTSPLocator locator = new iControl.services.LocalLBProfileRTSPLocator(config);
           iControl.services.LocalLBProfileRTSPBindingStub binding = (iControl.services.LocalLBProfileRTSPBindingStub) locator.getLocalLBProfileRTSPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileRTSP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileRTSP binding stub to cache.");
           services.put("LocalLBProfileRTSP",locator);
           stubs.put("LocalLBProfileRTSP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileRequestLogging iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileRequestLoggingBindingStub LocalLBProfileRequestLogging() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileRequestLogging");
      if(stubs.containsKey("LocalLBProfileRequestLogging")){
           log.info("returning LocalLBProfileRequestLogging binding stub from cache.");
           iControl.services.LocalLBProfileRequestLoggingBindingStub binding = (iControl.services.LocalLBProfileRequestLoggingBindingStub) stubs.get("LocalLBProfileRequestLogging");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileRequestLogging", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileRequestLogging binding stub for "+getUrl());
           iControl.services.LocalLBProfileRequestLoggingLocator locator = new iControl.services.LocalLBProfileRequestLoggingLocator(config);
           iControl.services.LocalLBProfileRequestLoggingBindingStub binding = (iControl.services.LocalLBProfileRequestLoggingBindingStub) locator.getLocalLBProfileRequestLoggingPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileRequestLogging", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileRequestLogging binding stub to cache.");
           services.put("LocalLBProfileRequestLogging",locator);
           stubs.put("LocalLBProfileRequestLogging",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileSCTP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileSCTPBindingStub LocalLBProfileSCTP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileSCTP");
      if(stubs.containsKey("LocalLBProfileSCTP")){
           log.info("returning LocalLBProfileSCTP binding stub from cache.");
           iControl.services.LocalLBProfileSCTPBindingStub binding = (iControl.services.LocalLBProfileSCTPBindingStub) stubs.get("LocalLBProfileSCTP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileSCTP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileSCTP binding stub for "+getUrl());
           iControl.services.LocalLBProfileSCTPLocator locator = new iControl.services.LocalLBProfileSCTPLocator(config);
           iControl.services.LocalLBProfileSCTPBindingStub binding = (iControl.services.LocalLBProfileSCTPBindingStub) locator.getLocalLBProfileSCTPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileSCTP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileSCTP binding stub to cache.");
           services.put("LocalLBProfileSCTP",locator);
           stubs.put("LocalLBProfileSCTP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileSIP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileSIPBindingStub LocalLBProfileSIP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileSIP");
      if(stubs.containsKey("LocalLBProfileSIP")){
           log.info("returning LocalLBProfileSIP binding stub from cache.");
           iControl.services.LocalLBProfileSIPBindingStub binding = (iControl.services.LocalLBProfileSIPBindingStub) stubs.get("LocalLBProfileSIP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileSIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileSIP binding stub for "+getUrl());
           iControl.services.LocalLBProfileSIPLocator locator = new iControl.services.LocalLBProfileSIPLocator(config);
           iControl.services.LocalLBProfileSIPBindingStub binding = (iControl.services.LocalLBProfileSIPBindingStub) locator.getLocalLBProfileSIPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileSIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileSIP binding stub to cache.");
           services.put("LocalLBProfileSIP",locator);
           stubs.put("LocalLBProfileSIP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileServerSSL iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileServerSSLBindingStub LocalLBProfileServerSSL() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileServerSSL");
      if(stubs.containsKey("LocalLBProfileServerSSL")){
           log.info("returning LocalLBProfileServerSSL binding stub from cache.");
           iControl.services.LocalLBProfileServerSSLBindingStub binding = (iControl.services.LocalLBProfileServerSSLBindingStub) stubs.get("LocalLBProfileServerSSL");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileServerSSL", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileServerSSL binding stub for "+getUrl());
           iControl.services.LocalLBProfileServerSSLLocator locator = new iControl.services.LocalLBProfileServerSSLLocator(config);
           iControl.services.LocalLBProfileServerSSLBindingStub binding = (iControl.services.LocalLBProfileServerSSLBindingStub) locator.getLocalLBProfileServerSSLPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileServerSSL", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileServerSSL binding stub to cache.");
           services.put("LocalLBProfileServerSSL",locator);
           stubs.put("LocalLBProfileServerSSL",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileStream iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileStreamBindingStub LocalLBProfileStream() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileStream");
      if(stubs.containsKey("LocalLBProfileStream")){
           log.info("returning LocalLBProfileStream binding stub from cache.");
           iControl.services.LocalLBProfileStreamBindingStub binding = (iControl.services.LocalLBProfileStreamBindingStub) stubs.get("LocalLBProfileStream");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileStream", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileStream binding stub for "+getUrl());
           iControl.services.LocalLBProfileStreamLocator locator = new iControl.services.LocalLBProfileStreamLocator(config);
           iControl.services.LocalLBProfileStreamBindingStub binding = (iControl.services.LocalLBProfileStreamBindingStub) locator.getLocalLBProfileStreamPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileStream", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileStream binding stub to cache.");
           services.put("LocalLBProfileStream",locator);
           stubs.put("LocalLBProfileStream",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileTCP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileTCPBindingStub LocalLBProfileTCP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileTCP");
      if(stubs.containsKey("LocalLBProfileTCP")){
           log.info("returning LocalLBProfileTCP binding stub from cache.");
           iControl.services.LocalLBProfileTCPBindingStub binding = (iControl.services.LocalLBProfileTCPBindingStub) stubs.get("LocalLBProfileTCP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileTCP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileTCP binding stub for "+getUrl());
           iControl.services.LocalLBProfileTCPLocator locator = new iControl.services.LocalLBProfileTCPLocator(config);
           iControl.services.LocalLBProfileTCPBindingStub binding = (iControl.services.LocalLBProfileTCPBindingStub) locator.getLocalLBProfileTCPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileTCP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileTCP binding stub to cache.");
           services.put("LocalLBProfileTCP",locator);
           stubs.put("LocalLBProfileTCP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileUDP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileUDPBindingStub LocalLBProfileUDP() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileUDP");
      if(stubs.containsKey("LocalLBProfileUDP")){
           log.info("returning LocalLBProfileUDP binding stub from cache.");
           iControl.services.LocalLBProfileUDPBindingStub binding = (iControl.services.LocalLBProfileUDPBindingStub) stubs.get("LocalLBProfileUDP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileUDP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileUDP binding stub for "+getUrl());
           iControl.services.LocalLBProfileUDPLocator locator = new iControl.services.LocalLBProfileUDPLocator(config);
           iControl.services.LocalLBProfileUDPBindingStub binding = (iControl.services.LocalLBProfileUDPBindingStub) locator.getLocalLBProfileUDPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileUDP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileUDP binding stub to cache.");
           services.put("LocalLBProfileUDP",locator);
           stubs.put("LocalLBProfileUDP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileUserStatistic iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileUserStatisticBindingStub LocalLBProfileUserStatistic() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileUserStatistic");
      if(stubs.containsKey("LocalLBProfileUserStatistic")){
           log.info("returning LocalLBProfileUserStatistic binding stub from cache.");
           iControl.services.LocalLBProfileUserStatisticBindingStub binding = (iControl.services.LocalLBProfileUserStatisticBindingStub) stubs.get("LocalLBProfileUserStatistic");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileUserStatistic", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileUserStatistic binding stub for "+getUrl());
           iControl.services.LocalLBProfileUserStatisticLocator locator = new iControl.services.LocalLBProfileUserStatisticLocator(config);
           iControl.services.LocalLBProfileUserStatisticBindingStub binding = (iControl.services.LocalLBProfileUserStatisticBindingStub) locator.getLocalLBProfileUserStatisticPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileUserStatistic", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileUserStatistic binding stub to cache.");
           services.put("LocalLBProfileUserStatistic",locator);
           stubs.put("LocalLBProfileUserStatistic",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileWebAcceleration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileWebAccelerationBindingStub LocalLBProfileWebAcceleration() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileWebAcceleration");
      if(stubs.containsKey("LocalLBProfileWebAcceleration")){
           log.info("returning LocalLBProfileWebAcceleration binding stub from cache.");
           iControl.services.LocalLBProfileWebAccelerationBindingStub binding = (iControl.services.LocalLBProfileWebAccelerationBindingStub) stubs.get("LocalLBProfileWebAcceleration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileWebAcceleration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileWebAcceleration binding stub for "+getUrl());
           iControl.services.LocalLBProfileWebAccelerationLocator locator = new iControl.services.LocalLBProfileWebAccelerationLocator(config);
           iControl.services.LocalLBProfileWebAccelerationBindingStub binding = (iControl.services.LocalLBProfileWebAccelerationBindingStub) locator.getLocalLBProfileWebAccelerationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileWebAcceleration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileWebAcceleration binding stub to cache.");
           services.put("LocalLBProfileWebAcceleration",locator);
           stubs.put("LocalLBProfileWebAcceleration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBProfileXML iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBProfileXMLBindingStub LocalLBProfileXML() throws RemoteException, ServiceException {
      log.trace("entering LocalLBProfileXML");
      if(stubs.containsKey("LocalLBProfileXML")){
           log.info("returning LocalLBProfileXML binding stub from cache.");
           iControl.services.LocalLBProfileXMLBindingStub binding = (iControl.services.LocalLBProfileXMLBindingStub) stubs.get("LocalLBProfileXML");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileXML", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBProfileXML binding stub for "+getUrl());
           iControl.services.LocalLBProfileXMLLocator locator = new iControl.services.LocalLBProfileXMLLocator(config);
           iControl.services.LocalLBProfileXMLBindingStub binding = (iControl.services.LocalLBProfileXMLBindingStub) locator.getLocalLBProfileXMLPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/ProfileXML", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBProfileXML binding stub to cache.");
           services.put("LocalLBProfileXML",locator);
           stubs.put("LocalLBProfileXML",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBRAMCacheInformation iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBRAMCacheInformationBindingStub LocalLBRAMCacheInformation() throws RemoteException, ServiceException {
      log.trace("entering LocalLBRAMCacheInformation");
      if(stubs.containsKey("LocalLBRAMCacheInformation")){
           log.info("returning LocalLBRAMCacheInformation binding stub from cache.");
           iControl.services.LocalLBRAMCacheInformationBindingStub binding = (iControl.services.LocalLBRAMCacheInformationBindingStub) stubs.get("LocalLBRAMCacheInformation");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/RAMCacheInformation", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBRAMCacheInformation binding stub for "+getUrl());
           iControl.services.LocalLBRAMCacheInformationLocator locator = new iControl.services.LocalLBRAMCacheInformationLocator(config);
           iControl.services.LocalLBRAMCacheInformationBindingStub binding = (iControl.services.LocalLBRAMCacheInformationBindingStub) locator.getLocalLBRAMCacheInformationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/RAMCacheInformation", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBRAMCacheInformation binding stub to cache.");
           services.put("LocalLBRAMCacheInformation",locator);
           stubs.put("LocalLBRAMCacheInformation",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBRateClass iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBRateClassBindingStub LocalLBRateClass() throws RemoteException, ServiceException {
      log.trace("entering LocalLBRateClass");
      if(stubs.containsKey("LocalLBRateClass")){
           log.info("returning LocalLBRateClass binding stub from cache.");
           iControl.services.LocalLBRateClassBindingStub binding = (iControl.services.LocalLBRateClassBindingStub) stubs.get("LocalLBRateClass");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/RateClass", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBRateClass binding stub for "+getUrl());
           iControl.services.LocalLBRateClassLocator locator = new iControl.services.LocalLBRateClassLocator(config);
           iControl.services.LocalLBRateClassBindingStub binding = (iControl.services.LocalLBRateClassBindingStub) locator.getLocalLBRateClassPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/RateClass", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBRateClass binding stub to cache.");
           services.put("LocalLBRateClass",locator);
           stubs.put("LocalLBRateClass",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBRule iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBRuleBindingStub LocalLBRule() throws RemoteException, ServiceException {
      log.trace("entering LocalLBRule");
      if(stubs.containsKey("LocalLBRule")){
           log.info("returning LocalLBRule binding stub from cache.");
           iControl.services.LocalLBRuleBindingStub binding = (iControl.services.LocalLBRuleBindingStub) stubs.get("LocalLBRule");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Rule", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBRule binding stub for "+getUrl());
           iControl.services.LocalLBRuleLocator locator = new iControl.services.LocalLBRuleLocator(config);
           iControl.services.LocalLBRuleBindingStub binding = (iControl.services.LocalLBRuleBindingStub) locator.getLocalLBRulePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/Rule", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBRule binding stub to cache.");
           services.put("LocalLBRule",locator);
           stubs.put("LocalLBRule",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBSNAT iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBSNATBindingStub LocalLBSNAT() throws RemoteException, ServiceException {
      log.trace("entering LocalLBSNAT");
      if(stubs.containsKey("LocalLBSNAT")){
           log.info("returning LocalLBSNAT binding stub from cache.");
           iControl.services.LocalLBSNATBindingStub binding = (iControl.services.LocalLBSNATBindingStub) stubs.get("LocalLBSNAT");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNAT", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBSNAT binding stub for "+getUrl());
           iControl.services.LocalLBSNATLocator locator = new iControl.services.LocalLBSNATLocator(config);
           iControl.services.LocalLBSNATBindingStub binding = (iControl.services.LocalLBSNATBindingStub) locator.getLocalLBSNATPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNAT", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBSNAT binding stub to cache.");
           services.put("LocalLBSNAT",locator);
           stubs.put("LocalLBSNAT",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBSNATPool iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBSNATPoolBindingStub LocalLBSNATPool() throws RemoteException, ServiceException {
      log.trace("entering LocalLBSNATPool");
      if(stubs.containsKey("LocalLBSNATPool")){
           log.info("returning LocalLBSNATPool binding stub from cache.");
           iControl.services.LocalLBSNATPoolBindingStub binding = (iControl.services.LocalLBSNATPoolBindingStub) stubs.get("LocalLBSNATPool");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATPool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBSNATPool binding stub for "+getUrl());
           iControl.services.LocalLBSNATPoolLocator locator = new iControl.services.LocalLBSNATPoolLocator(config);
           iControl.services.LocalLBSNATPoolBindingStub binding = (iControl.services.LocalLBSNATPoolBindingStub) locator.getLocalLBSNATPoolPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATPool", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBSNATPool binding stub to cache.");
           services.put("LocalLBSNATPool",locator);
           stubs.put("LocalLBSNATPool",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBSNATPoolMember iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBSNATPoolMemberBindingStub LocalLBSNATPoolMember() throws RemoteException, ServiceException {
      log.trace("entering LocalLBSNATPoolMember");
      if(stubs.containsKey("LocalLBSNATPoolMember")){
           log.info("returning LocalLBSNATPoolMember binding stub from cache.");
           iControl.services.LocalLBSNATPoolMemberBindingStub binding = (iControl.services.LocalLBSNATPoolMemberBindingStub) stubs.get("LocalLBSNATPoolMember");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATPoolMember", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBSNATPoolMember binding stub for "+getUrl());
           iControl.services.LocalLBSNATPoolMemberLocator locator = new iControl.services.LocalLBSNATPoolMemberLocator(config);
           iControl.services.LocalLBSNATPoolMemberBindingStub binding = (iControl.services.LocalLBSNATPoolMemberBindingStub) locator.getLocalLBSNATPoolMemberPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATPoolMember", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBSNATPoolMember binding stub to cache.");
           services.put("LocalLBSNATPoolMember",locator);
           stubs.put("LocalLBSNATPoolMember",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBSNATTranslationAddress iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBSNATTranslationAddressBindingStub LocalLBSNATTranslationAddress() throws RemoteException, ServiceException {
      log.trace("entering LocalLBSNATTranslationAddress");
      if(stubs.containsKey("LocalLBSNATTranslationAddress")){
           log.info("returning LocalLBSNATTranslationAddress binding stub from cache.");
           iControl.services.LocalLBSNATTranslationAddressBindingStub binding = (iControl.services.LocalLBSNATTranslationAddressBindingStub) stubs.get("LocalLBSNATTranslationAddress");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATTranslationAddress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBSNATTranslationAddress binding stub for "+getUrl());
           iControl.services.LocalLBSNATTranslationAddressLocator locator = new iControl.services.LocalLBSNATTranslationAddressLocator(config);
           iControl.services.LocalLBSNATTranslationAddressBindingStub binding = (iControl.services.LocalLBSNATTranslationAddressBindingStub) locator.getLocalLBSNATTranslationAddressPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATTranslationAddress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBSNATTranslationAddress binding stub to cache.");
           services.put("LocalLBSNATTranslationAddress",locator);
           stubs.put("LocalLBSNATTranslationAddress",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBSNATTranslationAddressV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBSNATTranslationAddressV2BindingStub LocalLBSNATTranslationAddressV2() throws RemoteException, ServiceException {
      log.trace("entering LocalLBSNATTranslationAddressV2");
      if(stubs.containsKey("LocalLBSNATTranslationAddressV2")){
           log.info("returning LocalLBSNATTranslationAddressV2 binding stub from cache.");
           iControl.services.LocalLBSNATTranslationAddressV2BindingStub binding = (iControl.services.LocalLBSNATTranslationAddressV2BindingStub) stubs.get("LocalLBSNATTranslationAddressV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATTranslationAddressV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBSNATTranslationAddressV2 binding stub for "+getUrl());
           iControl.services.LocalLBSNATTranslationAddressV2Locator locator = new iControl.services.LocalLBSNATTranslationAddressV2Locator(config);
           iControl.services.LocalLBSNATTranslationAddressV2BindingStub binding = (iControl.services.LocalLBSNATTranslationAddressV2BindingStub) locator.getLocalLBSNATTranslationAddressV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/SNATTranslationAddressV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBSNATTranslationAddressV2 binding stub to cache.");
           services.put("LocalLBSNATTranslationAddressV2",locator);
           stubs.put("LocalLBSNATTranslationAddressV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBVirtualAddress iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBVirtualAddressBindingStub LocalLBVirtualAddress() throws RemoteException, ServiceException {
      log.trace("entering LocalLBVirtualAddress");
      if(stubs.containsKey("LocalLBVirtualAddress")){
           log.info("returning LocalLBVirtualAddress binding stub from cache.");
           iControl.services.LocalLBVirtualAddressBindingStub binding = (iControl.services.LocalLBVirtualAddressBindingStub) stubs.get("LocalLBVirtualAddress");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/VirtualAddress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBVirtualAddress binding stub for "+getUrl());
           iControl.services.LocalLBVirtualAddressLocator locator = new iControl.services.LocalLBVirtualAddressLocator(config);
           iControl.services.LocalLBVirtualAddressBindingStub binding = (iControl.services.LocalLBVirtualAddressBindingStub) locator.getLocalLBVirtualAddressPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/VirtualAddress", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBVirtualAddress binding stub to cache.");
           services.put("LocalLBVirtualAddress",locator);
           stubs.put("LocalLBVirtualAddress",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBVirtualAddressV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBVirtualAddressV2BindingStub LocalLBVirtualAddressV2() throws RemoteException, ServiceException {
      log.trace("entering LocalLBVirtualAddressV2");
      if(stubs.containsKey("LocalLBVirtualAddressV2")){
           log.info("returning LocalLBVirtualAddressV2 binding stub from cache.");
           iControl.services.LocalLBVirtualAddressV2BindingStub binding = (iControl.services.LocalLBVirtualAddressV2BindingStub) stubs.get("LocalLBVirtualAddressV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/VirtualAddressV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBVirtualAddressV2 binding stub for "+getUrl());
           iControl.services.LocalLBVirtualAddressV2Locator locator = new iControl.services.LocalLBVirtualAddressV2Locator(config);
           iControl.services.LocalLBVirtualAddressV2BindingStub binding = (iControl.services.LocalLBVirtualAddressV2BindingStub) locator.getLocalLBVirtualAddressV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/VirtualAddressV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBVirtualAddressV2 binding stub to cache.");
           services.put("LocalLBVirtualAddressV2",locator);
           stubs.put("LocalLBVirtualAddressV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBVirtualServer iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBVirtualServerBindingStub LocalLBVirtualServer() throws RemoteException, ServiceException {
      log.trace("entering LocalLBVirtualServer");
      if(stubs.containsKey("LocalLBVirtualServer")){
           log.info("returning LocalLBVirtualServer binding stub from cache.");
           iControl.services.LocalLBVirtualServerBindingStub binding = (iControl.services.LocalLBVirtualServerBindingStub) stubs.get("LocalLBVirtualServer");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/VirtualServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBVirtualServer binding stub for "+getUrl());
           iControl.services.LocalLBVirtualServerLocator locator = new iControl.services.LocalLBVirtualServerLocator(config);
           iControl.services.LocalLBVirtualServerBindingStub binding = (iControl.services.LocalLBVirtualServerBindingStub) locator.getLocalLBVirtualServerPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/VirtualServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBVirtualServer binding stub to cache.");
           services.put("LocalLBVirtualServer",locator);
           stubs.put("LocalLBVirtualServer",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBIFile iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBIFileBindingStub LocalLBIFile() throws RemoteException, ServiceException {
      log.trace("entering LocalLBIFile");
      if(stubs.containsKey("LocalLBIFile")){
           log.info("returning LocalLBIFile binding stub from cache.");
           iControl.services.LocalLBIFileBindingStub binding = (iControl.services.LocalLBIFileBindingStub) stubs.get("LocalLBIFile");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/iFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBIFile binding stub for "+getUrl());
           iControl.services.LocalLBIFileLocator locator = new iControl.services.LocalLBIFileLocator(config);
           iControl.services.LocalLBIFileBindingStub binding = (iControl.services.LocalLBIFileBindingStub) locator.getLocalLBIFilePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/iFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBIFile binding stub to cache.");
           services.put("LocalLBIFile",locator);
           stubs.put("LocalLBIFile",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the LocalLBIFileFile iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.LocalLBIFileFileBindingStub LocalLBIFileFile() throws RemoteException, ServiceException {
      log.trace("entering LocalLBIFileFile");
      if(stubs.containsKey("LocalLBIFileFile")){
           log.info("returning LocalLBIFileFile binding stub from cache.");
           iControl.services.LocalLBIFileFileBindingStub binding = (iControl.services.LocalLBIFileFileBindingStub) stubs.get("LocalLBIFileFile");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/iFileFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating LocalLBIFileFile binding stub for "+getUrl());
           iControl.services.LocalLBIFileFileLocator locator = new iControl.services.LocalLBIFileFileLocator(config);
           iControl.services.LocalLBIFileFileBindingStub binding = (iControl.services.LocalLBIFileFileBindingStub) locator.getLocalLBIFileFilePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:LocalLB/iFileFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding LocalLBIFileFile binding stub to cache.");
           services.put("LocalLBIFileFile",locator);
           stubs.put("LocalLBIFileFile",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementApplicationService iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementApplicationServiceBindingStub ManagementApplicationService() throws RemoteException, ServiceException {
      log.trace("entering ManagementApplicationService");
      if(stubs.containsKey("ManagementApplicationService")){
           log.info("returning ManagementApplicationService binding stub from cache.");
           iControl.services.ManagementApplicationServiceBindingStub binding = (iControl.services.ManagementApplicationServiceBindingStub) stubs.get("ManagementApplicationService");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ApplicationService", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementApplicationService binding stub for "+getUrl());
           iControl.services.ManagementApplicationServiceLocator locator = new iControl.services.ManagementApplicationServiceLocator(config);
           iControl.services.ManagementApplicationServiceBindingStub binding = (iControl.services.ManagementApplicationServiceBindingStub) locator.getManagementApplicationServicePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ApplicationService", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementApplicationService binding stub to cache.");
           services.put("ManagementApplicationService",locator);
           stubs.put("ManagementApplicationService",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementApplicationTemplate iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementApplicationTemplateBindingStub ManagementApplicationTemplate() throws RemoteException, ServiceException {
      log.trace("entering ManagementApplicationTemplate");
      if(stubs.containsKey("ManagementApplicationTemplate")){
           log.info("returning ManagementApplicationTemplate binding stub from cache.");
           iControl.services.ManagementApplicationTemplateBindingStub binding = (iControl.services.ManagementApplicationTemplateBindingStub) stubs.get("ManagementApplicationTemplate");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ApplicationTemplate", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementApplicationTemplate binding stub for "+getUrl());
           iControl.services.ManagementApplicationTemplateLocator locator = new iControl.services.ManagementApplicationTemplateLocator(config);
           iControl.services.ManagementApplicationTemplateBindingStub binding = (iControl.services.ManagementApplicationTemplateBindingStub) locator.getManagementApplicationTemplatePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ApplicationTemplate", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementApplicationTemplate binding stub to cache.");
           services.put("ManagementApplicationTemplate",locator);
           stubs.put("ManagementApplicationTemplate",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementCCLDAPConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementCCLDAPConfigurationBindingStub ManagementCCLDAPConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementCCLDAPConfiguration");
      if(stubs.containsKey("ManagementCCLDAPConfiguration")){
           log.info("returning ManagementCCLDAPConfiguration binding stub from cache.");
           iControl.services.ManagementCCLDAPConfigurationBindingStub binding = (iControl.services.ManagementCCLDAPConfigurationBindingStub) stubs.get("ManagementCCLDAPConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/CCLDAPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementCCLDAPConfiguration binding stub for "+getUrl());
           iControl.services.ManagementCCLDAPConfigurationLocator locator = new iControl.services.ManagementCCLDAPConfigurationLocator(config);
           iControl.services.ManagementCCLDAPConfigurationBindingStub binding = (iControl.services.ManagementCCLDAPConfigurationBindingStub) locator.getManagementCCLDAPConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/CCLDAPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementCCLDAPConfiguration binding stub to cache.");
           services.put("ManagementCCLDAPConfiguration",locator);
           stubs.put("ManagementCCLDAPConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementCRLDPConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementCRLDPConfigurationBindingStub ManagementCRLDPConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementCRLDPConfiguration");
      if(stubs.containsKey("ManagementCRLDPConfiguration")){
           log.info("returning ManagementCRLDPConfiguration binding stub from cache.");
           iControl.services.ManagementCRLDPConfigurationBindingStub binding = (iControl.services.ManagementCRLDPConfigurationBindingStub) stubs.get("ManagementCRLDPConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/CRLDPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementCRLDPConfiguration binding stub for "+getUrl());
           iControl.services.ManagementCRLDPConfigurationLocator locator = new iControl.services.ManagementCRLDPConfigurationLocator(config);
           iControl.services.ManagementCRLDPConfigurationBindingStub binding = (iControl.services.ManagementCRLDPConfigurationBindingStub) locator.getManagementCRLDPConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/CRLDPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementCRLDPConfiguration binding stub to cache.");
           services.put("ManagementCRLDPConfiguration",locator);
           stubs.put("ManagementCRLDPConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementCRLDPServer iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementCRLDPServerBindingStub ManagementCRLDPServer() throws RemoteException, ServiceException {
      log.trace("entering ManagementCRLDPServer");
      if(stubs.containsKey("ManagementCRLDPServer")){
           log.info("returning ManagementCRLDPServer binding stub from cache.");
           iControl.services.ManagementCRLDPServerBindingStub binding = (iControl.services.ManagementCRLDPServerBindingStub) stubs.get("ManagementCRLDPServer");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/CRLDPServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementCRLDPServer binding stub for "+getUrl());
           iControl.services.ManagementCRLDPServerLocator locator = new iControl.services.ManagementCRLDPServerLocator(config);
           iControl.services.ManagementCRLDPServerBindingStub binding = (iControl.services.ManagementCRLDPServerBindingStub) locator.getManagementCRLDPServerPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/CRLDPServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementCRLDPServer binding stub to cache.");
           services.put("ManagementCRLDPServer",locator);
           stubs.put("ManagementCRLDPServer",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementChangeControl iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementChangeControlBindingStub ManagementChangeControl() throws RemoteException, ServiceException {
      log.trace("entering ManagementChangeControl");
      if(stubs.containsKey("ManagementChangeControl")){
           log.info("returning ManagementChangeControl binding stub from cache.");
           iControl.services.ManagementChangeControlBindingStub binding = (iControl.services.ManagementChangeControlBindingStub) stubs.get("ManagementChangeControl");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ChangeControl", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementChangeControl binding stub for "+getUrl());
           iControl.services.ManagementChangeControlLocator locator = new iControl.services.ManagementChangeControlLocator(config);
           iControl.services.ManagementChangeControlBindingStub binding = (iControl.services.ManagementChangeControlBindingStub) locator.getManagementChangeControlPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ChangeControl", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementChangeControl binding stub to cache.");
           services.put("ManagementChangeControl",locator);
           stubs.put("ManagementChangeControl",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementDBVariable iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementDBVariableBindingStub ManagementDBVariable() throws RemoteException, ServiceException {
      log.trace("entering ManagementDBVariable");
      if(stubs.containsKey("ManagementDBVariable")){
           log.info("returning ManagementDBVariable binding stub from cache.");
           iControl.services.ManagementDBVariableBindingStub binding = (iControl.services.ManagementDBVariableBindingStub) stubs.get("ManagementDBVariable");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/DBVariable", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementDBVariable binding stub for "+getUrl());
           iControl.services.ManagementDBVariableLocator locator = new iControl.services.ManagementDBVariableLocator(config);
           iControl.services.ManagementDBVariableBindingStub binding = (iControl.services.ManagementDBVariableBindingStub) locator.getManagementDBVariablePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/DBVariable", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementDBVariable binding stub to cache.");
           services.put("ManagementDBVariable",locator);
           stubs.put("ManagementDBVariable",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementDevice iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementDeviceBindingStub ManagementDevice() throws RemoteException, ServiceException {
      log.trace("entering ManagementDevice");
      if(stubs.containsKey("ManagementDevice")){
           log.info("returning ManagementDevice binding stub from cache.");
           iControl.services.ManagementDeviceBindingStub binding = (iControl.services.ManagementDeviceBindingStub) stubs.get("ManagementDevice");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Device", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementDevice binding stub for "+getUrl());
           iControl.services.ManagementDeviceLocator locator = new iControl.services.ManagementDeviceLocator(config);
           iControl.services.ManagementDeviceBindingStub binding = (iControl.services.ManagementDeviceBindingStub) locator.getManagementDevicePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Device", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementDevice binding stub to cache.");
           services.put("ManagementDevice",locator);
           stubs.put("ManagementDevice",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementDeviceGroup iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementDeviceGroupBindingStub ManagementDeviceGroup() throws RemoteException, ServiceException {
      log.trace("entering ManagementDeviceGroup");
      if(stubs.containsKey("ManagementDeviceGroup")){
           log.info("returning ManagementDeviceGroup binding stub from cache.");
           iControl.services.ManagementDeviceGroupBindingStub binding = (iControl.services.ManagementDeviceGroupBindingStub) stubs.get("ManagementDeviceGroup");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/DeviceGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementDeviceGroup binding stub for "+getUrl());
           iControl.services.ManagementDeviceGroupLocator locator = new iControl.services.ManagementDeviceGroupLocator(config);
           iControl.services.ManagementDeviceGroupBindingStub binding = (iControl.services.ManagementDeviceGroupBindingStub) locator.getManagementDeviceGroupPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/DeviceGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementDeviceGroup binding stub to cache.");
           services.put("ManagementDeviceGroup",locator);
           stubs.put("ManagementDeviceGroup",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementEM iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementEMBindingStub ManagementEM() throws RemoteException, ServiceException {
      log.trace("entering ManagementEM");
      if(stubs.containsKey("ManagementEM")){
           log.info("returning ManagementEM binding stub from cache.");
           iControl.services.ManagementEMBindingStub binding = (iControl.services.ManagementEMBindingStub) stubs.get("ManagementEM");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/EM", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementEM binding stub for "+getUrl());
           iControl.services.ManagementEMLocator locator = new iControl.services.ManagementEMLocator(config);
           iControl.services.ManagementEMBindingStub binding = (iControl.services.ManagementEMBindingStub) locator.getManagementEMPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/EM", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementEM binding stub to cache.");
           services.put("ManagementEM",locator);
           stubs.put("ManagementEM",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementEventNotification iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementEventNotificationBindingStub ManagementEventNotification() throws RemoteException, ServiceException {
      log.trace("entering ManagementEventNotification");
      if(stubs.containsKey("ManagementEventNotification")){
           log.info("returning ManagementEventNotification binding stub from cache.");
           iControl.services.ManagementEventNotificationBindingStub binding = (iControl.services.ManagementEventNotificationBindingStub) stubs.get("ManagementEventNotification");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/EventNotification", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementEventNotification binding stub for "+getUrl());
           iControl.services.ManagementEventNotificationLocator locator = new iControl.services.ManagementEventNotificationLocator(config);
           iControl.services.ManagementEventNotificationBindingStub binding = (iControl.services.ManagementEventNotificationBindingStub) locator.getManagementEventNotificationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/EventNotification", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementEventNotification binding stub to cache.");
           services.put("ManagementEventNotification",locator);
           stubs.put("ManagementEventNotification",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementEventSubscription iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementEventSubscriptionBindingStub ManagementEventSubscription() throws RemoteException, ServiceException {
      log.trace("entering ManagementEventSubscription");
      if(stubs.containsKey("ManagementEventSubscription")){
           log.info("returning ManagementEventSubscription binding stub from cache.");
           iControl.services.ManagementEventSubscriptionBindingStub binding = (iControl.services.ManagementEventSubscriptionBindingStub) stubs.get("ManagementEventSubscription");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/EventSubscription", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementEventSubscription binding stub for "+getUrl());
           iControl.services.ManagementEventSubscriptionLocator locator = new iControl.services.ManagementEventSubscriptionLocator(config);
           iControl.services.ManagementEventSubscriptionBindingStub binding = (iControl.services.ManagementEventSubscriptionBindingStub) locator.getManagementEventSubscriptionPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/EventSubscription", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementEventSubscription binding stub to cache.");
           services.put("ManagementEventSubscription",locator);
           stubs.put("ManagementEventSubscription",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementFolder iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementFolderBindingStub ManagementFolder() throws RemoteException, ServiceException {
      log.trace("entering ManagementFolder");
      if(stubs.containsKey("ManagementFolder")){
           log.info("returning ManagementFolder binding stub from cache.");
           iControl.services.ManagementFolderBindingStub binding = (iControl.services.ManagementFolderBindingStub) stubs.get("ManagementFolder");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Folder", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementFolder binding stub for "+getUrl());
           iControl.services.ManagementFolderLocator locator = new iControl.services.ManagementFolderLocator(config);
           iControl.services.ManagementFolderBindingStub binding = (iControl.services.ManagementFolderBindingStub) locator.getManagementFolderPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Folder", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementFolder binding stub to cache.");
           services.put("ManagementFolder",locator);
           stubs.put("ManagementFolder",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementKeyCertificate iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementKeyCertificateBindingStub ManagementKeyCertificate() throws RemoteException, ServiceException {
      log.trace("entering ManagementKeyCertificate");
      if(stubs.containsKey("ManagementKeyCertificate")){
           log.info("returning ManagementKeyCertificate binding stub from cache.");
           iControl.services.ManagementKeyCertificateBindingStub binding = (iControl.services.ManagementKeyCertificateBindingStub) stubs.get("ManagementKeyCertificate");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/KeyCertificate", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementKeyCertificate binding stub for "+getUrl());
           iControl.services.ManagementKeyCertificateLocator locator = new iControl.services.ManagementKeyCertificateLocator(config);
           iControl.services.ManagementKeyCertificateBindingStub binding = (iControl.services.ManagementKeyCertificateBindingStub) locator.getManagementKeyCertificatePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/KeyCertificate", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementKeyCertificate binding stub to cache.");
           services.put("ManagementKeyCertificate",locator);
           stubs.put("ManagementKeyCertificate",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementLDAPConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementLDAPConfigurationBindingStub ManagementLDAPConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementLDAPConfiguration");
      if(stubs.containsKey("ManagementLDAPConfiguration")){
           log.info("returning ManagementLDAPConfiguration binding stub from cache.");
           iControl.services.ManagementLDAPConfigurationBindingStub binding = (iControl.services.ManagementLDAPConfigurationBindingStub) stubs.get("ManagementLDAPConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/LDAPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementLDAPConfiguration binding stub for "+getUrl());
           iControl.services.ManagementLDAPConfigurationLocator locator = new iControl.services.ManagementLDAPConfigurationLocator(config);
           iControl.services.ManagementLDAPConfigurationBindingStub binding = (iControl.services.ManagementLDAPConfigurationBindingStub) locator.getManagementLDAPConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/LDAPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementLDAPConfiguration binding stub to cache.");
           services.put("ManagementLDAPConfiguration",locator);
           stubs.put("ManagementLDAPConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementLicenseAdministration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementLicenseAdministrationBindingStub ManagementLicenseAdministration() throws RemoteException, ServiceException {
      log.trace("entering ManagementLicenseAdministration");
      if(stubs.containsKey("ManagementLicenseAdministration")){
           log.info("returning ManagementLicenseAdministration binding stub from cache.");
           iControl.services.ManagementLicenseAdministrationBindingStub binding = (iControl.services.ManagementLicenseAdministrationBindingStub) stubs.get("ManagementLicenseAdministration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/LicenseAdministration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementLicenseAdministration binding stub for "+getUrl());
           iControl.services.ManagementLicenseAdministrationLocator locator = new iControl.services.ManagementLicenseAdministrationLocator(config);
           iControl.services.ManagementLicenseAdministrationBindingStub binding = (iControl.services.ManagementLicenseAdministrationBindingStub) locator.getManagementLicenseAdministrationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/LicenseAdministration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementLicenseAdministration binding stub to cache.");
           services.put("ManagementLicenseAdministration",locator);
           stubs.put("ManagementLicenseAdministration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementNamed iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementNamedBindingStub ManagementNamed() throws RemoteException, ServiceException {
      log.trace("entering ManagementNamed");
      if(stubs.containsKey("ManagementNamed")){
           log.info("returning ManagementNamed binding stub from cache.");
           iControl.services.ManagementNamedBindingStub binding = (iControl.services.ManagementNamedBindingStub) stubs.get("ManagementNamed");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Named", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementNamed binding stub for "+getUrl());
           iControl.services.ManagementNamedLocator locator = new iControl.services.ManagementNamedLocator(config);
           iControl.services.ManagementNamedBindingStub binding = (iControl.services.ManagementNamedBindingStub) locator.getManagementNamedPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Named", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementNamed binding stub to cache.");
           services.put("ManagementNamed",locator);
           stubs.put("ManagementNamed",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementOCSPConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementOCSPConfigurationBindingStub ManagementOCSPConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementOCSPConfiguration");
      if(stubs.containsKey("ManagementOCSPConfiguration")){
           log.info("returning ManagementOCSPConfiguration binding stub from cache.");
           iControl.services.ManagementOCSPConfigurationBindingStub binding = (iControl.services.ManagementOCSPConfigurationBindingStub) stubs.get("ManagementOCSPConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/OCSPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementOCSPConfiguration binding stub for "+getUrl());
           iControl.services.ManagementOCSPConfigurationLocator locator = new iControl.services.ManagementOCSPConfigurationLocator(config);
           iControl.services.ManagementOCSPConfigurationBindingStub binding = (iControl.services.ManagementOCSPConfigurationBindingStub) locator.getManagementOCSPConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/OCSPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementOCSPConfiguration binding stub to cache.");
           services.put("ManagementOCSPConfiguration",locator);
           stubs.put("ManagementOCSPConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementOCSPResponder iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementOCSPResponderBindingStub ManagementOCSPResponder() throws RemoteException, ServiceException {
      log.trace("entering ManagementOCSPResponder");
      if(stubs.containsKey("ManagementOCSPResponder")){
           log.info("returning ManagementOCSPResponder binding stub from cache.");
           iControl.services.ManagementOCSPResponderBindingStub binding = (iControl.services.ManagementOCSPResponderBindingStub) stubs.get("ManagementOCSPResponder");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/OCSPResponder", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementOCSPResponder binding stub for "+getUrl());
           iControl.services.ManagementOCSPResponderLocator locator = new iControl.services.ManagementOCSPResponderLocator(config);
           iControl.services.ManagementOCSPResponderBindingStub binding = (iControl.services.ManagementOCSPResponderBindingStub) locator.getManagementOCSPResponderPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/OCSPResponder", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementOCSPResponder binding stub to cache.");
           services.put("ManagementOCSPResponder",locator);
           stubs.put("ManagementOCSPResponder",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementPartition iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementPartitionBindingStub ManagementPartition() throws RemoteException, ServiceException {
      log.trace("entering ManagementPartition");
      if(stubs.containsKey("ManagementPartition")){
           log.info("returning ManagementPartition binding stub from cache.");
           iControl.services.ManagementPartitionBindingStub binding = (iControl.services.ManagementPartitionBindingStub) stubs.get("ManagementPartition");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Partition", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementPartition binding stub for "+getUrl());
           iControl.services.ManagementPartitionLocator locator = new iControl.services.ManagementPartitionLocator(config);
           iControl.services.ManagementPartitionBindingStub binding = (iControl.services.ManagementPartitionBindingStub) locator.getManagementPartitionPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Partition", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementPartition binding stub to cache.");
           services.put("ManagementPartition",locator);
           stubs.put("ManagementPartition",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementProvision iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementProvisionBindingStub ManagementProvision() throws RemoteException, ServiceException {
      log.trace("entering ManagementProvision");
      if(stubs.containsKey("ManagementProvision")){
           log.info("returning ManagementProvision binding stub from cache.");
           iControl.services.ManagementProvisionBindingStub binding = (iControl.services.ManagementProvisionBindingStub) stubs.get("ManagementProvision");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Provision", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementProvision binding stub for "+getUrl());
           iControl.services.ManagementProvisionLocator locator = new iControl.services.ManagementProvisionLocator(config);
           iControl.services.ManagementProvisionBindingStub binding = (iControl.services.ManagementProvisionBindingStub) locator.getManagementProvisionPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Provision", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementProvision binding stub to cache.");
           services.put("ManagementProvision",locator);
           stubs.put("ManagementProvision",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementRADIUSConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementRADIUSConfigurationBindingStub ManagementRADIUSConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementRADIUSConfiguration");
      if(stubs.containsKey("ManagementRADIUSConfiguration")){
           log.info("returning ManagementRADIUSConfiguration binding stub from cache.");
           iControl.services.ManagementRADIUSConfigurationBindingStub binding = (iControl.services.ManagementRADIUSConfigurationBindingStub) stubs.get("ManagementRADIUSConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/RADIUSConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementRADIUSConfiguration binding stub for "+getUrl());
           iControl.services.ManagementRADIUSConfigurationLocator locator = new iControl.services.ManagementRADIUSConfigurationLocator(config);
           iControl.services.ManagementRADIUSConfigurationBindingStub binding = (iControl.services.ManagementRADIUSConfigurationBindingStub) locator.getManagementRADIUSConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/RADIUSConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementRADIUSConfiguration binding stub to cache.");
           services.put("ManagementRADIUSConfiguration",locator);
           stubs.put("ManagementRADIUSConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementRADIUSServer iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementRADIUSServerBindingStub ManagementRADIUSServer() throws RemoteException, ServiceException {
      log.trace("entering ManagementRADIUSServer");
      if(stubs.containsKey("ManagementRADIUSServer")){
           log.info("returning ManagementRADIUSServer binding stub from cache.");
           iControl.services.ManagementRADIUSServerBindingStub binding = (iControl.services.ManagementRADIUSServerBindingStub) stubs.get("ManagementRADIUSServer");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/RADIUSServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementRADIUSServer binding stub for "+getUrl());
           iControl.services.ManagementRADIUSServerLocator locator = new iControl.services.ManagementRADIUSServerLocator(config);
           iControl.services.ManagementRADIUSServerBindingStub binding = (iControl.services.ManagementRADIUSServerBindingStub) locator.getManagementRADIUSServerPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/RADIUSServer", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementRADIUSServer binding stub to cache.");
           services.put("ManagementRADIUSServer",locator);
           stubs.put("ManagementRADIUSServer",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementResourceRecord iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementResourceRecordBindingStub ManagementResourceRecord() throws RemoteException, ServiceException {
      log.trace("entering ManagementResourceRecord");
      if(stubs.containsKey("ManagementResourceRecord")){
           log.info("returning ManagementResourceRecord binding stub from cache.");
           iControl.services.ManagementResourceRecordBindingStub binding = (iControl.services.ManagementResourceRecordBindingStub) stubs.get("ManagementResourceRecord");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ResourceRecord", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementResourceRecord binding stub for "+getUrl());
           iControl.services.ManagementResourceRecordLocator locator = new iControl.services.ManagementResourceRecordLocator(config);
           iControl.services.ManagementResourceRecordBindingStub binding = (iControl.services.ManagementResourceRecordBindingStub) locator.getManagementResourceRecordPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ResourceRecord", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementResourceRecord binding stub to cache.");
           services.put("ManagementResourceRecord",locator);
           stubs.put("ManagementResourceRecord",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementSNMPConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementSNMPConfigurationBindingStub ManagementSNMPConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementSNMPConfiguration");
      if(stubs.containsKey("ManagementSNMPConfiguration")){
           log.info("returning ManagementSNMPConfiguration binding stub from cache.");
           iControl.services.ManagementSNMPConfigurationBindingStub binding = (iControl.services.ManagementSNMPConfigurationBindingStub) stubs.get("ManagementSNMPConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/SNMPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementSNMPConfiguration binding stub for "+getUrl());
           iControl.services.ManagementSNMPConfigurationLocator locator = new iControl.services.ManagementSNMPConfigurationLocator(config);
           iControl.services.ManagementSNMPConfigurationBindingStub binding = (iControl.services.ManagementSNMPConfigurationBindingStub) locator.getManagementSNMPConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/SNMPConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementSNMPConfiguration binding stub to cache.");
           services.put("ManagementSNMPConfiguration",locator);
           stubs.put("ManagementSNMPConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementTACACSConfiguration iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementTACACSConfigurationBindingStub ManagementTACACSConfiguration() throws RemoteException, ServiceException {
      log.trace("entering ManagementTACACSConfiguration");
      if(stubs.containsKey("ManagementTACACSConfiguration")){
           log.info("returning ManagementTACACSConfiguration binding stub from cache.");
           iControl.services.ManagementTACACSConfigurationBindingStub binding = (iControl.services.ManagementTACACSConfigurationBindingStub) stubs.get("ManagementTACACSConfiguration");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/TACACSConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementTACACSConfiguration binding stub for "+getUrl());
           iControl.services.ManagementTACACSConfigurationLocator locator = new iControl.services.ManagementTACACSConfigurationLocator(config);
           iControl.services.ManagementTACACSConfigurationBindingStub binding = (iControl.services.ManagementTACACSConfigurationBindingStub) locator.getManagementTACACSConfigurationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/TACACSConfiguration", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementTACACSConfiguration binding stub to cache.");
           services.put("ManagementTACACSConfiguration",locator);
           stubs.put("ManagementTACACSConfiguration",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementTMOSModule iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementTMOSModuleBindingStub ManagementTMOSModule() throws RemoteException, ServiceException {
      log.trace("entering ManagementTMOSModule");
      if(stubs.containsKey("ManagementTMOSModule")){
           log.info("returning ManagementTMOSModule binding stub from cache.");
           iControl.services.ManagementTMOSModuleBindingStub binding = (iControl.services.ManagementTMOSModuleBindingStub) stubs.get("ManagementTMOSModule");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/TMOSModule", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementTMOSModule binding stub for "+getUrl());
           iControl.services.ManagementTMOSModuleLocator locator = new iControl.services.ManagementTMOSModuleLocator(config);
           iControl.services.ManagementTMOSModuleBindingStub binding = (iControl.services.ManagementTMOSModuleBindingStub) locator.getManagementTMOSModulePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/TMOSModule", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementTMOSModule binding stub to cache.");
           services.put("ManagementTMOSModule",locator);
           stubs.put("ManagementTMOSModule",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementTrafficGroup iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementTrafficGroupBindingStub ManagementTrafficGroup() throws RemoteException, ServiceException {
      log.trace("entering ManagementTrafficGroup");
      if(stubs.containsKey("ManagementTrafficGroup")){
           log.info("returning ManagementTrafficGroup binding stub from cache.");
           iControl.services.ManagementTrafficGroupBindingStub binding = (iControl.services.ManagementTrafficGroupBindingStub) stubs.get("ManagementTrafficGroup");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/TrafficGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementTrafficGroup binding stub for "+getUrl());
           iControl.services.ManagementTrafficGroupLocator locator = new iControl.services.ManagementTrafficGroupLocator(config);
           iControl.services.ManagementTrafficGroupBindingStub binding = (iControl.services.ManagementTrafficGroupBindingStub) locator.getManagementTrafficGroupPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/TrafficGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementTrafficGroup binding stub to cache.");
           services.put("ManagementTrafficGroup",locator);
           stubs.put("ManagementTrafficGroup",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementTrust iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementTrustBindingStub ManagementTrust() throws RemoteException, ServiceException {
      log.trace("entering ManagementTrust");
      if(stubs.containsKey("ManagementTrust")){
           log.info("returning ManagementTrust binding stub from cache.");
           iControl.services.ManagementTrustBindingStub binding = (iControl.services.ManagementTrustBindingStub) stubs.get("ManagementTrust");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Trust", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementTrust binding stub for "+getUrl());
           iControl.services.ManagementTrustLocator locator = new iControl.services.ManagementTrustLocator(config);
           iControl.services.ManagementTrustBindingStub binding = (iControl.services.ManagementTrustBindingStub) locator.getManagementTrustPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Trust", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementTrust binding stub to cache.");
           services.put("ManagementTrust",locator);
           stubs.put("ManagementTrust",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementUserManagement iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementUserManagementBindingStub ManagementUserManagement() throws RemoteException, ServiceException {
      log.trace("entering ManagementUserManagement");
      if(stubs.containsKey("ManagementUserManagement")){
           log.info("returning ManagementUserManagement binding stub from cache.");
           iControl.services.ManagementUserManagementBindingStub binding = (iControl.services.ManagementUserManagementBindingStub) stubs.get("ManagementUserManagement");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/UserManagement", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementUserManagement binding stub for "+getUrl());
           iControl.services.ManagementUserManagementLocator locator = new iControl.services.ManagementUserManagementLocator(config);
           iControl.services.ManagementUserManagementBindingStub binding = (iControl.services.ManagementUserManagementBindingStub) locator.getManagementUserManagementPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/UserManagement", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementUserManagement binding stub to cache.");
           services.put("ManagementUserManagement",locator);
           stubs.put("ManagementUserManagement",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementView iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementViewBindingStub ManagementView() throws RemoteException, ServiceException {
      log.trace("entering ManagementView");
      if(stubs.containsKey("ManagementView")){
           log.info("returning ManagementView binding stub from cache.");
           iControl.services.ManagementViewBindingStub binding = (iControl.services.ManagementViewBindingStub) stubs.get("ManagementView");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/View", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementView binding stub for "+getUrl());
           iControl.services.ManagementViewLocator locator = new iControl.services.ManagementViewLocator(config);
           iControl.services.ManagementViewBindingStub binding = (iControl.services.ManagementViewBindingStub) locator.getManagementViewPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/View", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementView binding stub to cache.");
           services.put("ManagementView",locator);
           stubs.put("ManagementView",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementZone iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementZoneBindingStub ManagementZone() throws RemoteException, ServiceException {
      log.trace("entering ManagementZone");
      if(stubs.containsKey("ManagementZone")){
           log.info("returning ManagementZone binding stub from cache.");
           iControl.services.ManagementZoneBindingStub binding = (iControl.services.ManagementZoneBindingStub) stubs.get("ManagementZone");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Zone", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementZone binding stub for "+getUrl());
           iControl.services.ManagementZoneLocator locator = new iControl.services.ManagementZoneLocator(config);
           iControl.services.ManagementZoneBindingStub binding = (iControl.services.ManagementZoneBindingStub) locator.getManagementZonePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/Zone", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementZone binding stub to cache.");
           services.put("ManagementZone",locator);
           stubs.put("ManagementZone",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the ManagementZoneRunner iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.ManagementZoneRunnerBindingStub ManagementZoneRunner() throws RemoteException, ServiceException {
      log.trace("entering ManagementZoneRunner");
      if(stubs.containsKey("ManagementZoneRunner")){
           log.info("returning ManagementZoneRunner binding stub from cache.");
           iControl.services.ManagementZoneRunnerBindingStub binding = (iControl.services.ManagementZoneRunnerBindingStub) stubs.get("ManagementZoneRunner");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ZoneRunner", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating ManagementZoneRunner binding stub for "+getUrl());
           iControl.services.ManagementZoneRunnerLocator locator = new iControl.services.ManagementZoneRunnerLocator(config);
           iControl.services.ManagementZoneRunnerBindingStub binding = (iControl.services.ManagementZoneRunnerBindingStub) locator.getManagementZoneRunnerPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Management/ZoneRunner", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding ManagementZoneRunner binding stub to cache.");
           services.put("ManagementZoneRunner",locator);
           stubs.put("ManagementZoneRunner",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingARP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingARPBindingStub NetworkingARP() throws RemoteException, ServiceException {
      log.trace("entering NetworkingARP");
      if(stubs.containsKey("NetworkingARP")){
           log.info("returning NetworkingARP binding stub from cache.");
           iControl.services.NetworkingARPBindingStub binding = (iControl.services.NetworkingARPBindingStub) stubs.get("NetworkingARP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ARP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingARP binding stub for "+getUrl());
           iControl.services.NetworkingARPLocator locator = new iControl.services.NetworkingARPLocator(config);
           iControl.services.NetworkingARPBindingStub binding = (iControl.services.NetworkingARPBindingStub) locator.getNetworkingARPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ARP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingARP binding stub to cache.");
           services.put("NetworkingARP",locator);
           stubs.put("NetworkingARP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingAdminIP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingAdminIPBindingStub NetworkingAdminIP() throws RemoteException, ServiceException {
      log.trace("entering NetworkingAdminIP");
      if(stubs.containsKey("NetworkingAdminIP")){
           log.info("returning NetworkingAdminIP binding stub from cache.");
           iControl.services.NetworkingAdminIPBindingStub binding = (iControl.services.NetworkingAdminIPBindingStub) stubs.get("NetworkingAdminIP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/AdminIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingAdminIP binding stub for "+getUrl());
           iControl.services.NetworkingAdminIPLocator locator = new iControl.services.NetworkingAdminIPLocator(config);
           iControl.services.NetworkingAdminIPBindingStub binding = (iControl.services.NetworkingAdminIPBindingStub) locator.getNetworkingAdminIPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/AdminIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingAdminIP binding stub to cache.");
           services.put("NetworkingAdminIP",locator);
           stubs.put("NetworkingAdminIP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingInterfaces iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingInterfacesBindingStub NetworkingInterfaces() throws RemoteException, ServiceException {
      log.trace("entering NetworkingInterfaces");
      if(stubs.containsKey("NetworkingInterfaces")){
           log.info("returning NetworkingInterfaces binding stub from cache.");
           iControl.services.NetworkingInterfacesBindingStub binding = (iControl.services.NetworkingInterfacesBindingStub) stubs.get("NetworkingInterfaces");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/Interfaces", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingInterfaces binding stub for "+getUrl());
           iControl.services.NetworkingInterfacesLocator locator = new iControl.services.NetworkingInterfacesLocator(config);
           iControl.services.NetworkingInterfacesBindingStub binding = (iControl.services.NetworkingInterfacesBindingStub) locator.getNetworkingInterfacesPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/Interfaces", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingInterfaces binding stub to cache.");
           services.put("NetworkingInterfaces",locator);
           stubs.put("NetworkingInterfaces",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingLLDPGlobals iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingLLDPGlobalsBindingStub NetworkingLLDPGlobals() throws RemoteException, ServiceException {
      log.trace("entering NetworkingLLDPGlobals");
      if(stubs.containsKey("NetworkingLLDPGlobals")){
           log.info("returning NetworkingLLDPGlobals binding stub from cache.");
           iControl.services.NetworkingLLDPGlobalsBindingStub binding = (iControl.services.NetworkingLLDPGlobalsBindingStub) stubs.get("NetworkingLLDPGlobals");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/LLDPGlobals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingLLDPGlobals binding stub for "+getUrl());
           iControl.services.NetworkingLLDPGlobalsLocator locator = new iControl.services.NetworkingLLDPGlobalsLocator(config);
           iControl.services.NetworkingLLDPGlobalsBindingStub binding = (iControl.services.NetworkingLLDPGlobalsBindingStub) locator.getNetworkingLLDPGlobalsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/LLDPGlobals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingLLDPGlobals binding stub to cache.");
           services.put("NetworkingLLDPGlobals",locator);
           stubs.put("NetworkingLLDPGlobals",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingPacketFilter iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingPacketFilterBindingStub NetworkingPacketFilter() throws RemoteException, ServiceException {
      log.trace("entering NetworkingPacketFilter");
      if(stubs.containsKey("NetworkingPacketFilter")){
           log.info("returning NetworkingPacketFilter binding stub from cache.");
           iControl.services.NetworkingPacketFilterBindingStub binding = (iControl.services.NetworkingPacketFilterBindingStub) stubs.get("NetworkingPacketFilter");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/PacketFilter", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingPacketFilter binding stub for "+getUrl());
           iControl.services.NetworkingPacketFilterLocator locator = new iControl.services.NetworkingPacketFilterLocator(config);
           iControl.services.NetworkingPacketFilterBindingStub binding = (iControl.services.NetworkingPacketFilterBindingStub) locator.getNetworkingPacketFilterPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/PacketFilter", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingPacketFilter binding stub to cache.");
           services.put("NetworkingPacketFilter",locator);
           stubs.put("NetworkingPacketFilter",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingPacketFilterGlobals iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingPacketFilterGlobalsBindingStub NetworkingPacketFilterGlobals() throws RemoteException, ServiceException {
      log.trace("entering NetworkingPacketFilterGlobals");
      if(stubs.containsKey("NetworkingPacketFilterGlobals")){
           log.info("returning NetworkingPacketFilterGlobals binding stub from cache.");
           iControl.services.NetworkingPacketFilterGlobalsBindingStub binding = (iControl.services.NetworkingPacketFilterGlobalsBindingStub) stubs.get("NetworkingPacketFilterGlobals");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/PacketFilterGlobals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingPacketFilterGlobals binding stub for "+getUrl());
           iControl.services.NetworkingPacketFilterGlobalsLocator locator = new iControl.services.NetworkingPacketFilterGlobalsLocator(config);
           iControl.services.NetworkingPacketFilterGlobalsBindingStub binding = (iControl.services.NetworkingPacketFilterGlobalsBindingStub) locator.getNetworkingPacketFilterGlobalsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/PacketFilterGlobals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingPacketFilterGlobals binding stub to cache.");
           services.put("NetworkingPacketFilterGlobals",locator);
           stubs.put("NetworkingPacketFilterGlobals",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingPortMirror iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingPortMirrorBindingStub NetworkingPortMirror() throws RemoteException, ServiceException {
      log.trace("entering NetworkingPortMirror");
      if(stubs.containsKey("NetworkingPortMirror")){
           log.info("returning NetworkingPortMirror binding stub from cache.");
           iControl.services.NetworkingPortMirrorBindingStub binding = (iControl.services.NetworkingPortMirrorBindingStub) stubs.get("NetworkingPortMirror");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/PortMirror", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingPortMirror binding stub for "+getUrl());
           iControl.services.NetworkingPortMirrorLocator locator = new iControl.services.NetworkingPortMirrorLocator(config);
           iControl.services.NetworkingPortMirrorBindingStub binding = (iControl.services.NetworkingPortMirrorBindingStub) locator.getNetworkingPortMirrorPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/PortMirror", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingPortMirror binding stub to cache.");
           services.put("NetworkingPortMirror",locator);
           stubs.put("NetworkingPortMirror",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingProfileGRE iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingProfileGREBindingStub NetworkingProfileGRE() throws RemoteException, ServiceException {
      log.trace("entering NetworkingProfileGRE");
      if(stubs.containsKey("NetworkingProfileGRE")){
           log.info("returning NetworkingProfileGRE binding stub from cache.");
           iControl.services.NetworkingProfileGREBindingStub binding = (iControl.services.NetworkingProfileGREBindingStub) stubs.get("NetworkingProfileGRE");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ProfileGRE", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingProfileGRE binding stub for "+getUrl());
           iControl.services.NetworkingProfileGRELocator locator = new iControl.services.NetworkingProfileGRELocator(config);
           iControl.services.NetworkingProfileGREBindingStub binding = (iControl.services.NetworkingProfileGREBindingStub) locator.getNetworkingProfileGREPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ProfileGRE", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingProfileGRE binding stub to cache.");
           services.put("NetworkingProfileGRE",locator);
           stubs.put("NetworkingProfileGRE",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingProfileIPIP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingProfileIPIPBindingStub NetworkingProfileIPIP() throws RemoteException, ServiceException {
      log.trace("entering NetworkingProfileIPIP");
      if(stubs.containsKey("NetworkingProfileIPIP")){
           log.info("returning NetworkingProfileIPIP binding stub from cache.");
           iControl.services.NetworkingProfileIPIPBindingStub binding = (iControl.services.NetworkingProfileIPIPBindingStub) stubs.get("NetworkingProfileIPIP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ProfileIPIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingProfileIPIP binding stub for "+getUrl());
           iControl.services.NetworkingProfileIPIPLocator locator = new iControl.services.NetworkingProfileIPIPLocator(config);
           iControl.services.NetworkingProfileIPIPBindingStub binding = (iControl.services.NetworkingProfileIPIPBindingStub) locator.getNetworkingProfileIPIPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ProfileIPIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingProfileIPIP binding stub to cache.");
           services.put("NetworkingProfileIPIP",locator);
           stubs.put("NetworkingProfileIPIP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingProfileWCCPGRE iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingProfileWCCPGREBindingStub NetworkingProfileWCCPGRE() throws RemoteException, ServiceException {
      log.trace("entering NetworkingProfileWCCPGRE");
      if(stubs.containsKey("NetworkingProfileWCCPGRE")){
           log.info("returning NetworkingProfileWCCPGRE binding stub from cache.");
           iControl.services.NetworkingProfileWCCPGREBindingStub binding = (iControl.services.NetworkingProfileWCCPGREBindingStub) stubs.get("NetworkingProfileWCCPGRE");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ProfileWCCPGRE", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingProfileWCCPGRE binding stub for "+getUrl());
           iControl.services.NetworkingProfileWCCPGRELocator locator = new iControl.services.NetworkingProfileWCCPGRELocator(config);
           iControl.services.NetworkingProfileWCCPGREBindingStub binding = (iControl.services.NetworkingProfileWCCPGREBindingStub) locator.getNetworkingProfileWCCPGREPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/ProfileWCCPGRE", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingProfileWCCPGRE binding stub to cache.");
           services.put("NetworkingProfileWCCPGRE",locator);
           stubs.put("NetworkingProfileWCCPGRE",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingRouteDomain iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingRouteDomainBindingStub NetworkingRouteDomain() throws RemoteException, ServiceException {
      log.trace("entering NetworkingRouteDomain");
      if(stubs.containsKey("NetworkingRouteDomain")){
           log.info("returning NetworkingRouteDomain binding stub from cache.");
           iControl.services.NetworkingRouteDomainBindingStub binding = (iControl.services.NetworkingRouteDomainBindingStub) stubs.get("NetworkingRouteDomain");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteDomain", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingRouteDomain binding stub for "+getUrl());
           iControl.services.NetworkingRouteDomainLocator locator = new iControl.services.NetworkingRouteDomainLocator(config);
           iControl.services.NetworkingRouteDomainBindingStub binding = (iControl.services.NetworkingRouteDomainBindingStub) locator.getNetworkingRouteDomainPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteDomain", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingRouteDomain binding stub to cache.");
           services.put("NetworkingRouteDomain",locator);
           stubs.put("NetworkingRouteDomain",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingRouteDomainV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingRouteDomainV2BindingStub NetworkingRouteDomainV2() throws RemoteException, ServiceException {
      log.trace("entering NetworkingRouteDomainV2");
      if(stubs.containsKey("NetworkingRouteDomainV2")){
           log.info("returning NetworkingRouteDomainV2 binding stub from cache.");
           iControl.services.NetworkingRouteDomainV2BindingStub binding = (iControl.services.NetworkingRouteDomainV2BindingStub) stubs.get("NetworkingRouteDomainV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteDomainV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingRouteDomainV2 binding stub for "+getUrl());
           iControl.services.NetworkingRouteDomainV2Locator locator = new iControl.services.NetworkingRouteDomainV2Locator(config);
           iControl.services.NetworkingRouteDomainV2BindingStub binding = (iControl.services.NetworkingRouteDomainV2BindingStub) locator.getNetworkingRouteDomainV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteDomainV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingRouteDomainV2 binding stub to cache.");
           services.put("NetworkingRouteDomainV2",locator);
           stubs.put("NetworkingRouteDomainV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingRouteTable iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingRouteTableBindingStub NetworkingRouteTable() throws RemoteException, ServiceException {
      log.trace("entering NetworkingRouteTable");
      if(stubs.containsKey("NetworkingRouteTable")){
           log.info("returning NetworkingRouteTable binding stub from cache.");
           iControl.services.NetworkingRouteTableBindingStub binding = (iControl.services.NetworkingRouteTableBindingStub) stubs.get("NetworkingRouteTable");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteTable", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingRouteTable binding stub for "+getUrl());
           iControl.services.NetworkingRouteTableLocator locator = new iControl.services.NetworkingRouteTableLocator(config);
           iControl.services.NetworkingRouteTableBindingStub binding = (iControl.services.NetworkingRouteTableBindingStub) locator.getNetworkingRouteTablePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteTable", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingRouteTable binding stub to cache.");
           services.put("NetworkingRouteTable",locator);
           stubs.put("NetworkingRouteTable",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingRouteTableV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingRouteTableV2BindingStub NetworkingRouteTableV2() throws RemoteException, ServiceException {
      log.trace("entering NetworkingRouteTableV2");
      if(stubs.containsKey("NetworkingRouteTableV2")){
           log.info("returning NetworkingRouteTableV2 binding stub from cache.");
           iControl.services.NetworkingRouteTableV2BindingStub binding = (iControl.services.NetworkingRouteTableV2BindingStub) stubs.get("NetworkingRouteTableV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteTableV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingRouteTableV2 binding stub for "+getUrl());
           iControl.services.NetworkingRouteTableV2Locator locator = new iControl.services.NetworkingRouteTableV2Locator(config);
           iControl.services.NetworkingRouteTableV2BindingStub binding = (iControl.services.NetworkingRouteTableV2BindingStub) locator.getNetworkingRouteTableV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/RouteTableV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingRouteTableV2 binding stub to cache.");
           services.put("NetworkingRouteTableV2",locator);
           stubs.put("NetworkingRouteTableV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingSTPGlobals iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingSTPGlobalsBindingStub NetworkingSTPGlobals() throws RemoteException, ServiceException {
      log.trace("entering NetworkingSTPGlobals");
      if(stubs.containsKey("NetworkingSTPGlobals")){
           log.info("returning NetworkingSTPGlobals binding stub from cache.");
           iControl.services.NetworkingSTPGlobalsBindingStub binding = (iControl.services.NetworkingSTPGlobalsBindingStub) stubs.get("NetworkingSTPGlobals");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/STPGlobals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingSTPGlobals binding stub for "+getUrl());
           iControl.services.NetworkingSTPGlobalsLocator locator = new iControl.services.NetworkingSTPGlobalsLocator(config);
           iControl.services.NetworkingSTPGlobalsBindingStub binding = (iControl.services.NetworkingSTPGlobalsBindingStub) locator.getNetworkingSTPGlobalsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/STPGlobals", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingSTPGlobals binding stub to cache.");
           services.put("NetworkingSTPGlobals",locator);
           stubs.put("NetworkingSTPGlobals",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingSTPInstance iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingSTPInstanceBindingStub NetworkingSTPInstance() throws RemoteException, ServiceException {
      log.trace("entering NetworkingSTPInstance");
      if(stubs.containsKey("NetworkingSTPInstance")){
           log.info("returning NetworkingSTPInstance binding stub from cache.");
           iControl.services.NetworkingSTPInstanceBindingStub binding = (iControl.services.NetworkingSTPInstanceBindingStub) stubs.get("NetworkingSTPInstance");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/STPInstance", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingSTPInstance binding stub for "+getUrl());
           iControl.services.NetworkingSTPInstanceLocator locator = new iControl.services.NetworkingSTPInstanceLocator(config);
           iControl.services.NetworkingSTPInstanceBindingStub binding = (iControl.services.NetworkingSTPInstanceBindingStub) locator.getNetworkingSTPInstancePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/STPInstance", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingSTPInstance binding stub to cache.");
           services.put("NetworkingSTPInstance",locator);
           stubs.put("NetworkingSTPInstance",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingSTPInstanceV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingSTPInstanceV2BindingStub NetworkingSTPInstanceV2() throws RemoteException, ServiceException {
      log.trace("entering NetworkingSTPInstanceV2");
      if(stubs.containsKey("NetworkingSTPInstanceV2")){
           log.info("returning NetworkingSTPInstanceV2 binding stub from cache.");
           iControl.services.NetworkingSTPInstanceV2BindingStub binding = (iControl.services.NetworkingSTPInstanceV2BindingStub) stubs.get("NetworkingSTPInstanceV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/STPInstanceV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingSTPInstanceV2 binding stub for "+getUrl());
           iControl.services.NetworkingSTPInstanceV2Locator locator = new iControl.services.NetworkingSTPInstanceV2Locator(config);
           iControl.services.NetworkingSTPInstanceV2BindingStub binding = (iControl.services.NetworkingSTPInstanceV2BindingStub) locator.getNetworkingSTPInstanceV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/STPInstanceV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingSTPInstanceV2 binding stub to cache.");
           services.put("NetworkingSTPInstanceV2",locator);
           stubs.put("NetworkingSTPInstanceV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingSelfIP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingSelfIPBindingStub NetworkingSelfIP() throws RemoteException, ServiceException {
      log.trace("entering NetworkingSelfIP");
      if(stubs.containsKey("NetworkingSelfIP")){
           log.info("returning NetworkingSelfIP binding stub from cache.");
           iControl.services.NetworkingSelfIPBindingStub binding = (iControl.services.NetworkingSelfIPBindingStub) stubs.get("NetworkingSelfIP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/SelfIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingSelfIP binding stub for "+getUrl());
           iControl.services.NetworkingSelfIPLocator locator = new iControl.services.NetworkingSelfIPLocator(config);
           iControl.services.NetworkingSelfIPBindingStub binding = (iControl.services.NetworkingSelfIPBindingStub) locator.getNetworkingSelfIPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/SelfIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingSelfIP binding stub to cache.");
           services.put("NetworkingSelfIP",locator);
           stubs.put("NetworkingSelfIP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingSelfIPPortLockdown iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingSelfIPPortLockdownBindingStub NetworkingSelfIPPortLockdown() throws RemoteException, ServiceException {
      log.trace("entering NetworkingSelfIPPortLockdown");
      if(stubs.containsKey("NetworkingSelfIPPortLockdown")){
           log.info("returning NetworkingSelfIPPortLockdown binding stub from cache.");
           iControl.services.NetworkingSelfIPPortLockdownBindingStub binding = (iControl.services.NetworkingSelfIPPortLockdownBindingStub) stubs.get("NetworkingSelfIPPortLockdown");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/SelfIPPortLockdown", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingSelfIPPortLockdown binding stub for "+getUrl());
           iControl.services.NetworkingSelfIPPortLockdownLocator locator = new iControl.services.NetworkingSelfIPPortLockdownLocator(config);
           iControl.services.NetworkingSelfIPPortLockdownBindingStub binding = (iControl.services.NetworkingSelfIPPortLockdownBindingStub) locator.getNetworkingSelfIPPortLockdownPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/SelfIPPortLockdown", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingSelfIPPortLockdown binding stub to cache.");
           services.put("NetworkingSelfIPPortLockdown",locator);
           stubs.put("NetworkingSelfIPPortLockdown",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingSelfIPV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingSelfIPV2BindingStub NetworkingSelfIPV2() throws RemoteException, ServiceException {
      log.trace("entering NetworkingSelfIPV2");
      if(stubs.containsKey("NetworkingSelfIPV2")){
           log.info("returning NetworkingSelfIPV2 binding stub from cache.");
           iControl.services.NetworkingSelfIPV2BindingStub binding = (iControl.services.NetworkingSelfIPV2BindingStub) stubs.get("NetworkingSelfIPV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/SelfIPV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingSelfIPV2 binding stub for "+getUrl());
           iControl.services.NetworkingSelfIPV2Locator locator = new iControl.services.NetworkingSelfIPV2Locator(config);
           iControl.services.NetworkingSelfIPV2BindingStub binding = (iControl.services.NetworkingSelfIPV2BindingStub) locator.getNetworkingSelfIPV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/SelfIPV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingSelfIPV2 binding stub to cache.");
           services.put("NetworkingSelfIPV2",locator);
           stubs.put("NetworkingSelfIPV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingTrunk iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingTrunkBindingStub NetworkingTrunk() throws RemoteException, ServiceException {
      log.trace("entering NetworkingTrunk");
      if(stubs.containsKey("NetworkingTrunk")){
           log.info("returning NetworkingTrunk binding stub from cache.");
           iControl.services.NetworkingTrunkBindingStub binding = (iControl.services.NetworkingTrunkBindingStub) stubs.get("NetworkingTrunk");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/Trunk", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingTrunk binding stub for "+getUrl());
           iControl.services.NetworkingTrunkLocator locator = new iControl.services.NetworkingTrunkLocator(config);
           iControl.services.NetworkingTrunkBindingStub binding = (iControl.services.NetworkingTrunkBindingStub) locator.getNetworkingTrunkPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/Trunk", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingTrunk binding stub to cache.");
           services.put("NetworkingTrunk",locator);
           stubs.put("NetworkingTrunk",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingTunnel iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingTunnelBindingStub NetworkingTunnel() throws RemoteException, ServiceException {
      log.trace("entering NetworkingTunnel");
      if(stubs.containsKey("NetworkingTunnel")){
           log.info("returning NetworkingTunnel binding stub from cache.");
           iControl.services.NetworkingTunnelBindingStub binding = (iControl.services.NetworkingTunnelBindingStub) stubs.get("NetworkingTunnel");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/Tunnel", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingTunnel binding stub for "+getUrl());
           iControl.services.NetworkingTunnelLocator locator = new iControl.services.NetworkingTunnelLocator(config);
           iControl.services.NetworkingTunnelBindingStub binding = (iControl.services.NetworkingTunnelBindingStub) locator.getNetworkingTunnelPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/Tunnel", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingTunnel binding stub to cache.");
           services.put("NetworkingTunnel",locator);
           stubs.put("NetworkingTunnel",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingVLAN iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingVLANBindingStub NetworkingVLAN() throws RemoteException, ServiceException {
      log.trace("entering NetworkingVLAN");
      if(stubs.containsKey("NetworkingVLAN")){
           log.info("returning NetworkingVLAN binding stub from cache.");
           iControl.services.NetworkingVLANBindingStub binding = (iControl.services.NetworkingVLANBindingStub) stubs.get("NetworkingVLAN");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/VLAN", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingVLAN binding stub for "+getUrl());
           iControl.services.NetworkingVLANLocator locator = new iControl.services.NetworkingVLANLocator(config);
           iControl.services.NetworkingVLANBindingStub binding = (iControl.services.NetworkingVLANBindingStub) locator.getNetworkingVLANPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/VLAN", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingVLAN binding stub to cache.");
           services.put("NetworkingVLAN",locator);
           stubs.put("NetworkingVLAN",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingVLANGroup iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingVLANGroupBindingStub NetworkingVLANGroup() throws RemoteException, ServiceException {
      log.trace("entering NetworkingVLANGroup");
      if(stubs.containsKey("NetworkingVLANGroup")){
           log.info("returning NetworkingVLANGroup binding stub from cache.");
           iControl.services.NetworkingVLANGroupBindingStub binding = (iControl.services.NetworkingVLANGroupBindingStub) stubs.get("NetworkingVLANGroup");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/VLANGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingVLANGroup binding stub for "+getUrl());
           iControl.services.NetworkingVLANGroupLocator locator = new iControl.services.NetworkingVLANGroupLocator(config);
           iControl.services.NetworkingVLANGroupBindingStub binding = (iControl.services.NetworkingVLANGroupBindingStub) locator.getNetworkingVLANGroupPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/VLANGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingVLANGroup binding stub to cache.");
           services.put("NetworkingVLANGroup",locator);
           stubs.put("NetworkingVLANGroup",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionAdvertisedRoute iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionAdvertisedRouteBindingStub NetworkingISessionAdvertisedRoute() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionAdvertisedRoute");
      if(stubs.containsKey("NetworkingISessionAdvertisedRoute")){
           log.info("returning NetworkingISessionAdvertisedRoute binding stub from cache.");
           iControl.services.NetworkingISessionAdvertisedRouteBindingStub binding = (iControl.services.NetworkingISessionAdvertisedRouteBindingStub) stubs.get("NetworkingISessionAdvertisedRoute");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionAdvertisedRoute", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionAdvertisedRoute binding stub for "+getUrl());
           iControl.services.NetworkingISessionAdvertisedRouteLocator locator = new iControl.services.NetworkingISessionAdvertisedRouteLocator(config);
           iControl.services.NetworkingISessionAdvertisedRouteBindingStub binding = (iControl.services.NetworkingISessionAdvertisedRouteBindingStub) locator.getNetworkingISessionAdvertisedRoutePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionAdvertisedRoute", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionAdvertisedRoute binding stub to cache.");
           services.put("NetworkingISessionAdvertisedRoute",locator);
           stubs.put("NetworkingISessionAdvertisedRoute",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionAdvertisedRouteV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionAdvertisedRouteV2BindingStub NetworkingISessionAdvertisedRouteV2() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionAdvertisedRouteV2");
      if(stubs.containsKey("NetworkingISessionAdvertisedRouteV2")){
           log.info("returning NetworkingISessionAdvertisedRouteV2 binding stub from cache.");
           iControl.services.NetworkingISessionAdvertisedRouteV2BindingStub binding = (iControl.services.NetworkingISessionAdvertisedRouteV2BindingStub) stubs.get("NetworkingISessionAdvertisedRouteV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionAdvertisedRouteV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionAdvertisedRouteV2 binding stub for "+getUrl());
           iControl.services.NetworkingISessionAdvertisedRouteV2Locator locator = new iControl.services.NetworkingISessionAdvertisedRouteV2Locator(config);
           iControl.services.NetworkingISessionAdvertisedRouteV2BindingStub binding = (iControl.services.NetworkingISessionAdvertisedRouteV2BindingStub) locator.getNetworkingISessionAdvertisedRouteV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionAdvertisedRouteV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionAdvertisedRouteV2 binding stub to cache.");
           services.put("NetworkingISessionAdvertisedRouteV2",locator);
           stubs.put("NetworkingISessionAdvertisedRouteV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionDatastor iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionDatastorBindingStub NetworkingISessionDatastor() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionDatastor");
      if(stubs.containsKey("NetworkingISessionDatastor")){
           log.info("returning NetworkingISessionDatastor binding stub from cache.");
           iControl.services.NetworkingISessionDatastorBindingStub binding = (iControl.services.NetworkingISessionDatastorBindingStub) stubs.get("NetworkingISessionDatastor");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionDatastor", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionDatastor binding stub for "+getUrl());
           iControl.services.NetworkingISessionDatastorLocator locator = new iControl.services.NetworkingISessionDatastorLocator(config);
           iControl.services.NetworkingISessionDatastorBindingStub binding = (iControl.services.NetworkingISessionDatastorBindingStub) locator.getNetworkingISessionDatastorPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionDatastor", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionDatastor binding stub to cache.");
           services.put("NetworkingISessionDatastor",locator);
           stubs.put("NetworkingISessionDatastor",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionDeduplication iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionDeduplicationBindingStub NetworkingISessionDeduplication() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionDeduplication");
      if(stubs.containsKey("NetworkingISessionDeduplication")){
           log.info("returning NetworkingISessionDeduplication binding stub from cache.");
           iControl.services.NetworkingISessionDeduplicationBindingStub binding = (iControl.services.NetworkingISessionDeduplicationBindingStub) stubs.get("NetworkingISessionDeduplication");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionDeduplication", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionDeduplication binding stub for "+getUrl());
           iControl.services.NetworkingISessionDeduplicationLocator locator = new iControl.services.NetworkingISessionDeduplicationLocator(config);
           iControl.services.NetworkingISessionDeduplicationBindingStub binding = (iControl.services.NetworkingISessionDeduplicationBindingStub) locator.getNetworkingISessionDeduplicationPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionDeduplication", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionDeduplication binding stub to cache.");
           services.put("NetworkingISessionDeduplication",locator);
           stubs.put("NetworkingISessionDeduplication",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionLocalInterface iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionLocalInterfaceBindingStub NetworkingISessionLocalInterface() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionLocalInterface");
      if(stubs.containsKey("NetworkingISessionLocalInterface")){
           log.info("returning NetworkingISessionLocalInterface binding stub from cache.");
           iControl.services.NetworkingISessionLocalInterfaceBindingStub binding = (iControl.services.NetworkingISessionLocalInterfaceBindingStub) stubs.get("NetworkingISessionLocalInterface");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionLocalInterface", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionLocalInterface binding stub for "+getUrl());
           iControl.services.NetworkingISessionLocalInterfaceLocator locator = new iControl.services.NetworkingISessionLocalInterfaceLocator(config);
           iControl.services.NetworkingISessionLocalInterfaceBindingStub binding = (iControl.services.NetworkingISessionLocalInterfaceBindingStub) locator.getNetworkingISessionLocalInterfacePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionLocalInterface", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionLocalInterface binding stub to cache.");
           services.put("NetworkingISessionLocalInterface",locator);
           stubs.put("NetworkingISessionLocalInterface",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionPeerDiscovery iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionPeerDiscoveryBindingStub NetworkingISessionPeerDiscovery() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionPeerDiscovery");
      if(stubs.containsKey("NetworkingISessionPeerDiscovery")){
           log.info("returning NetworkingISessionPeerDiscovery binding stub from cache.");
           iControl.services.NetworkingISessionPeerDiscoveryBindingStub binding = (iControl.services.NetworkingISessionPeerDiscoveryBindingStub) stubs.get("NetworkingISessionPeerDiscovery");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionPeerDiscovery", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionPeerDiscovery binding stub for "+getUrl());
           iControl.services.NetworkingISessionPeerDiscoveryLocator locator = new iControl.services.NetworkingISessionPeerDiscoveryLocator(config);
           iControl.services.NetworkingISessionPeerDiscoveryBindingStub binding = (iControl.services.NetworkingISessionPeerDiscoveryBindingStub) locator.getNetworkingISessionPeerDiscoveryPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionPeerDiscovery", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionPeerDiscovery binding stub to cache.");
           services.put("NetworkingISessionPeerDiscovery",locator);
           stubs.put("NetworkingISessionPeerDiscovery",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionRemoteInterface iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionRemoteInterfaceBindingStub NetworkingISessionRemoteInterface() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionRemoteInterface");
      if(stubs.containsKey("NetworkingISessionRemoteInterface")){
           log.info("returning NetworkingISessionRemoteInterface binding stub from cache.");
           iControl.services.NetworkingISessionRemoteInterfaceBindingStub binding = (iControl.services.NetworkingISessionRemoteInterfaceBindingStub) stubs.get("NetworkingISessionRemoteInterface");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionRemoteInterface", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionRemoteInterface binding stub for "+getUrl());
           iControl.services.NetworkingISessionRemoteInterfaceLocator locator = new iControl.services.NetworkingISessionRemoteInterfaceLocator(config);
           iControl.services.NetworkingISessionRemoteInterfaceBindingStub binding = (iControl.services.NetworkingISessionRemoteInterfaceBindingStub) locator.getNetworkingISessionRemoteInterfacePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionRemoteInterface", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionRemoteInterface binding stub to cache.");
           services.put("NetworkingISessionRemoteInterface",locator);
           stubs.put("NetworkingISessionRemoteInterface",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the NetworkingISessionRemoteInterfaceV2 iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.NetworkingISessionRemoteInterfaceV2BindingStub NetworkingISessionRemoteInterfaceV2() throws RemoteException, ServiceException {
      log.trace("entering NetworkingISessionRemoteInterfaceV2");
      if(stubs.containsKey("NetworkingISessionRemoteInterfaceV2")){
           log.info("returning NetworkingISessionRemoteInterfaceV2 binding stub from cache.");
           iControl.services.NetworkingISessionRemoteInterfaceV2BindingStub binding = (iControl.services.NetworkingISessionRemoteInterfaceV2BindingStub) stubs.get("NetworkingISessionRemoteInterfaceV2");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionRemoteInterfaceV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating NetworkingISessionRemoteInterfaceV2 binding stub for "+getUrl());
           iControl.services.NetworkingISessionRemoteInterfaceV2Locator locator = new iControl.services.NetworkingISessionRemoteInterfaceV2Locator(config);
           iControl.services.NetworkingISessionRemoteInterfaceV2BindingStub binding = (iControl.services.NetworkingISessionRemoteInterfaceV2BindingStub) locator.getNetworkingISessionRemoteInterfaceV2Port(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:Networking/iSessionRemoteInterfaceV2", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding NetworkingISessionRemoteInterfaceV2 binding stub to cache.");
           services.put("NetworkingISessionRemoteInterfaceV2",locator);
           stubs.put("NetworkingISessionRemoteInterfaceV2",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemCertificateRevocationListFile iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemCertificateRevocationListFileBindingStub SystemCertificateRevocationListFile() throws RemoteException, ServiceException {
      log.trace("entering SystemCertificateRevocationListFile");
      if(stubs.containsKey("SystemCertificateRevocationListFile")){
           log.info("returning SystemCertificateRevocationListFile binding stub from cache.");
           iControl.services.SystemCertificateRevocationListFileBindingStub binding = (iControl.services.SystemCertificateRevocationListFileBindingStub) stubs.get("SystemCertificateRevocationListFile");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/CertificateRevocationListFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemCertificateRevocationListFile binding stub for "+getUrl());
           iControl.services.SystemCertificateRevocationListFileLocator locator = new iControl.services.SystemCertificateRevocationListFileLocator(config);
           iControl.services.SystemCertificateRevocationListFileBindingStub binding = (iControl.services.SystemCertificateRevocationListFileBindingStub) locator.getSystemCertificateRevocationListFilePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/CertificateRevocationListFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemCertificateRevocationListFile binding stub to cache.");
           services.put("SystemCertificateRevocationListFile",locator);
           stubs.put("SystemCertificateRevocationListFile",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemCluster iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemClusterBindingStub SystemCluster() throws RemoteException, ServiceException {
      log.trace("entering SystemCluster");
      if(stubs.containsKey("SystemCluster")){
           log.info("returning SystemCluster binding stub from cache.");
           iControl.services.SystemClusterBindingStub binding = (iControl.services.SystemClusterBindingStub) stubs.get("SystemCluster");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Cluster", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemCluster binding stub for "+getUrl());
           iControl.services.SystemClusterLocator locator = new iControl.services.SystemClusterLocator(config);
           iControl.services.SystemClusterBindingStub binding = (iControl.services.SystemClusterBindingStub) locator.getSystemClusterPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Cluster", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemCluster binding stub to cache.");
           services.put("SystemCluster",locator);
           stubs.put("SystemCluster",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemConfigSync iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemConfigSyncBindingStub SystemConfigSync() throws RemoteException, ServiceException {
      log.trace("entering SystemConfigSync");
      if(stubs.containsKey("SystemConfigSync")){
           log.info("returning SystemConfigSync binding stub from cache.");
           iControl.services.SystemConfigSyncBindingStub binding = (iControl.services.SystemConfigSyncBindingStub) stubs.get("SystemConfigSync");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/ConfigSync", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemConfigSync binding stub for "+getUrl());
           iControl.services.SystemConfigSyncLocator locator = new iControl.services.SystemConfigSyncLocator(config);
           iControl.services.SystemConfigSyncBindingStub binding = (iControl.services.SystemConfigSyncBindingStub) locator.getSystemConfigSyncPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/ConfigSync", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemConfigSync binding stub to cache.");
           services.put("SystemConfigSync",locator);
           stubs.put("SystemConfigSync",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemConnections iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemConnectionsBindingStub SystemConnections() throws RemoteException, ServiceException {
      log.trace("entering SystemConnections");
      if(stubs.containsKey("SystemConnections")){
           log.info("returning SystemConnections binding stub from cache.");
           iControl.services.SystemConnectionsBindingStub binding = (iControl.services.SystemConnectionsBindingStub) stubs.get("SystemConnections");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Connections", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemConnections binding stub for "+getUrl());
           iControl.services.SystemConnectionsLocator locator = new iControl.services.SystemConnectionsLocator(config);
           iControl.services.SystemConnectionsBindingStub binding = (iControl.services.SystemConnectionsBindingStub) locator.getSystemConnectionsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Connections", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemConnections binding stub to cache.");
           services.put("SystemConnections",locator);
           stubs.put("SystemConnections",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemDisk iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemDiskBindingStub SystemDisk() throws RemoteException, ServiceException {
      log.trace("entering SystemDisk");
      if(stubs.containsKey("SystemDisk")){
           log.info("returning SystemDisk binding stub from cache.");
           iControl.services.SystemDiskBindingStub binding = (iControl.services.SystemDiskBindingStub) stubs.get("SystemDisk");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Disk", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemDisk binding stub for "+getUrl());
           iControl.services.SystemDiskLocator locator = new iControl.services.SystemDiskLocator(config);
           iControl.services.SystemDiskBindingStub binding = (iControl.services.SystemDiskBindingStub) locator.getSystemDiskPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Disk", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemDisk binding stub to cache.");
           services.put("SystemDisk",locator);
           stubs.put("SystemDisk",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemExternalMonitorFile iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemExternalMonitorFileBindingStub SystemExternalMonitorFile() throws RemoteException, ServiceException {
      log.trace("entering SystemExternalMonitorFile");
      if(stubs.containsKey("SystemExternalMonitorFile")){
           log.info("returning SystemExternalMonitorFile binding stub from cache.");
           iControl.services.SystemExternalMonitorFileBindingStub binding = (iControl.services.SystemExternalMonitorFileBindingStub) stubs.get("SystemExternalMonitorFile");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/ExternalMonitorFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemExternalMonitorFile binding stub for "+getUrl());
           iControl.services.SystemExternalMonitorFileLocator locator = new iControl.services.SystemExternalMonitorFileLocator(config);
           iControl.services.SystemExternalMonitorFileBindingStub binding = (iControl.services.SystemExternalMonitorFileBindingStub) locator.getSystemExternalMonitorFilePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/ExternalMonitorFile", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemExternalMonitorFile binding stub to cache.");
           services.put("SystemExternalMonitorFile",locator);
           stubs.put("SystemExternalMonitorFile",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemFailover iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemFailoverBindingStub SystemFailover() throws RemoteException, ServiceException {
      log.trace("entering SystemFailover");
      if(stubs.containsKey("SystemFailover")){
           log.info("returning SystemFailover binding stub from cache.");
           iControl.services.SystemFailoverBindingStub binding = (iControl.services.SystemFailoverBindingStub) stubs.get("SystemFailover");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Failover", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemFailover binding stub for "+getUrl());
           iControl.services.SystemFailoverLocator locator = new iControl.services.SystemFailoverLocator(config);
           iControl.services.SystemFailoverBindingStub binding = (iControl.services.SystemFailoverBindingStub) locator.getSystemFailoverPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Failover", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemFailover binding stub to cache.");
           services.put("SystemFailover",locator);
           stubs.put("SystemFailover",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemGeoIP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemGeoIPBindingStub SystemGeoIP() throws RemoteException, ServiceException {
      log.trace("entering SystemGeoIP");
      if(stubs.containsKey("SystemGeoIP")){
           log.info("returning SystemGeoIP binding stub from cache.");
           iControl.services.SystemGeoIPBindingStub binding = (iControl.services.SystemGeoIPBindingStub) stubs.get("SystemGeoIP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/GeoIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemGeoIP binding stub for "+getUrl());
           iControl.services.SystemGeoIPLocator locator = new iControl.services.SystemGeoIPLocator(config);
           iControl.services.SystemGeoIPBindingStub binding = (iControl.services.SystemGeoIPBindingStub) locator.getSystemGeoIPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/GeoIP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemGeoIP binding stub to cache.");
           services.put("SystemGeoIP",locator);
           stubs.put("SystemGeoIP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemHAGroup iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemHAGroupBindingStub SystemHAGroup() throws RemoteException, ServiceException {
      log.trace("entering SystemHAGroup");
      if(stubs.containsKey("SystemHAGroup")){
           log.info("returning SystemHAGroup binding stub from cache.");
           iControl.services.SystemHAGroupBindingStub binding = (iControl.services.SystemHAGroupBindingStub) stubs.get("SystemHAGroup");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/HAGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemHAGroup binding stub for "+getUrl());
           iControl.services.SystemHAGroupLocator locator = new iControl.services.SystemHAGroupLocator(config);
           iControl.services.SystemHAGroupBindingStub binding = (iControl.services.SystemHAGroupBindingStub) locator.getSystemHAGroupPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/HAGroup", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemHAGroup binding stub to cache.");
           services.put("SystemHAGroup",locator);
           stubs.put("SystemHAGroup",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemHAStatus iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemHAStatusBindingStub SystemHAStatus() throws RemoteException, ServiceException {
      log.trace("entering SystemHAStatus");
      if(stubs.containsKey("SystemHAStatus")){
           log.info("returning SystemHAStatus binding stub from cache.");
           iControl.services.SystemHAStatusBindingStub binding = (iControl.services.SystemHAStatusBindingStub) stubs.get("SystemHAStatus");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/HAStatus", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemHAStatus binding stub for "+getUrl());
           iControl.services.SystemHAStatusLocator locator = new iControl.services.SystemHAStatusLocator(config);
           iControl.services.SystemHAStatusBindingStub binding = (iControl.services.SystemHAStatusBindingStub) locator.getSystemHAStatusPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/HAStatus", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemHAStatus binding stub to cache.");
           services.put("SystemHAStatus",locator);
           stubs.put("SystemHAStatus",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemInet iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemInetBindingStub SystemInet() throws RemoteException, ServiceException {
      log.trace("entering SystemInet");
      if(stubs.containsKey("SystemInet")){
           log.info("returning SystemInet binding stub from cache.");
           iControl.services.SystemInetBindingStub binding = (iControl.services.SystemInetBindingStub) stubs.get("SystemInet");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Inet", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemInet binding stub for "+getUrl());
           iControl.services.SystemInetLocator locator = new iControl.services.SystemInetLocator(config);
           iControl.services.SystemInetBindingStub binding = (iControl.services.SystemInetBindingStub) locator.getSystemInetPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Inet", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemInet binding stub to cache.");
           services.put("SystemInet",locator);
           stubs.put("SystemInet",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemInternal iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemInternalBindingStub SystemInternal() throws RemoteException, ServiceException {
      log.trace("entering SystemInternal");
      if(stubs.containsKey("SystemInternal")){
           log.info("returning SystemInternal binding stub from cache.");
           iControl.services.SystemInternalBindingStub binding = (iControl.services.SystemInternalBindingStub) stubs.get("SystemInternal");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Internal", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemInternal binding stub for "+getUrl());
           iControl.services.SystemInternalLocator locator = new iControl.services.SystemInternalLocator(config);
           iControl.services.SystemInternalBindingStub binding = (iControl.services.SystemInternalBindingStub) locator.getSystemInternalPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Internal", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemInternal binding stub to cache.");
           services.put("SystemInternal",locator);
           stubs.put("SystemInternal",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemServices iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemServicesBindingStub SystemServices() throws RemoteException, ServiceException {
      log.trace("entering SystemServices");
      if(stubs.containsKey("SystemServices")){
           log.info("returning SystemServices binding stub from cache.");
           iControl.services.SystemServicesBindingStub binding = (iControl.services.SystemServicesBindingStub) stubs.get("SystemServices");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Services", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemServices binding stub for "+getUrl());
           iControl.services.SystemServicesLocator locator = new iControl.services.SystemServicesLocator(config);
           iControl.services.SystemServicesBindingStub binding = (iControl.services.SystemServicesBindingStub) locator.getSystemServicesPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Services", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemServices binding stub to cache.");
           services.put("SystemServices",locator);
           stubs.put("SystemServices",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemSession iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemSessionBindingStub SystemSession() throws RemoteException, ServiceException {
      log.trace("entering SystemSession");
      if(stubs.containsKey("SystemSession")){
           log.info("returning SystemSession binding stub from cache.");
           iControl.services.SystemSessionBindingStub binding = (iControl.services.SystemSessionBindingStub) stubs.get("SystemSession");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Session", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemSession binding stub for "+getUrl());
           iControl.services.SystemSessionLocator locator = new iControl.services.SystemSessionLocator(config);
           iControl.services.SystemSessionBindingStub binding = (iControl.services.SystemSessionBindingStub) locator.getSystemSessionPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Session", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemSession binding stub to cache.");
           services.put("SystemSession",locator);
           stubs.put("SystemSession",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemSoftwareManagement iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemSoftwareManagementBindingStub SystemSoftwareManagement() throws RemoteException, ServiceException {
      log.trace("entering SystemSoftwareManagement");
      if(stubs.containsKey("SystemSoftwareManagement")){
           log.info("returning SystemSoftwareManagement binding stub from cache.");
           iControl.services.SystemSoftwareManagementBindingStub binding = (iControl.services.SystemSoftwareManagementBindingStub) stubs.get("SystemSoftwareManagement");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/SoftwareManagement", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemSoftwareManagement binding stub for "+getUrl());
           iControl.services.SystemSoftwareManagementLocator locator = new iControl.services.SystemSoftwareManagementLocator(config);
           iControl.services.SystemSoftwareManagementBindingStub binding = (iControl.services.SystemSoftwareManagementBindingStub) locator.getSystemSoftwareManagementPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/SoftwareManagement", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemSoftwareManagement binding stub to cache.");
           services.put("SystemSoftwareManagement",locator);
           stubs.put("SystemSoftwareManagement",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemStatistics iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemStatisticsBindingStub SystemStatistics() throws RemoteException, ServiceException {
      log.trace("entering SystemStatistics");
      if(stubs.containsKey("SystemStatistics")){
           log.info("returning SystemStatistics binding stub from cache.");
           iControl.services.SystemStatisticsBindingStub binding = (iControl.services.SystemStatisticsBindingStub) stubs.get("SystemStatistics");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Statistics", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemStatistics binding stub for "+getUrl());
           iControl.services.SystemStatisticsLocator locator = new iControl.services.SystemStatisticsLocator(config);
           iControl.services.SystemStatisticsBindingStub binding = (iControl.services.SystemStatisticsBindingStub) locator.getSystemStatisticsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/Statistics", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemStatistics binding stub to cache.");
           services.put("SystemStatistics",locator);
           stubs.put("SystemStatistics",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemSystemInfo iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemSystemInfoBindingStub SystemSystemInfo() throws RemoteException, ServiceException {
      log.trace("entering SystemSystemInfo");
      if(stubs.containsKey("SystemSystemInfo")){
           log.info("returning SystemSystemInfo binding stub from cache.");
           iControl.services.SystemSystemInfoBindingStub binding = (iControl.services.SystemSystemInfoBindingStub) stubs.get("SystemSystemInfo");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/SystemInfo", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemSystemInfo binding stub for "+getUrl());
           iControl.services.SystemSystemInfoLocator locator = new iControl.services.SystemSystemInfoLocator(config);
           iControl.services.SystemSystemInfoBindingStub binding = (iControl.services.SystemSystemInfoBindingStub) locator.getSystemSystemInfoPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/SystemInfo", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemSystemInfo binding stub to cache.");
           services.put("SystemSystemInfo",locator);
           stubs.put("SystemSystemInfo",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the SystemVCMP iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.SystemVCMPBindingStub SystemVCMP() throws RemoteException, ServiceException {
      log.trace("entering SystemVCMP");
      if(stubs.containsKey("SystemVCMP")){
           log.info("returning SystemVCMP binding stub from cache.");
           iControl.services.SystemVCMPBindingStub binding = (iControl.services.SystemVCMPBindingStub) stubs.get("SystemVCMP");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/VCMP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating SystemVCMP binding stub for "+getUrl());
           iControl.services.SystemVCMPLocator locator = new iControl.services.SystemVCMPLocator(config);
           iControl.services.SystemVCMPBindingStub binding = (iControl.services.SystemVCMPBindingStub) locator.getSystemVCMPPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:System/VCMP", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding SystemVCMP binding stub to cache.");
           services.put("SystemVCMP",locator);
           stubs.put("SystemVCMP",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the WebAcceleratorApplications iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.WebAcceleratorApplicationsBindingStub WebAcceleratorApplications() throws RemoteException, ServiceException {
      log.trace("entering WebAcceleratorApplications");
      if(stubs.containsKey("WebAcceleratorApplications")){
           log.info("returning WebAcceleratorApplications binding stub from cache.");
           iControl.services.WebAcceleratorApplicationsBindingStub binding = (iControl.services.WebAcceleratorApplicationsBindingStub) stubs.get("WebAcceleratorApplications");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:WebAccelerator/Applications", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating WebAcceleratorApplications binding stub for "+getUrl());
           iControl.services.WebAcceleratorApplicationsLocator locator = new iControl.services.WebAcceleratorApplicationsLocator(config);
           iControl.services.WebAcceleratorApplicationsBindingStub binding = (iControl.services.WebAcceleratorApplicationsBindingStub) locator.getWebAcceleratorApplicationsPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:WebAccelerator/Applications", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding WebAcceleratorApplications binding stub to cache.");
           services.put("WebAcceleratorApplications",locator);
           stubs.put("WebAcceleratorApplications",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the WebAcceleratorPolicies iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.WebAcceleratorPoliciesBindingStub WebAcceleratorPolicies() throws RemoteException, ServiceException {
      log.trace("entering WebAcceleratorPolicies");
      if(stubs.containsKey("WebAcceleratorPolicies")){
           log.info("returning WebAcceleratorPolicies binding stub from cache.");
           iControl.services.WebAcceleratorPoliciesBindingStub binding = (iControl.services.WebAcceleratorPoliciesBindingStub) stubs.get("WebAcceleratorPolicies");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:WebAccelerator/Policies", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating WebAcceleratorPolicies binding stub for "+getUrl());
           iControl.services.WebAcceleratorPoliciesLocator locator = new iControl.services.WebAcceleratorPoliciesLocator(config);
           iControl.services.WebAcceleratorPoliciesBindingStub binding = (iControl.services.WebAcceleratorPoliciesBindingStub) locator.getWebAcceleratorPoliciesPort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:WebAccelerator/Policies", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding WebAcceleratorPolicies binding stub to cache.");
           services.put("WebAcceleratorPolicies",locator);
           stubs.put("WebAcceleratorPolicies",binding);
           return binding;
      }

   }
   /**
   * Creates the service interface object for the WebAcceleratorProxyMessage iControl interface.
   *
   * @since     1.0
   */
   public iControl.services.WebAcceleratorProxyMessageBindingStub WebAcceleratorProxyMessage() throws RemoteException, ServiceException {
      log.trace("entering WebAcceleratorProxyMessage");
      if(stubs.containsKey("WebAcceleratorProxyMessage")){
           log.info("returning WebAcceleratorProxyMessage binding stub from cache.");
           iControl.services.WebAcceleratorProxyMessageBindingStub binding = (iControl.services.WebAcceleratorProxyMessageBindingStub) stubs.get("WebAcceleratorProxyMessage");
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:WebAccelerator/ProxyMessage", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           return binding;
      }else{
           log.info("creating WebAcceleratorProxyMessage binding stub for "+getUrl());
           iControl.services.WebAcceleratorProxyMessageLocator locator = new iControl.services.WebAcceleratorProxyMessageLocator(config);
           iControl.services.WebAcceleratorProxyMessageBindingStub binding = (iControl.services.WebAcceleratorProxyMessageBindingStub) locator.getWebAcceleratorProxyMessagePort(getUrl());
           locator.setMaintainSession(true);
           if(session_id >= 0) {
                binding.setHeader("urn:iControl:WebAccelerator/ProxyMessage", "X-iControl-Session", Long.toString(session_id));
                httpHeaders.put("X-iControl-Session", Long.toString(session_id));
           }
           binding.setUsername(username);
           binding.setPassword(password);
           binding._setProperty(org.apache.axis.transport.http.HTTPConstants.REQUEST_HEADERS, httpHeaders);
           log.info("adding WebAcceleratorProxyMessage binding stub to cache.");
           services.put("WebAcceleratorProxyMessage",locator);
           stubs.put("WebAcceleratorProxyMessage",binding);
           return binding;
      }

   }
    /**
    * Starts a session with the BigIP. Insert both HTTP headers and SOAP headers.
    *
    * @since     1.0
    */
   public long getSessionId() throws RemoteException, ServiceException {
      log.trace("entering getSessionId()");
      if(session_id >= 0) {
      	  return session_id;
      }else{
         session_id = SystemSession().get_session_identifier();
         log.trace("adding HTTP header X-iControl-Session:"+session_id);
         httpHeaders.put("X-iControl-Session", Long.toString(session_id));
            return session_id;
      }
   }
    /**
    * Set the current folder context.
    *
    * @since     1.0
    */
   public void setCurrentFolder(String folderName) throws RemoteException, ServiceException {
      if(session_id < 0) {
         getSessionId();
      }
      currentFolder = folderName;
      log.trace("entering setCurrentFolder(folderName)");
      SystemSession().set_active_folder(folderName);
   }
    /**
    * Returns the current folder context.
    *
    * @since     1.0
    */
   public String getCurrentFolder() throws RemoteException, ServiceException {
      log.trace("entering getCurrentFolder()");
      currentFolder =  SystemSession().get_active_folder();
      return currentFolder;
   }
    /**
    * Starts a transaction and returns the session id.
    *
    * @since     1.0
    */
   public long startTransaction() throws RemoteException, ServiceException {
      log.trace("entering startTransaction()");
      if(session_id < 0) {
         getSessionId();
      }
      if(inTransaction) {
         log.error("already in transaction.  Will not start another.");
      } else {
         inTransaction = true;
      }
      return SystemSession().start_transaction();
   }
    /**
    * Attempts to commit all commands within a transaction.
    *
    * @since     1.0
    */
   public void submitTransaction() throws RemoteException, ServiceException {
      log.trace("submitTransaction()");
      if(session_id < 0) {
         getSessionId();
      }
      if(inTransaction) {
          SystemSession().submit_transaction();
          inTransaction=false;
      } else {
          log.error("not in a transaction.  Nothing submit.");
      }
   }
   /**
   * Attempts to roll back commands within a transaction.
   *
   * @since     1.0
   */
   public void rollbackTransaction() throws RemoteException, ServiceException {
      log.trace("entering rollbackTransaction()");
      if(session_id < 0) {
         getSessionId();
      }
      if(inTransaction) {
         SystemSession().rollback_transaction();
         inTransaction=false;
      } else {
          log.error("not in a transaction.  Nothing submit.");
      }
   }

   /**
   * Returns a String array containing all the service interfaces
   * available with this library.
   *
   * @since     1.0
   */
   public String[] getInterfaces() {
          interfaces.add("ASMLoggingProfile");
          interfaces.add("ASMObjectParams");
          interfaces.add("ASMPolicy");
          interfaces.add("ASMSystemConfiguration");
          interfaces.add("ASMWebApplication");
          interfaces.add("ASMWebApplicationGroup");
          interfaces.add("GlobalLBApplication");
          interfaces.add("GlobalLBDNSSECKey");
          interfaces.add("GlobalLBDNSSECZone");
          interfaces.add("GlobalLBDataCenter");
          interfaces.add("GlobalLBGlobals");
          interfaces.add("GlobalLBLink");
          interfaces.add("GlobalLBMonitor");
          interfaces.add("GlobalLBPool");
          interfaces.add("GlobalLBPoolMember");
          interfaces.add("GlobalLBProberPool");
          interfaces.add("GlobalLBRegion");
          interfaces.add("GlobalLBRule");
          interfaces.add("GlobalLBServer");
          interfaces.add("GlobalLBTopology");
          interfaces.add("GlobalLBVirtualServer");
          interfaces.add("GlobalLBVirtualServerV2");
          interfaces.add("GlobalLBWideIP");
          interfaces.add("LTConfigClass");
          interfaces.add("LTConfigField");
          interfaces.add("LocalLBClass");
          interfaces.add("LocalLBDNSExpress");
          interfaces.add("LocalLBDataGroupFile");
          interfaces.add("LocalLBMonitor");
          interfaces.add("LocalLBNAT");
          interfaces.add("LocalLBNATV2");
          interfaces.add("LocalLBNodeAddress");
          interfaces.add("LocalLBNodeAddressV2");
          interfaces.add("LocalLBPool");
          interfaces.add("LocalLBPoolMember");
          interfaces.add("LocalLBProfileAnalytics");
          interfaces.add("LocalLBProfileAuth");
          interfaces.add("LocalLBProfileClientSSL");
          interfaces.add("LocalLBProfileDNS");
          interfaces.add("LocalLBProfileDiameter");
          interfaces.add("LocalLBProfileFTP");
          interfaces.add("LocalLBProfileFastHttp");
          interfaces.add("LocalLBProfileFastL4");
          interfaces.add("LocalLBProfileHttp");
          interfaces.add("LocalLBProfileHttpClass");
          interfaces.add("LocalLBProfileHttpCompression");
          interfaces.add("LocalLBProfileIIOP");
          interfaces.add("LocalLBProfileOneConnect");
          interfaces.add("LocalLBProfilePersistence");
          interfaces.add("LocalLBProfileRADIUS");
          interfaces.add("LocalLBProfileRTSP");
          interfaces.add("LocalLBProfileRequestLogging");
          interfaces.add("LocalLBProfileSCTP");
          interfaces.add("LocalLBProfileSIP");
          interfaces.add("LocalLBProfileServerSSL");
          interfaces.add("LocalLBProfileStream");
          interfaces.add("LocalLBProfileTCP");
          interfaces.add("LocalLBProfileUDP");
          interfaces.add("LocalLBProfileUserStatistic");
          interfaces.add("LocalLBProfileWebAcceleration");
          interfaces.add("LocalLBProfileXML");
          interfaces.add("LocalLBRAMCacheInformation");
          interfaces.add("LocalLBRateClass");
          interfaces.add("LocalLBRule");
          interfaces.add("LocalLBSNAT");
          interfaces.add("LocalLBSNATPool");
          interfaces.add("LocalLBSNATPoolMember");
          interfaces.add("LocalLBSNATTranslationAddress");
          interfaces.add("LocalLBSNATTranslationAddressV2");
          interfaces.add("LocalLBVirtualAddress");
          interfaces.add("LocalLBVirtualAddressV2");
          interfaces.add("LocalLBVirtualServer");
          interfaces.add("LocalLBIFile");
          interfaces.add("LocalLBIFileFile");
          interfaces.add("ManagementApplicationService");
          interfaces.add("ManagementApplicationTemplate");
          interfaces.add("ManagementCCLDAPConfiguration");
          interfaces.add("ManagementCRLDPConfiguration");
          interfaces.add("ManagementCRLDPServer");
          interfaces.add("ManagementChangeControl");
          interfaces.add("ManagementDBVariable");
          interfaces.add("ManagementDevice");
          interfaces.add("ManagementDeviceGroup");
          interfaces.add("ManagementEM");
          interfaces.add("ManagementEventNotification");
          interfaces.add("ManagementEventSubscription");
          interfaces.add("ManagementFolder");
          interfaces.add("ManagementKeyCertificate");
          interfaces.add("ManagementLDAPConfiguration");
          interfaces.add("ManagementLicenseAdministration");
          interfaces.add("ManagementNamed");
          interfaces.add("ManagementOCSPConfiguration");
          interfaces.add("ManagementOCSPResponder");
          interfaces.add("ManagementPartition");
          interfaces.add("ManagementProvision");
          interfaces.add("ManagementRADIUSConfiguration");
          interfaces.add("ManagementRADIUSServer");
          interfaces.add("ManagementResourceRecord");
          interfaces.add("ManagementSNMPConfiguration");
          interfaces.add("ManagementTACACSConfiguration");
          interfaces.add("ManagementTMOSModule");
          interfaces.add("ManagementTrafficGroup");
          interfaces.add("ManagementTrust");
          interfaces.add("ManagementUserManagement");
          interfaces.add("ManagementView");
          interfaces.add("ManagementZone");
          interfaces.add("ManagementZoneRunner");
          interfaces.add("NetworkingARP");
          interfaces.add("NetworkingAdminIP");
          interfaces.add("NetworkingInterfaces");
          interfaces.add("NetworkingLLDPGlobals");
          interfaces.add("NetworkingPacketFilter");
          interfaces.add("NetworkingPacketFilterGlobals");
          interfaces.add("NetworkingPortMirror");
          interfaces.add("NetworkingProfileGRE");
          interfaces.add("NetworkingProfileIPIP");
          interfaces.add("NetworkingProfileWCCPGRE");
          interfaces.add("NetworkingRouteDomain");
          interfaces.add("NetworkingRouteDomainV2");
          interfaces.add("NetworkingRouteTable");
          interfaces.add("NetworkingRouteTableV2");
          interfaces.add("NetworkingSTPGlobals");
          interfaces.add("NetworkingSTPInstance");
          interfaces.add("NetworkingSTPInstanceV2");
          interfaces.add("NetworkingSelfIP");
          interfaces.add("NetworkingSelfIPPortLockdown");
          interfaces.add("NetworkingSelfIPV2");
          interfaces.add("NetworkingTrunk");
          interfaces.add("NetworkingTunnel");
          interfaces.add("NetworkingVLAN");
          interfaces.add("NetworkingVLANGroup");
          interfaces.add("NetworkingISessionAdvertisedRoute");
          interfaces.add("NetworkingISessionAdvertisedRouteV2");
          interfaces.add("NetworkingISessionDatastor");
          interfaces.add("NetworkingISessionDeduplication");
          interfaces.add("NetworkingISessionLocalInterface");
          interfaces.add("NetworkingISessionPeerDiscovery");
          interfaces.add("NetworkingISessionRemoteInterface");
          interfaces.add("NetworkingISessionRemoteInterfaceV2");
          interfaces.add("SystemCertificateRevocationListFile");
          interfaces.add("SystemCluster");
          interfaces.add("SystemConfigSync");
          interfaces.add("SystemConnections");
          interfaces.add("SystemDisk");
          interfaces.add("SystemExternalMonitorFile");
          interfaces.add("SystemFailover");
          interfaces.add("SystemGeoIP");
          interfaces.add("SystemHAGroup");
          interfaces.add("SystemHAStatus");
          interfaces.add("SystemInet");
          interfaces.add("SystemInternal");
          interfaces.add("SystemServices");
          interfaces.add("SystemSession");
          interfaces.add("SystemSoftwareManagement");
          interfaces.add("SystemStatistics");
          interfaces.add("SystemSystemInfo");
          interfaces.add("SystemVCMP");
          interfaces.add("WebAcceleratorApplications");
          interfaces.add("WebAcceleratorPolicies");
          interfaces.add("WebAcceleratorProxyMessage");
          return (String[]) interfaces.toArray();
   }

    /**
    * Get the version of the BigIP or return error in attempting to.
    *
    * @since     1.0
    */
    public String ping() {
   	      log.trace("entering ping()");
   	      try {
   	    	  return SystemSystemInfo().get_version();
   	      } catch (Exception e) {
   	    	  log.error("can not ping because "+e.getMessage());
   	    	  return "ping failed because "+e.getMessage();
   	      }
      }
}
