package paquetePrincipal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class ClasePrincipal {
	static ArrayList<Persona> personas= new ArrayList<Persona>();
	static HashMap<Integer,Ronda> rondas;
	
	public static void main(String[] args) {
		
		boolean salir=false;
		
		System.out.println("-Bienvenido!!");
		System.out.println("\n-A continuacion se ingresara o crearan los archivos necesarios.");
		// Introduccion y set de ruta para leer las rondas
		modificarRutas(0);
		modificarRutas(1);
		lecturaArchivos();

		do {
			salir=menu(ManejoConsola.pedirEntero("-MENU PRINCIPAL\n"+
				"1) Mostrar datos de una ronda.\n"+
				"2) Mostrar puntaje de una persona.\n"+
				"3) Mostrar tabla de puntajes.\n"+
				"4) Volver a cargar los archivos.\n"+
				"5) Cambiar de rutas.\n"+
				"6)Salir\n"
				));
		}while(!salir);
		
	}
	public static boolean menu(int opcion)	// Menu principal.
	{
		switch(opcion)
		{
		case 1:
				Ronda r=rondas.get(ManejoConsola.pedirEntero("-Ingrese el numero de ronda a mostrar: ")-1);
				if(r!=null)r.mostrarDatosConsola();
				else System.out.println("-No se pudo encontrar la ronda!.");
				return false;
		case 2:
			menuPersonas();
			return false;
		case 3:
			tablaPuntajes();
			return false;
		case 4:
			rondas=new HashMap<Integer,Ronda>();
			personas=new ArrayList<Persona>();
			Ronda.resetContador();
			lecturaArchivos();
		return false;
		case 5:
			for(int i=0; i<=1;i++)modificarRutas(i);
			return false;
		case 6:
			System.out.println("Saliendo...");
			return true;
		default:
			System.out.println("-Por favor ingrese alguna de las opciones listadas arriba.");
			return false;
		}
	}
	
	public static void menuPersonas()	// Menu que permite interactuar con la informacion de las personas.
	{
		System.out.println("\nMENU PERSONAS\n");
		int opcion1=-1;
		int opcion2;
		Persona p;
		int puntos;
		
		for(int i=0; i<personas.size();i++)
		{
			System.out.println((i+1)+") "+personas.get(i).getNombre()+".");
		}
		System.out.println((personas.size()+1)+") Volver.");
		do{
			opcion1=ManejoConsola.pedirEntero("-Elija una opcion: ");
			if(opcion1==personas.size()+1) return;
			opcion1-=1;
		}while(opcion1<0||opcion1>=personas.size());
		p=personas.get(opcion1);
		do {
			opcion2=ManejoConsola.pedirEntero("\n"+p.getNombre().toUpperCase()+"\n"
				+ "1) Ver puntos de una ronda en particular.\n"
				+ "2) Ver total de puntos.\n"
				+ "3) Elejir otra persona.\n"
				+ "4) Volver al menu principal.\n");
			Ronda r;
			switch(opcion2)
			{
			case 1:
				int nroRonda=ManejoConsola.pedirEntero("-Ingrese el numero de la ronda: ")-1;
				 r=rondas.get(nroRonda);
				if(r!=null)
				{
					if(ManejoConsola.preguntaSioNo("-Desea ver los detalles? s/n"))
					{
						puntos =p.getPuntosConDetalle(r);						
					}
					else
					{
						puntos =p.getPuntosSinDetalle(r);
					}
					System.out.println("-"+p.getNombre()+" tiene un total de: "+puntos);
				}
				else {
					System.out.println("-No se pudo encontrar la ronda especificada!");
				}
				break;
			case 2:
				puntos=0;
				
				boolean detalles=ManejoConsola.preguntaSioNo("-Desea ver los detalles? s/n");
				if(!detalles)
				{
					puntos=p.getPuntosTotales(rondas);
				}
				else {
					for(int i=0;i<=rondas.size();i++)
					{
						r=rondas.get(i);
						if(r!=null)
						{
							puntos+=p.getPuntosConDetalle(r);						
						}
					}
				}
				System.out.println("-"+p.getNombre()+" tiene un total de: "+puntos);
				break;
			case 3:
				menuPersonas();
				return;
			case 4:
				opcion2=-1;
				break;
			}
		
		} while(opcion2!=-1);
	}
	public static void modificarRutas(int opcion)	// Procedimiento que permite modificar las rutas para la lectura de archivos. Rondas(0), Pronosticos(1).							
	{
		boolean rutaEncontrada=false;
		switch(opcion)
		{
			case 0:
				System.out.println("\n-Por favor ingrese la direccion de donde desea cargar los archivos de las rondas.\n"+
					"El archivo debe llevar el nombre de \"Rondas.txt\"\n"+
					"En caso de que la ruta sea invalida o no se proporcione ninguna. Se crearan automaticamente en la ruta: \""+ControlArchivos.getRutaRondasPorDefecto()+"\".\n");
				do {
					String rutaDeseada=ManejoConsola.pedirTexto("-Ruta deseada: ");
					rutaEncontrada=ControlArchivos.setRutaRondas(rutaDeseada);
					if(!rutaEncontrada)
					{
						
						if(!ManejoConsola.preguntaSioNo("-Desea volver a intentar? s/n"))
						{
							rutaEncontrada=ControlArchivos.setRutaRondas(null);
							if(!ControlArchivos.crearDirectoriosPorDefecto(0))
							{
								System.out.println("-Hubo un error al crear el archivo por defecto! Saliendo del programa.");
								System.exit(0);
							}else
							{
								ControlArchivos.crearArchivosRondas();
								System.out.println("-Se ha creado en la ruta \""+ControlArchivos.getRutaRondasPorDefecto()+
										"\" un archivo de rondas. Por favor editelo.");	
							}
						}
					}
				}while(!rutaEncontrada);
			break;

			case 1:
					System.out.println("\n-Por favor ingrese la direccion de donde desea cargar los archivos de los pronosticos.\n"+
					"Dentro de la ruta esecificada debe haber otra con el nombre de la persona y dentro el archivo \"Pronosticos.txt\"\n"+
					"En caso de que la ruta sea invalida o no se proporcione ninguna. Se crearan automaticamente en la ruta: \""+ControlArchivos.getRutaPronosticosPorDefecto()+"\".\n");
					do 
					{
						rutaEncontrada=ControlArchivos.setRutaPronosticos(ManejoConsola.pedirTexto("Ruta deseada: "));
						if(!rutaEncontrada)
						{
							if(!ManejoConsola.preguntaSioNo("-Desea volver a intentar? s/n"))
							{
								rutaEncontrada=ControlArchivos.setRutaPronosticos(null);
								if(!ControlArchivos.crearDirectoriosPorDefecto(1))
								{
									System.out.println("-Hubo un error al crear el archivo por defecto! Saliendo del programa.");
									System.exit(0);
								}
								else
								{
									System.out.println("-Se ha creado en la ruta \""+ControlArchivos.getRutaPronosticos()+
											"\" un archivo de pronosticos.");
									if(ManejoConsola.preguntaSioNo("-Desea crear los archivos de pronosticos? s/n"))
									{
										int cantidad=ManejoConsola.pedirEntero("-Cuantas personas desea agregar?");
										for(int i=1; i<=cantidad;i++)
										{
											String nombre=ManejoConsola.pedirTexto("-Ingrese el nombre de la persona: ");
											Persona p=ControlArchivos.crearPronosticosPersona(nombre);
											if(p!=null)personas.add(p);
										}
									}
								
								}
							}
						}
					}while(!rutaEncontrada);
				break;
		}
	}
	public static void lecturaArchivos ()	// Procedimiento de lectura de los archivos de Rondas.txt y Pronosticos.txt .
	{	
		do {
			rondas=ControlArchivos.leerRondas();
			if(rondas==null)
			{
				if(!ManejoConsola.preguntaSioNo("-Pudo resolver el error? s/n")) 
				{
					if(ManejoConsola.preguntaSioNo("-Desea cambiar de directorio? s/n"))
					{
						modificarRutas(0);
					}
					else {
						if(ManejoConsola.preguntaSioNo("-Desea salir? s/n"))
						{
							System.out.println("Saliendo...");
							System.exit(0);
						}
					}
				}
			}
		}while(rondas==null);
		
		do {
			personas=ControlArchivos.leerPronosticos(rondas);
			if(personas==null)
			{
				if(!ManejoConsola.preguntaSioNo("-Pudo resolver el error? s/n")) 
				{
					if(ManejoConsola.preguntaSioNo("-Desea cambiar de directorio? s/n"))
					{
						modificarRutas(1);
					}
					else {
						if(ManejoConsola.preguntaSioNo("-Desea salir? s/n"))
						{
							System.out.println("Saliendo...");
							System.exit(0);
						}
					}
				}
			}
		}while(personas==null);
	}
	public static void tablaPuntajes()	// Imprime en consola una tabla con los resultados ordenados de mayor puntaje a menor.
	{
		System.out.println("Tabla de puntajes:");
		for(Persona p : personas)
		{
			p.getPuntosTotales(rondas);
		}
		Comparator<Persona> c=(o1,o2) -> o1.compareTo(o2);
		personas.sort(c.reversed());
		for(Persona p : personas)
		{
			int lugar=personas.indexOf(p)+1;
			System.out.println(lugar+")"+p.getNombre()+" con "+p.getPuntos()+" puntos.");
		}
	}
	
}


