package blugin.com.ar.fe;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

public class SignXML {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String getLoginTicketRequestSigned(String certPath, String keyPath, String loginTicketRequest) throws Exception {
        /*String certPath = "certificados/afip-cyp.pem";
        String keyPath = "certificados/clave-prueba";
        String xmlPath = "certificados/LoginTicketRequest.xml";
        String outputPath = "TEST-LoginTicketRequest.xml.cms";
        */

        // Cargar el certificado
        X509Certificate cert = (X509Certificate) loadCertificate(certPath);

        // Cargar la clave privada
        PrivateKey privateKey = loadPrivateKey(keyPath);

        // Leer el XML
        //byte[] xmlData = loadTicket(xmlPath);loginTicketRequest
        byte[] xmlData = loginTicketRequest.getBytes();

        // Crear la firma digital
        byte[] signedData = signData(xmlData, privateKey, cert);

        // Codificar en Base64
        String xmlCms = Base64.getEncoder().encodeToString(signedData);
        System.out.println("LoginTicketRequest.xml.cms: "+xmlCms);

        return xmlCms;

    }

    public static String getLoginTicketRequest(String dn, String service, Duration expirationTime) throws Exception {
        // Construir el cuerpo del mensaje SOAP
        //String dn = "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 20290833869"; // Reemplaza con tu CUIT
        //String dn = "SERIALNUMBER=CUIT 20290833869, CN=cyp";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String uniqueId = Long.toString(System.currentTimeMillis() / 1000L);

        String xml = "<loginTicketRequest version=\"1.0\">" +
                "<header>" +
                //"<source>"+dn+"</source>" +
                //"<destination>cn=wsaa,o=afip,c=ar,serialNumber=CUIT 33693450239</destination>" +
                "<uniqueId>" + uniqueId + "</uniqueId>" +
                "<generationTime>" + sdf.format(new Date(System.currentTimeMillis())) + "</generationTime>" +

                "<expirationTime>" + sdf.format(new Date(System.currentTimeMillis()+ expirationTime.toMillis())) + "</expirationTime>" +
                "</header>" +
                "<service>" + service + "</service>" +
                "</loginTicketRequest>";

        // LoginTicketRequest.xml
        System.out.println("LoginTicketRequest.xml: \n"+xml);
        return xml;
    }
    private static byte[] loadTicket(String xmlPath) throws Exception {
        try(InputStream requestStream = SignXML.class.getClassLoader().getResourceAsStream(xmlPath)) {
            String xml = new String(requestStream.readAllBytes(), StandardCharsets.UTF_8);
            return xml.getBytes();
        }
    }

    private static Certificate loadCertificate(String certPath) throws Exception {
        try ( InputStream in = SignXML.class.getClassLoader().getResourceAsStream(certPath)) {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            return factory.generateCertificate(in);
        }
    }

    private static PrivateKey loadPrivateKey(String keyPath) throws Exception {

        InputStream in = SignXML.class.getClassLoader().getResourceAsStream(keyPath);
        try (PEMParser pemParser = new PEMParser(new PemReader(new InputStreamReader(in, StandardCharsets.UTF_8)))){
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

            if (object instanceof PEMKeyPair) {
                // Caso en que el archivo PEM contiene un par de claves
                return converter.getKeyPair((PEMKeyPair) object).getPrivate();
            } else if (object instanceof PrivateKeyInfo) {
                // Caso en que el archivo PEM contiene solo la clave privada
                return converter.getPrivateKey((PrivateKeyInfo) object);
            } else {
                throw new IllegalArgumentException("Tipo inesperado en el archivo PEM");
            }

        }
    }

    private static byte[] signData(byte[] data, PrivateKey privateKey, X509Certificate cert) throws Exception {
        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                .build(privateKey);

        // Convertir el X509Certificate de Java a X509CertificateHolder de BouncyCastle
        JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder(cert);

        generator.addSignerInfoGenerator(
                new org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder(
                        new JcaDigestCalculatorProviderBuilder().setProvider(BouncyCastleProvider.PROVIDER_NAME).build())
                        .build(signer, certHolder));

        generator.addCertificates(new JcaCertStore(Collections.singletonList(certHolder)));

        CMSProcessableByteArray content = new CMSProcessableByteArray(data);
        CMSSignedData signedData = generator.generate(content, true);

        return signedData.getEncoded();
    }

    private static void saveToFile(String data, String path) throws IOException {
        Files.write(Paths.get(path), data.getBytes());
    }
}
