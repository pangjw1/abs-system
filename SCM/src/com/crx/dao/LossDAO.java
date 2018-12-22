package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Inbound;
import com.crx.model.Loss;
import com.crx.model.Page;
import com.crx.model.User;
import com.crx.utils.DBService;

public class LossDAO implements IBaseDAO<Loss>{
   private GoodsDAO gdao = new GoodsDAO();
   private PurchaseDAO pdao = new PurchaseDAO();
	@Override
	public void insert(Loss t) {
		// TODO Auto-generated method stub
		String sql="";
		if(t.getPur()==null)
			sql = "insert into loss values(null,"+t.getGoods().getId()+","+t.getNum()+",'"+t.getLossdate()+"','"+t.getReason()+"',null)";
		else
			sql = "insert into loss values(null,"+t.getGoods().getId()+","+t.getNum()+",'"+t.getLossdate()+"','"+t.getReason()+"',"+t.getPur().getId()+")";
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
	public void update(Loss t) {
		// TODO Auto-generated method stub
		String sql="update loss set num="+t.getNum()+" where id="+t.getId();
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
		String sql="delete from loss where id="+id;
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
	public Loss findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from loss where id="+id;
		List<Loss> los = findBySQL(sql);
		return los.size()>0?los.get(0):null;
	}
	public Loss findByGId(int id) {
		// TODO Auto-generated method stub
		String sql="select * from loss where g_id="+id;
		List<Loss> los = findBySQL(sql);
		return los.size()>0?los.get(0):null;
	}


	@Override
	public List<Loss> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from loss";
		return findBySQL(sql);
	}

	@Override
	public List<Loss> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Loss> findBySQL(String sql) {
		// TODO Auto-generated method stub
		List<Loss> ins = new ArrayList<>();
		try {
			Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Loss lo = new Loss();
				lo.setId(rs.getInt(1));
				lo.setGoods(gdao.findById(rs.getInt(2)));
				lo.setNum(rs.getInt(3));
				lo.setLossdate(rs.getString(4));
				lo.setReason(rs.getString(5));
				lo.setPur(pdao.findById(rs.getInt(6)));
				ins.add(lo);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins;
	}

	public Loss findByPId(int id) {
		// TODO Auto-generated method stub
		String sql="select * from loss where pid="+id;
		System.out.println(sql);
		List<Loss> losses =findBySQL(sql);
		return losses.size()>0?losses.get(0):null;
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from loss where id in("+ids+")";
		try {
			 Statement stmt = DBService.getStmt();
				stmt.executeUpdate(sql);
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Loss> findByDate(String st, String en) {
		// TODO Auto-generated method stub
			String sql="select * from loss where lossdate between '"+st+"' and '"+en+"'";
			return findBySQL(sql);
	}
	
	public int findCount(String ids){
		String sql="";
		if(ids==null ||ids.equals("")){
			sql="select count(*) from loss";
		}else{
			sql="select count(*) from loss where g_id in("+ids+")";
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


	public List<Loss> findByPage(Page page,String ids) {
		// TODO Auto-generated method stub
		String sql="";
		if(ids==null||ids.equals("")){
			sql="select * from loss order by lossdate desc limit "+page.getStart()+","+page.getSize();
		}else{
			sql="select * from loss where   g_id in("+ids+") order by lossdate desc limit "+page.getStart()+","+page.getSize();
		}
		return findBySQL(sql); 
	}

	public void deleteSomeByGID(String newids) {
		// TODO Auto-generated method stub
		String sql="delete from loss where g_id in("+newids+")";
		try {
			 Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
