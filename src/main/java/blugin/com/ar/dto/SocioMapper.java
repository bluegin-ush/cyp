package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.Socio;

import java.util.ArrayList;
import java.util.List;

public class SocioMapper {

    public static SocioDTO toDTO(Socio socio) {
        if (socio == null) {
            return null;
        }

        SocioDTO dto = new SocioDTO();
        dto.id = socio.id;
        dto.nro = socio.nro;
        dto.nombre = socio.nombre;
        dto.apellido = socio.apellido;
        dto.tipoDoc = socio.tipoDoc;
        dto.numDoc = socio.numDoc;
        dto.correo = socio.correo;
        dto.telefono = socio.telefono;
        dto.domicilio = socio.domicilio;
        dto.tarjetaNum = socio.tarjetaNum;
        dto.tarjetaVto = socio.tarjetaVto;
        dto.activo = socio.activo;
        dto.ctacte = socio.ctacte;
        dto.tieneDeuda = socio.tieneDeuda;

        if (socio.entidadCrediticia != null) {
            dto.entidadCrediticia = EntidadCrediticiaMapper.toDTO(socio.entidadCrediticia);
        }

        return dto;
    }

    public static Socio toEntity(SocioDTO dto) {
        if (dto == null) {
            return null;
        }

        Socio socio = new Socio();
        socio.id = dto.id;
        socio.nro = dto.nro;
        socio.nombre = dto.nombre;
        socio.apellido = dto.apellido;
        socio.tipoDoc = dto.tipoDoc;
        socio.numDoc = dto.numDoc;
        socio.correo = dto.correo;
        socio.telefono = dto.telefono;
        socio.domicilio = dto.domicilio;
        socio.tarjetaNum = dto.tarjetaNum;
        socio.tarjetaVto = dto.tarjetaVto;
        socio.activo = dto.activo;
        socio.ctacte = dto.ctacte;
        socio.tieneDeuda = dto.tieneDeuda;

        if (dto.entidadCrediticia != null) {
            socio.entidadCrediticia = EntidadCrediticiaMapper.toEntity(dto.entidadCrediticia);
        }

        return socio;
    }

    public static List<SocioDTO> toDTOList(List<Socio> socios) {
        if (socios == null) {
            return null;
        }

        List<SocioDTO> dtoList = new ArrayList<>();
        for (Socio socio : socios) {
            dtoList.add(toDTO(socio));
        }

        return dtoList;
    }

    public static List<Socio> toEntityList(List<SocioDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<Socio> entityList = new ArrayList<>();
        for (SocioDTO dto : dtos) {
            entityList.add(toEntity(dto));
        }

        return entityList;
    }
}
