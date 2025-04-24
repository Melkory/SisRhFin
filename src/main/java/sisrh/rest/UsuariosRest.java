package sisrh.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	
	@GET
	@Path("{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterUsuarioMatricula (@PathParam("matricula") String matricula) throws Exception {
		try {
			Usuario usuario = Banco.buscarUsuarioPorMatricula(matricula);
			
			if (usuario != null) {
				return Response.ok().entity(usuario).build();
			} else {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
			}
		} catch (Exception e) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{ \"mensagem\" : \"Falha para obter usuario!\" }").build();
		}
	}

}
