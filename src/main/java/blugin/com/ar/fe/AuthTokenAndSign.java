package blugin.com.ar.fe;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.prefs.Preferences;

public class AuthTokenAndSign {
    private static final String TOKEN_KEY = "token";
    private static final String SIGN_KEY = "sign";
    private static final String TOKEN_TIMESTAMP_KEY = "token_timestamp";

    private Preferences prefs;

    public AuthTokenAndSign() {
        prefs = Preferences.userNodeForPackage(AuthTokenAndSign.class);
    }

    public void saveAuthToken(Map<String, String> auth){
        saveToken(auth.get(TOKEN_KEY));
        saveSign(auth.get(SIGN_KEY));
    }
    // Método para guardar el token junto con el timestamp actual
    private void saveToken(String token) {
        prefs.put(TOKEN_KEY, token);
        prefs.putLong(TOKEN_TIMESTAMP_KEY, Instant.now().toEpochMilli());
    }

    // Método para recuperar el token
    public String getToken() {
        return prefs.get(TOKEN_KEY, null);
    }

    // Método para guardar el sign
    private void saveSign(String sign) {
        prefs.put(SIGN_KEY, sign);
    }

    // Método para recuperar el sign
    public String getSign() {
        return prefs.get(SIGN_KEY, null);
    }

    // Método para recuperar el timestamp del token
    public Instant getTokenTimestamp() {
        long timestamp = prefs.getLong(TOKEN_TIMESTAMP_KEY, 0);
        return (timestamp > 0) ? Instant.ofEpochMilli(timestamp) : null;
    }

    // Método para verificar si el umbral de tiempo ha sido superado
    public boolean isThresholdExceeded(Duration threshold) {
        Instant tokenTimestamp = getTokenTimestamp();
        if (tokenTimestamp == null) {
            return true; // Si no hay timestamp, consideramos que el umbral está superado
        }
        Instant now = Instant.now();
        Duration duration = Duration.between(tokenTimestamp, now);
        return duration.compareTo(threshold) > 0;
    }
}
