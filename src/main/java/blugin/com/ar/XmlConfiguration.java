package blugin.com.ar;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import javax.xml.parsers.DocumentBuilderFactory;

@Startup
@ApplicationScoped
public class XmlConfiguration {

    @PostConstruct
    public void init() {
        //System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
        //                   "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

        //
        System.out.println("============== DocumentBuilderFactory configurado a Xerces ==============");
        DocumentBuilderFactory doc = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        System.out.println("============== DocumentBuilderFactory instnaciado a "+ doc.getClass().getName()+"==============");

    }
}