package blugin.com.ar.wsfe.wrappers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

public class Tributos{

    private final Set<Tributo> tributos;

    public Tributos() {
        this.tributos = Collections.EMPTY_SET;
    }

    public BigDecimal total() {
        return tributos.stream().map(Tributo::importe).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Tributo> todos() {
        return tributos;
    }

}