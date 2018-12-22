package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.SupCheck;
import com.crx.model.Supplier;
import com.crx.model.User;
import com.crx.utils.DBService;

public class SupCheckDAO implements IBaseDAO<SupCheck>{
  private Statement stmt= DBService.getStmt();
  private SupplierDAO sdao = new SupplierDAO();
	@Override
	public void insert(SupCheck t) {
		// TODO Auto-generated method stub
		
		String sql="insert into supcheck values(null,'"+t.getSup().getId()+"','"+t.getPrice()+"','"+t.getSample()+"','"+t.getSat()+"','"+t.getDelivery()+"','"+t.getF_data()+"','"+t.getQuality()+"','"+t.getWdate()+"','')";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(SupCheck t) {
		// TODO Auto-generated method stub
		String sql="update supcheck set price='"+t.getPrice()+"',sample='"+t.getSample()+"',sat='"+t.getSat()+"',delivery='"+t.getDelivery()+"',f_data='"+t.getF_data()+"',quality='"+t.getQuality()+"',wdate='"+t.getWdate()+"',demo='"+t.getDemo()+"' where sid="+t.getId();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		/*String sql="delete from supplier where id="+id;
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	@Override
	public SupCheck findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from supcheck where id="+id;
		return findBySQL(sql).get(0);
	}
	public SupCheck findBySId(int sid) {
		// TODO Auto-generated method stub
		String sql="select * from supcheck where sid="+sid;
		return findBySQL(sql).get(0);
	}
	@Override
	public List<SupCheck> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from supcheck";
		return findBySQL(sql);
	}

	@Override
	public List<SupCheck> findByName(String name) {
		// TODO Auto-generated method stub
		String sql= "select * from supcheck where name like '%"+name+"%'";
		
		return findBySQL(sql);
	}

	@Override
	public List<SupCheck> findBySQL(String sql) {
		// TODO Auto-generated method stub
		ResultSet rs;
		List<SupCheck> sups = new ArrayList<>();
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
			SupCheck sc = new SupCheck();
			  sc.setId(rs.getInt(1));
			  sc.setSup(sdao.findById(rs.getInt(2)));
			  sc.setPrice(rs.getString(3));
			  sc.setSample(rs.getString(4));
			  sc.setSat(rs.getString(5));
			  sc.setDelivery(rs.getString(6));
			  sc.setF_data(rs.getString(7));
			  sc.setQuality(rs.getString(8));
			  sc.setWdate(rs.getString(9));
			  sc.setDemo(rs.getString(10));
			 sups.add(sc); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sups;
	}

	public void deleteBySID(String id) {
		// TODO Auto-generated method stub
		String sql="delete from  supcheck where sid="+id;
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from supcheck where id in("+ids+")";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
   /* public SupCheck findCheckBySupId(int id){
    	String sql="select * from supcheck where sid="+id;
    	System.out.println(sql);
       return findBySQL(sql).get(0);
    }*/
	

}
