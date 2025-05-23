package sisrh.seguranca;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import sisrh.banco.Banco;
import sisrh.exception.SisRhException;

public class Autenticador {

	@SuppressWarnings("rawtypes")
	public static boolean autenticarUsuarioSenha(WebServiceContext context) throws Exception {
		MessageContext messageContext = context.getMessageContext();
		Map httpHeaders = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);

		if (!httpHeaders.containsKey("usuario")) {
			throw new SisRhException("Informe o usuario");
		}

		if (!httpHeaders.containsKey("senha")) {
			throw new SisRhException("Informe a senha");
		}

		String usuario = ((List) httpHeaders.get("usuario")).get(0).toString();
		String senha = ((List) httpHeaders.get("senha")).get(0).toString();

		if (!autenticarUsuarioSenha(usuario, senha)) {
			throw new SisRhException("Usuario e senha invalidos");
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public static String getUsuario(WebServiceContext context) throws Exception {
		MessageContext messageContext = context.getMessageContext();
		Map httpHeaders = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
		return ((List) httpHeaders.get("usuario")).get(0).toString();
	}

	private static boolean autenticarUsuarioSenha(String usuario, String senha) throws Exception {
		try {
			String senhaMd5 = md5(senha);
			Connection conn = Banco.getConexao();
			String sql = "SELECT nome, senha FROM Usuario WHERE nome = ? and senha = ?";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, usuario);
			prepStmt.setString(2, senhaMd5);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String md5(String valor) throws Exception {
		String s = valor;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(s.getBytes(), 0, s.length());
		return "" + new BigInteger(1, m.digest()).toString(16);
	}

}
