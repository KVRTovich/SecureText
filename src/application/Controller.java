package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.util.Base64;

public class Controller {
	static String var;
	static String password;
	static byte[] chars;
	@FXML
	public Button encode;
	public Button decode;
	
	public TextField textfield;
	public TextField output;
	public PasswordField passfield;
	@FXML
	public void AES() throws Exception {
		password = password.substring(0,16);
		Cipher xuy1 = Cipher.getInstance("AES");
		SecretKeySpec xuy123 = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), "AES");
		xuy1.init(Cipher.ENCRYPT_MODE, xuy123);
		chars = xuy1.doFinal(var.getBytes(StandardCharsets.UTF_8));
	}
	public void Hashing() throws Exception  {
		MessageDigest initmd = MessageDigest.getInstance("SHA-256");
		chars = initmd.digest(password.getBytes(StandardCharsets.UTF_8));
	    password = String.format("%064x", new BigInteger(1, chars));

	}
	public void encode() throws Exception{
		try {
			var = textfield.getText();
			password = passfield.getText();
			Hashing();
			AES();
			output.setText(Base64.getEncoder().encodeToString(chars));
		}
		catch (Exception e) {
			error();
		}
	}
	public void Decode() throws Exception {
			password = password.substring(0,16);
			Cipher aes = Cipher.getInstance("AES");
			SecretKeySpec aespass = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), "AES");
			aes.init(Cipher.DECRYPT_MODE, aespass);
			chars = aes.doFinal(Base64.getDecoder().decode(var));
	}
	public void error() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Error -1!!");
		alert.setHeaderText("Oops");
		alert.setContentText("Something went wrong (Incorrect password or text)");

		alert.showAndWait();
	}
	@FXML
	public void decode() throws Exception {
		try {
			var = textfield.getText();
			password = passfield.getText();
			Hashing();
			Decode();
			output.setText(new String(chars, "UTF-8"));
		}
	    catch (Exception e) {
		error();
	    }
	}
	
}
