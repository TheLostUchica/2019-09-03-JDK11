package it.polito.tdp.food.model;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.*;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(int c) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getVertex(c));
		
		for(String s1 : this.grafo.vertexSet()) {
			for(String s2 : this.grafo.vertexSet()) {
				if(s1.compareTo(s2)<0) {
					int i = dao.CiboinComune(s1, s2);
					if(i>0) {
						Graphs.addEdgeWithVertices(this.grafo, s1, s2, i);
					}
				}
			}
		}
	}
	
	public Set<String> setCombo(){
		return this.grafo.vertexSet();
	}
	
	public TreeMap<String, Integer> getConnessa(String S){
		
		TreeMap<String, Integer> map = new TreeMap<>();
		//ConnectivityInspector<String, DefaultWeightedEdge> iter = new ConnectivityInspector<String, DefaultWeightedEdge>(this.grafo);
		for(String s : Graphs.neighborListOf(this.grafo, S)) {
			map.put(s,(int)this.grafo.getEdgeWeight(this.grafo.getEdge(s, S)));
		}
		
		return map;
	}
	
	public Graph<String, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}
	
	
	
	List<String> path;
	int N;
	String partenza;
	int best;
	
	private void ricorsione(String s, int n) {
		this.N = n;
		this.partenza = s;
		path = new LinkedList<>();
		LinkedList<String> parziale = new LinkedList<String>();
		parziale.add(partenza);
		cerca(parziale,0);
	}
	
	private void cerca(LinkedList<String> parziale, int costo) {
		
		if(parziale.size()==N) {
			if(costo>best) {
				path = new LinkedList<>(parziale);
			}
			return;
		}
		
		for(String s : Graphs.neighborListOf(this.grafo,parziale.getLast())) {
			if(!parziale.contains(s)) {
				
				int c = (int)this.grafo.getEdgeWeight(this.grafo.getEdge(s, parziale.getLast()));
				costo += c;
				parziale.add(s);
				
				cerca(parziale, costo);
				
				parziale.remove(s);
				costo -= c;
			}
		}
	}
	
	public List<String> getBestPath(String s, int n){
		this.ricorsione(s, n);
		return this.path;
	}

	
	
	
	
	
	
}
