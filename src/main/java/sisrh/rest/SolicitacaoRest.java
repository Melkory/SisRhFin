package sisrh.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Solicitacao;

@Api
@Path("/solicitacao")
public class SolicitacaoRest {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarSolicitacoes () throws Exception {
		List<Solicitacao> list = Banco.listarSolicitacoes();
		GenericEntity<List<Solicitacao>> entity = new GenericEntity<List<Solicitacao>>(list) {};
		return Response.ok().entity(list).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterSolicitacao (@PathParam("id") Integer id) throws Exception {
		try {
			Solicitacao solicitacao = Banco.buscarSolicitacaoPorId(id); 
			
			if (solicitacao != null) {
				return Response.ok().entity(solicitacao).build();
			} else {
				return Response.status(Status.NOT_FOUND).entity("{ \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{ \"mensagem\" : \"Falha ao obter solicitacao!\" }").build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response incluirSolicitacao (Solicitacao solicitacao) {
		try {
			Solicitacao solic = Banco.incluirSolicitacao(solicitacao);
			return Response.ok().entity(solic).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{ \"mensagem\" : \"Falha ao incluir solicitacao!\" }").build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterarSolicitacao (@PathParam("id") Integer id, Solicitacao solicitacao) {
		try {
			if (Banco.buscarSolicitacaoPorId(id) == null) {
				return Response.status(Status.NOT_FOUND).entity("{  \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}
			
			Solicitacao solic = Banco.alterarSolicitacao(id, solicitacao);
			return Response.ok().entity(solic).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{ \"mensagem\" : \"Falha ao alterar solicitacao!\" }").build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirSolicitacao (@PathParam("id") Integer id) {
		try {
			if (Banco.buscarSolicitacaoPorId(id) == null) {
				return Response.status(Status.NOT_FOUND).entity("{  \"mensagem\" : \"Solicitacao nao encontrada!\" }").build();
			}
			
			Banco.excluirSolicitacao(id);
			return Response.ok().entity("{  \"mensagem\" : \"Solicitacao " + id + " excluida!\" }").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("{ \"mensagem\" : \"Falha ao alterar solicitacao!\" }").build();
		}
	}

}
