package blugin.com.ar.fe;

import blugin.com.ar.cyp.model.Configuracion;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@ApplicationScoped
public class AuthTokenAndSign {

    private static final String TOKEN_KEY = "token";
    private static final String SIGN_KEY = "sign";
    private static final String TOKEN_TIMESTAMP_KEY = "token_timestamp";

    @Inject
    EntityManager entityManager;

    @Transactional
    public void saveAuthToken(Map<String, String> auth) {
        saveValue(TOKEN_KEY, auth.get(TOKEN_KEY));
        saveValue(SIGN_KEY, auth.get(SIGN_KEY));
        String timeStamp = String.valueOf(Instant.now().toEpochMilli());
        saveValue(TOKEN_TIMESTAMP_KEY, timeStamp);
        System.out.println("============================");
        System.out.println("============================");
        System.out.println("guardando valores");
        System.out.printf("TOKEN_KEY=%s", auth.get(TOKEN_KEY));
        System.out.printf("SIGN_KEY=%s", auth.get(SIGN_KEY));
        System.out.printf("TOKEN_TIMESTAMP_KEY=%s", timeStamp);
        System.out.println("============================");
        System.out.println("============================");
    }

    @Transactional
    public String getToken() {
        return findValue(TOKEN_KEY);
    }

    @Transactional
    public String getSign() {
        return findValue(SIGN_KEY);
    }

    @Transactional
    public Instant getTokenTimestamp() {
        String timestampStr = findValue(TOKEN_TIMESTAMP_KEY);
        return (timestampStr != null) ? Instant.ofEpochMilli(Long.parseLong(timestampStr)) : null;
    }

    @Transactional
    public boolean isThresholdExceeded(Duration threshold) {
        Instant tokenTimestamp = getTokenTimestamp();
        if (tokenTimestamp == null) {
            return true;
        }
        Instant now = Instant.now();
        Duration duration = Duration.between(tokenTimestamp, now);
        return duration.compareTo(threshold) > 0;
    }

    private void saveValue(String key, String value) {
        Configuracion config = Configuracion.find("clave", key).firstResult();
        if (config == null) {
            config = new Configuracion();
            config.clave = key;
        }
        config.valor = value;
        config.persist();
    }

    private String findValue(String key) {
        Configuracion config = Configuracion.find("clave", key).firstResult();
        return (config != null) ? config.valor : null;
    }

    public void setTime(String value){
        saveValue(TOKEN_TIMESTAMP_KEY, value);
    }
}
