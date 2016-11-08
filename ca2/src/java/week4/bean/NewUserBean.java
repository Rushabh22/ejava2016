package week4.bean;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class NewUserBean {

	@Resource(name = "jdbc/jdbcRealm")
	private DataSource ds;

	@Size(min = 3, max = 50)
	private String username;

	@Size(min = 3, max = 50)
	private String password;

	@Pattern(regexp
			= "[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]")
	private String email;

	private String usertype;

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// SHA-256 hash code to encrypt the password
	public String encryptPassword(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
		}
		return "encryption error";
	}

	public String add() {

		FacesContext context = FacesContext.getCurrentInstance();

		if (ds == null) {
			return "db_connection_error";
		}

		Connection conn;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			return "db_connection_error";
		}

		// check if 'username' is already registered
		int count = 0;
		try {
			PreparedStatement countQuery = conn.prepareStatement(
					"SELECT COUNT(*) AS total from USERTABLE WHERE USERNAME = ?");
			countQuery.setString(1, username);
			ResultSet rs = countQuery.executeQuery();
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				return "db_connection_error";
			}
		}
		if (count != 0) {
			context.addMessage("f:username",
					new FacesMessage("Username already exists"));
			return null;
		}

		// now insert a new user and usergroup
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			return "db_connection_error";
		}

		boolean committed = false;

		try {

			conn.setAutoCommit(false);

			PreparedStatement insertUserQuery = conn.prepareStatement(
					"INSERT INTO USERTABLE(USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)");
			insertUserQuery.setString(1, username);
			insertUserQuery.setString(2, encryptPassword(password));
			insertUserQuery.setString(3, email);
			insertUserQuery.executeUpdate();

			PreparedStatement insertGroupQuery = conn.prepareStatement(
					"INSERT INTO  GROUPTABLE(GROUPNAME, USERNAME) VALUES (?, ?)");
			if (usertype.equals("user") || usertype.equals("user+admin")) {
				insertGroupQuery.setString(1, "studentgroup"); // group name
				insertGroupQuery.setString(2, username);
				insertGroupQuery.executeUpdate();
			}
			if (usertype.equals("admin") || usertype.equals("user+admin")) {
				insertGroupQuery.setString(1, "admingroup"); // group name
				insertGroupQuery.setString(2, username);
				insertGroupQuery.executeUpdate();
			}

			conn.commit(); // both user and usergroup are inserted successfully
			committed = true;
			context.addMessage(null,
					new FacesMessage(username + ": successfully inserted."));

		} catch (SQLException e) {
			context.addMessage(null,
					new FacesMessage("SQLException to insert user/usergroup"));
		} finally {
			try {
				if (!committed) {
					conn.rollback();
				}
				conn.close();
			} catch (SQLException e) {
				return "db_connection_error";
			}
		}

		return null;
	}
}
