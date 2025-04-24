package sisrh.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Usuario;

@Api
@Path("/usuario")
public class UsuariosRest {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarios () throws Exception {
		List<Usuario> lista = Banco.listarUsuarios();
		GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(lista) {};
		return Response.ok().entity(entity).build();
	}

}
