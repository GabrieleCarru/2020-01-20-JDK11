package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artista;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getAllRole() {
		
		String sql = "select distinct(role) " + 
				"from authorship "; 
		
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString("role"));
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Artista> getArtistiByRole(String role) {
		String sql = "select distinct(a.artist_id) as id, a.name as name " + 
				"from authorship au, artists a " + 
				"where au.artist_id = a.artist_id " + 
				"and au.role = ? ";
		
		Map<Integer, Artista> result = new HashMap<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Artista a = new Artista(rs.getInt("id"), rs.getString("name"));
				result.put(rs.getInt("id"), a);
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getCollegamentiAndArtisti(String role) {
		String sql = "select au1.artist_id as id1, a1.name as nome1, au2.artist_id as id2, a2.name as nome2, count(distinct(eo1.exhibition_id)) as peso " + 
				"from artists a1, artists a2, authorship au1, authorship au2, exhibition_objects eo1, exhibition_objects eo2 " + 
				"where au1.object_id = eo1.object_id and au2.object_id = eo2.object_id " + 
				"and a1.artist_id = au1.artist_id and a2.artist_id = au2.artist_id " + 
				"and au1.role = ? and au2.role = ? " + 
				"and eo1.exhibition_id = eo2.exhibition_id " + 
				"and au1.artist_id > au2.artist_id " + 
				"group by au1.artist_id, a1.name, au2.artist_id, a2.name ";
		
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, role);
			st.setString(2, role);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Adiacenza a = new Adiacenza(rs.getInt("id1"), 
											rs.getString("nome1"), 
											rs.getInt("id2"), 
											rs.getString("nome2"), 
											rs.getInt("peso"));
				result.add(a);
			}
			
			conn.close();
			return result;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
