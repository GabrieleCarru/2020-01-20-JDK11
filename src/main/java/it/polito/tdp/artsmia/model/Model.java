package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
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
	private List<Artista> best;
	
	public Model() {
		dao = new ArtsmiaDAO();
	}
	
	public List<String> getAllRole() {
		return dao.getAllRole();
	}
	
	public Map<Integer, Artista> getArtistiByRole(String role) {
		return dao.getArtistiByRole(role);
	}
	
	public void creaGrafo(String role) {
		// Creo il grafo
		graph = new SimpleWeightedGraph<Artista, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		artisti = dao.getArtistiByRole(role);
		adiacenze = dao.getCollegamentiAndArtisti(role);
		
		for(Adiacenza a : adiacenze) {
			// Aggiungo vertici
			if(!graph.containsVertex(artisti.get(a.getId1()))) {
				graph.addVertex(artisti.get(a.getId1()));
				//a.setNome1(artisti.get(a.getId1()).getArtist_name());
			}
	
			if(!graph.containsVertex(artisti.get(a.getId2()))) {
				graph.addVertex(artisti.get(a.getId2()));
				//a.setNome2(artisti.get(a.getId2()).getArtist_name());
			}
				
			// Aggiungo arco
			if(!graph.containsEdge(artisti.get(a.getId1()), artisti.get(a.getId2()))) {
				Graphs.addEdge(this.graph, artisti.get(a.getId1()), artisti.get(a.getId2()), 
				a.getPeso());
			}
		}
	}
	
	public List<Artista> trovaPercorso(Integer artistaID) {
		List<Artista> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		parziale.add(artisti.get(artistaID));
		trovaRicorsivo(parziale, -1);
		return this.best;
	}
	
	private void trovaRicorsivo(List<Artista> parziale, int peso) {
		
		Artista ultimo = parziale.get(parziale.size()-1);
		
		// Ottengo i vicini
		List<Artista> vicini = Graphs.neighborListOf(this.graph, ultimo);
		for(Artista vicino : vicini) {
			if(!parziale.contains(vicino) && peso == -1) {
				parziale.add(vicino);
				trovaRicorsivo(parziale, (int) this.graph.getEdgeWeight(this.graph.getEdge(ultimo, vicino)));
				parziale.remove(vicino);
			} else {
				if(!parziale.contains(vicino) && (this.graph.getEdgeWeight(this.graph.getEdge(ultimo, vicino)) == peso)) {
					parziale.add(vicino);
					trovaRicorsivo(parziale, peso);
					parziale.remove(vicino);
				}
			}
		}
		
		if(parziale.size() > best.size()) {
			this.best = new ArrayList<>(parziale);
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
	
	public boolean graphConteinsArtist(Integer artistaID) {
		if(this.graph.containsVertex(artisti.get(artistaID))) {
			return true;
		}
		return false;
	}	
}
