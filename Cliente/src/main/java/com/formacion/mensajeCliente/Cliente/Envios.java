package com.formacion.mensajeCliente.Cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class Envios {

	JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private JButton btnCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Envios window = new Envios();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Envios() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(21, 11, 390, 191);
		frame.getContentPane().add(textArea);
		
		JButton btnEnviarMensajes = new JButton("Enviar mensaje");
		btnEnviarMensajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userExists(textField.getText())){
				try{
				URL url = new URL(Menu.urlp + "mensajes/mensajes/message/send");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("text", textArea.getText());
				con.setRequestProperty("emitterNick", Menu.nick);
				con.setRequestProperty("receiverNick", textField.getText());
				con.connect();
				con.getResponseCode();
				con.disconnect();
				frame.dispose();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				}
				else{
					JOptionPane.showMessageDialog(null, "El usuario introducido no existe");
					textField.setText("");
				}
			}
		});
		btnEnviarMensajes.setBounds(255, 213, 135, 23);
		frame.getContentPane().add(btnEnviarMensajes);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setBounds(10, 226, 61, 14);
		frame.getContentPane().add(lblDestino);
		
		textField = new JTextField();
		textField.setBounds(57, 223, 167, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCerrar.setBounds(255, 239, 135, 23);
		frame.getContentPane().add(btnCerrar);
	}
	
	private boolean userExists(String nick){
		boolean b = false;
		try{
			URL url = new URL(Menu.urlp + "mensajes/mensajes/user/getAll");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			JSONArray array = new JSONArray(br.readLine());
			for(int i = 0;i<array.length();i++){
				JSONObject obj =(JSONObject) array.get(i);
				if(nick.equals(obj.getString("id"))){
					b = true;
				}
				
			}
			con.connect();
			con.getResponseCode();
			con.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return b;
		
	}
	
}
