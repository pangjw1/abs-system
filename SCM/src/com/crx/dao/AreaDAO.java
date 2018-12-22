package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Area;
import com.crx.model.Goods;
import com.crx.model.Page;
import com.crx.utils.DBService;

public class AreaDAO implements IBaseDAO<Area>{
   private GoodsDAO gdao = new GoodsDAO();
	@Override
	public void insert(Area t) {
		// TODO Auto-generated method stub
		String sql="insert into area values(null,"+t.getGoods().getId()+",'"+t.getName()+"',"+t.getMinprice()+","+t.getMaxprice()+",'')";
		try {
			   Statement stmt = DBService.getStmt();
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void update(Area t) {
		// TODO Auto-generated method stub
		String sql="update area set name='"+t.getName()+"',minprice="+t.getMinprice()+",maxprice='"+t.getMaxprice()+"' where id="+t.getId();
		try {
			 Statement stmt = DBService.getStmt();
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
		String sql ="delete from area where id="+id;
		try {
			   Statement stmt = DBService.getStmt();
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public Area findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from area where id="+id;
		List<Area> as = findBySQL(sql);
		return as.size()>0?as.get(0):null;
	}

	@Override
	public List<Area> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from area";
		return findBySQL(sql);
	}

	@Override
	public List<Area> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Area> findBySQL(String sql) {
		System.out.println(sql);
		List<Area> as = new ArrayList<>();
		try {
			 Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Area a = new Area();
				a.setId(rs.getInt(1));
				a.setGoods(gdao.findById(rs.getInt(2)));
				a.setName(rs.getString(3));
				a.setMinprice(rs.getDouble(4));
				a.setMaxprice(rs.getDouble(5));
				a.setDemo(rs.getString(6));
				as.add(a);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return as;
	}
	
//	∑÷“≥”√
	public int findCount(String ids){
		String sql="";
		if(ids==null ||ids.equals("")){
			sql="select count(*) from area";
		}else{
			sql="select count(*) from area where gid in("+ids+")";
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


	public List<Area> findByPage(Page page,String ids) {
		// TODO Auto-generated method stub
		String sql="";
		if(ids==null||ids.equals("")){
			sql="select * from area order by name desc limit "+page.getStart()+","+page.getSize();
		}else{
			sql="select * from area where gid in("+ids+") order by name desc limit "+page.getStart()+","+page.getSize();
		}
		return findBySQL(sql); 
	}

	public List<Area> findGoodsAndArea(Goods goods,String name) {
		// TODO Auto-generated method stub
		String sql="select * from area where gid="+goods.getId()+" and name='"+name+"'";
		System.out.println(sql);
		return findBySQL(sql);
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from area where id in("+ids+")";
		try {
			 Statement stmt = DBService.getStmt();
				stmt.executeUpdate(sql);
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Area> findGoodsAndArea(Goods goods, String name, String name2) {
		// TODO Auto-generated method stub
		String sql="select * from area where gid="+goods.getId()+" and name='"+name+"' and name!='"+name2+"'";
		System.out.println(sql);
		return findBySQL(sql);
	}

	public void deleteSomeByGID(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from area where gid in("+ids+")";
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
