package in.co.fennel.project.model;


import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import in.co.fennel.project.bean.AdvertisementBean;
import in.co.fennel.project.bean.ItemBean;
import in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.exception.DatabaseException;
import in.co.fennel.project.exception.DuplicateRecordException;
import in.co.fennel.project.util.JDBCDataSource;



public class AdvertisementModel {
	
	//Getting records by id
		public AdvertisementBean getRecordByID(long pk) throws ApplicationException {
		
			StringBuffer sql = new StringBuffer("SELECT * FROM F_ADVERTISE WHERE ID=?");
			AdvertisementBean bean = null;
			Connection conn = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new AdvertisementBean();
					bean.setId(rs.getLong(1));
					bean.setAdv_price(rs.getString(2));
					bean.setAdv_name(rs.getString(3));
					bean.setRecipient_adv(rs.getString(4));
					bean.setSender(rs.getString(5));
					bean.setAdv_category(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDatetime(rs.getTimestamp(9));
					bean.setModifiedDatetime(rs.getTimestamp(10));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			
				throw new ApplicationException("Exception : Exception in getting adverstise by id");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			
			return bean;
		}

	public long addAdvertisement(AdvertisementBean bean) throws ApplicationException, DuplicateRecordException {

		long pk = add(bean);

		return pk;
	}
	//Add advertisement
	public long add(AdvertisementBean bean) throws ApplicationException {
		Connection con = null;
		int pk = 0;
		
		try {
			con = JDBCDataSource.getConnection();
			pk = nextPK();
			con.setAutoCommit(false);
			String sql = "INSERT INTO F_ADVERTISE  VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, pk);
			ps.setString(2, bean.getAdv_price());
			ps.setString(3, bean.getAdv_name());
			ps.setString(4, bean.getRecipient_adv());
			ps.setString(5, bean.getSender());
			ps.setString(6, bean.getAdv_category());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setBlob(11,bean.getImage());
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
			throw new ApplicationException("Exception : Exception in add Adverstise");
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
			PreparedStatement pstmt = con.prepareStatement("SELECT MAX(ID) FROM F_ADVERTISE");
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
	
	public void update(AdvertisementBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE F_ADVERTISE SET ADV_PRICE=?,ADV_NAME=?,RECIPIENT_ADV=?,SENDER=?,ADV_CATEGORY=?,"
							+ "CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=?,image=? WHERE ID=?");
			ps.setString(1, bean.getAdv_price());
			ps.setString(2, bean.getAdv_name());
			ps.setString(3, bean.getRecipient_adv());
			ps.setString(4, bean.getSender());
			ps.setString(5, bean.getAdv_category());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());
			ps.setTimestamp(9, bean.getModifiedDatetime());
			ps.setBlob(10,bean.getImage());
			ps.setLong(11, bean.getId());
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
			throw new ApplicationException("Exception in updating Adverstise ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from F_ADVERTISE");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				AdvertisementBean bean = new AdvertisementBean();
				bean.setId(rs.getLong(1));
				bean.setAdv_price(rs.getString(2));
				bean.setAdv_name(rs.getString(3));
				bean.setRecipient_adv(rs.getString(4));
				bean.setSender(rs.getString(5));
				bean.setAdv_category(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
				e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting list of Adverstise");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
	public void delete(AdvertisementBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM F_ADVERSTISE WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Adverstise");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	// Search
		public List search(AdvertisementBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		public List search(AdvertisementBean bean, int pageNo, int pageSize) throws ApplicationException {

			StringBuffer sql = new StringBuffer("SELECT * FROM F_ADVERTISE WHERE 1=1");
			if (bean != null) {
				if (bean.getId() > 0) {
					sql.append(" AND id = " + bean.getId());
				}
				if (bean.getAdv_name() != null && bean.getAdv_name().length() > 0) {
					sql.append(" AND adv_name LIKE '" + bean.getAdv_name() + "%'");
				}
				
				if (bean.getAdv_category() != null && bean.getAdv_category().length() > 0) {
					sql.append(" AND Adv_category LIKE '" + bean.getAdv_category() + "%'");
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
					bean = new AdvertisementBean();
					bean.setId(rs.getLong(1));
					bean.setAdv_price(rs.getString(2));
					bean.setAdv_name(rs.getString(3));
					bean.setRecipient_adv(rs.getString(4));
					bean.setSender(rs.getString(5));
					bean.setAdv_category(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDatetime(rs.getTimestamp(9));
					bean.setModifiedDatetime(rs.getTimestamp(10));
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
	
	public static void getImage(long id,HttpServletResponse response) {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from F_ADVERTISE where id=?");
		    ps.setLong(1, id);
		    ResultSet rs = ps.executeQuery();
		 
		    if(rs.next()){
		        Blob blob = rs.getBlob("image");
		        byte byteArray[] = blob.getBytes(1, (int)blob.length());
		 
		        response.setContentType("image/gif");
		        OutputStream os = response.getOutputStream();
		        os.write(byteArray);
		        os.flush();
		        os.close();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
