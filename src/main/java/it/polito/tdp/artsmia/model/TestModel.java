package it.polito.tdp.artsmia.model;

import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model;
		model = new Model();
		String role = "Designer";
		Map<Integer, Artista> map = model.getArtistiByRole(role);
		
		for(Map.Entry<Integer, Artista> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " ---> " + entry.getValue());
		}
		
	}

}
