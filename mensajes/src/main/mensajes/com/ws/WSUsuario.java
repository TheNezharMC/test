package com.ws;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vo.UsuarioVO;

import mensajes.Datos;

@Path("/user")
public class WSUsuario {
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response lista_usuarios(){
		List<UsuarioVO> lista = Datos.listaUsuarios;
		JSONArray array = new JSONArray();
		for(UsuarioVO u:lista){
			JSONObject obj = new JSONObject();
			try{
			obj.put("id", u.getNick());
			obj.put("Contraseña", u.getPass());
			}catch(JSONException e){
				e.printStackTrace();
			}
			array.put(obj);
			
			
			
		}
		return Response.ok(array.toString(),MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/get/{nick}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("nick")String nick){
		
		
		JSONObject jo = new JSONObject();
		
		for(UsuarioVO i:Datos.listaUsuarios){
			
			if(i.getNick().equals(nick)){
				jo.put("nick",i.getNick());
				jo.put("pass", i.getPass());
			}
			
		}
		
		return Response.ok(jo.toString(),MediaType.APPLICATION_JSON).build();
		
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUsuario(@HeaderParam("nick") String nick,@HeaderParam("pass") String pass){
		
		boolean found = false;
		
		for(UsuarioVO e :Datos.listaUsuarios){
			if(e.getNick().equals(nick)){
				found = true;
			}
			
		}
		UsuarioVO u = new UsuarioVO(nick,pass);
		if(!found){
		Datos.listaUsuarios.add(u);
		
		
		}
		
		
	}
	
	@PUT
	@Path("/modify")
	@Consumes(MediaType.APPLICATION_JSON)
	public void modifyUsuario(@HeaderParam("nick") String nick,@HeaderParam("newPass") String newPass){
		
		for(UsuarioVO u:Datos.listaUsuarios){
			
			if(u.getNick().equals(nick)){
				u.setPass(newPass);
			}
			
		}
		
	}
	
	

}
