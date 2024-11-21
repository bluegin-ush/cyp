package blugin.com.ar.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.wildfly.common.xml.DocumentBuilderFactoryUtil;
import javax.xml.parsers.DocumentBuilderFactory;
*/
public class CifradoSHA256 {


    public static String cifrarSHA256(String input) {
        try {
            // Obtener instancia de SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            
            // Convertir los bytes a formato hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String password = "123456";
        String hashedPassword = cifrarSHA256(password);
        System.out.println("Clave cifrada con SHA-256: " + hashedPassword);
        
        // Este hashedPassword lo puedes usar en tu import.sql
    }
}
