package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Goods;
import com.crx.model.Page;
import com.crx.model.SupCheck;
import com.crx.model.Supplier;
import com.crx.model.User;
import com.crx.utils.DBService;

public class SupplierDAO implements IBaseDAO<Supplier>{

	@Override
	public void insert(Supplier t) {
		// TODO Auto-generated method stub
		String sql="insert into supplier values(null,'"+t.getName()+"','"+t.getAddress()+"','"+t.getTel()+"','"+t.getEmail()+"','"+t.getFax()+"','"+t.getLinkman()+"','"+t.getLink_tel()+"','"+t.getStatus()+"','"+t.getDemo()+"')";
		try {
			Statement stmt= DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Supplier t) {
		// TODO Auto-generated method stub
		String sql="update supplier set name='"+t.getName()+"',address='"+t.getAddress()+"',tel='"+t.getTel()+"',email='"+t.getEmail()+"',fax='"+t.getFax()+"',linkman='"+t.getLinkman()+"',linkman_tel='"+t.getLink_tel()+"',status='"+t.getStatus()+"' where id="+t.getId();
		try {
			Statement stmt= DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String sql="delete from supplier where id="+id;
		try {
		Statement stmt= DBService.getStmt();
		stmt.executeUpdate(sql);
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Supplier findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from supplier where id="+id;
		List<Supplier> sups = findBySQL(sql);
		return sups.size()>0?sups.get(0):null;
	}

	@Override
	public List<Supplier> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from supplier";
		return findBySQL(sql);
	}

	@Override
	public List<Supplier> findByName(String name) {
		// TODO Auto-generated method stub
		String sql= "select * from supplier where name like '%"+name+"%'";
		System.out.println(sql);
		return findBySQL(sql);
	}
	public List<Supplier> findByFullName(String name) {
		// TODO Auto-generated method stub
		String sql= "select * from supplier where name ='"+name+"' and status!='审核未通过'";
		System.out.println(sql);
		return findBySQL(sql);
	}
	@Override
	public List<Supplier> findBySQL(String sql) {
		// TODO Auto-generated method stub
		 Statement stmt= DBService.getStmt();
		List<Supplier> sups = new ArrayList<>();
		try {
			ResultSet	rs = stmt.executeQuery(sql);
			while(rs.next()){
				Supplier sup = new Supplier();
			   sup.setId(rs.getInt(1));
			   sup.setName(rs.getString(2));
			   sup.setAddress(rs.getString(3));
			   sup.setTel(rs.getString(4));
			   sup.setEmail(rs.getString(5));
			   sup.setFax(rs.getString(6));
			   sup.setLinkman(rs.getString(7));
			   sup.setLink_tel(rs.getString(8));
			   sup.setStatus(rs.getString(9));
			  sups.add(sup); 
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sups;
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		//此处关联  先删从表  后写
		String sql1="delete from supcheck where sid in("+ids+")";
		String sql="delete from supplier where id in("+ids+")";
		System.out.println(sql1);
		System.out.println(sql);
		try {
			Statement stmt= DBService.getStmt();
			stmt.addBatch(sql1);
			stmt.addBatch(sql);
			stmt.executeBatch();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCheckSup(Supplier sup) {
		// TODO Auto-generated method stub
		String sql="update supplier set status='"+sup.getStatus()+"' where id="+sup.getId();
		try {
			Statement stmt= DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Supplier> findSupNameByCon(String name) {
		// TODO Auto-generated method stub
		String sql= "select * from supplier where name like '%"+name+"%' and status='已审核通过'";
		return findBySQL(sql);
	}

	public List<Supplier> findSupFullNameByCon(String name) {
		// TODO Auto-generated method stub
		String sql= "select * from supplier where name = '"+name+"' and status='已审核通过'";
		return findBySQL(sql);
	}

	
//	分页用
	public int findCount(String name){
		String sql="";
		if(name==null ||name.equals("")){
			sql="select count(*) from supplier";
		}else{
			sql="select count(*) from supplier where name like '%"+name+"%'";
			
		}
		int count=0;
		try {
		Statement stmt = DBService.getStmt();
		ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
			count = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return count;
		
	}


	public List<Supplier> findByPage(Page page,String name) {
		// TODO Auto-generated method stub
		String sql="";
		if(name==null||name.equals("")){
			sql="select * from supplier limit "+page.getStart()+","+page.getSize();
		}else{
			sql="select * from supplier where name like '%"+name+"%' limit "+page.getStart()+","+page.getSize();
		}
		return findBySQL(sql); 
	}

	public List<Supplier> findByIds(String ids) {
		// TODO Auto-generated method stub
		 String sql="select * from supplier where id in("+ids+")";
		return findBySQL(sql);
	}
}
