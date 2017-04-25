package com.formacion.mensajeCliente.Cliente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Font;

public class Menu {

	JFrame frame;
	private JTextField userField;
	private JPasswordField passwordField;
	private JTextField userField2;
	private JPasswordField passwordField2;
	private JLabel lblError;
	static String nick;
	static String urlp = "http://localhost:8080/";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
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
	public Menu() {
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

		userField = new JTextField();
		userField.setBounds(38, 87, 129, 20);
		frame.getContentPane().add(userField);
		userField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(38, 143, 129, 20);
		frame.getContentPane().add(passwordField);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(38, 61, 59, 14);
		frame.getContentPane().add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(38, 118, 76, 14);
		frame.getContentPane().add(lblContrasea);

		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(10, 226, 414, 14);
		frame.getContentPane().add(lblError);

		JLabel lblIniciarSesin = new JLabel("Iniciar sesi\u00F3n");
		lblIniciarSesin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIniciarSesin.setBounds(39, 27, 112, 20);
		frame.getContentPane().add(lblIniciarSesin);

		JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRegistrarse.setBounds(280, 27, 112, 20);
		frame.getContentPane().add(lblRegistrarse);

		JLabel labelUsuario2 = new JLabel("Usuario:");
		labelUsuario2.setBounds(237, 61, 59, 14);
		frame.getContentPane().add(labelUsuario2);

		JLabel labelContrasea2 = new JLabel("Contrase\u00F1a:");
		labelContrasea2.setBounds(234, 118, 76, 14);
		frame.getContentPane().add(labelContrasea2);

		userField2 = new JTextField();
		userField2.setColumns(10);
		userField2.setBounds(235, 87, 129, 20);
		frame.getContentPane().add(userField2);

		passwordField2 = new JPasswordField();
		passwordField2.setBounds(235, 143, 129, 20);
		frame.getContentPane().add(passwordField2);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				nick = userField.getText();
				String pass = new String(passwordField.getPassword());

				URL url;
				try {
					url = new URL(urlp + "mensajes/mensajes/user/get/" + nick);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String result = br.readLine();
					JSONObject res = new JSONObject(result);
					String stPass = res.getString("pass");
					if (stPass.equals(pass)) {
						frame.dispose();
						Mensajes m = new Mensajes();
						m.frame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
						passwordField.setText("");
					}

				} catch (FileNotFoundException f) {
					lblError.setText("Se deben cumplimentar los campos");
				} catch (JSONException er) {
					JOptionPane.showMessageDialog(null, "Usuario inexistente");
					userField.setText("");
					passwordField.setText("");
				} catch (IOException i) {
					JOptionPane.showMessageDialog(null, "No se admiten nombres de usuario con espacios");
					userField.setText("");
					passwordField.setText("");
				} catch (Exception o) {
					o.printStackTrace();
				}

			}
		});

		btnAceptar.setBounds(48, 174, 89, 23);
		frame.getContentPane().add(btnAceptar);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");

				try {
					URL url2 = new URL(urlp + "mensajes/mensajes/user/get/" + userField2.getText());
					HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
					con2.setRequestMethod("GET");
					con2.connect();
					BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream()));
					String result = "";
					result = br.readLine();
					JSONObject res = new JSONObject(result);
					String nick = res.getString("nick");
					if (nick.equals(userField2.getText())) {
						JOptionPane.showMessageDialog(null, "El usuario introducido ya existe, introduce otro");
						userField2.setText("");
					}
					con2.getResponseCode();
					con2.disconnect();
				} catch (FileNotFoundException f) {
					lblError.setText("Se deben cumplimentar los campos");
				} catch (JSONException j) {
					if (passwordField2.getPassword().length == 0) {
						JOptionPane.showMessageDialog(null, "La contraseña no puede estar en blanco");
					} else {
						addUser();
						JOptionPane.showMessageDialog(null, "Usuario añadido correctamente");
						userField2.setText("");
						passwordField2.setText("");
					}
				} catch (IOException i) {
					JOptionPane.showMessageDialog(null, "No se admiten nombres de usuario con espacios");
					userField2.setText("");
					passwordField2.setText("");
				} catch (Exception u) {
					u.printStackTrace();
				}
			}
		});
		btnRegistrar.setBounds(245, 174, 89, 23);
		frame.getContentPane().add(btnRegistrar);

	}

	private void addUser() {
		String pass = new String(passwordField2.getPassword());
		try {
			URL url = new URL(urlp + "mensajes/mensajes/user/register");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("nick", userField2.getText());
			con.setRequestProperty("pass", pass);
			con.connect();
			con.getResponseCode();
			con.disconnect();
		} catch (Exception mue) {
			mue.printStackTrace();
		}
	}

}
