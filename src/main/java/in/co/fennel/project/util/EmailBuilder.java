package in.co.fennel.project.util;

import java.util.HashMap;

/**
 * Class that build Application Email messages
 *
 * @author Navigable Set
 * @version 1.0
 * @Copyright (c) Navigable Set
 *
 */

public class EmailBuilder {
	/**
	 * Returns Successful User Registration Message
	 *
	 * @param map
	 *            : Message parameters
	 * @return
	 */

	public static String getUserRegistrationMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();

		msg.append("<HTML><BODY>");
		msg.append("<H1> Merci d'avoir créé un compte pour accéder à Fenouil</H1>");
		
		
		msg.append(
				"<P>Il vous suffit de cliquer sur le bouton ci-dessous pour vérifier votre email et utiliser pleinement nos services</p>");
		
		msg.append("<P><a href='http://localhost:8080/Fennel/' >-Fenouil Miatech</a></P>");
		msg.append("</BODY></HTML>");

		return msg.toString();
	}

	/**
	 * Returns Email message of Forget Password
	 *
	 * @param map
	 *            : params
	 * @return
	 */

	public static String getForgetPasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();

		msg.append("<HTML><BODY>");
		msg.append("<H1>Your password is reccovered !! " + map.get("firstName") + " " + map.get("lastName") + "</H1>");
		msg.append("<P><B>Voici vos identifiants de connexion : : " + map.get("login") + "<BR>" + " Password : "
				+ map.get("password") + "</B></p>");
		msg.append("</BODY></HTML>");

		return msg.toString();
	}

	/**
	 * Returns Email message of Change Password
	 *
	 * @param map
	 * @return
	 */
	public static String getChangePasswordMessage(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();

		msg.append("<HTML><BODY>");
		msg.append("<H1>Mot de passe à jour " + map.get("firstName") + " "
				+ map.get("lastName") + "</H1>");

		msg.append("<P><B>Voici vos identifiants de connexion : : " + map.get("login") + "<BR>" + " Password : "
				+ map.get("password") + "</B></p>");
		msg.append("</BODY></HTML>");

		return msg.toString();
	}
	
	
	public static String getItemsLink(HashMap<String, String> map) {
		StringBuilder msg = new StringBuilder();

		msg.append("<HTML><BODY>");
		msg.append("<H1>Bonjour " + map.get("firstName") + " "	+ map.get("lastName") + "</H1>");
		
		msg.append("<P><B>Profitons des beaux jours de Printemps</B></p>");
		msg.append("<P><B>Les offres de Printemps continuent chez Fenouil !</B></p>");
		
		msg.append("<P><B>Les meilleures offres de la semaine sont ici : : "+map.get("link")+"</B></p>");
		msg.append("<img src=\"https://www.bricorama.fr/media/wysiwyg/offre-du-mois/produit-gjardin21.jpg\" height=\"400px\" width=\"800px\" />\r\n"
				+ "\r\n"
				+ "");
		
		msg.append("</BODY></HTML>");

		return msg.toString();
	}
	

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", "AZWAW");
		map.put("password", "azwaw@123");
		System.out.println(EmailBuilder.getUserRegistrationMessage(map));
	}
}
