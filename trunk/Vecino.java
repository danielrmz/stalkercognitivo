public class Vecino {

	private Persona p;
	private int nivel;
	
	public Vecino(Persona p, int nivel){
		this.p = p;
		this.nivel=nivel;
	}

	public Persona getP() {
		return p;
	}

	public void setP(Persona p) {
		this.p = p;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public String toString(){
		return p.toString()+ " " + nivel;
	}
}
