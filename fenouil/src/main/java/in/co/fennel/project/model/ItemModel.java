package in.co.fennel.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.fennel.project.bean.ItemBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DatabaseException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.util.JDBCDataSource;


public class ItemModel {
	
	public ItemBean getRecordByID(long pk) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM F_ITEMS WHERE ID=?");
		ItemBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ItemBean();
				
				bean.setId(rs.getLong(1));
				bean.setDesignation(rs.getString(2));
				bean.setPrice(rs.getString(3));
				bean.setStock(rs.getString(4));
				bean.setQuantities(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setPhoto(rs.getBlob(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				bean.setCategory(rs.getString(12));
				bean.setName(rs.getString(13));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		
			throw new ApplicationException("Exception : Exception in getting item by id");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;
	}

	// Search
	public List search(ItemBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(ItemBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM F_ITEMS WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getDesignation() != null && bean.getDesignation().length() > 0) {
				sql.append(" AND DESIGNATION LIKE '" + bean.getDesignation() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME LIKE '" + bean.getName() + "%'");
			}
			if (bean.getCategory() != null && bean.getCategory().length() > 0) {
				sql.append(" AND CATEGORY LIKE '" + bean.getCategory() + "%'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		System.out.println(sql.toString());
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ItemBean();
				bean.setId(rs.getLong(1));
				bean.setDesignation(rs.getString(2));
				bean.setPrice(rs.getString(3));
				bean.setStock(rs.getString(4));
				bean.setQuantities(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setPhoto(rs.getBlob(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				bean.setCategory(rs.getString(12));
				bean.setName(rs.getString(13));
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

	public long addItems(ItemBean bean) throws ApplicationException, DuplicateRecordException {

		long pk = addItemByAdmin(bean);

		return pk;
	}

	public int addItemByAdmin(ItemBean bean) throws ApplicationException {
		Connection con = null;
		int pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			pk = nextPK();
			con.setAutoCommit(false);
			String sql = "INSERT INTO F_ITEMS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pk);
			ps.setString(2, bean.getDesignation());
			ps.setString(3, bean.getPrice());
			ps.setString(4, bean.getStock());
			ps.setString(5, bean.getQuantities());
			ps.setString(6, bean.getDescription());
			ps.setBlob(7, bean.getImage());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			ps.setString(12,bean.getCategory());
			ps.setString(13,bean.getName());
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

	public int nextPK() throws DatabaseException {

		Connection con = null;
		int pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement("SELECT MAX(ID) FROM F_ITEMS");
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

	// Delete Items by Admin
	public void delete(ItemBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM F_ITEMS WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete items");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	// Update Items By Admin
	public void update(ItemBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE F_ITEMS SET DESIGNATION=?,PRICE=?,STOCK=?,QUANTITIES=?,DESCRIPTION=?,Image=?,"
							+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=?,CATEGORY=?,Name=? WHERE ID=?");
			ps.setString(1, bean.getDesignation());
			ps.setString(2, bean.getPrice());
			ps.setString(3, bean.getStock());
			ps.setString(4, bean.getQuantities());
			ps.setString(5, bean.getDescription());
			ps.setBlob(6, bean.getImage());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setString(11,bean.getCategory());
			ps.setString(12,bean.getName());
			ps.setLong(13, bean.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating items ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from F_ITEMS");
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
				ItemBean bean = new ItemBean();
				bean.setId(rs.getLong(1));
				bean.setDesignation(rs.getString(2));
				bean.setPrice(rs.getString(3));
				bean.setStock(rs.getString(4));
				bean.setQuantities(rs.getString(5));
				bean.setDescription(rs.getString(6));
				bean.setPhoto(rs.getBlob(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				bean.setCategory(rs.getString(12));
				bean.setName(rs.getString(13));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of items");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

}
