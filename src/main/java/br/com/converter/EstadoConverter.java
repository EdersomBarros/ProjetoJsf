package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Estados;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {

	private static final long serialVersionUID = -628943317877875062L;

	@Override /* retorna objeto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {

		// EntityManager entityManager = jpaUtil.getEntityManager();
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();
		// transaction.begin();

		Estados estados = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));

		return estados;

	}

	@Override /* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component, Object estado) {

		if (estado == null) {

			return null;
		}
		if (estado instanceof Estados) {
			return ((Estados) estado).getId().toString();
		} else {
			return estado.toString();
		}

	}

}
