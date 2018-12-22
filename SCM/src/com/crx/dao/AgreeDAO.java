package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Agree;
import com.crx.model.Goods;
import com.crx.utils.DBService;

public class AgreeDAO implements IBaseDAO<Agree>{

	@Override
	public void insert(Agree t) {
		// TODO Auto-generated method stub
		String sql="insert into agree values(null,'"+t.getCont()+"')";
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
	public void update(Agree t) {
		// TODO Auto-generated method stub
		String sql="update agree set cont='"+t.getCont()+"' where id="+t.getId();
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
		
	}

	@Override
	public Agree findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agree> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from agree";
		return findBySQL(sql);
	}

	@Override
	public List<Agree> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agree> findBySQL(String sql) {
		// TODO Auagreeto-generated method stub
		List<Agree> ags = new ArrayList<>();
		try {
			 Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Agree g = new Agree();
				g.setId(rs.getInt(1));
				g.setCont(rs.getString(2));
				ags.add(g);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ags;
	}

}
