package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.2
 * 2015-10-13T14:16:26.631+08:00
 * Generated source version: 3.1.2
 * 
 */
@WebServiceClient(name = "WRService", 
                  wsdlLocation = "http://quyu.safefood.gov.cn/sgnz/ws/WRService.asmx?WSDL",
                  targetNamespace = "http://tempuri.org/") 
public class WRService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://tempuri.org/", "WRService");
    public final static QName WRServiceHttpPost = new QName("http://tempuri.org/", "WRServiceHttpPost");
    public final static QName WRServiceSoap = new QName("http://tempuri.org/", "WRServiceSoap");
    public final static QName WRServiceSoap12 = new QName("http://tempuri.org/", "WRServiceSoap12");
    public final static QName WRServiceHttpGet = new QName("http://tempuri.org/", "WRServiceHttpGet");
    static {
        URL url = null;
        try {
            url = new URL("http://quyu.safefood.gov.cn/sgnz/ws/WRService.asmx?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(WRService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://quyu.safefood.gov.cn/sgnz/ws/WRService.asmx?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public WRService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public WRService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WRService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public WRService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public WRService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public WRService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns WRServiceHttpPost
     */
    @WebEndpoint(name = "WRServiceHttpPost")
    public WRServiceHttpPost getWRServiceHttpPost() {
        return super.getPort(WRServiceHttpPost, WRServiceHttpPost.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WRServiceHttpPost
     */
    @WebEndpoint(name = "WRServiceHttpPost")
    public WRServiceHttpPost getWRServiceHttpPost(WebServiceFeature... features) {
        return super.getPort(WRServiceHttpPost, WRServiceHttpPost.class, features);
    }


    /**
     *
     * @return
     *     returns WRServiceSoap
     */
    @WebEndpoint(name = "WRServiceSoap")
    public WRServiceSoap getWRServiceSoap() {
        return super.getPort(WRServiceSoap, WRServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WRServiceSoap
     */
    @WebEndpoint(name = "WRServiceSoap")
    public WRServiceSoap getWRServiceSoap(WebServiceFeature... features) {
        return super.getPort(WRServiceSoap, WRServiceSoap.class, features);
    }


    /**
     *
     * @return
     *     returns WRServiceSoap
     */
    @WebEndpoint(name = "WRServiceSoap12")
    public WRServiceSoap getWRServiceSoap12() {
        return super.getPort(WRServiceSoap12, WRServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WRServiceSoap
     */
    @WebEndpoint(name = "WRServiceSoap12")
    public WRServiceSoap getWRServiceSoap12(WebServiceFeature... features) {
        return super.getPort(WRServiceSoap12, WRServiceSoap.class, features);
    }


    /**
     *
     * @return
     *     returns WRServiceHttpGet
     */
    @WebEndpoint(name = "WRServiceHttpGet")
    public WRServiceHttpGet getWRServiceHttpGet() {
        return super.getPort(WRServiceHttpGet, WRServiceHttpGet.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WRServiceHttpGet
     */
    @WebEndpoint(name = "WRServiceHttpGet")
    public WRServiceHttpGet getWRServiceHttpGet(WebServiceFeature... features) {
        return super.getPort(WRServiceHttpGet, WRServiceHttpGet.class, features);
    }

}
