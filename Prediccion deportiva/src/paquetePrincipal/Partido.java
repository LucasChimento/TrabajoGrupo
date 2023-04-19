package paquetePrincipal;



public class Partido {
	private Equipo[] equipos=new Equipo[2];
	private int nroPartido;
	private int golEquipo1;
	private int golEquipo2;
	
	public Partido(Equipo equipo1, int golE1,int golE2,Equipo equipo2)
	{
		equipos[0]=equipo1;
		equipos[1]=equipo2;
		golEquipo1=golE1;
		golEquipo2=golE2;
	}
	public Equipo getEquipo(int i)
	{
		switch(i)
		{
			case 1:
				return equipos[0];
			case 2:
				return equipos[1];
				default:
					return null;
		}
	}
	public int getGolesEquipo1() {return golEquipo1;}
	public int getGolesEquipo2() {return golEquipo2;}
	public void setNroPartido(int nro) {nroPartido=nro;}
	public int getNroPartido() {return nroPartido;}
	
	public ResultadoPartido getResultado(Equipo equipo)
	{
		if(golEquipo1==golEquipo2) return ResultadoPartido.EMPATA;
		if(equipos[0].equals(equipo))
		{
			if(golEquipo1>golEquipo2)return ResultadoPartido.GANA;
			else return ResultadoPartido.PIERDE;
		}
		else if(equipos[1].equals(equipo))
		{
			if(golEquipo2>golEquipo1)return ResultadoPartido.GANA;
			else return ResultadoPartido.PIERDE;
		}
		else
		{
			System.out.println("Partido equivocado! el partido es entre :"+
								equipos[0].getDatos()+" y "	+ equipos[1].getDatos());
			return ResultadoPartido.EMPATA;
		}
	}
	public String getDatosConsola()
	{
		return "Partido nro: "+nroPartido+"\n"+ControlArchivos.getEncabezadoRonda()+
				equipos[0].getNombre()+"\t"+golEquipo1+"\t"+golEquipo2+"\t"+equipos[1].getNombre();
	}
}
