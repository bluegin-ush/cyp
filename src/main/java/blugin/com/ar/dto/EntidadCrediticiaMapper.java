package blugin.com.ar.dto;

import blugin.com.ar.cyp.model.EntidadCrediticia;

import java.util.ArrayList;
import java.util.List;

public class EntidadCrediticiaMapper {

    public static EntidadCrediticiaDTO toDTO(EntidadCrediticia entidadCrediticia) {
        if (entidadCrediticia == null) {
            return null;
        }

        EntidadCrediticiaDTO dto = new EntidadCrediticiaDTO();
        dto.id = entidadCrediticia.id;
        dto.tipo = entidadCrediticia.tipo;
        dto.nombre = entidadCrediticia.nombre;
        dto.archivo = entidadCrediticia.archivo;
        dto.cuit = entidadCrediticia.cuit;
        dto.contacto = entidadCrediticia.contacto;

        return dto;
    }

    public static EntidadCrediticia toEntity(EntidadCrediticiaDTO dto) {
        if (dto == null) {
            return null;
        }

        EntidadCrediticia entidadCrediticia = new EntidadCrediticia();
        entidadCrediticia.id = dto.id;
        entidadCrediticia.tipo = dto.tipo;
        entidadCrediticia.nombre = dto.nombre;
        entidadCrediticia.archivo = dto.archivo;
        entidadCrediticia.cuit = dto.cuit;
        entidadCrediticia.contacto = dto.contacto;

        return entidadCrediticia;
    }

    public static List<EntidadCrediticiaDTO> toDTOList(List<EntidadCrediticia> entidades) {
        if (entidades == null) {
            return null;
        }

        List<EntidadCrediticiaDTO> dtoList = new ArrayList<>();
        for (EntidadCrediticia entidad : entidades) {
            dtoList.add(toDTO(entidad));
        }

        return dtoList;
    }

    public static List<EntidadCrediticia> toEntityList(List<EntidadCrediticiaDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<EntidadCrediticia> entityList = new ArrayList<>();
        for (EntidadCrediticiaDTO dto : dtos) {
            entityList.add(toEntity(dto));
        }

        return entityList;
    }
}
