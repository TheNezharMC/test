package com.ws;

import java.util.Calendar;
import java.util.Date;
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

import com.vo.MensajeVO;
import com.vo.UsuarioVO;

import mensajes.Datos;

@Path("/message")
public class WSMensaje {
	
	public static long id = 0;

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response lista_mensajes(){
		
		List<MensajeVO> lista = Datos.listaMensajes;
		JSONArray array = new JSONArray();
		for(MensajeVO u:lista){
			JSONObject obj = new JSONObject();
			JSONObject emisor = new JSONObject();
			JSONObject receptor = new JSONObject();
			try{
			obj.put("id", u.getId());
			obj.put("texto", u.getText());
			obj.put("fecha", u.getFecha());
			emisor.put("nick",u.getUsuario().getNick());
			emisor.put("pass", u.getUsuario().getPass());
			receptor.put("nick", u.getUsuario2().getNick());
			receptor.put("pass", u.getUsuario2().getPass());
			}catch(JSONException e){
				e.printStackTrace();
			}
			obj.put("emisor", emisor);
			obj.put("receptor", receptor);
			array.put(obj);
		}
		return Response.ok(array.toString(),MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMensaje(@PathParam("id") long id){
		
		JSONObject jo = new JSONObject();
		JSONObject emisor = new JSONObject();
		JSONObject receptor = new JSONObject();
		
		for(MensajeVO i:Datos.listaMensajes){
			
			if(i.getId()== id){
				jo.put("id", i.getId());
				jo.put("text",i.getText());
				jo.put("fecha", i.getFecha());
				emisor.put("nick", i.getUsuario().getNick());
				emisor.put("pass", i.getUsuario().getPass());
				receptor.put("nick",i.getUsuario2().getNick());
				receptor.put("pass", i.getUsuario2().getNick());
				jo.put("emisor", emisor);
				jo.put("receptor",receptor);
			}
			
			
			
		}
		
		return Response.ok(jo.toString(),MediaType.APPLICATION_JSON).build();
		
	}
	
	@GET
	@Path("/getByReceiver/{nick}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMensaje(@PathParam("nick") String nick){
		JSONArray array = new JSONArray();
		
		
		for(MensajeVO i:Datos.listaMensajes){
			if(i.getUsuario2().getNick().equals(nick)){
				JSONObject jo = new JSONObject();
				JSONObject receptor = new JSONObject();
				JSONObject emisor = new JSONObject();
				jo.put("id", i.getId());
				jo.put("text",i.getText());
				jo.put("fecha", i.getFecha());
				emisor.put("nick", i.getUsuario().getNick());
				emisor.put("pass", i.getUsuario().getPass());
				receptor.put("nick", i.getUsuario2().getNick());
				receptor.put("pass", i.getUsuario2().getPass());
				jo.put("receptor",receptor);
				jo.put("emisor", emisor);
				array.put(jo);
			}
			
			
		}
		
		return Response.ok(array.toString(),MediaType.APPLICATION_JSON).build();
		
	}
	
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMensaje(@HeaderParam("text") String text, @HeaderParam("emitterNick") String emitterNick, @HeaderParam("receiverNick") String receiverNick){
		int exists = 0;
		Date d = Calendar.getInstance().getTime();
		UsuarioVO user = new UsuarioVO();
		UsuarioVO user2 = new UsuarioVO();
		for(UsuarioVO u:Datos.listaUsuarios){
			if(u.getNick().equals(emitterNick)){
				user = u;
				exists = exists + 1;
			}
			if(u.getNick().equals(receiverNick)){
				user2 = u;
				exists = exists + 1;
			}
			
		}
		if(exists==2){
		MensajeVO m = new MensajeVO(id,text,d,user,user2);
		id = id + 1;
		Datos.listaMensajes.add(m);
		}
	}
	
	@PUT
	@Path("/modify")
	@Consumes(MediaType.APPLICATION_JSON)
	public void modifyMensaje(@HeaderParam("id") long id, @HeaderParam("newText") String newText){
		
		for(MensajeVO m:Datos.listaMensajes){
			
			if(m.getId()==id){
				m.setText(newText);
			}
			
		}
		
	}
	
}
