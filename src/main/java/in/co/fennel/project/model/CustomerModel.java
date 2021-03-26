package in.co.fennel.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.AdvertisementBean;
import in.co.fennel.project.bean.CustomerBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DatabaseException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.util.JDBCDataSource;





public class CustomerModel {
	private static Logger log = Logger.getLogger(CustomerModel.class);

	public long registerUser(CustomerBean bean) throws ApplicationException, DuplicateRecordException {

		long pk = addCustomer(bean);

		return pk;
	}

	public CustomerBean authenticate(String username, String password) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM F_CUSTOMER WHERE USERNAME = ? AND PASSWORD = ?");
		CustomerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setUserName(rs.getString(2));
				bean.setFirstName(rs.getString(3));
				bean.setSurName(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setProfessionalSocialCategory(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setPhoneNo(rs.getString(8));
				bean.setEmailID(rs.getString(9));
				bean.setCommercialCategories(rs.getString(10));
				bean.setPassword(rs.getString(11));
				bean.setRoleid(rs.getInt(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));

			}
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model authenticate End");
		return bean;
	}

	public List getRoles(CustomerBean bean) throws ApplicationException {

		String sql = "SELECT * FROM F_CUSTOMER WHERE role_id=?";
		Connection conn = null;
		List list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRoleid());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setUserName(rs.getString(2));
				bean.setFirstName(rs.getString(3));
				bean.setSurName(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setProfessionalSocialCategory(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setPhoneNo(rs.getString(8));
				bean.setEmailID(rs.getString(9));
				bean.setCommercialCategories(rs.getString(10));
				bean.setPassword(rs.getString(11));
				bean.setRoleid(rs.getInt(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public Integer nextPK() throws DatabaseException {

		Connection con = null;
		int pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT MAX(ID) FROM F_CUSTOMER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk + 1;
	}

	public int addCustomer(CustomerBean bean) throws ApplicationException {
		Connection con = null;
		int pk = 0;
		CustomerBean customerPresent = findByLogin(bean.getUserName());
		if (customerPresent != null) {
			throw new ApplicationException("Username already Exist in the system");
		}
		try {
			con = JDBCDataSource.getConnection();
			pk = nextPK();
			con.setAutoCommit(false);
			String sql = "INSERT INTO F_CUSTOMER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pk);
			ps.setString(2, bean.getUserName());
			ps.setString(3, bean.getFirstName());
			ps.setString(4, bean.getSurName());
			ps.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			ps.setString(6, bean.getProfessionalSocialCategory());
			ps.setString(7, bean.getAddress());
			ps.setString(8, bean.getPhoneNo());
			ps.setString(9, bean.getEmailID());
			ps.setString(10, bean.getCommercialCategories());
			ps.setString(11, bean.getPassword());
			ps.setInt(12, bean.getRoleid());
			ps.setString(13, bean.getCreatedBy());
			ps.setString(14, bean.getModifiedBy());
			ps.setTimestamp(15, bean.getCreatedDatetime());
			ps.setTimestamp(16, bean.getModifiedDatetime());

			ps.executeUpdate();
			con.commit();
			ps.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				con.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Customer");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk;
	}

	public CustomerBean findByLogin(String username) throws ApplicationException {
		String sql = "SELECT * FROM F_CUSTOMER WHERE EMAILID=?";
		CustomerBean bean = null;
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setUserName(rs.getString(2));
				bean.setFirstName(rs.getString(3));
				bean.setSurName(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setProfessionalSocialCategory(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setPhoneNo(rs.getString(8));
				bean.setEmailID(rs.getString(9));
				bean.setCommercialCategories(rs.getString(10));
				bean.setPassword(rs.getString(11));
				bean.setRoleid(rs.getInt(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return bean;
	}
	
	public CustomerBean findByUserName(String username) throws ApplicationException {
		String sql = "SELECT * FROM F_CUSTOMER WHERE UserName=?";
		CustomerBean bean = null;
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setUserName(rs.getString(2));
				bean.setFirstName(rs.getString(3));
				bean.setSurName(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setProfessionalSocialCategory(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setPhoneNo(rs.getString(8));
				bean.setEmailID(rs.getString(9));
				bean.setCommercialCategories(rs.getString(10));
				bean.setPassword(rs.getString(11));
				bean.setRoleid(rs.getInt(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return bean;
	}

	public void update(CustomerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		CustomerBean beanExist = findByLogin(bean.getUserName());
		// Check if updated LoginId already exist
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateRecordException("Username already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE F_CUSTOMER SET USERNAME=?,FIRSTNAME=?,SURNAME=?,DOB=?,PROF_SOCIAL_CATEGORY=?,ADDRESS=?,PHONENO=?,EMAILID=?,COMM_CATEGORY=?,PASSWORD=?,ROLE_ID=?,"
							+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, bean.getUserName());
			ps.setString(2, bean.getFirstName());
			ps.setString(3, bean.getSurName());
			ps.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			ps.setString(5, bean.getProfessionalSocialCategory());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getPhoneNo());
			ps.setString(8, bean.getEmailID());
			ps.setString(9, bean.getCommercialCategories());
			ps.setString(10, bean.getPassword());
			ps.setLong(11, bean.getRoleid());
			ps.setString(12, bean.getCreatedBy());
			ps.setString(13, bean.getModifiedBy());
			ps.setTimestamp(14, bean.getCreatedDatetime());
			ps.setTimestamp(15, bean.getModifiedDatetime());
			ps.setLong(16, bean.getId());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Customer ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from F_CUSTOMER");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("sql in list user :" + sql);
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerBean bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setUserName(rs.getString(2));
				bean.setFirstName(rs.getString(3));
				bean.setSurName(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setProfessionalSocialCategory(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setPhoneNo(rs.getString(8));
				bean.setEmailID(rs.getString(9));
				bean.setCommercialCategories(rs.getString(10));
				bean.setPassword(rs.getString(11));
				bean.setRoleid(rs.getInt(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of Customer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}

	public void delete(CustomerBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM F_CUSTOMER WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Customer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	//Getting records by id
	public CustomerBean getRecordByID(long pk) throws ApplicationException {
	
		StringBuffer sql = new StringBuffer("SELECT * FROM F_CUSTOMER WHERE ID=?");
		CustomerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CustomerBean();
				bean.setId(rs.getLong(1));
				bean.setUserName(rs.getString(2));
				bean.setFirstName(rs.getString(3));
				bean.setSurName(rs.getString(4));
				bean.setDob(rs.getDate(5));
				bean.setProfessionalSocialCategory(rs.getString(6));
				bean.setAddress(rs.getString(7));
				bean.setPhoneNo(rs.getString(8));
				bean.setEmailID(rs.getString(9));
				bean.setCommercialCategories(rs.getString(10));
				bean.setPassword(rs.getString(11));
				bean.setRoleid(rs.getInt(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Customer by id");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;
	}
	
	// Search
			public List search(CustomerBean bean) throws ApplicationException {
				return search(bean, 0, 0);
			}

			public List search(CustomerBean bean, int pageNo, int pageSize) throws ApplicationException {

				StringBuffer sql = new StringBuffer("SELECT * FROM  F_CUSTOMER WHERE 1=1");
				if (bean != null) {
					if (bean.getId() > 0) {
						sql.append(" AND id = " + bean.getId());
					}
					if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
						sql.append(" AND FirstName LIKE '" + bean.getFirstName() + "%'");
					}
					
					if (bean.getEmailID() != null && bean.getEmailID().length() > 0) {
						sql.append(" AND emailId LIKE '" + bean.getEmailID() + "%'");
					}

				}

				// if page size is greater than zero then apply pagination
				if (pageSize > 0) {
					// Calculate start record index
					pageNo = (pageNo - 1) * pageSize;
					sql.append(" Limit " + pageNo + ", " + pageSize);
					// sql.append(" limit " + pageNo + "," + pageSize);
				}
				ArrayList list = new ArrayList();
				Connection conn = null;
				try {
					conn = JDBCDataSource.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql.toString());
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						bean = new CustomerBean();
						bean.setId(rs.getLong(1));
						bean.setUserName(rs.getString(2));
						bean.setFirstName(rs.getString(3));
						bean.setSurName(rs.getString(4));
						bean.setDob(rs.getDate(5));
						bean.setProfessionalSocialCategory(rs.getString(6));
						bean.setAddress(rs.getString(7));
						bean.setPhoneNo(rs.getString(8));
						bean.setEmailID(rs.getString(9));
						bean.setCommercialCategories(rs.getString(10));
						bean.setPassword(rs.getString(11));
						bean.setRoleid(rs.getInt(12));
						bean.setCreatedBy(rs.getString(13));
						bean.setModifiedBy(rs.getString(14));
						bean.setCreatedDatetime(rs.getTimestamp(15));
						bean.setModifiedDatetime(rs.getTimestamp(16));
						list.add(bean);
					}
					rs.close();
				} catch (Exception e) {

					throw new ApplicationException("Exception : Exception in search ItemModel");
				} finally {
					JDBCDataSource.closeConnection(conn);
				}

				return list;
			}

	public static void main(String[] args) throws ApplicationException {
		CustomerModel m = new CustomerModel();
		CustomerBean bean = new CustomerBean();
		bean.setUserName("Admin");
		bean.setPassword("Admin");
		m.authenticate(bean.getUserName(), bean.getPassword());
	}
}
