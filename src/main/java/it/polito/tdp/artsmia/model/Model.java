package it.polito.tdp.artsmia.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenza;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	private Graph<Artista, DefaultWeightedEdge> graph;
	private List<Adiacenza> adiacenze;
	private Map<Integer, Artista> artisti;
	
	public Model() {
		dao = new ArtsmiaDAO();
	}
	
	public List<String> getAllRole() {
		return dao.getAllRole();
	}
	
	public void creaGrafo(String role) {
		// Creo il grafo
		graph = new SimpleWeightedGraph<Artista, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		adiacenze = dao.getCollegamentiAndArtisti(role);
		artisti = dao.getArtistiByRole(role);
		
		for(Adiacenza a : adiacenze) {
			// Aggiungo vertici
			if(!graph.containsVertex(artisti.get(a.getId1()))) {
				graph.addVertex(artisti.get(a.getId1()));
				a.setNome1(artisti.get(a.getId1()).getArtist_name());
			}
	
			if(!graph.containsVertex(artisti.get(a.getId2()))) {
				graph.addVertex(artisti.get(a.getId2()));
				a.setNome2(artisti.get(a.getId2()).getArtist_name());
			}
				
			// Aggiungo arco
			if(!graph.containsEdge(artisti.get(a.getId1()), artisti.get(a.getId2()))) {
				Graphs.addEdge(this.graph, artisti.get(a.getId1()), artisti.get(a.getId2()), 
				a.getPeso());
			}
		}
	}
	
	public List<Adiacenza> getAdiacenze() {
		Collections.sort(this.adiacenze);
		return this.adiacenze;
	}
	
	public int getNumberVertex() {
		return this.graph.vertexSet().size();
	}
	
	public int getNumberEdges() {
		return this.graph.edgeSet().size();
	}	
}
