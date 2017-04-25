package com.formacion.mensajeCliente.Cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JComboBox;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Mensajes {

	 JFrame frame;
	private static JTextArea textArea;
	private static JComboBox<String> comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mensajes window = new Mensajes();
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
	public Mensajes() {
		
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
		
		JButton btnEnvarMensaje = new JButton("Envíar mensaje");
		btnEnvarMensaje.setBounds(20, 228, 124, 23);
		frame.getContentPane().add(btnEnvarMensaje);
		btnEnvarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Envios env = new Envios();
				env.frame.setVisible(true);
			}
				});
		
		JButton btnActualizarLista = new JButton("Actualizar lista");
		btnActualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnActualizarLista.setBounds(148, 228, 151, 23);
		frame.getContentPane().add(btnActualizarLista);
		
		JLabel lblNewLabel = new JLabel("Bandeja de  entrada");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel.setBounds(136, 0, 163, 23);
		frame.getContentPane().add(lblNewLabel);
		
		
		
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(126, 26, 173, 20);
		frame.getContentPane().add(comboBox);
		
		
		JLabel lblMensajes = new JLabel("Mensajes");
		lblMensajes.setBounds(57, 29, 59, 14);
		frame.getContentPane().add(lblMensajes);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(20, 51, 374, 149);
		frame.getContentPane().add(textArea);
		
		update();
		
		JButton btnNewButton = new JButton("Cerrar sesión");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu m = new Menu();
				m.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnNewButton.setBounds(309, 228, 115, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Ver texto");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateText(e);
			}
		});
		btnNewButton_1.setBounds(170, 204, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
	
	}
	
	private static void update(){

		try{
		URL url = new URL(Menu.urlp + "mensajes/mensajes/message/getByReceiver/" + Menu.nick);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		JSONArray array = new JSONArray(br.readLine());
		comboBox.removeAllItems();
		for(int i=0;i<array.length();i++){
			JSONObject obj =(JSONObject) array.get(i);
			JSONObject emisor = (JSONObject)obj.get("emisor");
			comboBox.addItem("Mensaje de " + emisor.getString("nick") + " id: "+obj.get("id"));
		
		}
		con.disconnect();
		}catch(Exception yee){
			yee.printStackTrace();
		}
		
	}
	
	private static void updateText(ActionEvent e){
		String pepe = comboBox.getSelectedItem().toString();
		int id = Integer.parseInt(pepe.split(" ")[pepe.split(" ").length-1]);
		try{
		URL url = new URL(Menu.urlp + "mensajes/mensajes/message/getByReceiver/" + Menu.nick);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		JSONArray array = new JSONArray(br.readLine());
		for(int i=0;i<array.length();i++){
			JSONObject obj =(JSONObject) array.get(i);
			if(Integer.parseInt(obj.get("id").toString())== id){
				textArea.setText(obj.getString("text"));
			}
		}
		}
		catch(Exception y){
			y.printStackTrace(); 
		}
	}
}
