package paquetePrincipal;

public class Pronostico {
	private int nroRonda;
	private Partido partido;
	private Equipo equipo;
	private ResultadoPartido res;
	
	public Pronostico(int nroRonda,Partido partido, Equipo equipo,ResultadoPartido res)
	{
		this.partido=partido;
		this.equipo=equipo;
		this.res=res;
		this.nroRonda=nroRonda;
	}
	public int getNroRonda() {return nroRonda;}
	public Partido getPartido() {return partido;}
	public Equipo getEquipo() {return equipo;}
	public ResultadoPartido getResultado(){return res;}
}
