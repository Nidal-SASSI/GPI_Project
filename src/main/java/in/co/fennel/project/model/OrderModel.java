package in.co.fennel.project.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.fennel.project.bean.OrderBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DatabaseException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.util.JDBCDataSource;


public class OrderModel {

	private static Logger log = Logger.getLogger(OrderModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM F_ORDER");
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
	
	public long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO F_ORDER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setDate(2, new java.sql.Date(bean.getTime_slot().getTime()));
			pstmt.setString(3, bean.getStatus());
			pstmt.setString(4,bean.getTotal());
			pstmt.setString(5, bean.getName());
			pstmt.setString(6, bean.getEmail());
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setString(8, bean.getAddress1());
			pstmt.setString(9, bean.getAddress2());
			pstmt.setString(10, bean.getCity());
			pstmt.setString(11, bean.getState());
			pstmt.setLong(12, bean.getUserId());
			pstmt.setString(13, bean.getCreatedBy());
			pstmt.setString(14, bean.getModifiedBy());
			pstmt.setTimestamp(15, bean.getCreatedDatetime());
			pstmt.setTimestamp(16, bean.getModifiedDatetime());
			pstmt.setLong(17, bean.getItemId());
			pstmt.setString(18, bean.getItemName());
			pstmt.setString(19, bean.getCategory());
			pstmt.setString(20, bean.getQuantity());
			pstmt.setLong(21,bean.getOrderid());
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
	public OrderBean findByLogin(String name) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM F_ORDER WHERE STATUS=?");
		OrderBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setTime_slot(rs.getDate(2));
				bean.setStatus(rs.getString(3));
				bean.setTotal(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setEmail(rs.getString(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress1(rs.getString(8));
				bean.setAddress2(rs.getString(9));
				bean.setCity(rs.getString(10));
				bean.setState(rs.getString(11));
				bean.setUserId(rs.getLong(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
				bean.setItemId(rs.getLong(17));
				bean.setItemName(rs.getString(18));
				bean.setCategory(rs.getString(19));
				bean.setQuantity(rs.getString(20));
				bean.setOrderid(rs.getLong(21));
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
	
	public OrderBean findByPK(long id) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM F_ORDER WHERE ID=?");
		OrderBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setTime_slot(rs.getDate(2));
				bean.setStatus(rs.getString(3));
				bean.setTotal(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setEmail(rs.getString(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress1(rs.getString(8));
				bean.setAddress2(rs.getString(9));
				bean.setCity(rs.getString(10));
				bean.setState(rs.getString(11));
				bean.setUserId(rs.getLong(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
				bean.setItemId(rs.getLong(17));
				bean.setItemName(rs.getString(18));
				bean.setCategory(rs.getString(19));
				bean.setQuantity(rs.getString(20));
				bean.setOrderid(rs.getLong(21));

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
	
	public void delete(OrderBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM F_ORDER WHERE ID=?");
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
	
	public List search(OrderBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM F_ORDER WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if (bean.getUserId() > 0) {
				sql.append(" AND UserId = " + bean.getUserId());
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND STATUS like '" + bean.getStatus() + "%'");
			}
			if (bean.getTime_slot() != null && bean.getTime_slot().getDate() > 0) {
				sql.append(" AND TIME-SLOT = " + bean.getTime_slot());
			}
			
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			
			if (bean.getItemName() != null && bean.getItemName().length() > 0) {
				sql.append(" AND ItemName like '" + bean.getItemName() + "%'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println("user model search  :" + sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setTime_slot(rs.getDate(2));
				bean.setStatus(rs.getString(3));
				bean.setTotal(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setEmail(rs.getString(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress1(rs.getString(8));
				bean.setAddress2(rs.getString(9));
				bean.setCity(rs.getString(10));
				bean.setState(rs.getString(11));
				bean.setUserId(rs.getLong(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
				bean.setItemId(rs.getLong(17));
				bean.setItemName(rs.getString(18));
				bean.setCategory(rs.getString(19));
				bean.setQuantity(rs.getString(20));
				bean.setOrderid(rs.getLong(21));
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
	
	
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from F_ORDER");
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
				OrderBean bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setTime_slot(rs.getDate(2));
				bean.setStatus(rs.getString(3));
				bean.setTotal(rs.getString(4));
				bean.setName(rs.getString(5));
				bean.setEmail(rs.getString(6));
				bean.setMobileNo(rs.getString(7));
				bean.setAddress1(rs.getString(8));
				bean.setAddress2(rs.getString(9));
				bean.setCity(rs.getString(10));
				bean.setState(rs.getString(11));
				bean.setUserId(rs.getLong(12));
				bean.setCreatedBy(rs.getString(13));
				bean.setModifiedBy(rs.getString(14));
				bean.setCreatedDatetime(rs.getTimestamp(15));
				bean.setModifiedDatetime(rs.getTimestamp(16));
				bean.setItemId(rs.getLong(17));
				bean.setItemName(rs.getString(18));
				bean.setCategory(rs.getString(19));
				bean.setQuantity(rs.getString(20));
				bean.setOrderid(rs.getLong(21));
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
