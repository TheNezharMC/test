package com.vo;

import java.util.Date;

public class MensajeVO {

	private long id;
	private String text;
	private Date fecha;
	private UsuarioVO usuario;
	private UsuarioVO usuario2;
	
	
	public MensajeVO(long id, String text, Date fecha, UsuarioVO usuario, UsuarioVO usuario2) {
		this.id = id;
		this.text = text;
		this.fecha = fecha;
		this.usuario = usuario;
		this.usuario2 = usuario2;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public UsuarioVO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
	public UsuarioVO getUsuario2() {
		return usuario2;
	}
	public void setUsuario2(UsuarioVO usuario2) {
		this.usuario2 = usuario2;
	}
	
	
	
	
}
