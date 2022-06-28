package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	Graph<String,DefaultWeightedEdge> grafo; 
	List<String> best;
	String partenza;
	String arrivo;
	
	
	public Model() {
		this.dao=new EventsDao();
	}
	public List<String> cat(){
		return dao.listAllCat();
	}
	
	public void creaGrafo(String cat,int mese) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.listAllNodi(cat, mese));
		for(Adiacenza a:dao.getArchi(cat, mese)) {
			if(this.grafo.containsVertex(a.reato1) && this.grafo.containsVertex(a.reato1)) {
				Graphs.addEdge(this.grafo, a.reato1, a.reato2, a.peso);
			}
		}
	}
	


	public int nVert() {	
	return this.grafo.vertexSet().size();
	}

	public int nArch() {	
	return this.grafo.edgeSet().size();
	}

	public Set<String> vertici(){
	return this.grafo.vertexSet();
	}
	
	public List<Adiacenza> pesoMedio(){
		double media=0.0;
		int ndiv=0;
		List<Adiacenza> result=new ArrayList<>();
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {
			media+=this.grafo.getEdgeWeight(e);
			ndiv++;
		}
		
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>(double)(media/ndiv)) {
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),this.grafo.getEdgeWeight(e)));
				
			}
			
		}

		return result;
		
	}
	
	public List<String> ricorsione(String partenza,String arrivo){
		List<String> parziale=new LinkedList<>();
		this.partenza=partenza;
		this.arrivo=arrivo;
		this.best=new ArrayList<>();
		parziale.add(partenza);
		cerca(parziale);
		return best;
		
	}
	private void cerca(List<String> parziale) {
		if(parziale.get(parziale.size()-1).equalsIgnoreCase(arrivo)) {
			if(parziale.size()>best.size()) {
			best=new LinkedList<>(parziale);
			return;
			}
		}
		
		List<String> vicini=Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		for(String s: vicini) {
			if(aggiuntaValida(s,parziale)) {
				parziale.add(s);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	private boolean aggiuntaValida(String s, List<String> parziale) {
		if(!parziale.contains(s))
			return true;
		
		return false;
	}
	
	public List<Adiacenza> getArchi(){
		List<Adiacenza> result=new ArrayList<>();
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {			
			result.add(new Adiacenza(this.grafo.getEdgeSource(e),
				this.grafo.getEdgeTarget(e),this.grafo.getEdgeWeight(e)));
			
		}
		return result;

	}
	
}
