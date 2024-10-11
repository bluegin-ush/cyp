package blugin.com.ar.fe;

import jakarta.xml.soap.*;
import jakarta.xml.ws.soap.SOAPFaultException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WSAAClient {

    public static Map<String,String> getTicketAuthorization(String endpoint, String signedXML) throws Exception {

        // SOAP factory
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // SOAP message
        String soapEnvelope = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsaa=\"http://wsaa.view.sua.dvadac.desein.afip.gov\">\n" +
                "   <soapenv:Body>\n" +
                "      <wsaa:loginCms>\n" +
                "         <wsaa:in0>"+signedXML+"</wsaa:in0>\n" +
                "      </wsaa:loginCms>" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        // Create the SOAPMessage
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Set the SOAPEnvelope content
        soapPart.setContent(new StreamSource(new StringReader(soapEnvelope)));

        // Agregar la cabecera SOAPAction
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", "");

        mostrarMensaje(soapMessage);

        // SOAP call
        SOAPMessage soapResponse = soapConnection.call(soapMessage, new URL(endpoint));


        // Response
        SOAPBody responseBody = soapResponse.getSOAPBody();

        //
        String token = "";
        String sign = "";

        // Check Fault
        if(responseBody.getFault() != null){

            System.out.println("Fault code: "+responseBody.getFault().getFaultCode());
            System.out.println("Fault message: "+responseBody.getFault().getTextContent());

            //check error code
            throw new SOAPFaultException(responseBody.getFault());

        } else {

            String xml = responseBody.getTextContent();

            token = extractValue(xml, "token");
            sign = extractValue(xml, "sign");

            System.out.println(responseBody.getTextContent());

            //
            soapConnection.close();

        }

        //
        Map auth = new HashMap();

        //
        auth.put("token", token);
        auth.put("sign", sign);

        //
        return auth;
    }

    public static String extractValue(String xml, String tagName) {

        String patternString = "<" + tagName + ">(.*?)</" + tagName + ">";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(xml);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static void mostrarMensaje(SOAPMessage soapMessage) {
        try {
            //
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            //
            StringWriter stringWriter = new StringWriter();

            //
            transformer.transform(new DOMSource(soapMessage.getSOAPPart()), new StreamResult(stringWriter));

            //
            String soapMessageString = stringWriter.toString();

            //
            System.out.println(soapMessageString);

        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}