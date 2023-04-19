package paquetePrincipal;


import java.util.InputMismatchException;
import java.util.Scanner;
public class ManejoConsola {
	private static Scanner entrada= new Scanner(System.in);
	
	public static int pedirEntero(String mensaje)
	{
		int entero;
		do {
			try
			{
				if(mensaje==null)System.out.print("Ingrese un valor numerico entero: ");
				else System.out.print(mensaje);
				entero=entrada.nextInt();
				return entero;
			}catch(InputMismatchException e)
			{
				System.out.println("Error de tipeo. Solo se aceptan numeros enteros");
				entrada.next();
			}
		}while(true);
	}
	public static String pedirTexto(String mensaje) 
	{
		String texto;
		if(mensaje==null)System.out.print("Ingrese un texto: ");
		else System.out.print(mensaje);
		texto=entrada.nextLine();
		if(texto.equals(""))
			texto=entrada.nextLine();
		
		return texto;
	}
	public static boolean preguntaSioNo(String pregunta)
	{
		do {
			if(pregunta==null)System.out.println("ingrese (s)i o (n)o.");
			else System.out.println(pregunta);
			String respuesta=entrada.next().toLowerCase();
			if(respuesta.contains("s")) {
				entrada.nextLine();
				return true;
			} else if(respuesta.contains("n"))
			{
				entrada.nextLine();
				return false;
			}
			else
			{
				System.out.println("ingrese (s)i o (n)o.");
			}
		}while(true);
	}
}
