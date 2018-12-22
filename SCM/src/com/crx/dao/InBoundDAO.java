package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Inbound;
import com.crx.model.Loss;
import com.crx.model.Page;
import com.crx.model.Purchase;
import com.crx.model.User;
import com.crx.utils.DBService;

public class InBoundDAO implements IBaseDAO<Inbound>{
	private PurchaseDAO pdao = new PurchaseDAO();
	@Override
	public void insert(Inbound t) {
		// TODO Auto-generated method stub
		String sql = "insert into inbound values(null,"+t.getPur().getId()+",'"+t.getIndate()+"',"+t.getNum()+",'"+t.getDemo()+"')";
		try {
			Statement stmt =DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Inbound t) {
		// TODO Auto-generated method stub
		String sql ="update inbound set pid="+t.getPur().getId()+",indate='"+t.getIndate()+"',num="+t.getNum()+",demo='"+t.getDemo()+"' where id="+t.getId();
		System.out.println(sql);
		try {
			Statement stmt =DBService.getStmt();
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
		String sql="delete from inbound where id="+id;
		try {
			Statement stmt =DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Inbound findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inbound> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from inbound";
		return findBySQL(sql);
	}

	@Override
	public List<Inbound> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inbound> findBySQL(String sql) {
		// TODO Auto-generated method stub
		List<Inbound> ins = new ArrayList<>();
		try {
			Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Inbound in = new Inbound();
				in.setId(rs.getInt(1));
				in.setPur(pdao.findById(rs.getInt(2)));
				in.setIndate(rs.getString(3));
				in.setNum(rs.getInt(4));
				in.setDemo(rs.getString(5));
				ins.add(in);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

	public void deleteByPID(int id) {
		// TODO Auto-generated method stub
		String sql="delete from inbound where pid="+id;
		try {
			Statement stmt =DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Inbound findByPId(int id) {
		// TODO Auto-generated method stub
		String sql="select * from inbound where pid="+id;
		System.out.println(sql);
		List<Inbound> ins =findBySQL(sql);
		return ins.size()>0?ins.get(0):null;
	}

	public List<Inbound> findByDate(String st, String en) {
		// TODO Auto-generated method stub
		String sql="select * from inbound where indate between '"+st+"' and '"+en+"'";
		return findBySQL(sql);
	}

	


	

}
