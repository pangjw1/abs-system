package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Inbound;
import com.crx.model.Loss;
import com.crx.model.Page;
import com.crx.model.Sale;
import com.crx.utils.DBService;

public class SaleDAO implements IBaseDAO<Sale>{
   private GoodsDAO gdao = new GoodsDAO();
	
   public Sale findByGId(int id) {
		// TODO Auto-generated method stub
		String sql="select * from sale where g_id="+id;
		List<Sale> sales = findBySQL(sql);
		return sales.size()>0?sales.get(0):null;
	}
   
	@Override
	public void insert(Sale t) {
		// TODO Auto-generated method stub
		String sql="insert into sale values(null,"+t.getGoods().getId()+","+t.getNum()+",'"+t.getOutdate()+"',"+t.getPrice()+",'"+t.getDemo()+"')";
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
	public void update(Sale t) {
		// TODO Auto-generated method stub
		String sql="update sale set num="+t.getNum()+" ,price="+t.getPrice()+",demo='"+t.getDemo()+"' where id="+t.getId();
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
		String sql="delete from sale where id="+id;
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
	public Sale findById(int id) {
		// TODO Auto-generated method stub
	String sql="select * from sale where id="+id;
	List<Sale> sals = findBySQL(sql);
	return sals.size()>0?sals.get(0):null;
	}

	@Override
	public List<Sale> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from sale";
		return findBySQL(sql);
	}

	@Override
	public List<Sale> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sale> findBySQL(String sql) {
		// TODO Auto-generated method stub
		List<Sale> sales = new ArrayList<>();
		try {
			Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Sale sa = new Sale();
				sa.setId(rs.getInt(1));
				sa.setGoods(gdao.findById(rs.getInt(2)));
				sa.setNum(rs.getInt(3));
				sa.setOutdate(rs.getString(4));
				sa.setPrice(rs.getDouble(5));
				sa.setDemo(rs.getString(6));
				sales.add(sa);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sales;
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from sale where id in("+ids+")";
		try {
			 Statement stmt = DBService.getStmt();
				stmt.executeUpdate(sql);
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Sale> findByDate(String st, String en) {
		// TODO Auto-generated method stub
			String sql="select * from sale where outdate between '"+st+"' and '"+en+"'";
			return findBySQL(sql);
	}
	public int findCount(String ids){
		String sql="";
		if(ids==null ||ids.equals("")){
			sql="select count(*) from sale";
		}else{
			sql="select count(*) from sale where g_id in("+ids+")";
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


	public List<Sale> findByPage(Page page,String ids) {
		// TODO Auto-generated method stub
		String sql="";
		if(ids==null||ids.equals("")){
			sql="select * from sale order by outdate desc limit "+page.getStart()+","+page.getSize();
		}else{
			sql="select * from sale where   g_id in("+ids+") order by outdate desc limit "+page.getStart()+","+page.getSize();
		}
		return findBySQL(sql); 
	}

	public void deleteSomeByGID(String newids) {
		// TODO Auto-generated method stub
		String sql="delete from sale where g_id in("+newids+")";
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
