package in.co.fennel.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.CartBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DatabaseException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.util.DataUtility;
import in.co.fennel.project.util.JDBCDataSource;



public class CartModel {

	private static Logger log = Logger.getLogger(CartModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM F_Cart");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	public long add(CartBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO F_Cart VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getItemName());
			pstmt.setLong(3, bean.getItemId());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getQuantity());
			pstmt.setString(6, bean.getPrice());
			pstmt.setString(7, bean.getTotalPrice());
			pstmt.setLong(8, bean.getUserId());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, DataUtility.getCurrentTimestamp());
			pstmt.setTimestamp(12, DataUtility.getCurrentTimestamp());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
				e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Order");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public CartBean findByLogin(String name) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM F_Cart WHERE itemName=?");
		CartBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CartBean();
				bean.setId(rs.getLong(1));
				bean.setItemName(rs.getString(2));
				bean.setItemId(rs.getLong(3));
				bean.setDescription(rs.getString(4));
				bean.setQuantity(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setTotalPrice(rs.getString(7));
				bean.setUserId(rs.getLong(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;
	}

	public CartBean findByPK(long id) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM F_Cart WHERE ID=?");
		CartBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CartBean();
				bean.setId(rs.getLong(1));
				bean.setItemName(rs.getString(2));
				bean.setItemId(rs.getLong(3));
				bean.setDescription(rs.getString(4));
				bean.setQuantity(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setTotalPrice(rs.getString(7));
				bean.setUserId(rs.getLong(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;
	}

	public void delete(CartBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM F_Cart WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List search(CartBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(CartBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM F_Cart WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if (bean.getItemId() > 0) {
				sql.append(" AND itemId = " + bean.getItemId());
			}
			
			if (bean.getUserId() > 0) {
				sql.append(" AND UserId = " + bean.getUserId());
			}

			if (bean.getItemName() != null && bean.getItemName().length() > 0) {
				sql.append(" AND ItemName like '" + bean.getItemName() + "%'");
			}
			

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		System.out.println("user model search  :" + sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CartBean();
				bean.setId(rs.getLong(1));
				bean.setItemName(rs.getString(2));
				bean.setItemId(rs.getLong(3));
				bean.setDescription(rs.getString(4));
				bean.setQuantity(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setTotalPrice(rs.getString(7));
				bean.setUserId(rs.getLong(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public void update(CartBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE F_Cart SET ItemName=?,ItemId=?,description=?,quantity=?,price=?,totalPrice=?,userId=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getItemName());
			pstmt.setLong(2, bean.getItemId());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getQuantity());
			pstmt.setString(5, bean.getPrice());
			pstmt.setString(6, bean.getTotalPrice());
			pstmt.setLong(7, bean.getUserId());
			pstmt.setString(8, bean.getCreatedBy());
			pstmt.setString(9, bean.getModifiedBy());
			pstmt.setTimestamp(10, DataUtility.getCurrentTimestamp());
			pstmt.setTimestamp(11, DataUtility.getCurrentTimestamp());
			pstmt.setLong(12, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Role ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from F_Cart");
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
				CartBean bean = new CartBean();
				bean.setId(rs.getLong(1));
				bean.setItemName(rs.getString(2));
				bean.setItemId(rs.getLong(3));
				bean.setDescription(rs.getString(4));
				bean.setQuantity(rs.getString(5));
				bean.setPrice(rs.getString(6));
				bean.setTotalPrice(rs.getString(7));
				bean.setUserId(rs.getLong(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}
}
